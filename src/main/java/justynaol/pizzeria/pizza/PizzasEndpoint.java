package justynaol.pizzeria.pizza;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/pizzas")
public class PizzasEndpoint {

    private final PizzaRepository pizzaRepository;

    public PizzasEndpoint(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PizzaDefinition definition) {
        List<Topping> toppings = definition.toppings.stream()
                .map(name -> new Topping(name))
                .collect(toList());
        Pizza pizza = new Pizza(definition.name, toppings);
        pizzaRepository.save(pizza);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PizzaDefinition>> list() {
        List<PizzaDefinition> pizzas = pizzaRepository.findAll().stream()
                .map(pizza -> PizzaDefinition.from(pizza))
                .collect(toList());
        return new ResponseEntity<>(pizzas, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{name}")
    public ResponseEntity<Void> update(@PathVariable String name, @RequestBody PizzaDefinition definition) {
        Pizza pizza = pizzaRepository.getById(name);
        pizza.update(definition.toppings);
        pizzaRepository.save(pizza);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name){
        pizzaRepository.deleteById(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class PizzaDefinition {
        String name;
        List<String> toppings = new ArrayList<>();

        public static PizzaDefinition from(Pizza pizza) {
            PizzaDefinition pizzaDefinition = new PizzaDefinition();
            pizzaDefinition.name = pizza.name();
            pizzaDefinition.toppings = pizza.toppings().stream().map($ -> $.name()).collect(toList());
            return pizzaDefinition;
        }
    }
}

