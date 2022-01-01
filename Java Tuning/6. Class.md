자바 API에는 reflection이라는 패키지가 있는데, 이 패키지에 있는 클래스들을 사용하면 JVM에 로딩되어 있는 클래스와 메서드 정보를 읽어올 수 있다.

## Class 클래스
- 클래스에 대한 정보를 얻을 때 사용한다.
- 생성자는 따로 없으며, Object 클래스에 있는 `getClass()` 메서드를 이용해서 객체를 생성한다.
- [메서드](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)

<br>

## Method 클래스
- 메서드에 대한 정보를 얻을 수 있다.
- 생성자가 없기 때문에 Class 클래스의 getMethods()나, getDeclaredMethod() 메서드를 사용한다.

<br>

## Field 클래스
- 클래스에 있는 변수들의 정보를 제공하기 위해서 사용한다.
- Class 클래스의 getField()나 getDeclaredFields() 메서드를 사용한다.

<br>

#### 잘못 사용 예
```java
public String checkClass(Object obj) {
    if (obj.getClass().getName().equlas("java.math.BigDecimal")) {
        // 데이터 처리
    }
}
```

#### instance of 사용
```java
public String checkClass(Object obj) {
    if (obj instanceof java.math.BigDecimal) {
        // 데이터 처리
    }
}
```

- 로그에서 사용하기 위해서 reflection 관련 클래스를 사용하는 경우, 클래스 객체를 얻기보다는 클래스의 이름을 복사해서 붙여넣는 것이 좋다.
- instanceof를 사용하는 것이 클래스 이름으로 해당 객체의 타입을 비교하는 방법보다 낫다.
- 클래스의 메타 데이터 정보는 JVM의 Perm 영역에 저장되는데, 만약 Class 클래스를 사용하여 엄청나게 많은 클래스를 동적으로 생성하면 OutOfMemoryError가 발생할 수 있다.
