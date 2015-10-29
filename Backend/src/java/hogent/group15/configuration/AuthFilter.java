/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hogent.group15.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author wannes
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {
    
    private static final String KEY = "secret";
    
    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            if (requestContext.getUriInfo().getAbsolutePath().toString().contains("login") || requestContext.getUriInfo().getAbsolutePath().toString().contains("register")){
                return;
            }
            if (requestContext.getHeaderString("Authorization") != null){
                //we need to split off the non usefull things from the header 
                //in this case we need to chew off "Bearer "
                String code = requestContext.getHeaderString("Authorization").split(" ")[1];
                Claims claim = Jwts.parser().setSigningKey(KEY).parseClaimsJws(code).getBody();
                //after we check if the claim is correct lets set the userID into the header
                //with this we can identifie the user in class User.java
                requestContext.getHeaders().add("email", claim.getId());
            } else {
                throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).build());
            }

            
                

        } catch (SignatureException e) {
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).build());
            
        } catch (Exception ex){
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).build());
        }

    }

}
