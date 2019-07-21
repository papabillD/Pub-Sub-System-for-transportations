package Final;

public class Publisher extends Server {

    Publisher(String ip, int port) {
        this.setIp(ip);
        this.setPort(port);
    }

    public synchronized void run() {
        super.run();

    }
    public void Initialize() {

        this.OpenServer();

    }
}
