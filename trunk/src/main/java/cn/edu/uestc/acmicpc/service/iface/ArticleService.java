package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.impl.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Article service interface.
 */
public interface ArticleService extends DatabaseService<Article, Integer> {

  /**
   * Update article by {@link ArticleDto}.
   *
   * @param articleDto
   *          {@link ArticleDto} entity.
   * @throws AppException
   */
  public void updateArticle(ArticleDto articleDto) throws AppException;

  /**
   * Get {@link ArticleDto} by article id with specified fields.
   *
   * @param articleId
   *          article's id.
   * @param articleFields
   *          fields which is needed.
   * @return {@link ArticleDto} entity.
   * @throws AppException
   */
  public ArticleDto getArticleDto(Integer articleId,
      ArticleFields articleFields)
      throws AppException;

  /**
   * Counts the number of articles fit in condition.
   *
   * @param articleCriteria
   *          {@link ArticleCriteria} entity.
   * @return total number of articles fit in the condition.
   * @throws AppException
   */
  public Long count(ArticleCriteria articleCriteria) throws AppException;

  /**
   * Get the articles fit in condition and page range.
   *
   * @param articleCriteria
   *          {@link ArticleCriteria} entity.
   * @param pageInfo
   *          {@link PageInfo} entity.
   * @return List of {@link ArticleDto} entities.
   * @throws AppException
   */
  public List<ArticleDto> getArticleList(ArticleCriteria articleCriteria, PageInfo pageInfo)
      throws AppException;

  /**
   * Modify one filed of multiply entities as value.
   *
   * @param field
   *          filed need to modified.
   * @param ids
   *          entities' ID split by <code>,</code>.
   * @param value
   *          new value.
   * @throws AppException
   */
  public void applyOperation(String field, String ids, String value) throws AppException;

  /**
   * Creates a new article record.
   *
   * @param authorId
   *          {@code userId} of the message's author
   * @return the newly created article's id.
   * @throws AppException
   */
  public Integer createNewArticle(Integer authorId) throws AppException;

  /**
   * Increment clicked of specific article by 1.
   *
   * @param articleId
   *          article's id.
   * @throws AppException
   */
  public void incrementClicked(Integer articleId) throws AppException;

  /**
   * Check whether a problem exists.
   *
   * @param articleId
   *          article's id.
   * @return true if specified article exists.
   * @throws AppException
   */
  public Boolean checkArticleExists(Integer articleId) throws AppException;
}
