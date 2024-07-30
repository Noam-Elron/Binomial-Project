/**
 * BinomialHeap
 *
 * An implementation of binomial heap over positive integers.
 *
 */
public static void main(String[]args){
	BinomialHeap heap = new Binomi3alHeap();
	heap.insert(1, "geut");
}


public class BinomialHeap
{
	public int size;
	public HeapNode last;
	public HeapNode min;

	public BinomialHeap(){
		this.size = 0;
		this.last = null;
		this.min = null;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info) 
	{
		HeapItem hi = new HeapItem(null, key, info);
		HeapNode hn = new HeapNode(hi, null, hn, null, 0);
		hi.node = hn;
		BinomialHeap B0 = new BinomialHeap(1, hn, hn);

		this.meld(B0);
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		return; // should be replaced by student code

	}

	/**
	 * 
	 * Return the minimal HeapItem, null if empty.
	 *
	 */
	public HeapItem findMin()
	{
		if (this.min != null) {
			return this.min.item;
		}
		return null;
	} 

	/**
	 * 
	 * pre: 0<diff<item.key
	 * 
	 * Decrease the key of item by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapItem item, int diff) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2)
	{
		HeapNode p1 = this.last;
		p1 = p1.next;
		HeapNode p2 = heap2.last;
		p2 = p2.next;

		while(p1 != this.last && p2 != heap2.last){
			if (p1.rank == p2.rank) { // link two nodes of the same rank
				HeapNode temp = p1.next;
				p1 = this.link(p1, p2);
				p1.next = temp;
				p2 = p2.next;
			}
			else{
				if(p1.rank < p2.rank){ // advance in p1 (this.heap), no need to add items from p2 yet
					p1 = p1.next;
				}
				else{ // p1.rank > p2.rank, add item from p2 to p1
					HeapNode temp = p1.next; // saving pointer to what's going to be the next node in the end of the day
					p1.next = p2; // adding the current node in heap2 to this.heap - unfortunately with a "tail" of other nodes
					p1 = p1.next;
					p1.next = temp; // no more tail of unwanted nodes!
				}
			}
		}

		if (p1 == this.last && p2 == heap2.last){
			if(p1.rank == p2.rank){ // link nodes
				HeapNode temp = p1.next;
				p1 = this.link(p1, p2);
				p1.next = temp;
				p1 = p1.next; // we would like it to be the new "last"
			}
			else{ // add node
				HeapNode temp = p1.next; // saving pointer to what's going to be the next node in the end of the day
				p1.next = p2; // adding the current node in heap2 to this.heap - unfortunately with a "tail" of other nodes
				p1 = p1.next;
				p1.next = temp; // no more tail of unwanted nodes!
				p1 = p1.next; // we would like it to be the new "last"
			}
		}
		else {
			if (p1 == this.last && p2 != heap2.last) {
				heap2.last.next = p1.next; // p2 will now arrive even to p1's first element
				p1.next = p2;
				p1 = heap2.last;
			}
		}
		this.last = p1;
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size;
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 *   
	 */
	public boolean empty()
	{
		return this.size == 0;
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return 0; // should be replaced by student code
	}

	/**
	 * Class implementing a node in a Binomial Heap.
	 *  
	 */


	/**
	 *
	 * pre: x,y are Binomial tree header nodes
	 * pre: Trees with x,y as header are same degree( B_k and B_k )
	 *
	 * Static meld between two trees of same size
	 *
	 */
	public HeapNode link(HeapNode x, HeapNode y) {
		// change the line we discussed and make it work on this node and other node (receive 1 parameter insead of two)
		if (x.item.key > y.item.key) {
			HeapNode temp = x;
			x = y;
			y = temp;
		}
		// I'm adding a line that changes the rank
		x.rank = x.rank + y.rank;

        y.next = tail_node(x.child); // y points to x's smallest degree child (B_0)
		x.child.next = y; // x's original child points to y as y is inherently of bigger degree
		x.child = y; // y is x's new biggest degree child
		y.parent = x; // y's new parent(previously was null) is now x

		return x;
	}

	public HeapNode tail_node(HeapNode head) {
		HeapNode temp = head.next;
		while (temp.next != head) {
			temp = temp.next;
		}
		return temp;
	}

	public static class HeapNode{
		public HeapItem item;
		public HeapNode child;
		public HeapNode next;
		public HeapNode parent;
		public int rank;

		public HeapNode(){
			this.item = null;
			this.child = null;
			this.next = null;
			this.parent = null;
			this.rank = 0;
		}

		public HeapNode(HeapNode item, HeapNode child, HeapNode next, HeapNode parent){
			this.item = item;
			this.child = child;
			this.next = next;
			this.parent = parent;
			this.rank = 0;
		}
	}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *  
	 */
	public static class HeapItem{
		public HeapNode node;
		public int key;
		public String info;

		public HeapItem(){
			this.node = null;
			this.key = -1;
			this.info = "";
		}

		public HeapItem(HeapNode node, int key, String info){
			this.node = node;
			this.key = key;
			this.info = info;
		}
	}
}
