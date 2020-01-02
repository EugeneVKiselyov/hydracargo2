package ua.com.idltd.hydracargo.bot.telegram.web.entity.lists;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
public class RolesList {
  @Id
  @Column(name = "ROL_ID", nullable = false)
  public Long rol_id;

  @Column(name = "ROL_ROLE")
  public String rol_role;

  @Column(name = "ROL_SHEDULER_PROCEDURE")
  public String rol_sheduler_procedure;
}
