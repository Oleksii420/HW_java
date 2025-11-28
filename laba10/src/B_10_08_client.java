import java.io.*;
import java.net.*;
import java.util.Scanner;

public class B_10_08_client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12344;

        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server or 'exit':");

            while (true) {
                System.out.print("> ");
                String userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    out.println("exit");
                    break;
                }

                out.println(userInput);

                String response = in.readLine();
                if (response == null) {
                    break;
                }
                System.out.println("Result: " + response);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}