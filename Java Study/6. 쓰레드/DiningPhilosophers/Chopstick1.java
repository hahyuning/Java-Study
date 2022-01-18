package DiningPhilosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick1 {
    private Lock lock;

    public Chopstick1() {
        lock = new ReentrantLock();
    }

    public boolean pickUp() {
        return lock.tryLock();
    }

    public void putDown() {
        lock.unlock();
    }
}

class Philosopher1 extends Thread {
    private int bites = 10;
    private Chopstick1 left, right;

    public Philosopher1(Chopstick1 left, Chopstick1 right) {
        this.left = left;
        this.right = right;
    }

    public void eat() {
        if (pickUp()) {
            chew();
            putDown();
        }
    }

    public boolean pickUp() {
        if (!left.pickUp()) {
            return false;
        }
        // 오른쪽 젓가락을 집을 수 없는 경우 왼쪽 젓가락을 놓게 한다.
        // 하지만 철학자들이 완벽하게 동기화되었을 경우에는
        // 철학자들이 모두 동시에 왼쪽 젓가락을 집고, 내려놓는 과정을 반복하는 문제 발생
        if (!right.pickUp()) {
            left.putDown();
            return false;
        }
        return true;
    }

    public void chew() {
    }

    public void putDown() {
        right.putDown();
        left.putDown();
    }

    public void run() {
        for (int i = 0; i < bites; i++) {
            eat();
        }
    }
}
