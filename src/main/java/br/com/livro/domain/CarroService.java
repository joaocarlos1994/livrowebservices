package br.com.livro.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarroService {

	private final CarroDao carroDao = new CarroDao();

	public List<Carro> listaCarros() {
		try {
			final List<Carro> carros = carroDao.getCarros();
			return Collections.unmodifiableList(carros);
		} catch (SQLException e) {
			e.printStackTrace();
			// return type Generic
			return new ArrayList<>();
		}
	}

	public Carro getCarro(final Long id) {
		try {
			final Carro carro = carroDao.getCarroById(id);
			return carro;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean delete(final Long id) {
		try {
			return carroDao.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean save(final Carro carro) {
		try {
			carroDao.save(carro);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Carro> findByName(final String name) {
		try {
			final List<Carro> carros = carroDao.findByName(name);
			return Collections.unmodifiableList(carros);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> findByTipo(final String tipo) {
		try {
			final List<Carro> carros = carroDao.findByTipo(tipo);
			return (List<E>) carros;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
