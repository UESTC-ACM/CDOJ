package cn.edu.uestc.acmicpc.db.dto;

import cn.edu.uestc.acmicpc.db.criteria.transformer.AliasToProtocolBufferBuilderTransformer;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for protocol buffer entities.
 */
public class ProtocolBufferTest {

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

  @Test
  public void test_aliasToProtocolBufferBuilderTransformer_successful() throws AppException {

    AliasToProtocolBufferBuilderTransformer transformer = new AliasToProtocolBufferBuilderTransformer(ArticleDtoProtos.ArticleDto.class);

    Object values[] = new Object[2];
    values[0] = 1;
    values[1] = "Frequently Asked Questions";

    String alias[] = new String[2];
    alias[0] = "articleId";
    alias[1] = "title";

    ArticleDtoProtos.ArticleDto result = (ArticleDtoProtos.ArticleDto) transformer.transformTuple(values, alias);

    Assert.assertEquals(result.getArticleId(), 1);
    Assert.assertEquals(result.getTitle(), "Frequently Asked Questions");
    // Empty fields.
    Assert.assertEquals(result.hasContent(), false);
    Assert.assertEquals(result.hasTime(), false);
    Assert.assertEquals(result.hasClicked(), false);
    Assert.assertEquals(result.hasOrder(), false);
    Assert.assertEquals(result.hasType(), false);
    Assert.assertEquals(result.hasIsVisible(), false);
    Assert.assertEquals(result.hasParentId(), false);
    Assert.assertEquals(result.hasProblemId(), false);
    Assert.assertEquals(result.hasContestId(), false);
    Assert.assertEquals(result.hasUserId(), false);
  }
}
