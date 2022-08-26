package sergg.samples;

import java.lang.reflect.InvocationTargetException;

public class ClassForName {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz1 = Class.forName("java.lang.String");
        System.out.println("class: " + clazz1);
        Class clazz2 = (Class) Class.class.getDeclaredMethod("forName", String.class).invoke(null, "java.lang.String");
        System.out.println("class: " + clazz2);
    }
}
