package hogent.group15.configuration;

import hogent.group15.Achievement;
import hogent.group15.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Frederik
 */
@Path("achievements")
public class Achievements {

    @PersistenceContext
    private EntityManager manager;

    @Authorized
    @GET
    @Transactional
    public List<Achievement> getAchievements(@HeaderParam("email") String email) {
	User user = manager.find(User.class, email);
	if (user != null) {
	    return user.getAchievements();
	} else {
	    throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
	}
    }
}
