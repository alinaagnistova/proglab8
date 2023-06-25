package org.example.data;



import java.io.Serializable;
import java.time.LocalDate;
/**
 * The class with spaceMarine's constructor
 */
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable, IValidator {
    private Long id;
    private String name;
    private static final long serialVersionUID = 5L;

    private Coordinates coordinates;

    private LocalDate creationDate;
    private Float health;
    private AstartesCategory category;
    private Weapon weaponType;
    private MeleeWeapon meleeWeapon;
    private Chapter chapter;
    private String userLogin;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public SpaceMarine() {
    }

    public SpaceMarine(String name, Coordinates coordinates, Float health, LocalDate creationDate, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = 0L;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine(String name, Coordinates coordinates, Float health, AstartesCategory category, Weapon weapon, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = 0L;
        this.name = name;
        this.coordinates = coordinates;
        this.health = health;
        this.category = category;
        this.weaponType = weapon;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine(Long id, String name, Coordinates coordinates, LocalDate creationDate, Float health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter, String userLogin) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
        this.userLogin = userLogin;
    }
    public SpaceMarine(String name, Coordinates coordinates, LocalDate creationDate, Float health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter, String userLogin) {
        this.id = 0l;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
        this.userLogin = userLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Float getHealth() {
        return health;
    }

    public void setHealth(Float health) {
        this.health = health;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public int compareTo(SpaceMarine o) {
        if (this.getHealth() > o.getHealth()) {
            return 1;
        } else if (this.getHealth() < o.getHealth()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", health=" + health +
                ", category=" + category +
                ", weaponType=" + weaponType +
                ", meleeWeapon=" + meleeWeapon +
                ", chapter=" + chapter +
                '}';
    }


    @Override
    public boolean validate() {
        if (this.id == null ||
                this.id < 0 ||
                this.name == null ||
                this.name.isEmpty() ||
                this.coordinates == null ||
                this.creationDate == null ||
                this.chapter == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceMarine that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getCoordinates() != null ? !getCoordinates().equals(that.getCoordinates()) : that.getCoordinates() != null)
            return false;
        if (getCreationDate() != null ? !getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() != null)
            return false;
        if (getHealth() != null ? !getHealth().equals(that.getHealth()) : that.getHealth() != null) return false;
        if (getCategory() != that.getCategory()) return false;
        if (getWeaponType() != that.getWeaponType()) return false;
        if (getMeleeWeapon() != that.getMeleeWeapon()) return false;
        return getChapter() != null ? getChapter().equals(that.getChapter()) : that.getChapter() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCoordinates() != null ? getCoordinates().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        result = 31 * result + (getHealth() != null ? getHealth().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getWeaponType() != null ? getWeaponType().hashCode() : 0);
        result = 31 * result + (getMeleeWeapon() != null ? getMeleeWeapon().hashCode() : 0);
        result = 31 * result + (getChapter() != null ? getChapter().hashCode() : 0);
        return result;
    }
}

