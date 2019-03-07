package sergg.samples;

// https://stackoverflow.com/questions/25230171/unexpected-type-resulting-from-the-ternary-operator
//
// https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.25
// Otherwise, binary numeric promotion (§5.6.2) is applied to the operand types, and the type of the conditional expression is the promoted type of the second and third operands.
//
// https://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.6.2
// If either operand is of type double, the other is converted to double.

/**
 * @version $Revision:$
 */
public class TrenaryAndDouble {
    public static void main(String[] args) {
        System.out.println(giveMeNumberUsingIf(true));
        System.out.println(giveMeNumberUsingIf(false));
        System.out.println(giveMeNumberUsingTernary(true));
        System.out.println(giveMeNumberUsingTernary(false));
        System.out.println(0L);
        System.out.println(0D);
        System.out.println(giveMeObjectUsingTernary(true));
        System.out.println(giveMeObjectUsingTernary(false));
    }

    static Number giveMeNumberUsingIf(boolean longOrDouble) {
        if(longOrDouble) {
            return new Long(0L);
        } else {
            return new Double(0D);
        }
    }

    static Number giveMeNumberUsingTernary(boolean longOrDouble) {
        return longOrDouble?new Long(0L):(Number)new Double(0D);
    }

    static Object giveMeObjectUsingTernary(boolean objectOrString) {
        return objectOrString?new Object():new String("kuku");
    }

}


