package org.example.console;

import org.example.utils.ScannerManager;

import java.util.Scanner;

/**
 * Класс для стандартного ввода через консоль
 */
public class ConsoleInput implements UserInput{
    private static final Scanner userScanner = ScannerManager.getUserScanner();
@Override
    public String nextLine() {
        return userScanner.nextLine();
    }
}
