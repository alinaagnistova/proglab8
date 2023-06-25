package org.example.forms;

import org.example.utils.ReadManager;
import org.example.console.*;
import org.example.data.Chapter;
import org.example.utils.ExecuteFileManager;

public class ChapterForm extends Form<Chapter>{
    private final ReaderWriter console;
    private final UserInput scanner;

    public ChapterForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Сконструировать новый элемент класса {@link Chapter}
     * @return объект класса {@link Chapter}
     */
    @Override
    public Chapter build() {
        ReadManager readManager = new ReadManager(console);
        return new Chapter(
                readManager.readName(),
                readManager.readChapterMarinesCount()
        );
    }
}
