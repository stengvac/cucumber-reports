package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;

/**
 * Statement line interval.
 *
 * @author Vaclav Stengl
 */
public class LineRange implements Serializable {

    private static final long serialVersionUID = -3494696548475637387L;

    private int first;
    private int last;

    public LineRange() {}

    public LineRange(int first, int last) {
        this.first = first;
        this.last = last;
    }

    /**
     * First line.
     */
    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * Last line.
     */
    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }
}
