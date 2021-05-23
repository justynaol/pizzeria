package justynaol.pizzeria.user;


import justynaol.pizzeria.pizza.Pizza;
import justynaol.pizzeria.pizza.PizzaRepository;
import justynaol.pizzeria.pizza.Topping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ComponentScan
@AutoConfigureMockMvc(addFilters = false)
public class UsersManagementTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void registeringNewUser() throws Exception {
        //when
        this.mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"justynaol\", \"password\": \"V3rY5tR0nGp@5SwOrD\"}"))
                .andExpect(status().isCreated());

        //then
        assertThat(userRepository.findById("justynaol").isPresent()).isTrue();
    }

}
