package hogent.group15.configuration;

import hogent.group15.User;
import java.net.URI;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Frederik
 */
@Path("users")
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
}
