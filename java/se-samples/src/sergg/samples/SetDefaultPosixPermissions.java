package sergg.samples;

import sergg.util.FileUtil;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @version $Revision:$
 */
public class SetDefaultPosixPermissions {
    public static void main(String[] args) throws IOException {
        FileUtil.setDefaultPosixPermissions(Paths.get(args[0]));
    }
}
