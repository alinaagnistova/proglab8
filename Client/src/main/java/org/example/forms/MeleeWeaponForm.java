package org.example.forms;

import org.example.console.*;
import org.example.data.MeleeWeapon;
import org.example.error.FileModeException;
import org.example.utils.ConsoleColors;
import org.example.utils.ExecuteFileManager;

import java.util.Arrays;
import java.util.Locale;

public class MeleeWeaponForm extends Form<MeleeWeapon>{
    private final ReaderWriter console;
    private final UserInput scanner;
    public MeleeWeaponForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    @Override
    public MeleeWeapon build() {
        console.write("Возможные типы оружия ближнего боя бойца: ");
        console.write(Arrays.toString(MeleeWeapon.values()));
        while (true){
            console.write(ConsoleColors.toColor("Введите тип оружия " , ConsoleColors.GREEN));
            String input = scanner.nextLine().trim();
            try{
                return MeleeWeapon.valueOf(input.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception){
                console.printError("Такого оружия нет в списке");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }

}
