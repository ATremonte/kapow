/*
 *	recognizer.java
 * 
 * 	Created by: Adam Tremonte
 * 
 * 	This just tests the parser to see if a file is syntactically correct. That is all.
 */

class recognizer
{
	public static void main(String[] args) throws Exception
	{
		if (args.length > 0)
		{
			parser prs = new parser(args[0]);
			prs.testParse();
			//System.out.println(prs.testParse());
		}
		else
			System.out.println("Not enough arguments");
		
	}
}
