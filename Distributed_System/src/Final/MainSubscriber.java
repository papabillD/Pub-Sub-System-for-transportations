/*KARYDAS ALEXANDROS:3140074
  PAPAVASILIS DIMITRIOS:3140151
  FOUSFOUKAS MICHAIL:3140217
*/
package Final;

import java.io.*;

public class MainSubscriber extends Server {

    String lineCode;
    String lineId;
    public MainSubscriber(String name,String ip,int port){
        this.setName(name);
        this.setIp(ip);
        this.setPort(port);
    }

    public MainSubscriber(String name,String ip,int port,String lineCode){
        this.setName(name);
        this.setIp(ip);
        this.setPort(port);
        this.lineCode=lineCode;
    }

    public void run(){
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try{
            out = new ObjectOutputStream(getSocketConn().getOutputStream());
            in = new ObjectInputStream(getSocketConn().getInputStream());

            Query results = (Query) in.readObject();

            System.out.println("Hello mr/ms "+this.getName()+". The Bus "+ results.getLineID() +" is located: "+results.getResult());

            if(results.getResult().equals("I can't find the position.Perhaps sensor is broken! (The txt file comes to an end)")){
                this.CloseServer();
            }

        }
        catch (ClassNotFoundException | IOException ignored) {}
        finally {
            CloseConnections(in, out);
        }
    }

    public static void main(String[] args) {
        BufferedReader in = null;

        try {

            in = new BufferedReader(new InputStreamReader(System.in));

            String currentIp = Available_IP_and_PORTS.GetAddressForThisPC();
            int availPort = Available_IP_and_PORTS.GetAvailablePortForThisPC();

            System.out.println("Set the name of the client:");
            String clientName = in.readLine();

            System.out.println("I will create a Subscriber with ip for this device. IP: " + currentIp);
            String ip = currentIp;

            System.out.println("I will create a Subscriber with an available port for this device. PORT: " + availPort);
            int port = availPort;

            System.out.println("Set the IP of the Master:");
            String masterIP = in.readLine();

            System.out.println("Set the Port of the Master:");
            int masterPort = Integer.parseInt(in.readLine());

            System.out.println("Enter the bus:");
            String lineId = in.readLine();

            Query query = new Query(lineId,ip,port);
            MainSubscriber subscriber = new MainSubscriber(clientName,ip,port);

            subscriber.Push(query,masterIP,masterPort);
            subscriber.result();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void result(){

        this.OpenServer();

    }

    public String getLineCode(){
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
