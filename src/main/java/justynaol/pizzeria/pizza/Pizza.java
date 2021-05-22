package justynaol.pizzeria.pizza;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
public class Pizza {

    @Id
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="PIZZA_TOPPING")
    private List<Topping> toppings = new ArrayList<>();

    public Pizza(String name, List<Topping> toppings) {
        this.name = name;
        this.toppings = toppings;
    }

    protected Pizza() {
    }

    public String name() {
        return name;
    }

    public List<Topping> toppings() {
        return toppings;
    }

    public void update(List<String> toppings) {
        this.toppings = toppings.stream()
                .map(name -> new Topping(name))
                .collect(toList());
    }
}
