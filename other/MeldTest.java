import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MeldTest {

    @Test
    void sn_meldHeaps4with2() {
        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeSize4(1);
        BinomialHeap.HeapNode root2 = TreesGenerator.createTreeRank2(10);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        BinomialHeap heap2 = TreesGenerator.heapFromTree(root2);
        heap1.meld(heap2);
        BinomialHeap.HeapNode node1 = heap1.last;
        assertEquals(1, node1.item.key);
        assertEquals(1, heap1.min.item.key);
        assertEquals(6, heap1.size);

        BinomialHeap.HeapNode node10 = node1.next;
        assertEquals(10, node10.item.key);
        assertEquals(node1, node10.next);
    }

    @Test
    void sn_meldHeaps4with4() {
        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeSize4(1);
        BinomialHeap.HeapNode root2 = TreesGenerator.createTreeSize4(10);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        BinomialHeap heap2 = TreesGenerator.heapFromTree(root2);
        heap1.meld(heap2);
        BinomialHeap.HeapNode node1 = heap1.last;
        assertEquals(1, node1.item.key);
        assertEquals(1, heap1.min.item.key);
        assertEquals(8, heap1.size);
        assertEquals(node1, node1.next);
        BinomialHeap.HeapNode node10 = node1.child;
        assertEquals(10, node10.item.key);
        BinomialHeap.HeapNode node3 = node10.next;
        assertEquals(3, node3.item.key);
        assertEquals(node1, node3.parent);
        BinomialHeap.HeapNode node2 = node3.next;
        assertEquals(2, node2.item.key);
        assertEquals(node1, node2.parent);
    }

    @Test
    void sn_add2TreesOf4() {
        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeSize4(1);
        BinomialHeap.HeapNode root2 = TreesGenerator.createTreeSize4(10);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        BinomialHeap heap2 = TreesGenerator.heapFromTree(root2);
        Melder melder = new Melder(heap1, heap2);
        BinomialHeap.HeapNode root3 = melder.link(root1, root2);

        assertEquals(1, root3.item.key);
        assertEquals(3, root3.rank);
        assertEquals(10, root3.child.item.key); // biggest child
        assertEquals(1, root3.child.parent.item.key);

        assertEquals(3, root3.child.next.item.key); // lowest rank child
        assertEquals(1, root3.child.next.parent.item.key);

        assertEquals(2, root3.child.next.next.item.key);
        assertEquals(1, root3.child.next.next.parent.item.key);

        assertEquals(10, root3.child.next.next.next.item.key);
    }
    @Test
    void sn_add2TreesDiffSize() {

        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeSize4(1);
        BinomialHeap.HeapNode root2 = TreesGenerator.createTreeRank1(1);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        BinomialHeap heap2 = TreesGenerator.heapFromTree(root2);
        Melder melder = new Melder(heap1, heap2);
        BinomialHeap.HeapNode root3 = melder.link(root1, root2);
        assertNull(root3);
    }

    @Test
    void sn_add2TreesOf1() {
        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeRank1(1);
        BinomialHeap.HeapNode root2 = TreesGenerator.createTreeRank1(0);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        BinomialHeap heap2 = TreesGenerator.heapFromTree(root2);
        Melder melder = new Melder(heap1, heap2);
        BinomialHeap.HeapNode root3 = melder.link(root1, root2);
        assertEquals(1, root3.item.key);
        assertNull(root3.parent);
        assertEquals(1, root3.next.item.key);

        assertEquals(2, root3.child.item.key);
        assertEquals(1, root3.child.parent.item.key);
        assertEquals(2, root3.child.next.item.key);
        assertNull(root3.child.child);
    }

    @Test
    void sn_meld_heap_with_null(){
        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeSize4(1);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        heap1.meld(null);
        assertEquals(4, heap1.size);
        assertEquals(1, heap1.last.item.key);
    }

    @Test
    void sn_meld_empty_heap_with_empty_heap(){
        BinomialHeap heap1 = new BinomialHeap();
        BinomialHeap heap2 = new BinomialHeap();
        heap1.meld(heap2);
        assertEquals(0, heap1.size);
    }

    @Test
    void sn_meld_heap_with_empty_heap(){
        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeSize4(1);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        BinomialHeap heap2 = new BinomialHeap();
        heap1.meld(heap2);
    }

    @Test
    void sn_meld_empty_heap_with_full_heap(){
        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeSize4(1);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        BinomialHeap heap2 = new BinomialHeap();
        heap2.meld(heap1);
    }


    // These tests use isnert. They depend on its correctness
    @Test
    void sn_meldHeap15_with_heap3() {
        BinomialHeap heap1 = new BinomialHeap();
        BinomialHeap heap2 = new BinomialHeap();

        for (int i = 1; i < 16; i++){
            heap1.insert(i*10, ""+i);
        }

        for (int i = 3; i < 6; i++){
            heap2.insert(i*10+5,""+i*10+5);
        }
        heap2.meld(heap1);
        BinomialHeap.HeapNode n10 = heap2.last;
        assertEquals(n10, heap2.min);
        assertEquals(10, n10.item.key);
        assertEquals(4, n10.rank);
        BinomialHeap.HeapNode n55 = n10.next;
        assertEquals(55, n55.item.key);
        assertEquals(1, n55.rank);
        assertNull(n55.parent);
        assertEquals(n10, n55.next);

    }



}
