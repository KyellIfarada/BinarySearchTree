package edu.ser222.m03_02;



/**

 * A binary search tree based implementation of a symbol table.

 * 

 * Completion time: (your completion time)

 *

 * @author (your name), Sedgewick, Acuna

 * @version (version)

 */

import java.util.Collections;

import java.util.LinkedList;

import java.util.NoSuchElementException;

import java.util.Queue;



public class CompletedBST<Key extends Comparable<Key>, Value> implements BST<Key, Value> {

    private Node<Key, Value> root;


    
    private void updateHeight(Node x) {
        int leftChildHeight = N(x.left);
        int rightChildHeight = N(x.right);
 
        if (leftChildHeight > rightChildHeight)
        x.N = leftChildHeight + 1;
        else 
        x.N =   rightChildHeight + 1;
      }

      private Node rebalance(Node x) {
        int balanceFactor = balanceFactor(x);

        // Left-heavy?
        if (balanceFactor < -1) {
          if (balanceFactor(x.left) <= 0) {
            // Rotate right
            x = rotateRight(x);
          } else {
            // Rotate left-right
            x.left = rotateLeft(x.left);
            x = rotateRight(x);
          }
        }

        // Right-heavy?
        if (balanceFactor > 1) {
          if (balanceFactor(x.right) >= 0) {
            // Rotate left
            x = rotateLeft(x);
          } else {
            // Rotate right-left
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
          }
        }

        return x;
      }

      private Node rotateRight(Node x) {
        Node leftChild = x.left;

        x.left = leftChild.right;
        leftChild.right = x;

        updateHeight(x);
        updateHeight(leftChild);

        return leftChild;
      }

      private Node rotateLeft(Node x) {
        Node rightChild = x.right;

        x.right = rightChild.left;
        rightChild.left = x;

        updateHeight(x);
        updateHeight(rightChild);

        return rightChild;
      }

      private int balanceFactor(Node x) {
        return N(x.right) - N(x.left);
      }

      private int N(Node x) {
        return x != null ? x.N : -1;
      }
    
    
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override

    public int size() {

        return size(root);

    }



    private int size(Node x) {

        if (x == null)

            return 0;

        else

            return x.N;

    }



    @Override

    public Value get(Key key) {

        Node<Key, Value> iter = root;



        while(iter != null) {

            int cmp = key.compareTo(iter.key);



            if (cmp < 0)

                iter = iter.left;

            else if (cmp > 0)

                iter = iter.right;

            else

                return iter.val;

        }



        return null;

    }



    private Value get(Node<Key, Value> x, Key key) {

        // Return value associated with key in the subtree rooted at x;

        // return null if key not present in subtree rooted at x.

        if (x == null) return null;

        int cmp = key.compareTo(x.key);

        if (cmp < 0) return get(x.left, key);

        else if (cmp > 0) return get(x.right, key);

        else return x.val;

    }



    @Override

    public void put(Key key, Value val) {

        root = put(root, key, val);

    }



    private Node put(Node<Key, Value> x, Key key, Value val) {

        if (x == null)

            return new Node(key, val, 1);



        int cmp = key.compareTo(x.key);

        if (cmp < 0)

            x.left = put(x.left, key, val);

        else if (cmp > 0)

            x.right = put(x.right, key, val);

        else

            x.val = val;

        x.N = size(x.left) + size(x.right) + 1;

        updateHeight(x);

        return rebalance(x);

 

    }



    @Override

    public Key min() {

        if(root == null)

            throw new NoSuchElementException();

        return min(root).key;

    }



    private Node<Key, Value> min(Node x) {

        if (x.left == null)

            return x;

        return min(x.left);

    }



    @Override

    public Key max() {

        if(root == null)

            throw new NoSuchElementException();

        return max(root).key;

    }



    private Node<Key, Value> max(Node x) {

    if (x.right == null) return x;

        return max(x.right);

    }



    @Override

    public Key floor(Key key) {

        if(root == null)

            throw new NoSuchElementException();



        Node<Key, Value> x = floor(root, key);

        if (x == null)

            return null;

        return x.key;

    }



    private Node<Key, Value> floor(Node<Key, Value> x, Key key) {

        if (x == null)

            return null;

        int cmp = key.compareTo(x.key);

        if (cmp == 0) return x;

        if (cmp < 0) return floor(x.left, key);

        Node<Key, Value> t = floor(x.right, key);

        if (t != null) return t;

        else return x;

    }



    @Override

    public Key select(int k) {

        return select(root, k).key;

    }



    private Node<Key, Value> select(Node x, int k) {

        if (x == null) return null;

        int t = size(x.left);

        if (t > k) return select(x.left, k);

        else if (t < k) return select(x.right, k-t-1);

        else return x;

    }



