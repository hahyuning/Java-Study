package synchronize;

public class ContributeTest {
    public static void main(String[] args) {
        Contributor[] crs = new Contributor[10];

//        // 기부자와 기부 단체 초기화
//        for (int loop = 0; loop < 10; loop++) {
//            Contribution group = new Contribution();
//            crs[loop] = new Contributor(group, "Contributor" + loop);
//        }

        Contribution group = new Contribution();
        for (int loop = 0; loop < 10; loop++) {
            crs[loop] = new Contributor(group, "Contributor" + loop);
        }

        // 기부 실행
        for (int loop = 0; loop < 10; loop++) {
            crs[loop].start();
        }
    }
}
