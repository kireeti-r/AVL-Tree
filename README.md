# AVL-Tree
The program implements a simple AVL tree and its functions such as Initialization, Insertion, Deletion and Search.

# File Structure:
1) avltree.java: Contains the class avltree and main method. The entry point for execution is in this file.
2) Node.java: Contains the class Node.
3) Makefile: make file to create java class files which can be further executed.
4) Input.txt: Contains the input data which the main method reads and makes function calls. The input file name is not hard coded and it can have any name. Preferably it
should be a text file.
5) output_file.txt(optional): contains output data for test case 1. After each test this file gets overridden with new output. The file name output_file.txt is hardcoded.

# Input Format: 
The input should be given in a text file where each method call should be written in a new line. Spaces before the actual data in each line are omitted by the program.

Example for input:

Initialize()

Insert(12)

Delete(12)

Search(13)

Search(13,15)

# Output Format: 
The output will be printed on to a text file named output_file.txt. Initialize, Insert and Delete will not print anything in the output file. Search(int a) prints the "a" in the new line. If "a" is not found in the tree then NULL will be printed. Search(int a, int b) prints all the nodes in the avl tree within the range a, b inclusive. These values will be comma separated. If nothing is found, NULL will be printed. For each execution output file will be overwritten.

# Procedure to run the program:

Step 1: Inside the directory, create an input.txt file of your choice.

Step 2: Run the “make” command to create compile code. Note: For windows, you might want to use “MinGW32-make” command. If you want to use java compiler, use the command "javac avltree.java"

Step 3: Execute the code with the command "java avltree file_name"

Step 4: Check the output_file.txt file that is created for results.
