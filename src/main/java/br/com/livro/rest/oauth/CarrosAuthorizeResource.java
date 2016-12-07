package br.com.livro.rest.oauth;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider.Token;

/**
 * A <code>CarrosAuthorizeResource</code> e reponsavel por autenticar o usuario
 * na aplicacao.
 */
@Path("/authorize")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class CarrosAuthorizeResource {

	// Provedor do OAuth registrado no MyApplication
	@Context
	private DefaultOAuth1Provider provider;

	@GET
	public Response get(@QueryParam("oauth_token") final String oauth_token) throws URISyntaxException {
		if (oauth_token == null) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}

		java.net.URI uri = new java.net.URI(String.format("../authorize.jsp?oauth_token=%s", oauth_token));
		return Response.temporaryRedirect(uri).build();
	}

	@POST
	public Response post(final @FormParam("oauth_token") String token, 
						 final @FormParam("nome") String nome,
						 final @FormParam("login") String login, 
						 final @FormParam("senha") String senha) throws URISyntaxException {
		// Token de requisicao
		final Token requestToken = provider.getRequestToken(token);
		// Perfil do usuario
		final Set<String> roles = new HashSet<>();
		
		if ("livro".equals(login) && "livro123".equals(senha)) {
			roles.add("user");
		} else if ("admin".equals(login) && "admin123".equals(senha)) {
			roles.add("admin");
		} else {
			throw new NotAuthorizedException("Login incorreto");
		}
		
		final Principal userPrincipal = new Principal() {
			@Override
			public String getName() {
				return nome;
			}
		};
		
		// Cria o codigo verificador
		final String verifier = provider.authorizeToken(requestToken, userPrincipal, roles);
		// Url de retorno (callback) com parametros
		String callbackUrl = requestToken.getCallbackUrl();
		callbackUrl += String.format("?oauth_verifier=%s&oauth_token=%s", verifier, token);
		
		// Redirect
		final URI uri = new URI(callbackUrl.toString());
		
		return Response.status(Status.FOUND).location(uri).build();
	}
}
