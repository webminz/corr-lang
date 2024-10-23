package io.corrlang.engine.parser;

import io.corrlang.engine.domainmodel.*;
import io.corrlang.engine.reporting.ReportFacade;
import no.hvl.past.util.ShouldNotHappenException;
import io.corrlang.parser.CorrlangBaseListener;
import io.corrlang.parser.CorrlangParser;
import java.util.Iterator;
import java.util.Stack;

public class DefaultCorrlangListener extends CorrlangBaseListener {

    private String fileName;
    private SyntacticalResult context;
    private ReportFacade reportFacade;
    private String currentName;
    private Endpoint currentEndpoint;
    private CorrSpec currentSpec;
    private Commonality currentCommonality;
    private ElementRef currentElementRef;
    private Stack<Commonality> parentCommonalityStack;
    private ElementCondition.Alternative keyAlternative;
    private ElementCondition.Conjunction keyComposite;
    private ElementCondition.Identification keyIdentification;
    private ElementCondition.ArgumentConcatenation keyConcatenation;
    private ElementCondition.RelationRule keyRelation;
    private Stack<ElementCondition.RelationRule> parentKeyRelations;
    private ConsistencyRule currentConsistencyRule;
    private boolean buildingKeys = false;
    private int currentStartLine;
    private int currentStartColumn;
    private Goal currenGoal;

    public DefaultCorrlangListener(SyntacticalResult context, String fileName, ReportFacade reportFacade) {
        this.context = context;
        this.fileName = fileName;
        this.reportFacade = reportFacade;
        this.parentCommonalityStack = new Stack<>();
        this.parentKeyRelations = new Stack<>();
    }

    // Endpoints

    @Override
    public void enterEndpointDefinition(CorrlangParser.EndpointDefinitionContext ctx) {
        this.currentStartLine = ctx.getStart().getLine();
        this.currentStartColumn = ctx.getStart().getCharPositionInLine();
    }

    @Override
    public void enterEndpointName(CorrlangParser.EndpointNameContext ctx) {
        this.currentName = ctx.getText();
    }

    @Override
    public void enterEndpointType(CorrlangParser.EndpointTypeContext ctx) {
        if (ctx.getText().equals("SERVER")) {
            this.currentEndpoint = new ServerEndpoint(currentName);
        } else if (ctx.getText().equals("FILE")) {
            this.currentEndpoint = new FileEndpoint(currentName);
        } else {
            reportFacade.reportSyntaxError(fileName,ctx.start.getLine(), ctx.start.getCharPositionInLine(),"Unknown endpoint type '" + ctx.getText() + "'!");
        }
    }

    @Override
    public void enterEndpointPlaftorm(CorrlangParser.EndpointPlaftormContext ctx) {
        this.currentEndpoint.setTechnology(ctx.getText());
    }

    @Override
    public void enterEndpointURL(CorrlangParser.EndpointURLContext ctx) {
        this.currentEndpoint.setLocationURL(ctx.getText());
    }

    @Override
    public void enterSchemaURL(CorrlangParser.SchemaURLContext ctx) {
        this.currentEndpoint.setSchemaURL(ctx.getText());
    }

    @Override
    public void exitEndpointDefinition(CorrlangParser.EndpointDefinitionContext ctx) {
        if (this.currentEndpoint != null) {
            this.currentEndpoint.setFile(fileName);
            this.currentEndpoint.setStartTokenLine(this.currentStartLine);
            this.currentEndpoint.setStartTokenColumn(this.currentStartColumn);
            this.currentEndpoint.setStopTokenLine(ctx.stop.getLine());
            this.currentEndpoint.setStopTokenColumn(ctx.stop.getCharPositionInLine());
            context.addEndpoint(this.currentEndpoint);
            this.currentEndpoint = null;
            this.currentName = null;
        }
    }

    // Corrspec

    @Override
    public void enterCorrSpecName(CorrlangParser.CorrSpecNameContext ctx) {
        this.currentSpec = new CorrSpec(ctx.getText());
        this.currentSpec.setFile(fileName);
        this.currentSpec.setStartTokenLine(ctx.start.getLine());
        this.currentSpec.setStartTokenColumn(ctx.start.getCharPositionInLine());
    }

    @Override
    public void enterCorrSpecType(CorrlangParser.CorrSpecTypeContext ctx) {
        this.currentSpec.setTypedOverName(ctx.getText());
    }

    @Override
    public void enterCorrSpecEndpointRef(CorrlangParser.CorrSpecEndpointRefContext ctx) {
        this.currentSpec.addEndpoint(ctx.getText());
    }

