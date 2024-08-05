public class BinomialList {
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

    public void connect_tail_to_head() {
        this.last.next = this.first;
    }

    public boolean finished_iterating() {
        return cur == null;
    }

    /**
     *
     * pre: hasNext() returned True
     * @return returns current node and updates cur and prev
     */
    public BinomialHeap.HeapNode next() {
        prev = cur;
        cur = cur.next;
        return prev;
    }

    public BinomialHeap.HeapNode cur() {
        return cur;
    }

    public BinomialHeap.HeapNode get_previous() {
        return prev;
    }

    /**
     *
     * pre: hasNext() returned True
     * @return rank of the next node in the list
     */
    public int cur_rank() {
        return cur.rank;
    }

    public int cur_key() {
        return cur.item.getKey();
    }

    public void disconnect_tail_from_head() {
        this.last.next = null;
    }




}
