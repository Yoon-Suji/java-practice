package next.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Junit4Runner {
    @Test
    public void run() throws Exception {
        Class clazz = Junit4Test.class;

        Arrays.stream(clazz.getDeclaredMethods()).filter((method -> {
            return method.isAnnotationPresent(MyTest.class);
        })).forEach((method -> {
            try {
                method.invoke(clazz.newInstance());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}


