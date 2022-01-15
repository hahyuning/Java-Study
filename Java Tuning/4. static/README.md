# static
- 다른 JVM에서는 static이라고 선언해도 다른 주소나 다른 값을 참조하지만, 하나의 JVM이나 WAS 인스턴스에서는 같은 주소에 존재하는 값을 참조
- **GC의 대상이 되지 않는다.**
- static을 잘 사용하면 성능을 향상시킬 수 있지만, 잘못 사용하면 예기치 못한 결과를 초래할 수도 있다.
  - 웹 환경에서 static을 잘못 사용하면 여러 쓰레드에서 하나의 변수에 접근할 수도 있기 때문에 데이터가 꼬이는 일 발생

<br>

## static 활용하기
#### 1. 자주 사용하고 절대 변하지 않는 변수는 final static으로 선언한다.
- 만약 자주 변경되지 않고, 경우의 수가 단순한 쿼리 문장이 있다면 final static이나 static으로 선언하여 사용
  - 자주 사용되는 로그인 관련 쿼리들이나 간단한 목록 조회 쿼리를 final static으로 선언하면 적어도 1바이트 이상의 객체가 GC 대상에 포함되지 않는다.
- JNDI 이름이나 간단한 코드성 데이터들은 static으로 선언해 놓으면 편리하다.
- 템플릿 성격의 객체를 static으로 선언하는 것도 성능 향상에 도움 (Velocity)

> #### Velocity
> 자바 기반의 프로젝트를 수행할 때, UI가 될 수 있는 HTML 뿐만 아니라 XML, 텍스트 등의 템플릿을 정해놓고, 
> 실행 시 매개변수 값을 던져서 원하는 형식의 화면을 동적으로 구성할 수 있도록 도와주는 컴포넌트

<br>

#### 2. 설정 파일 정보도 static으로 관리한다.
- 클래스의 객체를 생성할 때마다 설정 파일을 로딩하면 성능 저하 발생
- 이럴 때는 반드시 static으로 데이터를 읽어서 관리해야 한다.

<br>

#### 3. 코드성 데이터는 DB에서 한 번만 읽는다.
- 부서가 적은 회사의 코드나, 건수가 그리 많지 않되 조회 빈도가 높은 코드성 데이터는 DB에서 한 번만 읽어서 관리하는 것이 성능 측면에서 좋다.

```java
import java.util.HashMap;

// 코드 정보를 미리 담아 놓는 클래스
public class CodeManager {
    
    private HashMap<String, String> codeMap; // 코드 정보
    private static CodeDao codeDao; // DB에서 코드 정보를 가지고 오는 DAO
    
    // 생성자가 아닌 getInstance() 메서드를 통해서 접근하도록 설정
    private static CodeManager cm;
    
    // 초기화
    static {
        codeDao = new CodeDao;
        cm = new CodeManager();
        
        if (!cm.getCodes()) {
            // 에러처리
        }
    }
    
    private CodeManager();
    
    public static CodeManagergetInstance() {
        return cm;
    }
    
    private boolean getCodes() {
        try {
            codeMap = codeDao.getCodes();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // 코드가 수정되었을 경우 코드 정보를 다시 읽어 오는 메서드
    public boolean updateCodes() {
        return cm.getCodes();
    }
    
    // 코드 정보를 가져오는 메서드
    public String getCodeValue(String code) {
        return codeMap.get(code);
    }
}
```

- 서로 다른 JVM에 올라가 있는 코드 정보는 수정된 코드와 상이하므로 그 부분에 대한 대책 필요
- JVM 간에 상이한 결과가 나오는 것을 방지하기 위해 memcached, EhCache 등의 캐시를 사용한다.

<br>

## static 잘못 사용 예

```java
import java.io.FileReader;
import java.util.HashMap;

public class BadQueryManager {

    private static String queryUrl = null; // 쿼리가 포함된 파일의 이름과 위치

    public BadQueryManager(String badUrl) {
        queryUrl = badUrl;
    }

    // DAO에서 쿼리를 요청하면, 해당 쿼리 파일을 읽어서 리턴
    public static String getSql(String idSql) {
        try {
            FileReader reader = new FileReader();
            HashMap<String, String> document = reader.read(queryUrl);
            
            return document.get(idSql);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
}
```

- 쿼리가 같은 파일에 있는 화면은 수행이 되고, 만약 어떤 화면의 수행 결과가 다른 파일의 쿼리인 경우에도 처음 그 화면이 호출되었다면, 정상적으로 수행
- 만약 어떤 화면에서 생성자를 통해서 queryUrl을 설정하고 getSql() 메서드를 호출하기 전에, 다른 queryUrl을 사용하는 화면의 스레드에서 생성자를 호출하는 경우, 시스템 오류 발생
- getSql() 메서드와 queryUrl을 static으로 선언해서 모든 스레드에서 동일한 주소를 가리키게 되어 문제 발생!

<br>

## 메모리 릭
- 만약 어떤 클래스에 데이터를 ArrayList에 담을 때 해당 Collection 객체를 static으로 선언하는 경우, 지속적으로 해당 객체에 데이터가 쌓인다면 GC가 되지 않아 시스템은 OutOfMemoryError 발생
  - 시스템을 재시작해야 하며, 해당 인스턴스는 더 이상 서비스할 수 없게 된다.
- 더 이상 사용 가능한 메모리가 없어지는 현상을 **메모리 릭**이라고 하는데, static과 Collection 객체를 잘못 사용하는 경우 발생한다.
- 메모리 릭이 발생하면 사용 가능한 메모리가 적어진다.

#### 메모리 릭 원인 분석
- 메모리 릭의 원인은 메모리의 현재 상태를 파일로 남기는 HeapDump라는 파일을 통해서 확인 가능
- JDK/bin 디렉터리에 있는 jmap 파일을 사용하여 덤프를 남길 수 있으며, 남긴 덤프는 eclipse에서 제공하는 MAT 툴을 통해서 분석할 수 있다.