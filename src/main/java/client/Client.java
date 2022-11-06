package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import server.Server;

public class Client {

  private Socket clientSocket;
  private DataOutputStream out;
  private DataInputStream in;
  private final String LOG_PATH = "C:\\Users\\Lenovo\\Desktop\\java-intelli\\KN-SOCKETS\\ClientLogger.log";
  Logger logger = Logger.getLogger("ClientLogger");
  FileHandler fh;

  public void startConnection(String ip, int port) throws IOException {
    fh = new FileHandler(LOG_PATH);
    logger.addHandler(fh);
    logger.setUseParentHandlers(false);
    SimpleFormatter formatter = new SimpleFormatter();
    fh.setFormatter(formatter);

    clientSocket = new Socket(ip, port);
    logger.log(Level.INFO,"Connecting to server...");
    out = new DataOutputStream(clientSocket.getOutputStream());
    in = new DataInputStream(clientSocket.getInputStream());
  }


  public String sendMessage(String msg) throws IOException {
    out.writeUTF(msg);
    out.flush();
    StringBuilder sb = new StringBuilder();
    String line = in.readUTF();
    sb.append(line);
    try {
      logger.info(msg);
      logger.log(Level.INFO, "Received message: {0}", line);
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  public static void main(String[] args) throws IOException {
    Client client = new Client();
    client.startConnection(Server.IP, Server.PORT);
    System.out.println(client.in.readUTF());

    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.println(client.sendMessage(sc.nextLine()));
    }
  }
}
