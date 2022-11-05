package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import plate.MonteCarloPlate;

public class Server {

  public static final String IP = "127.0.0.1";
  public static final int PORT = 1025 + 27;
  public static final String CLOSING_COMMAND = "stop-server";
  private final CommandParser commandParser;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private DataOutputStream out;
  private DataInputStream in;
  private final String LOG_PATH = "C:\\Users\\Lenovo\\Desktop\\java-intelli\\KN-SOCKETS\\ServerLogger.log";
  Logger logger = Logger.getLogger("ServerLogger");
  FileHandler fh;

  public Server(CommandParser commandParser) {
    this.commandParser = commandParser;
  }

  public void start(int port) throws IOException {
    fh = new FileHandler(LOG_PATH);
    logger.addHandler(fh);
    logger.setUseParentHandlers(false);
    SimpleFormatter formatter = new SimpleFormatter();
    fh.setFormatter(formatter);

    logger.log(Level.INFO, "Starting server...");
    logger.log(Level.INFO, "Showing initial table: \n{0}", commandParser.getPlate().toString());
    System.out.println("Initial figure:\n" + commandParser.getPlate().toString());
    serverSocket = new ServerSocket(port);
    clientSocket = serverSocket.accept();
    logger.log(Level.INFO, "Client is connected.");
    out = new DataOutputStream(clientSocket.getOutputStream());
    in = new DataInputStream(clientSocket.getInputStream());

    String inputLine;
    while ((inputLine = in.readUTF()) != null) {
      if (CLOSING_COMMAND.equals(inputLine)) {
        out.writeUTF("Closing sockets.");
        out.flush();
        logger.log(Level.INFO, "Closing sockets.");
        clientSocket.close();
        serverSocket.close();
      }
      logger.log(Level.INFO, "Receiving message: {0}", inputLine);
      String response = this.commandParser.parseCommand(inputLine);
      out.writeUTF(response);
      out.flush();
      logger.log(Level.INFO, "Sending message: {0}", response);
    }
  }

  public static void main(String[] args) throws IOException {
    MonteCarloPlate plate = new MonteCarloPlate(6, 6);
    List row1 = new ArrayList<Boolean>();
    row1.add(true);
    row1.add(false);
    row1.add(true);
    row1.add(false);
    row1.add(false);
    row1.add(false);

    List row2 = new ArrayList<Boolean>();
    row2.add(false);
    row2.add(true);
    row2.add(true);
    row2.add(true);
    row2.add(false);
    row2.add(true);

    List row3 = new ArrayList<Boolean>();
    row3.add(true);
    row3.add(false);
    row3.add(false);
    row3.add(true);
    row3.add(false);
    row3.add(true);

    List row4 = new ArrayList<Boolean>();
    row4.add(true);
    row4.add(false);
    row4.add(false);
    row4.add(true);
    row4.add(false);
    row4.add(true);

    List row5 = new ArrayList<Boolean>();
    row5.add(false);
    row5.add(false);
    row5.add(false);
    row5.add(true);
    row5.add(false);
    row5.add(true);

    List row6 = new ArrayList<Boolean>();
    row6.add(false);
    row6.add(false);
    row6.add(false);
    row6.add(false);
    row6.add(false);
    row6.add(true);

    List table = new ArrayList<List>();
    table.add(row1);
    table.add(row2);
    table.add(row3);
    table.add(row4);
    table.add(row5);
    table.add(row6);

    plate.setTable(table);
    CommandParser commandParser = new CommandParser(plate);
    Server server = new Server(commandParser);
    server.start(Server.PORT);
  }
}
