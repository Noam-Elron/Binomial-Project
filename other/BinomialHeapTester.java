import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinomialHeapTester {

    @Test
    void insert() {
    }

    @Test
    void deleteMin() {
    }

    @Test
    void findMin() {
    }

    @Test
    void decreaseKey() {
    }

    @Test
    void delete() {
    }

    @Test
    void meld() {
    }

    @Test
    void size() {
    }

    @Test
    void empty() {
        BinomialHeap bh = new BinomialHeap();
        assertTrue(bh.empty());
        bh.insert(1,"1");
        assertFalse(bh.empty());
    }

    @Test
    void numTrees() {
        BinomialHeap bh = new BinomialHeap();
        assertEquals(0, bh.numTrees());
        bh.insert(1,"1");
        assertEquals(1,bh.numTrees());
        bh.insert(2,"2");
        assertEquals(1,bh.numTrees());
        bh.insert(3,"3");
        assertEquals(2,bh.numTrees());
    }
}