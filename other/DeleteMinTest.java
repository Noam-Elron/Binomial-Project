import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DeleteMinTest {

    @Test
    void findMinimum(){
        BinomialHeap.HeapNode root1 = TreesGenerator.createTreeSize4(1);
        BinomialHeap.HeapNode root2 = TreesGenerator.createTreeRank2(10);
        BinomialHeap heap1 = TreesGenerator.heapFromTree(root1);
        BinomialHeap heap2 = TreesGenerator.heapFromTree(root2);
        BinomialHeap.HeapItem n1 = heap1.findMin();
        assert(1 == n1.key);
        BinomialHeap.HeapItem n10 = heap2.findMin();
        assert(10 == n10.key);
    }


    @Test
    void findMinimum2() {
        BinomialHeap h1 = TreesGenerator.createHeap(0);
        BinomialHeap.HeapItem item1 = h1.findMin();
        assert(0 == item1.key);
        BinomialHeap h2 = TreesGenerator.createRandomHeap72();
        BinomialHeap.HeapItem item2 = h2.findMin();
        assert(2 == item2.key);
    }

    @Test
    void deleteMin2_Min12(){
        BinomialHeap h = TreesGenerator.createRandomHeap72();
        BinomialHeap.HeapItem i2 = h.findMin();
        assertEquals(2, i2.key);
        h.deleteMin();

        assertEquals(30, h.size);
        assertEquals(19, h.last.item.key);
        BinomialHeap.HeapItem i12 = h.findMin();
        assertEquals(12, i12.key);
        h.deleteMin();
        assertEquals(29, h.size);
        assertEquals(19, h.last.item.key);
        BinomialHeap.HeapItem i15 = h.findMin();
        assertEquals(15, i15.key);
    }

    @Test
    void deleteMinEmpty(){
        BinomialHeap.HeapNode root = TreesGenerator.createTreeRank2(1);
        BinomialHeap h = TreesGenerator.heapFromTree(root);
        System.out.println(h);
        System.out.println(h.min);
        h.deleteMin();
        assertEquals(1, h.size);
        assertEquals(2, h.last.item.key);
        assertEquals(0, h.last.rank);
        assertEquals(h.min, h.last);
        assertEquals(h.last, h.last.next);
        h.deleteMin();
        assertEquals(0, h.size);
        assertNull(h.last);
        assertNull(h.min);

        h.deleteMin();
        h.deleteMin();
        h.deleteMin();
        assertEquals(0, h.size);
        assertNull(h.last);
        assertNull(h.min);

    }
}
