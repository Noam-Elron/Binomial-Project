import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class InsertTest {

    @Test
    void sn_insert_1_to_15() {
        BinomialHeap heap1 = new BinomialHeap();
        for (int i = 1; i < 16; i++){
            heap1.insert(i, ""+i);
        }
        assertEquals(15, heap1.size);
        BinomialHeap.HeapNode n1 = heap1.last;
        BinomialHeap.HeapNode n1A = heap1.min;
        assertEquals(n1, n1A);
        BinomialHeap.HeapNode n15 = n1.next;
        BinomialHeap.HeapNode n13 = n15.next;
        BinomialHeap.HeapNode n9 = n13.next;
        assertEquals(1, n1.item.key);
        assertEquals(15, n15.item.key);
        assertEquals(13, n13.item.key);
        assertEquals(9, n9.item.key);
        assertEquals(n1, n9.next);
    }

    void sn_insert_15_to_1() {
        BinomialHeap heap1 = new BinomialHeap();
        for (int i = 15; i > 0; i--){
            heap1.insert(i, ""+i);
        }
        assertEquals(15, heap1.size);
        BinomialHeap.HeapNode n8 = heap1.last;
        BinomialHeap.HeapNode n1 = heap1.min;
        assertEquals(n1, n8.next);

        BinomialHeap.HeapNode n2 = n1.next;
        BinomialHeap.HeapNode n4 = n2.next;
        assertEquals(n8, n4.next);

        assertEquals(1, n1.item.key);
        assertEquals(2, n2.item.key);
        assertEquals(4, n4.item.key);
        assertEquals(8, n8.item.key);

    }

    @Test
    void sn_insert_1s_2s() {
        BinomialHeap heap1 = new BinomialHeap();

        for (int i = 1; i < 4; i++){
            heap1.insert(1, ""+1);
        }
        for (int i = 1; i < 8; i++){
            heap1.insert(2, ""+2);
        }
        for (int i = 1; i < 3; i++){
            heap1.insert(1, ""+1);
        }

        assertEquals(12, heap1.size);
        BinomialHeap.HeapNode n1 = heap1.last;
        assertEquals(3, n1.rank);
        BinomialHeap.HeapNode n1A = heap1.min;
        assertEquals(1,n1.item.key);
        assertEquals(1,n1A.item.key);

        BinomialHeap.HeapNode n1B = n1.next;
        assertEquals(2, n1B.rank);
        assertEquals(1, n1B.item.key);
        assertEquals(n1B.next, n1);


    }

//    @Test
//    void sn_insert_5_4_3_2_1() {
//        // do nothing
//    }
//
//    @Test
//    void sn_insert_5_4_3_2_1() {
//
//    }
}