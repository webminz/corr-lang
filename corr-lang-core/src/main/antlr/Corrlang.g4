grammar Corrlang;

corrfile: WHITESPACE? (fileImport)* (endpointDefinition | corrspec | consistencyRule | goal)+ EOF;

fileImport: 'import' WHITESPACE importURL WHITESPACE? ';' WHITESPACE?;
importURL: URL_STRING;

endpointDefinition: 'endpoint' WHITESPACE  endpointName WHITESPACE '{' WHITESPACE
                    'type' WHITESPACE endpointType WHITESPACE
                    'at' WHITESPACE endpointURL WHITESPACE
                    'technology' WHITESPACE endpointPlaftorm WHITESPACE
                    ('schema' WHITESPACE schemaURL WHITESPACE)?
                    '}' WHITESPACE?;

endpointName : IDENTIFIER;
endpointType: 'FILE' | 'SERVER';
endpointPlaftorm: TECH_IDENTIFIER | IDENTIFIER;
endpointURL: QUALIFIED_ID | URL_STRING;
schemaURL: QUALIFIED_ID | URL_STRING;

corrspec: 'correspondence' WHITESPACE corrSpecName WHITESPACE
            ('instantiates' WHITESPACE corrSpecType WHITESPACE)?
            '(' WHITESPACE? corrSpecEndpointRef WHITESPACE? (',' WHITESPACE? corrSpecEndpointRef WHITESPACE?)+ ')' WHITESPACE
            '{' (WHITESPACE commonality)* WHITESPACE? '}' WHITESPACE?;

corrSpecEndpointRef: IDENTIFIER;
corrSpecName: IDENTIFIER;
corrSpecType: IDENTIFIER;

commonality: commonalityType WHITESPACE?
            '(' WHITESPACE? elmentRefDef WHITESPACE? (',' WHITESPACE? elmentRefDef WHITESPACE?)+ ')'
            (WHITESPACE 'as' WHITESPACE commonalityName constraints?)?
            (subCummonalities)?
            (WHITESPACE 'when' WHITESPACE '(' WHITESPACE? keyExpression WHITESPACE? ')' )?
            (WHITESPACE 'check' WHITESPACE (anonymousConssistencyRule || consistencyRuleRef))?
            ';';

elmentRefDef: elmentRef (WHITESPACE 'as' WHITESPACE elmentRefAlias)?;

elmentRefAlias: IDENTIFIER;


constraints: (WHITESPACE constraint)+;

constraint: '[' constraintName ']';
constraintName: URL_STRING;


commonalityType: 'relate' | 'sync' | 'identify';
commonalityName: QUALIFIED_ID | IDENTIFIER;
consistencyRuleRef: IDENTIFIER;
subCummonalities: WHITESPACE 'with' WHITESPACE '{' (WHITESPACE commonality)+ WHITESPACE? '}';

consistencyRule: 'rule' WHITESPACE ruleName WHITESPACE consistencyRuleBody WHITESPACE?;
ruleName: IDENTIFIER;
anonymousConssistencyRule: consistencyRuleBody;
consistencyRuleBody: '{' WHITESPACE 'using' WHITESPACE ruleLanguage WHITESPACE ruleBodyContent WHITESPACE '}';
ruleLanguage: TECH_IDENTIFIER | IDENTIFIER;
ruleBodyContent: EXTERNAL_CODE;

keyExpression: keyAlternative ('||' WHITESPACE keyAlternative)*;
keyAlternative: keyLiteral ('&&' WHITESPACE keyLiteral)*;
keyLiteral: keyIdentity | keyRelation;
keyIdentity: keyIdArgument '==' WHITESPACE keyIdArgument ('==' WHITESPACE keyIdArgument)*;
keyIdArgument: keyIdArgumentContent  ('++' WHITESPACE keyIdArgumentContent)*;
keyIdArgumentContent: (elmentRef | constant) WHITESPACE;
keyRelation:  elmentRef WHITESPACE keyRelDirection  WHITESPACE (elmentRef | keyRelation);
keyRelDirection: '<~>' | '~~>' | '<~~';