    @Override
    public void exitCorrspec(CorrlangParser.CorrspecContext ctx) {
        this.currentSpec.setStopTokenLine(ctx.stop.getLine());
        this.currentSpec.setStopTokenColumn(ctx.stop.getCharPositionInLine());
        context.addCorrspec(this.currentSpec);
        this.currentSpec = null;
    }

    // Commonality

    @Override
    public void enterCommonalityType(CorrlangParser.CommonalityTypeContext ctx) {
        if (ctx.getText().equals("relate")) {
            this.currentCommonality = new Relation();
        } else if (ctx.getText().equals("sync")) {
            this.currentCommonality = new Synchronisation();
        } else if (ctx.getText().equals("identify")) {
            this.currentCommonality = new Identification();
        } else {
            throw new ShouldNotHappenException(DefaultCorrlangListener.class, "You should never see this message, since the parser should prevent you from writing '" +
                    ctx.getText() + "' here! ("+ fileName + ":" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + ")" );
        }

        this.currentCommonality.setFile(fileName);
        this.currentCommonality.setStartTokenLine(ctx.start.getLine());
        this.currentCommonality.setStartTokenColumn(ctx.start.getCharPositionInLine());

    }

    @Override
    public void exitCommonality(CorrlangParser.CommonalityContext ctx) {
        this.currentCommonality.setStopTokenLine(ctx.stop.getLine());
        this.currentCommonality.setStopTokenColumn(ctx.stop.getCharPositionInLine());

        if (currentCommonality.getName().isEmpty()) {
            if (currentCommonality instanceof Identification) {
                currentCommonality.setName(currentCommonality.getRelates().get(0).getName());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(currentCommonality instanceof Synchronisation ? "sync" : "relate");
                stringBuilder.append("(");
                Iterator<ElementRef> iterator = this.currentCommonality.getRelates().iterator();
                while (iterator.hasNext()) {
                    ElementRef current = iterator.next();
                    stringBuilder.append(current.getName());
                    if (iterator.hasNext()) {
                        stringBuilder.append(',');
                    }
                }
                stringBuilder.append(")");
                currentCommonality.setName(stringBuilder.toString());
            }
        }
        if (this.parentCommonalityStack.isEmpty()) {
            this.currentSpec.addCommonality(this.currentCommonality);
        } else {
            this.parentCommonalityStack.peek().addSubCommonality(this.currentCommonality);
        }
        this.currentCommonality = null;
    }

    @Override
    public void enterElmentRef(CorrlangParser.ElmentRefContext ctx) {
        String qualified = ctx.getText();
        String[] split = qualified.split("\\.");
        this.currentElementRef = new ElementRef(split);
        this.currentElementRef.setFile(fileName);
        this.currentElementRef.setStartTokenLine(ctx.start.getLine());
        this.currentElementRef.setStartTokenColumn(ctx.start.getCharPositionInLine());
    }

    @Override
    public void enterElmentRefAlias(CorrlangParser.ElmentRefAliasContext ctx) {
        this.currentElementRef.setAlias(ctx.getText());
    }

    @Override
    public void exitElmentRefDef(CorrlangParser.ElmentRefDefContext ctx) {
        if (!buildingKeys) {
            this.currentCommonality.addRelatedElement(this.currentElementRef);
            this.currentElementRef = null;
        }
    }

    @Override
    public void exitElmentRef(CorrlangParser.ElmentRefContext ctx) {
        if (buildingKeys) {
            if (this.keyIdentification != null || this.keyConcatenation != null) {
                if (this.keyConcatenation != null) {
                    this.keyConcatenation.add(this.currentElementRef);
                } else {
                    this.keyIdentification.add(this.currentElementRef);
                }
            } else if (this.keyRelation != null) {
                if (this.keyRelation.getLeft() == null) {
                    this.keyRelation.setLeft(this.currentElementRef);
                } else {
                    this.keyRelation.setRight(this.currentElementRef);
                }
            }
        }
    }

    @Override
    public void enterCommonalityName(CorrlangParser.CommonalityNameContext ctx) {
        this.currentCommonality.setName(ctx.getText());
    }

    @Override
    public void enterSubCummonalities(CorrlangParser.SubCummonalitiesContext ctx) {
        this.parentCommonalityStack.push(this.currentCommonality);
        this.currentCommonality = null;
    }

    @Override
    public void exitSubCummonalities(CorrlangParser.SubCummonalitiesContext ctx) {
        this.currentCommonality = this.parentCommonalityStack.pop();
    }


