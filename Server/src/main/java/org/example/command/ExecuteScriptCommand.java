package org.example.command;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;


/**
 * execute_script file_name : read and execute the script from the specified file
 * The script contains commands in the same form as they are entered by the user in interactive mode.
 */
public class ExecuteScriptCommand extends BaseCommand {

        public ExecuteScriptCommand() {
            super("execute_script", "execute_script: выполнить скрипт");
        }

        /**
         * Исполнить команду
         * @param request запрос клиента
         * @throws IllegalArgumentsException неверные аргументы команды
         */
        @Override
        public Response execute(Request request) throws IllegalArgumentsException {
            if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
            return new Response(ResponseStatus.EXECUTE_SCRIPT, request.getArgs());
        }

    }



