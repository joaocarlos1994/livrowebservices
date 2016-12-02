package br.com.livro.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.ResponseWithURL;
import br.com.livro.domain.UploadService;
import br.com.livro.wrapper.Response;


@Path("/carros")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CarrosResource {
	
	private final CarroService carroService;
	private final UploadService uploadService; 
	
	@Autowired
	public CarrosResource(final CarroService carroService, final UploadService uploadService) {
		super();
		this.carroService = carroService;
		this.uploadService = uploadService;
	}

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
	
/*	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postFoto(final FormDataMultiPart multiPart) throws Exception {
		if (multiPart != null && multiPart.getFields() != null) {
			final Set<String> keys = multiPart.getFields().keySet();
			for (final String key : keys) {
				//Obtem a InputStream para ler o arquivo
				final FormDataBodyPart field = multiPart.getField(key);
				final InputStream in = field.getValueAs(InputStream.class);
				try {
					//Salva o arquivo
					final String fileName = field.getFormDataContentDisposition().getFileName();
					final String path = uploadService.upload(fileName, in);
					System.out.println("Arquivo: " + path);
					return Response.ok("Arquivo recebido com sucesso");
				} catch (final IOException e) {
					e.printStackTrace();
					return Response.Error("Erro ao enviar o arquivo.");
				}
			}
		}
		return Response.ok("Requisição inválida");
	}*/
	
	@POST
	@Path("/toBase64")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String toBase64(final FormDataMultiPart multiPart) {
		if (multiPart != null) {
			final Set<String> keys = multiPart.getFields().keySet();
			for (final String key : keys) {
				try {
					//Obtem a InputStream para ler o arquivo
					final FormDataBodyPart field = multiPart.getField(key);
					final InputStream in = field.getValueAs(InputStream.class);
					final byte[] bytes = IOUtils.toByteArray(in);
					final String base64 = Base64.getEncoder().encodeToString(bytes);
					return base64;
				} catch (final Exception e) {
					e.printStackTrace();
					return "Erro: " + e.getMessage();
				}
			}
		}
		return "Requisição inválida.";
	}
	
	@POST
	@Path("/postFotoBase64")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postFotoBase64(final @FormParam("fileName") String fileName, @FormParam("base64") String base64) {
		if (fileName != null && base64 != null) {
			try {
				//Decode: converte o Base64 para array de bytes
				final byte[] bytes = Base64.getDecoder().decode(base64);
				final InputStream in = new ByteArrayInputStream(bytes);
				// Faz upload (salva o arquivo em uma pasta)
				final String path = uploadService.upload(fileName, in);
				System.out.println("Arquivo: " + path);
				//Ok
				return Response.ok("Arquivo recebido com sucesso");
			} catch (final Exception e) {
				return Response.Error("Erro ao enviar o arquivo.");
			}
		}
		return Response.Error("Requisição inválida.");
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public ResponseWithURL postFoto(final FormDataMultiPart multiPart) {
		final Set<String> keys = multiPart.getFields().keySet();
		for (final String key : keys) {
			
			// Obtem a InputStream para ler o arquivo
			final FormDataBodyPart field = multiPart.getField(key);
			final InputStream in = field.getValueAs(InputStream.class);
			
			
			try {
				final String fileName = field.getFormDataContentDisposition().getFileName();
				final String url = uploadService.upload(fileName, in);
				return ResponseWithURL.OK("Arquivo recebido com sucesso", url);
			} catch (final Exception e) {
				e.printStackTrace();
				return ResponseWithURL.Error("Erro ao enviar o arquivo [" + e.getMessage() +"]");
			}
		}
		return ResponseWithURL.Error("Requisição inválida");
	}
	
}
