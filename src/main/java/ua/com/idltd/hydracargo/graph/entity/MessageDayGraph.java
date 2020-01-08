package ua.com.idltd.hydracargo.graph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MessageDayGraph {
    @Id
    @Column(name = "ID", nullable = false)
    public Long id;

    @Column(name = "LABELS", nullable = false)
    public String labels;

    @Column(name = "DATA1", nullable = false)
    public Integer data1;

    @Column(name = "DATA2", nullable = false)
    public Integer data2;

    @Column(name = "DATA3", nullable = false)
    public Integer data3;
}