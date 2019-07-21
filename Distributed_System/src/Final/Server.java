package Final;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable,Serializable {

    private static final long serialVersionUID= 2701531415539986794L;
    private String name;
    private String ip;
    private int port;

    private ServerSocket Socket;

    private Socket socketConn = null;

    protected Server() {}

    @Override
    public void run() { }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSocket(ServerSocket Socket) {
        this.Socket = Socket;
    }

    public void setSocketConn(Socket socketConn) {
        this.socketConn = socketConn;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getSocket() {
        return Socket;
    }

    public Socket getSocketConn() {
        return socketConn;
    }


    public void OpenServer(){
        try {
            Socket = new ServerSocket(getPort());

            //Print the data.
            System.out.println(getInstance() + " " + getName() + " " + getIp() + ":" + getPort() + " server opened!");

            while (true) {

                socketConn = Socket.accept();

                (new Thread(this)).start();
                Thread.sleep(600);

            }
        }catch(IOException ignored){} catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(Socket != null){
                try{
                    Socket.close();
                    System.out.println(getInstance() + " " + getName() + " " + getIp() + ":" + getPort() + " server closed!");
                }catch(IOException ignored){}
            }
        }
    }

    public void CloseServer(){
        if(Socket != null){
            try{
                Socket.close();
            }catch(IOException ignored){}
        }
    }

    public void CloseConnections(ObjectInputStream in, ObjectOutputStream out){
        try{
            if (in != null) {
                in.close();
            }
            if (out != null){

                out.close();
            }
        }
        catch(IOException ignored){}
    }

    public void CloseConnections(Socket socket, ObjectInputStream in, ObjectOutputStream out){
        try{
            if (socket != null){

                socket.close();
            }
            CloseConnections(in, out);
        }
        catch(IOException ignored){}
    }


    public void Push(Object message, String ip, int port) {

        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        Socket socket = null;

        boolean sent = false;

        try{
            socket = new Socket(ip, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(message);
            out.flush();

            sent = true;
        }catch(IOException ex){
            System.out.println("ERROR, while sending results to server!");
            ex.printStackTrace();
        }
        finally {
            CloseConnections(socket, in, out);
        }

        //Will try until do connection
        if(!sent){
            System.out.println("TRYING AGAIN");
            Push(message, ip, port);
        }
    }



    protected String getInstance(){
        if(this instanceof Publisher){
            return "Publisher";
        }
        else if(this instanceof MainSubscriber){
            return "Subscriber";
        }
        else if(this instanceof Broker){
            return "Broker";
        }
        else{
            return "Master";
        }
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