    // Keys


    @Override
    public void enterKeyExpression(CorrlangParser.KeyExpressionContext ctx) {
        this.buildingKeys = true;
        this.keyAlternative = new ElementCondition.Alternative();
        this.keyAlternative.setFile(fileName);
        this.keyAlternative.setStartTokenLine(ctx.start.getLine());
        this.keyAlternative.setStartTokenColumn(ctx.start.getCharPositionInLine());
    }

    @Override
    public void exitKeyExpression(CorrlangParser.KeyExpressionContext ctx) {
        if (this.keyAlternative.getArguments().size() == 1) {
            this.currentCommonality.setKey(this.keyAlternative.getArguments().get(0));
        } else {
            this.keyAlternative.setStopTokenLine(ctx.stop.getLine());
            this.keyAlternative.setStopTokenColumn(ctx.stop.getCharPositionInLine());
            this.currentCommonality.setKey(this.keyAlternative);
        }
        this.keyAlternative = null;
        this.buildingKeys = false;
    }


    @Override
    public void enterKeyAlternative(CorrlangParser.KeyAlternativeContext ctx) {
        this.keyComposite = new ElementCondition.Conjunction();
        this.keyComposite.setFile(fileName);
        this.keyComposite.setStartTokenLine(ctx.start.getLine());
        this.keyComposite.setStartTokenColumn(ctx.start.getCharPositionInLine());
    }

    @Override
    public void exitKeyAlternative(CorrlangParser.KeyAlternativeContext ctx) {
        if (this.keyComposite.getLiterals().size() == 1) {
            this.keyAlternative.add(this.keyComposite.getLiterals().get(0));
        } else {
            this.keyComposite.setStopTokenLine(ctx.stop.getLine());
            this.keyComposite.setStopTokenColumn(ctx.stop.getCharPositionInLine());
            this.keyAlternative.add(this.keyComposite);
        }
        this.keyComposite = null;
    }


    @Override
    public void enterKeyIdentity(CorrlangParser.KeyIdentityContext ctx) {
        this.keyIdentification = new ElementCondition.Identification();
        this.keyIdentification.setFile(fileName);
        this.keyIdentification.setStartTokenLine(ctx.start.getLine());
        this.keyIdentification.setStartTokenColumn(ctx.start.getCharPositionInLine());
    }

    @Override
    public void exitKeyIdentity(CorrlangParser.KeyIdentityContext ctx) {
        this.keyIdentification.setStopTokenLine(ctx.stop.getLine());
        this.keyIdentification.setStopTokenColumn(ctx.stop.getCharPositionInLine());
        this.keyComposite.add(this.keyIdentification);
        this.keyIdentification = null;
    }

    @Override
    public void enterKeyIdArgument(CorrlangParser.KeyIdArgumentContext ctx) {
        this.keyConcatenation = new ElementCondition.ArgumentConcatenation();
    }


    @Override
    public void exitKeyIdArgument(CorrlangParser.KeyIdArgumentContext ctx) {
        if (this.keyConcatenation.getParts().size() ==1) {
            this.keyIdentification.add(this.keyConcatenation.getParts().get(0));
        } else {
            this.keyIdentification.add(this.keyConcatenation);
        }
        this.keyConcatenation = null;
    }

    @Override
    public void enterStringConstant(CorrlangParser.StringConstantContext ctx) {
        this.keyConcatenation.add(new ElementCondition.ConstantArgument(ctx.getText().substring(1,ctx.getText().length()-1), ElementCondition.ConstantArgument.Type.STRING));
    }

    @Override
    public void enterIntConstant(CorrlangParser.IntConstantContext ctx) {
        if (this.keyConcatenation != null) {
            this.keyConcatenation.add(new ElementCondition.ConstantArgument(ctx.getText(), ElementCondition.ConstantArgument.Type.INT));
        } else {
            this.keyIdentification.add(new ElementCondition.ConstantArgument(ctx.getText(), ElementCondition.ConstantArgument.Type.INT));
        }
    }

    @Override
    public void enterBoolConstant(CorrlangParser.BoolConstantContext ctx) {
        if (this.keyConcatenation != null) {
            this.keyConcatenation.add(new ElementCondition.ConstantArgument(ctx.getText(), ElementCondition.ConstantArgument.Type.BOOL));
        } else {
            this.keyIdentification.add(new ElementCondition.ConstantArgument(ctx.getText(), ElementCondition.ConstantArgument.Type.BOOL));
        }
    }

