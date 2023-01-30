package task_1;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ViolationTotal implements Comparable<ViolationTotal> {
    @EqualsAndHashCode.Include
    private String type;
    private BigDecimal total;

    public ViolationTotal setType(String type) {
        this.type = type;
        return this;
    }

    public ViolationTotal setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    @Override
    public int compareTo(ViolationTotal o) {
        return total.compareTo(o.getTotal());
    }
}
