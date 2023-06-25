package org.example;

import org.example.console.BlankConsole;
import org.example.console.ReaderWriter;
import org.example.console.Console;
import org.example.utils.Client;
import org.example.error.IllegalArgumentsException;
import org.example.utils.RuntimeManager;

import java.util.Scanner;

public class MainClient {
    private static String host;
    private static int port;
    private static ReaderWriter console = new BlankConsole();

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
        Client client = new Client(host, port, 5000, 5, console);
        new RuntimeManager(console, new Scanner(System.in), client).interactiveMode();
    }
}
