package calculator;
import java.io.*;

public class Config {
    private String serverIP;
    private int serverPort;

    // 기본값
    private static final String DEFAULT_IP = "localhost";
    private static final int DEFAULT_PORT = 1234;

    public Config() {
        loadConfig();
    }

    private void loadConfig() {
        File file = new File("server_info.dat");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                if (line != null) {
                    String[] parts = line.split(" ");
                    serverIP = parts[0];
                    serverPort = Integer.parseInt(parts[1]);
                } else {
                    setDefault();
                }
            } catch (Exception e) {
                System.out.println("Error reading config file. Using default values.");
                setDefault();
            }
        } else {
            System.out.println("Config file not found. Using default values.");
            setDefault();
        }
    }

    private void setDefault() {
        serverIP = DEFAULT_IP;
        serverPort = DEFAULT_PORT;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }
}