    @Override
    public void enterFloatConstant(CorrlangParser.FloatConstantContext ctx) {
        if (this.keyConcatenation != null) {
            this.keyConcatenation.add(new ElementCondition.ConstantArgument(ctx.getText(), ElementCondition.ConstantArgument.Type.FLOAT));
        } else {
            this.keyIdentification.add(new ElementCondition.ConstantArgument(ctx.getText(), ElementCondition.ConstantArgument.Type.FLOAT));
        }
    }

    @Override
    public void enterKeyRelation(CorrlangParser.KeyRelationContext ctx) {
        if (this.keyRelation == null) {
            this.keyRelation = new ElementCondition.RelationRule();
            this.keyRelation.setFile(fileName);
            this.keyRelation.setStartTokenLine(ctx.start.getLine());
            this.keyRelation.setStartTokenColumn(ctx.start.getCharPositionInLine());
        } else {
            ElementCondition.RelationRule rel = new ElementCondition.RelationRule();
            this.keyRelation.setRight(rel);
            this.parentKeyRelations.push(this.keyRelation);
            this.keyRelation = rel;
        }
    }

    @Override
    public void enterKeyRelDirection(CorrlangParser.KeyRelDirectionContext ctx) {
        switch (ctx.getText()) {
            case "<~>":
                this.keyRelation.setDirection(ElementCondition.RelationRule.Direction.SYMM);
                break;
            case "~~>":
                this.keyRelation.setDirection(ElementCondition.RelationRule.Direction.LR);
                break;
            case "<~~":
                this.keyRelation.setDirection(ElementCondition.RelationRule.Direction.RL);
                break;
        }
    }

    @Override
    public void exitKeyRelation(CorrlangParser.KeyRelationContext ctx) {
        if (this.parentKeyRelations.isEmpty()) {
            this.keyComposite.add(this.keyRelation);
        } else {
            this.keyRelation = this.parentKeyRelations.pop();
        }
    }

    // Consistency Rules


    @Override
    public void enterAnonymousConssistencyRule(CorrlangParser.AnonymousConssistencyRuleContext ctx) {
        ConsistencyRule anon = new ConsistencyRule(this.currentCommonality.getName() + "_anonymousConsistencyRule");
        this.currentConsistencyRule = anon;
        this.currentCommonality.setConsistencyRule(anon);
    }

    @Override
    public void exitAnonymousConssistencyRule(CorrlangParser.AnonymousConssistencyRuleContext ctx) {
        this.currentConsistencyRule = null;
    }

    @Override
    public void enterRuleName(CorrlangParser.RuleNameContext ctx) {
        this.currentConsistencyRule = new ConsistencyRule(ctx.getText());
    }

    @Override
    public void enterConsistencyRuleRef(CorrlangParser.ConsistencyRuleRefContext ctx) {
        this.currentCommonality.setRuleName(ctx.getText());
    }

    @Override
    public void enterRuleLanguage(CorrlangParser.RuleLanguageContext ctx) {
        this.currentConsistencyRule.setLanguage(ctx.getText());
    }



    @Override
    public void enterRuleBodyContent(CorrlangParser.RuleBodyContentContext ctx) {
        String body = ctx.getText();
        body = body.substring(3); // skiping the first """
        body = body.substring(0, body.indexOf("'''")); // up the last demarcating """
        body = body.trim(); // removing unecessary whitespace in the beginning and end
        this.currentConsistencyRule.setBody(body);
    }


    @Override
    public void exitConsistencyRule(CorrlangParser.ConsistencyRuleContext ctx) {
        context.addRule(this.currentConsistencyRule);
        this.currentConsistencyRule = null;
    }

    @Override
    public void enterImportURL(CorrlangParser.ImportURLContext ctx) {
        context.addImport(ctx.getText());
    }


    @Override
    public void enterGoal(CorrlangParser.GoalContext ctx) {
        this.currentStartLine = ctx.getStart().getLine();
        this.currentStartColumn = ctx.getStart().getCharPositionInLine();
    }

    @Override
    public void enterGoalName(CorrlangParser.GoalNameContext ctx) {
        this.currenGoal = new Goal(ctx.getText());
    }


    @Override
    public void exitGoal(CorrlangParser.GoalContext ctx) {
        this.currenGoal.setStartTokenColumn(currentStartLine);
        this.currenGoal.setStartTokenColumn(currentStartColumn);
        this.currenGoal.setStopTokenLine(ctx.getStop().getLine());
        this.currenGoal.setStopTokenColumn(ctx.getStop().getCharPositionInLine());
        context.addGoal(this.currenGoal);
        this.currenGoal = null;
    }

