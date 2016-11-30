package br.com.livro.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.StorageObject;
import com.google.common.collect.ImmutableList;

public class CloudStorageUtil {

	private Storage client;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final String applicationName;
	private static HttpTransport httpTransport;

	public CloudStorageUtil(final String applicationName) {
		super();
		this.applicationName = applicationName;
	}

	// Conecta no Google APIs
	public void connect(final String accountId, final File p12File) throws Exception {
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		final Credential credential = authorize(accountId, p12File);
		client = new Storage.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(applicationName).build();
	}

	private Credential authorize(final String accountId, final File p12File) throws Exception {

		final Set<String> scopes = new HashSet<String>();

		scopes.add(StorageScopes.DEVSTORAGE_FULL_CONTROL);
		scopes.add(StorageScopes.DEVSTORAGE_READ_ONLY);
		scopes.add(StorageScopes.DEVSTORAGE_READ_WRITE);

		// Autoriza a aplicação
		final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		final GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(accountId)
				.setServiceAccountPrivateKeyFromP12File(p12File).setServiceAccountScopes(scopes).build();

		return credential;
	}

	// Retorna um bucket
	public Bucket getBucket(String bucketName) throws IOException {
		Storage.Buckets.Get getBucket = client.buckets().get(bucketName);
		getBucket.setProjection("full");
		Bucket bucket = getBucket.execute();
		return bucket;
	}

	// Lista os arquivos do bucket
	public List<StorageObject> getBucketFiles(final Bucket bucket) throws IOException {
		final Storage.Objects.List listObjects = client.objects().list(bucket.getName());
		com.google.api.services.storage.model.Objects objects;
		final List<StorageObject> all = new ArrayList<StorageObject>();
		do {
			objects = listObjects.execute();
			final List<StorageObject> items = objects.getItems();
			if (null == items) {
				return all;
			}
			// Adiciona os arquivos
			all.addAll(items);
			listObjects.setPageToken(objects.getNextPageToken());
		} while (null != objects.getNextPageToken());
		return all;
	}

	// Faz upload do arquivo (INSERT) no Storage
	public StorageObject upload(final String bucketName, final File file, final String contentType,
			final String projectId) throws IOException {

		if (client == null) {
			throw new IOException("Cloud Storage API não está conectada");
		}
		if (file == null || !file.exists() || !file.isFile()) {
			throw new FileNotFoundException("Arquivo não encontrado");
		}

		// Nome do arquivo
		final String fileName = file.getName();
		// Lê o arquivo
		final InputStream inputStream = new FileInputStream(file);
		final long byteCount = file.length();
		final InputStreamContent mediaContent = new InputStreamContent(contentType, inputStream);
		mediaContent.setLength(byteCount);
		// Configura o acesso ACL = Access Control List
		ImmutableList<ObjectAccessControl> acl = ImmutableList.of(
				new ObjectAccessControl().setEntity(
						String.format("project-owners-%s", projectId)).setRole(
						"OWNER"),
				new ObjectAccessControl().setEntity(
						String.format("project-editors-%s", projectId))
						.setRole("OWNER"),
				new ObjectAccessControl().setEntity(
						String.format("project-viewers-%s", projectId))
						.setRole("READER"), new ObjectAccessControl()
						.setEntity("allUsers").setRole("READER")); // URL pública 

		// Configura o objeto
		final StorageObject obj = new StorageObject();
		obj.setName(fileName); // nome
		obj.setContentType(contentType); // tipo do conteudo
		obj.setAcl(acl); // permissoes (foi configurado para URL publica
		// Insere o objeto no Cloud Storage (file)
		final Storage.Objects.Insert insertObjects = client.objects().insert(bucketName, obj, mediaContent);

		if (mediaContent.getLength() > 0
				&& mediaContent.getLength() <= 2 * 1000 * 1000 /* 2 MB */) {
			// Utiliza o Direct Upload para arquivos pequenos
			insertObjects.getMediaHttpUploader().setDirectUploadEnabled(true);
		}
		// Executa a API
		insertObjects.execute();
		return obj;
	}

}
