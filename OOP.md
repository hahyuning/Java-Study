
# 객체지향 프로그래밍2

## 1. 상속
+ 기존의 클래스를 재사용하여 새로운 클래스를 작성하는 것
+ 장점: 코드를 재사용함으로써 중복을 줄이고, 코드를 공통적으로 관리할 수 있어 유지보수에 용이하다.
+ 클래스 다이어그램에서 일반화 관계
<br>

+ 키워드로 `extends`를 사용하며, 생성자와 초기화 블럭은 상속되지 않고 멤버(변수, 메서드)만 상속된다.
+ 자바에서는 단일 상속만 허용 -> 클래스 간의 관계가 보다 명확해지고 코드를 더욱 신뢰할 수 있게 만들어준다.
<br>

#### 1-1. 오버라이딩
조상 클래스로부터 상속받은 메서드의 내용을 변경하는 것

> 주의점
> + 메서드의 선언부는 조상 클래스의 메서드와 완전히 일치해야 한다.
> + 접근 제어자는 조상 클래스의 메서드보다 좁은 범위로 변경할 수 없다.
> + 예외는 조상 클래스의 메서드보다 많이 선언할 수 없다.
<br>

#### 1-2. Object 클래스
+ 모든 클래스의 상속계층도의 최상위에 있는 조상 클래스
+ toString(), equals(Object o)를 포함해서 모든 인스턴스가 가져야 할 기본적인 메서드가 정의되어 있다.
<br>

## 2. 포함관계
+ 한 클래스의 멤버변수로 다른 클래스 타입의 참조변수를 선언하는 것
+ 장점: 코드를 이해하기 쉽고, 단위 클래스 별로 코드를 나누어 작성하기 때문에 관리하기가 수월하다.
<br>

***
## 3. 제어자
클래스, 변수, 메서드의 선언부에 함께 사용되어 부가적인 의미를 부여한다.

#### 3-1. 접근제어자
해당하는 멤버 또는 클래스를 외부에서 접근하지 못하도록 제한하는 역할

|제어자|같은 클래스|같은 패키지|자손 클래스|전체|
|:---:|:---:|:---:|:---:|:---:|
|public|O|O|O|O|
|protectd|O|O|O|X|
|default|O|O|X|X|
|private|O|X|X|X|
<br>

**private**: 멤버변수들은 주로 private로 접근을 제한하고, 외부에서는 메서드를 통해서만 멤버변수에 접근하게 하여 클래스 내부에 선언된 데이터를 보호한다.
+ 예제 1 - 도메인 모델 
<br>

주문 엔티티
```java
@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    private Long id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "member_id")
    private Member member;

    // @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 생성 메서드: 주문 엔티티를 생성할 때 사용
    // 주문 회원, 배송정보, 주문 상품의 정보를 받아서 실제 주문 엔티티를 생성
    public static Order createOrder(Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
	
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }
}
```

> Setter를 모두 열어두게 되면 엔티티가 의도와 다르게 변경될 가능성이 크고, 변경지점이 너무 많아 추적하기가 어렵고 유지보수하기 힘들다.
> -> 엔티티를 변경할 때는 Setter는 꼭 필요한 곳에서만 사용하고, 대신에 변경 지점을 명확하게 알 수 있는 비즈니스 메서드를 따로 작성하는 것이 좋다.
<br>

서비스 계층 - 주문 로직
```java

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        
	/**
         * 생략
         */
	
        // 주문 생성
        Order order = Order.createOrder(member, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }
    
```

> 서비스 계층은 단순히 엔티티에 필요한 요청을 위임하는 역할을 하고, 엔티티가 비즈니스 로직을 가지고 객체지향의 특성을 활용하는 것을 도메인 모델 패턴이라고 한다.
> 반대로 엔티티에는 비즈니스 로직이 거의 없고 서비스 계층에서 대부분의 비즈니스 로직을 처리하는 것을 트랜잭션 스크립트 패턴이라고 한다.
<br>

+ 예제 2 - 싱글톤 구현
	+ static 영역에 객체 instance를 미리 하나 생성해서 올려둔다.
	+ 이 객체 인스턴스가 필요하면 getInstance() 메서드(static 메서드로, instance 반환)를 통해서만 조회 가능하게 만든다.
	+ 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private로 막아서 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.

