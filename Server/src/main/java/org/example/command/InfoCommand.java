package org.example.command;


import org.example.collection.CollectionManager;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.utils.ConsoleColors;

import java.util.ResourceBundle;

/**
 * info :
 * output to the standard output stream information about the collection
 * (type, initialization date, number of items, etc.)
 */
public class InfoCommand extends BaseCommand {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Response", request.getLocale());
        String lastInitTime = (collectionManager.getLastInitTime() == null)
                ? resourceBundle.getString("noCollectionInSession")
                : collectionManager.getLastInitTime().toString();
        String stringBuilder = resourceBundle.getString("CollectionInfo") +
                resourceBundle.getString("type") + collectionManager.collectionType() + "\n" +
                resourceBundle.getString("elementsCount") + collectionManager.collectionSize() + "\n" +
                resourceBundle.getString("lastInitTime") + lastInitTime + "\n";
        return new Response(ResponseStatus.OK, stringBuilder);
    }
}