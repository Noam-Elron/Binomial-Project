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
	 * Time complexity O(logn) as operation is O(1) + meld which is O(logn)
	 */

	public HeapItem insert(int key, String info)
	{
		HeapItem b_0_heapitem = new HeapItem(key, info);
		HeapNode b_0_heapnode = new HeapNode(b_0_heapitem);
		b_0_heapitem.set_node(b_0_heapnode);
		BinomialHeap B0 = new BinomialHeap(b_0_heapnode);
		this.meld(B0);

		return b_0_heapitem;
	}

	/**
	 *
	 * Delete the minimal item
	 * Separates the heap into two by finding the minimum, removing it from the heap and then creating a new heap from its
	 * children(also finds the minimum for the new children heap), then melds the children heap together
	 * with the original heap that had its minimum removed.
	 */
	public void deleteMin()
	{
		if (this.empty()) {
			return;
		}
		if (this.size == 1) {
			this.min = null;
			this.first = null;
			this.last = null;
			this.size--;
			this.num_trees--;
			return;
		}


		HeapNode old_min = this.min;
		InputLinkedList heap_wrapper = new InputLinkedList(this);
		this.size -= old_min.get_size();
		this.num_trees--;
		heap_wrapper.remove_from_list(min);

		this.min = heap_wrapper.find_minimum();
		this.first = heap_wrapper.first;
		this.last = heap_wrapper.last;
		//System.out.println("New minimum: " + min);
		//System.out.println("Printing heap after removing min");
		//System.out.println(this);
		if (old_min.rank > 0) { // if it has children
			InputLinkedList children_list = new InputLinkedList(old_min.child.next, old_min.child); // Min child is max degree child so needs to be last, last points to first so min.child.next is first
			HeapNode orphaned_min = children_list.find_minimum();
			int num_children = children_list.orphan_children();
			BinomialHeap children = new BinomialHeap(old_min.get_size() - 1, num_children, children_list.first, children_list.last, orphaned_min);
			//System.out.println("Printing children heap");
			//System.out.println(children);
			meld(children);
		}
	}

	/**
	 *
	 * Return the minimal HeapItem, null if empty.
	 * O(1), simply returns appropriate field
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
	 * Operates by sifting up the node that had its key decreased as long as its key is smaller than its parents key in order to preserve
	 * Min heap property, this is done by simply swapping the current node we're on's item with its parent's item.
	 * O(logn) due the max tree rank being log(n) thus height being at most log(n), at most log(n) iterations each costing O(1).
	 */
	public void decreaseKey(HeapItem item, int diff)
	{
		if (!this.empty()) {
			item.key -= diff;
			int key = item.key;
			HeapNode node = item.node;

			while (node.parent != null && (key < node.parent.item.key)) {
				HeapItem temp = node.parent.item;
				node.parent.item = node.item;
				node.item = temp;
				node = node.parent;
			}
			if (key < this.min.item.key) {
				this.min = node;
			}

		}


	}

	/**
	 *
	 * Delete the item from the heap.
	 * Simply decreases node to be delete's key to value such that its less than minimum, thus ensuring it becomes the new minumum then deletes the new minimum thus deleting it
	 * O(logn), O(logn) for decrease_key + O(logn) for delete_min.
	 */
	public void delete(HeapItem item)
	{
		if (this.empty()) {
			return;
		}
		this.decreaseKey(item, item.key - this.min.item.key + 1);
		this.deleteMin();
		return;
	}


	/**
	 *
	 * Simple check to see if one of the heaps is empty and performing appropriate option, this is a helper function for meld.
	 * Time complexity: O(1).
	 */
	public boolean empty_heaps(BinomialHeap other_heap) {
		if (this.empty() && other_heap.empty()) {
			return true;
		}
		else if (this.empty()) {
			this.min = other_heap.min;
			this.first = other_heap.first;
			this.last = other_heap.last;
			this.num_trees = other_heap.num_trees;
			this.size = other_heap.size();
			return true;
		}
		else if (other_heap.empty()) {
			return true;
		}

		return false;
	}

	/**
	 *
	 * Meld the heap with heap2
	 * Melds the current heap with another heap. Most of its work is perfomed by Helper class Melder, meld first melds together the
	 * two heaps up to the point that we reach the end of one, this is performed by meld_till_exhausted,
	 * then if we exhausted both lists updates appropriate values and returns, or if there’s still nodes left over melds the
	 * remaining nodes to the resulting list, this is performed by meld_unexhausted_with_carry
	 *
	 * Time complexity: O(logn) specifically the logn being the rank of the largest tree in the smaller heap+number of connections needed after exhausting one of the heaps,
	 * Meld_till_exhausted iterates over the heaps, with every iteration increasing the rank its looking at by atleast one thus performing at most
	 * rank of biggest tree in smaller heap iterations, with every iteration performing constant time operations of either swapping nodes or linking trees or both.
	 * Then at most iterates over remaining heap after exhausting one.
	 */
	public void meld(BinomialHeap heap2) {
		if (empty_heaps(heap2)) {
			return;
		}

		this.size = this.size + heap2.size;
		this.min = return_true_min(heap2);
		Melder meld_operator = new Melder(this, heap2);


		//System.out.println("Before melding till exhausted");
		meld_operator.meld_till_exhausted();
		//System.out.println("After melding till exhausted");
		//System.out.println("Carry: " + meld_operator.getCarry());
		//System.out.println("Heap 1 finished iterating: " + meld_operator.get_first_heap().finished_iterating());
		//System.out.println("Heap 2 finished iterating: " + meld_operator.get_second_heap().finished_iterating());
		if(meld_operator.get_first_heap().finished_iterating() && meld_operator.get_second_heap().finished_iterating()) { // carry exist in this case
			//System.out.println("Finished iterating both lists only carry remains");

			meld_operator.result.add(meld_operator.getCarry());
			meld_operator.result.last = meld_operator.result.cur();
			meld_operator.result.remove_sentinel();

			this.first = meld_operator.getResult().first;
			this.last = meld_operator.result.last;
			this.last.next = this.first;
			this.num_trees = this.num_trees + heap2.num_trees - meld_operator.trees_merged;
			return;
		}
		//System.out.println("A list is unexhausted");
		ResultLinkedList result = meld_operator.meld_unexhausted_with_carry();
		//System.out.println("Completely finished");

		this.first = result.first;
		this.last = result.last;
		this.num_trees = this.num_trees + heap2.num_trees - meld_operator.trees_merged;

	}

	/**
	 *
	 * Simply returns the minimum with the smaller key value.
	 * Time Complexity: O(1)
	 */
	protected HeapNode return_true_min(BinomialHeap other_heap) {
		min = (this.min.item.key <= other_heap.min.item.key) ?  this.min : other_heap.min;
		return min;
	}



	/**
	 *
	 * Return the number of elements in the heap
	 * Time Complexity: O(1)
	 */
	public int size()
	{
		return this.size;
	}

	/**
	 *
	 * The method returns true if and only if the heap is empty.
	 * Time Complexity: O(1)
	 */
	public boolean empty()
	{
		return this.size == 0;
	}

	/**
	 *
	 * Return the number of trees in the heap.
	 * Time Complexity: O(1)
	 */
	public int numTrees()
	{
		return this.num_trees;
	}



	/**
	 * Class implementing a node in a Binomial Heap.
	 *
	 */
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
			this.next = this;
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

		/**
		 *
		 * A node's size(aka number of nodes in its subtree) can be calculated by 2^k, this is an axiom/definition of a binomial tree
		 * Time Complexity: O(1)
		 */
		public int get_size() {
			return (int)Math.pow(2, this.rank);
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

		/**
		 *
		 * Sets an item's node. Only used in BinomialHeap.insert
		 * Time Complexity: O(1)
		 *
		 */
		public void set_node(HeapNode node) {
			this.node = node;
		}

		/**
		 *
		 * Returns HeapItem's key. not really useful or perhaps not even used as key was declared public in given project base file.
		 * Time Complexity: O(1)
		 *
		 */
		public int getKey(){
			return this.key;
		}

		public String toString(){
			return "key: "+this.key+", info: "+this.info;
		}

	}
}
