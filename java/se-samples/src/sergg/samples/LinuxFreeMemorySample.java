package sergg.samples;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version $Revision:$
 */
public class LinuxFreeMemorySample {
    private static String FREE_OUTPUT = "             total       used       free     shared    buffers     cached\n" +
            "Mem:    8372453376 2635350016 5737103360  428703744  666017792  301764608\n" +
            "-/+ buffers/cache: 1667567616 6704885760\n" +
            "Swap:   8002727936   47226880 7955501056\n";

    private static String REGEXP = ".*buffers/cache: (\\d+) (\\d+).*";

    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEXP, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = p.matcher(FREE_OUTPUT);
        if(m.matches()) {
            System.out.println(m.group(1) + " " + m.group(2));
        }

    }

}
