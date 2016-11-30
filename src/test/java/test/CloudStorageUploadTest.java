package test;
import java.io.File;

import com.google.api.services.storage.model.StorageObject;

import br.com.livro.util.CloudStorageUtil;

public class CloudStorageUploadTest {
											   
	private static final String BUCKET_NAME = "livrowebservices";
	
	public static void main(String[] args) throws Exception {
		
		final CloudStorageUtil c = new CloudStorageUtil("Livro Lecheta");
		final String accountId = "1030560031543-compute@developer.gserviceaccount.com";
		final File p12File = new File("Teste-228c72ad7d9e.p12");
		// Conecta
		c.connect(accountId, p12File);
		// Upload
		System.out.println("Fazendo upload...");
		final File file = new File("Koala.png");
		final String contentType = "image/png";
		final String storageProjectId = "1030560031543";
		final StorageObject obj = c.upload(BUCKET_NAME, file, contentType, storageProjectId);
		System.out.println("Upload OK, objeto: " + obj.getName());
		
	}
}
