import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static java.util.Collections.*;

public class CollectionsTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        System.out.println("list = " + list);

        addAll(list, 1, 2, 3, 4, 5);
        System.out.println("list = " + list);

        // 오른쪽 2칸 이동
        rotate(list, 2);
        System.out.println("list = " + list);

        // 첫 번째와 세 번째 교환
        swap(list, 0, 2);
        System.out.println("list = " + list);

        // 저장된 요소의 위치를 임의로 변경
        shuffle(list);
        System.out.println("list = " + list);

        // 정렬
        sort(list);
        System.out.println("list = " + list);

        // 역순정렬
        sort(list, reverseOrder());
        System.out.println("list = " + list);

        // 3이 저장된 위치 반환
        int idx = binarySearch(list, 3);
        System.out.println("idx = " + idx);

        System.out.println("max = " + max(list));
        System.out.println("min = " + min(list));
        System.out.println("min = " + max(list, reverseOrder()));

        // 리스트를 9로 채운다.
        fill(list, 9);
        System.out.println("list = " + list);

        // 리스트를 생성하고 2로 채운다.
        // 결과는 변경 불가
        List newList = nCopies(list.size(), 2);
        System.out.println("newList = " + newList);

        // 공통 요소가 없으면 true
        System.out.println(disjoint(list, newList));

        copy(list, newList);
        System.out.println("newList = " + newList);
        System.out.println("list = " + list);

        replaceAll(list, 2, 1);
        System.out.println("list = " + list);

        Enumeration e = enumeration(list);
        ArrayList list2 = list(e);
        System.out.println("list2 = " + list2);
    }
}
