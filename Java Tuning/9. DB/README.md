# DB
- 자바 기반 애플리케이션의 성능을 진단해 보면, 애플리케이션의 응답 속도를 지연시키는 대부분의 요인은 DB 쿼리 수행 시간과 결과를 처리하는 시간
- JDBC 관련 API는 인터페이스로, 각 DB 벤더에 따라서 처리되는 속도나 내부 처리 방식이 다르다.

<BR>

#### 일반적인 DB 연결 과정
1. 드라이버를 로드한다.
2. DB 서버의 IP와 ID, PW 등을 DriverManager 클래스의 getConnection 메서드를 사용하여 Connection 객체로 만든다.
3. Connection으로부터 PreparedStatement 객체를 받는다.
4. executeQuery를 수행하여 그 결과로 ResultSet 객체를 받아서 데이터를 처리한다.
5. finally 구문을 사용하여 ResultSet, PreparedStatement, Connection 객체들을 닫는다. (객체를 닫는 순서는 각 객체를 얻는 순서의 역순)

## 1. DB Connection과 커넥션 풀, 데이터 소스
- DB와 WAS 사이에 통신이 발생하므로 Connection 객체를 얻는 과정이 가장 느리다.
- Connection 객체를 생성하는 부분에서 발생하는 대기 시간을 줄이고, 네트워크 부담을 줄이기 위해 사용하는 것이 DB 커넥션 풀
- DB 커넥션 풀은 자바 표준으로 지정되어 있는 것이 없어 WAS 벤더에 따라 사용법이 상이할 수 있지만, DataSource는 자바 표준이므로 WAS에 상관없이 사용법이 동일하다.

<br>

#### Connection 객체의 자원 반환 방법
1. close() 메서드를 호출하는 경우
2. GC의 대상이 되어 GC 되는 경우
3. 치명적인 에러가 발생하는 경우


- Connection은 대부분 커넥션 풀을 사용하여 관리되는데, 시스템이 기동되면 지정된 개수만큼 연결하고, 필요할 때 증가시킨다.
- 사용자가 증가해 사용할 수 있는 연결이 없으면 대기하고, 어느 정도 시간이 지나면 오류가 발생한다.
- GC 될때까지 기다리면 커넥션 풀이 부족해지기 때문에 close() 메서드를 호출해서 연결을 닫아줘야 한다.


## 2. Statement와 PreparedStatement
- PreparedStatement는 Statement와 거의 동일하게 사용할 수 있는 Statement 인터페이스의 자식 클래스로, 차이점은 캐시 사용 여부
- Statement를 사용하면 매번 쿼리를 수행할 때마다 아래의 과정을 거치지만, PreparedStatement는 처음 한 번만 세 단계를 거친후 캐시에 담아서 재사용한다.
1. 쿼리 문장 분석
2. 컴파일
3. 실행

-> 만약 동일한 쿼리를 반복적으로 수행한다면PreparedStatement가 DB에 훨씬 적은 부하를 주며 성능도 좋다.

<br>

#### Statement 객체의 자원 반환 방법
1. close() 메서드를 호출하는 경우
2. GC의 대상이 되어 GC 되는 경우

<br>

> ## 쿼리 수행 메서드
> |메서드|설명|
> |---|---|
> |executeQuery()|select 관련 쿼리를 수행하며, 수행 결과로 요청한 데이터 값이 ResultSet 객체의 형태로 전달된다.|
> |executeUpdate()|select 관련 쿼리를 제외한 DML, DDL 쿼리를 수행하며, 결과는 int 형태로 리턴된다.|
> |execute()|쿼리의 종류와 상관없이 쿼리를 수행하며, 수행 결과는 boolean 형태의 데이터를 리턴한다.|


## 3. ResultSet
- 쿼리를 수행한 결과는 ResultSet 인터페이스에 담기며, 여러 건의 데이터가 넘어오므로 next() 메서드를 사용하여 데이터의 커서를 다음으로 옮기면서 처리한다.
- last() 메서드는 커서를 맨 끝으로 옮기는 메서드로, 수행 시간이 데이터의 건수와 DB와의 통신 속도에 따라서 달라진다.
  - 전체 건수를 확인하기 위해서는 last() 메서드보다 쿼리를 한 번 더 던져서 확인하는 것이 훨씬 빠르다.

<br>

#### ResultSet 객체의 자원 반환 방법
1. close() 메서드를 호출하는 경우
2. GC의 대상이 되어 GC 되는 경우
3. 관련된 Statement 객체의 close() 메서드가 호출되는 경우

<br>

- 자원은 빨리 닫을수록 해당 DB 서버의 부담이 적어지게 되므로, 자동으로 호출되기 전에 close() 메서드를 호출하는 것이 좋다.
```java
finally {
    try{rs.close();} catch(Exception rse){}
    try{ps.close();} catch(Exception pse){}
    try{con.close();} catch(Exception cone){}
}
```

- 가장 좋은 방법은 DB와 관련된 처리를 담당하는 관리 클래스를 만드는 것으로, 보통 DBManager라는 이름의 클래스를 많이 사용한다.
- Connection 객체도 JNDI를 찾아서 사용하는 DataSource를 이용하여 얻는다.
- 여기에 ServiceLocator 패턴가지 적용하면, DB 연결 시간을 최소한으로 단축시킬 수 있다.

<br>

> 참고: AutoClosable 클래스<br>
> 리턴 타입이 void인 close() 메서드 단 한 개만 선언되어 있는데, try-with-resources 문장으로 관리되는 객체에 대해서 자동으로 close()를 처리한다.<br>
> 별도로 finally 블록에서 close() 메서드를 호출할 필요 없이, try 블록이 시작될 때 소괄호 안에 close() 메서드를 호출하는 객체를 생성해 준다.

<br>

## 4. JDBC 관련 팁
- setAutoCommit() 메서드를 사용하여 자동 커밋 여부를 지정하는 작업은 반드시 필요할 때만 사용한다.
- 배치성 작업을 할 때는 Statement 인터페이스에 정의되어 있는 addBatch() 메서드를 사용하여 쿼리를 지정하고, executeBatch() 메서드를 사용하여 쿼리를 수행한다.
  - 여러 개의 쿼리를 한 번에 수행할 수 있기 때문에 JDBC 호출 횟수가 감소되어 성능 향상
- setFetchSize() 메서드를 사용하여 데이터를 더 빠르게 가져온다.
  - 너무 맣은 건수를 지정하면 서버에 부하가 올 수 있으니, 적절하게 사용
- 한 건만 필요할 때는 한 건만 가져온다.