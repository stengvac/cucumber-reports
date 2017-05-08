package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;

/**
 * Dao representation of line range - usually used to mark range of longer structures inside feature file.
 *
 * @author Vaclav Stengl
 */
public class LineRange implements Serializable {

    private static final long serialVersionUID = 278179135364181082L;

    private int first;
    private int last;

    /**
     * Start line of range - inclusive.
     */
    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * Last line of range - inclusive.
     */
    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "LineRange{" +
                "first=" + first +
                ", last=" + last +
                '}';
    }
}
