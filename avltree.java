import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

class avltree{
    //constructor
    avltree(){

    }
    //root of the avl tree
    Node root;
    public void Initialize(){
           //resets the current avl tree
             root=null;
     }
    public Node Insert(Node node, int key){
        //If there are no nodes present, just insert the node with key
        if (node==null){
            return new Node(key); 
        }
        //If there are already some nodes present, node should be inserted at a correct position
        //case 1: node to be inserted has value less than current node=> insert left
        if(key<node.value){
            
             node.left=Insert(node.left, key);
        }
        //case 2: node to be inserted has value greater than current node=>insert right
        else if (key>node.value){
            
             node.right=Insert(node.right,key);
        }
        //Now that node is inserted, check for the balance factor
        //Step 1: Calculate the height of the node
        //It is the maximum of (height of left subtree,height of right subtree )+1
        if (nodeHeight(node.left)>nodeHeight(node.right)){
            node.height=nodeHeight(node.left)+1;
        }
        else{
            node.height=nodeHeight(node.right)+1;
        }
        //Step 2: Calculate Balance Factor
        int balanceFactor=calculateBalanceFactor(node);
        //Balance factor should be in the range -1,1 inclusive
        if (balanceFactor>1){
            //This means that height of the left tree is greater than the height of the right tree
            //RR
            if(key<node.left.value){
                
                //example: 3
                //        /
                //      2
                //     /
                //    1
                return rightRotate(node);
            }
            //RL
            else if(key>node.left.value){
                
                //example: 4
                //        /
                //      2
                //       \
                //         3

                node.left=leftRotate(node.left);
                return rightRotate(node);

            }
        }
        //Balance Factor less than -1
        //This happens when height of right tree is greater
        if(balanceFactor<-1){
            //LL
            if (key>node.right.value){
                //example: 3
                //          \
                //           4
                //            \
                //             5
                return leftRotate(node);
            }
            //LR
            else if(key<node.right.value){
                //example: 4
                //          \
                //           6
                //          /
                //         5
                node.right=rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }
    public Node Delete(Node node, int key){
        //If the node is not present, return null
        if (node == null) {
            return null;
          }
        // Search left or right sub-trees based on the key of the node to be deleted
        //case1: key is less than the current node=> search left
        if (key < node.value) {
            node.left = Delete(node.left,key);
        } 
        //case2: key is greater than the current node=>search right
        else if (key > node.value) {
            node.right = Delete(node.right, key);
        }
        //We have reached the node to be deleted
        //Case 1: If node has no left or right child
        //In such case we are free to delete the node
        else if (node.left == null && node.right == null) {
            node = null;
          }
        //Case 2: If node has only one child either left child or right child
        //In such case we replace the node that we are deleting with its child
        
        //There is no left child=>Replace the node with right child
        else if (node.left == null) {
            node = node.right;
          }
        //There is no right child => Replace the node with left child
        else if (node.right == null) {
            node = node.left;
          }
        //Case 3: A node which has to be deleted has two children
        else{
            deleteParentOfTwoChildren(node);
        }
        if (node==null){
            return null;
        }
        //Now that node is deletd, check for the balance factor
        //Calculate the height of the node
        //It is the maximum of (height of left subtree,height of right subtree )+1
        if (nodeHeight(node.left)>nodeHeight(node.right)){
            node.height=nodeHeight(node.left)+1;
        }
        else{
            node.height=nodeHeight(node.right)+1;
        }
        int balanceFactor=calculateBalanceFactor(node);
        //Balance factor should be in the range -1,1 inclusive
        if (balanceFactor>1){
            //This means that height of the left tree is greater than the height of the right tree
            //RR
            if(key<node.left.value){
                //Right Rotate
                //example: 3
                //        /
                //      2
                //     /
                //    1
                return rightRotate(node);
            }
            //RL
            else if(key>node.left.value){
                //Left Rotate
                //example: 4
                //        /
                //      2
                //       \
                //         3
                node.left=leftRotate(node.left);
                return rightRotate(node);

            }
        }
        //Balance Factor less than -1
        //This happens when height of right tree is greater
        if(balanceFactor<-1){
            //LL
            if (key>node.right.value){
                //example: 3
                //          \
                //           4
                //            \
                //             5
                return leftRotate(node);
            }
            //LR
            else if(key<node.right.value){
                //example: 4
                //          \
                //           6
                //          /
                //         5

                node.right=rightRotate(node.right);
                return leftRotate(node);
            }
        }


  
      
        return node;
    }
    public void deleteParentOfTwoChildren(Node node) {
        //We can implement deletion of a node with two children in two ways
        //Method 1: Replace node with Inorder predecessor(Largest Element of left subtree)
        //Method 2:Replace node with Inorder successor(Smallest Element of right subtree)
        // Find maximum node of left subtree ("inorder predecessor" of current node to be deleted)
        Node inorderPredecessor = findMaximumLeftChild(node.left);
    
        // Copy inorder predecessor value to current node
        node.value= inorderPredecessor.value;
    
        // Delete inorder predecessor recursively
        node.left = Delete(node.left, inorderPredecessor.value);
      }
    //Helper function to return the maximum value in the left subtree of a node to be deleted
    public Node findMaximumLeftChild(Node node) {
        while (node.right != null) {
          node = node.right;
        }
        return node;
      }
    
    int nodeHeight(Node node){
        if(node==null){
            return 0;

        }
        else{
            return node.height;
        }
    }
    
    int calculateBalanceFactor(Node node){
        //If the node is null Balance Factor=0
        if (node==null){
            return 0;

        }
        //Balance Factor= Height of left subtree- height of right sub tree
        else{
            return nodeHeight(node.left)-nodeHeight(node.right);
        }
    }
    public Node leftRotate(Node a){
        Node b=a.right;
        Node c=b.left;
        b.left=a;
        a.right=c;
        //Height of left subtree is greater
        if (nodeHeight(a.left)>nodeHeight(a.right)){
            a.height=nodeHeight(a.left)+1;
        }
        //Height of right subtree is greater
        else{
            a.height=nodeHeight(a.right)+1;
        }
        //Height of left subtree is greater
        if (nodeHeight(b.left)>nodeHeight(b.right)){
            b.height=nodeHeight(b.left)+1;
        }
        //Height of right subtree is greater
        else{
            b.height=nodeHeight(b.right)+1;
        }
        
        return b;
    }
    public Node rightRotate(Node b){
        Node a=b.left;
        Node c=a.right;
        a.right=b;
        b.left=c;
        //Height of left subtree is greater
        if (nodeHeight(b.left)>nodeHeight(b.right)){
            b.height=nodeHeight(b.left)+1;
        }
        //Height of right subtree is greater
        else{
            b.height=nodeHeight(b.right)+1;
        }
        //Height of left subtree is greater
        if (nodeHeight(a.left)>nodeHeight(a.right)){
            a.height=nodeHeight(a.left)+1;
        }
        //Height of right subtree is greater
        else{
            a.height=nodeHeight(a.right)+1;
        }

        return a;


    }
    public ArrayList<Node> inorderTraversal(Node node, int key1, int key2, ArrayList<Node> nodeList){
        //Inorder Traversal Algorithm
        if (node!=null){
            inorderTraversal(node.left, key1,  key2, nodeList);
            if (node.value>=key1){
                if (node.value<=key2){
                    //Add a node to the node list if and only if 
                    //Its value is in the range[key1,key2]
                    nodeList.add(node);
                }
            }
            inorderTraversal(node.right, key1,  key2, nodeList);
        }
        return nodeList;
    }

    public ArrayList<Node> Search(Node node, int key1, int key2){
        //Here the idea is to store the nodes that are in the range[key1, key2] inside an ArrayList
            ArrayList<Node> nodeList=new ArrayList<>();
            if (node==null){
                return nodeList;
            }
            //We traverse through the avl tree using inorder Traversal
            //Since we need to get the node values in ascending order in a certain range
            return inorderTraversal(node,  key1, key2, nodeList);
           
        
    }
    public Node Search(Node node,int key){
        //Iterative Binary Search Tree Algorithm
        while (node!=null){
            //Case 1: found the node
            if (node.value==key){
                return node;
            }
            //case 2: value of the current node is less than the key
            else if(node.value<key){
                node=node.right;
            }
            //case 3: value of the current node is greater than the key
            else if (node.value>key){
                node=node.left;
            }
        }
        return node;

    }
    
    public static void main(String [] args) throws FileNotFoundException
    {
        //Creates an object of class avltree
        avltree a=new avltree();
        //User gives argument input file while running the code
        //For example java avltree input.java
        //Input file contains Methods to be called
        File input=new File(args[0]);
        //output is written into the file output_file.txt
        PrintWriter output=new PrintWriter("output_file.txt");
        Scanner sc=new Scanner(input);
        //This loop runs as long as input file has data
        while (sc.hasNextLine()){
            String s=sc.nextLine();
            //Here trim method is used to remove white spaces before and after the text lines
            s=s.trim();
            //Seperate the text by paranthesis
            //For example Insert(13). In this case, insert 
            String operation= s.split("[\\(\\)]")[0]; 
            //Input operation is Insert
            if(operation.equals("Insert")||operation.equals("insert")){
                        //The value inside parantesis is stored in the variable val
                        //For example, in the case of Insert(13), 13 will be stored in val
                        String val=s.split("[\\(\\)]")[1];
                        //calling the method Insert with val
                        a.root=a.Insert(a.root, Integer.parseInt(val));
            }
            //Input operation is Initialize
            else if(operation.equals("Initialize")||operation.equals("initialize")|| operation.equals("Initialise")|| operation.equals("initialise"))
             {
                //calling the method Initialize
                a.Initialize();
             }
             //Input operation is Delete
             else if(operation.equals("Delete")||operation.equals("delete")||operation.equals("Remove")||operation.equals("remove")){
                //The value inside the paranthesis is stored in the variable var
                //For example, in the case of Delete(13), 13 will be stored in val
                String val=s.split("[\\(\\)]")[1];
                //calling Delete method with val
                a.root=a.Delete(a.root, Integer.parseInt(val));

             }
             //Input operation is Search
             //There are two cases
             //Case1: Search method is called with a single argument
             //Case2: Search method is called with two argument
             else if(operation.equals("Search")||operation.equals("search")||operation.equals("Find")||operation.equals("find")){
                //Content inside paranthesis is stored inside val
                String val=s.split("[\\(\\)]")[1];
                //content inside paranthesis is further splitted using ","(comma)
                //Here it is determined if the Search method is called with Single argument or two arguments
                String[] tokens=val.split(",");
                //Single argument
                if (tokens.length==1){
                    Node node=a.Search(a.root,Integer.parseInt(tokens[0]));
                    if (node==null){
                        output.println("NULL");
                    }
                    else{
                        //write node's value in the output file
                        output.println(node.value);
                    }
                }
                else{
                    //Two arguments
                    ArrayList<Node>valueList=a.Search(a.root,Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                    //Nothing is found ...So write null in the output file
                    if (valueList.size()==0){
                        output.println("NULL");
                    }
                    
                    else{
                        for (int i = 0; i < valueList.size(); i++){
                            if(i==0){
                                //We have to fill comma seperated values in the output file
                                //so for first value, do this
                                output.print(valueList.get(i).value);

                            }
                            else{
                                //for later values do this
                                output.print(","+valueList.get(i).value);
                            }
                            
                        }
                        //move cursor to next line
                        output.println();

                    }
                    
                    
           
            
                }

             }

        }
        //close scanner object
        sc.close();
        //close Printwriter object
        output.close();
       


 }
 
}