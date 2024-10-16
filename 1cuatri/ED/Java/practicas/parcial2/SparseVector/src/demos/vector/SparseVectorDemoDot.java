package demos.vector;

import dataStructures.vector.SparseVector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;
/* ================================================================================
 * En el directorio dot se encuentran los resultados visuales de ejecutar esta demo
 * Los vectores deben ser
 * (0,0,0,0,0,0,0,0)
 * (0,5,0,0,0,0,0,0)
 * (0,5,0,0,0,0,10,0)
 * (0,5,0,0,0,0,10,10)
 * ================================================================================*/
public class SparseVectorDemoDot {

    public static void main(String[] args) {
        SparseVector<Integer> sv = new SparseVector<>(3, 0);
        printSparseVector(sv);
        System.out.println(sv);
        saveTreeToDot("sv-0", sv);
        sv.set(1, 5);
        printSparseVector(sv);
        System.out.println(sv);
        saveTreeToDot("sv-1-5", sv);
        sv.set(6, 10);
        printSparseVector(sv);
        System.out.println(sv);
        saveTreeToDot("sv-6-10", sv);
        sv.set(7, 10);
        printSparseVector(sv);
        System.out.println(sv);
        saveTreeToDot("sv-7-10", sv);

    }

    private static void printSparseVector(SparseVector<Integer> sv) {
        StringJoiner sj = new StringJoiner(",", "(", ")");
        for (Integer x : sv) {
            sj.add(x.toString());
        }
        System.out.println(sj);
    }

    private static <T extends Comparable<? super T>> void saveTreeToDot(String name, SparseVector<T> tree) {
        Path path = Path.of("dot");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (PrintWriter pw = new PrintWriter(path.resolve( name + ".dot").toFile())) {
            pw.println(tree.toDot(name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