    @Override
    public void enterGoalTechSpace(CorrlangParser.GoalTechSpaceContext ctx) {
        this.currenGoal.setTechnology(ctx.getText());
    }

    @Override
    public void enterGoalActionType(CorrlangParser.GoalActionTypeContext ctx) {
        switch (ctx.getText()) {
            case "SCHEMA":
                this.currenGoal.setAction(Goal.Action.SCHEMA);
                break;
            case "FEDERATION":
                this.currenGoal.setAction(Goal.Action.FEDERATION);
                break;
            case "TRANSFORMATION":
                this.currenGoal.setAction(Goal.Action.TRANSFORMATION);
                break;
            case "MATCH":
                this.currenGoal.setAction(Goal.Action.MATCH);
                break;
            case "VERIFY":
                this.currenGoal.setAction(Goal.Action.VERIFY);
                break;
            case "SYNCHRONIZE":
                this.currenGoal.setAction(Goal.Action.SYNCHRONIZE);
                break;
            default: new ShouldNotHappenException(DefaultCorrlangListener.class, "You should never see this message, since the parser should prevent you from writing '" +
                    ctx.getText() + "' here! ("+ fileName + ":" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + ")" );
        }
    }

    @Override
    public void enterServerTarget(CorrlangParser.ServerTargetContext ctx) {
        this.currenGoal.setTarget(GoalTarget.server());
    }

    @Override
    public void enterServerTargetContextPath(CorrlangParser.ServerTargetContextPathContext ctx) {
        try {
            this.currenGoal.forTarget(new GoalTarget.Visitor() {
                @Override
                public void handle(GoalTarget.ServerRuntime serverRuntime) throws Exception {
                    serverRuntime.setContextPath(ctx.getText());
                }

                @Override
                public void handle(GoalTarget.CodeGeneration codeGeneration) throws Exception {
                }

                @Override
                public void handle(GoalTarget.FileCreation fileCreation) throws Exception {
                }

                @Override
                public void handle(GoalTarget.Batch batch) throws Exception {
                }
            });
        } catch (Throwable e) {
            throw new ShouldNotHappenException(DefaultCorrlangListener.class,e);
        }
    }

    @Override
    public void enterServerTargetPort(CorrlangParser.ServerTargetPortContext ctx) {
        try {
            this.currenGoal.forTarget(new GoalTarget.Visitor() {
                @Override
                public void handle(GoalTarget.ServerRuntime serverRuntime) throws Exception {
                    serverRuntime.setPort(Integer.parseInt(ctx.getText()));
                }

                @Override
                public void handle(GoalTarget.CodeGeneration codeGeneration) throws Exception {
                }

                @Override
                public void handle(GoalTarget.FileCreation fileCreation) throws Exception {
                }

                @Override
                public void handle(GoalTarget.Batch batch) throws Exception {
                }
            });
        } catch (Throwable e) {
            throw new ShouldNotHappenException(DefaultCorrlangListener.class,e);
        }
    }

    @Override
    public void enterCodegenTarget(CorrlangParser.CodegenTargetContext ctx) {
        this.currenGoal.setTarget(GoalTarget.codegen());
    }

    @Override
    public void enterFileTarget(CorrlangParser.FileTargetContext ctx) {
        this.currenGoal.setTarget(GoalTarget.file());
    }

    @Override
    public void enterBatchTarget(CorrlangParser.BatchTargetContext ctx) {
        this.currenGoal.setTarget(GoalTarget.batch());
    }

    @Override
    public void enterGoalCorrespondence(CorrlangParser.GoalCorrespondenceContext ctx) {
       this.currenGoal.setCorrespondenceName(ctx.getText());
    }


    @Override
    public void enterFileCreationTarget(CorrlangParser.FileCreationTargetContext ctx) {
        try {
            this.currenGoal.forTarget(new GoalTarget.Visitor() {
                @Override
                public void handle(GoalTarget.ServerRuntime serverRuntime) throws Exception {
                }

                @Override
                public void handle(GoalTarget.CodeGeneration codeGeneration) throws Exception {
                }

                @Override
                public void handle(GoalTarget.FileCreation fileCreation) throws Exception {
                    fileCreation.setLocation(ctx.getText());
                }

                @Override
                public void handle(GoalTarget.Batch batch) throws Exception {
                }
            });
        } catch (Throwable e) {
            throw new ShouldNotHappenException(DefaultCorrlangListener.class,e);
        }
    }
}
