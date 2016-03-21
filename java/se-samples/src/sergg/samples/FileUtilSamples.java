package sergg.samples;

import sergg.util.FileUtil;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by sergeygomanyuk on 09.02.16.
 */
public class FileUtilSamples {

    public static void main(String[] args) throws IOException {
        System.out.println(FileUtil.getDirectories(Paths.get(".")));
        System.out.println(FileUtil.getEntries(Paths.get(".")));
    }
}
