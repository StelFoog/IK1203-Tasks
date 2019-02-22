import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {
        int portNum = Integer.parseInt(args[0]);
        ServerSocket socket = new ServerSocket(portNum);
        String fromClient;
        StringBuilder toClient = new StringBuilder("HTTP/1.1 200 OK\r\n\r\n");

        while(true) {
            toClient.setLength(19); // resets the stringbuilder to just the header

            Socket connection = socket.accept();
            BufferedReader inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());

            while((fromClient = inStream.readLine()) != null && fromClient.length() != 0)
                toClient.append(fromClient + "\r\n");

            outStream.writeBytes(toClient.toString());
            connection.close();
        }
    }
}
