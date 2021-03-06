# IO
- 만약 자바를 사용해서 하드디스크에 있는 데이터를 읽는다면 아래와 같은 프로세스를 거치게 된다.
  1. 파일을 읽으라는 메서드를 자바에 전달한다.
  2. 파일명을 전달받은 메서드가 운영체제의 커널에게 파일을 읽어 달라고 요청한다.
  3. 커널이 하드디스크로부터 파일을 읽어서 커널에 있는 버퍼에 복사한다.
  4. 자바에서는 마음대로 커널의 버퍼를 사용하지 못하므로, JVM으로 그 데이터를 전달한다.
  5. JVM에서 메서드에 있는 스트림 관리 클래스를 사용하여 데이터를 처리한다.

- 자바에서는 3번과 4번 작업을 수행할 때 대기하는 시간이 발생하고, 이로인해 입출력 속도가 늦어진다.
- 이러한 단점을 보완하기 위해 NIO가 탄생했으며, NIO는 3번 작업을 자바에서 직접 통제하여 시간을 단축시켜 준다.

<br>

# NIO
