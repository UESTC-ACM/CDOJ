package cn.edu.uestc.acmicpc.web.dto;

/**
 * Description
 * TODO(mzry1992)
 */
public class FileInformationDTO {

  private String fileName;
  private String fileURL;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileURL() {
    return fileURL;
  }

  public void setFileURL(String fileURL) {
    this.fileURL = fileURL;
  }

  public FileInformationDTO(String fileName, String fileURL) {
    this.fileName = fileName;
    this.fileURL = fileURL;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    public FileInformationDTO build() {
      return new FileInformationDTO(fileName, fileURL);
    }

    private String fileName;
    private String fileURL;

    public String getFileName() {
      return fileName;
    }

    public Builder setFileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public String getFileURL() {
      return fileURL;
    }

    public Builder setFileURL(String fileURL) {
      this.fileURL = fileURL;
      return this;
    }
  }
}
