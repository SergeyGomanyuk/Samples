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
package sergg.samples.spi.impl;

import sergg.samples.spi.IService;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class ServiceImplTwo implements IService {

    private static int instanceNum = 0;


    public ServiceImplTwo() {
        instanceNum ++;
    }

    @Override
    public void hello() {
        System.out.println("Hello, it's service: " + ServiceImplTwo.class.getSimpleName() + ", instance num: " + instanceNum);
    }
}
