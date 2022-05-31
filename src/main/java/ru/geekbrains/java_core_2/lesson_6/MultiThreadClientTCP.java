package ru.geekbrains.java_core_2.lesson_6;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class MultiThreadClientTCP {
    private static final int PORT = 6629;
    private static final String HOST = "127.0.0.1";

    private Thread consoleInputThread;
    private Thread connectionThread;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean shutdownFlag = false;

    public static void main(String[] args) {
        new MultiThreadClientTCP().start();
    }

    public void start() {
        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("Client connected.");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            connection(socket);
            consoleInput();//use some resources initialized in connection()
            try {
                consoleInputThread.join();
                connectionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shutdownProcedure();
            System.out.println("Client app is closed.");
        }
    }

    private void connection(Socket socket) {
        connectionThread = new Thread(() -> {
            try {
                while (!shutdownFlag) {// true works too
                    String income = in.readUTF();
//                if (income.contains("/end")) { remote control
//                    break;
//                }
                    System.out.println(income);
                }
            } catch (SocketException e) {
                System.out.println("User terminated connection.");
            } catch (EOFException e) {
                System.out.println("Server terminated connection.");
                shutdownFlag = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectionThread.start();
    }

    private void shutdownProcedure() {
        try {
            in.close();
//            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (consoleInputThread != null && !consoleInputThread.isInterrupted()) {
            consoleInputThread.interrupt();
        }
        if (connectionThread != null && !connectionThread.isInterrupted()) {
            connectionThread.interrupt();
        }
    }

    private void consoleInput() {
        consoleInputThread = new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                while(!shutdownFlag) {
//                    System.out.println("Enter your message: ");
                    if (br.ready()) {
                        String outcome = br.readLine();
                        if (outcome.equalsIgnoreCase("/end")) {
                            shutdownProcedure();
                            break;
                        }
                        out.writeUTF(outcome);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        consoleInputThread.start();
    }



}
