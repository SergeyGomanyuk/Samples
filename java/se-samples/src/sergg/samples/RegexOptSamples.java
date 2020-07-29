package sergg.samples;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.stream.Collectors;

/**
 * Created by sergeygo on 16.03.2016.
 */

public class RegexOptSamples {

    private static String messagePart = "dcjwwxnxn wwqxdjqwepedxn qwxedxqwepdxnq wqeedopwqexndwqepx dowqn idxqw qwedxqn w dwxowqindxoqw xwediowqnx  Password='password'" +
            "wanwjedjwcv wfc4r34r=-3i2ncx 234r-2-nr cx 24rin23-rn 23i3kd mx03o" +
            "fo1c password='password' wdfpqwexdn3qnd qwejnqcd qcf3nic ]3qcofn 3qc] cqoifcn3]qn 3cqfoq3nc] 3 wqcfoiqn3 cfqc3wf qc3fnq 3]cqq cqon] c3qqc  qconi]o q3c password='password'";

    private static String[] messages = new String[100];

    static long start, finish;

    public static void main(String[] args) {
        String message = "";
        for(int i=0; i<100; i++) {
            StringBuilder sb = new StringBuilder();
            for(int j=0; j<5000; j++) {
                for(int k=0; k<80; k++) {
                    sb.append("a1assw" + (i % 10));
                }
                sb.append("Password='password'\n");
            }
            messages[i] = sb.toString();
//            System.out.println(messages[i]);
        }

        start = System.currentTimeMillis();
        for(int k=0; k<100; k++) {
            String replaced = replace(messages[k], pattern, MASKCARD);
        }
        finish = System.currentTimeMillis();
        System.out.println("replace took " + (finish - start)/1000 + " ms");

        start = System.currentTimeMillis();
        for(int j=0; j<100; j++) {
            String replacedOpt = replaceOpt(messages[j], patternOpt, MASKCARD);
        }
        finish = System.currentTimeMillis();
        System.out.println("replace took " + (finish - start)/1000 + " ms");

    }

    private static String MASKCARD = "$1***$2";
    private static Pattern pattern = Pattern.compile("(.*[P,p]assword=').*?('.*)");
    private static Pattern patternOpt = Pattern.compile("([P,p]assword=').*?(')");

    public static String replace(String input, Pattern pattern, String replacement) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.replaceAll(replacement);
        }
        return input;
    }

    public static String replaceOpt(String input, Pattern pattern, String replacement) {
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll(replacement);
    }
}