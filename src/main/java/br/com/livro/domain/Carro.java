package br.com.livro.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Carro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false, length=50)
	private String tipo;
	@Column(nullable=false, length=50)
	private String nome;
	@Column(name="descricao", nullable=false, length=50)
	private String desc;
	@Column(nullable=false, length=50)
	private String urlFoto;
	@Column(nullable=false, length=50)
	private String urlVideo;
	@Column(nullable=false, length=50)
	private String latitude;
	@Column(nullable=false, length=50)
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
