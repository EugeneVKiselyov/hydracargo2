package ua.com.idltd.hydracargo.utils.filehandler.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "LOAD_PACKING")
public class LoadPacking {
    @Id
    @Column(name = "LP_ID", nullable = false)
    @SequenceGenerator(name = "LOAD_PACKING_SEQ", sequenceName = "LOAD_PACKING_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAD_PACKING_SEQ")
    private long lp_id;
    @Column(name = "LT_PART")
    private Long lt_part;
    @Column(name = "LP_CREATE_DATE")
    private Date lp_create_date;
    @Column(name = "LP_CREATE_USER")
    private String lp_create_user;
    @Column(name = "LP_BOX_NUM")
    private String lp_box_num;
    @Column(name = "LP_BOX_DESCRIPTION")
    private String lp_box_description;
    @Column(name = "LP_BOX_WEIGHT")
    private Double lp_box_weight;
    @Column(name = "LP_BOX_LENGHT")
    private Double lp_box_lenght;
    @Column(name = "LP_BOX_WIDTH")
    private Double lp_box_width;
    @Column(name = "LP_BOX_HEIGHT")
    private Double lp_box_height;
    @Column(name = "LP_BOX_VOLUME_WEIGHT")
    private Double lp_box_volume_weight;
    @Column(name = "LP_BC_DESCRIPTION")
    private String lp_bc_description;
    @Column(name = "LP_BC_COUNT")
    private Long lp_bc_count;
    @Column(name = "LP_BC_UNITPRICE")
    private Double lp_bc_unitprice;
    @Column(name = "LP_TOTALCOST")
    private Double lp_totalcost;
    @Column(name = "LP_BC_MARK")
    private Long lp_bc_mark;
    @Column(name = "LP_BOX_INSHIPMENT")
    private String lp_box_inshipment;
    @Column(name = "LP_BOX_CARPLATE")
    private String lp_box_carplate;
    @Column(name = "LP_FPG_NAME")
    private String lp_fpg_name;
    @Column(name = "LP_FTPM_NAME")
    private String lp_ftpm_name;
    @Column(name = "LP_FIT_NAME")
    private String lp_fit_name;

    public long getLp_id() {
        return lp_id;
    }

    public void setLp_id(long lp_id) {
        this.lp_id = lp_id;
    }

    public Long getLt_part() {
        return lt_part;
    }

    public void setLt_part(Long lt_part) {
        this.lt_part = lt_part;
    }

    public Date getLp_create_date() {
        return lp_create_date;
    }

    public void setLp_create_date(Date lp_create_date) {
        this.lp_create_date = lp_create_date;
    }

    public String getLp_create_user() {
        return lp_create_user;
    }

    public void setLp_create_user(String lp_create_user) {
        this.lp_create_user = lp_create_user;
    }

    public String getLp_box_num() {
        return lp_box_num;
    }

    public void setLp_box_num(String lp_box_num) {
        this.lp_box_num = lp_box_num;
    }

    public String getLp_box_description() {
        return lp_box_description;
    }

    public void setLp_box_description(String lp_box_description) {
        this.lp_box_description = lp_box_description;
    }

    public Double getLp_box_weight() {
        return lp_box_weight;
    }

    public void setLp_box_weight(Double lp_box_weight) {
        this.lp_box_weight = lp_box_weight;
    }

    public Double getLp_box_lenght() {
        return lp_box_lenght;
    }

    public void setLp_box_lenght(Double lp_box_lenght) {
        this.lp_box_lenght = lp_box_lenght;
    }

    public Double getLp_box_width() {
        return lp_box_width;
    }

    public void setLp_box_width(Double lp_box_width) {
        this.lp_box_width = lp_box_width;
    }

    public Double getLp_box_height() {
        return lp_box_height;
    }

    public void setLp_box_height(Double lp_box_height) {
        this.lp_box_height = lp_box_height;
    }

    public Double getLp_box_volume_weight() {
        return lp_box_volume_weight;
    }

    public void setLp_box_volume_weight(Double lp_box_volume_weight) {
        this.lp_box_volume_weight = lp_box_volume_weight;
    }

    public String getLp_bc_description() {
        return lp_bc_description;
    }

    public void setLp_bc_description(String lp_bc_description) {
        this.lp_bc_description = lp_bc_description;
    }

    public Long getLp_bc_count() {
        return lp_bc_count;
    }

    public void setLp_bc_count(Long lp_bc_count) {
        this.lp_bc_count = lp_bc_count;
    }

    public Double getLp_bc_unitprice() {
        return lp_bc_unitprice;
    }

    public void setLp_bc_unitprice(Double lp_bc_unitprice) {
        this.lp_bc_unitprice = lp_bc_unitprice;
    }

    public Double getLp_totalcost() {
        return lp_totalcost;
    }

    public void setLp_totalcost(Double lp_totalcost) {
        this.lp_totalcost = lp_totalcost;
    }

    public Long getLp_bc_mark() {
        return lp_bc_mark;
    }

    public void setLp_bc_mark(Long lp_bc_mark) {
        this.lp_bc_mark = lp_bc_mark;
    }

    public String getLp_box_inshipment() {
        return lp_box_inshipment;
    }

    public void setLp_box_inshipment(String lp_box_inshipment) {
        this.lp_box_inshipment = lp_box_inshipment;
    }

    public String getLp_box_carplate() {
        return lp_box_carplate;
    }

    public void setLp_box_carplate(String lp_box_carplate) {
        this.lp_box_carplate = lp_box_carplate;
    }

    public String getLp_fpg_name() {
        return lp_fpg_name;
    }

    public void setLp_fpg_name(String lp_fpg_name) {
        this.lp_fpg_name = lp_fpg_name;
    }

    public String getLp_ftpm_name() {
        return lp_ftpm_name;
    }

    public void setLp_ftpm_name(String lp_ftpm_name) {
        this.lp_ftpm_name = lp_ftpm_name;
    }

    public String getLp_fit_name() {
        return lp_fit_name;
    }

    public void setLp_fit_name(String lp_fit_name) {
        this.lp_fit_name = lp_fit_name;
    }
}
