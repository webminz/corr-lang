package no.hvl.past.corrlang.parser;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.reporting.ReportFacade;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DefaultCorrlangListener extends CorrlangBaseListener {

    private String fileName;
    private SyntacticalResult context;
    private ReportFacade reportFacade;
    private String currentName;
    private Endpoint currentEndpoint;
    private CorrSpec currentSpec;
    private Commonality currentCommonality;
    private ElementRef currentElementRef;
    private Commonality currentTopCommonality;
    private Key.KeyAlternative keyAlternative;
    private Key.KeyComposite keyComposite;
    private Key.KeyLiteral keyLiteral;
    private Key.KeyConstant keyConstant;
    private Key.KeyConcatenation keyConcatenation;
    private ConsistencyRule currentConsistencyRule;
    private boolean createSubCommonalities = false;
    private boolean buildingKeys = false;
    private int currentStartLine;
    private int currentStartColumn;

    public DefaultCorrlangListener(SyntacticalResult context, String fileName, ReportFacade reportFacade) {
        this.context = context;
        this.fileName = fileName;
        this.reportFacade = reportFacade;
    }

    public List<String> parsedElements() {
        return Collections.emptyList();
    }

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
        if (ctx.getText().equals("server")) {
            this.currentEndpoint = new ServerEndpoint(currentName);
        } else if (ctx.getText().equals("file")) {
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
            this.currentEndpoint.setGetStopTokenColumn(ctx.stop.getCharPositionInLine());
            context.addEndpoint(this.currentEndpoint);
            this.currentEndpoint = null;
            this.currentName = null;
        }
    }

    @Override
    public void enterCorrSpecName(CorrlangParser.CorrSpecNameContext ctx) {
        this.currentSpec = new CorrSpec(ctx.getText());
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
        context.addCorrspec(this.currentSpec);
        this.currentSpec = null;
    }

    @Override
    public void enterCommonalityType(CorrlangParser.CommonalityTypeContext ctx) {
        if (ctx.getText().equals("relate")) {
            this.currentCommonality = new Relation("___relationAt" + ctx.getStart().getLine());
        } else if (ctx.getText().equals("sync")) {
            this.currentCommonality = new Synchronisation("___syncAt" + ctx.getStart().getLine());
        } else if (ctx.getText().equals("identify")) {
            this.currentCommonality = new Identification("___idAt" + ctx.getStart().getLine());
        } else {
            throw new Error("Not supported");
        }
    }

    @Override
    public void exitCommonality(CorrlangParser.CommonalityContext ctx) {
        if (currentCommonality.getName().startsWith("___")) {
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
        if (createSubCommonalities) {
            this.currentTopCommonality.addSubCommonality(this.currentCommonality);
        } else {
            this.currentSpec.addCommonality(this.currentCommonality);
        }
    }

    @Override
    public void enterElmentRef(CorrlangParser.ElmentRefContext ctx) {
        this.currentElementRef = new ElementRef();
        String qualified = ctx.getText();
        String[] split = qualified.split("\\.");
        if (split.length >= 4) {// all
            this.currentElementRef.setEndpointName(split[0]);
            this.currentElementRef.setOwnerName(split[1]);
            this.currentElementRef.setName(split[2]);
            this.currentElementRef.setTargetName(split[3]);
        } else if (split.length == 3) { //edge
            this.currentElementRef.setEndpointName(split[0]);
            this.currentElementRef.setOwnerName(split[1]);
            this.currentElementRef.setName(split[2]);
        } else if (split.length == 2) { //node
            this.currentElementRef.setEndpointName(split[0]);
            this.currentElementRef.setName(split[1]);
        } else if (split.length == 1) { // unqualified
            this.currentElementRef.setName(split[0]);
        }
    }

    @Override
    public void exitElmentRef(CorrlangParser.ElmentRefContext ctx) {
        if (!buildingKeys) {
            this.currentCommonality.addRelatedElement(this.currentElementRef);
            this.currentElementRef = null;
        }
    }

    @Override
    public void enterCommonalityName(CorrlangParser.CommonalityNameContext ctx) {
        this.currentCommonality.setName(ctx.getText());
    }

    @Override
    public void enterSubCummonalities(CorrlangParser.SubCummonalitiesContext ctx) {
        this.createSubCommonalities = true;
        this.currentTopCommonality = this.currentCommonality;
        this.currentCommonality = null;
    }

    @Override
    public void exitSubCummonalities(CorrlangParser.SubCummonalitiesContext ctx) {
        this.createSubCommonalities = false;
        this.currentCommonality = this.currentTopCommonality;
        this.currentTopCommonality = null;
    }


    @Override
    public void enterKeyExpression(CorrlangParser.KeyExpressionContext ctx) {
        this.keyAlternative = new Key.KeyAlternative();
        this.buildingKeys = true;
    }

    @Override
    public void enterKeyAlternative(CorrlangParser.KeyAlternativeContext ctx) {
        this.keyComposite = new Key.KeyComposite();
    }


    @Override
    public void enterKeyLiteral(CorrlangParser.KeyLiteralContext ctx) {
        this.keyLiteral = new Key.KeyLiteral();
    }

    @Override
    public void enterKeyIdentity(CorrlangParser.KeyIdentityContext ctx) {
        this.keyLiteral.setType(Key.KeyLiteral.Type.EQ);
    }

    @Override
    public void enterKeyRelation(CorrlangParser.KeyRelationContext ctx) {
        this.keyLiteral.setType(Key.KeyLiteral.Type.REL);
    }

    @Override
    public void enterStringConstant(CorrlangParser.StringConstantContext ctx) {
        this.keyConstant = new Key.KeyConstant(ctx.getText(), Key.KeyConstant.Type.STRING);
    }

    @Override
    public void enterIntConstant(CorrlangParser.IntConstantContext ctx) {
        this.keyConstant = new Key.KeyConstant(ctx.getText(), Key.KeyConstant.Type.INT);
    }

    @Override
    public void enterBoolConstant(CorrlangParser.BoolConstantContext ctx) {
        this.keyConstant = new Key.KeyConstant(ctx.getText(), Key.KeyConstant.Type.BOOL);
    }

    @Override
    public void enterFloatConstant(CorrlangParser.FloatConstantContext ctx) {
        this.keyConstant = new Key.KeyConstant(ctx.getText(), Key.KeyConstant.Type.FLOAT);
    }

    @Override
    public void enterEnumConstant(CorrlangParser.EnumConstantContext ctx) {
        this.keyConstant = new Key.KeyConstant(ctx.getText(), Key.KeyConstant.Type.ENUM_LITERAL);
    }

    @Override
    public void enterKeyArgumentSuffix(CorrlangParser.KeyArgumentSuffixContext ctx) {
        if (keyConcatenation == null) {
            this.keyConcatenation = new Key.KeyConcatenation();
            if (keyConstant != null) {
                keyConcatenation.add(keyConstant);
                keyConstant = null;
            } else if (currentElementRef != null) {
                keyConcatenation.add(currentElementRef);
                currentElementRef = null;
            }
        }
    }

    @Override
    public void exitKeyArgumentSuffix(CorrlangParser.KeyArgumentSuffixContext ctx) {
        if (keyConstant != null) {
            keyConcatenation.add(keyConstant);
            keyConstant = null;
        } else if (currentElementRef != null) {
            keyConcatenation.add(currentElementRef);
            currentElementRef = null;
        }
    }

    @Override
    public void exitKeyArgument(CorrlangParser.KeyArgumentContext ctx) {
        if (keyConcatenation != null) {
            this.keyLiteral.add(keyConcatenation);
            this.keyConcatenation = null;
        } else if (keyConstant != null) {
            this.keyLiteral.add(this.keyConstant);
            this.keyConstant = null;
        } else if (currentElementRef != null) {
            this.keyLiteral.add(this.currentElementRef);
            this.currentElementRef = null;
        }

    }

    @Override
    public void exitKeyLiteral(CorrlangParser.KeyLiteralContext ctx) {
        this.keyComposite.add(this.keyLiteral);
        this.keyLiteral = null;
    }

    @Override
    public void exitKeyAlternative(CorrlangParser.KeyAlternativeContext ctx) {
        this.keyAlternative.add(keyComposite);
        this.keyComposite = null;
    }

    @Override
    public void exitKeyExpression(CorrlangParser.KeyExpressionContext ctx) {
        this.currentCommonality.setKey(this.keyAlternative);
        this.keyAlternative = null;
        this.buildingKeys = false;
    }

    @Override
    public void enterRuleName(CorrlangParser.RuleNameContext ctx) {
        this.currentConsistencyRule = new ConsistencyRule(ctx.getText());
    }

    @Override
    public void enterRuleLanguage(CorrlangParser.RuleLanguageContext ctx) {
        if (ctx.getText().equals("OCL")) {
            this.currentConsistencyRule.setLanguage(ConsistencyRule.ConsistencyRuleLanguage.OCL);
        } else if (ctx.getText().equals("EVL")) {
            this.currentConsistencyRule.setLanguage(ConsistencyRule.ConsistencyRuleLanguage.EVL);
        }
    }


    @Override
    public void enterRuleBodyContent(CorrlangParser.RuleBodyContentContext ctx) {
        String body = ctx.getText();
        body = body.substring(3); // skiping the first """
        body = body.substring(0, body.indexOf("\"\"\"")); // up the last demarkating """
        body = body.trim(); // removing unecessary whitespace in the beginning and end
        this.currentConsistencyRule.setBody(body);
    }

    @Override
    public void exitConsistencyRule(CorrlangParser.ConsistencyRuleContext ctx) {
        context.addRule(this.currentConsistencyRule);
    }

    @Override
    public void enterImportURL(CorrlangParser.ImportURLContext ctx) {
        context.addImport(ctx.getText());
    }
}
