package br.com.livro.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CarroService {

	private final CarroDao carroDao;
	
	@Autowired
	public CarroService(final CarroDao carroDao) {
		super();
		this.carroDao = carroDao;
	}

	public List<Carro> listaCarros() {
		final List<Carro> carros = carroDao.getCarros();
		return carros;
	}

	public Carro getCarro(final Long id) {
		return carroDao.getCarroById(id);
	}

	// Deleta o carro pelo id
	@Transactional(rollbackFor=Exception.class)
	public boolean delete(final Long id) {
		return carroDao.delete(id);
	}

	//Salva ou atualizar o carro
	@Transactional(rollbackFor=Exception.class)
	public boolean save(final Carro carro) {
		carroDao.saveOrUpdate(carro);
		return true;
	}
	
	//Busca pelo nome
	public List<Carro> findByName(final String name) {
		return carroDao.findByName(name);
	}
	
	public <E> List<E> findByTipo(final String tipo) {
		return carroDao.findByTipo(tipo);
	}

}
