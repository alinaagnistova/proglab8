package org.example.command;


import org.example.collection.CollectionManager;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;

/**
 * shuffle : shuffle collection items in random order
 */
public class ShuffleCommand extends BaseCommand implements CollectionEditor{
    private final CollectionManager collectionManager;

    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle", "shuffle: перемешать элементы коллекции в случайном порядке");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (CollectionManager.getCollection() == null || CollectionManager.getCollection().isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Коллекция еще не инициализирована");
        }
        collectionManager.shuffle();
        return new Response(ResponseStatus.OK, "Коллекция перемешана\n");
    }

}

