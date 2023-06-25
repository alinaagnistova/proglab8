package org.example.forms;


import org.example.dtp.User;
import org.example.error.FileModeException;
import org.example.console.*;
import org.example.utils.*;


import java.util.Objects;

/**
 * Форма для создания юзера
 */
public class UserForm extends Form<User>{

    private final ReaderWriter console;
    private final UserInput scanner;

    public UserForm(Console console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    @Override
    public User build() {
        return new User(
                askLogin(),
                askPassword()
        );
    }

    public boolean askIfLogin(){
        for(;;) {
            console.write("У вас уже есть аккаунт? [yn]  ");
            String input = scanner.nextLine().trim().toLowerCase();
            switch (input){
                case "y", "yes", "да", "д" -> {
                    return true;
                }
                case "n", "no", "нет", "н" -> {
                    return false;
                }
                default -> console.printError("Ответ не распознан");
            }
        }
    }

    private String askLogin(){
        String login;
        while (true){
            console.write(ConsoleColors.toColor("Введите ваш логин", ConsoleColors.GREEN));
            login = scanner.nextLine().trim();
            if (login.isEmpty()){
                console.printError("Логин не может быть пустым");
                if (Console.isFileMode()) throw new FileModeException();
            }
            else{
                return login;
            }
        }
    }

    private String askPassword(){
        String pass;
        while (true){
            console.write(ConsoleColors.toColor("Введите пароль", ConsoleColors.GREEN));
            pass = (Objects.isNull(System.console()))
                    ? scanner.nextLine().trim()
                    : new String(System.console().readPassword());
            if (pass.isEmpty()){
                console.printError("Пароль не может быть пустым");
                if (Console.isFileMode()) throw new FileModeException();
            }
            else{
                return pass;
            }
        }
    }
}
