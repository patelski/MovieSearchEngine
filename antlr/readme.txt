This generates a Boolean parser. This requires ANTLR 4. To install ANTLR 4 refer to the address below.
http://www.antlr.org/wiki/display/ANTLR4/Getting+Started+with+ANTLR+v4

File Boolean.g4 contains the grammar used to generate the Boolean parser. In order to generate the Boolean parser run the following commands:
	$ antlr4 Boolean.g4 
	$ javac Boolean*.java
To test the parser run the following command:
	$ grun Boolean init -gui
	(a AND b) OR (c AND d)
Hold ctrl and D or ctrl and Z to parse the input.
