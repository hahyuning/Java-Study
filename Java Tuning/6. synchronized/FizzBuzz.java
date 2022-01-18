public class FizzBuzz {
    public static void main(String[] args) {
        Thread[] threads = {new FizzBuzzThread(true, true, 30, "FizzBuzz"),
                new FizzBuzzThread(true, false, 30, "Fizz"),
                new FizzBuzzThread(false, true, 30, "Buzz"),
                new NumberThread(false, false, 30)};

        for (Thread thread : threads) {
            thread.start();
        }
    }

    static class FizzBuzzThread extends Thread {

        private static Object lock = new Object(); // 락
        protected static int current = 1; // 현재 숫자
        private int max; // 최대 숫자
        private boolean div3, div5;
        private String toPrint; // 출력 정보

        public FizzBuzzThread(boolean div3, boolean div5, int max, String toPrint) {
            this.div3 = div3;
            this.div5 = div5;
            this.max = max;
            this.toPrint = toPrint;
        }

        public void print() {
            System.out.println(toPrint);
        }

        public void run() {
            while (true) {
                synchronized (lock) {
                    if (current > max) {
                        return;
                    }

                    if (((current % 3 == 0) == div3) && ((current % 5 == 0) == div5)) {
                        print();
                        current++;
                    }
                }
            }
        }
    }

    static class NumberThread extends FizzBuzzThread {
        public NumberThread(boolean div3, boolean div5, int max) {
            super(div3, div5, max, null);
        }
        public void print() {
            System.out.println(current);
        }
    }
}
