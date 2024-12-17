import java.net.Socket;

public class PhilosopherRecord {
    private int id;
    private Socket socket;
    private boolean hasForks;
    private int thinkCount;
    private int eatCount;

    public PhilosopherRecord(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.hasForks = false;
        this.thinkCount = 0;
        this.eatCount = 0;
    }

    public boolean hasForks() {
        return hasForks;
    }

    public void setHasForks(boolean hasForks) {
        this.hasForks = hasForks;
    }

    public void incrementThinkCount() {
        this.thinkCount++;
    }

    public void incrementEatCount() {
        this.eatCount++;
    }
}
