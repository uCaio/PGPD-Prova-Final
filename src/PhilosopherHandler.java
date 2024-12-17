import java.io.*;
import java.net.*;

public class PhilosopherHandler implements Runnable {

    private Socket socket;
    private PhilosopherServer server;

    public PhilosopherHandler(Socket socket, PhilosopherServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            int philosopherId = 0;
            String request;

            while ((request = in.readLine()) != null) {
                System.out.println("Server received: " + request);

                if (request.equals("HELLO")) {
                    philosopherId = server.registerPhilosopher(socket);
                    System.out.println("HELLO received. Assigning Philosopher ID: " + philosopherId);
                    out.println("HI " + philosopherId);
                } else if (request.startsWith("THINKING")) {
                    System.out.println("Philosopher " + philosopherId + " is thinking.");
                    out.println("OK");
                } else if (request.startsWith("REQUEST_FORKS")) {
                    if (server.requestForks(philosopherId)) {
                        System.out.println("Philosopher " + philosopherId + " obtained forks.");
                        out.println("FORKS_GRANTED");
                    } else {
                        System.out.println("Philosopher " + philosopherId + " is waiting for forks.");
                        out.println("WAIT");
                    }
                } else if (request.startsWith("EATING")) {
                    System.out.println("Philosopher " + philosopherId + " is eating.");
                    out.println("OK");
                } else if (request.startsWith("RELEASE_FORKS")) {
                    // Libera os garfos
                    server.releaseForks(philosopherId);
                    System.out.println("Philosopher " + philosopherId + " released the forks.");
                    out.println("OK");
                } else if (request.equals("QUIT")) {
                    System.out.println("Philosopher " + philosopherId + " disconnected.");
                    out.println("BYE");
                    break;
                } else {
                    System.out.println("Unknown request: " + request);
                    out.println("ERROR Unknown request");
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}
