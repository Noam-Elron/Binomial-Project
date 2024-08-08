public class Melder {
    BinomialHeap.HeapNode carry = new BinomialHeap.HeapNode(); // Virtual Carry
    ResultLinkedList result = new ResultLinkedList();
    InputLinkedList first_heap;
    InputLinkedList second_heap;
    int trees_merged = 0;

    public Melder(BinomialHeap heap1, BinomialHeap heap2) {
        first_heap = new InputLinkedList(heap1);
        second_heap = new InputLinkedList(heap2);
        first_heap.disconnect_tail_from_head();
        second_heap.disconnect_tail_from_head();
    }

    /**
     *
     * Melds two heaps until one runs out.
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
                        //System.out.println("First heap cur beforehand: " + first_heap.cur());
                        //System.out.println("Second heap cur beforehand: " + second_heap.cur());
                        first_heap.next();
                        second_heap.next();
                        //System.out.println("First heap cur: " + first_heap.cur());
                        //System.out.println("Second heap cur: " + second_heap.cur());
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


    public ResultLinkedList meld_unexhausted_with_carry() {
        InputLinkedList unexhausted = !first_heap.finished_iterating() ? first_heap : second_heap;
        while (carry.rank != -1 && !unexhausted.finished_iterating()) {
            //System.out.println("Unexhausted heap cur: " + unexhausted.cur);
            //System.out.println("Unexhausted heap last: " + unexhausted.last);
            if (carry.rank < unexhausted.cur().rank) {
                result.add(carry);
                carry = new BinomialHeap.HeapNode();
                break;
            }
            else {
                //System.out.println("Linking carry and remainder: ");
                //System.out.println("carry: " + carry);
                //System.out.println("remaining: " + remaining);
                unexhausted.next();
                carry = link(carry, unexhausted.get_previous());
                trees_merged++;
                if (unexhausted.prev == unexhausted.last) {
                    //System.out.println("updating last node to: " + carry);
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


    public void swap_with_carry(InputLinkedList list) {
        BinomialHeap.HeapNode temp = list.cur();
        carry.next = temp.next;
        list.cur = carry;

        carry = temp;
        carry.next = null; //new carry's pointer is null
    }

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
     * pre: x,y are Binomial tree header nodes
     * pre: Trees with x,y as header are same degree( B_k and B_k )
     *
     * Static meld between two trees of same size
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

    public InputLinkedList get_first_heap() {
        return first_heap;
    }

    public InputLinkedList get_second_heap() {
        return second_heap;
    }

    public ResultLinkedList getResult() {
        return result;
    }

    public BinomialHeap.HeapNode getCarry() {
        return carry;
    }
}
