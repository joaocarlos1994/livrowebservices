package br.com.livro.domain;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateDAO<T> {
	
	protected Class<T> clazz;
	protected Session session;
	
	@Autowired
	private SessionFactory sessionFactory;

	
	public HibernateDAO(final Class<T> clazz) {
		super();
		this.clazz = clazz;
	}
	
	public void delete(final T entity) {
		getSession().delete(entity);
	}
	
	public void update(final T entity) {
		getSession().update(entity);
	}
	
	public void save(final T entity) {
		getSession().save(entity);
	}
	
	public void saveOrUpdate(final T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public T load(final Serializable id) {
		return (T) getSession().load(this.clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public T get(final Serializable id) {
		return (T) getSession().get(this.clazz, id);
	}
	
	protected Query createQuery(final String query) {
		return getSession().createQuery(query);
	}
	
	protected Criteria createCriteria() {
		return getSession().createCriteria(this.clazz);
	}
	
	public Session getSession() {
		if (sessionFactory != null) {
			session = sessionFactory.getCurrentSession();
		}
		if (session == null) {
			throw new RuntimeException("Hibernate session is null");
		}
		return session;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
