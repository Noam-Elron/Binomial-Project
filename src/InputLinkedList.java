public class InputLinkedList extends BinomialList {
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

    public int orphan_children() {
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

    public void remove_from_list(BinomialHeap.HeapNode node) {
        //System.out.println("First: " + first);
        //System.out.println("Last: " + last);
        //System.out.println("Node: " + node);
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
                //System.out.println(cur);
                next();
            }
            prev.next = cur.next;
            cur = prev.next;
            if (node == last) {
                //System.out.println("new last: " + prev);
                this.last = prev;
            }

        }
        connect_tail_to_head();
        cur = first;

    }

    private void empty_list() {
        this.first = null;
        this.last = null;
        this.min_node = null;
        this.prev = null;
        this.cur = null;
    }

    public BinomialHeap.HeapNode get_min() {
        return this.min_node;
    }
}
