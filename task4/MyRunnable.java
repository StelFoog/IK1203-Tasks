import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class MyRunnable implements Runnable {
    // HTTP headers
    String HTTP200 = "HTTP/1.1 200 OK\r\n\r\n";
    String HTTP400 = "HTTP/1.1 400 Bad Request\r\n";
    String HTTP404 = "HTTP/1.1 404 Not Found\r\n";

    Socket connection;

    String hostname;
    String port;
    String string;

    String request;

    public MyRunnable(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        try {
            BufferedReader inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());

            StringBuilder response =  new StringBuilder();

            request = inStream.readLine();

            if(request != null) {
                hostname = null;
                port = null;
                string = null;

                String[] parts = request.split("[?&= ]");
                for(int i = 0; i < parts.length; i++) {
                    if(parts[i].equals("hostname"))
                        hostname = parts[++i];
                    else if(parts[i].equals("port"))
                        port = parts[++i];
                    else if(parts[i].equals("string"))
                        string = parts[++i];
                }

                if(parts[1].equals("/ask") && hostname != null && port != null && port.matches("[0-9]+")) {
                    try {
                        String tempResponse = TCPClient.askServer(hostname, Integer.parseInt(port), string);
                        response.append(HTTP200);
                        response.append(tempResponse);
                    } catch(IOException e) {
                        response.append(HTTP404);
                    }
                } else {
                    response.append(HTTP400);
                }
                outStream.writeBytes(response.toString());
            }
            connection.close();
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}
