package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends AbstractService implements ArticleService {

  private final IArticleDAO articleDAO;

  @Autowired
  public ArticleServiceImpl(IArticleDAO articleDAO) {
    this.articleDAO = articleDAO;
  }

  @Override
  public IArticleDAO getDAO() {
    return articleDAO;
  }

  @Override
  public ArticleDTO getArticleDTO(Integer articleId)
      throws AppException {
    return articleDAO.getDTOByUniqueField(ArticleDTO.class,
        ArticleDTO.builder(), "articleId", articleId);
  }

  @Override
  public Long count(ArticleCondition condition) throws AppException {
    return articleDAO.count(condition.getCondition());
  }

  @Override
  public List<ArticleListDTO> getArticleList(ArticleCondition articleCondition,
                                             PageInfo pageInfo) throws AppException {
    Condition condition = articleCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return articleDAO.findAll(ArticleListDTO.class, ArticleListDTO.builder(), condition);
  }

  @Override
  public void operator(String field, String ids, String value) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    properties.put(field, value);
    articleDAO.updateEntitiesByField(properties, "articleId", ids);
  }

  @Override
  public Integer createNewArticle(Integer authorId) throws AppException {
    Article article = new Article();
    article.setTitle("");
    article.setContent("");
    article.setTime(new Timestamp(new Date().getTime()));
    article.setClicked(0);
    article.setOrder(0);
    article.setType(1);
    article.setIsVisible(true);
    article.setUserId(authorId);
    articleDAO.add(article);
    return article.getArticleId();
  }

  private void updateArticleByArticleDTO(Article article, ArticleDTO articleDTO) {
    if (articleDTO.getParentId() != null) {
      article.setParentId(articleDTO.getParentId());
    }
    if (articleDTO.getClicked() != null) {
      article.setClicked(articleDTO.getClicked());
    }
    if (articleDTO.getContent() != null) {
      article.setContent(articleDTO.getContent());
    }
    if (articleDTO.getType() != null) {
      article.setType(articleDTO.getType());
    }
    if (articleDTO.getIsVisible() != null) {
      article.setIsVisible(articleDTO.getIsVisible());
    }
    if (articleDTO.getOrder() != null) {
      article.setOrder(articleDTO.getOrder());
    }
    if (articleDTO.getProblemId() != null) {
      article.setProblemId(articleDTO.getProblemId());
    }
    if (articleDTO.getContestId() != null) {
      article.setContestId(articleDTO.getContestId());
    }
    if (articleDTO.getTime() != null) {
      article.setTime(articleDTO.getTime());
    }
    if (articleDTO.getTitle() != null) {
      article.setTitle(articleDTO.getTitle());
    }
    if (articleDTO.getUserId() != null) {
      article.setUserId(articleDTO.getUserId());
    }
  }

  @Override
  public void updateArticle(ArticleDTO articleDTO) throws AppException {
    AppExceptionUtil.assertNotNull(articleDTO);
    AppExceptionUtil.assertNotNull(articleDTO.getArticleId());
    Article article = articleDAO.get(articleDTO.getArticleId());
    AppExceptionUtil.assertNotNull(article);
    updateArticleByArticleDTO(article, articleDTO);
    articleDAO.update(article);
  }

  @Override
  public void incrementClicked(Integer articleId) throws AppException {
    articleDAO.increment("clicked", "articleId", articleId.toString());
  }

  @Override
  public Boolean checkArticleExists(Integer articleId) throws AppException {
    AppExceptionUtil.assertNotNull(articleId);
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.startId = articleId;
    articleCondition.endId = articleId;
    return articleDAO.count(articleCondition.getCondition()) == 1;
  }

}
