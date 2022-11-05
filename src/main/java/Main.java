import client.Client;
import server.Server;
import java.io.IOException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException {
    Client client = new Client();
    client.startConnection(Server.IP, Server.PORT);

    System.out.println(client.sendMessage("check"));

    Scanner sc = new Scanner(System.in);
    while(true){
      System.out.println(client.sendMessage(sc.nextLine()));
    }
  }
}