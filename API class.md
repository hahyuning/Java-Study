# java.lang 패키지
## 1. Object 클래스
#### equals(Object 0)
- 매개변수로 객체의 참조변수를 받아서 두 참조변수에 저장된 주소값이 같은지 비교한 결과를 boolean으로 반환한다.<br>
(==과 동일한 결과)
- 일반적으로 Object의 equals() 메서드는 직접 사용되지 않고 하위 클래스에서 재정의하여 논리적으로 동등 비교할 때 이용된다.<br>
(ex. String 클래스)

#### hashCode()
- 객체의 메모리 번지를 이용해서 해시코드를 만들어 리턴하기 때문에 객체마다 다른 값을 가진다.
- String 클래스는 문자열의 내용이 같으면 동일한 해시코드를 반환하도록 오버라이딩되어 있기 때문에, 문자열의 내용이 같으면 항상 동일한 해시코드값을 얻는다.
<br>

#### HashSet, HashMap, Hashtable에서 두 객체가 동등한지 비교하는 방법
<br>

![image](https://user-images.githubusercontent.com/60869749/146666316-6b7f49ce-4fa3-4529-9568-1e07be192e96.png)

- Key 클래스 
```java
public class Key {
	public int num;
	
	publc Key(int num);
		this.num = num;
	
	// num 필드값이 같으면 true 리턴
	@Oberride
	public boolean equals(Object obj) {
		if (obj instancdof Member) {
			Member member = (Member) obj;
			if (id.equals(member.id)) {
				return true;
			}
		}
		return false;
	}

}
```
 
```java
public class KeyTest {
	public static void main(String[] args) {
	
	// key 객체를 식별키로 사용해서 String 값을 저장하는 HashMap 객체 생성
	HashMap<Key, String> hashMap = new HashMap<>();
	
	// 식별키 new Key(1)로 "test" 저장
	hashMap.put(new Key(1), "test");
	
	// 식별키 new Key(1)로 "test" 읽어옴
	String value = hashMap.get(new Key(1));
	
	// 결과 null
	System.out.println(value);
	}

}
```

- 의도한 대로 "test"를 읽어오려면 hashCode() 메서드를 재정의해야 한다.
```java
public class Key {
	public int num;
	
	...
	@Override
	public int hashCode() {
		return num;
	}
}
```

-> 객체의 동등 비교를 위해서는 equals()와 hashCode() 메서드 모두 재정의해서 논리적으로 동등한 객체일 경우 동일한 해시코드가 리턴되도록 해야 한다.
<br><br>

#### toString()
- 인스턴스에 대한 정보를 문자열로 리턴한다. (기본적으로 `클래스이름@16진수해시코드`로 구성된 문자 정보를 리턴)
- String 클래스는 저장하고 있는 문자열을 리턴하고, Date 클래스는 현재 시스템의 날짜와 시간 정보를 리턴하도록 재정의되어 있다.
- `System.out.println()`은 매개값으로 객체가 주어지면 객체의 toString() 메서드를 호출해서 리턴값을 받아 출력한다.
<br>

#### clone()
- 자신을 복제하여 새로운 인스턴스를 생성한다.
- 인스턴스 변수의 값만을 복사하기 때문에 참조타입의 인스턴스 변수가 있는 클래스는 완전한 인스턴스 복제가 이루어지지 않는다.
- clone()을 사용하려면 복제할 클래스가 Cloneable 인터페이스를 구현한 뒤 clone()을 오버라이딩할 때 접근 제어자를 protectd에서 public으로 변경해야 하고, clone()을 호출하는 코드는 반드시 예외처리를 해주어야 한다.

> 공변 반환타입:오버라이딩할 때 조상 메서드의 반환타입을 자손 클래스의 타입으로 변경하는 것을 허용하는 것

> 얕은복사와 깊은복사
> - 얕은복사: 원본과 복제본이 같은 객체를 공유하므로 원본을 변경하면 복사본도 영향을 받는다.
> - 깊은복사: 원본이 참조하고 있는 객체까지 복사하는 것으로 원본과 복사본이 서로 다른 객체를 참조하기 때문에 원봉늬 변경이 복사본에 영향을 미치지 않는다. (deepCopy() 메서드)
<br>

## 2. System 클래스
- 자바 프로그램은 운영체제에서 바로 실행되는 것이 아니라 JVM 위에서 실행되기 때문에 운영체제의 모든 기능을 직접 이용하기가 어렵지만, java.lang 패키지의 System 클래스를 이용하면운영체제의 일부 기능을 이용할 수 있다.
- 프로그램 종료, 키보드로부터 입력, 모니터로 출력, 현재 시간 읽기 등
- System 클래스의 모든 필드와 메서드는 static으로 구성되어 있다.
<br>

#### exit()
- 현재 실행하고 있는 프로세스를 강제 종료시킨다.
- int 값을 매개변수로 지정하는데, 이 값을 종료 상태값이라고 한다. (일반적으로 정상 종료일 경우 0)

> System.exit(0): 정상 종료
> System.exit(-1):
<br>

#### currentTimeMillies(), nanoTime()
- 컴퓨터의 시계로부터 현재 시간을 읽어서 밀리세컨드 단위와 나노세컨드 단위의 long 값을 리턴한다.
- 주로 프로그램의 실행 소요 시간 측정에 사용되는데, 프로그램 시작 시 시각을 읽고 프로그램이 끝날 때 시간을 읽어서 프로그램 실행 소요 시간을 구한다.
<br>

## 3. Class 클래스
- 자바는 클래스와 인터페이스의 메타 데이터를 lava.lang 패키지의 Class 클래스로 관리한다.
- 메타 데이터: 클래스의 이름, 생성자 정보, 필드 정보, 메서드 정보
<br>

#### 클래스 객체
- 클래스 객체는 클래스 파일이 클래스 로더에 의해서 메모리에 올라갈 때 자동으로 생성된다.
	- 클래스 로더: 실행 시에 필요한 클래스를 동적으로 메모리에 로드하는 역할
- 클래스 파일을 읽어서 사용하기 편한 형태로 저장해 놓은 것이 클래스 객체
- Class 객체를 이용하면 클래스에 정의된 멤버나 이름이나 개수 등, 클래스에 대한 모든 정보를 얻을 수 있기 때문에 Class 객체를 통해서 객체를 생성하고 메서드를 호출하는 등 보다 동적인 코드를 작성할 수 있다.
<br>

#### 클래스 객체를 얻는 방법
- `클래스이름.class`: 객체 없이 클래스 이름만 가지고 얻는 방법
- `참조변수.getClass()`: 객체가 이미 생성되어 있을 경우에 사용하는 방법
<br>

## 4. String 클래스
- 문자열을 저장하기 위해 문자열 배열 참조변수를 인스턴스 변수로 정의해 놓은 클래스

#### 생성자
- 문자열 리터럴은 String 객체로 자동 생성되지만, 다양한 생성자를 이용해서 직접 String 객체를 생성할 수 있다.
- 파일의 내용을 읽거나, 네트워크를 통해 받은 데이터는 보통 byte[] 배열이므로 이것을 문자열로 변환해야 한다.
- 바이트 배열을 문자열로 바꾸는 방법
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

#### 자주 사용하는 메서드
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

> 참고: 한번 생성된 String 인스턴스가 갖고 있는 문자열은 읽어올 수만 있고 변경할 수 없기 때문에 +를 사용해서 문자열을 결합하면 매 연산마다 새로운 문자열을 가진 String 인스턴스가 생성되어 메모리 공간을 차지하게 되므로 가능한 결합횟수를 줄이는 것이 좋다.
```java
log
```
<br>

#### StringBuffer
- String 클래스는 인스턴스를 생성할 때 지정된 문자열을 변경할 수 없지만 StringBuffer 클래스는 변경이 가능하다.
- StringBuffer 클래스는 인스턴스를 생성할 때 적절한 길이의 char 형 배열이 생성되고, 이 배열은 문자열을 저장하고 편집하기 위한 공간(buffer)으로 사용된다.
- StringBuffer 인스턴스를 생성할 때는 크기를 지정할 수 있으며, 저장될 문자열의 길이를 고려하여 충분히 여유있는 크기로 지정하는 것이 좋다.
	- 버퍼의 크기가 작업하려는 문자열의 길이보다 작을 때 내부적으로 버퍼의 크기를 증가시킨 후 내용을 복사하는 작업 수행
- StringBuffer 클래스는 equals() 메서드를 오버라이딩 하지 않아서 ==을 사용하는 것과 같은 결과를 얻는다.
	- StringBuffer 인스턴스에 담긴 문자열을 비교하기 위해서는 StringBuffer 인스턴스에 toString()을 호출해서 String 인스턴스를 얻은 다음, 여기에 equals() 메서드를 사용해서 비교해야 한다.

#### StringBuilder
- StringBuffer는 멀티쓰레드에 thread safe 하도록 동기화되어 있어, 성능이 떨어진다.
- StringBuffer에서 쓰레드의 동기화만 뺀 것이 StringBuilder
<br>

## 5. Wrapper 클래스
- 기본형 변수를 객체로 다뤄야하는 경우 사용한다. (Boolean, Character, Byte, Short, Integer, Long, Float, Double)
- 포장하고 있는 기본 타입 값은 외부에ㅐ서 변경할 수 없으며, 만약 내부의 값을 변경하고 싶다면 새로운 래퍼 객체를 만들어야 한다.
<br>

#### 박싱과 언박싱
- 박싱: 기본 타입의 값을 래퍼 클래스 객체로 변환하는 것
- 언박싱: 래퍼 클래스의 객체를 기본 타입의 값으로 변환하는 것

> 오토 박싱과 오토 언박싱
> - 오토 박싱: 래퍼 클래스 타입에 기본값이 대입될 경우에 발생
> - 오토 언박싱: 기본 타입에 래퍼 클래스 객체가 대입되는 경우와 연산에서 발생

```java
// 오토 박싱
Integer obj = 100;

// 오토 언박싱
Integer obj = new Integer(200);
int value1 = obj;
int value2 = obj + 100;
```
<br>

#### 문자열을 기본 타입 값으로 변환
- 래퍼 클래스의 주요 용도는 기본 타입의 값을 박싱해서 래퍼 클래스의 객체로 만드는 것이지만, 문자열을 기본 타입의 값으로 변환할 때에도 많이 사용된다.
- 대부분의 래퍼 클래스에는 `parse + 기본 타입 이름`으로 되어 있는 static 메서드가 있으며, 이 메서드는 문자열을 매개변수로 받아 기본 타입 값으로 변환한다.
<br>

#### 값 비교
- ==과 !=는 내부의 값을 비교하는 것이 아니라 래퍼 클래스의 참조를 비교하기 때문에, 래퍼 클래스 객체는 내부의 값을 비교하기 위해 ==과 !=를 사용하지 않는 것이 좋다.
- 직접 내부 값을 언박싱해서 비교하거나, equals() 메서드로 내부 값을 비교하는 것이 좋다.
- 래퍼 클래스의 equals() 메서드는 내부의 값을 비교하도록 재정의 되어 있다.

```java
Integer value1 = 100;
Integer value2 = 100;

// 결과: false
System.out.pringln(value1 == value2);
```
<br>

## 5. Math 클래스
- 수학 계산에 사용할 수 있는 메서드를 제공하며, 모두 static 메서드이므로 Math 클래스로 바로 사용 가능
- abs(), ceil(), floor(), max(), min(), random(), rint(), round()
<br>

#### round()
- 항상 소수점 첫째자리에서 반올림해서 정수값을 결과로 반환한다.
- 소수점 n번째 자리에서 반올림한 결과를 얻기 위해서는 10의 n제곱으로 곱하고, round()를 사용한 결과를 다시 곱한 수로 나눠준다. (정수형 간의 연산에서는 반올림이 이루어지지 않으므로 주의)
<br>

# java.util 패키지
## 1. Objects 클래스
- Object 클래스의 보조 클래스로 모든 메서드가 static
- 객체의 비교나 널 체크에 유용
	- isNull(): 해당 객체가 널인지 확인
	- requireNonNull(): 매개변수의 유효성 검사
	- compare(): 두 비교대상이 같으면 0, 크면 양수, 작으면 음수 반환
	- deepEquals(): 객체를 재귀적으로 비교하기 때문에 다차원 배열의 비교 가능
<br>

## 2. [정규식](https://wikidocs.net/4308)
- 정규식: 텍스트 데이터 중에서 원하는 조건과 일치하는 문자열을 찾아내기 위해 사용하는 것으로, 미리 정의된 기호와 문자를 이용해서 작성한 문자열
- Patter은 정규식을 정의하는데 사용되고 Matcher는 정규식을 데이터와 비교하는 역할
- 과정
	- 정규식을 매개변수로 Pattern 클래스의 static 메서드인 compile(String regex)을 호출하여 Pattern 인스턴스를 얻는다.
	- 정규식으로 비교할 대상을 매개변수로 Pattern 클래스의 matcher(CharSequence input)을 호출해서  Matcher 인스턴스를 얻는다.
	- Matcher 인스턴스에 matches()를 호출해서 정규식에 부합하는지 확인한다.
<br>

## 3. Date 클래스
- 날짜와 시간 정보를 저장하는 클래스 (특정 시점의 연도, 월, 일, 시간 정보 저장)
- 객체 간에 날짜 정보를 주고받을 때 매개변수나 리턴 타입으로 주로 사용된다.
- Date 객체이 toString() 메서드는 영문으로 된 날짜를 리턴하기 때문에 원하는 날짜 형식의 문자열을 얻고 싶으면 java.text 패키지의 SimpleDateFormat 클래스와 함께 사용하는 것이 좋다.
```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초")

String strNow = sdf.format(new Date());

// 결과 -> xxxx년 xx월 xx일 xx시 xx분 xx초
System.out.println(strNow);
```
<br>

## 4. Calendar 클래스
- 운영체제의 날짜와 시간을 얻을 때 사용
- 추상 클래스이므로 new 연산자를 사용해서 인스턴스를 생성할 수 없고, 정적 메소드인 getInstance*( 메서드를 이용하면 현재 운영체제에 설정되어 있는 시간대를 기준으로 한 Calendar 하위 객체를 얻을 수 있다.
```java
Calendar now = Calendar.getInstance();

int year = now.get(Calendar.YEAR);
int month = now.get(Calendar.MONTH) + 1
int day = now.get(Calendar.DAY_OF_MONTH)
int week = now.get(Calendar.DAY_OF_WEEK);
int amPm = now.get(Calendar.AM_PM;
int hour = now.get(Calendar.HOUR);
int min = now.get(Calendar.MINUTE);
int sec = now.get(Calendar.SECOND);
```
