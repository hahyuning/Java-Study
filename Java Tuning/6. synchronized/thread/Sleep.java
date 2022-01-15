package thread;

public class Sleep extends Thread {
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Somebody stopped me");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Sleep s = new Sleep();
        // 스레드 시작
        s.start();

        try {
            int cnt = 0;
            while (cnt < 10) {
                s.join(1000);
                cnt++;
                System.out.println(cnt + " sec waited");
            }
            if (s.isAlive()) {
                s.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
