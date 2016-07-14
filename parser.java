/*
 *	parser.java
 * 
 * 	Created by: Adam Tremonte
 * 
 * 	This class is used to simultaneously check for syntax errors and build parse trees from the lexemes passed through the lexer.
 * 	There is alot going on here, and that is because it has to check for every combination of things in the grammer.
 * 	The parse trees are built using the lexeme objects which have a left and right property.
 * 	They are used in the evaluater to determine what order to evaluate the lexemes.
 */
class parser
{
	lexeme CurrentLexeme;
	lexeme Pending;
	static lexer lexr;
	
	// The parser goes hand-in-hand with the lexer.
	parser(String filename)
	{
		lexr = new lexer(filename);
	}
	
	// Used for testing the parser individually;
	String testParse() throws Exception
	{
		Pending = lexr.lex();
		CurrentLexeme = Pending;
		advance();
		while (!check(types.ENDofINPUT))
		{
			statement();
		}
		return "legal";
	}
		
	// Base case for parsing before beginning parseIter
	lexeme parse() throws Exception
	{
		Pending = lexr.lex();
		CurrentLexeme = Pending;
		advance();
		if (!check(types.ENDofINPUT))
			return parseIter();
		else 
			return new lexeme(types.ENDofINPUT);
	}
	
	// Parses the rest of the file.
	lexeme parseIter() throws Exception
	{
		lexeme node = new lexeme(types.NODE);
		
		// Statements are the root of all programs, so we build a parse tree of statements which contains our entire program.
		node.left = statement();
		if (!check(types.ENDofINPUT))
		{
			node.right = parseIter();
		}
		return node;
	}
	
	boolean check(String type)
	{
		return CurrentLexeme.type == type;
	}
	
	// Proceeds to the next lexeme
	void advance() throws Exception
	{
		lexeme old = Pending;
		Pending = lexr.lex();
		CurrentLexeme = old;
	}
	
	// This is where syntax errors are thrown.
	lexeme match(String type) throws Exception
	{
		if (check(type))
		{
			lexeme temp = CurrentLexeme;
			advance();
			return temp;
		}
		else
			throw new Exception("\nillegal: Syntax Error. Expected type: "+ type + " got type: "+ CurrentLexeme.type);
	}
	
	// The rest of the functions are either building parse trees for the different cases in the grammer or checking
	// if another case is pending.
	
	lexeme operation() throws Exception
	{
		lexeme tree = operator();
		tree.left = primary();
		tree.right = primary();
		return tree;
	}
	
	lexeme list() throws Exception
	{
		lexeme tree = primary();
		if (check(types.COMMA))
		{
			lexeme temp;
			temp = match(types.COMMA);
			temp.left = tree;
			temp.right = list();
			tree = temp;
		}
		return tree;
	}
	
	boolean listPending()
	{
		return primaryPending();
	}
	
	lexeme optList() throws Exception
	{
		if (primaryPending())
			return list();
		else
			return new lexeme(types.EMPTY);
	}

	lexeme funcCall() throws Exception
	{
		lexeme tree;
		lexeme temp;
		tree = match(types.ID);
		if (check(types.OPAREN))
		{
			tree.right = match(types.OPAREN);
			tree.right.right = optList();
			match(types.CPAREN);
			return tree;
		}
		else
		{
			temp = new lexeme(types.DOT);
			temp.right = tree;
			return temp;
		}
	}
	
	lexeme funcCallList() throws Exception
	{
		lexeme tree = funcCall();
		
		if (check(types.DOT))
			match(types.DOT);
			tree.left = funcCallList();
	
		return tree;
		
	}
	
	boolean exprPending()
	{
		return primaryPending();
	}
	
	lexeme expression() throws Exception
	{
		lexeme tree = primary();
		if (opPending())
		{
			lexeme temp = operator();
			temp.right = expression();
			temp.left = tree;
			tree = temp;
		}
		return tree;
	}
	
	boolean opPending()
	{
		return check(types.PLUS) || check(types.TIMES) || check(types.DIVIDES) || check(types.MINUS) || 
			check(types.EXP) || check(types.MOD) || check(types.ISEQUAL) || check(types.GREATERTHAN) || 
			check(types.LESSTHAN) || check(types.AND) || check(types.OR);
	}
	
