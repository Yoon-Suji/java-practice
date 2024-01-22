package next.reflection;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.*;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("테스트1: 리플렉션을 이용해서 클래스와 메소드의 정보를 정확하게 출력해야 한다.")
    public void showClass() {
        SoftAssertions s = new SoftAssertions();
        Class<Question> clazz = Question.class;

        logger.debug("Class Name: {}", clazz.getName());
        Arrays.stream(clazz.getDeclaredConstructors()).forEach((constructor -> {
            logger.debug("Class Constructor: {}", constructor);
        }));

        Arrays.stream(clazz.getDeclaredMethods()).forEach((method -> {
            logger.debug("Class Method Name: {}", method.getName());
            logger.debug("Class Parameters:");
            Arrays.stream(method.getParameters()).forEach((parameter -> {
                logger.debug("Parameter Name: {}", parameter.getName());
                logger.debug("Parameter Type: {}", parameter.getType());
            }));
            logger.debug("Class Method Return Type {}\n", method.getReturnType());
        }));

        Arrays.stream(clazz.getDeclaredFields()).forEach((field -> {
            logger.debug("Class Field name: {}", field.getName());
            logger.debug("Class Field type: {}\n", field.getType());
        }));
    }

    @Test
    public void constructor() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }
    }
}
