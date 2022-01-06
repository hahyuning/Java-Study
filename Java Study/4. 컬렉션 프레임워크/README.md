# 컬렉션 프레임워크

## 1. 핵심 인터페이스

#### Collection
- List와 Set 인터페이스의 공통된 부분을 뽑아서 정의한 인터페이스
- Map 인터페이스는 다른 형태로 컬렉션을 다루기 때문에 같은 상속계층도에 포함되지 않는다.
- 컬렉션 클래스에 저장된 데이터를 읽고, 추가하고 삭제하는 등 컬렉션을 다루는데 가장 기본적인 메서드들을 정의

---

#### List
- 순서가 있는 데이터의 집합으로, 데이터의 중복을 허용한다.
- 구현 클래스: ArrayList, LinkedList, Stack, Vector 등

#### Set
- 순서를 유지하지 않는 데이터의 집합으로, 데이터의 중복을 허용하지 않는다.
- 구현 클래스: HashSet, TreeSet 등

#### Map
- 키와 값의 쌍으로 이루어진 데이터의 집합
- 순서는 유지되지 않으며, 키는 중복을 허용하지 않고, 값은 중복을 허용한다.
  - value() 의 반환타입은 Collection, keySet() 의 반환타입은 Set
- 구현 클래스: HashMap, TreeMap, Hashtable, Properties 등

> 컬렉션 프레임워크의 모든 컬렉션 클래스들은 List, Set, Map 중 하나를 구현하고 있다.

---

#### Map.Entry 
- Map 인터페이스의 내부 인터페이스
- Map에 저장되는 key-value 쌍을 다루기 위해 내부적으로 Entry 인터페이스를 정의해 놓았다.
```java
public interface Map {
    
    public static interface Entry {
        Object getKey(); // Entry의 key 객체를 반환
        Object getValue(); // Entry의 value 객체를 반환
        Object setValue(Object value); // Entry의 value 객체를 지정된 객체로 변환
        
        boolean equals(Object o); // 동일한 Entry인지 비교
        int hashCode(); // Entry의 해시코드 반환
    }
    
}
```

<br>

## 2. Iterator
- 컬렉션에 저장된 요소에 저근하는데 사용되는 인터페이스
- Collection 인터페이스에 Iterator(Iterator를 구현한 클래스의 인스턴스)를 반환하는 iterator()를 정의해 놓았다.
- 컬렉션 클래스에 대해 iterator()를 호출하여 Iterator를 얻은 다음 반복문을 사용해서 컬렉션 클래스의 요소를 읽어올 수 있다.
- Iterator를 사용해서 컬렉션의 요소를 읽어오는 방법을 표준화함으로써, 코드의 일관성을 유지하여 재사용성을 극대화할 수 있다.

<br>

|메서드|설명|
|---|---|
|boolean hasNext()|읽어올 요소가 남아있는지 확인|
|Object next()|다음 요소를 읽어오는 메서드로, 호출하기 전 hasNext()를 호출해서 읽어올 요소가 있는지 확인하는 것이 안전|
|void remove()|next()로 읽어온 요소를 삭제하며, **next()를 호출한 다음에 remove()를 호출해야 한다.**|

```java
List list = new ArrayList();
Iterator iter = list.iterator();

while (it.hasNext()) {
    System.out.println(it.next());
}
```

<br>

- Map 인터페이스를 구현한 컬렉션 클래스는 iterator()를 직접 호출할 수 없고, keySet()이나 entrySet()과 같은 메서드를 통해 키와 값을 각각 따로 Set 형태로 얻어온 후에
다시 iterator()를 호출해야 Iterator을 얻을 수 있다.
- Set 클래스들은 각 요소간의 순서가 유지되지 않기 때문에 Iterator를 통해서 저장된 요소들을 읽어와도 처음에 저장된 순서와 같지 않다.
```java
Map map = new HashMap();

Iterator iter = map.entrySet().iterator();
```

<br>

#### ListIterator
- Iterator를 상속받아서 기능을 추가한 것으로, 양방향으로 이동이 가능하다.
- ArrayList나 LinkedList와 같이 List 인터페이스를 구현한 컬렉션에서만 사용 가능
- 이동하기 전에 반드시 hasNext()나 hasPrevious()를 호출해서 이동할 수 있는지 확인해야 한다.

<br>

## 3. Arrays
배열을 다루는데 유용한 메서드가 정의되어 있는 클래스

#### 배열의 복사
- copyOf(): 배열 전체를 복사해서 새로운 배열을 만들어 반환
- copyOfRange() 배열의 일부를 복사해서 새로운 배열을 만들어 반환

#### 배열 채우기
- fill(): 배열의 모든 요소를 지정된 값으로 채운다.
- setAll(): 배열을 채우는 데 필요한 함수형 인터페이스를 매개변수로 받는다.

#### 정렬과 검색
- sort(): 배열을 정렬할 때 사용한다.
- binarySearch(): 배열에서 지정된 값이 저장된 위치를 찾아서 반환하는데,
  반드시 배열이 정렬된 상태여야 하며 일치하는 요소들이 여러개 있다면 이 중 어떤 것의 위치가 반환되는지는 알 수 없다.

