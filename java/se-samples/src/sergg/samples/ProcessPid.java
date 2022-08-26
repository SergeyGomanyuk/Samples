package sergg.samples;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 */
public class ProcessPid {
    static final Logger log = Logger.getLogger("vip-manager.executor");

    private static final String PID_OUTPUT_VAR = "$PID=";
    private static final String PID_OUTPUT_CMD = "echo \\" + PID_OUTPUT_VAR + "$$; ";

    static final boolean WAIT_OUTPUTS = false;

    private final long maxCommandExecutionTime = 10000;

    public static void main(String[] args) {
        new ProcessPid().executeCommand(args[0], true);
    }


    Result executeCommand(String cmd, boolean killByTimeout) {
        String command[] = { "/bin/sh", "-c", PID_OUTPUT_CMD   + cmd };
        log.info("Executing external command using /bin/sh : " + cmd);
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            p.getOutputStream().close();
            DrainerThread stdout = new DrainerThread("out", Level.INFO, cmd, p.getInputStream());
            DrainerThread stderr = new DrainerThread("err", Level.WARN, cmd, p.getErrorStream());
            stdout.start();
            stderr.start();
            boolean exited = p.waitFor(maxCommandExecutionTime, TimeUnit.MILLISECONDS);
            if (exited) {
                int result = p.exitValue();
                if (WAIT_OUTPUTS) {
                    stdout.join();
                    stderr.join();
                }
                if (result == 0) {
                    log.info("Return code: 0");
                    return new Result(true, null);
                } else {
                    log.warn("Return code: " + result);
                    return new Result(false, null);
                }
            } else {
                String reason = "Process not exited in " + maxCommandExecutionTime + " millis";
                boolean killed = killByTimeout ? killProcessWithAllChildren(p, stdout.pid, reason) : false;
                throw new RuntimeException(reason + ". This process was killed: " + killed);
            }
        } catch (Exception e) {
            log.error("Error executing command: " + Arrays.toString(command), e);
            return new Result(false, e);
        }
    }

    private boolean killProcessWithAllChildren(Process p, String pid, String reason) {
        if (p.isAlive() && pid != null) {
            log.info(reason + ", kill all related processes");
            String killCmd = "kill -9 `ps -o pid= --ppid " + pid + "` " + pid;
            return executeCommand(killCmd, false).isOk();
        }
        return false;
    }

    static class DrainerThread extends Thread {

        final String id;
        final String prefix;
        final String command;
        final BufferedReader lnr;
        final InputStream stream;
        final Level pty;
        String pid;

        public DrainerThread(String id, Level pty, String command, InputStream stream) throws IOException {
            super("process-" + id);
            this.id = id;
            this.prefix = "[" + id + "]: ";
            this.pty = pty;
            this.command = command;
            this.stream = stream;
            lnr = new BufferedReader(new InputStreamReader(stream));
        }

        public void run() {
            try {
                lnr.lines().forEach(line -> processOutput(line));
            } catch (Exception cause) {
                log.error("Error reading std" + id + " stream of '" + command + "'", cause);
            }
        }

        private void processOutput(String line) {
            if (line.startsWith(PID_OUTPUT_VAR)) {
                pid = line.substring(PID_OUTPUT_VAR.length());
                log.info("The PID of '" + command + "' process: " + pid);
            } else {
                log.log(pty, prefix + line);
            }
        }
    }

    static class Result {
        private boolean   isOk;
        private Throwable cause;

        Result(boolean isOk, Throwable cause) {
            this.isOk  = isOk;
            this.cause = cause;
        }
        /**
         * @return the isOk
         */
        public boolean isOk() {
            return isOk;
        }
        /**
         * @return the cause
         */
        public Throwable getCause() {
            return cause;
        }
    }
}
