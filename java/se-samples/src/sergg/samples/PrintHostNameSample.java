/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p>
 * $Id:$
 */
package sergg.samples;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class PrintHostNameSample {
    public static void main(String[] args) throws IOException {
        java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
        System.out.println("Hostname of local machine: '" + localMachine.getHostName() + "'");

        Process p = Runtime.getRuntime().exec("hostname");
        final InputStream inputStream = p.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        System.out.println("Hostname returns: '" + result.trim() + "'");
    }

    private static String getHostname() throws IOException {
        final Process p = Runtime.getRuntime().exec("hostname");
        final InputStream inputStream = p.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        final String h = s.hasNext() ? s.next() : "";
        return h.trim();
    }

}
