package br.com.livro.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.google.api.services.storage.model.StorageObject;
import com.google.common.io.Files;

import br.com.livro.util.CloudStorageUtil;

@Component
public class UploadService {
	
	private static final String PROJECT_ID = "1030560031543";
	private static final String ACCOUNT_ID = "1030560031543-compute@developer.gserviceaccount.com";
	private static final String APP_NAME = "Livro Lecheta";
	private static final String BUCKET_NAME = "livrowebservices";
	
	public String upload(final String fileName, final InputStream in) throws Exception {
		
		// Salva o arquivo na pasta temporaria da JVM
		final File file = saveToTmpDir(fileName, in);
		
		// Faz upload para o Cloud Storage
		final String url = uploadToCloudStorage(file);
		
		// Retorna a URL do arquivo
		return url;
	}

	private String uploadToCloudStorage(final File file) throws Exception {
		// Arquivo .p12 chave privada
		final String s = System.getProperty("p12File");
		
		if (s == null) {
			throw new IOException("Erro no servidor");
		}
		
		final File p12File = new File(s);
		if (!p12File.exists()) {
			throw new IOException("Erro no servidor");
		}
		
		// Conecta no Cloud Storage
		final CloudStorageUtil c = new CloudStorageUtil(APP_NAME);
		c.connect(ACCOUNT_ID, p12File);
		
		// Upload
		final String fileName = file.getName();
		final String contentType = getContentType(fileName);
		final String storageProjectId = PROJECT_ID;
		final StorageObject obj = c.upload(BUCKET_NAME, file, contentType, storageProjectId);
		
		if (obj == null) {
			throw new IOException("Erro ao fazer upload.");
		}
		
		//Retorna a URL pública
		final String url = String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME,
				obj.getName());
		
		return url;
	}

	private String getContentType(final String fileName) {
		
		final String ext = Files.getFileExtension(fileName);
		
		if ("png".equals(ext)) {
			return "image/png";
		} else if ("jpg".equals(ext) || "jpeg".equals(ext) ) {
			return "image/jpg";
		} else if ("gif".equals(ext)) {
			return "image/gif";
		}
		
		return "text/plain";
	}

	private File saveToTmpDir(final String fileName, final InputStream in) throws FileNotFoundException, IOException {
		
		if (fileName == null || in == null) {
			throw new IllegalArgumentException("Parâmetros inválidos");
		}
		
		// Pasta temporária da JVM
		final File tmpDir = new File(System.getProperty("java.io.tmpdir"), "carros");
		if (!tmpDir.exists()) {
			// Cria a pasta carros se não existe
			tmpDir.mkdir();
		}
		
		// Cria o arquivo
		final File file = new File(tmpDir, fileName);
		// Abre a OutputStream para escrever no arquivo
		final FileOutputStream out = new FileOutputStream(file);
		
		// Escrever os dados no arquivo
		IOUtils.copy(in, out);
		IOUtils.closeQuietly(out);
		
		return file;
	}
	
}
