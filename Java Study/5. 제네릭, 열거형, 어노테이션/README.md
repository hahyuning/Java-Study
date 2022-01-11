# 제네릭

- 다양한 타입의 객체들을 다루는 메서드나 컬렉션 클래스에서 객체의 타입을 컴파일 시에 체크를 해주는 기능
- 객체의 타입 안정성을 높이고 형변환의 번거로움이 줄어든다는 장점
- 따라서, raw 타입 대신 타입 매개변수를 명확히 지정해주는 것이 좋다.

## 객체 생성과 사용

```java
// T: 타입 변수, 임의의 참조형 타입을 의미
class Box<T> {
    T item;

    void setItem(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }
}
```

- 제네릭 클래스의 객체를 생성할 때는 참조변수와 생성자에 타입 T 대신 사용될 실제 타입을 지정하고, 참조변수와 생성자에 대입된 타입은 일치해야 한다. (제네릭 타입 호출)<br>
  `Box<String> box = new Box<String>();`
- 두 제네릭 클래스의 타입이 상속관계에 있고(Box와 FruitBox), 대입된 타입이 같은 것(Apple)은 괜찮다.<br>
  `Box<Apple> appleBox = new FruitBox<Apple>();`

<br>

## 주의점

- 타입 변수 T는 인스턴스 변수로 간주되기 때문에 static 멤버에 타입 변수 T를 사용할 수 없다.
    - static 멤버는 대입된 타입의 종류에 관계없이 동일한 것이어야 하기 때문
- 제네릭 배열을 위한 참조변수를 선언하는 것은 가능하지만, 제네릭 타입의 배열은 생성할 수 없다.
    - new 연산자는 컴파일 시점에 타입 T가 무엇인지 정확히 알아야 하기 때문
    - 같은 이유로 instanceof 연산자도 T를 피연산자로 사용할 수 없다.

```java
class Box<T> {
    T[] itemArr; // T 타입의 배열을 위한 참조변수, OK

    T[] toArray() {
        T[] tmpArr = new T[itemArr.length]; // 제네릭 타입의 배열, 에러 발생
        return tmpArr;
    }
}
```

- 제네릭 배열을 생성해야 할 때는, `Reflection API의 newInstance()`와 같이 동적으로 객체를 생성하는 메서드로 배열을 생성하거나,
- `Object 배열을 생성해서 복사한 다음에 T[]로 형변환`하는 방법을 사용한다.

<br>

## 제네릭 클래스 제한하기

- 제네릭 타입에 `extends`를 사용하면, 특정 타입의 자손들만 대입할 수 있게 제한할 수 있다.
- 인터페이스를 구현해야 한다는 제약이 필요할 때도 extends 사용<br>
  `Class FruitBox<T extends Fruit>`

<br>

#### 와일드 카드

- &#60;? extends T&#62; : 와일드 카드의 상한 제한, T와 그 자손들만 가능
- &#60;? super T&#62; : 와일드 카드의 하한 제한, T와 그 조상들만 가능
- &#60;?&#62; : 제한 없음, 모든 타입 가능 (&#60;? extends Object&#62; 와 동일)

> 타입이 다른 제네릭 클래스끼리의 형변환은 불가능하지만, 와일드 카드가 포함된 제네릭 타입으로의 형변환은 가능하다.

```java
class Test {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings); // 컴파일 에러
    }

    // List<Object>를 받는 메서드
    private static void unsafeAdd(List<Object> list) {
        // 생략
    }
}
```

<br>

## 제네릭 메서드

- 메서드의 선언부에 제네릭 타입이 선언된 메서드로, 제네릭 타입은 반환 타입 바로 앞에 선언한다.
- static 멤버에는 타입 매개변수를 사용할 수 없지만, 메서드에 제네릭 타입을 선언하고 사용하는 것은 가능하다.
    - 메서드에 선언된 제네릭 타입은 지역변수를 선언한 것과 같다.

<br>

#### ArrayList 구현 코드 중 일부

- T[] toArray(T[] a) : 이미 생성된 배열 a를 매개변수로 받아서, arr의 내용을 복사해서 반환하는 메서드
- 상위타입으로 들어오는 객체에 대해 데이터를 담을 수 있도록 별도의 제네릭 메소드로 구현
    - E를 Integer라고 가정하고, T를 Object라고 가정하면 Object는 Integer보다 상위 타입이므로 Object 안에 Integer 데이터를 담을 수 있다.

