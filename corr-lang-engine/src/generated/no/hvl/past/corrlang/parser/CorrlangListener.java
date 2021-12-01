// Generated from Corrlang.g4 by ANTLR 4.8
package no.hvl.past.corrlang.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CorrlangParser}.
 */
public interface CorrlangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#corrfile}.
	 * @param ctx the parse tree
	 */
	void enterCorrfile(CorrlangParser.CorrfileContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#corrfile}.
	 * @param ctx the parse tree
	 */
	void exitCorrfile(CorrlangParser.CorrfileContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#fileImport}.
	 * @param ctx the parse tree
	 */
	void enterFileImport(CorrlangParser.FileImportContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#fileImport}.
	 * @param ctx the parse tree
	 */
	void exitFileImport(CorrlangParser.FileImportContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#importURL}.
	 * @param ctx the parse tree
	 */
	void enterImportURL(CorrlangParser.ImportURLContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#importURL}.
	 * @param ctx the parse tree
	 */
	void exitImportURL(CorrlangParser.ImportURLContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#endpointDefinition}.
	 * @param ctx the parse tree
	 */
	void enterEndpointDefinition(CorrlangParser.EndpointDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#endpointDefinition}.
	 * @param ctx the parse tree
	 */
	void exitEndpointDefinition(CorrlangParser.EndpointDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#endpointName}.
	 * @param ctx the parse tree
	 */
	void enterEndpointName(CorrlangParser.EndpointNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#endpointName}.
	 * @param ctx the parse tree
	 */
	void exitEndpointName(CorrlangParser.EndpointNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#endpointType}.
	 * @param ctx the parse tree
	 */
	void enterEndpointType(CorrlangParser.EndpointTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#endpointType}.
	 * @param ctx the parse tree
	 */
	void exitEndpointType(CorrlangParser.EndpointTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#endpointPlaftorm}.
	 * @param ctx the parse tree
	 */
	void enterEndpointPlaftorm(CorrlangParser.EndpointPlaftormContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#endpointPlaftorm}.
	 * @param ctx the parse tree
	 */
	void exitEndpointPlaftorm(CorrlangParser.EndpointPlaftormContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#endpointURL}.
	 * @param ctx the parse tree
	 */
	void enterEndpointURL(CorrlangParser.EndpointURLContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#endpointURL}.
	 * @param ctx the parse tree
	 */
	void exitEndpointURL(CorrlangParser.EndpointURLContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#schemaURL}.
	 * @param ctx the parse tree
	 */
	void enterSchemaURL(CorrlangParser.SchemaURLContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#schemaURL}.
	 * @param ctx the parse tree
	 */
	void exitSchemaURL(CorrlangParser.SchemaURLContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#corrspec}.
	 * @param ctx the parse tree
	 */
	void enterCorrspec(CorrlangParser.CorrspecContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#corrspec}.
	 * @param ctx the parse tree
	 */
	void exitCorrspec(CorrlangParser.CorrspecContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#corrSpecEndpointRef}.
	 * @param ctx the parse tree
	 */
	void enterCorrSpecEndpointRef(CorrlangParser.CorrSpecEndpointRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#corrSpecEndpointRef}.
	 * @param ctx the parse tree
	 */
	void exitCorrSpecEndpointRef(CorrlangParser.CorrSpecEndpointRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#corrSpecName}.
	 * @param ctx the parse tree
	 */
	void enterCorrSpecName(CorrlangParser.CorrSpecNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#corrSpecName}.
	 * @param ctx the parse tree
	 */
	void exitCorrSpecName(CorrlangParser.CorrSpecNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#corrSpecType}.
	 * @param ctx the parse tree
	 */
	void enterCorrSpecType(CorrlangParser.CorrSpecTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#corrSpecType}.
	 * @param ctx the parse tree
	 */
	void exitCorrSpecType(CorrlangParser.CorrSpecTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#commonality}.
	 * @param ctx the parse tree
	 */
	void enterCommonality(CorrlangParser.CommonalityContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#commonality}.
	 * @param ctx the parse tree
	 */
	void exitCommonality(CorrlangParser.CommonalityContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#elmentRefDef}.
	 * @param ctx the parse tree
	 */
	void enterElmentRefDef(CorrlangParser.ElmentRefDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#elmentRefDef}.
	 * @param ctx the parse tree
	 */
	void exitElmentRefDef(CorrlangParser.ElmentRefDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#elmentRefAlias}.
	 * @param ctx the parse tree
	 */
	void enterElmentRefAlias(CorrlangParser.ElmentRefAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#elmentRefAlias}.
	 * @param ctx the parse tree
	 */
	void exitElmentRefAlias(CorrlangParser.ElmentRefAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#constraints}.
	 * @param ctx the parse tree
	 */
	void enterConstraints(CorrlangParser.ConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#constraints}.
	 * @param ctx the parse tree
	 */
	void exitConstraints(CorrlangParser.ConstraintsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(CorrlangParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(CorrlangParser.ConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#constraintName}.
	 * @param ctx the parse tree
	 */
	void enterConstraintName(CorrlangParser.ConstraintNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#constraintName}.
	 * @param ctx the parse tree
	 */
	void exitConstraintName(CorrlangParser.ConstraintNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#commonalityType}.
	 * @param ctx the parse tree
	 */
	void enterCommonalityType(CorrlangParser.CommonalityTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#commonalityType}.
	 * @param ctx the parse tree
	 */
	void exitCommonalityType(CorrlangParser.CommonalityTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#commonalityName}.
	 * @param ctx the parse tree
	 */
	void enterCommonalityName(CorrlangParser.CommonalityNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#commonalityName}.
	 * @param ctx the parse tree
	 */
	void exitCommonalityName(CorrlangParser.CommonalityNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#consistencyRuleRef}.
	 * @param ctx the parse tree
	 */
	void enterConsistencyRuleRef(CorrlangParser.ConsistencyRuleRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#consistencyRuleRef}.
	 * @param ctx the parse tree
	 */
	void exitConsistencyRuleRef(CorrlangParser.ConsistencyRuleRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#subCummonalities}.
	 * @param ctx the parse tree
	 */
	void enterSubCummonalities(CorrlangParser.SubCummonalitiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#subCummonalities}.
	 * @param ctx the parse tree
	 */
	void exitSubCummonalities(CorrlangParser.SubCummonalitiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#consistencyRule}.
	 * @param ctx the parse tree
	 */
	void enterConsistencyRule(CorrlangParser.ConsistencyRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#consistencyRule}.
	 * @param ctx the parse tree
	 */
	void exitConsistencyRule(CorrlangParser.ConsistencyRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#ruleName}.
	 * @param ctx the parse tree
	 */
	void enterRuleName(CorrlangParser.RuleNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#ruleName}.
	 * @param ctx the parse tree
	 */
	void exitRuleName(CorrlangParser.RuleNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#anonymousConssistencyRule}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousConssistencyRule(CorrlangParser.AnonymousConssistencyRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#anonymousConssistencyRule}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousConssistencyRule(CorrlangParser.AnonymousConssistencyRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#consistencyRuleBody}.
	 * @param ctx the parse tree
	 */
	void enterConsistencyRuleBody(CorrlangParser.ConsistencyRuleBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#consistencyRuleBody}.
	 * @param ctx the parse tree
	 */
	void exitConsistencyRuleBody(CorrlangParser.ConsistencyRuleBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#ruleLanguage}.
	 * @param ctx the parse tree
	 */
	void enterRuleLanguage(CorrlangParser.RuleLanguageContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#ruleLanguage}.
	 * @param ctx the parse tree
	 */
	void exitRuleLanguage(CorrlangParser.RuleLanguageContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#ruleBodyContent}.
	 * @param ctx the parse tree
	 */
	void enterRuleBodyContent(CorrlangParser.RuleBodyContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#ruleBodyContent}.
	 * @param ctx the parse tree
	 */
	void exitRuleBodyContent(CorrlangParser.RuleBodyContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#keyExpression}.
	 * @param ctx the parse tree
	 */
	void enterKeyExpression(CorrlangParser.KeyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#keyExpression}.
	 * @param ctx the parse tree
	 */
	void exitKeyExpression(CorrlangParser.KeyExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#keyAlternative}.
	 * @param ctx the parse tree
	 */
	void enterKeyAlternative(CorrlangParser.KeyAlternativeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#keyAlternative}.
	 * @param ctx the parse tree
	 */
	void exitKeyAlternative(CorrlangParser.KeyAlternativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#keyLiteral}.
	 * @param ctx the parse tree
	 */
	void enterKeyLiteral(CorrlangParser.KeyLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#keyLiteral}.
	 * @param ctx the parse tree
	 */
	void exitKeyLiteral(CorrlangParser.KeyLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#keyIdentity}.
	 * @param ctx the parse tree
	 */
	void enterKeyIdentity(CorrlangParser.KeyIdentityContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#keyIdentity}.
	 * @param ctx the parse tree
	 */
	void exitKeyIdentity(CorrlangParser.KeyIdentityContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#keyIdArgument}.
	 * @param ctx the parse tree
	 */
	void enterKeyIdArgument(CorrlangParser.KeyIdArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#keyIdArgument}.
	 * @param ctx the parse tree
	 */
	void exitKeyIdArgument(CorrlangParser.KeyIdArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#keyIdArgumentContent}.
	 * @param ctx the parse tree
	 */
	void enterKeyIdArgumentContent(CorrlangParser.KeyIdArgumentContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#keyIdArgumentContent}.
	 * @param ctx the parse tree
	 */
	void exitKeyIdArgumentContent(CorrlangParser.KeyIdArgumentContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#keyRelation}.
	 * @param ctx the parse tree
	 */
	void enterKeyRelation(CorrlangParser.KeyRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#keyRelation}.
	 * @param ctx the parse tree
	 */
	void exitKeyRelation(CorrlangParser.KeyRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#keyRelDirection}.
	 * @param ctx the parse tree
	 */
	void enterKeyRelDirection(CorrlangParser.KeyRelDirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#keyRelDirection}.
	 * @param ctx the parse tree
	 */
	void exitKeyRelDirection(CorrlangParser.KeyRelDirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(CorrlangParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(CorrlangParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#goalCorrespondence}.
	 * @param ctx the parse tree
	 */
	void enterGoalCorrespondence(CorrlangParser.GoalCorrespondenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#goalCorrespondence}.
	 * @param ctx the parse tree
	 */
	void exitGoalCorrespondence(CorrlangParser.GoalCorrespondenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#goalQuery}.
	 * @param ctx the parse tree
	 */
	void enterGoalQuery(CorrlangParser.GoalQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#goalQuery}.
	 * @param ctx the parse tree
	 */
	void exitGoalQuery(CorrlangParser.GoalQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#goalName}.
	 * @param ctx the parse tree
	 */
	void enterGoalName(CorrlangParser.GoalNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#goalName}.
	 * @param ctx the parse tree
	 */
	void exitGoalName(CorrlangParser.GoalNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#goalTechSpace}.
	 * @param ctx the parse tree
	 */
	void enterGoalTechSpace(CorrlangParser.GoalTechSpaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#goalTechSpace}.
	 * @param ctx the parse tree
	 */
	void exitGoalTechSpace(CorrlangParser.GoalTechSpaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#goalActionType}.
	 * @param ctx the parse tree
	 */
	void enterGoalActionType(CorrlangParser.GoalActionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#goalActionType}.
	 * @param ctx the parse tree
	 */
	void exitGoalActionType(CorrlangParser.GoalActionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#goalTargetType}.
	 * @param ctx the parse tree
	 */
	void enterGoalTargetType(CorrlangParser.GoalTargetTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#goalTargetType}.
	 * @param ctx the parse tree
	 */
	void exitGoalTargetType(CorrlangParser.GoalTargetTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#batchTarget}.
	 * @param ctx the parse tree
	 */
	void enterBatchTarget(CorrlangParser.BatchTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#batchTarget}.
	 * @param ctx the parse tree
	 */
	void exitBatchTarget(CorrlangParser.BatchTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#fileTarget}.
	 * @param ctx the parse tree
	 */
	void enterFileTarget(CorrlangParser.FileTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#fileTarget}.
	 * @param ctx the parse tree
	 */
	void exitFileTarget(CorrlangParser.FileTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#fileCreationOverwrite}.
	 * @param ctx the parse tree
	 */
	void enterFileCreationOverwrite(CorrlangParser.FileCreationOverwriteContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#fileCreationOverwrite}.
	 * @param ctx the parse tree
	 */
	void exitFileCreationOverwrite(CorrlangParser.FileCreationOverwriteContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#fileCreationTarget}.
	 * @param ctx the parse tree
	 */
	void enterFileCreationTarget(CorrlangParser.FileCreationTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#fileCreationTarget}.
	 * @param ctx the parse tree
	 */
	void exitFileCreationTarget(CorrlangParser.FileCreationTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#codegenTarget}.
	 * @param ctx the parse tree
	 */
	void enterCodegenTarget(CorrlangParser.CodegenTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#codegenTarget}.
	 * @param ctx the parse tree
	 */
	void exitCodegenTarget(CorrlangParser.CodegenTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#codegenVersion}.
	 * @param ctx the parse tree
	 */
	void enterCodegenVersion(CorrlangParser.CodegenVersionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#codegenVersion}.
	 * @param ctx the parse tree
	 */
	void exitCodegenVersion(CorrlangParser.CodegenVersionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#codegenGroup}.
	 * @param ctx the parse tree
	 */
	void enterCodegenGroup(CorrlangParser.CodegenGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#codegenGroup}.
	 * @param ctx the parse tree
	 */
	void exitCodegenGroup(CorrlangParser.CodegenGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#codegenArtefact}.
	 * @param ctx the parse tree
	 */
	void enterCodegenArtefact(CorrlangParser.CodegenArtefactContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#codegenArtefact}.
	 * @param ctx the parse tree
	 */
	void exitCodegenArtefact(CorrlangParser.CodegenArtefactContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#codegenTargetLocation}.
	 * @param ctx the parse tree
	 */
	void enterCodegenTargetLocation(CorrlangParser.CodegenTargetLocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#codegenTargetLocation}.
	 * @param ctx the parse tree
	 */
	void exitCodegenTargetLocation(CorrlangParser.CodegenTargetLocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#serverTarget}.
	 * @param ctx the parse tree
	 */
	void enterServerTarget(CorrlangParser.ServerTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#serverTarget}.
	 * @param ctx the parse tree
	 */
	void exitServerTarget(CorrlangParser.ServerTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#serverTargetPort}.
	 * @param ctx the parse tree
	 */
	void enterServerTargetPort(CorrlangParser.ServerTargetPortContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#serverTargetPort}.
	 * @param ctx the parse tree
	 */
	void exitServerTargetPort(CorrlangParser.ServerTargetPortContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#serverTargetContextPath}.
	 * @param ctx the parse tree
	 */
	void enterServerTargetContextPath(CorrlangParser.ServerTargetContextPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#serverTargetContextPath}.
	 * @param ctx the parse tree
	 */
	void exitServerTargetContextPath(CorrlangParser.ServerTargetContextPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(CorrlangParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(CorrlangParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#stringConstant}.
	 * @param ctx the parse tree
	 */
	void enterStringConstant(CorrlangParser.StringConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#stringConstant}.
	 * @param ctx the parse tree
	 */
	void exitStringConstant(CorrlangParser.StringConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#intConstant}.
	 * @param ctx the parse tree
	 */
	void enterIntConstant(CorrlangParser.IntConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#intConstant}.
	 * @param ctx the parse tree
	 */
	void exitIntConstant(CorrlangParser.IntConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#floatConstant}.
	 * @param ctx the parse tree
	 */
	void enterFloatConstant(CorrlangParser.FloatConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#floatConstant}.
	 * @param ctx the parse tree
	 */
	void exitFloatConstant(CorrlangParser.FloatConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#boolConstant}.
	 * @param ctx the parse tree
	 */
	void enterBoolConstant(CorrlangParser.BoolConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#boolConstant}.
	 * @param ctx the parse tree
	 */
	void exitBoolConstant(CorrlangParser.BoolConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link CorrlangParser#elmentRef}.
	 * @param ctx the parse tree
	 */
	void enterElmentRef(CorrlangParser.ElmentRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CorrlangParser#elmentRef}.
	 * @param ctx the parse tree
	 */
	void exitElmentRef(CorrlangParser.ElmentRefContext ctx);
}