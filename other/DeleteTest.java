import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class DeleteTest {
    @Test
    void heapifyUp_middleNodeOneLevelUP(){
        BinomialHeap bh = TreesGenerator.createRandomHeap72();
        BinomialHeap.HeapNode root = bh.last;
        assertEquals(19, root.item.key);
        BinomialHeap.HeapNode node34 = bh.last.child;
        BinomialHeap.HeapNode node46 = node34.child.next.next;
        assertEquals(46, node46.item.key);
        bh.decreaseKey(node46.item, 25);
        assertEquals(21, node34.item.key);
        assertEquals(34, node46.item.key);
    }
    @Test
    void heapifyUp_middleNodeToTheRoot(){
        BinomialHeap bh = TreesGenerator.createRandomHeap72();
        BinomialHeap.HeapNode root = bh.last;
        assertEquals(19, root.item.key);
        BinomialHeap.HeapNode node34 = bh.last.child;
        BinomialHeap.HeapNode node46 = node34.child.next.next;
        assertEquals(46, node46.item.key);
        bh.decreaseKey(node46.item, 30);
        assertEquals(16, root.item.key);
        assertEquals(19, node34.item.key);
        assertEquals(34, node46.item.key);
        assertEquals(2,bh.min.item.key);
    }

    @Test
    void heapifyUp_middleNodeBecomeMin(){
        BinomialHeap bh = TreesGenerator.createRandomHeap72();
        BinomialHeap.HeapNode root = bh.last;
        assertEquals(19, root.item.key);
        BinomialHeap.HeapNode node34 = bh.last.child;
        BinomialHeap.HeapNode node46 = node34.child.next.next;
        assertEquals(46, node46.item.key);
        bh.decreaseKey(node46.item, 45);
        assertEquals(1, root.item.key);
        assertEquals(19, node34.item.key);
        assertEquals(34, node46.item.key);
        assertEquals(1,bh.min.item.key);
    }

    @Test
    void heapifyUp_rootOfMiddleTree(){
        BinomialHeap bh = TreesGenerator.createRandomHeap72();
        BinomialHeap.HeapNode root = bh.last;
        assertEquals(19, root.item.key);
        BinomialHeap.HeapNode node34 = bh.last.child;
        BinomialHeap.HeapNode node46 = node34.child.next.next;
        assertEquals(46, node46.item.key);
        BinomialHeap.HeapNode node12 = root.next.next.next.next;
        assertEquals(12, node12.item.key);
        bh.decreaseKey(node12.item,8);
        assertEquals(4, node12.item.key);

        BinomialHeap bh2 = new BinomialHeap();
        //BinomialHeap.HeapItem hi = bh2.insert(10,"10");
        //bh2.decreaseKey(hi,5);
    }

    @Test
    void delete_2Nodes(){
        BinomialHeap bh = TreesGenerator.createRandomHeap72();
        BinomialHeap.HeapNode root = bh.last;
        assertEquals(19, root.item.key);
        BinomialHeap.HeapNode node34 = bh.last.child;
        BinomialHeap.HeapNode node46 = node34.child.next.next;
        assertEquals(46, node46.item.key);
        BinomialHeap.HeapNode node45 = root.next;
        assertEquals(45, node45.item.key);
        BinomialHeap.HeapNode node12 = node45.next.next.next;
        assertEquals(12, node12.item.key);
        bh.decreaseKey(node12.item,8);
        bh.delete(node45.item);;
        bh.delete(root.item);
    }

}
