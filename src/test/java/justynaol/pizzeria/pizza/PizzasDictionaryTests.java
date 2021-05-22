package justynaol.pizzeria.pizza;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ComponentScan
@AutoConfigureMockMvc(addFilters = false)
public class PizzasDictionaryTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Test
    public void addingNewPizzaToDictionary() throws Exception {
        //when
        this.mockMvc.perform(post("/pizzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"margarita\", \"toppings\": [\"mozzarella\",\"tomato sauce\"]}"))
                .andExpect(status().isCreated());

        //then
        this.mockMvc.perform(get("/pizzas"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\": \"margarita\", \"toppings\": [\"mozzarella\",\"tomato sauce\"]}]"));
    }

    @Test
    public void updatePizzaInDictionary() throws Exception {
        //given
        pizzaRepository.save(new Pizza("margarita",
                List.of(new Topping("mozzarella"), new Topping("tomato sauce"))));

        //when
        this.mockMvc.perform(put("/pizzas/margarita")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"margarita\", \"toppings\": [\"mozzarella\",\"tomato sauce\", \"basil\"]}"))
                .andExpect(status().isOk());

        //then
        assertThat(pizzaRepository.getById("margarita").toppings())
                .contains(new Topping("basil"));
    }

    @Test
    public void deletePizzaFromDictionary() throws Exception {
        //given
        pizzaRepository.save(new Pizza("margarita",
                List.of(new Topping("mozzarella"), new Topping("tomato sauce"))));

        //when
        this.mockMvc.perform(delete("/pizzas/margarita")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        assertThat(pizzaRepository.findById("margarita").isEmpty()).isTrue();
    }
}
