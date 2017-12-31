package xwords;

import java.util.Objects;

public class OrderedPartialFill {

    private final PartialFill fill;
    private final int count;

    public OrderedPartialFill(PartialFill fill, int count) {
        this.fill = fill;
        this.count = count;
    }

    public PartialFill getFill() {
        return fill;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return count + ". " + fill.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedPartialFill that = (OrderedPartialFill) o;
        return count == that.count &&
                Objects.equals(fill, that.fill);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fill, count);
    }
}
