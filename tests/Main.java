import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        not_working();



    }

    public static void not_working() {
        BinomialHeap heap = new BinomialHeap();
        int[] shitty_vals = {68, 13, 121, 178, 66, 7, 85, 72, 75, 63, 128, 74, 125, 113, 136, 149, 175, 90, 157, 64};
        for (int v : shitty_vals) {
            heap.insert(v, "");
        }
        Arrays.sort(shitty_vals);
        System.out.println(Arrays.toString(shitty_vals));

        System.out.println();
        for (int v : shitty_vals) {
            System.out.println(heap);
            System.out.println("Num trees: " + heap.numTrees());
            System.out.println("Size: " + heap.size());
            System.out.println("Expected new min(who were deleting) is: " + v);
            System.out.println("Actual new min in my heap: " + heap.min);
            if (heap.findMin().key != v) {
                System.out.println("Recieved min: " + heap.findMin() + " is wrong");
                break;
            }


            heap.deleteMin();
        }
        System.out.println(heap);
    }

    public static void test() {
        BinomialHeap heap1 = new BinomialHeap();
        int size = 0;

        for (int i = 10; i < 30; ++i) {
            if (!heap1.empty()) {
                System.out.println("empty but empty() returns false");
                break;
            }
            if (heap1.size() != size) {
                System.out.println("size is " + size +
                        " but size() returns " +
                        heap1.size());
                break;
            }
            for (int j = 0; j < i; ++j) {
                heap1.insert(i, "" + size);
                ++size;
                if (heap1.empty()) {
                    System.out.println(
                            "not empty but empty() returns true");
                    break;
                }
                if (heap1.size() != size) {
                    System.out.println("size is " + size +
                            " but size() returns " +
                            heap1.size());
                    break;
                }
            }
            System.out.println(heap1);
            for (int j = 0; j < i; ++j) {
                if (heap1.empty()) {
                    System.out.println(
                            "not empty but empty() returns true");
                    break;
                }
                //HeapGraph.draw(heap1);
                System.out.println("Heap number: " + i);
                System.out.println("Deleting node: " + j);
                System.out.println("Heap size: " + heap1.size());
                System.out.println("Min: " + heap1.min);
                System.out.println("Min size: " + heap1.min.get_size());
                System.out.println(heap1);
                heap1.deleteMin();
                --size;
                if (heap1.size() != size) {
                    System.out.println("size is " + size +
                            " but size() returns " +
                            heap1.size());
                    break;
                }
            }
            if (!heap1.empty()) {
                System.out.println("empty but empty() returns false");
                break;
            }
        }
    System.out.println(heap1);
    }

    public static void test2() {
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
        //HeapGraph.draw(heap1);
    }



}