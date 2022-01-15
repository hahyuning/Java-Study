package thread;

public class InterruptSample {
    public static void main(String[] args) throws InterruptedException {
        InfinitThread infinit = new InfinitThread();
        infinit.start();

        Thread.sleep(2000);
        System.out.println("isInterrupted= " + infinit.isInterrupted());
        infinit.interrupt(); // 스레드가 대기 상태일 때만 interrupt 시킬 수 있다.
        System.out.println("isInterrupted= " + infinit.isInterrupted());

        infinit.setFlag(false);
    }
}
