
package tester;

import entity.Address;
import entity.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Tester {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Customer c1 = new Customer("Hans", "Hansen");
        Customer c2 = new Customer("Peter", "Petersen");
        
        Address a1 = new Address("Galevej 12", "Lyngby");
        Address a2 = new Address("Galevej 25", "Lyngby");
        Address a3 = new Address("Galevej 37", "Lyngby");
        
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            em.persist(a3);
            
//            c1.setAddress(a1);
//            c2.setAddress(a3);

            List<Address> addresses = new ArrayList<>();
            
            addresses.add(a1);
            addresses.add(a3);
            c1.setAddresses(addresses);
            
            em.getTransaction().commit();
            System.out.println(c1.getId());
            System.out.println(c2.getId());

        } finally {
            em.close();
        }
        
        Persistence.generateSchema("pu", null);

    }
    
}