#### 문자열의 비교와 출력
- equals(): 두 배열에 저장된 모든 요소를 비교한다.
    - deepEquals(): 다차원 배열의 요소를 재귀적으로 비교
- toString(): 배열의 모든 요소를 문자열로 출력한다.
    - deepToString(): 배열의 모든 요소를 재귀적으로 접근해서 문자열을 구성하므로, 다차원 배열에도 동작

## List로 변환: asList(Object... a)
- 배열을 List에 담아서 반환한다.
- 매개변수의 타입이 가변인수라서 배열 생성 없이 저장할 요소들만 나열하는 것도 가능하다.
- asList()가 반환한 List의 크기는 변경할 수 없기 때문에 추가, 삭제가 불가능하다.

> 크기를 변경할 수 있는 List가 필요한 경우
> `List list = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));` 와 같이 사용해야 한다.

## 그 외
- parallelXXX(): 보다 빠른 결과를 얻기 위해 여러 쓰레드가 작업을 나누어 처리하도록 한다.
- spliterator(): 여러 쓰레드가 처리할 수 있게 하나의 작업을 여러 작업으로 나누는 Spliterator를 반환한다.
- stream(): 컬렉션을 스트림으로 변환한다.

<br>

## 4. Comparator와 Comparable
- Comparator와 Comparable 모두 인터페이스로 컬렉션을 정렬하는데 필요한 메서드 정의
  - Comparable: 기본 정렬기준을 구현하는데 사용
  - Comparator: 기본 정렬기준 외에 다른 기준으로 정렬하고자 할 때 사용
- Wrapper 클래스와 String, Data, File 등의 클래스들은 Comparable을 구현하고 있으며, 기본적으로 오름차순으로 정렬 가능

<br>

#### compare()과 compareTo()
- 선언형태와 이름이 약간 다를 뿐 두 객체를 비교하는 기능
- 비교하는 두 객체가 같으면 0, 비교하는 값보다 작으면 음수, 크면 양수를 반환한다.
```java
public interface Comparator {
    int compare(Object o1, Object o2);
    boolean equals(Object obj);
}

public interface Comparable {
    public int compareTo(Object o);
}
```

#### Arrays.sort()
- Comparator을 지정해주지 않으면 저장하는 객체(주로 Comparable을 구현한 클래스의 객체)에 구현된 내용에 따라 정렬
```java
import java.util.Arrays;
import java.util.Comparator;

class ComparatorTest {
  public static void main(String[] args) {
    String[] arr = {"cat", "dog", "lion", "tiger"};

    Arrays.sort(arr); // String의 Comparable 구현에 의한 정렬
    Arrays.sort(arr, String.CASE_INSENSITIVE_ORDER); // 대소문자 구분 안함
    Arrays.sort(arr, new Descending()); // 역순 정렬
  }
}

class Descending implements Comparator<T> {
  public int compare(Object o1, Object o2) {
    // object 타입은 compareTo()를 바로 호출할 수 없으므로, Comparable로 형변환
    // 즉, 해당 object가 Comparable을 구현했는지 확인하고 Comparable의 compareTo() 호출
    if (o1 instanceof Comparable && o2 instanceof Comparable) {
      Comparable c1 = (Comparable) o1;
      Comparable c2 = (Comparable) o2;
      return c1.compareTo(c2) * -1;
    }
    return -1;
  }
}
```

<br>

## 5. Collections
- Arrays가 배열과 관련된 메서드를 제공하는 것처럼, Collections는 컬렉션과 고나련된 메서드를 제공

<br>

#### 컬렉션의 동기화
- 멀티 쓰레드 프로그래밍에서는 하나의 객체를 여러 쓰레드가 동시에 접근할 수 있기 때문에, 데이터의 일관성을 유지하기 위해서는 공유되는 객체에 동기화 필요
- Vector와 Hashtable과 같은 구버전 클래스들은 자체적으로 동기화 처리가 되어 있다.
  - 멀티쓰레드 프로그래밍이 아닌 경우에는 불필요한 기능이 되어 성능을 떨어뜨리는 요인
- ArrayList와 HashMap과 같은 컬렉션은 동기화를 자체적으로 처리하지 않고, 필요한 경우에만 동기화 메서드를 이용해서 동기화 처리가 가능하도록 변경

`static Collection synchronizedCollection(Collection c)`

#### 변경불가 컬렉션 만들기
- 컬렉션에 저장된 데이터를 보호하기 위해서 컬렉션을 읽기 전용으로 만든다.

`static Collection unmodifiableCollection(Collection c)`

#### 싱글톤 컬렉션 만들기
- 단 하나의 객체만을 저장하는 컬렉션을 만들때 사용한다.
- 매개변수로 저장할 요소를 지정하면 해당 요소를 저장하는 컬렉션을 반환하고, 반환된 컬렉션은 변경할 수 없다.

`static List singletonList(Object o)`<br>
`static Set singleton(Object o)`<br>
`static Map singletonMap(Object key, Object value)`

#### 한 종류의 객체만 저장하는 컬렉션 만들기
- 컬렉션에 지정된 종류의 객체만 저장할 수 있도록 제한한다.

`static Collection checkedCollection(Collection c, Class type)`