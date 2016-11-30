package br.com.livro.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseWithURL {
	
	private final String status;
	private final String msg;
	private final String url;
	
	private ResponseWithURL(final String status, final String msg, final String url) {
		super();
		this.status = status;
		this.msg = msg;
		this.url = url;
	}

	public static ResponseWithURL OK(final String string, final String url) {
		final ResponseWithURL r = new ResponseWithURL("OK", string, url);
		return r;
	}

	public static ResponseWithURL Error(final String string) {
		final ResponseWithURL r = new ResponseWithURL("ERROR", string, null);
		return r;
	}

	public String getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public String getUrl() {
		return url;
	}
}
