package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import plate.MonteCarloPlate;

public class Server {
  public static final String IP = "127.0.0.1";
  public static final int PORT = 1025 + 27;
  public static final String CLOSING_COMMAND = "stop-server";
  private CommandParser commandParser;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public Server(CommandParser commandParser){
    this.commandParser = commandParser;
  }

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    clientSocket = serverSocket.accept();
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      if (CLOSING_COMMAND.equals(inputLine)) {

      }
      out.println(inputLine.toUpperCase());
    }
  }

  public static void main(String[] args) throws IOException {
    MonteCarloPlate plate = new MonteCarloPlate(2,2);
    plate.setTable(new);
    Server server = new Server();
    server.start(Server.PORT);
  }
}
