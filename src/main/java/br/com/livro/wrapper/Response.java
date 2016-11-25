package br.com.livro.wrapper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {
	
	private String status;
	private String msg;
	
	public Response() {}
	
	public Response(final String status, final String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	
	public static Response ok(final String mensagem) {
		final Response response = new Response("OK", mensagem);
		return response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}
	
	
	
}
