package sergg.samples;

/*
Questions:
- describe output
- change the code to reduce iteration to 0 (do not change cycle itself)
- change the code to increase iteration to inf (do not change cycle itself)

 Answers:
        i=5
        i=6

        int start = Integer.MAX_VALUE; // zero iterations
        int start = Integer.MAX_VALUE-1; // infinite iterations
 */
public class InfiniteLoop {
    public static void main(String[] args) {

        int start = 5;

        // do not change
        for (int i = start; i <= start + 1; i++) {
            System.out.println("i=" + i);
        }
        // do not change
    }
}
