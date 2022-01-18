package DiningPhilosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick2 {
    private Lock lock;
    private int number;

    public Chopstick2(int n) {
        lock = new ReentrantLock();
        this.number = n;
    }

    public void pickUp() {
        lock.lock();
    }

    public void putDown() {
        lock.unlock();
    }

    public int getNumber() {
        return number;
    }
}

class Philosopher2 extends Thread {
    private int bites = 10;
    private Chopstick2 lower, higher;
    private int index;

    public Philosopher2(int i, Chopstick2 left, Chopstick2 right) {
        index = i;

        // 젓가락에 0부터 n-1 까지 숫자를 매긴 뒤 숫자가 작은 젓가락을 먼저 집도록 한다.
        // 즉, 마지막 철학자를 제외한 모든 철학자들이 오른쪽보다 왼족 젓가락을 먼저 집게 된다. -> 사이클이 발생하지 않는다.
        if (left.getNumber() < right.getNumber()) {
            this.lower = left;
            this.higher = right;
        }
    }

    public void eat() {
        pickUp();
        chew();
        putDown();
    }

    public void pickUp() {
        lower.pickUp();
        higher.pickUp();
    }

    public void chew() {
    }

    public void putDown() {
        higher.putDown();
        lower.putDown();
    }

    public void run() {
        for (int i = 0; i < bites; i++) {
            eat();
        }
    }
}
