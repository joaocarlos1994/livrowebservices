package br.com.livro.service;

import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("userInfo")
public class UserInfoResource {
	
	@Context
	private SecurityContext securityContext;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String userInfo() {
		
		if (this.securityContext == null) {
			throw new NotAuthorizedException("Acesso nao autorizado");
		}
		
		final String name = securityContext.getUserPrincipal().getName();
		
		if (securityContext.isUserInRole("admin")) {
			return "Voce e um administrador: " + name; 
		}
		
		if (securityContext.isUserInRole("user")) {
			return "Voce e um usuario: " + name; 
		}
		return "Nenhum dos dois";
	}
	
}
