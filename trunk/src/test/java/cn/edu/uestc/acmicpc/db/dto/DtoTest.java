package cn.edu.uestc.acmicpc.db.dto;

import static com.google.common.truth.Truth.assertThat;

import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import org.testng.annotations.Test;

/**
 * Test for generated dtos
 */
public class DtoTest {

  @Test
  public void testToString() {
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(1).setClicked(0).setContent("\"Test<>;;;;{{}{}{}").setTitle("Test").build();
    assertThat(articleDto.toString()).isEqualTo(
        "{\"articleId\":1,\"clicked\":0,\"content\":\"\\\"Test<>;;;;{{}{}{}\",\"title\":\"Test\"}");
  }
}
