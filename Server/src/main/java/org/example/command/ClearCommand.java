package org.example.command;


import org.example.collection.CollectionManager;
import org.example.data.SpaceMarine;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.utils.DatabaseHandler;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Command clear : clear the collection
 */
public class ClearCommand extends BaseCommand implements CollectionEditor{
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "clear: очистить коллекцию");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        //todo
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Response", request.getLocale());
        List<Long> deletedIds = collectionManager.getCollection().stream()
                .filter(studyGroup -> studyGroup.getUserLogin().equals(request.getUser().name()))
                .map(SpaceMarine::getId)
                .toList();
        if(DatabaseHandler.getDatabaseManager().deleteAllObjects(request.getUser(), deletedIds)) {
            collectionManager.removeSpaceMarines(deletedIds);
            return new Response(ResponseStatus.OK, resourceBundle.getString("allIsCleared"));
        }
        return new Response(ResponseStatus.ERROR, "Элементы коллекции удалить не удалось");
    }
}
