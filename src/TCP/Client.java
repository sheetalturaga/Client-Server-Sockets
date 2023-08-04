package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Client class to set up a connection and communicate with the server
 *
 * Using InputStreamReader & OutputStreamWriter to set up the respective streams
 * Using buffered reader and buffered writer to read and write line by line for better efficiency
 */

public class Client {
    private Socket clientSocket = null;
    private InputStreamReader inputStream = null;
    private OutputStreamWriter outputStream = null;
    private BufferedReader inputReader;
    private BufferedReader outputWriter;

    public Client(String ipAddress, int portNumber) throws IOException {
      // Begin creating a socket to establish a connection with server
      clientSocket = new Socket(ipAddress, portNumber);
      System.out.println("Client Connection Successful on port" + portNumber);

      // Setting up the streams for reading and writing to streams
      InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
      OutputStreamWriter outputStream = new OutputStreamWriter(clientSocket.getOutputStream());
      BufferedReader inputReader = new BufferedReader(inputStream);
      BufferedWriter outputWriter = new BufferedWriter(outputStream);

      // Read client's input from the terminal
      Scanner clientInput = new Scanner(System.in);
      try {
        String messageSent = clientInput.nextLine();
        outputWriter.write(messageSent);
        outputWriter.flush();

        // Printing out the response received from the server
        String messageReceived = inputReader.readLine();
        System.out.println("Server Response: " + messageReceived);

      } catch (UnknownHostException i) {
        i.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      inputReader.close();
      outputWriter.close();
      clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
      Client client = new Client("127.0.0.1", 8080);
    }
}
