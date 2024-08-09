import java.util.Random;
public class TreesGenerator {

    /**
     * 8
     * @param offset
     * @return
     */
    public static BinomialHeap.HeapNode createTreeRank8(int offset){
        BinomialHeap heap = new BinomialHeap();

        BinomialHeap.HeapNode[] arr1 = new BinomialHeap.HeapNode[8];
        for (int i = 0; i < 8; i++) {
            //arr1[i] = heap.new HeapNode(i + offset, "A");
            BinomialHeap.HeapItem item = new BinomialHeap.HeapItem(i + offset, "A8");
            arr1[i] = new BinomialHeap.HeapNode(item);
        }
//        arr1[0].next = single;
        arr1[0].next = arr1[0];
        arr1[0].child = arr1[1];
        arr1[3].next =arr1[2];
        arr1[2].next =arr1[1];
        arr1[1].next =arr1[3];
        arr1[1].child = arr1[4];
        arr1[5].next =arr1[4];
        arr1[4].next =arr1[5];
        arr1[2].child = arr1[6];
        arr1[6].next =arr1[6];
        arr1[4].child = arr1[7];
        arr1[7].next =arr1[7];
        arr1[1].parent = (arr1[0]);
        arr1[2].parent = (arr1[0]);
        arr1[3].parent = (arr1[0]);
        arr1[4].parent = (arr1[1]);
        arr1[5].parent = (arr1[1]);
        arr1[6].parent = (arr1[2]);
        arr1[7].parent = (arr1[4]);
        arr1[0].rank = (3);
        arr1[1].rank = (2);
        arr1[2].rank = (1);
        arr1[3].rank = (0);
        arr1[4].rank = (1);
        arr1[5].rank = (0);
        arr1[6].rank = (0);
        arr1[7].rank = (0);

        return arr1[0];
    }

    public static BinomialHeap createHeapTreeSize8(int offset) {
        BinomialHeap.HeapNode root = TreesGenerator.createTreeRank8(1);
        
        BinomialHeap heap = new BinomialHeap();
        heap.last = (root);
        heap.min = (root);
        heap.size = (8);
        return heap;
    }

    /**
     * 8
     * @param offset
     * @return
     */
    public static BinomialHeap.HeapNode createTreeSize4(int offset){
        BinomialHeap heap = new BinomialHeap();

        BinomialHeap.HeapNode[] arr2 = new BinomialHeap.HeapNode[4];
        for (int i = 0; i < 4; i++) {
            BinomialHeap.HeapItem item = new BinomialHeap.HeapItem(i + offset, "A4");
            arr2[i] = new BinomialHeap.HeapNode(item);
        }
        arr2[0].next = (arr2[0]);
        arr2[0].child = arr2[1];
        arr2[2].next =arr2[1];
        arr2[1].next =arr2[2];
        arr2[1].child = arr2[3];
        arr2[3].next =arr2[3];
        arr2[1].parent = (arr2[0]);
        arr2[2].parent = (arr2[0]);
        arr2[3].parent = (arr2[1]);
        arr2[0].rank = (2);
        arr2[1].rank = (1);
        arr2[2].rank = (0);
        arr2[3].rank = (0);

        return arr2[0];
    }

    public static BinomialHeap.HeapNode createTreeRank2(int offset){
        BinomialHeap heap = new BinomialHeap();

        BinomialHeap.HeapNode[] arr2 = new BinomialHeap.HeapNode[4];
        for (int i = 0; i < 2; i++) {
            BinomialHeap.HeapItem item = new BinomialHeap.HeapItem(i + offset, "A2");
            arr2[i] = new BinomialHeap.HeapNode(item);
        }
        arr2[0].next = arr2[0];
        arr2[0].child = arr2[1];
        arr2[0].rank = 1;
        arr2[1].parent = arr2[0];
        arr2[1].next = arr2[1];
        arr2[1].rank = 0;

        return arr2[0];
    }


    public static BinomialHeap createHeapTreeRank4(int offset) {
        BinomialHeap.HeapNode root = TreesGenerator.createTreeSize4(1);

        BinomialHeap heap = new BinomialHeap();
        heap.last = (root);
        heap.min = (root);
        heap.size = (4);
        return heap;
    }

