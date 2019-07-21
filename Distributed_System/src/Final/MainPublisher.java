/*KARYDAS ALEXANDROS:3140074
  PAPAVASILIS DIMITRIOS:3140151
  FOUSFOUKAS MICHAIL:3140217
*/
package Final;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainPublisher {

    public static final int defaultLat=1, defaultLong=1;
    public static final String defaultLineCode="00", defaultRouteCode="00",defauldVehicleID="00",defaultDate="00";

    public static void main(String[] args){
        readBusPosition();

    }


    public static void readBusPosition(){
        try {

            BufferedReader in = null;

            in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Set the IP of the Master:");
            String masterIP = in.readLine();

            System.out.println("Set the Port of the Master:");
            int masterPort = Integer.parseInt(in.readLine());

            String ipForThisPublisher = Available_IP_and_PORTS.GetAddressForThisPC(); //Gets the ip of the currents pc.
            int portForThisPublisher = Available_IP_and_PORTS.GetAvailablePortForThisPC(); //Gets an available port.


            Publisher pub = new Publisher(ipForThisPublisher,portForThisPublisher);

            //csv file containing data
            String filePath = "busPositionsNew.txt";

            //read csv data into buffer

            try (BufferedReader buf = new BufferedReader(new FileReader(filePath))) {
                String strLine;
                StringTokenizer strToken = null;


                //parse comma separated line by line
                double latitude = 0;
                double longitude = 0;
                String lineCode = null;
                String routeCode = null;
                String vehicleId = null;
                String month, day, year, hour, minutes, seconds, milSecond;
                String date=null;
                while ((strLine = buf.readLine()) != null) {

                    //break comma separated line using " ,:"
                    strToken = new StringTokenizer(strLine, ", :");
                    while (strToken.hasMoreTokens()) {

                        lineCode = strToken.nextToken();
                        routeCode = strToken.nextToken();
                        vehicleId = strToken.nextToken();
                        latitude = Double.parseDouble(strToken.nextToken());
                        longitude = Double.parseDouble(strToken.nextToken());
                        month = strToken.nextToken();


                        day = strToken.nextToken();
                        year = strToken.nextToken();
                        hour = strToken.nextToken();
                        minutes = strToken.nextToken();
                        seconds = strToken.nextToken();
                        milSecond = strToken.nextToken();


                        date = year + "-"+month + "-"+day+" "+hour+":"+minutes+":"+seconds+"."+milSecond;


                    }

                    BusPosition bus = new BusPosition(latitude,longitude,lineCode,routeCode, vehicleId,date,false);


                    if(bus.getLatitude()<=0 || bus.getLongitude()<=0){
                        bus.setFailure(true);
                    }

                    pub.Push(bus,masterIP,masterPort);

                }

                //create a pseudo busposition to terminate the servers for subscribers
                BusPosition bus = new BusPosition(defaultLat,defaultLong,defaultLineCode,defaultRouteCode, defauldVehicleID,defaultDate,true);
                pub.Push(bus,masterIP,masterPort);


            }

        } catch (Exception e) {
            System.err.println("Exception while reading csv file: " + e);
        }
    }
}
