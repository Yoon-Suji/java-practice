package next.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Junit3Runner {
    @Test
    public void runner() throws Exception {
        Class clazz = Junit3Test.class;

        Arrays.stream(clazz.getDeclaredMethods()).filter((method -> {
            return method.getName().startsWith("test");
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
