@FunctionalInterface
interface MyFunction {
    void run();
}

public class LambdaEx1 {

    // 매개변수의 타입이 함수형 인터페이스인 메서드
    static void execute(MyFunction f) {
        f.run();
    }

    // 반환 타입이 함수형 인터페이스인 메서드
    static MyFunction getMyFunction() {
        MyFunction f = () -> System.out.println("f3.run()");
        return f;
    }

    public static void main(String[] args) {
        MyFunction f1 = () -> System.out.println("f1.run()");

        // 익명 클래스로 run() 구현
        MyFunction f2 = new MyFunction() {
            @Override
            public void run() { // public 을 반드시 붙여야 한다.
                System.out.println("f2.run()");
            }
        };

        MyFunction f3 = getMyFunction();

        f1.run();
        f2.run();
        f3.run();

        execute(f1);
        execute(() -> System.out.println("run()"));
    }
}
