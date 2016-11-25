package br.com.livro.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Carro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String tipo;
	private String nome;
	private String desc;
	private String urlFoto;
	private String urlVideo;
	private String latitude;
	private String longitude;
	
	public Carro(final Long id, final String tipo, final String nome, final String desc, 
			final String urlFoto, final String urlVideo, final String latitude, final String longitude) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.desc = desc;
		this.urlFoto = urlFoto;
		this.urlVideo = urlVideo;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Carro() {}

	public Long getId() {
		return id;
	}

	public String getTipo() {
		return tipo;
	}

	public String getNome() {
		return nome;
	}

	public String getDesc() {
		return desc;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		final String toString = "Carro [id=" + this.id + ", tipo=" + this.tipo + ", nome=" + nome +
				", desc=" + this.desc + ", urlFoto=" + this.urlFoto + ", urlVideo=" + this.urlVideo +
				", latitude=" + this.latitude + ", longitude=" + this.longitude + "]";
		return toString;
	}

}
