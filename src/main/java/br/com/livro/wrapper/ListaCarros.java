package br.com.livro.wrapper;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.livro.domain.Carro;

@XmlRootElement(name="carros")
public class ListaCarros implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Carro> carros;

	public ListaCarros(final List<Carro> carros) {
		super();
		this.carros = carros;
	}
	
	public ListaCarros() {
	}

	@XmlElement(name="carro")
	public List<Carro> getCarros() {
		return carros;
	}

	@Override
	public String toString() {
		return "ListaCarros [carros=" + carros + "]";
	}
	
	

}
