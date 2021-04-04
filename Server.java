import java.lang.reflect.Method;
import java.util.stream.*;
import java.lang.*;
import java.net.*;
import java.io.*;

public class Server implements Runnable {
    int portNumber; // port number to use. Hostname/IP is assumed to be the same (localhost).
    volatile boolean exit = false; // flag to exit while loop

    public Server(int port){
        portNumber = port;
    }

    // Can be called by Thread or run as a blocking call
    public void run() {

        try(ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (!exit){

                System.out.println("Server stub: Waiting for client...");
                Socket clientSocket = serverSocket.accept();

                //Create new thread for each client
                new ThreadedHandler(clientSocket);

                System.out.println("Spawning new thread");

            }
        } catch (EOFException e) {
            System.out.println("Server stub: Processed all messages, closing connection.\n");
        } catch (Exception e) {
            System.out.println("Server stub: Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    // Method to stop while loop
    public void stop(){
        exit = true;
        System.out.println("Server stub: Closing server.");
    }

    // Extremely basic main method
    public static void main(String[] args){

        int portIDStudent = 1280; //C11280
        Server server = new Server(portIDStudent); //port is static and set to four digits of student ID
        server.run();
    }
}