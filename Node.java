//Class node represents each node that make up avl tree
public class Node{
    //Node has the properties
    //1) value 2) height 3)left neighbor 4) right neighbor
    int value;
    int height;
    Node left;
    Node right;
    //constructor
    Node(int val){
        value=val;
        //By default, a node has height 1
        height=1;

    }
}