    public static BinomialHeap heapFromTree(BinomialHeap.HeapNode root) {
        BinomialHeap heap = new BinomialHeap();
        heap.last = (root);
        heap.min = (root);
        int size = (int) Math.pow(2, root.rank);
        heap.size = (size);
        return heap;
    }

    public static BinomialHeap.HeapNode createTreeRank1(int offset) {
        BinomialHeap heap = new BinomialHeap();
        BinomialHeap.HeapItem item = new BinomialHeap.HeapItem(1 + offset, "A");
        BinomialHeap.HeapNode single = new BinomialHeap.HeapNode(item);
        single.next = single;
        return single;
    }


    public static BinomialHeap createRandomHeap72(){
        Random generator = new Random(100);
        BinomialHeap heap = new BinomialHeap();
        for (int i = 0; i < 31; i++) {
            double numDouble = generator.nextDouble();
            int numInt = (int) Math.floor(numDouble * 100);
            heap.insert(numInt, ""+numInt);
        }
        return heap;
    }
    public static BinomialHeap createHeap(int offset)
    {
        BinomialHeap heap = new BinomialHeap();
        BinomialHeap.HeapNode[] arr1 = new BinomialHeap.HeapNode[8];
        BinomialHeap.HeapNode[] arr2 = new BinomialHeap.HeapNode[4];
        BinomialHeap.HeapNode[] arr3 = new BinomialHeap.HeapNode[2];
        BinomialHeap.HeapItem item = new BinomialHeap.HeapItem(30 + offset, "A");
        BinomialHeap.HeapNode single = new BinomialHeap.HeapNode(item);
        BinomialHeap.HeapItem x = null;
        for (int i = 0; i < 8; i++) {
            x = new BinomialHeap.HeapItem(i + offset, "A");
            arr1[i] = new BinomialHeap.HeapNode(x);
        }
        for (int i = 0; i < 4; i++) {
            x = new BinomialHeap.HeapItem(i + 10 + offset, "A");
            arr2[i] = new BinomialHeap.HeapNode(x);
        }
        for (int i = 0; i < 2; i++) {
            x = new BinomialHeap.HeapItem(i + 20 + offset, "A");
            arr3[i] = new BinomialHeap.HeapNode(x);
        }
        arr1[0].next = single;
        arr1[0].child = arr1[1];
        arr1[3].next =arr1[2];
        arr1[2].next =arr1[1];
        arr1[1].next =arr1[3];
        arr1[1].child = arr1[4];
        arr1[5].next =arr1[4];
        arr1[4].next =arr1[5];
        arr1[2].child = arr1[6];
        arr1[6].next =arr1[6];
        arr1[4].child = arr1[7];
        arr1[7].next =arr1[7];
        arr1[1].parent = (arr1[0]);
        arr1[2].parent = (arr1[0]);
        arr1[3].parent = (arr1[0]);
        arr1[4].parent = (arr1[1]);
        arr1[5].parent = (arr1[1]);
        arr1[6].parent = (arr1[2]);
        arr1[7].parent = (arr1[4]);
        arr1[0].rank = (3);
        arr1[1].rank = (2);
        arr1[2].rank = (1);
        arr1[3].rank = (0);
        arr1[4].rank = (1);
        arr1[5].rank = (0);
        arr1[6].rank = (0);
        arr1[7].rank = (0);

        arr2[0].next = (arr1[0]);
        arr2[0].child = arr2[1];
        arr2[2].next =arr2[1];
        arr2[1].next =arr2[2];
        arr2[1].child = arr2[3];
        arr2[3].next =arr2[3];
        arr2[1].parent = (arr1[0]);
        arr2[2].parent = (arr1[0]);
        arr2[3].parent = (arr1[1]);
        arr2[0].rank = (2);
        arr2[1].rank = (1);
        arr2[2].rank = (0);
        arr2[3].rank = (0);

        arr3[0].next =arr2[0];
        arr3[0].child = arr3[1];
        arr3[1].next =arr3[1];
        arr3[1].parent = (arr3[0]);
        arr3[0].rank = (1);
        arr3[1].rank = (0);

        single.next =arr3[0];
        single.rank = (0);

        heap.last = (arr1[0]);
        heap.min = (arr1[0]);
        heap.size = (15);
        return heap;
    }


}