```java
class ArrayList<E> implements List<E> {

    Object[] arr;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {

        if (a.length < size) {
            // copyOf(원본 배열, 복사할 길이, Class<? extends T[]> 타입)
            return (T[]) Arrays.copyOf(arr, size, a.getClass());
        }

        // arraycopy(원본 배열, 시작위치, 복사할 배열, 시작위치, 복사할 요소 수)
        System.arraycopy(arr, 0, a, 0, size);
        return a;
    }
}
```

> #### copyOf()
> 복사할 배열이 원본 배열보다 작은 경우, 크기에 맞게 복사할 배열의 공간을 재할당 하면서 모든 요소를 복사<br>
> 클래스 타입을 지정해서, T의 상위타입의 배열에 요소를 복사해서 반환 가능하다.<br>
> 이후 반환된 타입을 T[]로 형변환하는 과정에서 발생하는 경고를 억제하기 위해 @SuppressWarnings("unchecked") 사용

<br>

## 제네릭 싱글턴 팩토리 패턴

#### 예) Collections.reversOrder()

- reversOrder()는 ReverseComparator의 싱글톤 객체 REVERSE_ORDER를 Comparator<T> 타입으로 형변환 해주는 역할
- 제네릭을 사용하지 않았다면 요청 타입마다 형변환하는 정적 팩토리를 따로 만들어야 한다.

```java
class Collections {
    
    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> reverseOrder() {
        return (Comparator<T>) ReverseComparator.REVERSE_ORDER;
    }

    private static class ReverseComparator implements Comparator<Comparable<Object>> {

        static final ReverseComparator REVERSE_ORDER = new ReverseComparator();

        public int compare(Comparable<Object> c1, Comparable<Object> c2) {
            return c2.compareTo(c1);
        }
    }
}
```

#### 재귀적 타입 한정

- 자기 자신이 들어간 표현식을 사용하여 타입 매개변수의 허용 범위를 한정한다.
- 주로 Comparable과 함께 쓰인다. `public static <E extends Comparable<E>> E max(Collection<E> c);`
- <E extends Comparable<E>>는 모든 타입 E는 자신과 비교할 수 있음을 의미

# 열거형

- 서로 관련된 상수를 편리하게 선언하기 위한 것으로, 여러 상수를 정의할 때 사용한다.
- 자바의 열거형은 타입에 안전한 열거형으로, 실제 값이 같아도 타입이 다르면 컴파일 에러가 발생한다.
- 열거형 상수 간의 비교에는 ==을 사용할 수 있지만, 비교 연산자는 사용할 수 없고 compareTo()는 사용가능하다.

```java

@Getter
@RequiredArgsConstructor
public enum Role {
    // Role 클래스의 static 객체
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    // 필드
    private final String key;
    private final String title;
}
```

<br>

## Enum 클래스

- 모든 열거형의 조상

|메서드|설명|
|---|---|
|Class<E> getDeclaringClass()|열거형의 Classs 객체 반환|
|String name()|열거형 상수의 이름을 문자열로 반환|
|int ordinal()|열거형 상수가 정의된 순서 반환|
|T valueOf(Class<T> enumType, String name)|지정된 열거형에서 name과 일치하는 열거형 상수 반환|

<br>

#### Enum 추상 클래스 구현

```java
abstract class MyEnum<T extends MyEunm<T>> implements Comparable<T> {
    static int id = 0;

    int ordinal;
    String name = "";

    public int ordinal() {
        return ordinal;
    }

    MyEnum(String name) {
        this.name = name;
        ordinal = id++;
    }

    public int compareTo(T t) {
        return ordinal - t.ordinal();
    }
}
```

- MyEnum<T>로 선언하면, 타입 T에 ordinal()이 정의되어 있는지 확인할 수 없으므로 에러 발생
- MyEnum<T extends MyEnum<T>> : 타입 T가 MyEnum<T>의 자손이어야 한다는 의미로, ordinal()이 정의되어 있다.

> EnumSet, EnumMap

<br>

# 어노테이션

- 주석처럼 프로그래밍 언어에 영향을 미치지 않으면서, 다른 프로그램에게 유용한 정보를 제공한다.
- JDK에서 제공하는 표준 어노테이션은 주로 컴파일러를 위한 것으로 컴파일러에게 유용한 정보를 제공하고,
- 새로운 어노테이션을 정의할 때 사용하는 메타 어노테이션을 제공한다.

<br>

## 표준 어노테이션

#### @Override

