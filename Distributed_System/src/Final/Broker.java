package Final;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Broker extends Server implements Serializable {
    private static final long serialVersionUID= -3613923400183867897L;

    public static ArrayList<BusLine> allBuses = new ArrayList<>();
    public static ArrayList<Broker> brokers = new ArrayList<>();
    //public static ArrayList<BusPosition> positions = new ArrayList<>();
    public static ArrayList<MainSubscriber> conSubscribers = new ArrayList<>();

    private String masterIp;
    private int masterPort;

    Broker(String name, String ip, int port) {
        this.setName(name);
        this.setIp(ip);
        this.setPort(port);
    }

    public Broker(String name, String ip, int port, String masterIp, int masterPort) {
        this(name, ip, port);
        this.masterIp = masterIp;
        this.masterPort = masterPort;
    }


    public synchronized void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;


        try {
            out = new ObjectOutputStream(getSocketConn().getOutputStream());
            in = new ObjectInputStream(getSocketConn().getInputStream());

            Object object = in.readObject();

            if(object instanceof BusPosition){

                //System.out.println(((BusPosition) object).toString2()); Print to brokers the buspositions every time

                for(MainSubscriber curSub : conSubscribers){
                    Query curQuery=new Query();
                    curQuery.setLineCode(curSub.lineCode);

                    if(((BusPosition) object).getEnd()){
                        curQuery.setResult("I can't find the position");
                        this.Push(curQuery, curSub.getIp(),curSub.getPort());
                    }
                    else if(curSub.getLineCode().equals(((BusPosition) object).getLineCode())){

                        curQuery.setLineID(curSub.getLineId());
                        curQuery.setResult(object.toString());

                        this.Push(curQuery, curSub.getIp(),curSub.getPort());
                    }
                }
            }
            else if(object instanceof Query){

                MainSubscriber sub = new MainSubscriber("Sub",((Query) object).getIp(),((Query) object).getPort(),((Query) object).getLineCode());
                sub.setLineId(((Query) object).getLineID());
                conSubscribers.add(sub);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void Initialize() {

        sendBr(); //Send the brokers to the master
        try {

            String filePath = "busLinesNew.txt";

            //read csv data into buffer
            BufferedReader buf = new BufferedReader(new FileReader(filePath));
            String strLine = "";
            StringTokenizer strToken = null;

            //parse comma separated line by line
            String lineCode = null;
            String lineId = null;
            String description = null;
            while ((strLine = buf.readLine()) != null) {

                //break comma separated line using ","
                strToken = new StringTokenizer(strLine, ",");
                while (strToken.hasMoreTokens()) {

                    lineCode = strToken.nextToken();
                    lineId = strToken.nextToken();
                    description = strToken.nextToken();

                }

                BusLine bus = new BusLine(lineCode, lineId, description);

                allBuses.add(bus); //Add the bus to the list
            }


        } catch (Exception e) {
            System.err.println("Exception while reading csv file: " + e);
        }

        sendBu(); //Send the buses to the master

        this.OpenServer();
    }

    public void sendBr(){
        this.Push(brokers, masterIp,masterPort ); //Send the brokers to the master

    }

    public void sendBu (){

        this.Push(allBuses, masterIp,masterPort ); //Send the buses to the master
    }

    @Override
    public String toString() {
        return "Broker{" +
                "masterIp='" + masterIp + '\'' +
                ", masterPort=" + masterPort +
                "} " + super.toString();
    }
}
