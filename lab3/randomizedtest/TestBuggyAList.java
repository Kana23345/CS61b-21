package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> bl = new BuggyAList<>();
        AListNoResizing<Integer> nl = new AListNoResizing<>();
        for (int i = 0; i < 3; i++) {
            bl.addLast(i + 4);
            nl.addLast(i + 4);

        }
        for (int i = 0; i < 3; i++) {
            assertEquals(nl.removeLast(), bl.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> BL = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BL.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
                assertEquals(L.size(), BL.size());
            } else if (operationNumber == 2 && L.size() > 0) {
                // getLast and removeLast
                int getLastNum = L.getLast();
                int removeLastNum = L.removeLast();
                System.out.println("get last number:" + getLastNum + "\n"
                        + "remove last num:" + removeLastNum);
                assertEquals(getLastNum, BL.getLast().intValue());
                assertEquals(removeLastNum, BL.removeLast().intValue());
            }
        }
    }

}
