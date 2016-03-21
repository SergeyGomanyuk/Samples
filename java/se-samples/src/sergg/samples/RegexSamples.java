package sergg.samples;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by sergeygo on 16.03.2016.
 */

public class RegexSamples {

    public static void main(String[] args) {
        simpleMatch();
        split();
        findGroups();
        findNamedGroups();
        replace();
    }

    public static void simpleMatch() {
        // aditional options: asPredicate()

        System.out.format("%nRegexSamples.simpleMatch...%n");
        final String input1 = "bbbbccc";
        final String input2 = "aaaccc";
        final String input3 = "aaab";
        final String regex = "a*b+c*";

        final boolean isMatched1 = Pattern.matches(regex, input1);
        final boolean isMatched2 = Pattern.matches(regex, input2);

        System.out.format("isMatched: %s, input: %s, regex: %s%n", isMatched1, input1, regex);
        System.out.format("isMatched: %s, input: %s, regex: %s%n", isMatched2, input2, regex);

        // java 8 pattern as predicate:
        final Pattern pattern = Pattern.compile(regex);
        final List<String> inputs = Arrays.asList(new String[] {input1, input2, input3});
        final List<String> filteredInputs = inputs.stream().filter(pattern.asPredicate()).collect(Collectors.toList());
        System.out.format("inputs: %s, filtered: %s%n", inputs, filteredInputs);
    }

    // also consider split(CharSequence input, int limit), splitAsStream(CharSequence input)
    public static void split() {
        System.out.format("%nRegexSamples.split...%n");

        final String line = "token1;;;token2//token3";
        final String splitRegex = "(;+|/+)";

        final Pattern pattern = Pattern.compile(splitRegex);
        final String[] tokens = pattern.split(line);

        System.out.format("tokens: %s, line: %s, splitRegex: %s%n", Arrays.toString(tokens), line, splitRegex);
    }

    public static void findGroups() {
        System.out.format("%nRegexSamples.findGroups...%n");

        final String input = "aaaabbbbcccc\nbbbccc";
        final String regex = "(a*)((b+)(c*))";

        // Create a Pattern object; consider compile(String regex, int flags)
        final Pattern p = Pattern.compile(regex);
        // Now create matcher object.
        final Matcher m = p.matcher(input);

        System.out.format("groupCount: %s, input: %s, regex: %s%n", m.groupCount(), input, regex);
        // consider find(int start)
        while(m.find()) {
            System.out.format("regex found in input%n");
            for(int i=0; i<=m.groupCount(); i++) {
                String group = m.group(i);
                System.out.format("group(%d): %s%n", i, group);
            }
        }
    }

    public static void findNamedGroups() {
        System.out.format("%nRegexSamples.findNamedGroups...%n");

        final String input = "aaaabbbbcccc";
        String regex = "(?<g1>a*)(?<g4>(?<g2>b*)(?<g3>c*))";

        // Create a Pattern object; consider compile(String regex, int flags)
        Pattern r = Pattern.compile(regex);
        // Now create matcher object.
        Matcher m = r.matcher(input);

        System.out.format("groupCount: %s, input: %s, regex: %s%n", m.groupCount(), input, regex);
        // start searching from 2nd position; consider region(int start, int end)
        if(m.find(2)) {
            System.out.format("group: g1, found:%s%n", m.group("g1"));
            System.out.format("group: g2, found:%s%n", m.group("g2"));
            System.out.format("group: g3, found:%s%n", m.group("g3"));
            System.out.format("group: g4, found:%s%n", m.group("g4"));
        }
    }

    public static void replace() {
        System.out.format("%nRegexSamples.replace...%n");
        final String input = "token1;;;token2//token3";
        final String regexToReplace = "(;+|/+)";
        final String replaceString = ", ";

        System.out.format("input: g4, regexToReplace:%s, replaceString:%s%n", input, regexToReplace, replaceString);

        // Create a Pattern object; consider compile(String regex, int flags)
        Pattern r = Pattern.compile(regexToReplace);
        // Now create matcher object.
        Matcher m = r.matcher(input);

        final String replaceAll = m.replaceAll(replaceString);
        final String replaceFirst = m.replaceFirst(replaceString);

        System.out.format("replaceAll: %s%n", replaceAll);
        System.out.format("replaceFirst: %s%n", replaceFirst);
    }
}