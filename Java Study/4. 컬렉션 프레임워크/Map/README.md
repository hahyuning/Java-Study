## 1. HashMap
- 키와 값을 묶어서 하나의 데이터로 저장
- 해싱을 사용하기 때문에 많은 양의 데이터를 검색하는데 있어 뛰어난 성능을 보인다.
- Entry라는 내부 클래스를 정의하고, 다시 Entry 타입의 배열을 선언해서 구현
- 키와 값을 각각 Object 타입으로 저장하기 때문에 어떠한 객체도 저장할 수 있지만, 키는 주로 String을 대문자 또는 소문자로 통일해서 사용
    - 키와 값으로 null을 허용한다.
- 카는 저장된 값을 찾는데 사용되기 때문에 컬렉션 내에서 유일해야 한다.
- 해싱을 구현한 컬렉션 클래스들은 저장순서를 유지하지 않는다.

<br>

#### 해싱과 해시함수
- 해싱: 해시함수를 이용해서 데이터를 해시테이블에 저장하고 검색하는 기법
- 해싱을 구현한 클래스로는 HashSet, HashMap, Hashtable 등이 있다.
- 해싱에서 사용하는 자료구조는 배열과 링크드 리스트의 조합으로 되어 있다.


- 저장할 데이터의 키를 해시함수에 넣으면 배열의 한 요소를 얻어오게 되고, 다시 그곳에 연결된 링크드 리스트에 저장한다.
- 데이터를 검색할 때는 검색하고자 하는 값의 키로 해시함수를 호출하고, 해시함수의 계산결과로 해당 값이 저장되어 있는 링크드 리스트를 찾아서
  링크드 리스트에서 검색한 키와 일치하는 데이터를 찾는다.


- 링크드 리스트는 검색에 불리한 자료구조이기 때문에 링크드 리스트의 크기가 커질수록 검색속도가 떨어지게 되므로, 충돌을 최소화하는 해시함수의 알고리즘이 중요
- 실제로 해싱을 구현한 컬렉션 클래스에서는 Object 클래스에 정의된 haseCode()를 해시함수로 사용한다.
    - 객체의 주소를 이용하여 해시코드를 만들기 때문에 모든 객체에 대한 hashCode()를 호출한 결과가 서로 유일
- String 클래스의 경우 문자열의 내용으로 해시코드를 만들기 때문에, 서로 다른 String 인스턴스라도 같은 내용이면 같은 해시코드를 얻는다.


- 새로운 클래스를 정의할 때, equals()를 재정의 오버라이딩해야 한다면, haseCode()도 같이 재정의해서 equals()의 결과가 true인 두 객체의 해시코드의
  결과값이 항상 같도록 해주어야 한다.
- 그렇지 않으면 해싱을 구현한 컬렉션 클래스에서는 equals()의 호출결과가 true지만, 해시코드가 다른 두 객체를 서로 다른 것으로 인식하고 따로 저장
- equals()로 비교한 결과가 false이고 해시코드가 같은 경우는 같은 링크드 리스트에 저장된 서로 다른 두 데이터가 된다.

<br>

## 2. TreeMap
- 이진검색트리의 형태로 키와 값의 쌍으로 이루어진 데이터를 저장하므로, 검색과 정렬에 적합한 컬렉션 클래스
- 검색에 관한한 대부분의 경우에서 HashMap이 TreeMap보다 더 뛰어나므로 HashMap을 사용하는 것이 좋으며, 범위검색이나 정렬이 필요한 경우에는 TreeMap을 사용한다.
- HashMap과는 달리 키가 오름차순으로 정렬된다.

<br>

## 3. Properties
- HashMap의 구버전인 Hashtable을 상속받아 구현한 것으로, 키와 값을 (String, String) 형태로 저장하는 보다 단순화된 컬렉션 클래스
- 주로 애플리케이션의 환경설정과 관련된 속성을 저장하는데 사용되며, 데이터를 파일로부터 읽고 쓰는 편리한 기능 제공
- Map의 특성상 저장순서를 유지하지 않고, Iterator 대신 Enumeration을 사용한다.
- 데이터를 저장하는데 사용되는 setProperty()는 단순히 Hashtable의 put 메서드를 호출한다.
    - 기존에 같은 키로 저장된 값이 있는 경우 그 값을 Object 타입으로 반환하고, 없는 경우 null을 반환한다.
- getProperty()는 저장된 값을 읽어오는 메서드로, 만일 읽어오려는 키가 존재하지 않으면 저장된 기본값을 반환한다.