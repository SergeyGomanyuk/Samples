/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p>
 * $Id:$
 */
package sergg.samples;

import sergg.util.FileUtil;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class SetDefaultPosixPermissions {
    public static void main(String[] args) throws IOException {
        FileUtil.setDefaultPosixPermissions(Paths.get(args[0]));
    }
}
