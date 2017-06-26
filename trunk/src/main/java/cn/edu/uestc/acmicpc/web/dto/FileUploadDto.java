package cn.edu.uestc.acmicpc.web.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * Dto post from file upload form.
 */
public class FileUploadDto {

  private List<MultipartFile> files;

  public List<MultipartFile> getFiles() {
    return files;
  }

  public void setFiles(List<MultipartFile> files) {
    this.files = files;
  }

  public FileUploadDto(List<MultipartFile> files) {
    this.files = files;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    public Builder() {
    }

    public FileUploadDto build() {
      return new FileUploadDto(files);
    }

    List<MultipartFile> files;

    public Builder setFiles(List<MultipartFile> files) {
      this.files = files;
      return this;
    }
  }
}
