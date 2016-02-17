package sergg.samples.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sergeygomanyuk on 09.02.16.
 */
public class FileUtil {

    public static List<Path> getDirectories(final Path dir) throws IOException {
        final List<Path> dirlist = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (final Iterator<Path> it = stream.iterator(); it.hasNext();) {
                dirlist.add(dir.resolve(it.next()));
            }
        }
        return dirlist;
    }

    public static List<Path> getEntries(final Path dir) throws IOException {
        final List<Path> entries = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (final Iterator<Path> it = stream.iterator(); it.hasNext();) {
                entries.add(it.next());
            }
        }
        return entries;
    }

    public static List<Path> getEntries(final Path dir, final DirectoryStream.Filter<? super Path> filter) throws IOException {
        final List<Path> entries = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter)) {
            for (final Iterator<Path> it = stream.iterator(); it.hasNext();) {
                entries.add(it.next());
            }
        }
        return entries;
    }

    public static List<Path> getEntries(final Path dir, final String glob) throws IOException {
        final List<Path> entries = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, glob)) {
            for (final Iterator<Path> it = stream.iterator(); it.hasNext();) {
                entries.add(it.next());
            }
        }
        return entries;
    }

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
