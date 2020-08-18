package sergg.samples.spi.impl;

import sergg.samples.spi.IService;

/**
 * @author <a href="mailto:sergeygomanyuk@yandex.ru">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class ServiceImplOne implements IService {

    private static int instanceNum = 0;


    public ServiceImplOne() {
        instanceNum ++;
    }

    @Override
    public void hello() {
        System.out.println("Hello, it's service: " + ServiceImplOne.class.getSimpleName() + ", instance num: " + instanceNum);
    }
}
