package justynaol.pizzeria;

import justynaol.pizzeria.user.User;
import justynaol.pizzeria.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@EnableTransactionManagement(proxyTargetClass=true)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PizzeriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzeriaApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {
		User admin = new User("admin", passwordEncoder.encode("admin"));
		admin.makeAdmin();
		userRepository.save(admin);
		User user = new User("user", passwordEncoder.encode("user"));
		userRepository.save(user);
	}
}
