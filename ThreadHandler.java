import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Stream;

class ThreadedHandler implements Runnable {

    private final Socket incoming;

    ThreadedHandler(Socket socket) throws IOException {
        this.incoming = socket;
        new Thread(this).start();
    }

    Object processMessage(Message msg) throws Exception {
        Class<?>[] paramTypes = Stream.of(msg.paramValues).map(Object::getClass).toArray(Class<?>[]::new);
        Method toExecute = msg.classInstance.getClass().getDeclaredMethod(
                msg.methodName,
                paramTypes);
        return toExecute.invoke(
                msg.classInstance,
                msg.paramValues[0],
                msg.paramValues[1]);
    }

    @Override
    public void run() {

        Thread messageHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
                    System.out.println("Server stub: Connected to client stub!");
                    Message msg;
                    while ((msg = (Message)in.readObject()) != null) {
                        System.out.format(
                                "Server stub: Received method '%s' from class '%s' ...\n",
                                msg.methodName, msg.classInstance.getClass().getName());
                        Object result = processMessage(msg);
                        out.writeObject(result);
                        System.out.println("Server stub: Message processed, returning results...");
                    }
                } catch (EOFException e) {
                    System.out.println("Server stub: Processed all messages, closing connection.\n");
                } catch (Exception e) {
                    System.out.println("Server stub: Exception caught when trying to listen on port "
                            + incoming + " or listening for a connection");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }

        });
        messageHandler.start();

    }
}