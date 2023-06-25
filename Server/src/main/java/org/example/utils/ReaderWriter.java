package org.example.utils;

import org.example.utils.ConsoleColors;

public interface ReaderWriter {
    Long readLong();
    String readLine();
    void write(String text);
    void write(String text, ConsoleColors consoleColors);
    void printError(String text);
    String getValidatedValue(String message);
}
