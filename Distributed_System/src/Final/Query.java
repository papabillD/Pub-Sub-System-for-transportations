package Final;

import java.io.Serializable;
import java.util.ArrayList;

public class Query implements Serializable {


    private String lineID,lineCode;
    private String ip;
    private int port;
    private String result;
    private ArrayList<BusPosition> listReturn;

    public Query() {

    }

    public Query(String lineID, String ip, int port) {
        this.lineID = lineID;
        this.ip = ip;
        this.port = port;
    }

    public String getLineID() {
        return lineID;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }


    public ArrayList<BusPosition> getListReturn() {
        return listReturn;
    }

    public void setListReturn(ArrayList<BusPosition> listReturn) {
        this.listReturn = listReturn;
    }

    public void addToList(BusPosition input){

        this.listReturn.add(input);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Query{" +
                "lineID='" + lineID + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
