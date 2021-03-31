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
endpointPlaftorm: 'ECORE' | 'XML' | 'GRAPH_QL' | 'REST_OPENAPI' | 'PROTOCOL_BUFFERS' | 'TYPESCRIPT_JSON' | 'SQL' | 'JAVA';
endpointURL: URL_STRING;
schemaURL: URL_STRING;

corrspec: 'correspondence' WHITESPACE corrSpecName WHITESPACE
            ('instantiates' WHITESPACE corrSpecType WHITESPACE)?
            '(' WHITESPACE? corrSpecEndpointRef WHITESPACE? (',' WHITESPACE? corrSpecEndpointRef WHITESPACE?)+ ')' WHITESPACE
            '{' (WHITESPACE commonality)* WHITESPACE? '}' WHITESPACE?;

corrSpecEndpointRef: IDENTIFIER;
corrSpecName: IDENTIFIER;
corrSpecType: IDENTIFIER;

commonality: commonalityType WHITESPACE?
            '(' WHITESPACE? elmentRef WHITESPACE? (',' WHITESPACE? elmentRef WHITESPACE?)+ ')'
            (WHITESPACE 'as' WHITESPACE commonalityName constraints?)?
            (subCummonalities)?
            (WHITESPACE 'when' WHITESPACE '(' WHITESPACE? keyExpression WHITESPACE? ')' )?
            (WHITESPACE 'check' WHITESPACE (anonymousConssistencyRule || consistencyRuleRef))?
            ';';

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
ruleLanguage: 'OCL' | 'EVL';
ruleBodyContent: EXTERNAL_CODE;

keyExpression: keyAlternative (WHITESPACE '||' WHITESPACE keyAlternative)*;
keyAlternative: keyLiteral (WHITESPACE '&&' WHITESPACE keyLiteral)*;
keyLiteral: keyIdentity | keyRelation;
keyIdentity: keyIdArgument WHITESPACE  '==' WHITESPACE keyIdArgument ('==' WHITESPACE keyIdArgument)*;
keyIdArgument: (elmentRef | constant) (WHITESPACE keyIdArgumentSuffix)*;
keyIdArgumentSuffix:  '++' WHITESPACE (elmentRef | constant);
keyRelation:  elmentRef WHITESPACE keyRelDirection  WHITESPACE (elmentRef | keyRelation);
keyRelDirection: '<~>' | '~~>' | '<~~';


goal: 'goal' WHITESPACE goalName WHITESPACE '{' WHITESPACE
        'correspondence' WHITESPACE goalCorrespondence WHITESPACE
       'action' WHITESPACE goalActionType WHITESPACE
        ('technology' WHITESPACE goalTechSpace WHITESPACE)?
        'target' WHITESPACE goalTargetType WHITESPACE
        ('query' WHITESPACE goalQuery WHITESPACE)?
        ('params' WHITESPACE goalParamMap)?
        '}' WHITESPACE?;

goalCorrespondence: IDENTIFIER;
        
goalParamMap: '{' WHITESPACE (goalParamKvPair WHITESPACE)* '}';
goalParamKvPair: goalParamKvPairKey WHITESPACE ':=' WHITESPACE goalParamKvPairValue ;

goalParamKvPairKey: STRING;
goalParamKvPairValue: constant; 
goalQuery: EXTERNAL_CODE;
goalName: IDENTIFIER;
goalTechSpace: 'ECORE' | 'XML' | 'GRAPH_QL' | 'REST_OPENAPI' | 'PROTOCOL_BUFFERS' | 'TYPESCRIPT_JSON' | 'SQL' | 'JAVA';
goalActionType: 'SCHEMA' | 'FEDERATION' | 'TRANSFORMATION' | 'MATCH' | 'VERIFY' | 'SYNCHRONIZE';
goalTargetType: serverTarget | codegenTarget | fileTarget | batchTarget;

batchTarget: 'batch';

fileTarget: 'file' WHITESPACE '{' WHITESPACE 'at' WHITESPACE fileCreationTarget WHITESPACE ('overwrite' WHITESPACE fileCreationOverwrite WHITESPACE)? '}';

fileCreationOverwrite: BOOL;
fileCreationTarget: URL_STRING;

codegenTarget: 'codegen' WHITESPACE '{' WHITESPACE 
        'technology' WHITESPACE codegenTchnology WHITESPACE
        'at' WHITESPACE codegenTargetLocation WHITESPACE 
        'artefactId' WHITESPACE codegenArtefact WHITESPACE
        'groupId' WHITESPACE codegenGroup WHITESPACE
        ('version' WHITESPACE codegenVersion WHITESPACE)?
        '}';

codegenVersion: STRING;
codegenGroup: STRING;
codegenArtefact: STRING;
codegenTargetLocation: URL_STRING;
codegenTchnology: 'JAVA_SPRING';

serverTarget: 'server' WHITESPACE '{' WHITESPACE 'contextPath' WHITESPACE serverTargetContextPath WHITESPACE 'port' WHITESPACE serverTargetPort WHITESPACE '}';

serverTargetPort: INTEGER;
serverTargetContextPath: STRING;




constant: stringConstant | intConstant | floatConstant | boolConstant;

stringConstant: STRING;
intConstant: INTEGER;
floatConstant: FLOAT;
boolConstant: BOOL;

elmentRef: QUALIFIED_ID;


INTEGER: '-' DIGIT+ | DIGIT+;
FLOAT: INTEGER ('.' DIGIT+);
BOOL: 'true' | 'false';
STRING: STRING_CONSTANT_SEPARATOR (DIGIT | LETTER | GEEK_CHAR | WHITESPACE_CHAR)* STRING_CONSTANT_SEPARATOR;
STRING_CONSTANT_SEPARATOR: '"';

IDENTIFIER: LETTER_OR_UNDERSCORE LETTER_OR_DIGIT*;
QUALIFIED_ID: LETTER_OR_UNDERSCORE (LETTER_OR_DIGIT | '.')*;

fragment LETTER_OR_UNDERSCORE: LETTER | '_';
fragment LETTER_OR_DIGIT: LETTER | DIGIT;

URL_STRING: ('a'..'z'|'A'..'Z'|'0'..'9'|'/'|':'|'-'|'#'|'?'|'.')+  ;
fragment LETTER: 'a' .. 'z' | 'A' .. 'Z';
fragment DIGIT: '0' .. '9';
fragment GEEK_CHAR: '(' | ')' | '[' | ']' | '{' | '}' | '.' | ';' | ':' | '+' | '-' | '/' | '*' | '=' | '?' | '\\' | '\''  | '"' | '<' | '>' | '|';

 EXTERNAL_CODE : EXTERNAL_CODE_DELIMITTER (DIGIT | LETTER | GEEK_CHAR | WHITESPACE_CHAR)* EXTERNAL_CODE_DELIMITTER;
 EXTERNAL_CODE_DELIMITTER : '\'\'\'';

fragment SPACE: ' ';
fragment TAB: '\t';
fragment LINE_TABULATION: '\u000B';
fragment NEWLINE: '\n';
fragment CARRIAGE_RETURN: '\r';

fragment WHITESPACE_CHAR: SPACE | TAB | LINE_TABULATION | NEWLINE | CARRIAGE_RETURN;
WHITESPACE: WHITESPACE_CHAR+;
