package sergg.samples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCount {
    public static void main(String[] args) {
        wordCount(new String[] {"three", "two", "three", "two", "one", "three"});

//        System.out.println(wordCount(args));
    }

    private static void wordCount(String[] words) {
        Stream.of(words).
            collect(Collectors.toMap(word -> word, word -> 1, (val1, val2) -> val1 + val2))
            .entrySet().stream()
            .sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue()))
                .forEach(System.out::println);
    }
}
