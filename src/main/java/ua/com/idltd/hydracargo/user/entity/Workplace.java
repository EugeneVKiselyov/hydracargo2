package ua.com.idltd.hydracargo.user.entity;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;

@Component
@SessionScope
@Entity
@Table(name = "WORKPLACE")
public class Workplace {
    @Id
    @SequenceGenerator( name = "WORKPLACE_SEQ", sequenceName = "WORKPLACE_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "WORKPLACE_SEQ")
    public Long wp_id;

    @Column(name = "LOC_ID", nullable = false)
    public Long loc_id;

    @Column(name = "WP_NAME", nullable = false)
    public String wp_name;

    @Column(name = "WP_TEXTTEMPLATE", nullable = false)
    public String wp_texttemplate;
}
