/*
 * Binomial Heap test program for Data Structures course.
 * To be compiled with BinomialHeap.java (Student file).
 *
 * @author  Oren Kishon
 *
 */

import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BinomialHeapTest {
    private static int[] createValues(int n) {
        int[] values = new int[n];
        int maxValue = n * 10;
        Random randomGenerator = new Random();

        for (int i = 0; i < n; ++i){
            while (true) {
                int j, randInt = randomGenerator.nextInt(maxValue);

                for (j = 0; j < i && randInt != values[j]; ++j);
                if (j < i) {
                    continue;
                }
                values[i] = randInt;
                break;
            }
        }

        return values;
    }

    private static String vals2str(int[] values) {
        int min = values[0];
        for (int val : values) {
            min = val < min ? val : min;
        }
        String s = "size=" + values.length + " min=" + min;


        if (values.length <= 20) {
            for (int val : values) {
                s += " " + val;
            }
        } else {
            for (int j = 0; j < 5; ++j) {
                s += " " + values[j];
            }
            s += " ...";
            for (int j = 0; j < 5; ++j) {
                s += " " + values[values.length - 6 + j];
            }
        }

        return s;
    }

    private static abstract class Test implements Runnable {
        public final String name;
        private boolean failed;
        private String error;
        private String eMessage;
        private String eTrace;

        public Test(String name) {
            this.name = name;
            this.failed = false;
            this.error = "";
            this.eMessage = "";
            this.eTrace = "";
//            System.out.println("Adding test: " + name);
        }

        @Override
        public void run() {
            try {
                test();
            } catch (Exception e) {
                setFailed(e);
            }
        }

        protected abstract void test();

        public void setFailed(String error) {
            failed = true;
            this.error = error;
//            System.out.println("Failed. error: " + error);
        }

        public void setFailed(Exception e) {
            setFailed("Java Exception");
            this.eMessage = e.getMessage();
            this.eTrace = "\n";
            for (StackTraceElement el : e.getStackTrace()) {
                if (!el.getClassName().contains("BinomialHeap")) {
                    continue;
                }
                this.eTrace += "\t" + el + "\n";
            }
            //System.out.println("Exception message: " + eMessage);
            //System.out.println("Stack trace:\n" + eTrace);
        }

        public boolean failed() {
            return failed;
        }

        public String toString() {
            return String.format("%s, Faild: %s, Error: %s, "+
                            "Exception: %s, Call stack: %s", name,
                    failed ? "Y" : "N", error, eMessage, eTrace);
        }
    }

    static private class TestMeld1 extends Test {
        public TestMeld1() {
            super("Meld two empty heaps");
        }

        protected void test() {
            BinomialHeap heap1 = new BinomialHeap();
            BinomialHeap heap2 = new BinomialHeap();
            heap1.meld(heap2);
            if (!heap1.empty()) {
                setFailed("result not empty!");
            }
        }
    }

    static private class TestMeld2 extends Test {
        public TestMeld2() {
            super("Meld other empty heap");
        }

        protected void test() {
            BinomialHeap heap1 = new BinomialHeap();
            BinomialHeap heap2 = new BinomialHeap();
            heap1.insert(3, "");
            int size1 = heap1.size();
            heap1.meld(heap2);
            if (heap1.empty()) {
                setFailed("result empty!");
            }
            if (heap1.size() != size1 + heap2.size()) {
                setFailed("melded heap size ("+heap1.size()+
                        "!= heap1 ("+size1+") + heap2 ("+heap2.size()+")");
            }
            if (heap1.findMin().key != 3) {
                setFailed("findMin after meld failed");
            }
        }
    }

    static private class TestMeld3 extends Test {
        public TestMeld3() {
            super("Meld this empty heap with other nonempty");
        }

        protected void test() {
            BinomialHeap heap1 = new BinomialHeap();
            BinomialHeap heap2 = new BinomialHeap();
            heap2.insert(3, "");
            int size1 = heap1.size();
            heap1.meld(heap2);
            if (heap1.empty()) {
                setFailed("result empty!");
            }
            if (heap2.size() != size1 + heap2.size()) {
                setFailed("melded heap size ("+heap1.size()+
                        ") != heap1 ("+size1+") + heap2 ("+heap2.size()+
                        ")");
            }
            if (heap1.findMin().key != 3) {
                setFailed("findMin after meld failed");
            }
        }
    }

    static private class TestMeld4 extends Test {
        public TestMeld4() {
            super("Meld nonempty heap with other nonempty");
        }

        protected void test() {
            BinomialHeap heap1 = new BinomialHeap();
            BinomialHeap heap2 = new BinomialHeap();
            heap1.insert(5, "");
            heap1.insert(17, "");
            int size1 = heap1.size();
            heap2.insert(3, "");
            int size2 = heap2.size();

            heap1.meld(heap2);
            if (heap1.empty()) {
                setFailed("result empty!");
            }
            if (heap1.size() != size1 + size2) {
                setFailed("melded heap size ("+heap1.size()+
                        ") != heap1 ("+size1+") + heap2 ("+size2+")");
            }
            if (heap1.findMin().key != 3) {
                setFailed("findMin after meld failed");
            }
        }
    }

    static private class TestMeld5 extends Test {
        public TestMeld5() {
            super("Meld large heaps");
        }

        protected void test() {
            BinomialHeap heap1 = new BinomialHeap();
            BinomialHeap heap2 = new BinomialHeap();
            int[] vals1 = createValues(5000);
            int[] vals2 = createValues(5000);
            int min = vals1[0];
            for (int v : vals1) {
                heap1.insert(v, "");
                min = v < min ? v : min;
            }
            //System.out.println(heap1);
            for (int v : vals2) {
                heap2.insert(v, "");
                min = v < min ? v : min;
            }
            int size1 = heap1.size();
            int size2 = heap2.size();

            heap1.meld(heap2);
            if (heap1.empty()) {
                setFailed("result empty!");
            }
            if (heap1.size() != size1 + size2) {
                setFailed("melded heap size ("+heap1.size()+
                        ") != heap1 ("+size1+") + heap2 ("+size2+")");
            }
            if (heap1.findMin().key != min) {
                setFailed("findMin after meld failed");
            }
        }
    }

    static private class TestInsert extends Test {
        public TestInsert() {
            super("Check size each insert");
        }

        protected void test() {
            int[] vals = createValues(1000);

            BinomialHeap heap1 = new BinomialHeap();
            for (int i = 0; i < vals.length; ++i) {
                if (heap1.size() != i) {
                    setFailed("size is "+i+" but size() says "+
                            heap1.size());
                    break;
                }
                heap1.insert(vals[i], "");
            }
        }
    }

    static private class TestFindMin1 extends Test {
        public TestFindMin1() {
            super("Check findMin each unsorted insert");
        }

        protected void test() {

            int[] vals = createValues(1000);
            int min = vals[0];
            BinomialHeap heap1 = new BinomialHeap();

            for (int i = 0; i < vals.length; ++i) {
                min = vals[i] < min ? vals[i] : min;
                heap1.insert(vals[i], "");
                if (heap1.findMin().key != min) {
                    setFailed("min is "+min+
                            "but findMin() says "+
                            heap1.findMin());
                    break;
                }
            }
        }
    }

    static private class TestFindMin2 extends Test {
        public TestFindMin2() {
            super("Check findMin each sorted insert");
        }

        protected void test() {
            int[] vals = createValues(1000);
            Arrays.sort(vals);
            BinomialHeap heap1 = new BinomialHeap();
            for (int i = vals.length - 1; i >= 0 ; --i) {
                heap1.insert(vals[i], "");
                if (heap1.findMin().key != vals[i]) {
                    setFailed("min is "+vals[i]+
                            "but findMin() says "+
                            heap1.findMin());
                    break;
                }
            }
        }
    }

    static private class TestDeleteMin extends Test {
        public TestDeleteMin() {
            super("Check findMin after each deleteMin");
        }

        protected void test() {

            int[] vals = createValues(1000);
            BinomialHeap heap1 = new BinomialHeap();
            //System.out.println(Arrays.toString(vals));
            for (int v : vals) {
                heap1.insert(v, "");
            }
            //System.out.println(heap1);
            Arrays.sort(vals);
            for (int v : vals) {
                //System.out.println("Expected new min: " + v);
                //System.out.println("Actual new min in my heap: " + heap1.min);
                if (heap1.findMin().key != v) {
                    setFailed("min is "+v+" but findMin() says "+
                            heap1.findMin());
                    break;
                }
                //System.out.println(heap1);
                //System.out.println(heap1.min);
                //System.out.println(heap1.min.child);
                heap1.deleteMin();

            }
        }
    }

    static private class TestEmpty extends Test {
        public TestEmpty() {
            super("Check empty/size after insert and deleteMin");
        }

        protected void test() {

            BinomialHeap heap1 = new BinomialHeap();
            int size = 0;

            for (int i = 10; i < 100; ++i) {
                if (!heap1.empty()) {
                    setFailed("empty but empty() returns false");
                    break;
                }
                if (heap1.size() != size) {
                    setFailed("size is "+size+
                            " but size() returns "+
                            heap1.size());
                    break;
                }
                for (int j = 0; j < i; ++j) {
                    heap1.insert(i, "" + size);
                    ++size;
                    if (heap1.empty()) {
                        setFailed(
                                "not empty but empty() returns true");
                        break;
                    }
                    if (heap1.size() != size) {
                        setFailed("size is "+size+
                                " but size() returns "+
                                heap1.size());
                        break;
                    }
                }
                for (int j = 0; j < i; ++j) {
                    if (heap1.empty()) {
                        setFailed(
                                "not empty but empty() returns true");
                        break;
                    }
                    //HeapGraph.draw(heap1);
                    heap1.deleteMin();
                    --size;
                    if (heap1.size() != size) {
                        setFailed("size is "+size+
                                " but size() returns "+
                                heap1.size());
                        break;
                    }
                }
                if (!heap1.empty()) {
                    setFailed("empty but empty() returns false");
                    break;
                }
            }
        }
    }


    public static void main(String[] argv) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Test[] tests = {
                new TestMeld1(),
                new TestMeld2(),
                new TestMeld3(),
                new TestMeld4(),
                new TestMeld5(),
                new TestInsert(),
                new TestFindMin1(),
                new TestFindMin2(),
                new TestDeleteMin(),
                new TestEmpty(),
        };

        for (Test test : tests) {
            try {
                executor.submit(test).get(5, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                test.setFailed("Timed out. Infinite loop");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        executor.shutdown();

        int failed = 0, i = 0;
        for (Test test : tests) {
            if (test.failed()) {
                ++failed;
            }
            System.out.printf("Test %2d | " + test + "\n", ++i);
        }
        System.out.println("Failed "+failed+" tests");
        System.exit(0);
    }
}