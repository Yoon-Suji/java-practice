# Java Reflection

## Java Relection

구체적인 class Type을 알지 못하여도 method, type, variable에 접근할 수 있도록 해주는 자바 API. 컴파일된 바이트코드를 통해 동적으로 특정 class 정보를 추출할 수 있다.

**바인딩**

프로그램에 사용된 구성요소 의 실제 값 또는 property를 결정짓는 행위. 즉 사용되는 변수나 메소드 등 모든 것들이 결정되도록 연결해주는 것

1. 정적 바인딩
    1. 컴파일 시간에 결정, 프로그램 실행시에도 불변, private final static이 붙은 메소드
    2. 오버로딩이 이 예시이다. 컴파일 과정에서 어떤 메소드를 호출할 지 결정하기 때문에 컴파일 에러를 발생시킨다.
2. 동적바인딩
    1. 실행시간에 결정, 다형성 및 상속이 가능한 이유
    2. 오버라이딩이 이 예시이다. 상속을 이용하여 부모에서 정의한 메소드를 자식에서 오버라이딩 할 때, 해당 메소드를 이용할 경우 실행시 결정된다.
    3. 성능상 오버헤드가 있지만 상속과 다형성 등 다양한 기능을 사용할 수 있는 장점이 있다.

즉, Reflection은 Runtime시 Class Type을 모르는 채로 객체를 생성하고 이용하기 때문에 동적바인딩을 제공한다.

스프링 프레임워크에서 @Autowired가 붙은 생성자를 보고 안에있는 매개변수를 검토한다. 이 매개변수들은 스프링이 객체를 만들 때 주입해야 할 의존성으로 나타낸다. 빈을 스프링 컨테이너에서 찾아내고 어떤 객체를 필요로 하는지 파악한다.

### **Class 객체 획득 방법**

```java
public class RefectionEx {
    public static void main(String[] args) {
        Class<?> carClass = Car.class;
        System.out.println("FQCN : " + carClass.getName());
    }
}
       
// 출력 결과
// FQCN : reflection.Car
```

이 외에도 Class.forName(), getClass()를 사용하여 가져올 수 있다.

### 요구사항 1 - ****클래스 정보 출력****

- `java.lang.class.getName()`: 클래스의 이름 리턴
- `java.lang.class.getDeclaredConstrucotrs()`: 클래스에서 선언된 `Constructor` 객체의 배열 리턴
- `java.lang.class.getConstructors()`: 클래스에서 접근 가능한 `public` `Constructor` 객체의 배열 리턴
- `java.lang.class.getDeclaredMethods()`: 클래스에서 선언한 `Method` 객체의 배열 리턴 - public, protected, default, private (상속받은 메서드는 X)
- `java.lang.class.getMethods()`: 클래스에서 접근 가능한 `public` `Method` 객체의 배열 리턴 - 상속받은 메서드도 포함
    - `getName()`
    - `getType()`
    - `getParameters()`
        - `getName()`
        - `getType()`
    - `getReturnType()`
- `java.lang.class.getDeclaredFields()`: 클래스나 인터페이스에서 정의한 필드의 배열 리턴
    - `getName()`
    - `getType()`

### 요구사항 2 - t****est로 시작하는 메소드 실행****

- `Method.invoke(Object obj, Object... args)` : 파라미터로 전달한 Object의 해당 메서드를 실행
- Class가 기본 생성자를 가질 경우 `newInstance()`를 활용해 인스턴스를 생성할 수 있다.
    - deprecated since version 9

### 요구사항 3 - ****@Test 애노테이션 메소드 실행****

어노테이션은 프로그램(컴파일러)에게 유용한 정보를 제공하기 위해 사용되는 것으로, 주석과 같은 의미를 가진다.

- 컴파일러에게 문법 에러를 체크하도록 정보를 제공한다.
- 프로그램을 빌드할 때 코드를 자동으로 생성할 수 있도록 제공한다.
- 런타임에 특정 기능을 실행하도록 정보를 제공한다.
- `getDeclaredAnnotations`: 어노테이션의 정보를 가져올 수 있다.

### 요구사항 4 - ****private field에 값 할당****

* 리플렉션을 활용하여 Field타입의 오브젝트를 획득하여 객체 필드에 직접 접근 가능하다.
  - `field.setAccessible(true)`로 필드에 대한 접근이 가능해진다


### 요구사항 5 - ****인자를 가진 생성자의 인스턴스 생성****

- 기본 생성자가 없는 경우 `clazz.newInstance()`로 인스턴스 생성할 수 없음
- `constructor.newInstance(Object… args)`로 인스턴스 생성 가능

## Trouble Shooting

- `getDeclaredAnnotations` 메서드로 왜 `@MyTest` 어노테이션이 안가져와지는가

    ```java
    Class aClass = TheClass.class;
    Annotation[] annotations = aClass.getAnnotations();
    ```

  의 형식으로 클래스 어노테이션을 가져왔기 때문이다.

    ```java
    Method method = ... //obtain method object
    Annotation[] annotations = method.getDeclaredAnnotations();
    ```

  이와같이 메소드에 `getDeclaredAnnotations()` 을 이용하여 메소드에 선언된 어노테이션 정보를 가져올 수 있다.

***
## Reference

https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Class.html
