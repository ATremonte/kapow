/*
 *	scanner.java
 * 
 * 	Created by: Adam Tremonte
 * 
 * 	This is used to test the lexer class by itself.
 */
class scanner
{
	public scanner(String filename)
	{
		lexeme token;
		lexer i = new lexer(filename);
		token = i.lex();
		while (token.type != types.ENDofINPUT)
		{
			System.out.println(token.scannerString());
			token = i.lex();
		}
		System.out.println(token);
		i.closeReader();
	}
	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			scanner scan = new scanner(args[0]);
		}
		else
			System.out.println("Not enough arguments");
	}
	
}