```java
public class Singleton {

    // 싱글톤 객체
    private static final Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }

    // 기본 생성자
    private Singleton() {
    }

}
```
<br>

> 싱글톤 주의점: 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 객체는 무상태로 설계해야 한다. 
> + 특정 클라이언트에 의존적인 필드가 있으면 안 된다.
> + 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안 된다.
> + 가급적 읽기만 가능해야 하며, 필드 대신에 공유되지 않는 지역변수, 파라미터, 쓰레드로컬 등을 사용해야 한다.
<br>

#### 3-2. 그 외
+ **final**: 변수에 사용되면 값을 변경할 수 없는 상수가 되며, 메서드에 사용하면 오버라이딩할 수 없게 되고, 클래스에 사용되면 자신을 확장하는 자손클래스를 정의하지 못하게 된다.

예제 - 생성자 주입
```java
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    /**
     * 생략
     */
 
}
```
> 스프링의 경우 스프링 빈으로 등록되는 컨트롤러, 서비스, 엔티티, 리포지토리 등은 싱글톤으로 만들어지므로, 필요한 객체를 final로 선언해 두고 스프링 빈으로 등록시 호출되는 생성자를 통해 의존성을 주입받는다.
<br>

+ abstract: 메서드의 선언부만 작성하고 실제 수행내용은 구현하지 않은 추상메서드를 선언하는데 사용한다.
<br>

***
## 4. 추상클래스
+ 추상메서드를 포함하고 있는 클래스로 메서드의 내용이 상속받는 클래스에 따라 달라지는 경우 조상클래스에서는 선언부만 작성하고, 실제 내용은 상속받는 클래스에서 구현하도록 비워놓는 것
+ 추상클래스로 인스턴스를 생성할 수는 없고, 상속을 통해서 자손클래스에 의해서만 완성될 수 있다.
+ 추상클래스로부터 상속받는 자손클래스는 오버라이딩을 통해 조상인 추상클래스의 추상메서드를 모두 구현해야 한다.
<br>

> #### 추상클래스 vs 인터페이스
![image](https://user-images.githubusercontent.com/60869749/144988756-2a9c6723-2e8c-4338-b2bb-b22fd139ed0a.png)
<br>

## 5. 인터페이스
+ 추상클래스와 달리 몸통을 갖춘 일반 메서드 또는 멤버변수를 구성원으로 가질 수 없다.
+ 오직 추상메서드와 상수만을 멤버로 가질 수 있다. (모든 멤버변수는 public static final, 모든 메서드는 public abstract, 둘 다 제어자 생략 가능)
+ 인터페이스는 인터페이스로부터만 상속받을 수 있으며, 클래스와 달리 다중상속이 가능하다.
+ 키워드로 **implements**를 사용하며, 인터페이스를 구현한 클래스는 인터페이스의 모든 메서드를 오버라이딩 해야 한다.

> 다형성
> + 조상클래스 타입의 참조변수로 자손클래스의 인스턴스를 참조할 수 있도록 하는 것
> + 자손타입과 조상타입 간에 양방향으로 형변환이 가능하지만, 조상타입을 자손타입으로 다운캐스팅 하는경우 형변환을 명시해주어야 한다.
> + 형변환을 수행하기 전에 **instanceof** 연산자를 사용해서 참조변수가 참조하고 있는 실제 인스턴스 타입을 확인하는 것이 안전하다.
<br>

예제 - 다형성 활용
```java
public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
```

멤버 서비스 구현체
```java
@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 생략
     */
 
}
```

> 객체 설계 시 역할(인터페이스)과 구현(구현 객체)으로 기능을 분리하면 클라이언트는 구현 대상의 내부 구조가 변경되거나 구현 대상 자체를 변경해도 영향을 받지 않기 때문에,
> 유연하고 변경이 용이하며, 확장 가능한 설계가 가능하다.
> 즉, 위의 코드에서는 매개변수의 다형성을 이용하여 데이터베이스가 중간에 변경되더라도 서비스 계층의 코드는 전혀 건들 필요없이, 설정정보만 변경해주면 된다.


***
## 7. 내부 클래스

## 8. 익명 클래스

