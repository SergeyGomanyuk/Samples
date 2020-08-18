package sergg.samples;

import sergg.samples.spi.IService;

import java.util.ServiceLoader;

/**
 * @author <a href="mailto:sergeygomanyuk@yandex.ru">Sergey Gomanyuk</a>
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
