package sergg.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InstanceOfTest {
    public static void main(String[] args) {
        Test1 t = new Test1();
        System.out.println(Testable1.class.isInstance(t));
        System.out.println(Testable.class.isInstance(t));
        System.out.println(Test.class.isInstance(t));

        List l = new ArrayList();
        l.add(t);

        System.out.println(l.stream().filter(Testable1.class::isInstance).collect(Collectors.toList()));
        System.out.println(l.stream().filter(Testable.class::isInstance).collect(Collectors.toList()));
        System.out.println(l.stream().filter(Test.class::isInstance).collect(Collectors.toList()));

    }

    public interface Testable {

    }


    public interface Testable1 {

    }

    public static class Test implements Testable {

    }

    public static class Test1 extends Test implements Testable1 {

    }
}
