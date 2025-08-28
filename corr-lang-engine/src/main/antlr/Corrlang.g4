grammar Corrlang;

file : definition*;

definition: endpoint | correspondence | view;

endpoint: 'endpoint' endpointName ':' endpointType '{' endpointSpec* '}' ;

correspondence : 'correspondence' correspondenceName '(' memberRef (',' memberRef)+ ')' '{' correspondenceSpec* '}';

view : 'view' viewName '(' correspondenceRef ')' ':' endpointType '{' endpointSpec* '}' ;

viewName: IDENTIFIER;

correspondenceRef: IDENTIFIER;

correspondenceSpec : property | correspondenceDirective;

correspondenceDirective : ('identify' | 'id') '(' elementRef (',' elementRef)+ ')' aliasDef? subSpec? matchRule? ';'? # idDirective
                        | ('relate' | 'rel') '(' elementRef (',' elementRef)+ ')' relName? subSpec? matchRule? ';'? # relateDirective
                        | ('synchronize' | 'sync')  '(' elementRef (syncDirection elementRef)+ ')' relName? subSpec? matchRule? ';'? #syncDirective;


syncDirection : ',' #symmetricSync
              | '~>' # asymmetricSync;

relName: 'via' elementRef;

matchRule : 'when' '[' matchDisjunction ']';

matchDisjunction : matchConjunction ('or' matchConjunction)*;

matchConjunction : matchExpression ('and' matchExpression)*;

matchExpression : elementRef* '~~' elementRef # matchedElements
                | elementRef* '==' elementExpression # equalElements;

elementExpression : elementExpression '+' elementExpression # sumExpression
                  | elementExpression '*' elementExpression # productExpression
                  | elementExpression '-' elementExpression # differenceExpression
                  | functionName '(' elementExpression ')' # functionCallExpression
                  | value # literalExpression
                  | elementRef # elemRefExpression;

functionName : IDENTIFIER;

subSpec: 'with' '{' correspondenceSpec* '}';

aliasDef: 'as' elementRef;

elementRef : path ('.' path)*;

path : IDENTIFIER #idPath
     | STRING #stringPath
     | URL #urlPath
     | '*' #wildcardPath;

memberRef: IDENTIFIER;

correspondenceName: IDENTIFIER;

endpointSpec: property | endpointDirective;

endpointDirective : 'file' '(' fileLocation ')' ';'?  # fileLocationDirective
                  | 'url' '(' urlTarget ')' ';'?  # urlDirective
                  | 'tech' '(' techSpaceName ')' ';'?  # techSpaceDirective
                  | 'schema' '(' schemaSpec ')' ';'?  # schemaDirective
                  | 'hide' '(' elementRef ')' ';'? #hideDirective;


techSpaceName: IDENTIFIER;

schemaSpec : schemaLocation (',' schemaTech)?;

schemaLocation: 'file' '(' fileLocation ')' | 'url' '(' urlTarget ')' ;

schemaTech: techSpaceName;

urlTarget: URL;

fileLocation: STRING;

property: propertyName ':' value ';';

value: STRING # stringValue
     | INTEGER #integerValue
     | DECIMAL #decimalValue
     | BOOLEAN  # boolValue
     | listValueDefinition # listValue
     | objectValueDefinition # objValue;

objectValueDefinition: '{' objKVPair (',' objKVPair )* '}';

objKVPair: STRING ':' value;

listValueDefinition : '[' value (',' value)* ']';

propertyName: IDENTIFIER | STRING;

endpointType: 'DATABASE' | 'SERVER' | 'SOURCE' | 'SINK';
endpointName: IDENTIFIER;





fragment DIGIT : '0' .. '9';
fragment LETTER : [a-zA-Z];
fragment IDENTIFIER_CHAR : LETTER | '-' | '_';
fragment WHITESPACE_CHAR: ' ' | '\t' | '\n' | '\r';
fragment TRUE_LITERAL : 'true';
fragment FALSE_LITERAL : 'false';
fragment URL_SCHEME : [^:/?#>]+ ':';
BOOLEAN : TRUE_LITERAL | FALSE_LITERAL;
IDENTIFIER : LETTER IDENTIFIER_CHAR*;
INTEGER : '-'? DIGIT+;
DECIMAL : INTEGER '.' DIGIT+;
STRING: '"' .*? '"';
URL: '<' .*? '>'; //('//' [^/?#]*? )? [^?#]*? ('?'[^#]*)? ('#'.*?)? '>';
WHITESPACE : WHITESPACE_CHAR+ -> skip;
