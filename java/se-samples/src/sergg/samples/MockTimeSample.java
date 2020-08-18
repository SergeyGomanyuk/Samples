package sergg.samples;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author <a href="mailto:sergeygomanyuk@yandex.ru">Sergey Gomanyuk</a>
 * @version $Revision:$
 */

//@RunWith(PowerMockRunner.class) // Use PowerMock’s runner
//@PrepareForTest({MyClass.class}) // Prepare the class that calls a static method
public class MockTimeSample {
    public static void main(String[] args) {
        System.out.println("current time is: " + new Date());
        Instant.now(Clock.fixed(
                Instant.parse("2018-08-22T10:00:00Z"),
                ZoneOffset.UTC));
        System.out.println("current time is: " + new Date());
    }
}
//@RunWith(PowerMockRunner.class) // Use PowerMock’s runner
//@PrepareForTest({MyClass.class}) // Prepare the class that calls a static method
//public class MyClassTest {
//
//    @Test
//    public void meeningfulTestNameGoesHere() {
//        // given
//        MyClass myClass = new MyClass();
//
//        // when
//        PowerMockito.mockStatic(System.class);
//        when(System.currentTimeMillis()).thenReturn((long) 1);
//
//        myClass.myMethod();
//
//        // then
//        // Verify or assert something
//    }
//}