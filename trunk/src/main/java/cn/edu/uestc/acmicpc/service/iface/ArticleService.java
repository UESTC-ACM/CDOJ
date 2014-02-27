package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Article service interface.
 */
public interface ArticleService extends DatabaseService<Article, Integer> {

  /**
   * Update article by {@link ArticleDTO}.
   *
   * @param articleDTO {@link ArticleDTO} entity.
   * @throws AppException
   */
  public void updateArticle(ArticleDTO articleDTO) throws AppException;

  /**
   * Get {@link ArticleDTO} by article id.
   *
   * @param articleId article's id.
   * @return {@link ArticleDTO} entity.
   * @throws AppException
   */
  public ArticleDTO getArticleDTO(Integer articleId) throws AppException;

  /**
   * Counts the number of articles fit in condition.
   *
   * @param condition {@link ArticleCondition} entity.
   * @return total number of articles fit in the condition.
   * @throws AppException
   */
  public Long count(ArticleCondition condition) throws AppException;

  /**
   * Get the articles fit in condition and page range.
   *
   * @param condition {@link ArticleCondition} entity.
   * @param pageInfo  {@link PageInfo} entity.
   * @return List of {@link ArticleListDTO} entities.
   * @throws AppException
   */
  public List<ArticleListDTO> getArticleList(ArticleCondition condition, PageInfo pageInfo)
      throws AppException;

  /**
   * Modify one filed of multiply entities as value.
   *
   * @param field filed need to modified.
   * @param ids   entities' ID split by <code>,</code>.
   * @param value new value.
   * @throws AppException
   */
  public void operator(String field, String ids, String value) throws AppException;

  /**
   * Creates a new article record.
   *
   * @return the newly created article's id.
   * @throws AppException
   */
  public Integer createNewArticle() throws AppException;

  /**
   * Get {@link ArticleEditorShowDTO} by article id.
   *
   * @param articleId article's id.
   * @return {@link ArticleEditorShowDTO} entity.
   * @throws AppException
   */
  public ArticleEditorShowDTO getArticleEditorShowDTO(Integer articleId) throws AppException;

  /**
   * Increment clicked of specific article by 1.
   *
   * @param articleId article's id.
   * @throws AppException
   */
  public void incrementClicked(Integer articleId) throws AppException;

  /**
   * Check whether a problem exists.
   *
   * @param articleId article's id.
   * @return true if specified article exists.
   * @throws AppException
   */
  public Boolean checkArticleExists(Integer articleId) throws AppException;
}
