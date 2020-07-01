package client_server_tcp_multi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client() throws Exception {

        Socket socket = new Socket("localhost",2020);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        Scanner userInput = new Scanner(System.in);

        String command = "START";

        while ((command != null) && (!command.equals("EXIT"))) {
            System.out.print("Command: ");
            command = userInput.nextLine();
            writer.println(command);

            String reply = reader.readLine();
            System.out.println(reply);
        }

        socket.close();
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
