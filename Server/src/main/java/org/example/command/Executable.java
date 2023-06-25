package org.example.command;

import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.error.CommandRuntimeException;
import org.example.error.ExitObligedException;
import org.example.error.IllegalArgumentsException;

/**
 * Интерфейс для исполняемых команд
 */
public interface Executable {
    Response execute(Request request) throws CommandRuntimeException, ExitObligedException, IllegalArgumentsException;
}
