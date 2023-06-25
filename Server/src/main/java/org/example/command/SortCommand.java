package org.example.command;

import org.example.collection.CollectionManager;
import org.example.collection.CollectionUtil;
import org.example.data.SpaceMarine;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.error.NoSuchIDException;

import java.util.Collection;
import java.util.Objects;

/**
 * sort : sort the collection in natural order
 */
public class SortCommand extends BaseCommand implements CollectionEditor{
    private final CollectionManager collectionManager;

    public SortCommand(CollectionManager collectionManager) {
        super("sort", "sort: отсортировать коллекцию в естественном порядке");
        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException{
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (CollectionManager.getCollection() == null || CollectionManager.getCollection().isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Коллекция еще не инициализирована");
        }
        collectionManager.sort();
        return new Response(ResponseStatus.OK, "Коллекция отсортирована\n");
    }
    }