package org.example.command;


import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;

/**
 * exit : terminate the program (without saving to a file)
 */
public class ExitCommand extends BaseCommand {
    public ExitCommand() {
        super("exit", "exit: завершить программу (без сохранения в файл)");
    }
    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws org.example.error.ExitObligedException нужен выход из программы
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(ResponseStatus.EXIT);
    }
}



