package hogent.group15.configuration;

import hogent.group15.Challenge;
import hogent.group15.ChallengeCache;
import hogent.group15.DailyChallenges;
import hogent.group15.User;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author wannes
 */
@Path("challenges")
@Dependent
public class Challenges {

    @PersistenceContext
    private EntityManager em;
    @Context
    private Validator validator;
    @Inject
    private ChallengeCache cache;

    @Path("completed")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Challenge> getCompletedChallenges(@HeaderParam("email") String email) {
	User user = em.find(User.class, email);
	return user.getCompletedChallenges();
    }

    @Path("daily")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Challenge> getDailyChallenges(@HeaderParam("email") String email) {
	User user = em.find(User.class, email);
	DailyChallenges ch = cache.createDailyChallenges(user);
	em.persist(user);
	return Arrays.asList(new Challenge[]{ch.getFirst(), ch.getSecond(), ch.getThird()});
    }

    @Path("{challengeID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Challenge getChallengeDetails(@HeaderParam("email") String email, @PathParam("challengeID") int id) {
	User user = em.find(User.class, email);
	Challenge challenge = cache.getChallenge(user, id);
	if (challenge != null) {
	    return challenge;
	} else {
	    throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
	}
    }

    @Path("accepted")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Challenge getAcceptedChallenge(@HeaderParam("email") String email) {
	User user = em.find(User.class, email);
	return user.getCurrentChallenge();
    }

    @Path("{challengeID}/accept")
    @PUT
    @Transactional
    public Response acceptDailyChallenge(@HeaderParam("email") String email, @PathParam("challengeID") int id) {
	User user = em.find(User.class, email);
	//check if user has active challenge
	if (user.getCurrentChallenge() != null && user.getCurrentChallenge().getId() == id) {
	    return Response.status(Response.Status.BAD_REQUEST).build();
	}
	if (user.getDailyChallenges().getFirst().getId() == id) {
	    user.setCurrentChallenge(user.getDailyChallenges().getFirst());
	    em.persist(user);
	    return Response.ok().build();
	}
	if (user.getDailyChallenges().getSecond().getId() == id) {
	    user.setCurrentChallenge(user.getDailyChallenges().getSecond());
            em.persist(user);
	    return Response.ok().build();
	}
	if (user.getDailyChallenges().getThird().getId() == id) {
	    user.setCurrentChallenge(user.getDailyChallenges().getThird());
	    em.persist(user);
	    return Response.ok().build();
	}
	return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("complete")
    @PUT
    @Transactional
    public Response completeDailyChallenge(@HeaderParam("email") String email) {
	//if the challenge is user.currentchallenge then remove it & add to completedchallenge 
	User user = em.find(User.class, email);
	if (user.getCurrentChallenge() == null) {
	    throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
	}
	user.getCompletedChallenges().add(user.getCurrentChallenge());
	user.setCurrentChallenge(null);
	em.persist(user);
	return Response.ok().build();
    }

    @Path("add")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addChallenge(Challenge challenge) {
	Challenge dbCh = em.find(Challenge.class, challenge.getId());
	if (dbCh != null) {
	    return Response.status(Response.Status.BAD_REQUEST).entity("used").build();
	} else {
	    Set<ConstraintViolation<Challenge>> violations = validator.validate(challenge);
	    if (!violations.isEmpty()) {
		StringBuilder builder = new StringBuilder();
		violations.stream().map(cv -> cv.getMessage() + " ").forEach(builder::append);
		throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(builder.toString()).build());
	    } else {
		em.persist(challenge);
		cache.addToCache(challenge);

		return Response.status(Response.Status.CREATED).build();
	    }
	}
    }

}
