package task_2;

import task_2.parser.ToJSON;
import task_2.parser.ToXML;
import java.util.Map;
import java.util.Objects;

public class Violation implements ToJSON, ToXML, Comparable<Violation> {
    private String type;
    private double total;

    public static Violation newInstance(Map.Entry<String, Double> entry){
        return new Violation().setType(entry.getKey()).setTotal(entry.getValue());
    }

    public Violation setType(String type) {
        this.type = type;
        return this;
    }

    public double getTotal() {
        return total;
    }

    public Violation setTotal(double total) {
        this.total = total;
        return this;
    }

    @Override
    public String toXML() {
        return "<violation type=\""+ type +"\" total=\""+ total +"\"/>";
    }

    @Override
    public String toJSON() {
        return String.format("{\n\t\"type\": \"%s\",\n\t\"total\" : \"%s\"\n}", type,total);
    }

    @Override
    public String toString() {
        return "Violation{" + "type='" + type + '\'' + ", sumViolation=" + total + '}';
    }

    @Override
    public int compareTo(Violation o) {
        if(o.getTotal() < total) return -1;
        else if (o.getTotal() == total) return 0;
        else return 1;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Violation violation = (Violation) o;
        return Objects.equals(type, violation.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
