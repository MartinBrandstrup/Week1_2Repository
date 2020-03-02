package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import static javax.persistence.TemporalType.DATE;

/**
 *
 * @author Brandstrup
 */
@Entity
@NamedQuery(name = "Customer.deleteAllRows", query = "DELETE from Customer")
public class Customer implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName, lastName, email;
    
    List<Order> orders = new ArrayList();
    
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

    public Customer()
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
    public Customer(String firstName, String lastName, String email, Date created, Date lastEdited)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.created = created;
        this.lastEdited = lastEdited;
    }

    /**
     * Creates a Person entity with a LocalDate. Used when persisting to DB.
     *
     * @param date LocalDate, use LocalDate.now() for the current time and use
     * LocalDate.of(2001, Month.MARCH, 13) for instance for specific dates.
     */
    public Customer(String firstName, String lastName, String email, LocalDate date)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.created = convertToDateViaInstant(date);
        this.lastEdited = convertToDateViaInstant(date);
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Retrieves a Customer's lastEdited as a LocalDate.
     *
     * @return a LocalDateTime.
     */
    public LocalDateTime getlastEditedLocalDate()
    {
        return convertToLocalDateTimeViaInstant(lastEdited);
    }

    /**
     * Retrieves a Customer's lastEdited as a java.util.Date.
     *
     * @return a Date.
     */
    public Date getlastEditedDate()
    {
        return lastEdited;
    }

    /**
     * Retrieves a Customer's created as a LocalDate.
     *
     * @return a LocalDateTime.
     */
    public LocalDateTime getCreatedLocalDate()
    {
        return convertToLocalDateTimeViaInstant(created);
    }

    /**
     * Retrieves a Customer's created as a java.util.Date.
     *
     * @return a Date.
     */
    public Date getCreatedDate()
    {
        return created;
    }

    /**
     * Updates a Customer's lastEdited using a LocalDate format.
     *
     * @param lastEdited LocalDate, use LocalDate.now() for the current time and
     * use LocalDate.of(2001, Month.MARCH, 13) for instance for specific dates.
     */
    public void setLastEdited(LocalDate lastEdited)
    {
        this.lastEdited = convertToDateViaInstant(lastEdited);
    }

    /**
     * Updates a Customer's lastEdited using a java.util.Date format.
     *
     * @param lastEdited A regular java.util.Date, which is the SQL's preferred
     * format.
     */
    public void setLastEdited(Date lastEdited)
    {
        this.lastEdited = lastEdited;
    }

    @Override
    public String toString()
    {
        return "Person{" + "id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + ", created="
                + created + ", lastEdited=" + lastEdited + '}';
    }

}