	lexeme operator() throws Exception
	{
		lexeme tree;
		if (check(types.PLUS))
			tree = match(types.PLUS);
		else if (check(types.TIMES))
			tree = match(types.TIMES);
		else if (check(types.DIVIDES))
			tree = match(types.DIVIDES);
		else if (check(types.MINUS))
			tree = match(types.MINUS);
		else if (check(types.EXP))
			tree = match(types.EXP);
		else if (check(types.ISEQUAL))
			tree = match(types.ISEQUAL);
		else if (check(types.GREATERTHAN))
			tree = match(types.GREATERTHAN);
		else if (check(types.LESSTHAN))
			tree = match(types.LESSTHAN);
		else if (check(types.MOD))
			tree = match(types.MOD);
		else if (check(types.AND))
			tree = match(types.AND);
		else if (check(types.OR))
			tree = match(types.OR);
		else
			tree = match(types.UNKNOWN);
		return tree;
	}
	
	boolean primaryPending()
	{
		return check(types.ID) || check(types.STRING) || check(types.INTEGER) || check(types.REAL) || check(types.BOOLEAN) || 
					check(types.OPAREN) || funcDefPending() || opPending() || check(types.NOT) || check(types.DOT) || 
					check(types.PRINT) || check(types.PRINTLN) || check(types.ARRAY) || check(types.HASH) || check(types.TOSTRING);
	}
	
	lexeme dotLoop(lexeme t) throws Exception
	{
		lexeme temp = t.right;
		t.right = match(types.DOT);
		t.right.left = temp;
		t.right.right = match(types.ID);
		if (check(types.DOT))
		{
			t.right = dotLoop(t.right);
			return t;
		}	
		else
			return t;
	}
		
	lexeme handleId() throws Exception
	{
		lexeme tree = match(types.ID);
		if (check(types.OPAREN))
		{
			tree.right = match(types.OPAREN);
			tree.right.right = optList();
			match(types.CPAREN);
		}
		else if(varSetPending())
		{
			lexeme temp = varSet();
			temp.left = tree;
			tree = temp;
		}
		else if (check(types.DOT))
		{
			match(types.DOT);
			
			lexeme temp = funcCall();
			if (temp.type == types.DOT)
			{
				temp.left = tree;
				tree = temp;
				if (check(types.DOT))
					tree = dotLoop(tree);
				if (check(types.ASSIGN))
				{
					temp = varSet();
					temp.left = tree;
					tree = temp;
				}
			}
			else
				tree.right = temp;
		}
		else if (check(types.AOBRACE))
		{	
			match(types.AOBRACE);
			tree.left = match(types.INTEGER);
			match(types.ACBRACE);
			if (check(types.ASSIGN))
			{
				lexeme temp = varSet();
				temp.left = tree;
				tree = temp;
			}
		}
		return tree;
	}
	
	lexeme primary() throws Exception
	{
		lexeme tree;
		if (check(types.ID))
		{
			tree = handleId();
		}
		else if (check(types.STRING))
		{
			tree = match(types.STRING);
		}
		else if (check(types.INTEGER))
		{
			tree = match(types.INTEGER);
		}
		else if (check(types.REAL))
		{
			tree = match(types.REAL);
		}
		else if (check(types.NULL))
		{
			tree = match(types.NULL);
		}
		else if (check(types.BOOLEAN))
		{
			tree = match(types.BOOLEAN);
		}
		else if (check(types.OPAREN))
		{
			match(types.OPAREN);
			tree = expression();
			match(types.CPAREN);	
		}
		else if (funcDefPending())
		{
			tree = funcDef();
		}
		else if (check(types.DOT))
		{
			tree = funcCallList();
		}
		else if (opPending())
		{
			tree = operation();
		}
		else if (check(types.NOT))
		{
			tree = match(types.NOT);
			tree.right = primary();
		}
		else if (check(types.PRINT))
		{
			tree = match(types.PRINT);
			match(types.OPAREN);
			tree.right = optList();
			match(types.CPAREN);
		}
		else if (check(types.PRINTLN))
		{
			tree = match(types.PRINTLN);
			match(types.OPAREN);
			tree.right = optList();
			match(types.CPAREN);	
		}
		else if (check(types.HASH)) 
		{
			tree = match(types.HASH);
			match(types.OPAREN);
			if (check(types.STRING))
				tree.right = match(types.STRING);
			else if (check(types.INTEGER))
				tree.right = match(types.INTEGER);
			else if (check(types.REAL))
				tree.right = match(types.REAL);
			else if (check(types.ID))
				tree.right = match(types.ID);
			match(types.CPAREN);
		}
		else if (check(types.TOSTRING))
		{
			tree = match(types.TOSTRING);
			match(types.OPAREN);
			if (check(types.STRING))
				tree.right = match(types.STRING);
			else if (check(types.INTEGER))
				tree.right = match(types.INTEGER);
			else if (check(types.REAL))
				tree.right = match(types.REAL);
			match(types.CPAREN);
		}
		else if (check(types.ARRAY))
		{
			tree = match(types.ARRAY);
			if (check(types.AOBRACE))
			{
				match(types.AOBRACE);
				tree.left = match(types.INTEGER);
				match(types.ACBRACE);
				if (check(types.ASSIGN))
				{
					lexeme temp = varSet();
					temp.left = tree;
					tree = temp;
				}
			}
			else
			{
				match(types.OPAREN);
				tree.right = list();
				match(types.CPAREN);
			}
		}
		else
		{
			tree = match(types.ENDofINPUT);
		}
		return tree;
	}
	
