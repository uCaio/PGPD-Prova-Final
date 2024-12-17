import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PhilosopherServer {

    private static final int PORT = 12345;
    private final Map<Integer, PhilosopherRecord> philosophers = new ConcurrentHashMap<>();
    private int nextId = 1;

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for philosophers...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new PhilosopherHandler(clientSocket, this)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int registerPhilosopher(Socket socket) {
        int id = nextId++;
        philosophers.put(id, new PhilosopherRecord(id, socket));
        return id;
    }

    public synchronized boolean requestForks(int philosopherId) {
        PhilosopherRecord record = philosophers.get(philosopherId);
        if (record != null && !record.hasForks()) {
            record.setHasForks(true);
            return true;
        }
        return false;
    }

    public synchronized void releaseForks(int philosopherId) {
        PhilosopherRecord record = philosophers.get(philosopherId);
        if (record != null) {
            record.setHasForks(false);
        }
    }
}
