package hogent.group15.configuration;

import hogent.group15.Challenge;
import hogent.group15.User;
import java.net.URI;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Frederik & Wannes
 */
@Path("user")
@Dependent
public class Users {

    @PersistenceContext
    private EntityManager em;

    @Context
    private Validator validator;
    
    @Path("register")
    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {
	User dbUser = em.find(User.class, user.getEmail());
	if (dbUser != null) {
	    return Response.status(Response.Status.BAD_REQUEST).entity("used").build();
	} else {
	    Set<ConstraintViolation<User>> violations = validator.validate(user);
	    if (!violations.isEmpty()) {
		StringBuilder builder = new StringBuilder();
		violations.stream().map(cv -> cv.getMessage() + " ").forEach(builder::append);
		throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(builder.toString()).build());
	    } else {
		em.persist(user);
		return Response.created(URI.create("/users/me")).build();
	    }
	}
    }
    @Path("login")
    @Transactional
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
	User dbUser = em.find(User.class, user.getEmail());
	if (dbUser != null && dbUser.getPassword()==user.getPassword()) {
            //TODO implement jsonwebtoken
            return Response.accepted().build();
	} else {
            return Response.notAcceptable(null).build();
	}
	
    }
    @Path("{email}/completed")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Challenge> getCompletedChallenges(@PathParam("id") int id){
        User user = em.find(User.class, id);
        
        
        
        return user.getCompletedChallenges();
        
    }
}
