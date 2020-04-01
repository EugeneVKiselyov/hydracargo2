package ua.com.idltd.hydracargo.trace_cache.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "trace_cache")
public class Trace_cache {

    @Id
    @SequenceGenerator( name = "TRACE_CACHE_SEQ", sequenceName = "TRACE_CACHE_SEQ", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "TRACE_CACHE_SEQ")
    public Long trc_id;

    @Column(name = "TRC_SHIPMENT", nullable = false, updatable = false)
    public String trc_shipment;

    @Column(name = "TRC_TRACENUM", nullable = false, updatable = false)
    public String trc_tracnum;

    @Column(name = "user_name", nullable = false, updatable = false)
    public String user_name;

    @Column(name = "trc_event", nullable = false, updatable = false)
    public String trc_event;

    @Column(name = "trc_eventtime", nullable = false, updatable = false, columnDefinition = "DATETIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date trc_eventtime;

}
