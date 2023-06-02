package edu.messanger.tcp.server;

import edu.messanger.model.Message;
import edu.messanger.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static edu.messanger.utils.Constants.IO_EXCEPTION_MESSAGE;
import static edu.messanger.utils.Constants.PORT;

public class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                new Thread(new ClientHandler(serverSocket.accept())).start();
            }
        } catch (IOException e) {
            LOG.error(String.format(IO_EXCEPTION_MESSAGE, e.getMessage()));
        }

    }

    private record ClientHandler(Socket socket) implements Runnable {
        private static final String IO_EXCEPTION_CLIENT_HANDLER = "Problem in client handler request processing: %s";
        private static final String HELLO_TEXT =
                "Hello from server \n" +
                "Your connection data is %s, port is %d";



        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try (socket;
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out =
                         new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

                Message helloMessage = new Message(socket.getLocalAddress().getHostName(),
                        String.format(HELLO_TEXT, socket.getLocalAddress(), socket.getPort()));
                out.println(Utils.messageToJson(helloMessage));

                String inputString;
                while (Utils.isNotBlankOrEmpty(inputString = in.readLine())) {

                    if (Utils.isNotBlankOrEmpty(inputString)) {
                        Message message = Utils.jsonToMessage(inputString);
                        System.out.printf(String.format("Hello, %s, your message was %s\n ",
                                message.getName(), message.getText()));
                        out.println(Utils.messageToJson(message));
                    }
                }
            } catch (IOException e) {
                LOG.error(String.format(IO_EXCEPTION_CLIENT_HANDLER, e.getMessage()));
            }
        }
    }


    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }
}
