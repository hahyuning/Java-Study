package synchronize;

public class ContributionStatic {
    private static int amount = 0;

    public synchronized void donate() {
        amount++;
    }
    public int getTotal() {
        return amount;
    }
}
