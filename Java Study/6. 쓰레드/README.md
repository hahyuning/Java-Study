# 쓰레드

## 프로세스와 쓰레드
- 프로세스는 CPU 시간이나 메모리 등의 시스템 자원이 할당되는 독립적인 개체이다.
- 각 프로세스는 별도의 주소 공간에서 실행되며, 한 프로세스는 다른 프로세스의 변수나 자료구조에 접근할 수 없다.
  - 프로세스 간 통신 방법에는 파이프, 파일, 소켓을 이용한다.


- 스레드는 프로세스 안에 존재하며 프로세스의 자원(힙 영역)을 공유한다.
- 각각의 스레드는 독립적인 작업을 수행하기 위한 별도의 레지스터와 호출 스택을 가지고 있다.
- 모든 프로세스에는 최소 하나 이상의 쓰레드가 존재하며, 둘 이상의 쓰레드를 가진 프로세스를 멀티 쓰레드 프로세스라고 한다.

## 쓰레드의 상태
| 상태 | 설명 |
|--|---|
| NEW | 쓰레드가 생성되고 아직 start()가 호출되지 않은 상태 |
| RUNNABLE | 실행중 또는 실행 가능한 상태 |
| BLOCKED | 동기화블럭에 의해서 일시정지된 상태로, lock이 풀릴 때까지 기다리는 상태 |
| WAITING, TIMED_WAITING | 쓰레드의 작업이 종료되지는 않았지만 실행가능하지 않은 일시정지 상태 |
| TERMINATED | 쓰레드의 작업이 종료된 상태 |

<br>

## 멀티쓰레딩
- 하나의 프로세스 내에서 여러 쓰레드가 동시에 작업을 수행하는 것
- 코어가 아주 짧은 시간동안 여러 작업을 번갈아가며 수행하기 때문에, 여러 작업들이 모두 동시에 수행되는 것처럼 보인다.

#### 멀티쓰레딩의 장점
- CPU 사용률을 향상시킨다.
- 자원을 보다 효율적으로 사용할 수 있다.
- 사용자에 대한 응답성이 향상된다.
- 작업이 분리되어 코드가 간결해진다.

#### But, 여러 쓰레드가 같은 프로세스 내에서 자원을 공유하면서 작업하기 때문에, 동기화 문제나 교착상태가 발생할 수 있다.

> 교착상태란 두 쓰레드가 자원을 점유한 상태에서 서로 상대편이 점유한 자원을 사용하려고 기다리느라 진행이 멈춰있는 상태를 말한다.

> 여러 쓰레드가 여러 작업을 동시에 진행하는 것을 병행, 하나의 작업을 여러 쓰레드가 나눠서 처리하는 것을 병렬이라고 한다.

<br>

#### 문맥교환
- 두 프로세스를 전환하는데 드는 시간 (대기 중인 프로세스를 실행 상태로 전환하고 실행 중인 프로세스를 대기 상태나 종료 상태로 전환하는 데 드는 시간)
- 쓰레드간의 문맥교환이 일어날 때는 현재 진행 중인 작업의 상태를 저장하고 읽어오는 시간이 소요된다.
  - 다음에 실행해야할 명령어의 위치 (프로그램 카운터) 등의 정보 등
- 쓰레드의 스위칭에 비해 프로세스의 스위칭이 더 많은 정보를 저장해야하므로 더 많은 시간이 소요된다.

<br>

## start()와 run()
- main 메서드에서 run()을 호출하는 것은 생성된 쓰레드를 실행시키는 것이 아니라 단순히 클래스에 선언된 메서드를 호출하는 것
- start()는 새로운 쓰레드가 작업을 실행하는데 필요한 호출스택을 생성한 다음 run()을 호출해서, 생성된 호출스택에 run()이 첫 번째로 올라가게 한다.
- run() 수행이 종료된 쓰레드는 호출스택이 모두 비워지면서 이 쓰레드가 사용하던 호출스택은 소멸된다.

