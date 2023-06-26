package org.example.command;


import org.example.collection.CollectionManager;
import org.example.data.Weapon;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * print_field_descending_weapon_type :
 * print the values of the weaponType field of all elements in descending order
 */
public class PrintFieldDescendingWeapon extends BaseCommand {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingWeapon(CollectionManager collectionManager) {
        super("print_field_descending_weapon_type", "print_field_descending_weapon_type: вывести значения поля weaponType всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (CollectionManager.getCollection() == null || CollectionManager.getCollection().isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Коллекция еще не инициализирована");
        }
        ArrayList<Weapon> descWeapon = collectionManager.printFieldDescendingWeapon();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Response");
        return new Response(ResponseStatus.OK, MessageFormat.format(resourceBundle.getString("allWeaponType"), descWeapon));
    }
}


