public class GarbageCollector {
    public static void main(String[] args) {
        ThreadExt gc = new ThreadExt();
        gc.setDaemon(true);
        gc.start();

        int requiredMemory = 0;

        for (int i = 0; i < 20; i++) {
            requiredMemory = (int) (Math.random() * 10) * 20;

            // 필요한 메모리가 사용할 수 있는 양보다 크거나, 전체 메모리의 60% 이상을 사용한 경우
            // cg를 깨운다.
            if (gc.freeMemory() < requiredMemory || gc.freeMemory() < gc.totalMemory() * 0.4) {
                gc.interrupt();
                try {
                    gc.join(100);
                } catch (InterruptedException e) {
                    System.out.println("Awaken by interrupt()");
                }
            }

            gc.usedMemory += requiredMemory;
            System.out.println("usedMemory : " + gc.usedMemory);
        }
    }
}

class ThreadExt extends Thread {
    final static int MAX_MEMORY = 1000;
    int usedMemory = 0;

    public void run() {
        while (true) {
            try {
                // 10초 대기
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                System.out.println("Awaken by interrupt()");
            }
            gc();
            System.out.println("Garbage Collected. Free Memory : " + freeMemory());
        }
    }

    public void gc() {
        usedMemory -= 200;
        if (usedMemory < 0) {
            usedMemory = 0;
        }
    }

    public int totalMemory() { return MAX_MEMORY; }
    public int freeMemory() { return MAX_MEMORY - usedMemory; }
}