	boolean typePending()
	{
		return check(types.INTEGER) || check(types.STRING) || check(types.REAL) || check(types.BOOLEAN);
	}
	
	boolean statementPending()
	{
		return check(types.ID) || varDefPending() || varSetPending() || whileLoopPending() 
				|| exprPending() || ifStatementPending() || check(types.RETURN) || funcDefPending();
	}
	
	lexeme optStatementList() throws Exception
	{
		if (statementPending())
			return statementList();
		else
			return new lexeme(types.EMPTY);
	}
	
	lexeme statementList() throws Exception
	{
		lexeme tree = new lexeme(types.NODE);
		tree.left = statement();
		if (statementPending())
			tree.right = statementList();
		return tree;
	}
	
	lexeme statement() throws Exception
	{
		lexeme tree;
		
		if (varDefPending())
		{
			tree = varDef();
			match(types.SEMICOLON);
		}
		else if (whileLoopPending())
		{
			tree = whileLoop();
		}
		else if (funcDefPending())
		{
			tree = funcDef();
		}
		else if (exprPending())
		{
			tree = expression();
			match(types.SEMICOLON);
		}
		else if (ifStatementPending())
		{
			tree = ifStatement();
		}
		else
		{
			tree = match(types.RETURN);
			tree.left = expression();
			match(types.SEMICOLON);
		}
		return tree;
	}
	
	boolean varDefPending()
	{
		return check(types.VAR) || typePending();
	}
	
	lexeme varDef() throws Exception
	{
		lexeme tree;
		if (typePending())
			tree = type();
		else
			tree = match(types.VAR);
			
		tree.left = match(types.ID);
		if (check(types.ASSIGN))
		{
			tree.right = varSet();
			tree.right.left = tree.left;
		}
		return tree;
	}
	
	boolean varSetPending()
	{
		return check(types.ASSIGN);
	}
	
	lexeme varSet() throws Exception
	{
		lexeme tree = match(types.ASSIGN);
		tree.right = expression();
		return tree;
	}
	
	boolean blockPending()
	{
		return check(types.OBRACE);
	}
	
	lexeme block() throws Exception
	{
		lexeme tree = match(types.OBRACE);
		tree.right = optStatementList();
		match(types.CBRACE);
		return tree;
	}
	
	boolean funcDefPending()
	{
		return check(types.DEF) || check(types.LAMBDA);
	}
	
	lexeme funcDef() throws Exception
	{
		lexeme tree;
		if (check(types.DEF))
		{
			tree = match(types.DEF);
			tree.left = match(types.ID);
		}
		else
		{
			tree = match(types.LAMBDA);
		}
		match(types.OPAREN);
		tree.right = optList();
		match(types.CPAREN);
		if (tree.type ==(types.LAMBDA))
			tree.left = block();
		else
			tree.left.left = block();
		
		return tree;
	}
	
	lexeme type() throws Exception
	{
		if (check(types.INTEGER))
			return match(types.INTEGER);
		else if (check(types.STRING))
			return match(types.STRING);
		else if (check(types.REAL))
			return match(types.REAL);
		else
			return match(types.BOOLEAN);
	}
	
	lexeme optParamList() throws Exception
	{
		if (listPending())
			return list();
		else
			return new lexeme(types.EMPTY);
	}
	
	boolean ifStatementPending()
	{
		return check(types.IF);
	}
	
	lexeme ifStatement() throws Exception
	{
		lexeme tree = match(types.IF);
		match(types.OPAREN);
		tree.left = expression();
		match(types.CPAREN);
		if (blockPending())
		{
			tree.right = block();
		}
		else
		{
			tree.right = statement();
		}
		tree.right.left = optElse();
		return tree;
	}
	
	lexeme optElse() throws Exception
	{
		lexeme tree = new lexeme(types.EMPTY);
		if (check(types.ELSE))
		{
			tree = match(types.ELSE);
			if (blockPending())
				tree.right = block();
			else
				tree.right = statement();
		}
		return tree;
	}
	
	boolean whileLoopPending()
	{
		return check(types.WHILE);
	}
	
	lexeme whileLoop() throws Exception
	{
		lexeme tree = match(types.WHILE);
		match(types.OPAREN);
		tree.left = expression();
		match(types.CPAREN);
		if (blockPending())
			tree.right = block();
		else
			tree.right = statement();
		return tree;
	}
}
