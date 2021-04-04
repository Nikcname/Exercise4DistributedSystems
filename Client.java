import java.io.*;
import java.net.*;
import java.io.Serializable;

public class Client implements Serializable{
    int portNumber;

    // Handles method calls
    public Object remoteCall(Object instance, String method, Object... paramValues){
        Object result = null;
        try(
                Socket socket = new Socket("127.0.0.1", portNumber);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            System.out.println("Client stub: Connected to server stub!");
            Thread.sleep(5000);
            // This is the message that will be sent from client stub to server stub
            Message msg = new Message(method, paramValues, instance);
            System.out.format("Client stub: Calling method '%s' from class '%s' ...\n",
                    method, instance.getClass().getName());
            out.writeObject(msg); // write the message object into socket stream to server.
            result = in.readObject();
            System.out.println("Client stub: Received results!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Client(int port) {
        portNumber = port;
    }

    // A very simple main method to demonstrate some functionality.
    // Only calls the sort method of Sorter class with arguments 4 and 10.
    public static void main(String[] args){
        if(args.length != 1){ // Sanity check
            System.err.println("Usage: java Client <port number>");
            System.exit(1);
        }

        Client client = new Client(Integer.parseInt(args[0]));
        Sorter sorter = new Sorter();
        int[] results = (int[])client.remoteCall(sorter, "sort", 4, 10);
        System.out.println("Results: ");
        for(int result : results){
            System.out.format("%d ", result);
        }
        System.out.println(); // Only here to avoid the strange % sign after output
    }
}