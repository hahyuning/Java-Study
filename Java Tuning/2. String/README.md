# String

## String
- **불변 객체**이기 때문에 변경하면 새로운 String 클래스의 객체가 만들어지고, 이전에 있던 객체는 필요없는 쓰레기 값이 되어 GC 대상이 된다.
- 이런 작업이 반복 수행되면 메모리를 많이 사용하게 되고, 응답 속도에도 많은 영향을 미친다. 
  - GC를 하면 할수록 시스템의 CPU를 사용하게 되고 시간도 많이 소요되기 때문
- 짧은 문자열을 더할 경우 사용한다.

## StringBuffer
- String과는 다르게 새로운 객체를 생성하지 않고, **기존에 있는 객체의 크기를 증가시키면서 값을 더한다.**
- **스레드에 안전**하게 설계되어 있으므로, 여러 개의 스레드에서 하나의 StringBuffer 객체를 처리해도 전혀 문제가 되지 않는다.
- 스레드에 안전한 프로그램이 필요할 때나, 개발 중인 시스템의 부분이 스레드에 안전한지 모를 경우 사용
- 만약 클래스에 static으로 선언한 문자열을 변경하거나, 싱글톤으로 선언된 클래스에 선언된 문자열의 경우 StringBuffer를 사용해야 한다.

## StringBuilder
- 제공하는 메서드가 StringBuffer와 동일하지만, StringBuilder는 **단일 스레드에서의 안정성만을 보장**한다.
- 여러 개의 스레드에서 하나의 StringBuilder 객체를 처리하면 문제가 발생한다.
- 스레드에 안전한지의 여부와 전혀 관계 없는 프로그램을 개발할 때 사용
- 만약 메서드 내에 변수를 선언했다면, 해당 변수는 그 메서드 내에서만 살아있으므로 StringBuilder를 사용하면 된다.

<br>

> JDK 5.0 이상에서, String을 더하면 컴파일러에서 자동으로 StringBuilder로 변환해지만 반복 루프를 사용해서 문자열을 더할 때는 객체를 계속 추가한다.<br>
> 따라서, String 클래스를 쓰는 대신, 스레드와 관련이 있으면 StringBuffer를, 스레드 안전 여부와 상관 없으면 StringBuilder를 사용하는 것이 좋다.

<br>

#### 생성자
| 종류 | 설명 |
|---|---|
| StringBuffer() | 아무 값도 없는 StringBuffer 객체를 생성한다. (기본 용량은 16개의 char) |
| StringBuffer(CharSequence seq) | CharSequence를 매개변수로 받아 그 seq 값을 갖는 StringBuffer를 생성한다. |
| StringBuffer(int capacity) | capacity에 지정한 만큼의 용량을 갖는 StringBuffer를 생성한다. |
| StringBuffer(String str) | str의 값을 갖는 StringBuffer를 생성한다. |

<br>

#### CharSequence 인터페이스
StringBuffer나 StringBuilder로 생성한 객체를 전달할 때 사용한다.
```java
public class StringBufferTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("ABCED");
        
        StringBufferTest sbt = new StringBufferTest();
        sbt.check(sb);
    }
    
    public void check(CharSequence cs) {
        StringBuffer sb = new StringBuffer(cs);
        System.out.println("sb.length = " + sb.length());
    }
}
```
- StringBuilder를 CharSequence로 받고, StringBuffer로 처리해도 정상적으로 작동한다.
- StringBuffer나 StringBuilder로 값을 만든 후 굳이 toString을 수행하여 필요없는 객체를 만들어서 넘겨주기보다는, 
CharSequence로 받아서 처리하는 것이 메모리 효율에 더 좋다.
