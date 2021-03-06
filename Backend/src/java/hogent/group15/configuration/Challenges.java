package hogent.group15.configuration;

import hogent.group15.Achievement;
import hogent.group15.AchievementGenerator;
import hogent.group15.Challenge;
import hogent.group15.ChallengeCache;
import hogent.group15.CompletedChallenge;
import hogent.group15.DailyChallenges;
import hogent.group15.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
@Authorized
public class Challenges {

    @PersistenceContext
    private EntityManager em;

    @Context
    private Validator validator;

    @Inject
    private ChallengeCache cache;

    @Inject
    private AchievementGenerator achievements;

    @Path("completed")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Challenge> getCompletedChallenges(@HeaderParam("email") String email) {
	User user = em.find(User.class, email);
	List<Challenge> challenges = new ArrayList<>();
	
	for (CompletedChallenge completed : user.getCompletedChallenges()) {
	    challenges.add(completed.getChallenge());
	}
	
	return challenges;
    }

    @Path("daily")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<DailyChallenges> getDailyChallenges(@HeaderParam("email") String email, @QueryParam("days") @DefaultValue("1") int days) {
	List<DailyChallenges> challenges = getDailyChallengesDetailed(email, days);
	challenges.forEach(dc -> dc.setDetailed(false));
	return challenges;
    }

    @Path("daily/detailed")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<DailyChallenges> getDailyChallengesDetailed(@HeaderParam("email") String email, @QueryParam("days") @DefaultValue("1") int days) {
	User user = em.find(User.class, email);
	List<DailyChallenges> original = cache.createDailyChallenges(user, days);
	List<DailyChallenges> ch = new ArrayList<>(original);
	ch.removeAll(user.getDailyChallenges());
	user.getDailyChallenges().addAll(ch);
	em.merge(user);
	return original;
    }

    @Path("{challengeID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Challenge getChallengeDetails(@HeaderParam("email") String email, @PathParam("challengeID") int id) {
	User user = em.find(User.class, email);
	Challenge challenge = cache.getChallenge(id);
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

	Challenge challenge = cache.getChallenge(id);
	if (challenge != null) {
	    user.setCurrentChallenge(challenge);
	    em.merge(user);
	    achievements.generateAchievements(user, Achievement.AchievementType.ACCEPTED);
	    return Response.ok().build();
	}

	return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("fail")
    @PUT
    @Transactional
    public Response failDailyChallenge(@HeaderParam("email") String email) {
	User user = em.find(User.class, email);
	if (user.getCurrentChallenge() == null) {
	    return Response.notModified().build();
	}

	user.setCurrentChallenge(null);
	achievements.generateAchievements(user, Achievement.AchievementType.CANCELLED);
	return Response.noContent().build();
    }

    @Path("complete")
    @PUT
    @Transactional
    public Response completeDailyChallenge(@HeaderParam("email") String email) {
	//if the challenge is user.currentchallenge then remove it & add to completedchallenge 
	User user = em.find(User.class, email);
	if (user.getCurrentChallenge() == null) {
	    return Response.notModified().build();
	}

	if (!user.getCompletedChallenges().stream().map(CompletedChallenge::getChallenge).anyMatch(c -> c.equals(user.getCurrentChallenge()))) {
	    user.getCompletedChallenges().add(new CompletedChallenge(user.getCurrentChallenge()));
	}
	
	user.setCurrentChallenge(null);
	achievements.generateAchievements(user, Achievement.AchievementType.COMPLETED);
	em.merge(user);
	return Response.noContent().build();
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

    private boolean isAdmin(String email) {
	User user = em.find(User.class, email);
	return user == null ? false : user.getRole() == User.Role.ADMIN;
    }

    @Path("{id}/edit")
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editChallenge(@PathParam("id") int id, Challenge challenge, @HeaderParam("email") String email) {
	isAdmin(email);
	Challenge c = em.find(Challenge.class, id);

	if (c == null) {
	    return Response.notModified().build();
	}

	if (challenge.getDescription() != null && !challenge.getDescription().isEmpty()) {
	    c.setDescription(challenge.getDescription());
	}

	if (challenge.getDifficulty() != null) {
	    c.setDifficulty(challenge.getDifficulty());
	}

	if (challenge.getTitle() != null && !challenge.getTitle().isEmpty()) {
	    c.setTitle(challenge.getTitle());
	}

	if (challenge.getImageUrl() != null && !challenge.getImageUrl().isEmpty()) {
	    c.setImageUrl(challenge.getImageUrl());
	}

	em.merge(c);
	return Response.noContent().build();
    }

    @Path("{id}/remove")
    @GET
    @Transactional
    public Response removeChallenge(@PathParam("id") int id, @HeaderParam("email") String email) {
	isAdmin(email);
	Challenge c = em.find(Challenge.class, id);
	if (c != null) {
	    c.setUsable(false);
	    cache.removeFromCache(c);
	    em.merge(c);
	    return Response.noContent().build();
	}

	return Response.notModified().build();
    }
}
