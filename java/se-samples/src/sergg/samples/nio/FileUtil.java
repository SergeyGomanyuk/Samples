package sergg.samples.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergeygomanyuk on 09.02.16.
 */
public class FileUtil {

    public static List<String> fileListNio(String directory) {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {

        }
        return fileNames;
    }

    public static String[] fileListIo(String directory) {
        return new File(directory).list();
    }

}
