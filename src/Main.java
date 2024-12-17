public class Main {

    public static void main(String[] args) {

        // Inicia o servidor em uma thread separada
        Thread serverThread = new Thread(() -> {
            PhilosopherServer server = new PhilosopherServer();
            server.startServer();
        });
        serverThread.start();

        try {
            // Aguarda o servidor iniciar
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int numberOfPhilosophers = 5;
        // Inicia os filósofos em threads separadas
        for (int i = 0; i < numberOfPhilosophers; i++) {
            Thread philosopherThread = new Thread(() -> {
                PhilosopherClient client = new PhilosopherClient();
                client.start();
            });
            philosopherThread.start();
        }
    }
}
