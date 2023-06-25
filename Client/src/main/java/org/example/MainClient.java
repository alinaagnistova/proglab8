package org.example;

import org.example.console.BlankConsole;
import org.example.console.ReaderWriter;
import org.example.console.Console;
import org.example.gui.GuiManager;
import org.example.utils.Client;
import org.example.error.IllegalArgumentsException;


public class MainClient {
    private static String host;
    private static int port;
    private static ReaderWriter console = new BlankConsole();
    public final static int RECONNECTION_TIMEOUT = 5;
    public final static int MAX_RECONNECTION_ATTEMPTS = 5;



    public static boolean parseHostPort(String[] args){
        try{
            if(args.length != 2) throw new IllegalArgumentsException("Передайте хост и порт в аргументы " +
                    "командной строки в формате <host> <port>");
            host = args[0];
            port = Integer.parseInt(args[1]);
            if(port < 0) throw new IllegalArgumentsException("Порт должен быть натуральным числом");
            return true;
        } catch (IllegalArgumentsException e) {
            console.printError(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        if (!parseHostPort(args)) return;
        Console console = new Console();
        Client client = new Client(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, console);
        new GuiManager(client);
    }
}
