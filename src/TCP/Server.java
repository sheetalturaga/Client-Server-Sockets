package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Server class to set up a connection and communicate with the client
 *
 * Using InputStreamReader & OutputStreamWriter to set up the respective streams
 * Using buffered reader and buffered writer to read and write line by line for better efficiency
 */

public class Server {
  private Socket serverSocket = null;
  private InputStreamReader inputStream = null;
  private OutputStreamWriter outputStream = null;
  private BufferedReader inputReader = null;
  private BufferedReader outputWriter = null;
  private ServerSocket clientServerSocket;
  private boolean exit = false;

  public Server(int portNumber) throws IOException {
      try {
        clientServerSocket = new ServerSocket(portNumber); // Port number must be the same as client
      } catch (IOException e) {
        System.out.println("Creating Server Socket was unsuccessful");
        e.printStackTrace();
      }

    while (!exit) {
        try {
          System.out.println("Server Connection Successful on port" + portNumber);

          serverSocket = clientServerSocket.accept();
          System.out.println("Client-Server Connection Successful");

          // Setting up the streams for reading and writing to streams
          InputStreamReader inputStream = new InputStreamReader(serverSocket.getInputStream());
          OutputStreamWriter outputStream = new OutputStreamWriter(serverSocket.getOutputStream());
          BufferedReader inputReader = new BufferedReader(inputStream);
          BufferedWriter outputWriter = new BufferedWriter(outputStream);

          Scanner scanner = new Scanner(inputStream);
          System.out.println("Client >> " + scanner.nextLine());

           while (!exit) {
             StringBuilder stringBuilder = new StringBuilder();
             String clientMessage = inputReader.readLine();

             // Invert the case and reverse the string
             for (char c : clientMessage.toCharArray()) {
               stringBuilder.append(Character.isLowerCase(c) ? Character.isUpperCase(c): Character.isLowerCase(c));
             }

             String reversedMessage = stringBuilder.toString();
             outputWriter.write("Server >> " + reversedMessage);
             outputWriter.flush();
             exit = false;
           }

          //Closing all the streams and sockets
          clientServerSocket.close();
          inputStream.close();
          outputStream.close();
          inputReader.close();
          outputWriter.close();

        } catch (IOException e) {
          e.printStackTrace();
        }
        exit = false;
      }
  }

  public static void main(String[] args) throws IOException {
    Server server = new Server(8080);
  }
}