package calculator;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverIP = "localhost";
        int serverPort = 1234;

        // server_info.dat 읽기
        try {
            BufferedReader config = new BufferedReader(new FileReader("server_info.dat"));
            String[] parts = config.readLine().split(" ");
            serverIP = parts[0];
            serverPort = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            System.out.println("Config file not found. Using default server info.");
        }

        try (
                Socket socket = new Socket(serverIP, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in);
        ) {
            System.out.println("Connected to server " + serverIP + ":" + serverPort);

            while (true) {
                System.out.print("Enter command (or QUIT to exit): ");
                String command = sc.nextLine();
                if (command.equalsIgnoreCase("QUIT")) break;

                out.println(command); // 서버로 전송
                String response = in.readLine(); // 서버 응답
                System.out.println("Server Response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
