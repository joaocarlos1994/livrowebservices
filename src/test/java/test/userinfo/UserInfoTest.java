package test.userinfo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import br.com.livro.service.GsonMessageBodyHandler;
import junit.framework.TestCase;

public class UserInfoTest extends TestCase {

	final String URL = "http://localhost:8080/carros/rest/userInfo";

	// Faz login com "livro/livro123"
	public void testUserInfoLoginLivro() {
		// Cria o cliente da API
		final ClientConfig clientConfig = new ClientConfig();
		final Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("livro", "livro123"));
		client.register(GsonMessageBodyHandler.class);
		final WebTarget target = client.target(URL);
		final String s = target.request().get(String.class);
		assertEquals("Voce e um usuario: livro", s);
	}

	// Faz login com "admin/admin123"
	public void testUserInfoLoginAdmin() {
		// Cria o cliente da API
		final ClientConfig clientConfig = new ClientConfig();
		final Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("admin", "admin123"));
		client.register(GsonMessageBodyHandler.class);
		final WebTarget target = client.target(URL);
		final String s = target.request().get(String.class);
		assertEquals("Voce e um administrador: admin", s);
	}

	// Faz login com usuario que nao existe
	public void testUserInfoAcessoNegado() {
		// Cria o cliente da API
		final ClientConfig clientConfig = new ClientConfig();
		final Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("xxx", "yyy"));
		client.register(GsonMessageBodyHandler.class);
		final WebTarget target = client.target(URL);
		final Response response = target.request().get();
		assertEquals(401, response.getStatus());
	}

}
