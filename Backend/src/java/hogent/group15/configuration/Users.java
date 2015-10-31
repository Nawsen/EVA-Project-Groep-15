package hogent.group15.configuration;

import hogent.group15.Challenge;
import hogent.group15.ChallengeCache;
import hogent.group15.DailyChallenges;
import hogent.group15.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.net.URI;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.crypto.spec.SecretKeySpec;
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
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;
import static org.eclipse.persistence.sessions.remote.corba.sun.TransporterHelper.id;

/**
 *
 * @author Frederik & Wannes
 */
@Path("users")
@Dependent
public class Users {

    @PersistenceContext
    private EntityManager em;

    @Context
    private Validator validator;

    @Inject
    private ChallengeCache cache;

    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
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
    public String login(User user) {
        User dbUser = em.find(User.class, user.getEmail());
        if (dbUser != null && User.isExpectedPassword(user.getPassword().toCharArray(), dbUser.getSalt(), dbUser.getEncPassword())) {
            //TODO implement jsonwebtoken
            return getToken();
        } else {
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).build());
        }

    }

    public String getToken() {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("secret");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setId("demo@demo.be")
                                .setIssuedAt(now)
                .signWith(signatureAlgorithm, signingKey);
       
        return "{ \"token\": \"" + builder.compact() + "\" }";
    }

}
