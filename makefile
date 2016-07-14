evaluate : environment.java evaluate.java evaluater.java lexeme.java lexer.java parser.java types.java
	javac evaluate.java && jar -cvmf MANIFEST.MF evaluate.jar environment.class evaluate.class evaluater.class lexeme.class lexer.class parser.class types.class && cat stub.sh evaluate.jar > evaluate && chmod +x evaluate

cat-error1: 
	cat error1.txt

run-error1: evaluate
	evaluate error1.txt

cat-error2: 
	cat error2.txt

run-error2: evaluate
	evaluate error2.txt

cat-error3: 
	cat error3.txt

run-error3: evaluate
	evaluate error3.txt

cat-arrays:
	cat arrays.txt

run-arrays: evaluate
	evaluate arrays.txt

cat-conditionals:
	cat conditionals.txt

run-conditionals: evaluate
	evaluate conditionals.txt

cat-recursion:
	cat recursion.txt

run-recursion: evaluate
	evaluate recursion.txt

cat-iteration:
	cat iteration.txt

run-iteration: evaluate
	evaluate iteration.txt

cat-functions:
	cat functions.txt

run-functions: evaluate
	evaluate functions.txt

cat-dictionary:
	cat Dictionary.txt

run-dictionary: evaluate
	evaluate Dictionary.txt

cat-problem:
	cat Adder.txt

run-problem:
	evaluate Adder.txt

clean:
	rm *.class



