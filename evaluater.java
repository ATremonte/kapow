/*
 *	evaluater.java
 * 
 * 	Created by: Adam Tremonte
 * 
 * 	This is where the fun happens. This is the last step of the language and where all the parse trees built in the parser are evaluated.
 * 	The structure of this file is very similar to the parser. You will find an eval function for any functions that build parse trees in the parser.
 * 	Fully documenting this file is on the to-do list. Most of the functions are pretty straight forward with knowledge of how the parse trees are created
 * 	and by looking at the function names. For example: "evalPlus" evaluates the plus operation.
 */


import java.util.ArrayList;

public class evaluater
{
	private environment envObject = new environment();
	
	public lexeme evalPlus(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
			return new lexeme(types.INTEGER, left.ival + right.ival);
		else if (left.type == types.INTEGER && right.type == types.REAL)
			return new lexeme(types.REAL, left.ival + right.rval);
		else if (left.type == types.REAL && right.type == types.INTEGER)
			return new lexeme(types.REAL, left.rval + right.ival);
		else
			return new lexeme(types.REAL, left.rval + right.rval);
	}
	
	public lexeme evalMinus(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
			return new lexeme(types.INTEGER, left.ival - right.ival);
		else if (left.type == types.INTEGER && right.type == types.REAL)
			return new lexeme(types.REAL, left.ival - right.rval);
		else if (left.type == types.REAL && right.type == types.INTEGER)
			return new lexeme(types.REAL, left.rval - right.ival);
		else
			return new lexeme(types.REAL, left.rval - right.rval);
	}
	
	public lexeme evalTimes(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
			return new lexeme(types.INTEGER, left.ival * right.ival);
		else if (left.type == types.INTEGER && right.type == types.REAL)
			return new lexeme(types.REAL, left.ival * right.rval);
		else if (left.type == types.REAL && right.type == types.INTEGER)
			return new lexeme(types.REAL, left.rval * right.ival);
		else
			return new lexeme(types.REAL, left.rval * right.rval);
	}
	
	public lexeme evalDivides(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
			return new lexeme(types.INTEGER, left.ival / right.ival);
		else if (left.type == types.INTEGER && right.type == types.REAL)
			return new lexeme(types.REAL, left.ival / right.rval);
		else if (left.type == types.REAL && right.type == types.INTEGER)
			return new lexeme(types.REAL, left.rval / right.ival);
		else
			return new lexeme(types.REAL, left.rval / right.rval);
	}
	
	public lexeme evalMod(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
			return new lexeme(types.INTEGER, left.ival % right.ival);
		else if (left.type == types.INTEGER && right.type == types.REAL)
			return new lexeme(types.REAL, left.ival % right.rval);
		else if (left.type == types.REAL && right.type == types.INTEGER)
			return new lexeme(types.REAL, left.rval % right.ival);
		else
			return new lexeme(types.REAL, left.rval % right.rval);
	}
	
	public lexeme evalExp(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
			return new lexeme(types.REAL, Math.pow(left.ival, right.ival));
		else if (left.type == types.INTEGER && right.type == types.REAL)
			return new lexeme(types.REAL, Math.pow(left.ival, right.ival));
		else if (left.type == types.REAL && right.type == types.INTEGER)
			return new lexeme(types.REAL, Math.pow(left.ival, right.ival));
		else
			return new lexeme(types.REAL, Math.pow(left.ival, right.ival));
	}
	
