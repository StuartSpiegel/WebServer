import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public final class httpRequest implements Runnable {

	@Override
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	final static String CRLF = "\r\n";
	Socket socket;

	// Constructor
	public httpRequest(Socket socket) throws Exception {
		this.socket = socket;
	}

	private void processRequest() throws Exception {
		// Get a reference to the socket's input and output streams.
		InputStream IS = socket.getInputStream();

		// DataOutputStream os = ?;
		OutputStream OS = socket.getOutputStream();

		// Set up input stream filters.
		BufferedReader br = new BufferedReader(new InputStreamReader(IS));
		while (true) {
			String msg = br.readLine();

			// Print message received from client
			System.out.println("Received from client: ");
			System.out.println(msg);

			// convert message to upper case
			String outputMsg = msg.toUpperCase();

			// Send modified msg back to client (write to socket)
			OS.writeBytes(outputMsg);
			OS.writeBytes("\r\n");
			System.out.println("Sent to client: ");

		}

		// Get and display the header lines.
		String headerLine = null;
		while ((headerLine = br.readLine()).length() != 0) {
			System.out.println(headerLine);
		}

		// (headerLine = br.readLine()).length()

		// Close streams and socket.
		OS.close();
		br.close();
		socket.close();

	}

}
