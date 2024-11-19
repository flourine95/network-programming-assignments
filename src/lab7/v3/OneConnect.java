package lab7.v3;

import java.net.Socket;

public class OneConnect implements Runnable {
    private Socket socket;

    public OneConnect(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("OneConnect.run");
    }

    /*run
    * process
    * copy
    * close socket
    * respose
    * flush
    *
    *
    * */
}