    @Override

    public int rank(Key key) {

        return rank(key, root);

    }



    private int rank(Key key, Node<Key, Value> x) {

        // Return number of keys less than x.key in the subtree rooted at x.

        if (x == null) return 0;

        int cmp = key.compareTo(x.key);

        if (cmp < 0) return rank(key, x.left);

        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);

        else return size(x.left);

    }



    @Override

    public void deleteMin() {

        if(root == null)

            throw new NoSuchElementException();

        root = deleteMin(root);
        
      
       return;

    }



    private Node<Key, Value> deleteMin(Node x) {

        if (x.left == null) return x.right;

        x.left = deleteMin(x.left);

        x.N = size(x.left) + size(x.right) + 1;
        
        updateHeight(x);

        return rebalance(x);

    }



    @Override

    public void delete(Key key) {

        root = delete(root, key);

    }



    private Node<Key, Value> delete(Node<Key, Value> x, Key key) {

        if (x == null) return null;

        int cmp = key.compareTo(x.key);

        if (cmp < 0) x.left = delete(x.left, key);

        else if (cmp > 0) x.right = delete(x.right, key);

        else

        {

            if (x.right == null) return x.left;

            if (x.left == null) return x.right;

            Node t = x;

            x = min(t.right);

            x.right = deleteMin(t.right);

            x.left = t.left;

        }

        x.N = size(x.left) + size(x.right) + 1;

        updateHeight(x);

        return rebalance(x);

    }



    @Override

    public Iterable<Key> keys() {

        if (root == null)

            return new LinkedList<>();

        else

            return keys(min(), max());

    }



    @Override

    public Iterable<Key> keys(Key lo, Key hi)

    {

        Queue<Key> queue = new LinkedList<>();

        keys(root, queue, lo, hi);

        return queue;

    }



    private void keys(Node<Key, Value> x, Queue<Key> queue, Key lo, Key hi)

    {

        if (x == null) return;

        int cmplo = lo.compareTo(x.key);

        int cmphi = hi.compareTo(x.key);

        if (cmplo < 0) keys(x.left, queue, lo, hi);

        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);

        if (cmphi > 0) keys(x.right, queue, lo, hi);

    }



    public Key ceiling(Key key) {

        //SKIP, UNNEEDED

        return null;

    }

    public Node getRoot() {

        return root;

    }



    public boolean contains(Key key) 

    {

        

    	if ( get(key) != null)

    	{

    	return true;

    	}

		return false;

    

    }



    public boolean isEmpty() {

        //TODO

    	boolean	Empty = true;

    	boolean	NotEmpty = false;

    	if(size() == 0 )

    	{

    	return Empty;

    	}

    	else {

        return NotEmpty;

    	}

    }



    public void deleteMax()  

    {

    	

         // root = deleteMax(root);

        if(root == null)

        {

        	throw new NoSuchElementException();

        }

        else

        	root = deleteMax(root);

    }

    

    

        private Node<Key, Value> deleteMax(Node x)

        {

        	Node<Key, Value> Nothing = null;

        	if(x.right == Nothing)

        	{

        		return x.left;

        	}

        		

        	else

        	{

        	x.right =  deleteMax(x.right);

        			

        	x.N = size(x.left) + size(x.right) + 1 ;

            updateHeight(x);

            return rebalance(x);

        	

       

        	}

        }

        

       

    



    public int size(Key lo, Key hi) {

    	int sizeNow ;

        if(lo.compareTo(hi) > 0)

        {

        return 0;

        }

        

        if(contains(hi))

        {

        	sizeNow = rank(hi) - rank(lo) + 1 ;

        	return sizeNow;

        }

        else 

        {

        	sizeNow =  rank(hi) - rank(lo);

        	return sizeNow;

        }

        

    }



    public void putFast(Key key, Value val) 

    {

    	Node Nothing = null;

        if( root == Nothing)

        {

        	root = new Node(key,val,1);

        }

        else

        {

        	

        	Node MasterNode = Nothing;

        	Node PlaceNode = root;

        	

        	while (PlaceNode != Nothing)

        	{

        		MasterNode = PlaceNode;

        		

        		if (key.compareTo((Key) PlaceNode.key) < 0)

        		{

        			PlaceNode = PlaceNode.left;

        			

        		}

        		else if (key.compareTo((Key) PlaceNode.key) > 0)

        		{

        			PlaceNode = PlaceNode.right;

        		}

        		else

        		{

        			PlaceNode.val = val;

        			return;

        			

        		}

        	}

        

        	PlaceNode = root;

        	

        	while (PlaceNode != null)

        		

        	{

        		PlaceNode.N = PlaceNode.N + 1; 

        		

        		if (key.compareTo((Key) PlaceNode.key) < 0)

        		{

        			PlaceNode = PlaceNode.left;

        			

        		}

        		else if(key.compareTo((Key) PlaceNode.key) > 0)

        		{

        			PlaceNode = PlaceNode.right;

        			

        		}

        	}

        	

        	Node FreshNode = new Node(key,val,1);

        	

        	if(key.compareTo((Key) MasterNode.key) < 0)

        	{

        		MasterNode.left = FreshNode;

        	}

        	else

        	{

        		MasterNode.right = FreshNode;

        	}

        	

        	

        	

        }

    }



    public Value getFast(Key key) {

        Value Nothing = null;

    	Node<Key, Value> getNode = root;

    	while ( getNode != Nothing)

    		

    	{

    		int InnerValue = key.compareTo((Key)getNode.key);

    		

    		if (InnerValue == 0)

    		{

    			return (Value)getNode.val;

    		}

    		

    		else if (InnerValue < 0 )

    		{

    			getNode = getNode.left;

    		}

    		

    		else

    		{

    			getNode = getNode.right;

    		}

    		

    	}

        return Nothing;

    }



    public void balance() 

    {

        LinkedList<Node> SomeNodes = new LinkedList<Node>();

        sortMyNodes(SomeNodes,root);
        
        

        root = EqualizeTrees(SomeNodes,0,size() - 1);

    }    

        // Some Private Helpers!!

        

        //def of sortMyNodes

        

        private void sortMyNodes(LinkedList<Node> SomeNodes,Node x )

        {

        	Object Invalid = null;

        	if (x == Invalid)

        	{

        		return;

        	}

        	

        	sortMyNodes(SomeNodes,x.left);

        	SomeNodes.add(x);

        	x.N = size(x.left) + size(x.right) + 1;

        	sortMyNodes(SomeNodes,x.right);

        	



        }

       

       private Node<Key, Value> EqualizeTrees (LinkedList<Node> SomeNodes,int begin,int end)

       {

    	   Node<Key, Value> Invalid = null;

    	   if (begin > end) 

    	   	   {

    	   	return Invalid;

    		   }

    	   else {

    		   

    	   

    	   	  

    		 int  mid = (begin + end) / 2;

    		  

    		 

    		   if((begin + end) % 2 == 1)

    		   {

					mid = mid +1 ;

    		   }

		

		

			   Node midNode = SomeNodes.get(mid);

			   

			   midNode.left  = EqualizeTrees(SomeNodes,begin,mid-1);

			   midNode.right = EqualizeTrees(SomeNodes,mid +1 , end);

			   

			   return midNode;

			   

			   

    	   }

    	   	

       

       

        

    }



    public String displayLevel(Key key) 

    {

        Node PlaceNode = root;

        String Null= "empty";

        String Final="";

        while (PlaceNode != null)

        {

        	int val;

        	val = key.compareTo((Key) PlaceNode.key);

        	

        	if(val < 0)

        	{

        		PlaceNode = PlaceNode.left;

        	}

        	else if (val > 0)
        	
        	{

        		PlaceNode = PlaceNode.right;

        	}

        	else

        		break;

        }	

        

       if (PlaceNode == null)

    	   return Null;

       

       Queue<Node> Qeueue = new LinkedList<Node> ();

       Qeueue.add(PlaceNode);

       Object DNE = null;

       

       while(!Qeueue.isEmpty())

       {

    	   PlaceNode = Qeueue.poll();

    	   

    	  // System.out.println(PlaceNode.val);

    	   Final+= PlaceNode.val + " ";

    	   

    	   if (PlaceNode.left != DNE)

    	   {

    		   Qeueue.add(PlaceNode.left);

    		   

    	   }

    	   

    	    if (PlaceNode.right != DNE)

		{

			Qeueue.add(PlaceNode.right);

	            

        }



       }

    	

        return Final;

    }



    /**

     * entry point for testing.

     * 

     * @param args the command line arguments

     */

    public static void main(String[] args) {

        BST<Integer, String> bst = new CompletedBST();

        

        bst.put(10, "TEN");

        bst.put(3, "THREE");

        bst.put(1, "ONE");

        bst.put(5, "FIVE");

        bst.put(2, "TWO");

        bst.put(7, "SEVEN");

        

        System.out.println("Before balance:");

        System.out.println(bst.displayLevel(10)); //root

        

        System.out.println("After balance:");

        bst.balance();

        System.out.println(bst.displayLevel(5)); //root

    }

}
