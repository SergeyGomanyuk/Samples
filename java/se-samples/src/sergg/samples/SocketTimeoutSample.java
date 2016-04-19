package sergg.samples;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketTimeoutSample {

    private static final int SO_TIMEOUT = 1000;
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Usage:
     *
     * java -cp se-samples.jar sergg.samples.SocketTimeoutSample server-line,3333 client-line,localhost,3333 server-object,3334 client-object,localhost,3334
     *
     */
	public static void main(String[] args) throws InterruptedException {
        if(args.length == 0) usage();
        try {
            for (String arg : args) {
                StringTokenizer tokenizer = new StringTokenizer(arg, ",");
                String token = tokenizer.nextToken();
                switch (token) {
                    case "server-line":
                        executorService.submit(new LineReceiver(Integer.parseInt(tokenizer.nextToken())));
                        break;
                    case "server-object":
                        executorService.submit(new ObjectReceiver(Integer.parseInt(tokenizer.nextToken())));
                        break;
                    case "client-line":
                        executorService.submit(new LineSender(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken())));
                        break;
                    case "client-object":
                        executorService.submit(new ObjectSender(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken())));
                        break;
                    default:
                        usage();
                        break;
                }
            }
        } catch(Exception e) {
            usage();
        }
	}

    private static void usage() {
        System.out.println("java -cp se-samples.jar sergg.samples.SocketTimeoutSample server-line,3333 client-line,localhost,3333 server-object,3334 client-object,localhost,3334");
        System.exit(1);
    }

    // ----------------------- Receivers ---------------------------------

    private static abstract class AbstractReceiver implements Runnable {

        private final int port;

        private ExecutorService executorService;

        public AbstractReceiver(int port) {
            this.port = port;
        }

        protected abstract Runnable createReceiverTask(Socket socket);

        @Override
		public void run() {
            executorService = Executors.newCachedThreadPool();

            System.out.println("Receiver started");

			ServerSocket serverSocket = null;

			try {
				serverSocket = new ServerSocket(port);
				serverSocket.setSoTimeout(SO_TIMEOUT);

				while (!Thread.interrupted()) {
					try {
						Socket clientSocket = serverSocket.accept();
                        executorService.submit(createReceiverTask(clientSocket));
					} catch (SocketTimeoutException e) {
                        System.out.println("Receiver got " + e.getMessage() + ", bytesTransferred=" + e.bytesTransferred);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
                executorService.shutdownNow();
                System.out.println("Receiver finished");
			}

		}
	}

    private static class LineReceiver extends AbstractReceiver {

        public LineReceiver(int port) {
            super(port);
        }

        @Override
        protected Runnable createReceiverTask(final Socket socket) {
            return new Runnable() {
                @Override
                public void run() {
                    System.out.println("LineReceiverTask started");
                    try(Socket s = socket; BufferedReader inputReader = new BufferedReader(new InputStreamReader(s.getInputStream())); ) {
                        s.setSoTimeout(SO_TIMEOUT);
                        String line = "";
                        while (!Thread.interrupted() && line != null) {
                            try {
                                line = inputReader.readLine();
                                System.out.println("LineReceiverTask line received: "+ line);
                            } catch (SocketTimeoutException e) {
                                System.out.println("LineReceiverTask got " + e.getMessage() + ", bytesTransferred=" + e.bytesTransferred);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("LineReceiverTask got " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        System.out.println("LineReceiverTask finished");
                    }

                }
            };
        }
    }

    private static class ObjectReceiver extends AbstractReceiver {

        public ObjectReceiver(int port) {
            super(port);
        }

        protected ObjectInputStream createObjectInputStream(Socket socket) throws IOException {
            final InputStream inputStream = socket.getInputStream();
            if(inputStream instanceof BufferedInputStream) {
                return new ObjectInputStream(inputStream);
            } else {
                return new ObjectInputStream(new BufferedInputStream(inputStream));
            }
        }

        @Override
        protected Runnable createReceiverTask(final Socket socket) {
            return new Runnable() {
                @Override
                public void run() {
                    System.out.println("ObjectReceiverTask started");
                    try(Socket s = socket; ObjectInputStream ois = createObjectInputStream(s)) {
                        s.setSoTimeout(SO_TIMEOUT);
                        while (!Thread.interrupted()) {
                            try {
                                Object message = ois.readObject();
                                System.out.println("ObjectReceiverTask received: "+ message);
                            } catch (SocketTimeoutException e) {
                                System.out.println("ObjectReceiverTask got " + e.getMessage() + ", bytesTransferred=" + e.bytesTransferred);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("ObjectReceiverTask got " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        System.out.println("ObjectReceiverTask finished");
                    }
                }
            };
        }
    }

    // ----------------------------- Senders ------------------------------------

    private abstract static class AbstractSender implements Runnable {

        private final String host;
        private final int port;

        public AbstractSender(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public void run() {
            System.out.println("AbstractSender started");
            try(Socket socket = new Socket(host, port))
            {
                sendNormally(socket, getBytes("Message will be sent normally"));

                sendNormally(socket, getBytes("Message will be sent normally"));

                sendSlowly(socket, getBytes("Message will be sent slowly"));

                final byte[] bytes = getBytes("Line will be sent slowly with big pause in the middle");
                sendSlowly(socket, Arrays.copyOfRange(bytes, 0, bytes.length/2));
                Thread.sleep(400);
                sendSlowly(socket, Arrays.copyOfRange(bytes, bytes.length/2, bytes.length));
            } catch (Exception e) {
                System.out.println("AbstractSender got " + e.getMessage());
            } finally {
                System.out.println("AbstractSender finished");
            }
        }

        private void sendSlowly(Socket socket, byte[] bytes) throws IOException, InterruptedException {
            for(byte b : bytes) {
                Thread.sleep(SO_TIMEOUT - 200);
                socket.getOutputStream().write(b);
            }
        }

        private void sendNormally(Socket socket, byte[] bytes) throws IOException {
            socket.getOutputStream().write(bytes);
        }

        protected abstract byte[] getBytes(Object o) throws IOException;
    }

	private static class LineSender extends AbstractSender {

        public LineSender(String host, int port) {
            super(host, port);
        }

        @Override
        protected byte[] getBytes(Object o) throws IOException {
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintWriter outWriter = new PrintWriter(baos, true)) {
                outWriter.println(o.toString());
                return baos.toByteArray();
            }
        }
    }

    private static class ObjectSender extends AbstractSender {

        private ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private ObjectOutputStream oos;

        {
            try {
                oos = new ObjectOutputStream(baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ObjectSender(String host, int port) {
            super(host, port);
        }

        @Override
        protected byte[] getBytes(Object o) throws IOException {
            oos.writeObject(o);
            final byte[] bytes = baos.toByteArray();
            baos.reset();
            return bytes;
        }
    }
}
