package sergg.samples;

/**
 * Попросить собеседуемого описать, что такое design pattern Singleton и для чего он нужен, какие у него недостатки.
 *
 * Спросить, как Singleton реализуется в Spring.
 *
 * Спросить, какие проблемы видит собеседуемый в реализации Singleton ниже и как исправить релизацию.
 *
 * Наилучший ответ - использование Singleton holder, можно поговорить о недостаках других подходов:
 *

 private static class HOLDER {
   private static Singleton singleton = new Singleton();
 }

 private Singleton() {}

 public static Singleton getInstance() {
   return HOLDER.singleton;
 }

 *
 */
public class Singleton {
    private Singleton helper = null;

    public Singleton getHelper() {
        if (helper == null)
            helper = new Singleton();
        return helper;
    }
}
