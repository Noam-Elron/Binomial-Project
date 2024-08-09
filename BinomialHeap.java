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
        if (old_min.rank > 0) { // if it has children
            InputLinkedList children_list = new InputLinkedList(old_min.child.next, old_min.child); // Min child is max degree child so needs to be last, last points to first so min.child.next is first
            HeapNode children_min = children_list.find_minimum();
            int num_children = children_list.remove_parent();
            BinomialHeap children = new BinomialHeap(old_min.get_size() - 1, num_children, children_list.first, children_list.last, children_min);
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


        meld_operator.meld_till_exhausted();
        if(meld_operator.get_first_heap().finished_iterating() && meld_operator.get_second_heap().finished_iterating()) { // carry exist in this case

            meld_operator.result.add(meld_operator.getCarry());
            meld_operator.result.last = meld_operator.result.cur();
            meld_operator.result.remove_sentinel();

            this.first = meld_operator.getResult().first;
            this.last = meld_operator.result.last;
            this.last.next = this.first;
            this.num_trees = this.num_trees + heap2.num_trees - meld_operator.trees_merged;
            return;
        }
        ResultLinkedList result = meld_operator.meld_unexhausted_with_carry();

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
            this.info = "null value";
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


/**
 *
 * A helper/wrapper class for the meld operation, performs most of the work behind the scenes.
 *
 *
 */
class Melder {
    BinomialHeap.HeapNode carry = new BinomialHeap.HeapNode(); // Virtual Carry
    ResultLinkedList result = new ResultLinkedList();
    InputLinkedList first_heap;
    InputLinkedList second_heap;
    int trees_merged = 0;

    /**
     *
     * Takes in two heaps and converts them to an easier to work with format(InputLinkedList which is a wrapper for a BinomialHeap)
     * and to make iteration easier disconnects the last node from the first in order to properly determine when iteration is finished.
     *
     */
    public Melder(BinomialHeap heap1, BinomialHeap heap2) {
        first_heap = new InputLinkedList(heap1);
        second_heap = new InputLinkedList(heap2);
        first_heap.disconnect_tail_from_head();
        second_heap.disconnect_tail_from_head();
    }

    /**
     *
     * @pre Meld is instantiated with two non empty heaps
     *
     * Melds two heaps until one runs out. Iterates over available ranks in either heaps+carry and with each iteration returns all trees of the same rank were
     * currently iterating over. Determines what to do based on how many nodes of the same rank we have+whether one of the nodes of the same rank is a carry or
     * not.
     *
     * Time Complexity: O(logn), or more precisely at most performs rank of the largest tree in the smaller heap iterations, as with every iteration we
     * increase the rank were looking at by atleast 1(possibly much more than that) and with each iteration performs O(1) operations, thus
     * time complexity is as described.
     *
     */
    public void meld_till_exhausted() {
        while(!first_heap.finished_iterating() && !second_heap.finished_iterating()) {
            BinomialHeap.HeapNode[] same_ranks = next_rank_array();
            int num_of_same_rank = 0;
            while(num_of_same_rank < 3 && same_ranks[num_of_same_rank] != null) {
                num_of_same_rank++;
            }

            switch (num_of_same_rank) {
                case 3:
                    swap_carry_with_minimum_key();
                    result.add(carry);
                    first_heap.next();
                    second_heap.next();
                    carry = link(first_heap.get_previous(), second_heap.get_previous());
                    trees_merged++;
                    break;


                case 2:
                    if(same_ranks[0] == carry && same_ranks[1] == first_heap.cur()) { // Order of operations in same_ranks dictates result.first is carry second is diffrent node.
                        first_heap.next();
                        carry = link(carry, first_heap.get_previous());
                    }
                    else if(same_ranks[0] == carry && same_ranks[1] == second_heap.cur()) { // Order of operations in same_ranks dictates result.first is carry second is diffrent node.
                        second_heap.next();
                        carry = link(carry, second_heap.get_previous());
                    }

                    else {
                        first_heap.next();
                        second_heap.next();
                        carry = link(first_heap.get_previous(), second_heap.get_previous());
                    }

                    trees_merged++;

                    break;


                case 1:
                    BinomialHeap.HeapNode result_node;
                    if (same_ranks[0] == carry) {
                        result_node = carry;
                        carry = new BinomialHeap.HeapNode();
                    }
                    else if (same_ranks[0] == first_heap.cur()) {
                        result_node = first_heap.cur();
                        first_heap.next();
                    }
                    else {
                        result_node = second_heap.cur();
                        second_heap.next();
                    }

                    result.add(result_node);
                    break;


            }
        }
    }

    /**
     *
     * @pre a singular heap is unexhausted, must be called after meld_till_exhausted and conditional check that at least one of the heaps
     * is still not empty. BinomialHeap.meld does as required by the pre condition.
     *
     * Takes the unexhausted heap, and continues melding it together with the carry node from melding_till_exhausted, finally when carry doesnt exist or
     * we've exhausted the heap links the rest of the nodes to the resulting list in O(1) time thanks to pointers.
     *
     * Time Complexity: O(m) where m is size of remaining trees in heap.
     *
     */
    public ResultLinkedList meld_unexhausted_with_carry() {
        InputLinkedList unexhausted = !first_heap.finished_iterating() ? first_heap : second_heap;
        while (carry.rank != -1 && !unexhausted.finished_iterating()) {
            if (carry.rank < unexhausted.cur().rank) {
                result.add(carry);
                carry = new BinomialHeap.HeapNode();
                break;
            }
            else {
                unexhausted.next();
                carry = link(carry, unexhausted.get_previous());
                trees_merged++;
                if (unexhausted.prev == unexhausted.last) {
                    unexhausted.last = carry;
                }
            }
        }

        if (unexhausted.finished_iterating() && carry.rank != -1) {
            result.add(carry);
        }

        if(!unexhausted.finished_iterating()) {
            result.add(unexhausted.cur);
            result.last = unexhausted.last;
            result.cur = result.last;
        }
        result.last = result.cur;
        result.remove_sentinel();
        return result;
    }

    /**
     *
     * Returns all trees(at most one from each) of the same rank from heap1, heap2 and carry
     *
     * Time complexity: O(1) for constant time operations,
     */
    public BinomialHeap.HeapNode[] next_rank_array() {
        BinomialHeap.HeapNode[] res = new BinomialHeap.HeapNode[3];
        int cur = 0;
        int min_heaps_rank = Math.min(first_heap.cur_rank(), second_heap.cur_rank());
        // Notice that this way if carry exists its always the first one in the resulting list.
        if (carry.rank != -1 && carry.rank <= min_heaps_rank) { // carry.rank is always less than or equal if it exists so in fact that carry.rank<=min_heaps_rank is unneeded
            min_heaps_rank = carry.rank;
            res[cur] = carry;
            cur++;
        }
        if (first_heap.cur_rank() == min_heaps_rank) {
            res[cur] = first_heap.cur();
            cur++;
        }
        if (second_heap.cur_rank() == min_heaps_rank) {
            res[cur] = second_heap.cur();
        }

        return res;


    }

    /**
     *
     * @pre carry is valid and list must be non-exhausted, aka list.cur() must not be null.
     * Preconditions are met as this method is only called inside meld_till_exhausted where three nodes of the same rank exist, thus
     * carry is valid and list being swapped is non-exhausted
     *
     * Swaps the node from the given list/heap with the carry, updating appropriate pointers so nothing is ruined.
     * Time Complexity: O(1) for constant time operations
     *
     */
    public void swap_with_carry(InputLinkedList list) {
        BinomialHeap.HeapNode temp = list.cur();
        carry.next = temp.next;
        list.cur = carry;

        carry = temp;
        carry.next = null; //new carry's pointer is null
    }

    /**
     *
     * @pre carry is valid and both lists must be non-exhausted, aka heap1.cur() and heap2.cur must not be null
     * Swaps the current tree with the smallest key with carry, This is used for when 3 trees of the same rank exist.
     *
     * Time Complexity: O(1) for constant time operations
     *
     */
    public void swap_carry_with_minimum_key() {
        int min_key = Math.min(Math.min(first_heap.cur_key(), second_heap.cur_key()), carry.item.key);
        if (carry.item.key != min_key && first_heap.cur_key() == min_key) {
            swap_with_carry(first_heap);
        }
        else if (carry.item.key != min_key && second_heap.cur_key() == min_key) {
            swap_with_carry(second_heap);
        }

    }

    /**
     *
     * @pre: x,y are Binomial tree header nodes
     * @pre: Trees with x,y as header are same degree( B_k and B_k )
     *
     * Static meld between two trees of same size
     * Time Complexity: O(1) for constant time operations
     *
     */
    public BinomialHeap.HeapNode link(BinomialHeap.HeapNode x, BinomialHeap.HeapNode y) {
        if (x.item.key > y.item.key || (x.item.key == y.item.key && y == first_heap.get_min())) { // Unsure if >= or > because maybe duplicate keys?
            BinomialHeap.HeapNode temp = x;
            x = y;
            y = temp;
        }

        x.rank = x.rank + 1 ;
        y.next = y;
        if (x.child != null) {
            BinomialHeap.HeapNode smallest = x.child.next; // smallest degree in list
            x.child.next = y; // x's biggest child points to y as y has biggest degree
            y.next = smallest;
        }
        y.parent = x; // y's new parent(previously was null) is now x

        x.child = y; // y is x's new smallest degree child
        return x;
    }

    /**
     *
     * Getter for first heap
     * Time Complexity: O(1) for constant time operations
     *
     */
    public InputLinkedList get_first_heap() {
        return first_heap;
    }

    /**
     *
     * Getter for second heap
     * Time Complexity: O(1) for constant time operations
     *
     */
    public InputLinkedList get_second_heap() {
        return second_heap;
    }

    /**
     *
     * Getter for result heap
     * Time Complexity: O(1) for constant time operations
     *
     */
    public ResultLinkedList getResult() {
        return result;
    }

    /**
     *
     * Getter for carry
     * Time Complexity: O(1) for constant time operations
     *
     */
    public BinomialHeap.HeapNode getCarry() {
        return carry;
    }
}

/**
 *
 * Generic wrapper class for a BinomialHeap, abstracts operations for ease of use,
 *
 */
class BinomialList {
    protected BinomialHeap.HeapNode first;
    protected BinomialHeap.HeapNode last;
    protected BinomialHeap.HeapNode prev;
    protected BinomialHeap.HeapNode cur;


    public BinomialList() { // Perhaps one single reason why interface would be better as this is unneeded for InputLinkedList
    }

    public BinomialList(BinomialHeap heap) {
        this.first = heap.first;
        this.prev = first;
        this.cur = first;
        this.last = heap.last;
    }

    /**
     * @ pre The list must have at least one node and first and last pointers must be correctly set
     *
     * For ease of iteration, whenever iterating over a BinomialList(or subsequent subclasses) we disconnect the tail from end in order to easiy
     * know when we're done iterating. After iterating we undo our operation by reconnecting them
     * Time Complexity: O(1) for constant time operations
     *
     */
    public void connect_tail_to_head() {
        this.last.next = this.first;
    }

    /**
     *
     * @pre: Function disconnect_tail_from_head() was performed on this.
     *
     * Abstracting function to know when we're done iterating
     * Time Complexity: O(1) for constant time operations
     *
     */
    public boolean finished_iterating() {
        return cur == null;
    }

    /**
     * Returns the current node we're iterating over and updates cur and prev to the next nodes in the list
     * @pre list is unexhausted(aka finished_iterating() == false)
     * @return returns current node
     * Time Complexity: O(1) for constant time operations
     */
    public BinomialHeap.HeapNode next() {
        prev = cur;
        cur = cur.next;
        return prev;
    }

    /**
     *
     * Getter function to return current node.
     * @return: current node.
     * Time Complexity: O(1) for constant time operations
     *
     */
    public BinomialHeap.HeapNode cur() {
        return cur;
    }

    /**
     *
     * Getter function to return previous node.
     * @return: prev node
     * Time Complexity: O(1) for constant time operations
     *
     */
    public BinomialHeap.HeapNode get_previous() {
        return prev;
    }

    /**
     * Getter function to return cur node rank.
     * @return rank of the current node in the list
     */
    public int cur_rank() {
        return cur.rank;
    }

    /**
     * Getter function to return cur node key.
     * @return key of the current node in the list
     */
    public int cur_key() {
        return cur.item.getKey();
    }

    /**
     *
     * For ease of iteration, whenever iterating over a BinomialList(or subsequent subclasses) we disconnect the tail from end in order to easily
     * know when we're done iterating. After iterating we undo our operation by reconnecting tail and head.
     * Time Complexity: O(1) for constant time operations
     *
     */
    public void disconnect_tail_from_head() {
        this.last.next = null;
    }




}

/**
 * Subclass of BinomialList, specifically used in order to traverse a BinomialHeap. Used in both BinomialHeap.meld inside Melder Class aswell as
 * in delete_min in order to create the children heap.
 */
class InputLinkedList extends BinomialList {
    protected BinomialHeap.HeapNode min_node;
    public InputLinkedList(BinomialHeap heap) {
        super(heap);
        min_node = heap.min;
    }

    public InputLinkedList(BinomialHeap.HeapNode first_node, BinomialHeap.HeapNode last_node) {
        this.first = first_node;
        this.last = last_node;
        this.cur = first_node;
        this.prev = first_node;

    }

    /**
     *
     * Searches for node with minimum key in a linked list, used in delete_min to find min of children of deleted node.
     *
     * Time Complexity: O(m) for iterating over list of size m.
     *
     */
    public BinomialHeap.HeapNode find_minimum() {
        if (this.first == this.last) { // May return null, thats okay.
            return this.first;
        }

        disconnect_tail_from_head();
        int lowest_value_found = first.item.key;
        cur = first; // Reset cur just in case.
        min_node = cur;
        while (!finished_iterating()) {
            if(cur.item.key <= lowest_value_found) {
                min_node = cur;
                lowest_value_found = cur.item.key;
            }
            next();
        }
        connect_tail_to_head();
        cur = first;
        return min_node;
    }

    /**
     *
     * @return number of children(aka trees) in heap of deleted node
     * Disconnects childrens pointer to their parent, used in delete_min
     *
     * Time Complexity: O(m) for iterating over list of size m.
     *
     */
    public int remove_parent() {
        disconnect_tail_from_head();
        int num_children = 0;
        cur = first;
        while (!finished_iterating()) {
            cur.parent = null;
            next();
            num_children++;
        }
        connect_tail_to_head();
        cur = first;
        return num_children;
    }

    /**
     *
     * Removes a node from a linked list(aka changes pointers appropriately)
     *
     * Time Complexity: O(m) worst case if element is last element in list of size m.
     *
     */
    public void remove_from_list(BinomialHeap.HeapNode node) {
        if (this.first==this.last) {
            empty_list();
            return;
        }

        disconnect_tail_from_head();
        if (node == first) {
            this.first = first.next;

        }
        else {
            cur = first;

            while (cur != node) {
                next();
            }
            prev.next = cur.next;
            cur = prev.next;
            if (node == last) {
                this.last = prev;
            }

        }
        connect_tail_to_head();
        cur = first;

    }

    /**
     *
     * Helper function to empty a list
     *
     * Time Complexity: O(1) for constant time operation
     *
     */
    private void empty_list() {
        this.first = null;
        this.last = null;
        this.min_node = null;
        this.prev = null;
        this.cur = null;
    }

    /**
     *
     * Returns minimum in list
     *
     * Time Complexity: O(1) for constant time operation
     *
     */
    public BinomialHeap.HeapNode get_min() {
        return this.min_node;
    }
}

/**
 * Subclass of BinomialList, specifically used in order create the resulting heap after meld.
 */
class ResultLinkedList extends BinomialList {
    public int length = 0;

    public ResultLinkedList() {
        this.first = new BinomialHeap.HeapNode(); // Sentinel
        this.prev = first;
        this.cur = first;
        this.last = null;

    }

    /**
     *
     * @param new_cur: new node in result list<br><br>
     * Description: adds node to the list, updates pointers: <br> previous -> cur(old_one), <br> cur -> new_cur point,<br> <br>
     * Note: Does not modify input node-new_cur's next pointer! <br>
     * Time complexity: O(1) for constant time operations
     */

    public void add(BinomialHeap.HeapNode new_cur) {
        cur.next = new_cur;
        prev = cur;
        cur = cur.next;
        length++;
    }

    /**
     *
     * Helper function to remove a sentinel, this is needed because result list is insantiated with a sentinel in order to help with properly linking
     *
     * Time Complexity: O(1) for constant time operation
     *
     */
    public void remove_sentinel() {
        if (this.first.rank == -1) {
            this.last.next = first.next;
            this.first = this.last.next;
        }
    }

}
