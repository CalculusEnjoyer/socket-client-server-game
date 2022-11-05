package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Client {

  private Socket clientSocket;
  private DataOutputStream out;
  private DataInputStream in;
  private final String LOG_PATH = "C:\\Users\\Lenovo\\Desktop\\java-intelli\\KN-SOCKETS\\ClientLogger.log";
  Logger logger = Logger.getLogger("ClientLogger");
  FileHandler fh;

  public void startConnection(String ip, int port) throws IOException {
    clientSocket = new Socket(ip, port);
    out = new DataOutputStream(clientSocket.getOutputStream());
    in = new DataInputStream(clientSocket.getInputStream());
    fh = new FileHandler(LOG_PATH);
    logger.addHandler(fh);
    logger.setUseParentHandlers(false);
    SimpleFormatter formatter = new SimpleFormatter();
    fh.setFormatter(formatter);
  }


  public String sendMessage(String msg) throws IOException {
    out.writeUTF(msg);
    out.flush();
    StringBuilder sb = new StringBuilder();
    String line = in.readUTF();
    sb.append(line);
    try {
      logger.info(msg);
      logger.info(line);
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}
