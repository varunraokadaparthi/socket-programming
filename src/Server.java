import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Creates a server and process the requests sent by client.
 */
public class Server {

  private ServerSocket server;
  private static final int SERVER_PORT = 32000;

  /**
   * Creates a Server Socket
   */
  public void runServer() {
    try {
      // Open Server socket. The port will be used for listening.
      server = new ServerSocket(SERVER_PORT);
      this.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Accepts a client request and processes the request.
   * @throws IOException
   */
  public void run() throws IOException {
    // Wait for client. The port will be different from listening port.
    Socket s1 = server.accept();

    // Input Stream to read data from client
    InputStream in = s1.getInputStream();
    DataInputStream inStream = new DataInputStream(in);
    String input = inStream.readUTF();

    // Reverse the input string and convert the case of each character.
    String output = reverseAndConvertCase(input);

    // Write the string output on output stream for client to read.
    OutputStream out = s1.getOutputStream();
    DataOutputStream outStream = new DataOutputStream(out);
    outStream.writeUTF(output);

    // close input stream
    inStream.close();
    in.close();
    // close output stream
    outStream.close();
    out.close();

    // close client socket.
    s1.close();
    // close listening socket.
    server.close();
  }

  /**
   * Reverses given string and converts case of each character in given string.
   * Uppercase character is converted to lowercase and vice versa.
   * @param input the string to be processed
   * @return reverse the given string and convert case of each character
   */
  private static String reverseAndConvertCase(String input) {
    StringBuilder builder = new StringBuilder();
    for (int i = input.length() - 1; i >= 0; i--) {
      if (Character.isLowerCase(input.charAt(i))) {
        builder.append(Character.toUpperCase(input.charAt(i)));
      } else {
        builder.append(Character.toLowerCase(input.charAt(i)));
      }
    }
    return builder.toString();
  }

  public static void main(String[] args) {
    Server server = new Server();
    server.runServer();
  }

}
