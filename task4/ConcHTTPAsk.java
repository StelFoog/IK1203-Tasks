import java.net.*;
import java.io.*;

public class ConcHTTPAsk {
    public static void main(String[] args) throws IOException {
        int HTTPPort;
        if(args.length < 1)
            HTTPPort = 8888;
        else
            HTTPPort = Integer.parseInt(args[0]);
        try {
            ServerSocket socket = new ServerSocket(HTTPPort);
            while(true)
                (new Thread(new MyRunnable(socket.accept()))).start();
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}
