package facades;

import dtos.PersonDTO;
import entities.Person;
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
public class PersonFacade implements IPersonFacade
{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            PersonDTO pDTO = new PersonDTO(new Person(fName, lName, phone, LocalDate.now()));
            em.getTransaction().commit();
            return pDTO;
        }
        catch (Exception ex)
        {
            System.out.println("Failed to persist Person with provided fields");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            Person target = em.find(Person.class, id);
            em.remove(target);
            em.getTransaction().commit();
            return new PersonDTO(target);
        }
        catch (Exception ex)
        {
            System.out.println("Failed to delete Person with provided id");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO getPerson(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            Person p = em.find(Person.class, id);
            PersonDTO pDTO = new PersonDTO(p);
            return pDTO;
        }
        catch (Exception ex)
        {
            System.out.println("Failed to find the specified Person object.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getAllPersons()
    {
        EntityManager em = getEntityManager();
        try
        {
            List<PersonDTO> personDTOList = new ArrayList<>();
            TypedQuery<Person> query
                    = em.createQuery("SELECT p FROM Person p", Person.class);

            for (Person p : query.getResultList())
            {
                personDTOList.add(new PersonDTO(p));
            }

            return personDTOList;
        }
        catch (Exception ex)
        {
            System.out.println("Operation getAllPersons failed.");
            ex.printStackTrace();
            return null;
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();
            Person result = em.find(Person.class, p.getId());

            result.setFirstName(p.getFirstName());
            result.setLastName(p.getLastName());
            result.setPhone(p.getPhone());

            
//            em.merge(result);
            em.getTransaction().commit();
            return new PersonDTO(result);
        }
        finally
        {
            em.close();
        }
    }

}
