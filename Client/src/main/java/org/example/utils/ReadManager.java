package org.example.utils;

import org.example.console.*;
import org.example.data.AstartesCategory;
import org.example.data.MeleeWeapon;
import org.example.data.Weapon;
import org.example.error.FileModeException;

import java.util.Arrays;
/**
 * The class is responsible for what the user enters
 */
public class ReadManager implements Readable {
    private final ReaderWriter console;
    private final UserInput scanner;

    public ReadManager(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * method checks if the name is entered correctly, it contains only letters or not
     *
     * @return name
     */
    @Override
    public String readName() {
        String name;
        while (true) {
            console.write("Введите имя/название отряда:");
            name = scanner.nextLine().trim();
            if (name.equals("") || !name.matches("^[a-zA-Z-А-Яа-я]*$")) {
                console.printError("Имя не может быть пустой строкой/иными знаками, кроме букв");
                if (Console.isFileMode()) throw new FileModeException();
            } else {
                return name;
            }
        }
    }

    /**
     * checks if the entered coordinate is correct, whether a number is entered or not
     *
     * @return X
     */
    @Override
    public Integer readCoordinateX() {
        while (true) {
            console.write("Введите координату X:");
            String input = scanner.nextLine().trim();
            try {
                int x = Integer.parseInt(input);
                if (x <= -595) {
                    console.printError("Значение поля должно быть больше -595");
                }
                if (input.isEmpty()) {
                    console.printError("Введите число, а не пустую строку");
                } else {
                    return x;
                }
            } catch (NumberFormatException exception) {
                console.printError("Число введено неверно");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }

    }

    /**
     * checks if the entered coordinate is correct, whether a number is entered or not
     *
     * @return Y
     */
    @Override
    public Float readCoordinateY() {
        while (true) {
            console.write("Введите координату Y:");
            String input = scanner.nextLine().trim();
            try {
                float y = Float.parseFloat(input);
                if (input.isEmpty()) {
                    console.printError("Введите число, а не пустую строку");
                } else {
                    return y;
                }
            } catch (NumberFormatException exception) {
                console.printError("Число введено неверно");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }


    /**
     * checks the correctness of the entered health, whether the number is entered or not, check for zero
     *
     * @return health
     */
    @Override
    public Float readHealth() {
        while (true) {
            console.write("Введите уровень здоровья бойца:");
            String input = scanner.nextLine().trim();
            try {
                float health = Float.parseFloat(input);
                if (input.isEmpty()) {
                    console.printError("Введите число, а не пустую строку");
                } else {
                    return health;
                }
            } catch (NumberFormatException exception) {
                console.printError("Число введено неверно");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }

    }

    @Override
    public AstartesCategory readCategory() {
        console.write("Вы должны ввести одно из перечисленных видов оружия:" + Arrays.toString(AstartesCategory.values()));
        AstartesCategory astartesCategory;
        try {
            astartesCategory = AstartesCategory.valueOf(console.getValidatedValue("\nВведите вид оружия:").toUpperCase());
        } catch (IllegalArgumentException e) {
            astartesCategory = readCategory();
            if (Console.isFileMode()) throw new FileModeException();
        }
        return astartesCategory;
    }

    @Override
    public Weapon readWeapon() {
        console.write("Вы должны ввести одно из перечисленных видов оружия:" + Arrays.toString(Weapon.values()));
        Weapon weapon;
        try {
            weapon = Weapon.valueOf(console.getValidatedValue("\nВведите вид оружия:").toUpperCase());
        } catch (IllegalArgumentException e) {
            weapon = readWeapon();
            if (Console.isFileMode()) throw new FileModeException();
        }
        return weapon;
    }

    @Override
    public MeleeWeapon readMeleeWeapon() {
        console.write("Вы должны ввести одно из перечисленных видов оружия ближнего боя:" + Arrays.toString(MeleeWeapon.values()));
        MeleeWeapon meleeWeapon;
        try {
            meleeWeapon = MeleeWeapon.valueOf(console.getValidatedValue("\nВведите вид оружия ближнего боя:").toUpperCase());
        } catch (IllegalArgumentException e) {
            meleeWeapon = readMeleeWeapon();
            if (Console.isFileMode()) throw new FileModeException();
        }
        return meleeWeapon;
    }

    @Override
    public Integer readChapterMarinesCount() {
        while (true) {
            console.write("Введите количество бойцов дивизиона:");
            String input = scanner.nextLine().trim();
            try {
                int marinesCount = Integer.parseInt(input);
                if (input.isEmpty()) {
                    console.printError("Введите число, а не пустую строку");
                } else {
                    return marinesCount;
                }
            } catch (NumberFormatException exception) {
                console.printError("Число введено неверно");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
}
