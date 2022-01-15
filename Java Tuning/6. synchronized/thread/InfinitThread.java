package thread;

public class InfinitThread extends Thread {
    int value = Integer.MIN_VALUE;
    private boolean flag = true;

    public void run() {
        while (flag) {
            value++;
            if (value == Integer.MAX_VALUE) {
                value = Integer.MIN_VALUE;
                System.out.println("MAX_VALUE reached");
            }
            try {
                Thread.sleep(0, 1);
            } catch (Exception e) {
                break;
            }
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
