package br.com.livro.domain;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

@Component
public class CarroDao extends HibernateDAO<Carro> {

	public CarroDao() {
		// Informa o tipo de entidade para o Hibernate
		super(Carro.class);
	}

	public Carro getCarroById(final Long id) {
		return super.get(id);
	}

	@SuppressWarnings("unchecked")
	public List<Carro> findByName(final String name) {
		final Query query = getSession().createQuery("from Carro where lower(nome) like lower(?)");
		query.setString(0, "%" + name + "%");
		return Collections.unmodifiableList(query.list());
	}

	@SuppressWarnings("unchecked")
	public <E> List<E> findByTipo(final String tipo) {
		final Query query = getSession().createQuery("from Carro where tipo=?");
		query.setString(0, tipo);
		return (List<E>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Carro> getCarros() {
		final Query query = getSession().createQuery("from Carro");
		return Collections.unmodifiableList(query.list());
	}

	public void save(final Carro carro) {
		super.save(carro);
	}
	
	public boolean delete(final Long id) {
		final Carro carro = get(id);
		delete(carro);
		return true;
	}

}
