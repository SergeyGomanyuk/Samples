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

import java.io.*;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.*;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class OperationalStateWriter {


    private static OperationalContext context = new OperationalContext();
    private static StringBuilder          testString       = new StringBuilder();
    private static String                 selfTestFileName = "./self.test";

    public static void main(String[] args) {
        Throwable e = new Error("kuku");
        int errorCode = 66;
        writeImmediate((
                e.getMessage() == null ? "" :
                        e.getMessage() + " ") + errorCode);
    }

    public static void writeImmediate(String stateString) {
        synchronized (context) {
            try {
                testString.setLength(0);
                testString.append(System.currentTimeMillis());
                testString.append(":").append(stateString);
                if (!context.isEmpty() && (stateString == null || stateString.equals("null"))) {
                    context.toProperties(testString.append("\n"));
                }
                writeFile(testString.toString());
            } catch (IOException cause) {
                System.err.println("Couldn't save server selft-test result");
                cause.printStackTrace();
            }
        }
    }

    private static void writeFile(String data) throws IOException {
        writeByteArrayToFile(new File(selfTestFileName), data.getBytes());
    }

    private static final int BUFFER_SIZE = 16 * 1024;
    private static final ThreadLocal<WeakReference<ByteBuffer>> threadLocalBuffer = new ThreadLocal<WeakReference<ByteBuffer>>();

    public static void copyChannel(ReadableByteChannel inChannel, WritableByteChannel outChannel) throws IOException {
        ByteBuffer buffer;
        WeakReference<ByteBuffer> bufferRef = threadLocalBuffer.get();
        if (bufferRef == null || (buffer = bufferRef.get()) == null) {
            buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
            bufferRef = new WeakReference<ByteBuffer>(buffer);
            threadLocalBuffer.set(bufferRef);
        }

        while (inChannel.read(buffer) != -1) {
            buffer.flip();                      // prepare the buffer to be drained
            outChannel.write(buffer);       // write to the channel, may block

            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear()
            buffer.compact();
        }

        buffer.flip();                          // EOF will leave buffer in fill state

        while (buffer.hasRemaining()) {         // make sure the buffer is fully drained.
            outChannel.write(buffer);
        }
    }

    public static void writeByteArrayToFile(File file, byte[] buffer) throws IOException {
        FileChannel fileChannel = new FileOutputStream(file).getChannel();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);

        copyChannel(Channels.newChannel(inputStream), fileChannel);
        fileChannel.close();
        inputStream.close();
    }

    public enum OperationalState {

        ACTIVE         ("active"),
        STAND_BY       ("stand-by"),
        OPERATIONAL    ("operational"),
        NOT_OPERATIONAL("not-operational");

        private String value;
        /**
         *
         * @param value
         */
        private OperationalState(String value) {
            this.value = value;
        }
        /**
         */
        public String toString() {
            return value;
        }
    }

    public static class OperationalContext extends HashMap<OperationalContext.NAME, Set<String>>{
        private static final long serialVersionUID = -2368577313284550364L;

        public enum NAME {
            STATE  ("state"  , OperationalState.class),
            ADDRESS("address", String.class);

            private String   name;
            private Class<?> valueClass;
            /**
             *
             * @param name
             */
            private NAME(String name, Class<?> valueClass) {
                this.name = name;
                this.valueClass = valueClass;
            }
            /**
             *
             * @return
             */
            public String getName() {
                return name;
            }
            /**
             *
             * @return
             */
            public Class<?> getValueClass() {
                return valueClass;
            }
        }

        public void setAttribute(NAME attributeName, Object ... attributeValues) {
            Set<String> valueSet = new HashSet<>();
            for (Object value : attributeValues) {
                if (attributeName.valueClass != null && value != null &&
                        attributeName.valueClass != value.getClass()) {
                    throw new IllegalArgumentException("Illegal value specified for " + attributeName.name());
                }
                valueSet.add(String.valueOf(value));
            }
            put(attributeName, valueSet);
        }

        public void toProperties(StringBuilder builder) {
            for (NAME attribute : keySet()) {
                int index = 0;
                builder.append(attribute.getName()).append(" = ");
                for (String value : get(attribute)) {
                    builder.append(index++ == 0 ? "" : ",").append(value);
                }
                builder.append("\n");
            }
        }
    }
}
