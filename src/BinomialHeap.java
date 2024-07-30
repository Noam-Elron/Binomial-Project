/**
 * BinomialHeap
 *
 * An implementation of binomial heap over positive integers.
 *
 */

import java.util.HashSet;
import java.util.Set;


public class BinomialHeap
{

	public class Print{
		public static void print_r(BinomialHeap hi) {
			int s;
			if(hi.getLast().getRank() == 0){s = 1;}
			else if(hi.getLast().getRank() == 1){s = 2;}
			else if(hi.getLast().getRank() == 2){s = 3;}
			else {
				s = (int) Math.pow(2, hi.getLast().getRank() - 1);
			}
			int[][][] answer = new int[hi.numTrees()][s][s];
			BinomialHeap.HeapNode curr_tree = hi.getLast().getNext();
			for (int k = 0; k < hi.numTrees(); k++ ) {
				Set<BinomialHeap.HeapNode> dic = new HashSet<>();
				print_rec(curr_tree, 0, 0, dic, answer, k);
				curr_tree = curr_tree.getNext();
			}
			int x = 0;
			for (int i = 0; i < answer[x].length; i++) {
				for (int j = 0; j < answer[x].length; j++) {
					if(answer[x][i][j] != 0) {
						System.out.print(answer[x][i][j] + " ");
					}
					else{System.out.print("--");}
					if(x == answer.length-1 && j == answer[x].length-1) {break;}
					if(j == answer[x].length-1) {
						if (x + 1 < answer.length) {x += 1;}
						j = -1;
					}
				}
				System.out.println();
				x = 0;
			}
		}
		public static void print_rec(BinomialHeap.HeapNode first, int depth, int x, Set<BinomialHeap.HeapNode> dic, int[][][] answer, int num_in_row) {
			if (dic.contains(first)){return;}
			dic.add(first);
			answer[num_in_row][depth][x] = first.getItem().getKey();
			if(depth != 0) {
				if(x == 2 && depth == 1) {print_rec(first.getNext(), depth, x + 2, dic, answer, num_in_row);}
				else if (x == 4 && depth == 1) {print_rec(first.getNext(), depth, x + 4, dic, answer, num_in_row);}
				else if (x == 10 && depth == 2) {print_rec(first.getNext(), depth, x + 2, dic, answer, num_in_row);}
				else {
					print_rec(first.getNext(), depth, x + 1, dic, answer, num_in_row);
				}
			}
			if(first.getChild() != null){
				print_rec(first.getChild().getNext(),depth+1,x,dic,answer, num_in_row);
			}
		}
	}


	public static void main(String[]args){
		BinomialHeap heap = new BinomialHeap();
		heap.insert(12, "geut");
		//heap.insert(8, "geut");
		System.out.println("heap last: "+heap.last);
		System.out.println("heap last next: "+heap.last.next);
		//heap.insert(5, "geut");
		//heap.insert(1, "geut");
		//heap.insert(2, "geut");
		//heap.insert(9, "geut");
		Print.print_r(heap);
		System.out.println("heap.last.next: "+heap.last.next);
		System.out.println("heap.last: "+heap.last);
		System.out.println("heap.last.child: "+heap.last.child);
		System.out.println("heap.last.child.child: "+heap.last.child.child);
		System.out.println("heap.last.child.next: "+heap.last.child.next);
	}

	public int size;
	public HeapNode last;
	public HeapNode min;

	public BinomialHeap(){
		this.size = 0;
		this.last = new HeapNode();
		this.last.next = this.last;
		this.min = new HeapNode();
	}

	public BinomialHeap(HeapNode node){
		this.size = 1;
		this.last = node;
		this.min = node;
	}

	public HeapNode getLast(){
		return this.last;
	}
/* doesnt work
	public void displayHeap()
	{
		System.out.print("\nHeap : ");
		displayHeapRec(this.last.next, this.last.next, false);
		System.out.println("\n");
	}

	private void displayHeapRec(HeapNode r, HeapNode firstInRow, boolean wasFirst){
		if (r!=null) {
			if (!r.equals(firstInRow) || !wasFirst) {
				System.out.println("r: "+r);
				System.out.println("firstInRow: "+firstInRow);
				if (r == firstInRow) {
					wasFirst = true;
				}
				displayHeapRec(r.child, r.child, false);
				System.out.print(r.item + " ");
				if(r.rank!=-1) {
					displayHeapRec(r.next, r, wasFirst);
				}
			}
		}
	}
 */

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info) 
	{
		HeapItem hi = new HeapItem(new HeapNode(), key, info);
		HeapNode hn = new HeapNode(hi, new HeapNode(), new HeapNode(), new HeapNode());
		hn.next = hn;
		hi.node = hn;
		BinomialHeap B0 = new BinomialHeap(hn);


		this.meld(B0);

		return hi;
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
		System.out.println("melding");
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
				System.out.println("1");
				HeapNode temp = p1.next;
				p1 = this.link(p1, p2);
				System.out.println("p1 after link: "+p1);
				if(temp.rank != -1) {
					System.out.println("temp is nullish");
					p1.next = p1;
				}
				else{
					p1.next = p1;
				}
				p1 = p1.next; // we would like it to be the new "last"
			}
			else{ // add node
				System.out.println("2");
				HeapNode temp = heap2.last;
				if (this.last.rank!=-1) {
					System.out.println("3");
					temp = p1.next; // saving pointer to what's going to be the next node in the end of the day
				}
				else{
					System.out.println("4");
					temp.rank = 0;
				}
				p1.next = p2; // adding the current node in heap2 to this.heap - unfortunately with a "tail" of other nodes
				p1 = p1.next;
				p1.next = temp; // no more tail of unwanted nodes!
				p1 = p1.next; // we would like it to be the new "last"
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
		int count = 1;
		HeapNode p = this.last.next;
		while (p!=this.last){
			p = p.next;
			count++;
		}

		return count;
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
		y.next = x.child;
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
			this.item = new HeapItem();
			this.child = null;
			this.next = null;
			this.parent = null;
			this.rank = -1;
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

		public HeapItem(HeapNode node, int key, String info){
			this.node = node;
			this.key = key;
			this.info = info;
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