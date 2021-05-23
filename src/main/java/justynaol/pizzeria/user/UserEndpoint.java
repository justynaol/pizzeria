package justynaol.pizzeria.user;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserDefinition definiton) {
        User user = new User(definiton.login, passwordEncoder.encode(definiton.password));
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    protected static class UserDefinition {
        public String login;
        public String password;
    }
}
