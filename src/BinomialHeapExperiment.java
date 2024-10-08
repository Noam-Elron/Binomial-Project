///**
// * BinomialHeap
// *
// * An implementation of binomial heap over positive integers.
// *
// */
//public class BinomialHeap
//{
//
//    public int size;
//    public HeapNode first;
//    public HeapNode last;
//    public HeapNode min;
//    public int num_trees;
//
//    public BinomialHeap(){
//        this.size = 0;
//        this.num_trees = 0;
//        this.first = null;
//        this.last = null;
//        this.min = null;
//    }
//
//    public BinomialHeap(HeapNode node){
//        this.size = 1;
//        this.num_trees = 1;
//        this.first = node;
//        this.last = node;
//        this.min = node;
//    }
//
//    public BinomialHeap(int size, int num_trees, HeapNode first, HeapNode last, HeapNode min){
//        this.size = size;
//        this.num_trees = num_trees;
//        this.first = first;
//        this.last = last;
//        this.min = min;
//    }
//
//    public String toString() {
//        if (this.empty()) {
//            return "Heap is empty";
//        }
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("BinomialHeap\n");
//
//        BinomialHeap.HeapNode current = this.last;
//        do {
//            if (current != null) {
//                sb.append("Tree with root: ").append(current.toString()).append("\n");
//                appendTree(sb, current, "", true);
//                current = current.next;
//            }
//        } while (current != this.last);
//
//        return sb.toString();
//    }
//
//    private void appendTree(StringBuilder sb, BinomialHeap.HeapNode node, String indent, boolean last) {
//        if (node == null) return;
//
//        sb.append(indent);
//        if (last) {
//            sb.append("└── ");
//            indent += "    ";
//        } else {
//            sb.append("├── ");
//            indent += "│   ";
//        }
//        sb.append(node.toString()).append("\n");
//
//        if (node.child != null) {
//            BinomialHeap.HeapNode child = node.child;
//            do {
//                appendTree(sb, child, indent, child.next == node.child);
//                child = child.next;
//            } while (child != node.child);
//        }
//    }
//
//    /**
//     *
//     * pre: key > 0
//     *
//     * Insert (key,info) into the heap and return the newly generated HeapItem.
//     *
//     */
//
//    public int insert(int key, String info) //only for experiment. return back to HeapItem afterwards
//    {
//        HeapItem b_0_heapitem = new HeapItem(key, info);
//        HeapNode b_0_heapnode = new HeapNode(b_0_heapitem);
//        b_0_heapitem.set_node(b_0_heapnode);
//        BinomialHeap B0 = new BinomialHeap(b_0_heapnode);
//        int trees_merged = this.meld(B0);
//
//        //return b_0_heapitem;
//
//        return trees_merged; //only for experiment.
//    }
//
//    /**
//     *
//     * Delete the minimal item
//     *
//     */
//    public int[] deleteMin() // change back to void, only for experiment.
//    {
//        if (this.empty()) {
//            return new int[]{0, 0};
//        }
//        if (this.size == 1) {
//            this.min = null;
//            this.first = null;
//            this.last = null;
//            this.size--;
//            this.num_trees--;
//            return new int[]{0, 0};
//        }
//
//
//        HeapNode old_min = this.min;
//        int deleted_rank = old_min.rank; //only for experiment.
//        InputLinkedList heap_wrapper = new InputLinkedList(this);
//        this.size -= old_min.get_size();
//        this.num_trees--;
//        heap_wrapper.remove_from_list(min);
//        this.min = heap_wrapper.find_minimum();
//        this.first = heap_wrapper.first;
//        this.last = heap_wrapper.last;
//        //System.out.println("New minimum: " + min);
//        //System.out.println("Printing heap after removing min");
//        //System.out.println(this);
//        int trees_merged = 0;
//        if (old_min.rank > 0) { // if it has children
//            InputLinkedList orphaned_children = new InputLinkedList(old_min.child.next, old_min.child); // Min child is max degree child so needs to be last, last points to first so min.child.next is first
//            HeapNode orphaned_min = orphaned_children.find_minimum();
//            int num_children = orphaned_children.orphan_children();
//            BinomialHeap children = new BinomialHeap(old_min.get_size() - 1, num_children, orphaned_children.first, orphaned_children.last, orphaned_min);
//            //System.out.println("Printing children heap");
//            //System.out.println(children);
//            trees_merged = meld(children);
//        }
//        return new int[]{trees_merged, deleted_rank}; //only for experiment.
//    }
//
//    /**
//     *
//     * Return the minimal HeapItem, null if empty.
//     *
//     */
//    public HeapItem findMin()
//    {
//
//        if (this.min != null) {
//            return this.min.item;
//        }
//        return null;
//    }
//
//    /**
//     *
//     * pre: 0<diff<item.key
//     *
//     * Decrease the key of item by diff and fix the heap.
//     *
//     */
//    public void decreaseKey(HeapItem item, int diff)
//    {
//        item.key -= diff;
//        while (item.key < item.node.parent.item.key) {
//            HeapItem temp = item.node.parent.item;
//            item.node.parent.item = item;
//            item.node.item = temp;
//        }
//    }
//
//    /**
//     *
//     * Delete the item from the heap.
//     *
//     */
//
//    public void delete(HeapItem item)
//    {
//        this.decreaseKey(item, Integer.MAX_VALUE);
//        this.deleteMin();
//        return;
//    }
//
//    public boolean empty_heaps(BinomialHeap other_heap) {
//        if (this.empty() && other_heap.empty()) {
//            return true;
//        }
//        else if (this.empty()) {
//            this.min = other_heap.min;
//            this.first = other_heap.first;
//            this.last = other_heap.last;
//            this.num_trees = other_heap.num_trees;
//            this.size = other_heap.size();
//            return true;
//        }
//        else if (other_heap.empty()) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     *
//     * Meld the heap with heap2
//     *
//     */
//    public int meld(BinomialHeap heap2) { //only for experiment. remove return type after
//        if (empty_heaps(heap2)) {
//            return 0;
//        }
//
//        this.size = this.size + heap2.size;
//        this.min = return_true_min(heap2);
//        Melder meld_operator = new Melder(this, heap2);
//
//
//        //System.out.println("Before melding till exhausted");
//        meld_operator.meld_till_exhausted();
//        //System.out.println("After melding till exhausted");
//        //System.out.println("Carry: " + meld_operator.getCarry());
//        //System.out.println("Heap 1 finished iterating: " + meld_operator.get_first_heap().finished_iterating());
//        //System.out.println("Heap 2 finished iterating: " + meld_operator.get_second_heap().finished_iterating());
//        if(meld_operator.get_first_heap().finished_iterating() && meld_operator.get_second_heap().finished_iterating()) { // carry exist in this case
//            //System.out.println("Finished iterating both lists only carry remains");
//
//            meld_operator.result.add(meld_operator.getCarry());
//            meld_operator.result.last = meld_operator.result.cur();
//            meld_operator.result.remove_sentinel();
//
//            this.first = meld_operator.getResult().first;
//            this.last = meld_operator.result.last;
//            this.last.next = this.first;
//            this.num_trees = this.num_trees + heap2.num_trees - meld_operator.trees_merged;
//
//            return meld_operator.trees_merged; //only for experiment.
//        }
//        //System.out.println("A list is unexhausted");
//        ResultLinkedList result = meld_operator.meld_unexhausted_with_carry();
//        //System.out.println("Completely finished");
//
//        this.first = result.first;
//        this.last = result.last;
//        this.num_trees = this.num_trees + heap2.num_trees - meld_operator.trees_merged;
//
//        return meld_operator.trees_merged; //only for experiment.
//    }
//
//
//    protected HeapNode return_true_min(BinomialHeap other_heap) {
//        min = (this.min.item.key <= other_heap.min.item.key) ?  this.min : other_heap.min;
//        return min;
//    }
//
//
//
//    /**
//     *
//     * Return the number of elements in the heap
//     *
//     */
//    public int size()
//    {
//        return this.size;
//    }
//
//    /**
//     *
//     * The method returns true if and only if the heap
//     * is empty.
//     *
//     */
//    public boolean empty()
//    {
//        return this.size == 0;
//    }
//
//    /**
//     *
//     * Return the number of trees in the heap.
//     *
//     */
//    public int numTrees()
//    {
//        return this.num_trees;
//    }
//
//    /**
//     * Class implementing a node in a Binomial Heap.
//     *
//     */
//
//
//
//    public static class HeapNode{
//        public HeapItem item;
//        public HeapNode child;
//        public HeapNode next;
//        public HeapNode parent;
//        public int rank;
//
//        // Empty/Virtual HeapNode
//        public HeapNode(){
//            this.item = new HeapItem();
//            this.child = null;
//            this.next = null;
//            this.parent = null;
//            this.rank = -1;
//        }
//
//        // B_0 HeapNode, used in insert to create a B_0 Heap so no parent, child or next.
//        public HeapNode(HeapItem item){
//            this.item = item;
//            this.child = null;
//            this.next = this;
//            this.parent = null;
//            this.rank = 0;
//        }
//
//        public HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent){
//            this.item = item;
//            this.child = child;
//            this.next = next;
//            this.parent = parent;
//            this.rank = 0;
//        }
//
//        public int get_size() {
//            return (int)Math.pow(2, this.rank);
//        }
//
//        public String toString(){
//            String child = "null";
//            String next = "null";
//            String parent = "null";
//
//            if(this.child != null){
//                child = this.child.item.toString();
//            }
//            if(this.next != null){
//                next = this.next.item.toString();
//            }
//            if(this.parent != null){
//                parent = this.parent.item.toString();
//            }
//
//            return "(HeapNode) <"+this.item+">, child: "+child+", parent: "+parent+", next: <"+next+">, rank: "+this.rank+">";
//        }
//
//    }
//
//    /**
//     * Class implementing an item in a Binomial Heap.
//     *
//     */
//    public static class HeapItem{
//        public HeapNode node;
//        public int key;
//        public String info;
//
//        public HeapItem(){
//            this.node = null;
//            this.key = -1;
//            this.info = "empty value";
//        }
//
//        public HeapItem(int key, String info){
//            this.node = null;
//            this.key = key;
//            this.info = info;
//        }
//
//        public HeapItem(HeapNode node, int key, String info){
//            this.node = node;
//            this.key = key;
//            this.info = info;
//        }
//
//        public void set_node(HeapNode node) {
//            this.node = node;
//        }
//
//        public int getKey(){
//            return this.key;
//        }
//
//        public String toString(){
//            return "key: "+this.key+", info: "+this.info;
//        }
//
//    }
//}
