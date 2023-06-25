package org.example.utils;


import java.util.Scanner;

public class Console implements ReaderWriter {
    private static boolean fileMode = false;

    public static boolean isFileMode() {
        return fileMode;
    }

    public static void setFileMode(boolean fileMode) {
        Console.fileMode = fileMode;
    }

    @Override
    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine().trim();
        return text;
    }

    @Override
    public void write(String text) {
        System.out.print(text);
    }

    @Override
    public void write(String text, ConsoleColors consoleColors) {
        System.out.println(ConsoleColors.toColor(text, consoleColors));
    }

    @Override
    public void printError(String text) {
        System.out.println(ConsoleColors.RED + text + ConsoleColors.RESET);
    }

    @Override
    public String getValidatedValue(String message) {
        write(message);
        while (true) {
            String userPrint = readLine();
            if (!userPrint.isEmpty() && !userPrint.isBlank()) {
                return userPrint;
            }
        }
    }
    }

