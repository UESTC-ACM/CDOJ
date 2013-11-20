package cn.edu.uestc.acmicpc.web.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Description
 * TODO(mzry1992)
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

    public Builder(){}

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
