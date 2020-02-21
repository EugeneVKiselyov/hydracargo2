package ua.com.idltd.hydracargo.request.boxcontent.entity;

import javax.persistence.*;

@Entity
@Table(name = "BOXCONTENT")
public class Boxcontent {

  @Id
  @Column(name = "BC_ID", nullable = false)
  @SequenceGenerator(name = "BOXCONTENT_SEQ", sequenceName = "BOXCONTENT_SEQ", allocationSize = 1, initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOXCONTENT_SEQ")
  public Long bc_id;

  @Column(name = "BOX_ID")
  public Long box_id;

  @Column(name = "BC_NUM")
  public String bc_num;

  @Column(name = "BC_DESCRIPTION")
  public String bc_description;

  @Column(name = "BC_MARK")
  public Long bc_mark;

  @Column(name = "BC_COUNT")
  public Long bc_count;

  @Column(name = "BC_WEIGHT")
  public Double bc_weight;

  @Column(name = "BC_UNITPRICE")
  public Double bc_unitprice;

}
