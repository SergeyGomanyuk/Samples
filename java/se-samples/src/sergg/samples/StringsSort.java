package sergg.samples;

import java.util.*;

/**
 * @version $Revision:$
 */
public class StringsSort {
    public static void main(String[] args) {
        String[] strings = new String[] {"a", "b", "ab", null, null, "ba", "abab"};
        System.out.println("before sorting:" + Arrays.toString(strings));
//        Arrays.sort(strings);
//        System.out.println("after sorting:" + Arrays.toString(strings));

        List<String> list = new ArrayList<String>(Arrays.asList(strings));

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if(s1==s2) return 0;
                if(s1==null) return 1;
                if(s2==null) return -1;

                int result = s2.length() - s1.length();
                if(result != 0) {
                    return result;
                } else {
                    return s1.compareTo(s2);
                }
            }
        });
        System.out.println("after sorting:" + list);
    }
}
