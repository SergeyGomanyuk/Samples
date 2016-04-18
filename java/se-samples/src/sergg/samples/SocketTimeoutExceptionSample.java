package sergg.samples;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketTimeoutExceptionSample {

    static Thread serverThread;

	public static void main(String[] args) throws InterruptedException {
        serverThread = new Thread(new ObjectReaderServer());
        serverThread.start();
        new Thread(new ObjectWriterClient()).start();
        Thread.sleep(20000);
//        new Thread(new ObjectWriterClient()).start();
//        Thread.sleep(6000);
        serverThread.interrupt();

//startLocalLineClientServerScenario();
	}

    static void startLocalLineClientServerScenario() throws InterruptedException {
        startBufferedReaderServer();
        startPrintWriterClient();
        serverThread.interrupt();
    }

    static void startBufferedReaderServer() {
        serverThread = new Thread(new BufferedReaderServer());
        serverThread.start();
    }

    static void startPrintWriterClient() throws InterruptedException {
        new Thread(new PrintWriterClient()).start();
        Thread.sleep(6000);
        new Thread(new PrintWriterClient(true)).start();
        Thread.sleep(6000);
    }

	static abstract class AbstractReaderServer implements Runnable {

        private ExecutorService executorService;

        protected abstract Runnable createReaderTask(Socket socket);

        @Override
		public void run() {
            executorService = Executors.newCachedThreadPool();

            System.out.println("Server started");

			ServerSocket serverSocket = null;

			try {
				serverSocket = new ServerSocket(3333);
				serverSocket.setSoTimeout(1000);

				while (!Thread.interrupted()) {
					try {
						Socket clientSocket = serverSocket.accept();
                        executorService.submit(createReaderTask(clientSocket));
					} catch (SocketTimeoutException e) {
                        System.out.println("Server got " + e.getMessage() + ", bytesTransferred=" + e.bytesTransferred);
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
                executorService.shutdownNow();
                System.out.println("Server finished");
			}

		}
	}

    static class BufferedReaderServer extends AbstractReaderServer {
        @Override
        protected Runnable createReaderTask(Socket socket) {
            return new BufferedReaderTask(socket);
        }
    }

    static class ObjectReaderServer extends AbstractReaderServer {
        @Override
        protected Runnable createReaderTask(Socket socket) {
            return new ObjectReaderTask(socket);
        }
    }

    static class ObjectReaderTask implements Runnable {

        final Socket socket;

        public ObjectReaderTask(Socket socket) {
            this.socket = socket;
        }

        protected ObjectInputStream createObjectInputStream() throws IOException {
            final InputStream inputStream = socket.getInputStream();
            if(inputStream instanceof BufferedInputStream) {
                return new ObjectInputStream(inputStream);
            } else {
                return new ObjectInputStream(new BufferedInputStream(inputStream));
            }
        }

        @Override
        public void run() {
            System.out.println("ObjectReaderTask started");
            try(ObjectInputStream ois = createObjectInputStream(); Socket s = socket) {
                s.setSoTimeout(1000);
                while (!Thread.interrupted()) {
                    try {
                        Object message = ois.readObject();
                        System.out.println("ObjectReaderTask received: "+ message);
                    } catch (SocketTimeoutException e) {
                        System.out.println("ObjectReaderTask got " + e.getMessage() + ", bytesTransferred=" + e.bytesTransferred);
                    }
                }
            } catch (Exception e) {
                System.out.println("ObjectReaderTask got " + e.getMessage());
                e.printStackTrace();
            } finally {
                System.out.println("ObjectReaderTask finished");
            }
        }
    }


    static class ObjectWriterClient implements Runnable {

        @Override
        public void run() {
            try(Socket socket = new Socket("localhost", 3333)) {
                System.out.println("ObjectWriterClient started, 2 sec pause.");
                Thread.sleep(2000);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                byte[] bytes;
//                oos.writeObject("Hello Mr. Server");
//                byte[] bytes = baos.toByteArray();
//                System.out.println("Client sent first part of message");
//                socket.getOutputStream().write(bytes, 0, 4);
//                socket.getOutputStream().flush();
//                System.out.println("Client 3 sec pause");
//                Thread.sleep(3000);
//                socket.getOutputStream().write(bytes, 4, bytes.length-4);
//                socket.getOutputStream().flush();
//                System.out.println("Client sent 2nd part");
//                baos.reset();
//                bytes = baos.toByteArray();
//                System.out.println("Client bytes.length="+bytes.length);
                oos.writeObject("Kuku! AAAAAA!");
                bytes = baos.toByteArray();
                for(int i=0; i<bytes.length; i++) {
                    System.out.println("Client sent one byte and sleep 0.5 sec");
                    socket.getOutputStream().write(bytes[i]);
                    socket.getOutputStream().flush();
                    Thread.sleep(500);
                }
//                System.out.println("Client sent first part of message, bytes.length="+bytes.length);
//                socket.getOutputStream().write(bytes, 0, 4);
//                socket.getOutputStream().flush();
//                System.out.println("Client 3 sec pause");
//                Thread.sleep(3000);
//                bytes = baos.toByteArray();
//                socket.getOutputStream().write(bytes, 4, 3);
//                socket.getOutputStream().flush();
//                System.out.println("Client sent 2nd part");
//                Thread.sleep(3000);
//                bytes = baos.toByteArray();
//                socket.getOutputStream().write(bytes, 7, bytes.length-7);
//                socket.getOutputStream().flush();
//                System.out.println("Client sent 3rdnd part");
            } catch (Exception e) {
                System.out.println("Client got " + e.getMessage());
            } finally {
                System.out.println("Client finished");
            }
        }
    }

    static class BufferedReaderTask implements Runnable {

        final Socket socket;

        public BufferedReaderTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("BufferedReaderTask started");
            try(BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); Socket s = socket) {
                socket.setSoTimeout(1000);
                String line = "";
                while (!Thread.interrupted() && line != null) {
                    try {
                        line = inputReader.readLine();
                        System.out.println("BufferedReaderTask received: "+ line);
                    } catch (SocketTimeoutException e) {
                        System.out.println("BufferedReaderTask got " + e.getMessage() + ", bytesTransferred=" + e.bytesTransferred);
                    }
                }
            } catch (Exception e) {
                System.out.println("BufferedReaderTask got " + e.getMessage());
                e.printStackTrace();
            } finally {
                System.out.println("BufferedReaderTask finished");
            }
        }
    }

	static class PrintWriterClient implements Runnable {

        private boolean closeSocket = false;

        public PrintWriterClient(boolean closeSocket) {
            this.closeSocket = closeSocket;
        }

        public PrintWriterClient() {
            this.closeSocket = false;
        }

        @Override
		public void run() {
            System.out.println("Client started, 2 sec pause.");
			try(Socket socket = new Socket("localhost", 3333); PrintWriter outWriter = new PrintWriter(socket.getOutputStream(), true);) {
				Thread.sleep(2000);
				outWriter.print("Hello Mr.");
                System.out.println("Client sent 'Hello Mr.', 3 sec pause.");
				Thread.sleep(3000);
                if(!closeSocket) {
                    outWriter.println(" Server!");
                    System.out.println("Client sent ' Server!'");
                }
			} catch (Exception e) {
                System.out.println("Client got " + e.getMessage());
			} finally {
                System.out.println("Client finished");
			}
		}
	}
}
