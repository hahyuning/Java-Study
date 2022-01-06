package Map;

import java.util.*;
import java.util.ArrayList;

public class TreeMapTest {
    public static void main(String[] args) {
        String[] data = {"A", "K", "A", "K", "D", "K", "A", "K", "K", "K", "Z", "D"};

        TreeMap map = new TreeMap();

        for (int i = 0; i < data.length; i++) {
            if (map.containsKey(data[i])) {
                Integer value = (Integer) map.get(data[i]);
                map.put(data[i], value + 1);
            }
            else {
                map.put(data[i], 1);
            }
        }

        Iterator it = map.entrySet().iterator();

        System.out.println("기본정렬");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            int value = (Integer) entry.getValue();
            System.out.println(entry.getKey() + " : " + printBar('#', value) + " " + value);
        }

        System.out.println();

        // map을 ArrayList로 변환한 다음 Collections.sort()로 정렬
        Set set = map.entrySet();
        List list = new ArrayList(set);

        Collections.sort(list, new ValueComparator());

        it = list.iterator();

        System.out.println("값의 크기가 큰 순으로 정렬");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            int value = (Integer) entry.getValue();
            System.out.println(entry.getKey() + " : " + printBar('#', value) + " " + value);
        }
    }

    static class ValueComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            if (o1 instanceof Map.Entry && o2 instanceof Map.Entry) {
                Map.Entry e1 = (Map.Entry) o1;
                Map.Entry e2 = (Map.Entry) o2;

                int v1 = ((Integer) e1.getValue()).intValue();
                int v2 = ((Integer) e2.getValue()).intValue();

                return v2 - v1;
            }
            return -1;
        }
    }

    public static String printBar(char ch, int value) {
        char[] bar = new char[value];

        Arrays.fill(bar, ch);

        return new String(bar);
    }
}
