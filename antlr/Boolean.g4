grammar Boolean;

init    : andexpr ( OR andexpr )* ;
andexpr : notexpr ( AND notexpr )* ;
notexpr : ( NOT )? atom ;
atom    : TERM | '(' init ')' ;

AND     : 'AND' ;
OR      : 'OR' ;
NOT     : 'NOT' ;
TERM    : [a-zA-Z0-9] [a-zA-Z0-9\-]* ;
WS      : [ \r\t\n]+ -> skip ;
