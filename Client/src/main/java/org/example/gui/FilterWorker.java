package org.example.gui;



import org.example.data.SpaceMarine;

import java.util.*;
import java.util.function.Predicate;

public class FilterWorker {
    private final LinkedHashMap<Integer, Predicate<SpaceMarine>> predicates = new LinkedHashMap<>();

    public void addPredicate(int row, Predicate<SpaceMarine> predicate){
        predicates.put(row, predicate);
    }

    public Predicate<SpaceMarine> getPredicate(){
        return predicates.values().stream()
                .reduce(x -> true, Predicate::and);
    }

    public void clearPredicates(){
        this.predicates.clear();
    }

    public void parsePredicate(int row, List<?> values){
        switch (row) {
            case 0 -> this.addPredicate(0, (o) -> values.contains(o.getId()));
            case 1 -> this.addPredicate(1, (o) -> values.contains(o.getName()));
            case 2 -> this.addPredicate(2, (o) -> values.contains(o.getCoordinates()));
            case 3 -> this.addPredicate(3, (o) -> values.contains(o.getCreationDate()));
            case 4 -> this.addPredicate(4, (o) -> values.contains(o.getHealth()));
            case 5 -> this.addPredicate(5, (o) -> values.contains(o.getCategory()));
            case 6 -> this.addPredicate(6, (o) -> values.contains(o.getWeaponType()));
            case 7 -> this.addPredicate(7, (o) -> values.contains(o.getMeleeWeapon()));
            case 8 -> this.addPredicate(8, (o) -> values.contains(o.getChapter().getName()));
            case 9 -> this.addPredicate(9, (o) -> values.contains(o.getChapter().getMarinesCount()));
            case 10 -> this.addPredicate(10, (o) -> values.contains(o.getUserLogin()));
        }
    }
}

