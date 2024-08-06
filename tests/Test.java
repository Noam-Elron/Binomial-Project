public class Test {

    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        BinomialHeap heap = new BinomialHeap();

        for (int i = 1; i <= 7; i++) {
            heap.insert(i, "test");
        }

        //HeapGraph.draw(heap);
        System.out.println(heap);
        System.out.println(heap.size());
        System.out.println(heap.numTrees());
        for (int i = 1; i <= 7; i++) {
            heap.deleteMin();
            System.out.println(heap);
            System.out.println(heap.size());
            System.out.println(heap.numTrees());
        }

    }

    public static void test() {
        BinomialHeap heap = new BinomialHeap();
        heap.insert(12, "geut");
        heap.insert(8, "geut");
        System.out.println(heap.first);
        System.out.println(heap.last);
        System.out.println(heap);
        heap.insert(5, "geut");
        System.out.println(heap);
        System.out.println(heap.first);
        System.out.println(heap.last);
        heap.insert(1, "geut");
        System.out.println(heap);
        heap.insert(2, "geut");
        System.out.println(heap);
        heap.insert(9, "geut");
        System.out.println(heap);
        heap.insert(7, "geut");
        System.out.println(heap);
        heap.insert(4, "geut");
        System.out.println(heap);

        BinomialHeap heap2 = new BinomialHeap();
        heap2.insert(3, "geut");
        heap2.insert(17, "geut");
        heap2.insert(18, "geut");
        heap2.insert(42, "geut");
        heap2.insert(53, "geut");
        heap2.insert(67, "geut");
        heap2.insert(6, "geut");
        heap2.insert(99, "geut");
        System.out.println(heap2);

        heap.meld(heap2);

        System.out.println(heap);
        System.out.println("\n\n\n");
        System.out.println(heap.min);
        System.out.println(heap.min.child);
        System.out.println(heap.min.child.next);
        System.out.println(heap.min.child.child);
        System.out.println(heap.min.child.child.child);
        System.out.println("\n\n\n");
        System.out.println("last: " + heap.last);
        System.out.println("min: " + heap.min);
        System.out.println("num trees: " + heap.num_trees);

        System.out.println("\n\n");

        System.out.println("min: " + heap.min);
        System.out.println("first: " + heap.first);
        System.out.println("last: " + heap.last);
        HeapGraph.draw(heap2);
    }


    public static void test3() {
        BinomialHeap heap1 = new BinomialHeap();
        BinomialHeap heap2 = new BinomialHeap();

        heap1.insert(3, "");
        heap1.insert(4, "");
        heap1.insert(2, "");

        heap2.insert(5, "");
        heap2.insert(6, "");
        heap2.insert(7, "");

        //HeapGraph.draw(heap1);
        //HeapGraph.draw(heap2);

        heap1.meld(heap2);

        HeapGraph.draw(heap1);
    }
}



