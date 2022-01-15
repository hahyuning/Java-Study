# Collection
![image](https://user-images.githubusercontent.com/60869749/147850189-3f0b90ed-2cbb-4a97-96ba-3712f13f6a47.png)

## 인터페이스
- `Collection`: 가장 상위 인터페이스

<br>

- `Set`: 중복을 허용하지 않는 집합을 처리하기 위한 인터페이스
- `SortedSet`: 오름차순을 갖는 Set 인터페이스

<br>

- `List`: 순서가 있는 집합을 처리하기 위한 인터페이스
  - 인덱스가 있어 위치를 지정하여 값을 찾을 수 있고, 중복을 허용한다.
- `Queue`: 여러 개의 객체를 처리하기 전에 담아서 처리할 때 사용하기 위한 인터페이스
  - FIFO: 처음 추가되는 객체가 처음 삭제되는 것

<br>

- `Map`: 키와 값의 쌍으로 구현된 객체의 집합을 처리하기 위한 인터페이스 
  - 중복되는 키를 허용하지 않는다.
- `SortedMap`: 키를 오름차순으로 정렬하는 Map 인터페이스

<br>

## 1. Set 인터페이스
중복이 없는 집합 객체를 만들 때 유용하다.

#### 구현 클래스
- `HashSet`: 데이터를 **해시 테이블**에 담으며, **순서 없이** 저장한다.
- `TreeSet`: 데이터를 **red-black 트리**에 담으며, **값에 따라서** 순서가 정해진다.
  - 데이터를 담으면서 동시에 정렬하기 때문에 HashSet 보다 성능상 느리다.
- `LinkedHashSet`: 데이터를 **해시 테이블에** 담으며, **저장된 순서에 따라서** 순서가 결정된다.

<br>

> #### red-black 트리: 이진 트리 구조로 데이터를 담는 자료구조
> - 각각의 노드는 검은색이나 붉은색이어야 한다.
> - 가장 상위 노드와 가장 말단 노드는 검은색이다.
> - 붉은 노드는 검은 하위 노드만을 가진다. (검은 노드는 붉은 상위 노드만을 가진다.)
> - 모든 말단 노드로 이동하는 경로의 검은 노드 수는 동일하다.

<br>

#### 속도 비교
- 데이터 추가: HashSet과 LinkedHashSet의 성능이 비슷하고, TreeSet의 순서로 성능 차이가 발생한다.
  - 저장되는 데이터의 크기를 알고 있는 경우에는 객체 생성 시 크기를 미리 지정하는 거싱 성능상 유리
- 데이터 조회: LinkedHashSet이 가장 빠르고, HashSet, TreeSet 순으로 느려진다.

<br>

#### TreeSet

```java
public class TreeSet<E> extends AbstractSet<E> 
        implements NavigableSet<E>, Cloneable, Serializable {
    
}
```

NavigableSet 인터페이스는 특정 값보다 큰 값이나 작은 값, 가장 큰 값, 가장 작은 값 등을 추출하는 메서드를 선언해 놓았기 때문에 
**데이터를 순서에 따라 탐색하는 작업이 필요할 때는 TreeSet**을 사용하는 것이 좋다.

<br>

## 2. List 인터페이스
담을 수 있는 크기가 자동으로 증가되므로, 데이터의 개수를 확실히 모를 때 유용하다.

#### 구현 클래스
- `Vector`: 객체 생성시에 크기를 지정할 필요가 없는 배열 클래스 (예전 버전)
- `ArrayList`: Vector와 비슷하지만, 동기화 처리가 되어 있지 않다.
- `LinkedList`: ArrayList와 동일하지만, Queue 인터페이스를 구현했기 때문에 FIFO 큐 작업을 수행한다.

<br>

#### 속도 비교
- 데이터 추가: 큰 차이가 없다.
- 데이터 조회: ArrayList의 속도가 가장 빠르고, LinkedList, Vector 순으로 느려진다.
  - LinkedList는 Queue 인터페이스를 상속받기 때문에, get() 대신에 peek() 이나 poll() 사용
  - Vector는 여러 스레드에서 접근할 경우를 방지하기 위해 get() 메서드에 synchronized가 선언되어 있어, 성능 저하 발생
- 데이터 삭제: ArrayList와 Vector는 첫 번째 데이터를 삭제하는 속도와 마지막 데이터를 삭제하는 속도의 차이가 크지만, LinkedList는 별 차이가 없다.
  - ArrayList와 Vector는 첫 번째 데이터를 삭제하면 모든 데이터의 위치를 변경해야 한다.

<br>

## 3. Map 인터페이스
Key와 Value 쌍으로 저장되는 구조체로, 단일 객체만 저장되는 다른 컬렉션 API 들과 따로 분리되어 있다.

#### 구현 클래스
- `Hashtable`: 데이터를 해시 테이블에 담는다. (예전 버전)
  - 내부에서 관리하는 해시 테이블 객체가 **동기화**되어 있으므로, 동기화가 필요한 부분에서 사용한다.
- `HashMap`: 데이터를 해시 테이블에 담는다.
  - HashTable과의 차이점은 **null 값을 허용**한다는 것과 **동기화되어 있지 않다는 것**
- `TreeMap`: 데이터를 red-black 트리에 담는다.
  - TreeSet과의 차이점은 **키에 의해서** 순서가 정해진다는 것
- `LinkedHashMap`: HashMap과 거의 동일하며 데이터를 **이중 연결 리스트**에 담는다.

<br>

#### 속도 비교
- 데이터 추가: 큰 차이가 없다.
- 데이터 조회: 대부분의 클래스들이 동일하지만, 트리 형태로 처리하는 TreeMap 클래스가 가장 느리다.

<br>

## 4. Queue 인터페이스
- 데이터를 담아 두었다가 먼저 들어온 데이터부터 처리하기 위해서 사용한다.
- List의 가장 큰 단점은 데이터가 많은 경우 처리 시간이 늘어난다는 점
  - 가장 앞에 있는 데이터를 지우면 그 다음 데이터부터 마지막 데이터까지 한 칸씩 옮기는 작업을 수행
  - 데이터가 많으면 많을수록 가장 앞에 있는 데이터를 지우는데 소요되는 시간이 증가된다.

#### 구현 클래스
#### java.util 패키지: 일반적인 목적이 큐
- `LinkedList`
- `PriorityQueue`: 큐에 추가된 순서와 상관없이 먼저 생성된 객체가 먼저 나오도록 되어 있는 큐

<br>

#### java.util.concurrent 패키지: 컨커런트 큐
- `LinkedBlockingQueue`: 저장할 데이터의 크기를 선택적으로 정할 수 있는 FIFO 기반의 링크 노드를 사용하는 블로킹 큐
- `ArrayBlockingQueue`: 저장되는 데이터의 크기가 정해져 있는 FIFO 기반의 블로킹 큐
- `PriorityBlockingQueue`: 저장되는 데이터의 크기가 정해져 있지 않고, 객체의 생성순서에 따라서 순서가 저장되는 블로킹 큐
- `DelayQueue`: 큐가 대기하는 시간을 지정하여 처리하도록 되어 있는 큐
- `SynchronousQueue`: put() 메서드를 호출하면 다른 스레드에서 take() 메서드가 호출될 때까지 대기하도록 되어 있는 큐
  - 저장되는 데이터가 없고, 제공하는 대부분의 메서드는 0이나 null 리턴

> 블로킹 큐: 크기가 지정되어 있는 큐에 더 이상 공간이 없을 때, 공간이 생길 때까지 대기하도록 만들어진 큐

<br>

## 5. 동기화
- 동기화되지 않은 클래스: HashSet, TreeSet, LinkedHashSet, ArrayList, LinkedList, HashMap, TreeMap, LinkedHashMap
- 동기화되어 있는 클래스: Vector, Hashtable
- Collection 클래스에는 최신 버전 클래스들의 동기화를 지원하기 위한 synchronized로 시작하는 메서드들 존재

```java
Set s = Collections.synchronizedSet(new HashSet());

List list = Collections.synchronizedSet(new ArrayList());

List list = Collections.synchronizedSet(new LinkedList());

Map map = Collections.synchronizedSet(new HashMap());
```

- Map의 경우 키 값들을 Set으로 가져와 Iterator를 통해 데이터를 처리하는 경우 `ConcurrentModificationException` 발생
- 예외가 발생하는 원인 중 하나는 스레드에서 Iterator로 어떤 Map 객체의 데이터를 꺼내고 있는데, 다른 스레드에서 해당 Map을 수정하는 경우
- 이러한 문제를 해결해주는 클래스는 java.util.concurrent 패키지에서 확인