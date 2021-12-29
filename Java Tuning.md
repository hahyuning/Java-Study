#### 목차
- [디자인 패턴](#디자인-패턴)
- [프로그램 속도측정](#프로그램-속도측정)
- [String vs StringBuffer vs StringBuilder](#string-vs-stringbuffer-vs-stringbuilder)
- Collection
- [static](#static)
- 클래스 정보
- synchronized
- IO
- 로그
- JSP와 서블릿
- DB
- XML과 JSON
- 서버 세팅
- 안드로이드 개발
- JVM
- GC
- JMX
<br><br>

## 디자인 패턴
- 시스템을 만들기 위해서 전체 중 일부 의미있는 클래스들을 묶은 각각의 집합
- 반복되는 의미있는 집합을 정의하고 이름을 붙여서, 누가 이야기하더라도 동일한 의미의 패턴이 되도록 만들어 놓은 것
- 참고: GoF 패턴

#### MVC 모델
- 하나의 JSP나 스윙처럼 화면에 모든 처리로직을 모아두는 것이 아니라, 모델 역할, 뷰 역할, 컨트롤러 역할을 하는 클래스를 각각 만들어서 개발하는 모델

| 이름 | 역할 |
|--|--|
| 뷰 | - 사용자가 결과를 보거나 입력할 수 있는 화면<br> - 이벤트를 발생시키고, 이벤트의 결과를 보여주는 역할 |
| 컨트롤러 | - 뷰와 모델의 연결자<br> - 뷰에서 받은 이벤트를 모델로 연결하는 역할 |
| 모델 | - 뷰에서 입력된 내용을 저장, 관리, 수정하는 역할<br> - 이벤트에 대한 실질적인 처리를 하는 부분 |

> JSP 모델1
> - JSP에서 자바빈을 호출하고 데이터베이스에서 정보를 조회, 등록, 수정, 삭제 업무를 한 후 결과를 브라우저로 보내주는 방식
> - 간단하게 개발할 수 있다는 장점이 있지만, 개발 후 프로세스 변경이 생길 경우에 수정이 어렵다는 단점
> - 화면과 비즈니스 모델의 분업화가 어려워 개발자의 역량에 따라서 코드가 많이 달라질 수 있다.
> - 컨트롤러가 없다.

> JSP 모델2
> - MVC 모델을 정확하게 따른다.
> - 서블릿을 통해 요청을 하며, 서블릿이 컨트롤러의 역할을 수행한다.
<br>

#### J2EE 패턴
![image](https://user-images.githubusercontent.com/60869749/147656247-d09a8bd9-ba60-4068-8a1c-f59424da7829.png)

| 패턴 이름 | 설명 |
|--|--|
| Intercepting Filter | 요청 타입에 따라 다른 처리를 하기 위한 패턴 |
| Front Controller | 요청 전후에 처리하기 위한 컨트롤러를 지정하는 패턴 |
| View Helper | 프레젠테이션 로직과 상관 없는 비즈니스 로직을 헬퍼로 지정하는 패턴 |
| Composite View | 최소 단위의 하위 컴포넌트를 분리하여 화면을 구성하는 패턴 |
| Serivce to Worker | Front Controller와 View Helper 사이에 디스패처를 두어 조합하는 패턴 |
| Dispatcher View | Front Controller와 View Helper로 디스패처 컴포넌트를 형성하는 패턴 |
| **Business Delegate** | 비즈니스 서비스 접근을 캡슐화하는 패턴 |
| **Service Locator** | 서비스와 컴포넌트 검색을 쉽게 하는 패턴 |
| **Session Facade** | 비즈니스 티어 컴포넌트를 캡슐화하고, 원격 클라이언트에서 접근할 수 있는 서비스를 제공하는 패턴 |
| Composite Entity | 로컬 엔티티 빈과 POJO를 이용하여 큰 단위의 엔티티 객체를 구현 |
| **Transfer Object** | Value Object 패턴이라고도 부르며, 데이터를 전송하기 위한 객체에 대한 패턴 |
| Transfer Object Assembler | 하나의 VO로 모든 타입 데이터를 처리할 수 없으므로, 여러 VO를 조합하거나 변형한 객체를 생성하여 사용하는 패턴 |
| Value List Handler | 데이터 조회를 처리하고, 결과를 임시 저장하며, 결과 집합을 검색하여 필요한 항목을 선택하는 역할을 수행 |
| **Data Access Object** | DAO 라고도 부르며, DB에 접근을 전담하는 클래스를 추상화하고 캡슐화 |
| Service Activator | 비동기적 호출을 처리하기 위한 패턴 |

- 성능과 가장 밀접한 패턴은 Service Locator 패턴
- 성능에 직접적으로 많은 영향을 미치지는 않지만, Transfer Object는 애플리케이션 개발 시 반드시 사용해야 한다.

> 참고:

<br><br>

## 프로그램 속도측정
- 애플리케이션의 속도에 문제가 있을 때 각종 분석하기 위한 툴로는 프로파일링 툴이나 APM 툴 등이 있다.
- 시스템의 성능이 느릴 때는 병목지점을 파악해야 하는데, 이 툴을 사용하면 병목지점을 쉽게 파악할 수 있다.

| 구분 | 특징 |
|--|--|
| 프로파일링 툴 | - 소스 레벨의 분석을 위한 툴<br> - 애플리케이션의 세부 응답 시간까지 분석할 수 있다.<br> - 메모리 사용량을 객체나 클래스, 소스의 라인 단위까지 분석할 수 있다.<br> - 가격이 APM 툴에 비해서 저렴하다.<br> - 보통 사용자수 기반으로 가격이 정해진다.<br> - 자바 기반의 클라이언트 프로그램을 분석할 수 있다. |
| APM 툴 | - 애플리케이션의 장애 상황에 대한 모니터링 및 문제점 진단이 <br> - 서버의 사용자 수나 리소스에 대한 모니터링을 할 수 있다.<br> - 실시간 모니터링을 위한 툴로 가격이 프로파일링 툴에 비해 비싸다.<br> - 보통 CPU 수를 기반으로 가격이 정해진다.<br> - 자바 기바늬 클라이언트 프로그램 분석이 불가능하다. |
<br>

#### System 클래스의 currentTimeMillis(), nanoTime()
- currentTimeMillis(): 현재의 시간을 ms로 리턴한다. (long 타입)
- nanoTime(): 현재의 시간을 ns로 리턴한다. (1/1000000000 초)
<br>

- nanoTime() 메서드의 목적은 수행된 시간 측정이기 때문에 오늘날의 날짜를 알아내는 부분에는 사용하면 안 된다.
- nanoTime() 메서드는 나노 단위의 시간을 리턴해 주기 때문에 되도록이면 nanoTime()의 결과로 판단하도록 한다.
<br>

#### 전문 측정 라이브러리
<br>

## String vs StringBuffer vs StringBuilder 
#### String
- 불변 객체이기 때문에 변경하면 새로운 String 클래스의 객체가 만들어지고, 이전에 있던 객체는 필요없는 쓰레기 값이 되어 GC 대상이 된다.
- 이런 작업이 반복 수행되면 메모리를 많이 사용하게 되고, 응답 속도에도 많은 영향을 미친다. (GC를 하면 할수록 시스템의 CPU를 사용하게 되고 시간도 많이 소요)
- 짧은 문자열을 더할 경우 사용한다.

#### StringBuffer
- String과는 다르게 새로운 객체를 생성하지 않고, 기존에 있는 객체의 크기를 증가시키면서 값을 더한다.
- 스레드에 안전하게 설계되어 있으므로, 여러 개의 스레드에서 하나의 StringBuffer 객체를 처리해도 전혀 문제가 되지 않는다.
- 스레드에 안전한 프로그램이 필요할 때나, 개발 중인 시스템의 부분이 스레드에 안전한지 모를 경우 사용
- 만약 클래스에 static으로 선언한 문자열을 변경하거나, 싱글톤으로 선언된 클래스에 선언된 문자열의 경우 StringBuffer를 사용해야 한다.

#### StringBuilder
- 제공하는 메서드가 StringBuffer와 동일하지만, StringBuilder는 단일 스레드에서의 안정성만을 보장한다.
- 여러 개의 스레드에서 하나의 StringBuilder 객체를 처리하면 문제가 발생한다.
- 스레드에 안전한지의 여부와 전혀 관계 없는 프로그램을 개발할 때 사용
- 만약 메서드 내에 변수를 선언했다면, 해당 변수는 그 메서드 내에서만 살아있으므로 StringBuilder를 사용하면 된다.
<br>

#### 생성자
| 종류 | 설명 |
|--|--|
| StringBuffer() | 아무 값도 없는 StringBuffer 객체를 생성한다. (기본 용량은 16개의 char) |
| StringBuffer(CharSequence seq) | CharSequence를 매개변수로 받아 그 seq 값을 갖는 StringBuffer를 생성한다. |
| StringBuffer(int capacity) | capacity에 지정한 만큼의 용량을 갖는 StringBuffer를 생성한다. |
| StringBuffer(String str) | str의 값을 갖는 StringBuffer를 생성한다. |

> CharSequence 인터페이스
> - StringBuffer, StringBuilder는 CharSequence 인터페이스를 구현한 클래스로, StringBuffer나 StringBuilder로 생성한 객체를 전달할 때 사용한다.
> - StringBuffer나 StringBuilder로 값을 만든 후 굳이 toString을 수행하여 필요없는 객체를 만들어서 넘겨주기보다는 CharSequence로 받아서 처리하는 것이 메모리 효율에 더 좋다.
<br><br>

## static
