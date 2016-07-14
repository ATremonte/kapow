/*
 *	evaluate.java
 * 
 * 	Created by: Adam Tremonte
 * 
 * 	This class is what runs everything, basically the "main" for the language.
 */
public class evaluate
{
	
	// This is here for debugging
	private void printPreOrder(lexeme tree)
	{
		if (tree == null)
		{
			System.out.println("\tnull");
		}
		else
		{
			System.out.println("\t" + tree);
			System.out.print(tree + ".left:");
			printPreOrder(tree.left);
			System.out.print(tree + ".right:");
			printPreOrder(tree.right);
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		if (args.length > 0)
		{
			// Create all the different parts
			environment envObject = new environment();
			lexeme env = envObject.create();
			
			evaluate printer = new evaluate();
			evaluater evaltr = new evaluater();
			
			parser prs = new parser(args[0]);
			
			try
			{
				lexeme parseTree = prs.parse();
				if (parseTree.type.equals("ENDofINPUT"))
				{
					return;
				}
				while (parseTree != null)
				{
					evaltr.eval(parseTree.left, env);
					parseTree = parseTree.right;
				}
			}
			catch (Exception err)
			{
				// Display error?
			}	
		}
		else
			System.out.println("Not enough arguments");
	}
}
