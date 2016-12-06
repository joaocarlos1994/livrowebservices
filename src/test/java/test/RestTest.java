package test;

import java.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import br.com.livro.domain.Carro;
import br.com.livro.domain.ResponseWithURL;
import br.com.livro.service.GsonMessageBodyHandler;
import junit.framework.TestCase;

public class RestTest extends TestCase {

	public void testGetCarroId() {

		// Cria o cliente da API
		final ClientConfig clientConfig = new ClientConfig();
		final Client client = ClientBuilder.newClient(clientConfig);
		// Autentica com o usuario livro.
		client.register(HttpAuthenticationFeature.basic("livro", "livro123"));
		client.register(GsonMessageBodyHandler.class);
		final String URL = "http://localhost:8080/carros/rest";
		// Cria a requisicao com o "caminho"
		final WebTarget target = client.target(URL).path("/carros/11");
		final Response response = target.request(MediaType.APPLICATION_JSON).get();
		// Status HTTP de retorno
		final int status = response.getStatus();
		// Le um Carro (converte diretamente da string JSON)
		final Carro c = response.readEntity(Carro.class);
		assertEquals(200, status);
		assertEquals("Ferrari FF", c.getNome());

	}

	/*
	 * public void testDeleteCarroId() {
	 * 
	 * // Cria o cliente da API final ClientConfig clientConfig = new
	 * ClientConfig(); final Client client =
	 * ClientBuilder.newClient(clientConfig); // Autentica com o usuario livro.
	 * client.register(HttpAuthenticationFeature.basic("admin", "admin123"));
	 * client.register(GsonMessageBodyHandler.class); final String URL =
	 * "http://localhost:8080/carros/rest"; // Cria a requisicao com o "caminho"
	 * final WebTarget target = client.target(URL).path("/carros/39"); // Faz a
	 * requisicao do tipo GET solicitando um JSON como resposta final Response
	 * response = target.request(MediaType.APPLICATION_JSON).delete(); // Status
	 * HTTP de retorno final int status = response.getStatus(); // Valida se a
	 * req assertEquals(200, status); // Le um Carro (converte diretamente da
	 * string JSON) final br.com.livro.wrapper.Response s =
	 * response.readEntity(br.com.livro.wrapper.Response.class);
	 * assertEquals("OK", s.getStatus()); assertEquals(
	 * "Carro deletado com sucesso", s.getMsg());
	 * 
	 * }
	 */

	public void testPostFormParams() {

		// Cria o cliente da API
		final ClientConfig clientConfig = new ClientConfig();
		final Client client = ClientBuilder.newClient(clientConfig);
		// Autentica com o usuario livro.
		client.register(HttpAuthenticationFeature.basic("admin", "admin123"));
		client.register(GsonMessageBodyHandler.class);
		// Cria os parametros do formulario
		final String base64 = Base64.getEncoder().encodeToString("Ricado Lecheta".getBytes());
		final Form form = new Form();
		form.param("fileName", "nome.xt");
		form.param("base64", base64);
		final String URL = "http://localhost:8080/carros/rest";
		// Cria a requisicao com o "caminho"
		final WebTarget target = client.target(URL).path("/carros/postFotoBase64");
		// Faz a requisicao do tipo GET solicitando um JSON como resposta
		final Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		final Response response = target.request(MediaType.APPLICATION_JSON).post(entity);
		// Status HTTP de retorno
		final int status = response.getStatus();
		// Valida se a req
		assertEquals(200, status);
		// Le um Carro (converte diretamente da string JSON)
		final ResponseWithURL s = response.readEntity(ResponseWithURL.class);
		assertEquals("OK", s.getStatus());
		assertEquals("Arquivo recebido com sucesso", s.getMsg());

	}

	public void testSalvarNovoCarro() {

		// Cria o cliente da API
		final ClientConfig clientConfig = new ClientConfig();
		final Client client = ClientBuilder.newClient(clientConfig);
		// Autentica com o usuario livro.
		client.register(HttpAuthenticationFeature.basic("admin", "admin123"));
		client.register(GsonMessageBodyHandler.class);
		// Cria os parametros do formulario
		final Carro c = new Carro();
		c.setNome("Novo Carro");
		final String URL = "http://localhost:8080/carros/rest";
		// Cria a requisicao com o "caminho"
		final WebTarget target = client.target(URL).path("/carros/");
		// Faz a requisicao do tipo GET solicitando um JSON como resposta
		final Entity<Carro> entity = Entity.entity(c, MediaType.APPLICATION_JSON);
		final Response response = target.request(MediaType.APPLICATION_JSON).post(entity, Response.class);
		// Status HTTP de retorno
		final int status = response.getStatus();
		// Valida se a requisicao foi Ok
		assertEquals(200, status);
		// Le um Carro (converte diretamente da string JSON)
		final br.com.livro.wrapper.Response s = response.readEntity(br.com.livro.wrapper.Response.class);
		assertEquals("OK", s.getStatus());
		assertEquals("Carro salvo com sucesso", s.getMsg());

	}

}
