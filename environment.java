/*
 *	environment.java
 *	
 * 	Created by: Adam Tremonte
 *	
 *	This class is used to create and manipulate environments in a similar fashion as in SICP.
 *	Since everything in this language is a lexeme object, so is the environment. The purpose of the environment
 *	is to know what is in scope, what can be garbage collected, and also to store all set values.
 */

public class environment
{
	// Creates a new lexeme which links two pre-existing ones together.
	public lexeme cons(String type, lexeme left, lexeme right)
	{
		lexeme newLexeme = new lexeme(type);			
		newLexeme.left = left;
		newLexeme.right = right;
		return newLexeme;
	}
	
	// In SICP fashion, car is left and cdr is right.
	private lexeme car(lexeme lex)
	{
		return lex.left;
	}
	
	private lexeme cdr(lexeme lex)
	{
		return lex.right;
	}
	
	// cadr is the combination of doing performing cdr and then car.
	private lexeme cadr(lexeme lex)
	{
		return car(cdr(lex));
	}
	
	private void setCar(lexeme lst, lexeme lex)
	{
		lst.left = lex;
	}
	
	private void setCdr(lexeme lst, lexeme lex)
	{
		lst.right = lex;
	}
	
	public lexeme create()
	{
		return cons(types.ENV, null, cons(types.VALUES, null, null));
	}
	
	// Checks to see if a lexeme is in an environment. 
	// If it is in the environment, the value is returned else an error is thrown.
	public lexeme lookup(lexeme variable, lexeme env) throws Exception
	{
		while (env != null)
		{
			lexeme vars = car(env);
			lexeme vals = cadr(env);
			while (vars != null)
			{
				if (variable.printStr.equals(car(vars).printStr))
					return car(vals);
				vars = cdr(vars);
				vals = cdr(vals);
			}
			env = cdr(cdr(env));
		}
		throw new Exception("\nVariable "+ variable +" is undefined");
	}
	
	public lexeme getClosureEnvironment(lexeme variable, lexeme env) throws Exception
	{
		while (env != null)
		{
			lexeme vars = car(env);
			lexeme vals = cadr(env);
			while (vars != null)
			{
				if (variable.printStr.equals(car(vars).printStr))
					return env;
				vars = cdr(vars);
				vals = cdr(vals);
			}
			env = cdr(cdr(env));
		}
		throw new Exception("\nVariable "+ variable +" is undefined");
	}
	
	// Updates a lexeme's set value in an environment.
	public lexeme update(lexeme variable, lexeme value, lexeme env) throws Exception
	{
		while (env != null)
		{
			lexeme vars = car(env);
			lexeme vals = cadr(env);
			while (vars != null)
			{
				if (variable.printStr.equals(car(vars).printStr))
				{
					setCar(vals, value);
					return car(vals);
				}
				vars = cdr(vars);
				vals = cdr(vals);
			}
			env = cdr(cdr(env));
		}
		throw new Exception("\nVariable "+ variable +" is undefined");
	}
	
	// Adds a new lexeme to an environment.
	public lexeme insert(lexeme variable, lexeme value, lexeme env)
	{
		setCar(env, cons(types.JOIN, variable, car(env)));
		setCar(cdr(env), cons(types.JOIN, value, cadr(env)));
		return value;
	}
	
	// Combines two environments.
	public lexeme extend(lexeme variables, lexeme values, lexeme env)
	{
		return cons(types.ENV, variables, cons(types.ENV, values, env));
	}
}