goal: 'goal' WHITESPACE goalName WHITESPACE '{' WHITESPACE
        'correspondence' WHITESPACE goalCorrespondence WHITESPACE
       'action' WHITESPACE goalActionType WHITESPACE
        ('technology' WHITESPACE goalTechSpace WHITESPACE)?
        'target' WHITESPACE goalTargetType WHITESPACE
        ('query' WHITESPACE goalQuery WHITESPACE)?
        '}' WHITESPACE?;

goalCorrespondence: IDENTIFIER;

goalQuery: URL_STRING;
goalName: IDENTIFIER;
goalTechSpace: TECH_IDENTIFIER | IDENTIFIER;
goalActionType: 'SCHEMA' | 'FEDERATION' | 'TRANSFORMATION' | 'MATCH' | 'VERIFY' | 'RESTORE';
goalTargetType: serverTarget | codegenTarget | fileTarget | batchTarget;

batchTarget: 'LIB';

fileTarget: 'FILE' WHITESPACE '{' WHITESPACE 'at' WHITESPACE fileCreationTarget WHITESPACE ('overwrite' WHITESPACE fileCreationOverwrite WHITESPACE)? '}';

fileCreationOverwrite: BOOL;
fileCreationTarget: URL_STRING;

codegenTarget: 'CODEGEN' WHITESPACE '{' WHITESPACE
        'outputDir' WHITESPACE codegenTargetLocation WHITESPACE 
        'artefactId' WHITESPACE codegenArtefact WHITESPACE
        'groupId' WHITESPACE codegenGroup WHITESPACE
        ('version' WHITESPACE codegenVersion WHITESPACE)?
        '}';

codegenVersion: URL_STRING;
codegenGroup: URL_STRING;
codegenArtefact: URL_STRING;
codegenTargetLocation: URL_STRING;

serverTarget: 'SERVER' WHITESPACE '{' WHITESPACE 'contextPath' WHITESPACE serverTargetContextPath WHITESPACE 'port' WHITESPACE serverTargetPort WHITESPACE '}';

serverTargetPort: INTEGER;
serverTargetContextPath: URL_STRING;


constant: stringConstant | boolConstant;

stringConstant: '"' (WHITESPACE | IDENTIFIER) '"';
intConstant: INTEGER;
floatConstant: FLOAT;
boolConstant: 'true' | 'false';

elmentRef: QUALIFIED_ID;


fragment SPACE: ' ';
fragment TAB: '\t';
fragment LINE_TABULATION: '\u000B';
fragment NEWLINE: '\n';
fragment CARRIAGE_RETURN: '\r';
fragment WHITESPACE_CHAR: SPACE | TAB | LINE_TABULATION | NEWLINE | CARRIAGE_RETURN;
WHITESPACE: WHITESPACE_CHAR+;


fragment UNDERSCORE: '_';
fragment DIGIT: '0' .. '9';
fragment CAPITAL_LETTER: 'A' .. 'Z';
fragment LETTER: 'a' .. 'z' | 'A' .. 'Z';
fragment CAPITAL_LETTER_OR_UNDERSCORE: CAPITAL_LETTER | UNDERSCORE;
fragment LETTER_OR_UNDERSCORE: LETTER | '_';
fragment LETTER_OR_DIGIT: LETTER | DIGIT;

INTEGER: '-' DIGIT+ | DIGIT+;
FLOAT: INTEGER ('.' DIGIT+);


IDENTIFIER: LETTER_OR_UNDERSCORE LETTER_OR_DIGIT*;
QUALIFIED_ID: LETTER_OR_UNDERSCORE (LETTER_OR_DIGIT | '.')*;
TECH_IDENTIFIER: CAPITAL_LETTER+ | (CAPITAL_LETTER_OR_UNDERSCORE)+;
URL_STRING: ('a'..'z'|'A'..'Z'|'0'..'9'|'/'|':'|'-'|'#'|'?'|'.')+  ;

fragment GEEK_CHAR: '(' | ')' | '[' | ']' | '{' | '}' | '.' | ';' | ':' | '+' | '-' | '/' | '*' | '=' | '?' | '\\' | '\''  | '"' | '<' | '>' | '|';




EXTERNAL_CODE_DELIMITTER : '\'\'\'';
EXTERNAL_CODE : EXTERNAL_CODE_DELIMITTER (DIGIT | LETTER | GEEK_CHAR | WHITESPACE_CHAR)* EXTERNAL_CODE_DELIMITTER;
