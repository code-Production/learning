package ru.geekbrains.java_core_2.lesson_6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MultiThreadServerTCP {

    private static final int PORT = 6629;
    private static int connectionNum = 0;

    private final Object monitor = new Object();
    private final Map<Integer, Connection> clientConnections = new HashMap<>();
    private Thread connectionStarterThread;
    private Thread consoleInputThread;
    private ServerSocket serverSocket;
//    private boolean shutdownFlag = false;

    public static void main(String[] args) {
        new MultiThreadServerTCP().start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started.");
            connectionStarter(serverSocket);
            consoleInput();
            try {
                consoleInputThread.join();
                connectionStarterThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shutdownProcedureNormal();
            System.out.println("Server stopped.");
        }
    }

    private void shutdownProcedureNormal (){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (consoleInputThread != null && !consoleInputThread.isInterrupted()) {
            consoleInputThread.interrupt();
        }
        if (connectionStarterThread != null && !connectionStarterThread.isInterrupted()) {
            connectionStarterThread.interrupt();
        }
    }

    private void shutdownProcedureExtreme() {
        if (clientConnections.size() != 0) {
            clientConnections.forEach((k, v) -> {
                if (v.thread != null && !v.thread.isInterrupted()) {
                    v.thread.interrupt();
                }
                if (v.socket != null && !v.socket.isClosed()) {
                    try {
                        v.socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        shutdownProcedureNormal();
    }

    private void consoleInput() {
        consoleInputThread = new Thread(() -> {
            try(Scanner scanner = new Scanner(System.in)) {
                while(true) {
//                    System.out.println("Enter your message: ");
                    String outcome = scanner.nextLine();
                    if (outcome.equalsIgnoreCase("/end")) {
//                        shutdownFlag = true;
                        shutdownProcedureExtreme();
                        break;
                    }
                    synchronized (monitor) {
                        clientConnections.forEach((k, v) -> {
                            try {
                                v.out.writeUTF("Server: " + outcome);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        });
        consoleInputThread.start();
    }

    private void connectionStarter(ServerSocket serverSocket) {
        connectionStarterThread = new Thread(() -> {
            try {
                while(true) {
                    Socket socket = null;
                    try {
                        socket = serverSocket.accept();
                    } catch (SocketException e) {
//                        e.printStackTrace();
                        break;
                    }
                    System.out.println("Client #" + connectionNum + " connected.");
                    synchronized (monitor) {
                        clientConnections.put(connectionNum, new Connection(socket, connectionNum));
                    }
                    connectionNum++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectionStarterThread.start();
    }


    private class Connection implements Runnable {

        private DataInputStream in;
        private DataOutputStream out;
        private final Thread thread;
        private final Socket socket;
        private final int connectionID;

        public Connection(Socket socket, int connectionNum) {
            this.socket = socket;
            this.connectionID = connectionNum;
            thread = new Thread(this, "Connection #" + String.valueOf(connectionID));
            thread.start();
        }

        @Override
        public void run() {
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                while(true) {
                    String income = null;
                    try {
                        income = in.readUTF();
                    } catch (IOException e) {
//                        e.printStackTrace();
                        break;
                    }
                    String outcome = connectionID + ": " + income;
                    clientConnections.forEach((k, v) -> {
                        if (k != connectionID ) { //&& !v.socket.isClosed()
                            try {
                                v.out.writeUTF(outcome);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                    if (thread != null && !thread.isInterrupted()) {
                        thread.interrupt();
                    }
                    synchronized (monitor) {
                        clientConnections.remove(connectionID);
                    }
                    System.out.println("Client #" + connectionID + " connection was terminated.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
