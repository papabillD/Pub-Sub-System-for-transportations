/*KARYDAS ALEXANDROS:3140074
  PAPAVASILIS DIMITRIOS:3140151
  FOUSFOUKAS MICHAIL:3140217
*/
package Final;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMaster {

    public static void main(String[] args){

        BufferedReader in = null;

        in = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Set the IP of the Master:");
            String masterIP = in.readLine();

            System.out.println("Set the Port of the Master:");
            int masterPort = Integer.parseInt(in.readLine());

            Master master = new Master("Master 1", masterIP,masterPort);

            master.Initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }





    }
}