## 메인 쓰레드
- main 메서드의 작업을 수행하는 것도 쓰레드이며, 이를 main 쓰레드라고 한다.
- 프로그램을 실행하면 기본적으로 하나의 쓰레드를 생성하고, 그 쓰레드가 main 메서드를 호출하는 것

<br>

## 쓰레드의 우선순위
- 쓰레드는 우선순위라는 멤버변수를 가지고 있으며, 우선순위의 값에 따라 쓰레드가 얻는 실행시간이 달라진다.
- 시각적인 부분이나 사용자에게 빠르게 반응해야 하는 작업을 하는 쓰레드의 우선순위는 높아야 한다.
- 범위는 1 ~ 10이며, 숫자가 높을수록 우선순위가 높다.
- 쓰레드의 우선순위는 쓰레드를 생성한 쓰레드로부터 상속받는다.

## 쓰레드 그룹
- 서로 관련된 쓰레드를 그룹으로 다루기 위한 것으로, 쓰레드 그룹을 생성해서 그룹으로 묶어서 관리할 수 있다. 
- 자신이 속한 쓰레드 그룹이나 하위 쓰레드 그룹은 변경할 수 있지만, 다른 쓰레드 그룹의 쓰레드는 변경할 수 없다. (보안 목적)
- `ThreadGroup` 을 사용해서 그룹 생성


- 모든 쓰레드는 반드시 쓰레드 그룹에 포함되어야 하며, 쓰레드 그룹을 지정하지 않으면 기본적으로 자신을 생성한 쓰레드 그룹에 속하게 된다.
- JVM은 main과 system이라는 쓰레드 그룹을 만들고, JVM 운영에 필요한 쓰레드들을 생성해서 이 그룹에 포함시킨다.

<br>

## 데몬 쓰레드
- 다른 일반 쓰레드의 작업을 돕는 보조적인 역할을 수행하는 쓰레드
- 일반 쓰레드가 모두 종료되면 데몬 쓰레드는 강제적으로 자동 종료된다.
- 예) 가비지 컬렉터, 워드프로세서의 자동저장, 화면 자동갱신 등


- 데몬 쓰레드는 무한루프와 조건문을 이용해서 실행 후 대기하고 있다가 특정 조건이 만족되면 작업을 수행하고 다시 대기한다.
- 일반 쓰레드의 작성방법과 실행방법이 동일하며, 쓰레드를 생성한 다음 실행하기 전에 `setDaemon(true)`를 호출하면 된다.
- 데몬 쓰레드가 생성한 쓰레드는 자동적으로 데몬 쓰레드가 된다.

<br>

# 동기화
- 공유 데이터를 사용하는 코드 영역을 임계구역으로 지정하고, 공유 데이터가 가지고 있는 lock을 획득한 단 하나의 쓰레드만 이 영역 내의 코드를 수행할 수 있다.
- 해당 쓰레드가 임계구역 내의 모든 코드를 수행하고 lock을 반납해야만 다른 쓰레드가 반납된 lock을 획들하여 임계구역의 코드를 구행할 수 있다.

## Lock과 Condition을 이용한 동기화

| Lock 클래스               | 설명                                                                                                                                            |
|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| ReentrantLock          | - 재진입 가능한 lock으로, 특정 조건에서 lock을 풀고 나중에 다시 lock을 얻어 임계구역으로 들어와서 이후의 작업을 수행할 수 있다.                                                              |
| ReentrantReadWriteLock | - 읽기를 위한 lock과 쓰기를 위한 lock을 제공한다.<br>- 읽기 lock이 걸려 있으면, 다른 쓰레드가 일기 lock을 중복해서 걸고 읽기를 수행할 수 있다.<br>- 읽기 lock이 걸린 상태에서 쓰기 lock을 거는 것은 허용되지 않는다. |
| StampedLock            | - lock을 걸거나 해지할 때 스탬프를 사용하여, 읽기와 쓰기를 위한 lock 외에 낙관적 읽기 lock을 추가한 것<br>- 무조건 읽기 lock을 걸지 않고, 쓰기와 읽기가 충동할 때만 쓰기가 끝난 후에 읽기 lock을 건다.             |

## fork & join 프레임워크