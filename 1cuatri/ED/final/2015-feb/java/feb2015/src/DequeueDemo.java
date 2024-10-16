import dataStructures.doubleEndedQueue.DoubleEndedQueue;
import dataStructures.doubleEndedQueue.LinkedDoubleEndedQueue;

public class DequeueDemo {

    public static void main(String[] args) {

        final int N = 11;
        DoubleEndedQueue<Integer> dq = new LinkedDoubleEndedQueue<Integer>();

        // Check the constructor

        if (!dq.isEmpty()) {
            throw new RuntimeException("Dequeue should be created empty");
        }

        // Check working at front of an empty dequeue

        dq.addFirst(1);
        System.out.println("Add first (1): " + dq);
        if (dq.isEmpty()) {
            throw new RuntimeException("Dequeue should not be empty");
        }

        if (dq.first() != 1) {
            throw new RuntimeException("First should be 1");
        }

        if (dq.last() != 1) {
            throw new RuntimeException("Last should be 1");
        }

        dq.deleteFirst();
        System.out.println("Delete first (1): " + dq);
        if (!dq.isEmpty()) {
            throw new RuntimeException("Dequeue should be empty");
        }

        // Check working at rear of an empty dequeue

        dq.addLast(1);
        System.out.println("Add last (1): " + dq);
        if (dq.isEmpty()) {
            throw new RuntimeException("Dequeue should not be empty");
        }

        if (dq.last() != 1) {
            throw new RuntimeException("Last should be 1");
        }

        if (dq.first() != 1) {
            throw new RuntimeException("First should be 1");
        }

        dq.deleteLast();
        System.out.println("Delete last (1): " + dq);
        if (!dq.isEmpty()) {
            throw new RuntimeException("Dequeue should be empty");
        }

        // Check N insertions/retrievals/deletions at front

        for (int i= 0; i <= N; i++) {
            dq.addFirst(i);
        }
        System.out.println("Add first (up to 11): " + dq);
        for (int i= N; i >= 0; i--) {
            if (i != dq.first()) {
                throw new RuntimeException("First should be " + i);
            }
            dq.deleteFirst();
        }
        System.out.println("Delete first (up to 11): " + dq);
        if (!dq.isEmpty()) {
            throw new RuntimeException("Dequeue should be empty");
        }

        // Check N insertions/retrievals/deletions at rear

        for (int i= 0; i <= N; i++) {
            dq.addLast(i);
        }
        System.out.println("Add last (up to 11): " + dq);
        for (int i= N; i >= 0; i--) {
            if (i != dq.last()) {
                throw new RuntimeException("Last should be " + i);
            }
            dq.deleteLast();
        }
        System.out.println("Delete last (up to 11): " + dq);
        if (!dq.isEmpty()) {
            throw new RuntimeException("Dequeue should be empty");
        }

        // Check N insertions/retrievals/deletions alternating front and rear

        for (int i= 0; i<= N; i++) {
            if (i % 2 == 0) {
                dq.addFirst(i);
            }
            else {
                dq.addLast(i);
            }
        }
        System.out.println("Alternate add first (even) and add last (odd) (up to 11): " + dq);

        for (int i= N; i >= 0; i--) {
            if (i % 2 == 0) {
                if (i != dq.first()) {
                    throw new RuntimeException("First should be " + i);
                }
                dq.deleteFirst();
            }
            else {
                if (i != dq.last()) {
                    throw new RuntimeException("Last should be " + i);
                }
                dq.deleteLast();
            }
        }
        System.out.println("Alternate delete first (even) and delete last (odd) (up to 11): " + dq);
        if (!dq.isEmpty()) {
            throw new RuntimeException("Dequeue should be empty");
        }

        System.out.println("DONE");
    }
}
