package pmf.mina.bjelica.travelholic.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void store(MultipartFile file);

	Resource loadFile(String filename);

	void init();

	void deleteAll();

}
