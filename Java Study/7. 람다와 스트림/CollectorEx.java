import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CollectorEx {
    public static void main(String[] args) {
        String[] strArr = {"aaa", "bbb", "ccc"};
        Stream<String> strStream = Stream.of(strArr);

        String result = strStream.collect(new ConcatCollector());

        System.out.println(Arrays.toString(strArr));
        System.out.println("result = " + result);
    }
}


class ConcatCollector implements Collector<String, StringBuilder, String> {

    // 수집 결과를 저장할 공간 제공
    @Override
    public Supplier<StringBuilder> supplier() {
        return StringBuilder::new;
    }

    // 스트림의 요소를 어떻게 supplier()가 제공한 공간에 누적할 것인지 정의
    @Override
    public BiConsumer<StringBuilder, String> accumulator() {
        return StringBuilder::append;
    }

    // 여러 쓰레드에 의해 처리된 결괄르 어떻게 합칠 것인지 정의 (병렬 스트림)
    @Override
    public BinaryOperator<StringBuilder> combiner() {
        return StringBuilder::append;
    }

    // 결과를 최종적으로 변환할 방법 제공
    @Override
    public Function<StringBuilder, String> finisher() {
        return StringBuilder::toString;
    }

    // 컬렉터가 수행하는 작업의 속성에 대한 정보 제공
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}