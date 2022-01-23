import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LamdaEx3 {

    public static void main(String[] args) {
        Supplier<Integer> s = () -> (int) (Math.random() * 100) + 1;
        Consumer<Integer> c = i -> System.out.println(i + ", ");
        Predicate<Integer> p = i -> i % 2 == 0;
        Function<Integer, Integer> f = i -> i / 10 * 10;

        List<Integer> list = new ArrayList<>();
        makeRandomList(s, list);
        System.out.println(list);

        printEvenNum(p, c, list);

        List<Integer> newList = doSomthing(f, list);
        System.out.println(newList);
    }

    static <T> void makeRandomList(Supplier<T> s, List<T> list) {
        for (int i = 0; i < 10; i++) {
            list.add(s.get());
        }
    }

    static <T> void printEvenNum(Predicate<T> p, Consumer<T> c, List<T> list) {
        for (T i : list) {
            if (p.test(i)) {
                c.accept(i);
            }
        }
    }

    static <T> List<T> doSomthing(Function<T, T> f, List<T> list) {
        List<T> newList = new ArrayList<>();

        for (T i : list) {
            newList.add(f.apply(i));
        }
        return newList;
    }
}
