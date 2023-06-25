package org.example.command;


import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.managers.CommandManager;

/**
 * help : print help for available commands
 */
public class HelpCommand extends BaseCommand {
    private CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", "help: вывести справку по доступным командам");
        this.commandManager = commandManager;
    }
    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws org.example.error.IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(ResponseStatus.OK,
                String.join("\n",
                        commandManager.getCommands()
                                .stream().map(BaseCommand::getDescription).toList()) + "\n");
    }
}
