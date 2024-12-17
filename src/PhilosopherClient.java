import java.io.*;
import java.net.*;
import java.util.Random;

public class PhilosopherClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    private static final Random random = new Random();
    private Integer philosopherId = null;

    public void start() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT); 
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("HELLO");
            String response = in.readLine();
            if (response.startsWith("HI")) {
                philosopherId = Integer.parseInt(response.split(" ")[1]);
                System.out.println("Connected as Philosopher ID: " + philosopherId);
            }

            while (true) {
                think(out, in);
                requestForks(out, in);
                eat(out, in);
                releaseForks(out, in);

                if (random.nextDouble() < 0.1) {
                    quit(out, in);
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Connection lost. Reconnecting...");
            start();
        }
    }

    private void think(PrintWriter out, BufferedReader in) throws InterruptedException, IOException {
        double mean = 5.0;
        double stdDev = 2.0;
        double thinkingTime = Math.max(0, random.nextGaussian() * stdDev + mean);
        System.out.println("Philosopher " + philosopherId + " is thinking for " + thinkingTime + " seconds...");

        out.println("THINKING " + philosopherId);
        String response = in.readLine();
        if ("OK".equals(response)) {
            Thread.sleep((long) (thinkingTime * 1000));
        }
    }

    private void requestForks(PrintWriter out, BufferedReader in) throws IOException {
        while (true) {
            out.println("REQUEST_FORKS " + philosopherId);
            String response = in.readLine();
            if ("FORKS_GRANTED".equals(response)) {
                System.out.println("Philosopher " + philosopherId + " obtained forks.");
                break;
            } else if ("WAIT".equals(response)) {
                System.out.println("Philosopher " + philosopherId + " is waiting for forks...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void eat(PrintWriter out, BufferedReader in) throws InterruptedException, IOException {
        System.out.println("Philosopher " + philosopherId + " is eating...");
        out.println("EATING " + philosopherId);
        String response = in.readLine();
        if ("OK".equals(response)) {
            Thread.sleep(2000);
        }
    }

    private void releaseForks(PrintWriter out, BufferedReader in) throws IOException {
        out.println("RELEASE_FORKS " + philosopherId);
        String response = in.readLine();
        if ("OK".equals(response)) {
            System.out.println("Philosopher " + philosopherId + " released the forks.");
        }
    }

    private void quit(PrintWriter out, BufferedReader in) throws IOException {
        out.println("QUIT");
        String response = in.readLine();
        if ("BYE".equals(response)) {
            System.out.println("Philosopher " + philosopherId + " disconnected cleanly.");
        }
    }

    public static void main(String[] args) {
        PhilosopherClient client = new PhilosopherClient();
        client.start();
    }
}
