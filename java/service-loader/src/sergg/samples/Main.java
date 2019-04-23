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

import sergg.samples.spi.IService;

import java.util.ServiceLoader;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class Main {
    public static void main(String[] args) {
        loadServices();
        loadServices();
        loadServices();
    }

    private static void loadServices() {
        ServiceLoader<IService> services = ServiceLoader.load(IService.class);
        for(IService s : services) {
            s.hello();
        }
        for(IService s : services) {
            s.hello();
        }
    }
}
