package org.example.command;


import org.example.collection.CollectionManager;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.utils.DatabaseHandler;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Command add {element}
 */
public class AddCommand extends BaseCommand implements CollectionEditor{
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "add {element}: добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * add a new element to the collection
     *
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        //todo
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Response", request.getLocale());
        if (Objects.isNull(request.getObject())){
            return new Response(ResponseStatus.ASK_OBJECT,resourceBundle.getString("objNeed") + this.getName() + resourceBundle.getString("ForCommandObjecRrequired"));
        } else{
            long new_id = DatabaseHandler.getDatabaseManager().addObject(request.getObject(), request.getUser());
            if(new_id == -1) return new Response(ResponseStatus.ERROR, resourceBundle.getString("objNotSucceedAdd"));
            request.getObject().setId(new_id);
            request.getObject().setUserLogin(request.getUser().name());
            collectionManager.addSpaceMarine(request.getObject());
            return new Response(ResponseStatus.OK, resourceBundle.getString("objAddOl"));
        }
    }

}