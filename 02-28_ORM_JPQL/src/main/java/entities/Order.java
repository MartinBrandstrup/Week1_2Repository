/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Brandstrup
 */
@Entity
@NamedQuery(name = "Order.deleteAllRows", query = "DELETE from Order")
public class Order implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade ={CascadeType.MERGE, CascadeType.PERSIST})
    List<OrderLine> orderLines = new ArrayList();

    @Temporal(TemporalType.DATE)
    private Date created;
    @Temporal(TemporalType.DATE)
    private Date lastEdited;
    
    private Date convertToDateViaInstant(LocalDate dateToConvert)
    {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant().plusSeconds(86400));
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert)
    {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public Order()
    {
    }
    
    /**
     * Creates a Person entity with a Date. Mostly used when retrieving from the
     * DB.
     *
     * @param created A regular java.util.Date, which is the SQL's preferred
     * format.
     * @param lastEdited A regular java.util.Date, which is the SQL's preferred
     * format.
     */
    public Order(Customer customer, List<OrderLine> orderLines, Date created, Date lastEdited)
    {
        this.customer = customer;
        this.orderLines = orderLines;
        this.created = created;
        this.lastEdited = lastEdited;
    }

    /**
     * Creates a Person entity with a LocalDate. Used when persisting to DB.
     *
     * @param date LocalDate, use LocalDate.now() for the current time and use
     * LocalDate.of(2001, Month.MARCH, 13) for instance for specific dates.
     */
    public Order(Customer customer, List<OrderLine> orderLines, LocalDate date)
    {
        this.customer = customer;
        this.orderLines = orderLines;
        this.created = convertToDateViaInstant(date);
        this.lastEdited = convertToDateViaInstant(date);
    }
}
