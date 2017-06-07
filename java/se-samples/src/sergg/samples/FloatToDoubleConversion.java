/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p/>
 * $Id:$
 */
package sergg.samples;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
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
