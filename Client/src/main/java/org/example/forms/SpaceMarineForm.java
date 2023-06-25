package org.example.forms;

import org.example.data.*;
import org.example.utils.ReadManager;
import org.example.console.*;
import org.example.utils.ExecuteFileManager;

import java.time.LocalDate;

public class SpaceMarineForm extends Form<SpaceMarine> {
    private final ReaderWriter console;
    private final UserInput scanner;

    public SpaceMarineForm(Console console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Сконструировать новый элемент класса {@link SpaceMarine}
     *
     * @return объект класса {@link SpaceMarine}
     */
    @Override
    public SpaceMarine build() {
        ReadManager readManager = new ReadManager(console);
        LocalDate localDate = LocalDate.now();
        return new SpaceMarine(
                        readManager.readName(),
                        readCoordinates(),
                        readManager.readHealth(),
                        localDate,
                        readAstartesCategory(),
                        readWeapon(),
                        readMeleeWeapon(),
                        readChapter()
                );
    }
    private Coordinates readCoordinates(){
        return new CoordinatesForm(console).build();
    }
    private Chapter readChapter(){
        return new ChapterForm(console).build();
    }
    private AstartesCategory readAstartesCategory(){
        return new AstartesCategoryForm(console).build();
    }
    private Weapon readWeapon(){
        return new WeaponForm(console).build();
    }
    private MeleeWeapon readMeleeWeapon(){
        return new MeleeWeaponForm(console).build();
    }


}
