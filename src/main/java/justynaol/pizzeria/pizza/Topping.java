package justynaol.pizzeria.pizza;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Topping {

    @Id
    private String name;

    public Topping(String name) {
        this.name = name;
    }

    protected Topping() {
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topping topping = (Topping) o;
        return Objects.equals(name, topping.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
