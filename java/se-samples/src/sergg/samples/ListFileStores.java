package sergg.samples;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version $Revision:$
 */
public class ListFileStores {
    public static void main(String[] args) throws IOException {
        System.out.println("Root directories:");
        for(Path path : FileSystems.getDefault().getRootDirectories()) {
            System.out.println(path);
        };

        System.out.println("");
        System.out.format("%-20s %12s %12s %12s %12s %12s %12s%n", "Filesystem", "kbytes", "used", "avail", "type", "name", "root");

        for (FileStore store : FileSystems.getDefault().getFileStores()) {
            long total = store.getTotalSpace() / 1024;
            long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
            long avail = store.getUsableSpace() / 1024;
            if (total > 0) {
                System.out.format("%-20s %12d %12d %12d %12s %12s %12s%n", store, total, used, avail, store.type(), store.name(), getFileStoreRoot(store));
            }
        }

        String s = null;
        switch(s) {
            case "kuku":
                System.out.println("kuku");
                return;
            default:
                System.out.println("unknown");
                return;
        }
    }

    private static String getFileStoreRoot(FileStore fs) {
        String s = fs.toString();
        final Pattern pattern = Pattern.compile("(.*)\\s+\\((.*)\\)");
        final Matcher matcher = pattern.matcher(s);
        if(!matcher.matches()) return "doesn't match";
        String s1 = matcher.group(1);
        if(new File(s1).exists()) return s1;
        String s2 = matcher.group(2);
        if(new File(s2).exists()) return s2;
        return null;
    }
}
