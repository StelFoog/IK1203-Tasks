package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

    final static int max = 1024;

    public static String askServer(String hostname, int port, String scentence) throws  IOException {
        if(scentence == null) return askServer(hostname, port);
        Socket clientSocket = new Socket(hostname, port);
        clientSocket.setSoTimeout(1000);
        DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        toServer.writeBytes(scentence + '\n');
        String oneResponse = null;
        StringBuilder serverResponse = new StringBuilder();
        int counter = 0;
        try {
            while((oneResponse = fromServer.readLine()) != null && counter++ < max) {
                serverResponse.append(oneResponse + '\n');
            }
            clientSocket.close();

            return serverResponse.toString();
        } catch(IOException e) {
            clientSocket.close();

            return serverResponse.toString();
        }
    }

    public static String askServer(String hostname, int port) throws  IOException {
        Socket clientSocket = new Socket(hostname, port);
        clientSocket.setSoTimeout(1000);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String oneResponse = null;
        StringBuilder serverResponse = new StringBuilder();
        int counter = 0;
        try {
            while((oneResponse = fromServer.readLine()) != null && counter++ <= max) {
                serverResponse.append(oneResponse);
            }
            clientSocket.close();

            return serverResponse.toString();
        } catch(IOException e) {
            clientSocket.close();

            return serverResponse.toString();
        }
    }
}