- 메서드 앞에만 붙일 수 있으며, 조상의 메서들르 오버라이딩하는 것이라는 걸 컴파일러에게 알려주는 역할
- 컴파일러가 같은 이름의 메서드가 조상에 있는지 확인하고 없으면 에러메시지 출력
- 구체 클래스에서 상위 클래스의 추상 메서드를 재정의할 때 @Override를 달지 않아도 컴파일러가 알려준다.
    - but, java8부터 디폴트 메서드를 지원하면서 인터페이스의 추상 메서드를 구현한 메서드에도 @Override를 붙여주는게 좋다.

#### @Deprecated

- 더이상 사용되지 않는 필드나 메서드에 붙이며, 이 어노테이션이 붙은 대상은 다른 것으로 대체되었으니 더 이상 사용하지 않는다는 것을 권한다는 의미
- 기존의 것 대신 새로 추가된 개선된 기능을 사용하도록 유도

#### @FunctionalInterface

- 함수형 인터페이스를 선언할 때 붙이며, 컴파일러가 함수형 인터페이스를 올바르게 선언했는지 확인하고 잘못된 경우 에러를 발생시킨다.

#### @SuppressWarning

- 컴파일러가 보여주는 경고 메시지가 나타나지 않게 억제해준다.

|경고 메시지|설명|
|---|---|
|deprecation|@Deprecated가 붙은 대상을 사용해서 발생하는 경고 억제|
|unchecked|제네릭으로 타입을 지정하지 않았을 때 발생하는 경고 억제|
|rawtypes|제네릭을 사용하지 않아서 발생하는 경고 억제|
|varargs|가변인자의 타입이 제네릭 타입일 때 발생하는 경고 억제|

<br>

#### @SafeVarargs

- 메서드에 선언된 가변인자의 타입이 non-reifiable 타입일 경우, 해당 메서드를 선언하는 부분과 호출하는 부부에서 unchecked 경고가 발생하는데,
- 해당 코드에 문제가 없는 경우 이러한 경고를 억제하기 위해 사용한다.
- static이나 final이 붙은 메서드와 생성자에만 붙일 수 있다. (오버라이드될 수 있는 메서드에는 사용할 수 없다는 뜻)

> 컴파일 후에도 제거되지 않는 타입을 reifiable 타입, 제거되는 타입을 non-reifiable 타입이라고 한다. (제네릭)

#### @SafeVarargs vs @SuppressWarning("unchecked")

- 메서드를 선언할 때 @SafeVarargs를 붙이면, 이 메서드를 호출하는 곳에서 발생하는 경고도 억제된다.
- @SuppressWarning("unchecked")로 경고를 억제하려면, 메서드 선언뿐만 아니라 메서드가 호출되는 곳에도 어노테이션을 붙어야 한다.
- @SafeVarargs로 "unchecked" 경고를 억제할 수 있지만, "varargs" 경고는 억제할 수 없기 때문에,
- **@SafeVarargs와 @SuppressWarning("varargs")를 같이 사용**한다.

<br>

#### 가변인자와 제네릭 혼용

- 가변인자 메서드를 호출하면 가변인자를 담기 위한 배열이 만들어지는데, 이때 가변인자의 매개변수에 제네릭 클래스나 타입변수가 포함되면 컴파일 경고 발생
- stringLists는 내부적으로는 List<String> 타입이지만, 런타임에는 제네릭 타입이 소거되므로 같은 List로만 인식되어 Object[]에 할당 가능
- stringLists[0]을 하면 List가 나오고 List의 0번째 인덱스 위치의 객체를 호출해 눈에 보이지 않는 String으로 형변환

```java
class Arrays {
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> List<T> asList(T... a) {
        return new ArrayList<>(a);
    }

    private static class ArrayList<E> extends AbstractList<E>
            implements RandomAccess, java.io.Serializable {
        private final E[] a;

        ArrayList(E[] array) {
            a = Objects.requireNonNull(array);
        }
    }
}
```

- 가변인자인 동시에 제네릭 타입이므로, 내부적으로 Object[] 배열이 생성되는데, 생성된 배열로 ArrayList<T> 를 생성하는 것이 위험하다고 경고
- But, 컴파일러가 체크해서 타입 T가 아닌 다른 타입이 들어가지 못하게 하므로 문제가 발생하지 않는다.
- 즉, 메서드 내에서 이 배열에 아무것도 저장하지 않고, 배열의 참조가 밖으로 노출되지 않는거나, 순수하게 메서드의 생산자 역할만 하면 메서드는 안전하다.

<br>

## 어노테이션 정의
