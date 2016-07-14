/*	
 *	lexer.java
 * 
 * 	Created by: Adam Tremonte
 * 
 * 	This class is used to read the input file and convert everything into lexemes and pass them to the parser.
 */

import java.io.File;
import java.io.FileInputStream;

class lexer
{
	// usedNext is being used as a hacky way to push back to the scanner. Making this better is on the to-do list.
	int usedNext = 0;
	char next = ' ';
	FileInputStream Input;
	
	// This is where the file is being opened.
	public lexer(String filename)
	{
		File file = new File(filename);
		if (!file.exists())
		{
			System.out.println(filename + " does not exist");
		}
		else
		{
			try 
			{
				Input = new FileInputStream(file);
				
			}
			catch (Exception e)
			{
				System.out.println(filename + " could not be opened");
			}
		}
	}
	
	// These are to handle the specific cases of types of lexemes
	lexeme lexNumber(char c, boolean neg)
	{
		try
		{
			String buffer;
			
			if (neg)
				buffer = "-";
			else
				buffer = "";
				
			while (Character.isDigit(c))
			{
				buffer += c;
				c = (char)Input.read();
			}
			if (c == '.') return lexDouble(buffer + c);
			next = c;
			usedNext = 0;
			return new lexeme(types.INTEGER, Integer.parseInt(buffer));
		}
		catch (Exception e)
		{
			return new lexeme(types.ENDofINPUT);
		}
		
	}
	lexeme lexDouble(String buffer)
	{
		try
		{
			char c = (char)Input.read();
			while (Character.isDigit(c))
			{
				buffer += c;
				c = (char)Input.read();
			}
			next = c;
			usedNext = 0;
			return new lexeme(types.REAL, Double.parseDouble(buffer));
		}
		catch (Exception e)
		{
			return new lexeme(types.ENDofINPUT);
		}
		
	}
	
	// Handles variables as well as built-ins and special forms.
	lexeme lexVariable(char c)
	{
		try
		{
			String buffer = "" + c;
			c = (char)Input.read();
			while(Character.isDigit(c) || Character.isLetter(c) || c == '_')
			{
				buffer += c;
				c = (char)Input.read();
			}
			next = c;
			usedNext = 0;
			
			// built-ins, special forms
			if (buffer.equals("lambda")) return new lexeme(types.LAMBDA);
			if (buffer.equals("def")) return new lexeme(types.DEF);
			if (buffer.equals("var")) return new lexeme(types.VAR);
			if (buffer.equals("bool")) return new lexeme(types.BOOLEAN);
			if (buffer.equals("true")) return new lexeme(types.BOOLEAN, true);
			if (buffer.equals("false")) return new lexeme(types.BOOLEAN, false);
			if (buffer.equals("while")) return new lexeme(types.WHILE);
			if (buffer.equals("if")) return new lexeme(types.IF);
			if (buffer.equals("else")) return new lexeme(types.ELSE);
			if (buffer.equals("null")) return new lexeme(types.NULL);
			if (buffer.equals("and")) return new lexeme(types.AND);
			if (buffer.equals("or")) return new lexeme(types.OR);
			if (buffer.equals("return")) return new lexeme(types.RETURN);
			if (buffer.equals("print")) return new lexeme(types.PRINT);
			if (buffer.equals("hash")) return new lexeme(types.HASH);
			if (buffer.equals("toString")) return new lexeme(types.TOSTRING);
			if (buffer.equals("println")) return new lexeme(types.PRINTLN);
			if (buffer.equals("array")) return new lexeme(types.ARRAY);
			
			return new lexeme(types.ID, buffer);
		}
		catch (Exception e)
		{
			return new lexeme(types.ENDofINPUT);
		}
		
	}
	lexeme lexString()
	{
		try
		{
			String buffer = "";
			int r = Input.read();
			char c = (char)r;
			while (c != '\"')
			{
				if (r == -1) return new lexeme(types.ERRORQUOTE);
				if (c == '\\') c = (char)Input.read();
				buffer += c;
				r = Input.read();
				c = (char)r;
			}
			return new lexeme(types.STRING, buffer);
			
		}
		catch (Exception e)
		{
			return new lexeme(types.ENDofINPUT);
		}
	}
	
	int checkForComment()
	{
		try
		{
			int r;
			char c = (char)Input.read();
			if (c == '/')
			{
				while((r = Input.read()) != -1)
				{
					if ((char)r == '\n')
						return 1;
				}
			}
			else if (c == '*')
			{
				while((r = Input.read()) != -1)
				{
					if ((char)r == '*')
						if ((char)Input.read() == '/')
							return 1;
				}
				
			}
			else
				return 0;
			return 1;
		}
		catch(Exception e)
		{
			return 0;
		}
		
	}
	
	// TODO: Make this better. Right now it is bad.
	int skipWhiteSpace()
	{
		int r;
		char c;
		if ((next != ' ') && (usedNext == 0)) return 0;
		try
		{
			while ((r = Input.read()) != -1)
			{
				c = (char)r;
				if ((c != ' ') && (c != '\n') && (c != '\t'))
				{
					next = (char)r;
					usedNext = 0;
					return 0;
				}						
			}
			return -1;
		}
		catch (Exception e)
		{
			return -1;
		}		
	}
	
	void closeReader()
	{
		try
		{
			Input.close();
		}
		catch (Exception e) {}
	}
	
	lexeme lex()
	{
		int r;
		try 
		{
			if (skipWhiteSpace() == -1) return new lexeme(types.ENDofINPUT);
			char c = next;
			usedNext = 1;
			switch(c)
			{
				// single character tokens
				case '!':
					return new lexeme(types.NOT);
				case '%':
					return new lexeme(types.MOD);
				case '.':
					return new lexeme(types.DOT);
				case '(':
					return new lexeme(types.OPAREN);
				case ')':
					return new lexeme(types.CPAREN);
				case '[':
					return new lexeme(types.AOBRACE);
				case ']':
					return new lexeme(types.ACBRACE);
				case ',':
					return new lexeme(types.COMMA);
				case '+':
					return new lexeme(types.PLUS);
				case '*':
					return new lexeme(types.TIMES);
				case '-':
					c = (char)Input.read();
					if (Character.isDigit(c))
						return lexNumber(c, true);
					else
					{
						next = c;
						usedNext = 0;
						return new lexeme(types.MINUS);
					}
				case '^':
					return new lexeme(types.EXP);
				case '<':
					return new lexeme(types.LESSTHAN);
				case '>':
					return new lexeme(types.GREATERTHAN);
				case ';':
					return new lexeme(types.SEMICOLON);
				case '{':
					return new lexeme(types.OBRACE);
				case '}':
					return new lexeme(types.CBRACE);
				case '/':
					if(checkForComment() == 0)
						return new lexeme(types.DIVIDES);
					else
						return lex();
				case '=':
					if ((char)Input.read() == '=')
						return new lexeme(types.ISEQUAL);
					else
						return new lexeme(types.ASSIGN);
				default:
					// multi-character tokens 
					// (numbers, variables, keywords, strings)
					
					if (Character.isDigit(c))
					{
						return lexNumber(c, false);
					}
					else if (Character.isLetter(c))
					{
						return lexVariable(c); // and keywords
					}
					else if (c == '\"')
					{
						return lexString();
					}
					else 
					{
						return new lexeme(types.UNKNOWN, c);
					}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
			return new lexeme(types.ENDofINPUT);
		}
	}
}