	public lexeme evalIsEqual(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
		{
			if (left.ival == right.ival)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else if (left.type == types.INTEGER && right.type == types.REAL)
		{
			if (left.ival == right.rval)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else if (left.type == types.REAL && right.type == types.INTEGER)
		{
			if (left.rval == right.ival)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else if (left.type == types.REAL && right.type == types.REAL)
		{
			if (left.rval == right.rval)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else if (left.type == types.STRING && right.type == types.STRING)
		{
			if (left.sval.equals(right.sval))
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else
		{
			if (left.printStr.equals(right.printStr))
			{
				return new lexeme(types.BOOLEAN, true);
			}
			else
				return new lexeme(types.BOOLEAN, false);
		}
	}
	
	public lexeme evalNot(lexeme t, lexeme env) throws Exception
	{
		lexeme expr = eval(t.right, env);
		if (expr.bval == true)
			return new lexeme(types.BOOLEAN, false);
		else
			return new lexeme(types.BOOLEAN, true);
	}
	
	public lexeme evalGreaterThan(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
		{
			if (left.ival > right.ival)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else if (left.type == types.INTEGER && right.type == types.REAL)
		{
			if (left.ival > right.rval)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else if (left.type == types.REAL && right.type == types.INTEGER)
		{
			if (left.rval > right.ival)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else
		{
			if (left.rval > right.rval)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
	}
	
	public lexeme evalLessThan(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.type == types.INTEGER && right.type == types.INTEGER)
		{
			if (left.ival < right.ival)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else if (left.type == types.INTEGER && right.type == types.REAL)
		{
			if (left.ival < right.rval)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else if (left.type == types.REAL && right.type == types.INTEGER)
		{
			if (left.rval < right.ival)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
		else
		{
			if (left.rval < right.rval)
				return new lexeme(types.BOOLEAN, true);
			else
				return new lexeme(types.BOOLEAN, false);
		}
	}
	
	public lexeme evalAnd(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if (left.bval && right.bval)
			return new lexeme(types.BOOLEAN, true);
		else
			return new lexeme(types.BOOLEAN, false);
	}
	
	public lexeme evalOr(lexeme t, lexeme env) throws Exception
	{
		lexeme left = eval(t.left, env);
		lexeme right = eval(t.right, env);
		if(left.bval || right.bval)
			return new lexeme(types.BOOLEAN, true);
		else
			return new lexeme(types.BOOLEAN, false);
	}
	
	
	public lexeme evalVarDef(lexeme t, lexeme env) throws Exception
	{
		if (t.type == types.INTEGER)
		{
			envObject.insert(t.left, new lexeme(types.INTEGER, 0), env);
		}
		else if (t.type == types.REAL)
		{
			envObject.insert(t.left, new lexeme(types.REAL, 0.0), env);
		}
		else if (t.type == types.STRING)
		{
			envObject.insert(t.left, new lexeme(types.STRING, ""), env);
		}
		else
		{
			lexeme value = eval(t.right.right, env);
			return envObject.insert(t.left, value, env);
		}
		
		if (t.right.type == types.ASSIGN)
		{
			envObject.update(t.left, eval(t.right, env), env);
		}
		return t.left;
	}
	
	public lexeme evalFuncDef(lexeme t, lexeme env) throws Exception
	{
		lexeme closure = envObject.cons(types.CLOSURE, env, t);
		envObject.insert(t.left, closure, env);
		return closure;
	}
	
	public lexeme evalLambda(lexeme t, lexeme env) throws Exception
	{
		lexeme closure = envObject.cons(types.LAMBDA, env, t);
		return closure;
	}
	
	private lexeme getClosureParams(lexeme params)
	{
		if (params.type == types.COMMA)
		{
			lexeme node = new lexeme(types.JOIN);
			node.left = params.left;
			node.right = getClosureParams(params.right);
			return node;
		}
		else
		{
			lexeme node = new lexeme(types.JOIN);
			node.left = params;
			return node;
		}
	}
	
	private lexeme evalEArgs(lexeme args, lexeme env) throws Exception
	{
		if (args.type == types.COMMA)
		{
			lexeme node = new lexeme(types.JOIN);
			node.left = eval(args.left, env);
			node.right = evalEArgs(args.right, env);
			return node;
		}
		else
		{
			lexeme node = new lexeme(types.JOIN);
			node.left = eval(args, env);
			return node;
		}
	}
	
	
	public lexeme evalFuncCall(lexeme t, lexeme env) throws Exception
	{
		lexeme closure = envObject.lookup(t, env);
		lexeme args = t.right.right;
		lexeme params = getClosureParams(closure.right.right);
		
		lexeme body;
		if (closure.right.left.type == types.ID)
			body = closure.right.left.left;
		else
			body = closure.right.left;
		
		lexeme senv = closure.left;
		lexeme eargs = evalEArgs(args, env);
		lexeme xenv = envObject.extend(params, eargs, senv);
		
		// Insert a variable that points to xenv
		envObject.insert(new lexeme(types.ID, "this"), xenv, xenv);
		
		return eval(body, xenv);
	}
	
	public lexeme evalAssign(lexeme t, lexeme env) throws Exception
	{
		lexeme value = eval(t.right, env);
		lexeme obj = new lexeme(types.NULL);
		lexeme current = t.left;
		
		if (t.left.left != null && t.left.type != types.DOT)
		{
			lexeme temp = envObject.lookup(t.left, env);
			ArrayList<lexeme> temparr = temp.arr;
			temparr.set(t.left.left.ival, value);
			temp.arr = temparr;
			envObject.update(t.left, temp, env);
		}
		
		if (current.type == types.DOT)
		{
			obj = eval(current.left, env);
			current = current.right;
			if (current.type != types.DOT)
				envObject.update(current, value, obj);
		}
		else  if (t.left.left == null)
		{
			envObject.update(t.left, value, env);
		}
			
		while (current.type == types.DOT)
		{
			obj = eval(current.left, obj);
			current = current.right;
			if (current.type != types.DOT)
				envObject.update(current, value, obj);
		}
		
		return value;
			
	}
	
	public lexeme evalOptList(lexeme t, lexeme env)
	{
		return t;
	}
	
	public lexeme evalStatementList(lexeme t, lexeme env) throws Exception
	{
		lexeme result = new lexeme(types.EMPTY);
		while(t != null)
		{
			result = eval(t.left, env);
			t = t.right;
		}
		return result;
	}
	
	public lexeme evalBlock(lexeme t, lexeme env) throws Exception
	{
		if (t.right.type != types.EMPTY)
			return evalStatementList(t.right, env);
		else
			return new lexeme(types.EMPTY);	
	}
	
	public lexeme evalPrint(lexeme t, lexeme env) throws Exception
	{
		System.out.print(eval(t, env));
		return null;
	}
	
	public lexeme evalPrintln(lexeme t, lexeme env) throws Exception
	{
		System.out.print(eval(t, env));
		System.out.println();
		return null;
	}
	
	public lexeme evalHash(lexeme t, lexeme env) throws Exception
	{
		String str = eval(t.right, env).printStr;
		return new lexeme(types.INTEGER, str.hashCode());
	}
	
	public lexeme evalToString(lexeme t, lexeme env) throws Exception
	{
		lexeme lex = eval(t.right, env);
		return new lexeme(types.STRING, lex.printStr);
	}
	
	public lexeme evalArray(lexeme t, lexeme env) throws Exception
	{
		if (t.left != null) 
		{
			return envObject.lookup(t, env).arr.get(t.left.ival);
		}
		else
		{
			ArrayList<lexeme> al= new ArrayList<lexeme>();
			while(t.right.type == types.COMMA)
			{
				t = t.right;
				al.add(eval(t.left, env));
			}
			al.add(eval(t.right, env));
			return new lexeme(al);
		}
	}
	
	public lexeme evalIf(lexeme t, lexeme env) throws Exception
	{
		if (eval(t.left, env).bval)
			return eval(t.right, env);
		else if (t.right.left.type == types.ELSE)
			return eval(t.right.left.right, env);
		return null;
	}
	
	public lexeme evalWhile(lexeme t, lexeme env) throws Exception
	{
		boolean iter = eval(t.left, env).bval;
		while (iter)
		{
			eval(t.right, env);
			iter = eval(t.left, env).bval;
		}
		return t;
	}
	
	public lexeme evalDot(lexeme t, lexeme env) throws Exception
	{
		lexeme object = eval(t.left, env);
		return eval(t.right, object);
	}
	
	public lexeme evalID(lexeme t, lexeme env) throws Exception
	{
		if (t.right != null && t.right.type == types.OPAREN)
		{
			return evalFuncCall(t, env);
		}
		else if (t.right != null && t.right.type == types.ID)
		{
			lexeme obj = envObject.lookup(t, env);
			lexeme newE = envObject.extend(env.left, env.right.left, obj);
			return evalFuncCall(t.right, newE);
		}
		else if (t.left != null && t.left.type == types.INTEGER)
		{
			return evalArray(t, env);
		}
		else
		{
			return envObject.lookup(t, env);
		}
	}
	
	public lexeme eval(lexeme tree, lexeme env) throws Exception
	{
		switch(tree.type)
		{
			// Self evaluating
			
			case "INTEGER":
				return tree;
			case "REAL":
				return tree;
			case "STRING":
				return tree;
			case "BOOLEAN":
				return tree;
			case "NULL":
				return tree;
			case "EMPTY":
				return tree;
				
			// Find value of variables in the environment
			case "ID":
				return evalID(tree, env);
				
			case "DOT":
				return evalDot(tree, env);
				
			// Parenthesized expressions
			case "OPAREN":
				return eval(tree.right, env);
				
			// Operators
			
			case "PLUS":
				return evalPlus(tree, env);
			
			case "MINUS":
				return evalMinus(tree, env);
				
			case "TIMES":
				return evalTimes(tree, env);
				
			case "DIVIDES":
				return evalDivides(tree, env);
				
			case "MOD":
				return evalMod(tree, env);
				
			case "EXP":
				return evalExp(tree, env);
			
			case "GREATERTHAN":
				return evalGreaterThan(tree, env);
				
			case "LESSTHAN":
				return evalLessThan(tree, env);
			
			case "ISEQUAL":
				return evalIsEqual(tree, env);
				
			case "NOT":
				return evalNot(tree, env);
				
			case "AND":
				return evalAnd(tree, env);
				
			case "OR":
				return evalOr(tree, env);
				
			case "ASSIGN":
				return evalAssign(tree, env);
			
			case "OBRACE":
				return evalBlock(tree, env);
			
			case "NODE":
				System.out.println("FOUND A NODE");
			
			case "DEF":
				return evalFuncDef(tree, env);
				
			case "LAMBDA":
				return evalLambda(tree, env);
				
			case "IF":
				return evalIf(tree, env);
				
			case "WHILE":
				return evalWhile(tree, env);
			
			case "PRINT":
				return evalPrint(tree.right, env);
				
			case "PRINTLN":
				return evalPrintln(tree.right, env);
			
			case "ARRAY":
				return evalArray(tree, env);
				
			case "HASH":
				return evalHash(tree, env);
				
			case "TOSTRING":
				return evalToString(tree, env);
				
			case "VAR":
				return evalVarDef(tree, env);
			
			default:
				throw new Exception("Unhandled lexeme in eval: "+ tree.type);
		}
	}
}
