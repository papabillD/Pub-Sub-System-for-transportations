/*KARYDAS ALEXANDROS:3140074
  PAPAVASILIS DIMITRIOS:3140151
  FOUSFOUKAS MICHAIL:3140217
*/
package Final;

import java.io.*;

public class MainBroker {

    public static void main(String[] args){

        BufferedReader in = null;

        in = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Set the IP of the Master:");
            String masterIP = in.readLine();

            System.out.println("Set the Port of the Master:");
            int masterPort = Integer.parseInt(in.readLine());

            readIPandPORT(masterIP,masterPort);


        } catch (IOException e) {
            e.printStackTrace();
        }

        Broker broker = Broker.brokers.get(0);
        broker.Initialize();



    }

    public static void readIPandPORT(String masterIP,int masterPort ){
        File f = null;
        BufferedReader reader = null;

        try{
            f=new File("IP_PORT.txt");
        }
        catch(NullPointerException e){
            System.err.println("File not found...");
        }

        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        }
        catch (FileNotFoundException e){
            System.err.println("Error opening file!");
        }

        try{
            String line;
            boolean new_item=true;

            String name=null,ip = null;
            int port = -1;


            while((line = reader.readLine()) != null) {
                if (line.equals("}")) {
                    break;
                }
                if (line.trim().equals("IP_AND_PORT_LIST") || line.trim().isEmpty()) {
                    continue;
                } else if (line.trim().equals("}")) {
                    new_item = false;
                } else if (line.trim().equals("IP_PORT")) {
                    ip = null;
                    port = -1;
                    new_item = true;

                }
                if (new_item) {
                    if (line.trim().startsWith("NAME ")) {
                        name = line.trim().substring(5);
                    }
                    else if (line.trim().startsWith("IP ")) {

                        ip = line.trim().substring(3);
                    }else if (line.trim().startsWith("PORT ")) {

                        port = Integer.parseInt(line.trim().substring(5));
                        Broker broker = new Broker(name,ip,port,masterIP,masterPort);

                        Broker.brokers.add(broker);
                    }

                }
            }
        }
        catch(IOException e){
            System.err.println("Sudden end!");
        }

        try{
            reader.close();
        }
        catch(IOException e){
            System.err.println("Error closing file!");
        }
    }
}
