package org.example.command;


import org.example.collection.CollectionManager;
import org.example.data.SpaceMarine;
import org.example.dtp.*;
import org.example.error.FileModeException;
import org.example.error.IllegalArgumentsException;
import org.example.utils.DatabaseHandler;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Command remove_greater {element} : remove all elements from the collection that exceed the specified
 */
public class RemoveGreaterCommand extends BaseCommand implements CollectionEditor{
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater", "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws  IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            if (Objects.isNull(request.getObject())){
                ResourceBundle resourceBundle = ResourceBundle.getBundle("Response");
                return new Response(ResponseStatus.ASK_OBJECT,resourceBundle.getString("objNeed") + this.getName() + resourceBundle.getString("ForCommandObjecRrequired"));
            }
            Collection<SpaceMarine> toRemove = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(studyGroup -> studyGroup.compareTo(request.getObject()) >= 1)
                    .filter(studyGroup -> studyGroup.getUserLogin().equals(request.getUser().name()))
                    .filter((obj) -> DatabaseHandler.getDatabaseManager().deleteObject(obj.getId(), request.getUser()))
                    .toList();
            collectionManager.removeSpaceMarines(toRemove);
            return new Response(ResponseStatus.OK,"Удалены элементы большие чем заданный");
        } catch (NoSuchElementException e){
            return new Response(ResponseStatus.ERROR,"В коллекции нет элементов");
        } catch (FileModeException e){
            return new Response(ResponseStatus.ERROR,"Поля в файле не валидны! Объект не создан");
        }
    }
    }


