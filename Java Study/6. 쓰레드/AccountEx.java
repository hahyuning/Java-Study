public class AccountEx {

    public static void main(String[] args) {
        Runnable r = new RunnableImpl();
        new Thread(r).start();
        new Thread(r).start();
    }
}

class Account {
    // 동기화를 위해 외부에서 직접 접근을 막는다.
    private int balance = 1000;

    public int getBalance() {
        return balance;
    }

    public synchronized void withdraw(int money) {
        if (balance >= money) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            balance -= money;
        }
    }
}

class RunnableImpl implements Runnable {
    Account acc = new Account();

    public void run() {
        while (acc.getBalance() > 0) {
            int money = (int) (Math.random() * 3 + 1) * 100;
            acc.withdraw(money);

            System.out.println("balance : " + acc.getBalance());
        }
    }
}