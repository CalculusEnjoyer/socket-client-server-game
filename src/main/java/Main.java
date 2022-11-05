import client.Client;
import java.io.IOException;
import java.util.Scanner;
import server.Server;

public class Main {

  public static void main(String[] args) throws IOException {
    Client client = new Client();
    client.startConnection(Server.IP, Server.PORT);

    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.println(client.sendMessage(sc.nextLine()));
    }
  }
}