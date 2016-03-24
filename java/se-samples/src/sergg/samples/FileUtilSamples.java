package sergg.samples;

import sergg.util.FileUtil;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by sergeygomanyuk on 09.02.16.
 */
public class FileUtilSamples {

    public static void main(String[] args) throws IOException {
        // todo redisign as visitors for common tasks: get dirs, get files, get entries with filtering, etc..
        System.out.println(FileUtil.getDirectories(Paths.get(".")));
        System.out.println(FileUtil.getEntries(Paths.get(".")));

        // todo copy files samples
        // todo delete files/directories samples
        // todo manage file attrs samples
    }
}
