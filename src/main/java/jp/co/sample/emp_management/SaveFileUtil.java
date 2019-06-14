package jp.co.sample.emp_management;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SaveFileUtil {

	public String getExtension(String filename) {
		int dot = filename.lastIndexOf(".");
		if (dot > 0) {
			return filename.substring(dot).toLowerCase();
		}
		return "";
	}

	

	public void savefile(MultipartFile file) {
		String filename = file.getOriginalFilename();
		Path uploadfile = Paths.get("src/main/resources/static/img/" + filename);
		try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
			byte[] bytes = file.getBytes();
			os.write(bytes);
		} catch (IOException e) {
			// エラー処理は省略
		}
	}

	public void savefiles(List<MultipartFile> multipartFiles) {
		for (MultipartFile file : multipartFiles) {
			savefile(file);
		}
	}

}
