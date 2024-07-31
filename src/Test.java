import java.util.HashSet;
import java.util.Set;


public class Test {

    public static void main(String[] args) {
        BinomialHeap heap = new BinomialHeap();
        heap.insert(12, "geut");
        //heap.insert(8, "geut");
        System.out.println("heap last: " + heap.last);
        System.out.println("heap last next: " + heap.last.next);
        //heap.insert(5, "geut");
        //heap.insert(1, "geut");
        //heap.insert(2, "geut");
        //heap.insert(9, "geut");
        print_r(heap);
        System.out.println("heap.last.next: " + heap.last.next);
        System.out.println("heap.last: " + heap.last);
        System.out.println("heap.last.child: " + heap.last.child);
        System.out.println("heap.last.child.child: " + heap.last.child.child);
        System.out.println("heap.last.child.next: " + heap.last.child.next);
    }

    public static void print_r(BinomialHeap hi) {
        int s;
        if (hi.getLast().getRank() == 0) {
            s = 1;
        } else if (hi.getLast().getRank() == 1) {
            s = 2;
        } else if (hi.getLast().getRank() == 2) {
            s = 3;
        } else {
            s = (int) Math.pow(2, hi.getLast().getRank() - 1);
        }
        int[][][] answer = new int[hi.numTrees()][s][s];
        BinomialHeap.HeapNode curr_tree = hi.getLast().getNext();
        for (int k = 0; k < hi.numTrees(); k++) {
            Set<BinomialHeap.HeapNode> dic = new HashSet<>();
            print_rec(curr_tree, 0, 0, dic, answer, k);
            curr_tree = curr_tree.getNext();
        }
        int x = 0;
        for (int i = 0; i < answer[x].length; i++) {
            for (int j = 0; j < answer[x].length; j++) {
                if (answer[x][i][j] != 0) {
                    System.out.print(answer[x][i][j] + " ");
                } else {
                    System.out.print("--");
                }
                if (x == answer.length - 1 && j == answer[x].length - 1) {
                    break;
                }
                if (j == answer[x].length - 1) {
                    if (x + 1 < answer.length) {
                        x += 1;
                    }
                    j = -1;
                }
            }
            System.out.println();
            x = 0;
        }
    }

    public static void print_rec(BinomialHeap.HeapNode first, int depth, int x, Set<BinomialHeap.HeapNode> dic, int[][][] answer, int num_in_row) {
        if (dic.contains(first)) {
            return;
        }
        dic.add(first);
        answer[num_in_row][depth][x] = first.getItem().getKey();
        if (depth != 0) {
            if (x == 2 && depth == 1) {
                print_rec(first.getNext(), depth, x + 2, dic, answer, num_in_row);
            } else if (x == 4 && depth == 1) {
                print_rec(first.getNext(), depth, x + 4, dic, answer, num_in_row);
            } else if (x == 10 && depth == 2) {
                print_rec(first.getNext(), depth, x + 2, dic, answer, num_in_row);
            } else {
                print_rec(first.getNext(), depth, x + 1, dic, answer, num_in_row);
            }
        }
        if (first.getChild() != null) {
            print_rec(first.getChild().getNext(), depth + 1, x, dic, answer, num_in_row);
        }

    }
}



/* doesnt work
	public void displayHeap()
	{
		System.out.print("\nHeap : ");
		displayHeapRec(this.last.next, this.last.next, false);
		System.out.println("\n");
	}

	private void displayHeapRec(HeapNode r, HeapNode firstInRow, boolean wasFirst){
		if (r!=null) {
			if (!r.equals(firstInRow) || !wasFirst) {
				System.out.println("r: "+r);
				System.out.println("firstInRow: "+firstInRow);
				if (r == firstInRow) {
					wasFirst = true;
				}
				displayHeapRec(r.child, r.child, false);
				System.out.print(r.item + " ");
				if(r.rank!=-1) {
					displayHeapRec(r.next, r, wasFirst);
				}
			}
		}
	}
 */
