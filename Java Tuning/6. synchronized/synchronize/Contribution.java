package synchronize;

public class Contribution {
    private int amount = 0;

    public synchronized void donate() {
        amount++;
    }
    public int getTotal() {
        return amount;
    }
}
