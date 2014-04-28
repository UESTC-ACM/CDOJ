package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dto.ArticleDtoProtos;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for protobuf entities.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class ProtobufITTest extends AbstractTestNGSpringContextTests {

  @Test
  public void test_articleDto() {
    long currentTime = System.currentTimeMillis();
    // Build a new ArticleDto entity
    ArticleDtoProtos.ArticleDto articleDto = ArticleDtoProtos.ArticleDto.newBuilder()
        .setArticleId(1)
        .setTitle("title")
        .setContent("content")
        .setTime(currentTime)
        // Do not set fields with default value
        .build();

    Assert.assertEquals(articleDto.getArticleId(), 1);
    Assert.assertEquals(articleDto.getTitle(), "title");
    Assert.assertEquals(articleDto.getContent(), "content");
    Assert.assertEquals(articleDto.getTime(), currentTime);
    Assert.assertEquals(articleDto.getClicked(), 0);
    Assert.assertEquals(articleDto.getOrder(), 0);
    Assert.assertEquals(articleDto.getType(), ArticleDtoProtos.ArticleDto.ArticleType.ARTICLE);
    Assert.assertEquals(articleDto.getIsVisible(), false);

    // Null
    Assert.assertEquals(articleDto.hasParentId(), false);
    Assert.assertEquals(articleDto.hasProblemId(), false);
    Assert.assertEquals(articleDto.hasContestId(), false);

    Assert.assertEquals(articleDto.getUserId(), 1);
  }

}
