package br.com.livro.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider;
import org.glassfish.jersey.server.oauth1.OAuth1ServerFeature;
import org.glassfish.jersey.server.oauth1.OAuth1ServerProperties;
import org.springframework.stereotype.Component;


/**
 * A <code>MyApplication</code> foi criada para utilizar o
 * Jersey. Ela e um singleton que configura o Jersey.
 * */
@Component
public class MyApplication extends Application {
	
	private static final String APP_OWNER = "Livro Lecheta";
	private static final String CONSUMER_KEY = "XXX";
	private static final String CONSUMER_SECRET = "YYY";

	/**
	 * Este metodo e reponsavel por adicionar funcionalidade(features)
	 * ao Jersey. Neste caso, a classe JettisonFeature faz configuracao
	 * para permitir que o Jersey retorne dados em JSON.
	 * */
	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();
		// Suporte ao file upload
		singletons.add(new MultiPartFeature());
		// OAuth Server
		final DefaultOAuth1Provider oauthProvider = new DefaultOAuth1Provider();
		final MultivaluedMap<String, String> attributes = new MultivaluedStringMap();
		oauthProvider.registerConsumer(APP_OWNER, CONSUMER_KEY, CONSUMER_SECRET, attributes);
		singletons.add(new OAuth1ServerFeature(oauthProvider));
		return singletons;
	}
	
	/**
	 * Este metodo configura a propriedade jersey.config.server.provider.packges
	 * que indica ao Jersey em qual pacote estao as classes dos webs services.
	 * Isto e utilizado para fazer o escaneamento das anotacoes
	 * */
	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<>();
		// Configura o pacote para fazer scan das classes com anotacoes REST
		properties.put("jersey.config.server.provider.packages", "br.com.livro");
		// Ativa as URLs /requestToken e /accessToken do Jersey
		properties.put(OAuth1ServerProperties.ENABLE_TOKEN_RESOURCES, Boolean.TRUE);
		return properties;
	}
	
	/**
	 * Este e metodo e necessario para habilitar a seguranca por metodos.
	 * */
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> s = new HashSet<Class<?>>();
		// Seguranca por anotacao - JSR-250
		s.add(RolesAllowedDynamicFeature.class);
		return s;
	}
	
}
