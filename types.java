/*
 *	types.java
 * 
 *	Created by: Adam Tremonte
 *	 
 *	This is where all the types are stored! When lexemes are created they are assigned one of these types.
 * 	Since they are set equal to these strings, we can make object equality comparisons instead of string comparisons. (== versus .equals())
 * 	If we were doing string comparisons it would hamper the language's run time significantly.
 */

public class types
{
	public static final String OPAREN = "OPAREN";
	public static final String CPAREN = "CPAREN";
	public static final String COMMA = "COMMA";
	public static final String PLUS = "PLUS";
	public static final String TIMES = "TIMES";
	public static final String MINUS = "MINUS";
	public static final String DIVIDES = "DIVIDES";
	public static final String EXP = "EXP";
	public static final String MOD = "MOD";
	public static final String NOT = "NOT";
	public static final String LESSTHAN = "LESSTHAN";
	public static final String GREATERTHAN = "GREATERTHAN";
	public static final String ASSIGN = "ASSIGN";
	public static final String SEMICOLON = "SEMICOLON";
	public static final String ID = "ID";
	public static final String ENDofINPUT = "ENDofINPUT";
	public static final String WHILE = "WHILE";
	public static final String IF = "IF";
	public static final String ELSE = "ELSE";
	public static final String INTEGER = "INTEGER";
	public static final String STRING = "STRING";
	public static final String REAL = "REAL";
	public static final String VAR = "VAR";
	public static final String DEF = "DEF";
	public static final String LAMBDA = "LAMBDA";
	public static final String RETURN = "RETURN";
	public static final String OBRACE = "OBRACE";
	public static final String CBRACE = "CBRACE";
	public static final String DOT = "DOT";
	public static final String ISEQUAL = "ISEQUAL";
	public static final String UNKNOWN = "UNKNOWN";
	public static final String ERRORQUOTE = "ERROR infinite string";
	public static final String EMPTY = "EMPTY";
	public static final String ENV = "ENV";
	public static final String CLOSURE = "CLOSURE";
	public static final String VALUES = "VALUES";
	public static final String JOIN = "JOIN";
	public static final String PRINT = "PRINT";
	public static final String PRINTLN = "PRINTLN";
	public static final String NODE = "NODE";
	public static final String BOOLEAN = "BOOLEAN";
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String NULL = "NULL";
	public static final String ARRAY = "ARRAY";
	public static final String AOBRACE = "AOBRACE";
	public static final String ACBRACE = "ACBRACE";
	public static final String HASH = "HASH";
	public static final String TOSTRING = "TOSTRING";
}
