import exercises.LinkedSeq;
import java.util.Random;

public class LinkedSeqDemo {
    static void property(int d, int n) {
        LinkedSeq<Integer> linkedSeq1 = LinkedSeq.fromInt(n + d);

        LinkedSeq<Integer> linkedSeq2 = LinkedSeq.fromInt(n);
        LinkedSeq.addSingleDigit(d, linkedSeq2);
        System.out.println(d + " + " + n + " = " + linkedSeq2);
        boolean ok = linkedSeq1.equals(linkedSeq2);
        if (!ok)
            throw new AssertionError("*** FAILED! falsified for digit " + d + " and number " + n);
    }

    public static void main(String[] args) {
        Random rnd = new Random();

        int nTests = 100;
        for (int i = 1; i < nTests; i++) {
            int d = rnd.nextInt(10);
            int n = rnd.nextInt(Integer.MAX_VALUE - 10);
            property(d, n);
        }
        property(9, 99999);
        System.out.println("\n+++ OK. Passed " + nTests + " tests");
    }
}
