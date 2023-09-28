import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Creates a client and sends data to server and displays the result.
 */
public class Client {

  private Socket client;
  private static final String SERVER_IP = "127.0.0.1";
  private static final int SERVER_PORT = 32000;
  private static final String MESSAGE = "This is my text to be changed by the SERVER";

  /**
   * Create a Socket and sends the message to server.
   */
  public void runClient() {
    try {
      // create client socket
      client = new Socket(SERVER_IP, SERVER_PORT);

      // Get output stream and write string input to it.
      OutputStream out = client.getOutputStream();
      DataOutputStream outStream = new DataOutputStream(out);
      outStream.writeUTF(MESSAGE);

      // Input stream to read output string from server
      InputStream in = client.getInputStream();
      DataInputStream inStream = new DataInputStream(in);
      printMessage(inStream.readUTF());

      // close input stream
      inStream.close();
      in.close();
      // close output stream
      outStream.close();
      out.close();
      // close client socket
      client.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Prints the given message.
   * @param output the message to be printed.
   */
  private void printMessage(String output) {
    System.out.println(output);
  }

  public static void main(String[] args) {
    Client client = new Client();
    client.runClient();
  }
}
