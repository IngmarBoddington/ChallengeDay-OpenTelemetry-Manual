package client_server_tcp_multi;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerThread implements Runnable {

    private Socket socket;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){

        System.out.println("Socket Opened.");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            String command = "START";
            Service service = Service.getInstance();

            while ((command != null) && (!command.equals("EXIT"))) {
                command = reader.readLine();
                System.out.println("Serving Command: " + command);

                String reply;

                if (command.matches("^HELLO$")) {
                    reply = service.hello();
                    writer.println(reply);
                    continue;
                }
                if (command.matches("^TIME$")) {
                    reply = service.time();
                    writer.println(reply);
                    continue;
                }
                if (command.matches("^DIR [a-zA-Z0-9_\\/]*$")) {
                    Pattern pattern = Pattern.compile("^DIR ([a-zA-Z0-9_\\/]*)$");
                    Matcher matcher = pattern.matcher(command);
                    matcher.find();
                    reply = service.listing(matcher.group(1));
                    writer.println(reply);
                    continue;
                }
                if (command.matches("^EXIT$")) {
                    reply = "Bye";
                    writer.println(reply);
                    continue;
                }
                reply = "Unknown Command";
                writer.println(reply);
            }

            socket.close();
            System.out.println("Socket Closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
