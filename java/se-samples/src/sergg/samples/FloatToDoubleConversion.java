package sergg.samples;

/**
 * @version $Revision:$
 */
public class FloatToDoubleConversion {
    public static void main(String[] args) {
        float temp = 14009.35F;
        System.out.println(Float.toString(temp)); // Prints 14009.35
        System.out.println(Double.toString((double)temp)); // Prints 14009.349609375
        System.out.println(Double.valueOf(Float.valueOf(temp).toString())); // Prints 14009.35
    }
}
