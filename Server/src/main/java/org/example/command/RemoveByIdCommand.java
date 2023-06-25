package org.example.command;


import org.example.collection.CollectionManager;
import org.example.collection.CollectionUtil;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.error.NoSuchIDException;
import org.example.utils.DatabaseHandler;

import java.util.Scanner;

/**
 * Command remove_by_id id : remove an item from the collection by its id
 */
public class RemoveByIdCommand extends BaseCommand implements CollectionEditor{
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "remove_by_id id: удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        class NoSuchId extends RuntimeException {
        }
        try {
            long id = Long.parseLong(request.getArgs().trim());
            if (!CollectionUtil.checkExist(id)) throw new NoSuchId();
            if (DatabaseHandler.getDatabaseManager().deleteObject(id, request.getUser())) {
                collectionManager.removeSpaceMarine(collectionManager.getById(id));
                return new Response(ResponseStatus.OK,"Объект удален успешно");
            } else{
                return new Response(ResponseStatus.ERROR, "Выбранный объект не удален. Скорее всего он вам не принадлежит");
            }
        } catch (NoSuchId err) {
            return new Response(ResponseStatus.ERROR,"В коллекции нет элемента с таким id");
        } catch (NumberFormatException exception) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,"id должно быть числом типа int");
        }
    }
    }


