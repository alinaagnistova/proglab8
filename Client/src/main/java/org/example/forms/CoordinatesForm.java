package org.example.forms;

import org.example.utils.ReadManager;
import org.example.console.*;
import org.example.data.Coordinates;
import org.example.utils.ExecuteFileManager;

/**
 * Форма для координат
 */
public class CoordinatesForm extends Form<Coordinates> {
    private final ReaderWriter console;
    private final UserInput scanner;

    public CoordinatesForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Сконструировать новый элемент класса {@link Coordinates}
     *
     * @return объект класса {@link Coordinates}
     */
    @Override
    public Coordinates build() {
        ReadManager readManager = new ReadManager(console);
        return new Coordinates(
                readManager.readCoordinateX(),
                readManager.readCoordinateY()
        );
    }
}

