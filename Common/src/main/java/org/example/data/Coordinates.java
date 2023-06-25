package org.example.data;


import javax.xml.bind.annotation.*;
import java.io.Serializable;
/**
 * The class with spaceMarine's coordinates
 */
public class Coordinates implements Serializable, IValidator {
    private static final long serialVersionUID = 3L;

    private Integer x;
    private Float y;

    public Coordinates() {
    }

    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates that)) return false;

        if (getX() != null ? !getX().equals(that.getX()) : that.getX() != null) return false;
        return getY() != null ? getY().equals(that.getY()) : that.getY() == null;
    }

    @Override
    public int hashCode() {
        int result = getX() != null ? getX().hashCode() : 0;
        result = 31 * result + (getY() != null ? getY().hashCode() : 0);
        return result;
    }

    @Override
    public boolean validate() {
        if (this.x == null ||
            this.y == null ||
            this.x <= -595){
            return false;
        }else{
            return true;
        }
    }
}


