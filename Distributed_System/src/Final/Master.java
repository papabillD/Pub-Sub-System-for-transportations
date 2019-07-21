package Final;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Master extends Server {

    private ArrayList<BusLine> buses = new ArrayList<>();
    private ArrayList<Broker> brokers = new ArrayList<>();
    private ArrayList<MainSubscriber> subscribers = new ArrayList<>();

    Master(String name, String ip, int port) {
        this.setName(name);
        this.setIp(ip);
        this.setPort(port);
    }

    public synchronized void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {

            out = new ObjectOutputStream(getSocketConn().getOutputStream());
            in = new ObjectInputStream(getSocketConn().getInputStream());

            Object object = in.readObject();

            if(object instanceof ArrayList<?>) {

                if(((ArrayList<?>)object).get(0) instanceof Broker) {

                    brokers= (ArrayList<Broker>) object;

                }
                else if(((ArrayList<?>)object).get(0) instanceof BusLine) {

                    buses= (ArrayList<BusLine>) object;

                }
            }
            else if(object instanceof BusPosition){

                BusPosition bus = (BusPosition) object;
                String lineCode = bus.getLineCode();
                String busLineID=null;

                for(BusLine busline : buses){
                    if(busline.getLineCode().equals(lineCode)){
                        busLineID=busline.getLineId();

                    }
                }
                if(busLineID.equals(null)){
                    System.out.println("Error");
                }

                if(bus.getEnd()){
                    for(Broker cBroker : brokers){

                        this.Push(bus,cBroker.getIp(),cBroker.getPort());
                    }
                }

                Broker suitableBroker=findSuitableBroker(busLineID);

                this.Push(bus,suitableBroker.getIp(),suitableBroker.getPort());
            }
            else if(object instanceof Query){

                Query query = (Query) object;

                subscribers.add(new MainSubscriber(query.getLineID(),query.getIp(),query.getPort()));
                String busLineID = query.getLineID();

                boolean found =false;
                for(BusLine bus: buses){
                    if(query.getLineID().equals(bus.getLineId())){
                        query.setLineCode(bus.getLineCode());
                        found=true;
                    }
                }

                if(!found){

                    String notify = "Unknown bus!";

                    query.setResult(notify);

                    this.Push(query,query.getIp(),query.getPort());

                }
                else{
                    Broker suitableBroker=findSuitableBroker(busLineID);
                    this.Push(query,suitableBroker.getIp(),suitableBroker.getPort());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void Initialize() {

        this.OpenServer();
    }

    private BigInteger getSHA(String input){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        md.update(input.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }

        BigInteger bi = new BigInteger(hexString.toString(), 16);
        return bi;
    }

    public Broker findMinBroker(){
        BigInteger min=getSHA(brokers.get(0).getIp() + brokers.get(0).getPort());
        Broker minBroker=brokers.get(0);
        for(Broker broker : brokers){

            String together = broker.getIp() + broker.getPort();
            BigInteger togetherInt = getSHA(together);
            //System.out.println(broker.getName()+ "hash: " + togetherInt );
            if (togetherInt.compareTo(min) < 0) {
                min=togetherInt;
                minBroker=broker;
            }

        }

        return minBroker;
    }

    public Broker findSuitableBroker(String busLineID){
        BigInteger hashInt = getSHA(busLineID);

        Broker brokerTemp=null;

        boolean flag=false;

        for(Broker broker : brokers){

            String together = broker.getIp() + broker.getPort();
            BigInteger togetherInt = getSHA(together);

            if (togetherInt.compareTo(hashInt) > 0) {
                brokerTemp=broker;
                flag =true;
                break;
            }
        }

        if(!flag){
            brokerTemp=findMinBroker() ;
        }
        return brokerTemp;
    }
}
