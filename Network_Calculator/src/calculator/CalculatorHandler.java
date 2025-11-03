package calculator;
import java.io.*;
import java.net.*;

public class CalculatorHandler implements Runnable {
    private Socket socket;

    public CalculatorHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String response = processCommand(inputLine);
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }

    private String processCommand(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            return "RESPONSE_TYPE=ERROR; ERROR_CODE=ARG_COUNT";
        }

        String cmd = tokens[0];
        double a, b;
        try {
            a = Double.parseDouble(tokens[1]);
            b = Double.parseDouble(tokens[2]);
        } catch (NumberFormatException e) {
            return "RESPONSE_TYPE=ERROR; ERROR_CODE=INVALID_ARG";
        }

        switch (cmd.toUpperCase()) {
            case "ADD": return "RESPONSE_TYPE=ANSWER; VALUE=" + (a + b);
            case "SUB": return "RESPONSE_TYPE=ANSWER; VALUE=" + (a - b);
            case "MUL": return "RESPONSE_TYPE=ANSWER; VALUE=" + (a * b);
            case "DIV":
                if (b == 0) return "RESPONSE_TYPE=ERROR; ERROR_CODE=DIV_ZERO";
                return "RESPONSE_TYPE=ANSWER; VALUE=" + (a / b);
            default: return "RESPONSE_TYPE=ERROR; ERROR_CODE=INVALID_CMD";
        }
    }
}
