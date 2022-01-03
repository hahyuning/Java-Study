# String 클래스
- 문자열을 저장하기 위해 문자열 배열 참조변수를 인스턴스 변수로 정의해 놓은 클래스

## 생성자
- 문자열 리터럴은 String 객체로 자동 생성되지만, 다양한 생성자를 이용해서 직접 String 객체를 생성할 수 있다.
- 파일의 내용을 읽거나, 네트워크를 통해 받은 데이터는 보통 byte[] 배열이므로 이것을 문자열로 변환해야 한다.
- 바이트 배열을 문자열로 바꾸는 방법
  <br>

```java
// 배열 전체를 String 객체로 생성
String str = new String(byte[] bytes);

// 지정한 문자셋으로 디코딩
String str = new String(byte[] bytes, String charsetName);

// 배열의 offset 인덱스 위치부터 lenfth만큼 String 객체로 생성
String str = new String(byte[] bytes, int offset, int length);

// 지정한 문자셋으로 디코딩
String str = new String(byte[] bytes, int offset, int length, String charsetName);
```

<br>

## 자주 사용하는 메서드
- charAt(int index): 특정 위치의 문자를 리턴
- equals(Object obj): 두 문자열의 내용을 비교 (==을 사용하면 String 인스턴스의 주소를 비교)
- getBytes(): byte[]로 리턴
- getBytes(Charset charset): 주어진 문자셋으로 인코딩한 byte[]로 리턴
- indexOf(String str): 문자열 내에서 주어진 문자열의 위치 리턴
- replace(target, replacement): target 부분을 replacement로 대치한 새로운 문자열을 리턴
- substring(begin, end): begin 위치에서 end 전까지 잘라낸 새로운 문자열을 리턴
- toLowerCase(), toUpperCase(): 알파벳 소문자, 대문자로 변환한 새로운 문자열 리턴
- trim(): 앞뒤 공백을 제거한 새로운 문자열 리턴
- valueOf(int i): 기본 타입 값을 문자열로 리턴

<br>

> 한번 생성된 String 인스턴스가 갖고 있는 문자열은 읽어올 수만 있고 변경할 수 없기 때문에 +를 사용해서 문자열을 결합하면 매 연산마다 새로운 문자열을 가진 String 인스턴스가 생성되어 메모리 공간을 차지하게 되므로 가능한 결합횟수를 줄이는 것이 좋다.

<br>

## StringBuffer
- String 클래스는 인스턴스를 생성할 때 지정된 문자열을 변경할 수 없지만 StringBuffer 클래스는 변경이 가능하다.
- StringBuffer 클래스는 인스턴스를 생성할 때 적절한 길이의 char 형 배열이 생성되고, 이 배열은 문자열을 저장하고 편집하기 위한 공간(buffer)으로 사용된다.
- StringBuffer 인스턴스를 생성할 때는 크기를 지정할 수 있으며, 저장될 문자열의 길이를 고려하여 충분히 여유있는 크기로 지정하는 것이 좋다.
    - 버퍼의 크기가 작업하려는 문자열의 길이보다 작을 때 내부적으로 버퍼의 크기를 증가시킨 후 내용을 복사하는 작업 수행
- StringBuffer 클래스는 equals() 메서드를 오버라이딩 하지 않아서 ==을 사용하는 것과 같은 결과를 얻는다.
    - StringBuffer 인스턴스에 담긴 문자열을 비교하기 위해서는 StringBuffer 인스턴스에 toString()을 호출해서 String 인스턴스를 얻은 다음, 여기에 equals() 메서드를 사용해서 비교해야 한다.

## StringBuilder
- StringBuffer는 멀티쓰레드에 thread safe 하도록 동기화되어 있어, 성능이 떨어진다.
- StringBuffer에서 쓰레드의 동기화만 뺀 것이 StringBuilder

> 참고: [자바 성능 튜닝 - String vs StringBuffer vs StringBuilder](https://github.com/hahyuning/Java-Study/blob/main/Java%20Tuning.md#string-vs-stringbuffer-vs-stringbuilder)

<br>

### 참고: 문자열의 리스트가 주어졌을 때 이 문자열들을 하나로 이어붙이는 경우 수행시간 구하기 (문자열의 길이 x, 문자열의 개수 n)

#### String 사용
```java
String joinWordsString[] words) {
    String sentence = "";
    for (String w : words) {
        sentence = sentence + w;
	}
    
    return sentence;
}
```

- 문자열을 이어붙일 때마다 두 개의 문자열을 읽어 들인 뒤 문자를 하나하나 새로운 문자열에 복사해야 한다.
- 처음에는 x개, 두 번째는 2x개, n 번재는 nx개의 문자열을 복사해야 하므로, 총 수행 시간은 O(nx^2)

<br>

#### StringBuilder 사용
```java
String joinWords(String[] words) {
    String sentence = new StringBuilder();
    for (String w : words) {
        sentence.append(w);
	}
    
    return sentence.toStirng();
}
```

- 단순하게 가변 크기 배열을 이용해서 필요한 경우에만 문자열을 복사하게끔 해주기 때문에, 총 수행 시간은 O(n)