package org.example.utils;

import org.example.data.AstartesCategory;
import org.example.data.MeleeWeapon;
import org.example.data.Weapon;

public interface Readable {
    String readName();
    Integer readCoordinateX();
    Float readCoordinateY();
    Float readHealth();
    AstartesCategory readCategory();
    Weapon readWeapon();
    MeleeWeapon readMeleeWeapon();
    Integer readChapterMarinesCount();
}
