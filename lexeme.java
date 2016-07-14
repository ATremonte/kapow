/*
 *	lexeme.java
 *	
 * 	Created by: Adam Tremonte
 *
 *	This is the class to rule all classes. Everything in this language is a lexeme, from the variables to the environment.
 *	This is not the most efficient way to go about doing things, but it simplifies things immensely. By making everything lexemes
 *	you are able to treat functions as first-class objects, pass environments around, and also do object oriented programming.
 */

import java.util.ArrayList;

class lexeme
{
	// A variable for every possible type.
	String type;
	String printStr = "";
	int ival;
	String sval;
	double rval;
	boolean bval;
	ArrayList<lexeme> arr;
	
	// Each lexeme has a left and right (like a node in a tree) so that we can build parse trees with them :)
	lexeme left;
	lexeme right;
	
	// Different ways to initialize lexemes based off of type.
	lexeme(String str)
	{
		type = str;
		printStr = type;
	}
	lexeme(String str, int num)
	{
		type = str;
		ival = num;
		printStr = Integer.toString(num);
	}
	lexeme(String str, double num)
	{
		type = str;
		rval = num;
		printStr = Double.toString(num);
	}
	lexeme(String str1, String str2)
	{
		type = str1;
		sval = str2;
		printStr = str2;
	}	
	lexeme(String str, boolean val)
	{
		type = str;
		bval = val;
		printStr = Boolean.toString(val);
	}
	lexeme(ArrayList<lexeme> newArr) 
	{
		type = types.ARRAY;
		arr = newArr;
		printStr = newArr.toString();
	}
	
	// This is what will be printed out if you try to print this lexeme.
	public String scannerString()
	{
		if (sval != null)
			return type + " \"" + printStr + "\"";
		else 
			return type + " " + printStr;
	}
	
	public String toString()
	{
		return printStr; 
	}
}
