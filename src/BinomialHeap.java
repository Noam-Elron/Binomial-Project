/**
 * BinomialHeap
 *
 * An implementation of binomial heap over positive integers.
 *
 */
public class BinomialHeap
{

	public int size;
	public HeapNode last;
	public HeapNode min;
	public int num_trees;

	public BinomialHeap(){
		this.size = 0;
		this.num_trees = 0;
		this.last = null;
		this.min = null;
	}

	public BinomialHeap(HeapNode node){
		this.size = 1;
		this.num_trees = 1;
		this.last = node;
		this.min = node;
	}

	public HeapNode getLast(){
		return this.last;
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
		HeapItem b_0_heapitem = new HeapItem(key, info);
		HeapNode b_0_heapnode = new HeapNode(b_0_heapitem);
		b_0_heapitem.set_node(b_0_heapnode);


		if (this.size == 0) {
			this.min = b_0_heapnode;
			this.last = b_0_heapnode;
			this.size++;
			this.num_trees++;
			return b_0_heapitem;
		}

		BinomialHeap B0 = new BinomialHeap(b_0_heapnode);


		this.meld(B0);

		return b_0_heapitem;
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
		HeapNode previous = p1;

		while(p1 != this.last && p2 != heap2.last){
			if (p1.rank == p2.rank) { // link two nodes of the same rank
				HeapNode temp = p2.next; // p2.next is overriden while linking so need to save next Binomial tree
				p1 = this.link(p1, p2); // hang p2 as leftmost child of p1.
				p2 = temp; // temp contains the next binomial tree in the second heap
			}
			else if (p1.rank < p2.rank){ // advance in p1 (this.heap), no need to add items from p2 yet
					previous = p1;
					p1 = p1.next;
			}
			else{ // p1.rank > p2.rank, add p2 before p1
				HeapNode temp = p2.next; // need to save p2.next as p2 now needs to points to p1.
				previous.next = p2;
				p2.next = p1;
				p2 = temp;
				this.num_trees += 1;
			}

		}

		if (p1 == this.last && p2 == heap2.last){
			if(p1.rank == p2.rank){ // link nodes
				p1 = this.link(p1, p2);
			}
			else{ // add node
				if (p1.rank <= p2.rank) {
					HeapNode temp = p1.next; // first element of this heap
					p1.next = p2; // adding the current node in heap2 to this.heap - unfortunately with a "tail" of other nodes
					p2.next = temp; // no more tail of unwanted nodes!
					p1 = p1.next;
				}

			}
		}
		else {
			System.out.println("5");
			System.out.println("p1 shone milast?: "+p1);
			if (p1 == this.last && p2 != heap2.last) {
				System.out.println("6");
				heap2.last.next = p1.next; // p2 will now arrive even to p1's first element
				p1.next = p2;
				p1 = heap2.last;
			}
			else{
				System.out.println("7");
				if (p1.rank == p2.rank) { // link two nodes of the same rank
					HeapNode temp = p1.next;
					p1 = this.link(p1, p2);
					p1.next = temp;
					p2 = p2.next;
					System.out.println("p1 in the if: "+p1);
					System.out.println("p2 in the if: "+p2);
				}
				if (p1.rank == p1.next.rank){
					p1 = this.link(p1, p1.next);
					p1.next = p1;
				}
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
		return this.num_trees;
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
		x.rank = x.rank + y.rank +1 ;

		//y.next = tail_node(x.child); // y points to x's smallest degree child (B_0)
		y.next = x.child.next;
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

		// Empty/Virtual HeapNode
		public HeapNode(){
			this.item = new HeapItem();
			this.child = null;
			this.next = null;
			this.parent = null;
			this.rank = -1;
		}

		// B_0 HeapNode, used in insert to create a B_0 Heap so no parent, child or next.
		public HeapNode(HeapItem item){
			this.item = item;
			this.child = null;
			this.next = null;
			this.parent = null;
			this.rank = 0;
		}

		public HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent){
			this.item = item;
			this.child = child;
			this.next = next;
			this.parent = parent;
			this.rank = 0;
		}

		public String toString(){
			String child = "null";
			String next = "null";
			String parent = "null";

			if(this.child != null){
				child = this.child.item.toString2();
			}
			if(this.next != null){
				next = this.next.item.toString2();
			}
			if(this.parent != null){
				parent = this.parent.item.toString2();
			}

			return "(HeapNode) <item: "+this.item+", child: "+child+", next: "+next+", parent: "+parent+", rank: "+this.rank+">";
		}

		public HeapItem getItem(){
			return this.item;
		}
		public HeapNode getChild(){
			return this.child;
		}
		public HeapNode getNext(){
			return this.next;
		}
		public int getRank(){
			return this.rank;
		}
		/*
                public boolean equals(HeapNode node){
                    String this_child="", this_next="", this_parent="";
                    String node_child="", node_next="", node_parent="";

                    if (this.child == null){
                        this_child = "null";
                    }
                    if (this.next == null){
                        this_next = "null";
                    }
                    if (this.parent== null){
                        this_parent = "null";
                    }

                    if (node.child == null){
                        node_child = "null";
                    }
                    if (node.next == null){
                        node_next = "null";
                    }
                    if (node.parent== null){
                        node_parent = "null";
                    }

                    return this.item.equals(node.item) && this_child.equals(node_child) && this_next.equals(node_next) && this_parent.equals(node_parent) && this.rank == node.rank;
                }
        */
		public boolean equals(HeapNode node){
			return this.toString().equals(node.toString());
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
			this.info = "empty value";
		}

		public HeapItem(int key, String info){
			this.node = null;
			this.key = key;
			this.info = info;
		}

		public HeapItem(HeapNode node, int key, String info){
			this.node = node;
			this.key = key;
			this.info = info;
		}

		public void set_node(HeapNode node) {
			this.node = node;
		}

		public int getKey(){
			return this.key;
		}

		public String toString(){
			return "(HeapItem) <"+this.key+", "+this.info+">";
		}

		public String toString2(){
			return "(HeapNode's values) <"+this.key+", "+this.info+">";
		}

		public boolean equals(HeapItem item){
			return this.node == item.node && this.key == item.key && this.info == item.info;
		}
	}
}
