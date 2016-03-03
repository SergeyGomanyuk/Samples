package sergg.samples.nio;

import org.junit.Test;

import java.nio.file.Paths;

/**
 * Created by sergeygomanyuk on 09.02.16.
 */
public class FileUtilTest {

    @Test
    public void testGetDirectories() throws Exception {
        System.out.println(FileUtil.getDirectories(Paths.get(".")));
    }

    @Test
    public void testGetEntries() throws Exception {
        System.out.println(FileUtil.getEntries(Paths.get(".")));
    }
}
