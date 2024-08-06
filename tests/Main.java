public class Main {
    public static void main(String[] args) {
        BinomialHeap heap1 = new BinomialHeap();
        int size = 0;

        for (int i = 10; i < 13; ++i) {
            for (int j = 0; j < i; ++j) {
                System.out.println("HEAP SIZE IS: " + heap1.size());
                System.out.println("MINIMUM IS: " + heap1.min);
                System.out.println(heap1);
                heap1.insert(i, "" + size);
                ++size;

            }

            for (int j = 0; j < i; ++j) {
                System.out.println("HEAP SIZE IS: " + heap1.size());
                System.out.println("MINIMUM IS: " + heap1.min);
                System.out.println(heap1);
                //System.out.println(size);
                //System.out.println("Min: " + heap1.min);
                heap1.deleteMin();
                --size;

            }

        }
        HeapGraph.draw(heap1);



    }

}