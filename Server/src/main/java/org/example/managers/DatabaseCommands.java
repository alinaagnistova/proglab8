package org.example.managers;

public class DatabaseCommands {
    public static final String allTablesCreation = """
            CREATE TYPE MELEE_WEAPON AS ENUM(
                'CHAIN_SWORD',
                'POWER_SWORD',
                'CHAIN_AXE',
                'MANREAPER',
                'LIGHTING_CLAW'
            );
            CREATE TYPE ASTARTES_CATEGORY AS ENUM (
                'SCOUT',
                'INCEPTOR',
                'TACTICAL',
                'TERMINATOR',
                'HELIX'
            );
            CREATE TYPE WEAPON AS ENUM(
                'BOLT_PISTOL',
                'PLASMA_GUN',
                'FLAMER',
                'GRAV_GUN',
                'INFERNO_PISTOL'
            );
            CREATE TABLE IF NOT EXISTS spaceMarine (
                id BIGSERIAL PRIMARY KEY,
                name TEXT NOT NULL ,
                cord_x INT NOT NULL,
                cord_y NUMERIC NOT NULL ,
                creation_date DATE NOT NULL ,
                health NUMERIC NOT NULL ,
                astartes_category ASTARTES_CATEGORY,
                weapon_type WEAPON,
                melee_weapon MELEE_WEAPON,
                chapter_name TEXT NOT NULL,
                chapter_marines_count INT NOT NULL,
                owner_login TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                login TEXT,
                password TEXT,
                salt TEXT
            );
            """;
    public static final String addUser = """
            INSERT INTO users(login, password, salt) VALUES (?, ?, ?);""";

    public static final String getUser = """
            SELECT * FROM users WHERE (login = ?);""";

    public static final String addObject = """
            INSERT INTO spaceMarine(name, cord_x, cord_y, creation_date, health, astartes_category, weapon_type, melee_weapon, chapter_name, chapter_marines_count, owner_login)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """;

    public static final String getAllObjects = """
            SELECT * FROM spaceMarine;
            """;

    public static final String deleteUserOwnedObjects = """
            DELETE FROM spaceMarine WHERE (owner_login = ?) AND (id = ?) RETURNING id;
            """;

    public static final String deleteUserObject = """
            DELETE FROM spaceMarine WHERE (owner_login = ?) AND (id = ?) RETURNING id;
            """;

    public static final String updateUserObject = """
            UPDATE spaceMarine
            SET (name, cord_x, cord_y, creation_date, health, astartes_category, weapon_type, melee_weapon, chapter_name, chapter_marines_count)
             = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            WHERE (id = ?) AND (owner_login = ?)
            RETURNING id;
            """;
}