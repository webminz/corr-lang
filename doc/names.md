# Names (De-)Serialisation

## Hierarchy

* Name
** SingleName
*** Identifier
**** SimpleIdentifier
**** URI
**** CustomIdentifier
*** Variable
*** Value
**** StringValue
**** IntValue
**** FloatValue
**** BoolValue
**** CustomValue
** ComposedName
*** PrefixedName
*** IndexedName
*** CombinedName
**** UnaryCombinator
**** BinaryCombinator
**** MultiaryCombinator
** NameSet

## Format

### SimpleIdentifier

MAGIC_BYTE | STRING_BYTES

### URI

MAGIC_BYTE | STRING_BYTES

### Custom Identifier

MAGIC_BYTE | JAVA_CLASS_NAME | STRING_BYTES

### Variable

MAGIC_BYTE | STRING_BYTES

### String Value

MAGIC_BYTE | STRING_BYTES

### Int Value

MAGIC_BYTE | BIG_INTEGER_BYTE_SEQUENCE

### Float Value

MAGIC_BYTE | IEEE_754_64

### Bool Value

MAGIC_BYTE 

### Custom Value

MAGIC_BYTE | JAVA_CLASS_NAME | STRING_BYTES

### Prefixed Name

MAGIC_BYTE | LENGTH_OF_FIRST_SERIALISATION | FIRST_SERIALISATION | SECOND_SERIALISATION

### Indexed Name

MAGIC_BYTE | INTEGER_64 | SERIALISATION 

### Unary Combinator

MAGIC_BYTE | COMBINATOR_BYTE | SERIALISATION

### Binary Combinator

MAGIC_BYTE | COMBINATOR_BYTE | LENGTH_OF_FIRST_SERIALISATION | FIRST_SERIALISATION | SECOND_SERIALISATION

### Mulitary Combinator

MAGIC_BYTE | COMBINATOR_BYTE | NO_OF_ELEMENTS | (LENGTH_OF_SERIALISATION | SERIALISATION)*

## Name Set

MAGIC_BYTE | NO_OF_ELEMENTS | (LENGTH_OF_SERIALISATION | SERIALISATION)*

