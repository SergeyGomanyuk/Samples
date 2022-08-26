package sergg.samples;

import org.apache.commons.lang.mutable.MutableInt;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * https://www.baeldung.com/java-stream-sum
 *
 * @version :$
 */
public class StreamsElementsSum {
    public static void main(String[] args) {
        loop();
        glungu();
        sergeygo();
        denis();
    }

    private static void loop() {
        int j = 0;
        for(int i=1; i <=10; i++) {
            j += i*i;
        }
        System.out.println("loop: " + j);
    }

    private static void sergeygo() {
        final MutableInt j = new MutableInt(0);
        IntStream.rangeClosed(1, 10).forEach(i -> j.add(i*i));
        System.out.println("sergeygo version:" + j);
    }

    private static void glungu() {
        System.out.println("glungu:" + IntStream.range(1,11).map(i -> i*i).sum());
    }

    private static void denis() {
        System.out.print("denis: ");
        int[] tmp = {1,2,3,4,5,6,7,8,9,10};
        Stream.of(Arrays.stream(tmp).reduce((int x, int y) -> (x+= y * y)).getAsInt()).forEach(System.out::println);
    }
}
