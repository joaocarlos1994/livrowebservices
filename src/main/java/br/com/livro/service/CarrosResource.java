package br.com.livro.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.wrapper.Response;


@Path("/carros")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CarrosResource {
	
	private final CarroService carroService = new CarroService();
	
	@GET
	public List<Carro> get() {
		final List<Carro> carros = carroService.listaCarros();
		return carros;
	}
	
	@GET
	@Path("{id}")
	public Carro get(final @PathParam("id") long id) {
		final Carro carro = carroService.getCarro(id);
		return carro;
	}
	
	@GET
	@Path("/tipo/{tipo}")
	public List<Carro> getByTipo(final @PathParam("tipo") String tipo) {
		final List<Carro> carros = carroService.findByTipo(tipo);
		return carros;
	}
	
	@GET
	@Path("/nome/{nome}")
	public List<Carro> getByNome(final @PathParam("nome") String nome) {
		final List<Carro> carros = carroService.findByName(nome);
		return carros;
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(final @PathParam("id") long id) {
		carroService.delete(id);
		return Response.ok("Carro deletado com sucesso");
	}
	
	@POST
	public Response post(final Carro carro) {
		carroService.save(carro);
		return Response.ok("Carro salvo com sucesso");
	}
	
	@PUT
	public Response put(final Carro carro) {
		carroService.save(carro);
		return Response.ok("Carro atualizado com sucesso");
	}
	
}
