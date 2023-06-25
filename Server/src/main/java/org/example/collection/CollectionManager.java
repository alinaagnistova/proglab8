package org.example.collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.data.*;
import org.example.utils.DatabaseHandler;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * The class that implements collection related methods
 */
public class CollectionManager{
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writeLock = lock.writeLock();
    static Lock readLock = lock.readLock();

    private static final LinkedList<SpaceMarine> collection = new LinkedList<>();
    private LocalDateTime lastInitTime;
    private static final Logger collectionManagerLogger = LogManager.getLogger(CollectionManager.class);

    public CollectionManager() {
        this.lastInitTime = LocalDateTime.now();
        collection.addAll(DatabaseHandler.getDatabaseManager().loadCollection());
    }

    /**
     * @return collection
     */
    public static LinkedList<SpaceMarine> getCollection() {
        try {
            readLock.lock();
            Collections.sort(collection);
            return collection;
        } finally {
            readLock.unlock();
        }
    }

    public static String timeFormatter(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        if (localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getLastInitTime() {
        try {
            readLock.lock();
            return timeFormatter(lastInitTime);
        } finally {
            readLock.unlock();
        }
    }

    public String collectionType() {
        try {
            readLock.lock();
            return collection.getClass().getSimpleName();
        }finally{
            readLock.unlock();
        }
    }
    public int collectionSize() {
        try {
            readLock.lock();
            return collection.size();
        }finally{
            readLock.unlock();
        }
    }

    /**
     * print info about each element in collection
     */
    public StringBuilder information() {
        try {
            readLock.lock();
            if (collection.isEmpty()) {
                System.out.println("В коллекции нет объектов, доступных для просмотра");
            }
            StringBuilder information = new StringBuilder();
            for (SpaceMarine spaceMarine : collection) {
                information.append(CollectionUtil.display(spaceMarine));
            }
            return information;
        }finally {
            readLock.unlock();
        }
    }

    /**
     * adds SpaceMarine
     *
     */
    public void addSpaceMarine(SpaceMarine spaceMarine) {
        try{
            writeLock.lock();
        collection.add(spaceMarine);
        collectionManagerLogger.info("Добавлен объект в коллекцию", spaceMarine);
    }finally {
            writeLock.unlock();
        }
        }

    /**
     * updates data of spaceMarine, ID stays the same
     *
     */
    public void updateId(SpaceMarine newSpaceMarine, Long ID) {
        try {
            writeLock.lock();
            for (SpaceMarine spaceMarine : collection) {
                if (spaceMarine.getId().equals(ID)) {
                    spaceMarine.setName(newSpaceMarine.getName());
                    spaceMarine.setCoordinates(newSpaceMarine.getCoordinates());
                    spaceMarine.setHealth(newSpaceMarine.getHealth());
                    spaceMarine.setChapter(newSpaceMarine.getChapter());
                    if (spaceMarine.getCategory() != null) {
                        spaceMarine.setCategory(newSpaceMarine.getCategory());
                    }
                    if (spaceMarine.getWeaponType() != null) {
                        spaceMarine.setWeaponType(newSpaceMarine.getWeaponType());
                    }
                    if (spaceMarine.getMeleeWeapon() != null) {
                        spaceMarine.setMeleeWeapon(newSpaceMarine.getMeleeWeapon());
                    }
                }
            }
        }finally {
            writeLock.unlock();
        }
    }

    public void removeSpaceMarine(SpaceMarine spaceMarine) {
        try {
            writeLock.lock();
            collection.remove(spaceMarine);
        } finally {
            writeLock.unlock();
        }    }

    public void removeSpaceMarines(List<Long> deletedIds) {
        try {
            writeLock.lock();
            deletedIds
                    .forEach((id) -> collection.remove(this.getById(id)));
        } finally {
            writeLock.unlock();
        }
    }
    public void removeSpaceMarines(Collection<SpaceMarine> collection) {
        try {
            writeLock.lock();
            CollectionManager.collection.removeAll(collection);
        } finally {
            writeLock.unlock();
        }
    }

    public SpaceMarine getById(long id) {
        try {
            readLock.lock();
            for (SpaceMarine spaceMarine : collection) {
                if (spaceMarine.getId() == id) return spaceMarine;
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * clear collection
     */
    public void clear() {
        try {
            writeLock.lock();
            collection.clear();
            lastInitTime = LocalDateTime.now();
            collectionManagerLogger.info("Коллекция очищена");
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * shuffle collection
     */
    public void shuffle() {
        try{
            writeLock.lock();
            Collections.shuffle(collection);
        }finally {
            writeLock.unlock();
        }
    }


    /**
     * sort collection
     */
    public void sort() {
        try{
            writeLock.lock();
            Collections.sort(collection);
        }finally {
            writeLock.unlock();
        }    }

    /**
     * show elements with this kind of weapon
     *
     */
    public String filterByWeapon(Weapon weaponType) {
        try {
            writeLock.lock();
            StringBuilder filterObjects = new StringBuilder();
            for (SpaceMarine spaceMarine : collection) {
                if (spaceMarine.getWeaponType() == weaponType) {
                    filterObjects.append(CollectionUtil.display(spaceMarine));
                }
            }
            if (filterObjects.length() == 0) {
                System.out.println("Нет ни одного экземпляра с таким полем");
            }
            return filterObjects.toString();
        }finally {
            writeLock.unlock();
        }
    }


    /**
     * find unique meleeWeapons and print them
     */
    public ArrayList<MeleeWeapon> printUniqueMeleeWeapon() {
        try {
            writeLock.lock();
            ArrayList<MeleeWeapon> uniq = new ArrayList<>();
            for (SpaceMarine spaceMarine : collection) {
                MeleeWeapon meleeWeapon = spaceMarine.getMeleeWeapon();
                if (Collections.frequency(uniq, meleeWeapon) == 0) {
                    uniq.add(meleeWeapon);
                }
            }
            return uniq;
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * find all weapons in collection and print it (descending)
     */
    public ArrayList<Weapon> printFieldDescendingWeapon() {
        try {
            writeLock.lock();
            ArrayList<Weapon> descendingWeapon = new ArrayList<>();
            for (SpaceMarine spaceMarine : collection) {
                Weapon weaponType = spaceMarine.getWeaponType();
                if (Collections.frequency(descendingWeapon, weaponType) == 0) {
                    descendingWeapon.add(weaponType);
                }
            }
            descendingWeapon.sort(Collections.reverseOrder());
            return descendingWeapon;
        }finally {
            writeLock.unlock();
        }
    }


}





