package test;
import java.io.File;
import java.util.List;

import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

import br.com.livro.util.CloudStorageUtil;

public class CloudStorageTest {
	
	public static void main(String[] args) throws Exception {
		
		final CloudStorageUtil c = new CloudStorageUtil("Livro Lecheta");
		// Campo Service account ID criado no console
		final String accountId = "1030560031543-compute@developer.gserviceaccount.com";
		final File p12File = new File("Teste-228c72ad7d9e.p12");
		// Conecta
		c.connect(accountId, p12File);
		// Consulta o bucket
		final Bucket bucket = c.getBucket("livrowebservices");
		System.out.println("name: " + bucket.getName());
		System.out.println("location: " + bucket.getLocation());
		System.out.println("timeCreated: " + bucket.getTimeCreated());
		System.out.println("owner: " + bucket.getOwner());
		// Lista os arquivos
		final List<StorageObject> file = c.getBucketFiles(bucket);
		for (final StorageObject o : file) {
			System.out.println("> " + o.getName() + " (" + o.getSize() + "bytes)");
		}
		
	}

}
