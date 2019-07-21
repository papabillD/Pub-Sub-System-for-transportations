package Final;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;

public class Available_IP_and_PORTS {

    public static String GetAddressForThisPC(){
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch (IOException ignored) {}

        return ip;
    }

    public static int GetAvailablePortForThisPC(){

        Random random = new Random(System.currentTimeMillis());
        while(true){
            ServerSocket socket = null;
            try{
                int port = random.nextInt(25000);
                if(port < 16000){
                    continue;
                }

                socket = new ServerSocket(port);
                return port;
            }
            catch(IOException ignored){ }
            finally{
                if(socket != null){
                    try{
                        socket.close();
                    }
                    catch(IOException ignored){ }
                }
            }
        }

    }
}
