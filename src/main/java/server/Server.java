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
  private final String GREETING_MESSAGE = CommandParser.HELP;
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

    out.writeUTF(GREETING_MESSAGE);
    out.flush();
    logger.log(Level.INFO, "Sending greeting message...");
    String inputLine;
    while ((inputLine = in.readUTF()) != null) {
      logger.log(Level.INFO, "Received message: {0}", inputLine);
      if (CLOSING_COMMAND.equals(inputLine)) {
        out.writeUTF("Closing sockets.");
        out.flush();
        logger.log(Level.INFO, "Closing sockets.");
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        break;
      }
      String response = this.commandParser.parseCommand(inputLine);
      out.writeUTF(response);
      out.flush();
      logger.log(Level.INFO, "Sending message: {0}", response);
    }
  }

  public static void main(String[] args) throws IOException {
    MonteCarloPlate plate = new MonteCarloPlate(6, 6);
    List row1 = new ArrayList<Boolean>();

    row1.add(0);
    row1.add(0);
    row1.add(0);
    row1.add(0);
    row1.add(0);
    row1.add(0);

    List row2 = new ArrayList<Boolean>();
    row2.add(0);
    row2.add(1);
    row2.add(1);
    row2.add(0);
    row2.add(0);
    row2.add(0);

    List row3 = new ArrayList<Boolean>();
    row3.add(0);
    row3.add(1);
    row3.add(1);
    row3.add(1);
    row3.add(0);
    row3.add(0);

    List row4 = new ArrayList<Boolean>();
    row4.add(0);
    row4.add(1);
    row4.add(1);
    row4.add(1);
    row4.add(1);
    row4.add(0);

    List row5 = new ArrayList<Boolean>();
    row5.add(0);
    row5.add(0);
    row5.add(0);
    row5.add(0);
    row5.add(0);
    row5.add(0);

    List row6 = new ArrayList<Boolean>();
    row6.add(0);
    row6.add(0);
    row6.add(0);
    row6.add(0);
    row6.add(0);
    row6.add(0);

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
