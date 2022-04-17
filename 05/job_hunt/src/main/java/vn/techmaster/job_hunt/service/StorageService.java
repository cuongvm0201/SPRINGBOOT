package vn.techmaster.job_hunt.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.techmaster.job_hunt.exception.StorageException;

@Service
public class StorageService {
  @Value("${upload.path}")
  private String path;

  public String saveFile(MultipartFile file, String id) throws IOException {
    if (file.isEmpty()) {
      throw new StorageException("Failed to store empty file");
    }
    
    String extension = getFileExtension(file.getOriginalFilename());
    String newFileName = path + id + "." + extension;
    try {
      var is = file.getInputStream();
      Files.copy(is, Paths.get(newFileName), StandardCopyOption.REPLACE_EXISTING);
      return id + "." + extension;
    } catch (IOException e) {
      var msg = String.format("Failed to store file %s", newFileName);
      throw new StorageException(msg, e);
    }
  }

  public void deleteFile(String logoPath) throws IOException {
    String filePathToDelete = path + logoPath;
  try {
    Files.deleteIfExists(Paths.get(filePathToDelete));
  } catch (IOException e) {
    var msg = String.format("Failed to delete  %s", filePathToDelete);
    throw new StorageException(msg, e);
  }

  }

  public String getFileExtension(String fileName){
   int postOfDot = fileName.lastIndexOf(".");
   if(postOfDot >= 0){
     return fileName.substring(postOfDot + 1);
   } else {
     return null;
   }
  }
}
