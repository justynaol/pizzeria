package justynaol.pizzeria.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String login;

    @Column
    private String password;

    @Column
    private boolean admin = false;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    protected User() {
    }

    public String login() {
        return login;
    }

    public String password() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void makeAdmin() {
        this.admin = true;
    }
}
