package org.example.command;


import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.managers.DatabaseManager;

import java.sql.SQLException;

/**
 * Команда 'register'
 * Регистрирует пользователя
 */
public class Register extends BaseCommand {
    DatabaseManager databaseManager;

    public Register(DatabaseManager databaseManager) {
        super("register", "register : зарегистрировать пользователя");
        this.databaseManager = databaseManager;
    }

    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws org.example.error.IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        this.commandLogger.debug("получен юзер: " + request.getUser());
        try {
            databaseManager.addUser(request.getUser());
        } catch (SQLException e) {
            e.getMessage();
            commandLogger.fatal("Невозможно добавить пользователя");
            return new Response(ResponseStatus.LOGIN_FAILED, "Введен невалидный пароль!");
        }
        return new Response(ResponseStatus.OK,"Вы успешно зарегистрированы!");
    }
}
