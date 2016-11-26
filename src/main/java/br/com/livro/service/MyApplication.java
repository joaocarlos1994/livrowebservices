package br.com.livro.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;

import org.springframework.stereotype.Component;


/**
 * A <code>MyApplication</code> foi criada para utilizar o
 * Jersey. Ela e um singleton que configura o Jersey.
 * */
@Component
public class MyApplication extends Application {
	
	/**
	 * Este metodo e reponsavel por adicionar funcionalidade(features)
	 * ao Jersey. Neste caso, a classe JettisonFeature faz configuracao
	 * para permitir que o Jersey retorne dados em JSON.
	 * */
/*	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();
		//Para gerar JSON
		singletons.add(new JettisonFeature());
		return singletons;
	}*/
	
	/**
	 * Este metodo configura a propriedade jersey.config.server.provider.packges
	 * que indica ao Jersey em qual pacote estao as classes dos webs services.
	 * Isto e utilizado para fazer o escaneamento das anotacoes
	 * */
	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<>();
		properties.put("jersey.config.server.provider.packages", "br.com.livro");
		return properties;
	}
	
}
