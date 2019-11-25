package ua.com.idltd.user.entity;

//import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class Roles {

    @Id
    @SequenceGenerator( name = "ROLES_SEQ", sequenceName = "ROLES_SEQ", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "ROLES_SEQ")
    private long rol_id;

    private String rol_role;

    public long getRol_id() {
        return rol_id;
    }

    public void setRol_id(long rol_id) {
        this.rol_id = rol_id;
    }

    public String getRol_role() {
        return rol_role;
    }

    public void setRol_role(String rol_role) {
        this.rol_role = rol_role;
    }

}
