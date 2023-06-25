package org.example.forms;

import org.example.console.*;
import org.example.data.AstartesCategory;
import org.example.error.FileModeException;
import org.example.utils.ConsoleColors;
import org.example.utils.ExecuteFileManager;

import java.util.Arrays;
import java.util.Locale;

public class AstartesCategoryForm extends Form<AstartesCategory>{
    private final ReaderWriter console;
    private final UserInput scanner;

    public AstartesCategoryForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    /**
     * Сконструировать новый элемент класса {@link AstartesCategory}
     * @return объект класса {@link AstartesCategory}
     */
    @Override
    public AstartesCategory build() {
        console.write("Возможные категории бойца: ");
        console.write(Arrays.toString(AstartesCategory.values()));
        while (true){
            console.write(ConsoleColors.toColor("Введите категорию " , ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            try{
                return AstartesCategory.valueOf(input.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception){
                console.printError("Такой категории нет в списке");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
}
