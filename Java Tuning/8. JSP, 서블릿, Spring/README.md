## JSP
- JSP는 가장 처음에 호출되는 경우에만 시간이 소요되고, 그 이후의 시간에는 컴파일 된 서블릿 클래스가 수행된다.
- 따라서, 일반적으로 JSP와 같은 웹 화면단을 처리하는 부분에서 소요되는 시간은 많지 않다.

#### JSP의 라이프 사이클
1. JSP URL 호출
2. 페이지 번역
3. JSP 페이지 컴파일
4. 클래스 로드
5. 인스턴스 생성
6. jspInit 메서드 호출
7. _jspService 메서드 호출
8. jspDestroy 메서드 호출

- 해당 JSP 페이지가 이미 컴파일 되어 있고, 클래스가 로드되어 있고, JSP 파일이 변경되지 않았다면, 가장 많은 시간이 소요되는 2~4 프로세스는 생략된다.
- 서버가 기동될 때 컴파일을 미리 수행하는 Precompile 옵션을 선택하면 서버에 최신 버전을 반영한 이후에 처음 호출되었을 때 응답 시간이 느린 현상을 방지할 수 있다.

#### include
- include 기능을 사용하면 하나의 JSP에서 다른 JSP를 호출하여 여러 JSP 파일을 혼합해서 하나의 JSP로 만들 수 있다.
- JSP에서 사용할 수 있는 include 방식은 정적인 방식과 동적인 방식이 있다.
  - 정적인 방식은 JSP의 라이프 사이클 중 JSP 페이지 번역 및 컴파일 단계에서 필요한 JSP를 읽어서 메인 JSP의 자바 소스 및 클래스에 포함 시키는 방식
  - 동적인 방식은 페이지가 호출될 때마다 지정된 페이지를 불러들여 수행하도록 되어 있다.
- 동적인 방식이 정적인 방식보다 약 30배 정도 느리지만, 추가된 JSP와 메인 JSP에 동일한 이름의 변수가 있으면 오류가 발생할 수 있다.
- 따라서, 상황에 맞게 알맞은 include 방식을 선택하여 사용해야 한다.

#### 자바 빈즈
- UI에서 서버 측 데이터를 담아서 처리하기 위한 컴포넌트로, 너무 많이 사용하면 JSP에서 소요되는 시간이 증가할 수 있다.
- 이 시간을 줄이기 위해서는 TO 패턴을 사용해서, 하나의 TO 클래스를 만들고 데이터를 그 클래스의 변수로 지정하여 사용해야 한다.

#### 태그 라이브러리
- JSP에서 공통적으로 반복되는 코드를 클래스로 만들고, 그 클래스를 HTML 태그와 같이 정의된 태그로 사용할 수 있도록 하는 라이브러리
- XML 기반의 tld 파일과 태그 클래스로 구성되어 있다.
- 태그 라이브러리 클래스를 잘못 작성하거나 태그 라이브러리 클래스로 전송되는 데이터가 많으면 성능에 문제가 된다.
  - 태그 라이브러리는 태그 사이에 있는 데이터를 넘겨주어야 하는데, 이때 넘겨주는 데이터 형태는 대부분 문자열 타입
  - 데이터가 많으면 많을수록 처리해야 하는 내용이 많아지고, 처리되는 시간이 증가한다.
  - 목록을 처리하면서 대용량의 데이터를 처리할 경우에는 태그 라이브러리의 사용을 자제해야 한다.
  
<br>

## 서블릿
- WAS가 시작하고 사용 가능 상태가 되면 서블릿은 JVM에 살아 있고, 여러 스레드에서 해당 서블릿의 service() 메서드를 호출하여 공유한다.
- 따라서, service() 메서드를 구현할 때는 멤버 변수나 static한 클래스 변수를 선언하여 지속적으로 변경하는 작업은 피한다.

<br>

## Spring 
- 스프링 프레임워크를 사용하면 복잡한 애플리케이션도 POJO로 개발할 수 있다.
- Servlet을 개발하려면 반드시 HttpServlet 클래스를 상속해야 하지만, 스프링을 사용하면 HttpServlet을 확장하지 않아도 웹 요청을 처리할 수 있는 클래스를 만들 수 있다.
- 스프링 프레임워크를 사용할 때 성능 문제가 가장 많이 발생하는 부분은 프록시와 관련
- 스프링 프록시는 기본적으로 실행 시에 생성되며, 요청량이 많은 운영 상황에서 문제가 나타날 수 있다.

#### 프록시를 사용하는 경우
- @Transactional 어노테이션을 사용하면 해당 어노테이션을 사용한 클래스의 인스턴스를 처음 만들 때 프록시 객체를 만든다.
  - 스프링이 자체적으로 제공하는 기능으로, 오랜 시간 테스트를 거치고 검증받은 기능
- 개발자가 직접 스프링 AOP를 사용해서 별도의 기능을 추가하는 경우에 프록시를 사용한다.
  - 개발자가 직접 작성한 AOP 코드는 예상치 못한 성능 문제를 보일 가능성이 높다.

#### 캐시 관련 문제
- 스프링 MVC에서 작성하는 메서드의 리턴 타입으로 문자열을 사용하는 경우, 스프링은 해당 문자열에 해당하는 실제 뷰 객체를 찾는 매커니즘 사용
- 매번 동일한 문자열에 대한 뷰 객체를 새로 찾기 보다는 이미 찾아온 뷰를 캐싱해두면 동일한 문자열이 반환됐을 때 빠르게 뷰 객체를 찾을 수 있다.
- 스프링에서 제공하는 ViewResolver 중 InternalResourceViewResolver에는 이러한 캐싱 기능이 내장되어 있다.


- 만약 매번 다른 문자열이 생성될 가능성이 높고, 상당히 많은 수의 키 값으로 캐시 값이 생성될 여지가 있는 상황에서는 문자열을 반환하면 메모리에 치명적
- 이런 상황에서는 뷰 이름을 문자열로 반환하기보다는 뷰 객체 자체를 반환하는 방법이 메모리 릭을 방지하는데 도움이 된다.