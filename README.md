# kapow

How to run programs in my language:

1. Run the make command ("make clean" then make for a clean build)

2. After running the make command you should have a file called evaluate. Just type into the command line "evaluate fn" where fn is the file name and without the quotes. All of my programs have been in .txt files but any extension should work.




How to program in my language:

My language is some hybrid of python, c, and other modern languages.

You do not need a main function.

To define a new variable type: 
	var variableName = something;
*Note: you must assign a variable when defining it.*
*Currently you cannot say "type variableName = something", you have to use var*



To define a new function, type: 
	def functionName(optParam1, optParam2) {
		//body
	}
Note the bracket notation, for something reason when doing this notation:
	def func()
	{
		//body
	}
there is occasionally an error following an else statement, it is a known bug and in the mean time the first notation
is necessary.




Function calls are as follows:
	functionName(x, y);

To create an array do:
	var a = array(1, 2, 3, 312, 32421);

To access the array do:
	a[index];

To create an object just create a function where the last statement of the body is "this;":
	def Node(key, val) {
		this;
	}

Then you can access the object like this:
	var n = Node(2, "cool");
	println(n.key);
	n.key = 5;

To print out type:
	print("something");

	or 

	println("something");

	or

	print(ID);

All lexemes can be printed, so everything has a print string associated with it.


This readme is vague and I intend to make this more detailed in the future.
The implementations I've done can be a good resource for random things.



	

