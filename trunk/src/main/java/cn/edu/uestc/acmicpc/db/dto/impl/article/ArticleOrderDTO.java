package cn.edu.uestc.acmicpc.db.dto.impl.article;

/**
 * DTO used in notice arrangement operation.
 */
public class ArticleOrderDTO {
  private String order;

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ArticleOrderDTO)) {
      return false;
    }

    ArticleOrderDTO that = (ArticleOrderDTO) o;

    return !(order != null ? !order.equals(that.order) : that.order != null);

  }

  @Override
  public int hashCode() {
    return order != null ? order.hashCode() : 0;
  }
}
