/**
 * BinomialHeap
 *
 * An implementation of binomial heap over positive integers.
 *
 */
public class BinomialHeap
{

	public int size;
	public HeapNode first;
	public HeapNode last;
	public HeapNode min;
	public int num_trees;

	public BinomialHeap(){
		this.size = 0;
		this.num_trees = 0;
		this.first = null;
		this.last = null;
		this.min = null;
	}

	public BinomialHeap(HeapNode node){
		this.size = 1;
		this.num_trees = 1;
		this.first = node;
		this.last = node;
		this.min = node;
	}

	public BinomialHeap(int size, int num_trees, HeapNode first, HeapNode last, HeapNode min){
		this.size = size;
		this.num_trees = num_trees;
		this.first = first;
		this.last = last;
		this.min = min;
	}

	public HeapNode getLast(){
		return this.last;
	}

	public String toString() {
		if (this.empty()) {
			return "Heap is empty";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("BinomialHeap\n");

		BinomialHeap.HeapNode current = this.last;
		do {
			if (current != null) {
				sb.append("Tree with root: ").append(current.toString()).append("\n");
				appendTree(sb, current, "", true);
				current = current.next;
			}
		} while (current != this.last);

		return sb.toString();
	}

	private void appendTree(StringBuilder sb, BinomialHeap.HeapNode node, String indent, boolean last) {
		if (node == null) return;

		sb.append(indent);
		if (last) {
			sb.append("└── ");
			indent += "    ";
		} else {
			sb.append("├── ");
			indent += "│   ";
		}
		sb.append(node.toString()).append("\n");

		if (node.child != null) {
			BinomialHeap.HeapNode child = node.child;
			do {
				appendTree(sb, child, indent, child.next == node.child);
				child = child.next;
			} while (child != node.child);
		}
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
			this.first = b_0_heapnode;
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
		HeapNode p = this.first;
		while(p.next!=this.min){
			p = p.next;
		}
		HeapNode first = this.min.child;
		p.next = p.next.next;

		int num_trees = 1;
		HeapNode min_node = first;
		HeapNode p2 = first.next;
		int size = (int)Math.pow(first.rank, 2);
		int min = min_node.item.key;
		while(p2.next!=first){
			num_trees++;
			size += (int)Math.pow(p2.rank, 2);
			if (p2.item.key<min){
				min_node = p2;
				min = p2.item.key;
			}
		}

		num_trees++;
		size += (int)Math.pow(p2.rank, 2);
		if (p2.item.key<min){
			min_node = p2;
		}

		BinomialHeap heap2 = new BinomialHeap(size, num_trees, first, p2, min_node);

		this.size--;
		this.num_trees--;

		this.meld(heap2);
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
		item.key -= diff;
		while (item.key < item.node.parent.item.key) {
			HeapItem temp = item.node.parent.item;
			item.node.parent.item = item;
			item.node.item = temp;
		}
	}

	/**
	 *
	 * Delete the item from the heap.
	 *
	 */

	public void delete(HeapItem item)
	{
		this.decreaseKey(item, -Integer.MAX_VALUE);
		this.deleteMin();
		return;
	}

	/**
	 *
	 * Meld the heap with heap2
	 *
	 */

	public boolean empty_heaps(BinomialHeap other_heap) {
		if (this.size == 0 && other_heap.size == 0) {
			return true;
		}
		if (this.size == 0) {
			this.min = other_heap.min;
			this.last = other_heap.last;
			this.num_trees = other_heap.num_trees;
			return true;
		}
		if (other_heap.size == 0) {
			return true;
		}
		return false;
	}

	public void meld(BinomialHeap heap2)
	{
		if (empty_heaps(heap2)) {
			return;
		}


		this.num_trees = this.num_trees + heap2.num_trees;
		this.size = this.size + heap2.size;
		this.min = return_true_min(heap2);

		HeapNode h1_cur_node = this.first;
		HeapNode h2_cur_node = heap2.first;

		this.last.next = null; // disconnect lists for easier navigation
		heap2.last.next = null;

		HeapNode carry = new HeapNode();


		HeapNode[] result = new HeapNode[this.num_trees];
		int cur = 0;



		while(h1_cur_node != null && h2_cur_node != null) {
			System.out.println();
			System.out.println("h1: " + h1_cur_node);
			System.out.println("h2: " + h2_cur_node);

			HeapNode h1_next = h1_cur_node.next;
			HeapNode h2_next = h2_cur_node.next;

			if (h1_cur_node.rank == h2_cur_node.rank) {
				System.out.println("h1 and h2 cur node ranks are the same, linking them together");

				if (carry.rank != -1) {
					int min_key = Math.min(Math.min(h1_cur_node.item.key, h2_cur_node.item.key), carry.item.key);
					if (carry.item.key != min_key) {
						if (h1_cur_node.item.key == min_key) {
							HeapNode temp = carry;
							carry = h1_cur_node;
							h1_cur_node = temp;
							h1_cur_node.next = h1_next;
						}
						else {
							HeapNode temp = carry;
							carry = h2_cur_node;
							h2_cur_node = temp;
							h2_cur_node.next = h2_next;
						}
					}
					System.out.println("carry already exists so adding to result");
					result[cur] = carry;
					cur++;
				}

				carry = link(h1_cur_node, h2_cur_node);
				this.num_trees--;
				h1_cur_node = h1_next;
				h2_cur_node = h2_next;

			}

			else if (h1_cur_node.rank == carry.rank) { // Means h1.rank != h2.rank, thus h2.rank must be bigger because carry.rank <= h1.rank, h2.rank
				System.out.println("Linking carry and h1_cur_node");
				carry = link(h1_cur_node, carry);
				h1_cur_node = h1_next;
			}
			else if (h2_cur_node.rank == carry.rank) { // Means h1.rank != h2.rank, thus h1.rank must be bigger because carry.rank <= h1.rank, h2.rank
				System.out.println("Linking carry and h2_cur_node");
				carry = link(h2_cur_node, carry);
				h2_cur_node = h2_next;
			}

			else if (carry.rank != -1 && (carry.rank < h1_cur_node.rank || carry.rank < h2_cur_node.rank)) {
				System.out.println("Carry smaller than both nodes so putting in result");
				result[cur] = carry;
				cur++;
				carry = new HeapNode();
			}

			else if (h1_cur_node.rank < h2_cur_node.rank) {
				System.out.println("h1 rank smaller than h2 rank so adding h1 to result");
				result[cur] = h1_cur_node;
				cur++;
				h1_cur_node = h1_next;
			}

			else if (h2_cur_node.rank < h1_cur_node.rank) {
				System.out.println("h2 rank smaller than h1 rank so adding h2 to result");
				result[cur] = h2_cur_node;
				cur++;
				h2_cur_node = h2_next;
			}

		}


		System.out.println("\nFinished iterating over one of the heaps\n");

		if(h1_cur_node == null && h2_cur_node == null) { // carry exist in this case
			result[cur] = carry;
			this.first = result[0];
			this.last = carry;
			this.last.next = this.first;
			return;
		}

		HeapNode final_node = h1_cur_node == null ? heap2.last : this.last;
		HeapNode remaining = h1_cur_node == null ? h2_cur_node : h1_cur_node;
		System.out.println("carry: " + carry);
		System.out.println("remaining: " + remaining);
		while (carry.rank != -1 && remaining != null) {

			HeapNode next = remaining.next;
			if (carry.rank < remaining.rank) {
				carry.next = remaining;
				result[cur] = carry;
				cur++;
				break;
			} else {
				System.out.println("Linking carry and remainder: ");
				System.out.println("carry: " + carry);
				System.out.println("remaining: " + remaining);

				HeapNode temp = carry;
				carry = link(carry, remaining);
				if (remaining == final_node) {
					System.out.println("updating last node to: " + carry);
					final_node = carry;
				}
				if (temp == first) {
					System.out.println("updating last node to: " + carry);
					first = carry;
				}
				remaining = next;
			}
		}

		if (remaining == null && carry.rank != -1) {
			remaining = carry;
		}
		System.out.println("Cur: " + cur);
		System.out.println("carry: " + carry);
		System.out.println("remaining: " + remaining);
		while (remaining != null) {
			result[cur] = remaining;
			cur++;
			remaining = remaining.next;
		}

		for (int i = 0; i < cur; i++) {
			result[i].next = result[(i + 1) % cur];
		}


		//System.out.println("Cur: " + cur);
		this.first = result[0];
		this.last = result[cur-1];
		this.last.next = this.first;
		//System.out.println("this min:" + this.min);
		//System.out.println("this last:" + this.last);
		//System.out.println("\n\n");
	}



	/**
	 *
	 * @param other_heap heap to compare minimum values with
	 * @return minimum node between _this_ and other_heap
	 */

	protected HeapNode return_true_first(BinomialHeap other_heap) {
		if (this.first.rank == other_heap.first.rank) {
			first = (this.first.item.key < other_heap.first.item.key) ?  this.first : other_heap.first;
		}
		else {
			first = (this.first.rank < other_heap.min.rank) ? this.first : other_heap.first;
		}
		return first;
	}

	protected HeapNode return_true_min(BinomialHeap other_heap) {
		min = (this.min.item.key < other_heap.min.item.key) ?  this.min : other_heap.min;
		return min;
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
		x.rank = x.rank + 1 ;
		y.next = y;
		if (x.child != null) {
			HeapNode smallest = x.child.next; // smallest degree in list
			System.out.println("\nUpdating: " + y + " to point to: " + smallest + "\n");
			x.child.next = y; // x's biggest child points to y as y has biggest degree
			y.next = smallest;
		}
		y.parent = x; // y's new parent(previously was null) is now x

		x.child = y; // y is x's new smallest degree child
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
		public int size;

		// Empty/Virtual HeapNode
		public HeapNode(){
			this.item = new HeapItem();
			this.child = null;
			this.next = null;
			this.parent = null;
			this.size = 0;
			this.rank = -1;
		}

		// B_0 HeapNode, used in insert to create a B_0 Heap so no parent, child or next.
		public HeapNode(HeapItem item){
			this.item = item;
			this.child = null;
			this.next = this;
			this.parent = null;
			this.size = 1;
			this.rank = 0;
		}

		public HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent){
			this.item = item;
			this.child = child;
			this.next = next;
			this.parent = parent;
			this.size = 1 + child.size;
			this.rank = 0;
		}

		public String toString(){
			String child = "null";
			String next = "null";
			String parent = "null";

			if(this.child != null){
				child = this.child.item.toString();
			}
			if(this.next != null){
				next = this.next.item.toString();
			}
			if(this.parent != null){
				parent = this.parent.item.toString();
			}

			return "(HeapNode) <"+this.item+">, child: "+child+", parent: "+parent+", next: <"+next+">, rank: "+this.rank+">";
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
			return "key: "+this.key+", info: "+this.info;
		}

		public String toString2(){
			return "(HeapNode's values) <"+this.key+", "+this.info+">";
		}

		public boolean equals(HeapItem item){
			return this.node == item.node && this.key == item.key && this.info == item.info;
		}
	}
}
