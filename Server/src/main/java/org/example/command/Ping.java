package org.example.command;
import org.example.dtp.*;
import org.example.error.IllegalArgumentsException;

/**
 * Команда 'ping'
 * пингануть сервер
 */
public class Ping extends BaseCommand {
    public Ping() {
        super("ping", "ping : пингануть сервер");
    }

    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        return new Response(ResponseStatus.OK, "pong");
    }
}

