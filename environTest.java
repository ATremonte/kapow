
public class environTest
{
	
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
	
	private void print(String envName, lexeme tree)
	{
		System.out.println("\nThe "+ envName +" is: (pre-order)");
		printPreOrder(tree);
	}
	
	
	public static void main(String[] args) throws Exception
	{
		environTest test = new environTest();
		environment env = new environment();
		System.out.println("Creating a new environment");
		lexeme newEnv = env.create();
		test.print("environment", newEnv);
		System.out.println("Adding variable x with value 3"); 
		env.insert(new lexeme(types.ID, "x"), new lexeme(types.INTEGER, 3), newEnv);
		test.print("environment", newEnv);
		System.out.println("Extending the environment with y:4 and z:\"hello\"");
		test.print("local environment", env.extend(newEnv.left, newEnv.right.left, newEnv));
		test.print("environment", newEnv);
		
		
	}
}
