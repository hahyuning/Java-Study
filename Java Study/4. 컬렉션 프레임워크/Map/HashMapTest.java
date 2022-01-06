package Map;

import java.util.*;

public class HashMapTest {
    public static void main(String[] args) {
        HashMap map = new HashMap<>();

        map.put("user1", 90);
        map.put("user2", 80);
        map.put("user1", 100);
        map.put("user3", 90);

        // 키와 값을 함께 읽어온다.
        Set set = map.entrySet();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println("이름: " + e.getKey() + ", 점수: " + e.getValue());
        }

        // 키를 읽어온다.
        set = map.keySet();
        System.out.println("참가자 명단: " + set);

        // 값을 읽어온다.
        Collection values = map.values();
        it = values.iterator();

        int total = 0;
        while (it.hasNext()) {
            Integer i = (Integer) it.next();
            total += i.intValue();
        }

        System.out.println("total = " + total);
    }
}
