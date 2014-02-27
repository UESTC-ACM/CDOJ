package cn.edu.uestc.acmicpc.web.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * DTO post from file upload form.
 */
public class FileUploadDTO {

  private List<MultipartFile> files;

  public List<MultipartFile> getFiles() {
    return files;
  }

  public void setFiles(List<MultipartFile> files) {
    this.files = files;
  }

  public FileUploadDTO(List<MultipartFile> files) {
    this.files = files;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    public Builder() {
    }

    public FileUploadDTO build() {
      return new FileUploadDTO(files);
    }

    List<MultipartFile> files;

    public Builder setFiles(List<MultipartFile> files) {
      this.files = files;
      return this;
    }
  }
}
