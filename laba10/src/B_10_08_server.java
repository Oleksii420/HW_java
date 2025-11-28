import java.io.*;
import java.net.*;

public class B_10_08_server {
    public static void main(String[] args) {
        int port = 12344;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        if ("exit".equalsIgnoreCase(inputLine.trim())) {
                            break;
                        }

                        String response = calculate(inputLine);
                        out.println(response);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String calculate(String expression) {
        try {
            String[] parts = expression.trim().split("\\s+");

            if (parts.length != 3) {
                return "Error: Invalid format.";
            }

            double num1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double num2 = Double.parseDouble(parts[2]);
            double result = 0;

            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/":
                    if (num2 == 0) return "Error: Division by zero.";
                    result = num1 / num2;
                    break;
                default:
                    return "Error: Unknown operator.";
            }

            return String.valueOf(result);

        } catch (NumberFormatException e) {
            return "Error: Invalid numbers.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}