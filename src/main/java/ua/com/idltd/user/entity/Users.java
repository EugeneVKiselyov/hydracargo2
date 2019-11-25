package ua.com.idltd.user.entity;

//import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @SequenceGenerator( name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    private long user_id;

    private String user_username;

    private String user_password;

    private boolean user_enabled;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public boolean isUser_enabled() {
        return user_enabled;
    }

    public void setUser_enabled(boolean user_enabled) {
        this.user_enabled = user_enabled;
    }
}
