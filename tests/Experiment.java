//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//public class Experiment {
//    public static void main(String[] args) {
//        for (int i = 1; i <= 5; i++) {
//           double[] experiment_result = experment_ascending(i);
//            System.out.println(display_result(i, experiment_result));
//        }
//
//        for (int i = 1; i <= 5; i++) {
//            double[] experiment_result = experment_random(i);
//            System.out.println(display_result(i, experiment_result));
//        }
//
//        for (int i = 1; i <= 5; i++) {
//            double[] experiment_result = experment_descending(i);
//            System.out.println(display_result(i, experiment_result));
//        }
//    }
//
//    public static String display_result(int i, double[] arr) {
//        StringBuilder display = new StringBuilder();
//        display.append("Experiment: " + i + "\n");
//        display.append("Time Elapsed: " + arr[0] + "ms\n");
//        display.append("Total Links Performed: " + arr[1] + "\n");
//        display.append("Num trees at end of operation: " + arr[2] + "\n");
//        display.append("Total ranks of deleted nodes: " + arr[3] + "\n");
//        return display.toString();
//    }
//
//    public static double[] experment_ascending(int i) {
//        BinomialHeap heap = new BinomialHeap();
//        int total_links = 0;
//        double startTime = System.nanoTime();
//        for (int j = 1; j <= (Math.pow(3, i+7)-1); j++) {
//            total_links += heap.insert(j, "" + j);
//
//        }
//        double endTime = System.nanoTime();
//        double elapsedTime = endTime - startTime;
//        double totalTime_ms = elapsedTime / 1000000; // Convert nano second to ms, nanosecond = 10^-9 seconds, ms = 10^-3 seconds
//        //System.out.println(totalTime_ms);
//        return new double[]{totalTime_ms, total_links, heap.numTrees(), 0};
//    }
//
//    public static double[] experment_random(int i) {
//        BinomialHeap heap = new BinomialHeap();
//        int total_links = 0;
//        int ranks_deleted = 0;
//        int n = (int)(Math.pow(3, i+7)-1);
//        Integer[] arr = new Integer[n];
//        for (int j = 0; j < n; j++) {
//            arr[j] = j+1;
//        }
//        List<Integer> lst = Arrays.asList(arr);
//        Collections.shuffle(lst);
//        for (int j = 0; j < n; j++) {
//            arr[j] = lst.get(j);
//        }
//
//
//
//
//        double startTime = System.nanoTime();
//        for (int j : arr) {
//            total_links += heap.insert(j, "" + j);
//            //System.out.println("Inserting: " + j);
//        }
//
//        for (int j = 0; j < n/2; j++) {
//            int[] values = heap.deleteMin();
//            total_links += values[0];
//            ranks_deleted += values[1];
//        }
//
//
//        double endTime = System.nanoTime();
//        double elapsedTime = endTime - startTime;
//        double totalTime_ms = elapsedTime / 1000000; // Convert nano second to ms, nanosecond = 10^-9 seconds, ms = 10^-3 seconds
//        return new double[]{totalTime_ms, total_links, heap.numTrees(), ranks_deleted};
//    }
//
//    public static double[] experment_descending(int i) {
//        BinomialHeap heap = new BinomialHeap();
//        int total_links = 0;
//        int ranks_deleted = 0;
//        int n = (int)(Math.pow(3, i+7)-1);
//        int upto = (int) (Math.pow(2,5) - 1);
//
//        double startTime = System.nanoTime();
//        for (int j = n; j >= 1; j--) {
//            total_links += heap.insert(j, "" + j);
//            //System.out.println("Inserting: " + j);
//        }
//
//
//        for (int j = n; j > upto; j--) {
//            int[] values = heap.deleteMin();
//            total_links += values[0];
//            ranks_deleted += values[1];
//        }
//        //System.out.println(heap.size());
//
//
//        double endTime = System.nanoTime();
//        double elapsedTime = endTime - startTime;
//        double totalTime_ms = elapsedTime / 1000000; // Convert nano second to ms, nanosecond = 10^-9 seconds, ms = 10^-3 seconds
//        return new double[]{totalTime_ms, total_links, heap.numTrees(), ranks_deleted};
//    }
//
//
//}
