package cn.edu.uestc.acmicpc.util.dto;

/**
 * DTO for {@link cn.edu.uestc.acmicpc.util.settings.Global.AuthenticationType}
 */
public class AuthenticationTypeDTO {
  private Integer authenticationTypeId;
  private String description;

  public AuthenticationTypeDTO(Integer authenticationTypeId, String description) {
    this.authenticationTypeId = authenticationTypeId;
    this.description = description;
  }

  public Integer getAuthenticationTypeId() {
    return authenticationTypeId;
  }

  public void setAuthenticationTypeId(Integer authenticationTypeId) {
    this.authenticationTypeId = authenticationTypeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
