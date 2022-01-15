import java.util.List;

// 이펙티브 자바
public class VarargsTest {

    public static void main(String[] args) {
        List<String> arr1 = List.of("aa", "bb");
        List<String> arr2 = List.of("cc", "dd");
        dangerous(arr1, arr2);
    }

    static void dangerous(List<String>... stringLists) {
        List<Integer> intList = List.of(42); // {42}

        Object[] objects = stringLists;
        objects[0] = intList; //힙 오염 발생
        String s = stringLists[0].get(0);// ClassCastException 발생
    }
}
