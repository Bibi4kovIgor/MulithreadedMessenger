package edu.messanger.tcp.client;

import edu.messanger.model.Message;
import edu.messanger.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import static edu.messanger.utils.Constants.*;
import static edu.messanger.utils.Signals.EXIT;

public class Client {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        try (Socket socket = new Socket(host, port);
             BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter outputStream =
                     new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

             Scanner scanner = new Scanner(System.in)){

            String scannedString;
            String inputStreamString;
            System.out.println(Utils.jsonToMessage(inputStream.readLine()));
            while (!Objects.equals(scannedString = scanner.nextLine(), EXIT.getValue())){

                if (Objects.nonNull(scannedString))
                {
                    Message message = new Message(socket.getLocalAddress().getHostName(), scannedString);
                    outputStream.println(Utils.messageToJson(message));
                }

                if (Utils.isNotBlankOrEmpty(inputStreamString = inputStream.readLine())){
                    Message message = Utils.jsonToMessage(inputStreamString);
                    System.out.printf("Message from server:'%s'\n", message.getText());
                }
            }
        } catch (IOException e) {
            LOG.error(String.format(IO_EXCEPTION_MESSAGE, e.getMessage()));
        }
    }

    public static void main(String[] args) {
        Client client = new Client(LOCAL_HOST_ADDRESS, PORT);
        client.run();
    }
}
