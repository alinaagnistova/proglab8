package org.example.command;


import org.example.collection.CollectionManager;
import org.example.data.SpaceMarine;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;

import java.util.Collection;

/**
 * Command show. Output to the standard output stream all elements of the collection in string representation
 */
public class ShowCommand extends BaseCommand {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить команду
     *
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        Collection<SpaceMarine> collection = collectionManager.getCollection();
        if (collection == null || collection.isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Коллекция еще не инициализирована");
        }
        return new Response(ResponseStatus.OK, "Коллекция:", collection);
    }
}