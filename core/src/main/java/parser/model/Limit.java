package parser.model;

/**
 * Created by aude on 2016/12/23.
 */
public class Limit {
    private int offset;

    private int rows;

    public Limit(int offset, int rows) {
        this.offset = offset;
        this.rows = rows;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
