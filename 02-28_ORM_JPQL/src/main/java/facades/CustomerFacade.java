package facades;

import dtos.CustomerDTO;
import entities.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class CustomerFacade
{

    private static CustomerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CustomerFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CustomerFacade getFacadeExample(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public CustomerDTO addCustomer(String fName, String lName, String phone)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            CustomerDTO cDTO = new CustomerDTO(new Customer(fName, lName, phone, LocalDate.now()));
            em.getTransaction().commit();
            return cDTO;
        }
        catch (Exception ex)
        {
            System.out.println("Failed to persist Customer with provided fields");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    public CustomerDTO deleteCustomer(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            Customer target = em.find(Customer.class, id);
            em.remove(target);
            em.getTransaction().commit();
            return new CustomerDTO(target);
        }
        catch (Exception ex)
        {
            System.out.println("Failed to delete Customer with provided id");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    public CustomerDTO getCustomer(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            Customer c = em.find(Customer.class, id);
            CustomerDTO pDTO = new CustomerDTO(c);
            return pDTO;
        }
        catch (Exception ex)
        {
            System.out.println("Failed to find the specified Customer object.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    public List<CustomerDTO> getAllCustomers()
    {
        EntityManager em = getEntityManager();
        try
        {
            List<CustomerDTO> customerDTOList = new ArrayList<>();
            TypedQuery<Customer> query
                    = em.createQuery("SELECT c FROM Customer c", Customer.class);

            for (Customer c : query.getResultList())
            {
                customerDTOList.add(new CustomerDTO(c));
            }

            return customerDTOList;
        }
        catch (Exception ex)
        {
            System.out.println("Operation getAllCustomers failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }
    

    public CustomerDTO editPerson(CustomerDTO c)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            Customer result = em.find(Customer.class, c.getId());

            result.setFirstName(c.getFirstName());
            result.setLastName(c.getLastName());
            result.setEmail(c.getEmail());

            
//            em.merge(result);
            em.getTransaction().commit();
            return new CustomerDTO(result);
        }
        finally
        {
            em.close();
        }
    }

}
