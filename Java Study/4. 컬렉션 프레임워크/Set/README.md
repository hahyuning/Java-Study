## 1. HashSet
- Set 인터페이스를 구현한 가장 대표적인 컬렉션이며, 중복된 요소를 저장하지 않는다.
- 새로운 요소를 추가할 때는 add나 addAll 메서드를 사용하는데, 이미 저장되어 있는 요소와 중복된 요소를 추가한다면 false를 반환한다.
- 저장순서를 유지하지 않으므로 저장순서를 유지하고자 한다면 LinkedHashSet을 사용한다.

<br>

#### equals()와 hashCode()
- HashSet의 add 메서드는 새로운 요소를 추가하기 전에 기존에 저장된 요소와 같은 것인지 판별하기 위해 추가하려는 요소의 equals()와 hashCode()를 호출
- 즉, HashSet의 key로 사용하는 객체의 equals()와 hashCode()를 목적에 맞게 오버라이딩해야 한다.

```java
import java.util.Objects;

class Person {
  String name;
  int age;

  Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public boolean equals(Object obj) {
    if (obj instanceof Person) {
      Person tmp = (Person) obj;
      return name.equals(tmp.name) && age == tmp.age;
    }
    return false;
  }

  // String 클래스의 hashCode() 이용
  public int haseCode() {
    return Objects.hash(name, age); // int hash(Object... values)
  }
}
```

오버라이딩을 통해 작성된 hashCode()는 다음의 세 가지 조건을 만족해야 한다.
1. 실행 중인 애플리케이션 내의 동일한 객체에 대해서 여러번 hashCode()를 호출해도 동일한 int 값을 반환해야 한다.
    - Object 클래스는 객체의 주소로 해시코드를 만들기 때문에 실행할 때마다 해시코드 값이 달라질 수 있다.
    - 따라서, 동일한 멤버변수 값에 대해서 **실행시마다** 동일한 int 값을 반환하지 않을 수 있다.

2. equals()를 이용한 비교에 의해서 true를 얻은 두 객체에 대해 각각 hashCode()를 호출해서 얻은 결과는 반드시 같아야 한다.
3. equals()를 호출했을 때 false를 반환하는 두 객체는 hashCode() 호출에 대해 같은 int 값을 반환하는 경우는 가능하지만,
   해싱을 사용하는 컬렉션의 성능을 향상시키기 위해서는 다른 int 값을 반환하는게 좋다.
    - 서로 다른 객체에 대해서 해시코드값이 중복되는 경우가 많아질수록 해싱을 사용하는 컬렉션의 검색 속도가 떨어진다.

<br>

## 2. TreeSet
- 이진 검색 트리라는 자료구조의 형태로 데이터를 저장하는 컬렉션 클래스
- 중복된 데이터의 저장을 허용하지 않으며, 정렬된 위치에 저장하므로 저장순서를 유지하지 않는다.
- TreeSet에 저장되는 객체는 Comparable을 구현하던가, Comparator를 제공해서 두 객체를 비교할 방법을 알려줘야 한다.
- 그렇지 않으면, TreeSet에 객체를 저장할 때 예외 발생

> 이진 검색 트리
> - 정렬, 검색, 범위검색에 높은 성능을 보이는 자료구조로, TreeSet은 이진 검색 트리의 성능을 향상시킨 `레드-블랙 트리`로 구현되어 있다.
> - 부모노드의 왼쪽에는 부모노드의 값보다 작은 값의 자식노드를, 오른족에는 큰 값의 자식노드를 저장한다.
> - 저장위치를 찾아서 저장해야 하므로 삭제하는 경우 트리의 일부를 재구성해야 하므로 연결리스트보다 추가/삭제 시간은 더 걸리는 대신 배열이나 연결리스트에 비해 검색과 정렬 기능이 더 뛰어나다.

<br>

#### 범위검색
|메서드|설명|
|---|---|
|SortedSet subSet(Object from, Object to)|from과 to 사이의 범위 검색 결과를 반환|
|SortedSet headSet(Object from)|지정된 객체보다 작은 값의 객체들을 반환|
|SortedSet tailSet(Object from)|지정된 객체보다 큰 값의 객체들을 반환|