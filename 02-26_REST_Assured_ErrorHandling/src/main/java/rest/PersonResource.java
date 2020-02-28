package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/02-26_REST_Assured_ErrorHandling",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(
            {
                MediaType.APPLICATION_JSON
            })
    public String demo()
    {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCity(String person)
    {
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO result = FACADE.addPerson(pDTO.getFirstName(), pDTO.getLastName(), pDTO.getPhone());
        return Response.ok(result).build();
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteCity(@PathParam("id") int id)
    {
        PersonDTO result = FACADE.deletePerson(id);
        return "{\"deleted\": \"Person with id: " + result.getId() + "\"}";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPerson(@PathParam("id") int id)
    {
        PersonDTO pDTO = FACADE.getPerson(id);
        if (pDTO != null)
        {
            return GSON.toJson(pDTO);
        }
        else
        {
            return "{\"msg\":\"Operation getPerson " + id + " failed\"}";
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersons()
    {
//        List<PersonDTO> pDTOList = FACADE.getAllPersons();
        PersonsDTO psDTO = FACADE.getAllPersons();
        if (psDTO != null)
        {
            return GSON.toJson(psDTO);
        }
        else
        {
            return "{\"msg\":\"Operation getAllPersons failed\"}";
        }
    }

}
