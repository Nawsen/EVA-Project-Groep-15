package hogent.group15.configuration;

import hogent.group15.Challenge;
import hogent.group15.ChallengeCache;
import hogent.group15.DailyChallenges;
import hogent.group15.User;
import java.net.URI;
import java.util.ArrayList;
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

    @Inject
    private ChallengeCache cache;

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
        if (dbUser != null && dbUser.getPassword() == user.getPassword()) {
            //TODO implement jsonwebtoken
            return Response.accepted().build();
        } else {
            return Response.notAcceptable(null).build();
        }

    }

    @Path("{email}/completed")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Challenge> getCompletedChallenges(@PathParam("email") String email) {
        User user = em.find(User.class, email);

        return user.getCompletedChallenges();

    }

    @Path("{email}/daily")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Challenge> getDailyChallenges(@PathParam("email") String email) {
        User user = em.find(User.class, email);
        DailyChallenges ch = cache.createDailyChallenges(user);
        return Arrays.asList(new Challenge[]{ch.getFirst(), ch.getSecond(), ch.getThird()});
    }
    
    @Path("{email}/{challengeID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Challenge getChallengeDetails(@PathParam("email") String email, @PathParam("challengeID") int id){
        User user = em.find(User.class, email);
        Challenge challenge = cache.doesUserHasChallenge(user, id);
        
        if (challenge !=null){
            return challenge;
        } else {
            //challenge info mag niet gevraagd worden door user (beter errorcode hier??)
            return null;
        }
    }
    @Path("{email}/accepted")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Challenge getAcceptedChallenge(@PathParam("email") String email){
        User user = em.find(User.class, email);
        return user.getCurrentChallenge();
    }
    
    
    @Path("{email}/{challengeID}/accept")
    @POST
    public Response acceptDailyChallenge(@PathParam("email") String email, @PathParam("challengeID") int id){
        User user = em.find(User.class, email);
        
        //check if user has active challenge
        if (user.getCurrentChallenge().getId()==id){
            return Response.notAcceptable(null).build();
        }
        if (user.getDailyChallenges().getFirst().getId() == id) {
            user.setCurrentChallenge(user.getDailyChallenges().getFirst());
            return Response.ok().build();
        }
        if (user.getDailyChallenges().getSecond().getId() == id) {
            user.setCurrentChallenge(user.getDailyChallenges().getSecond());
            return Response.ok().build();
        }
        if (user.getDailyChallenges().getThird().getId() == id) {
            user.setCurrentChallenge(user.getDailyChallenges().getThird());
            return Response.ok().build();
        }
        
        return Response.notAcceptable(null).build();
    }
    @Path("{email}/{challengeID}/complete")
    @POST
    public Response completeDailyChallenge(@PathParam("email") String email, @PathParam("challengeID") int id){
        //if the challenge is user.currentchallenge then remove it & add to completedchallenge 
        User user = em.find(User.class, email);
        if (user.getCurrentChallenge().getId() == id){
            user.getCompletedChallenges().add(user.getCurrentChallenge());
            user.setCurrentChallenge(null);
            return Response.ok().build();
        }
                
        return Response.notAcceptable(null).build();
    }
}
