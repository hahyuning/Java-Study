package synchronize;

public class ContributeStaticTest {
    public static void main(String[] args) {
        ContributorStatic[] crs = new ContributorStatic[10];

//        for (int loop = 0; loop < 10; loop++) {
//            ContributionStatic group = new ContributionStatic();
//            crs[loop] = new ContributorStatic(group, "Contributor" + loop);
//        }

        ContributionStatic group = new ContributionStatic();
        for (int loop = 0; loop < 10; loop++) {
            crs[loop] = new ContributorStatic(group, "Contributor" + loop);
        }

        // 기부 실행
        for (int loop = 0; loop < 10; loop++) {
            crs[loop].start();
        }
    }
}
