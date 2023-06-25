package org.example.collection;

import org.example.data.SpaceMarine;

/**
 * The class that realize methods for work with SpaceMarine
 */
public class CollectionUtil {

    /**
     * @param ID
     * @return
     */
        public static boolean checkExist(Long ID) {
            for (SpaceMarine spaceMarine:CollectionManager.getCollection()) {
                if (spaceMarine.getId().equals(ID))
                    return true;
            }
            return false;
        }
    /**
     * displays information about the character with all fields
     *
     * @param spaceMarine
     */
        public static String display(SpaceMarine spaceMarine) {
            return ("ID элемента коллекции – " + spaceMarine.getId() +
                    "\nИмя бойца – " + spaceMarine.getName() +
                    "\nКоордината X – " + spaceMarine.getCoordinates().getX() +
                    "\nКоордината Y – " + spaceMarine.getCoordinates().getY() +
                    "\nДата создания элемента – " + spaceMarine.getCreationDate() +
                    "\nУровень здоровья – " + spaceMarine.getHealth() +
                    "\nКатегория – " + spaceMarine.getCategory() +
                    "\nТип оружия – " + spaceMarine.getWeaponType() +
                    "\nТип оружия ближнего боя – " + spaceMarine.getMeleeWeapon() +
                    "\nНазвание дивизиона – " + spaceMarine.getChapter().getName() +
                    "\nКоличество бойцов дивизиона – " + spaceMarine.getChapter().getMarinesCount() +
                    "\n-----------------------------------------------------------------------------\n"
            );
        }

    }

