public class ResultLinkedList extends BinomialList {
    public int length = 0;

    public ResultLinkedList() {
        this.first = new BinomialHeap.HeapNode(); // Sentinel
        this.prev = first;
        this.cur = first;
        this.last = null;

    }

    public ResultLinkedList(BinomialHeap.HeapNode node) {
        node.next = null;
        this.first = node;
        this.prev = node;
        this.cur = node;
        this.last = null;
    }

    /**
     *
     * @param new_cur new cur node<br><br>
     * Description: adds node to the list, updates pointers: <br> previous -> cur(old_one), <br> cur -> new_cur point,<br> <br>
     * Note: Does not modify input node-new_cur's next pointer! <br>
     */

    public void add(BinomialHeap.HeapNode new_cur) {
        cur.next = new_cur;
        prev = cur;
        cur = cur.next;
        length++;
    }


    public void remove_sentinel() {
        if (this.first.rank == -1) {
            this.last.next = first.next;
            this.first = this.last.next;
        }
    }



}
