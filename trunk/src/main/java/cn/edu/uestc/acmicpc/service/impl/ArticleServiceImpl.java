package cn.edu.uestc.acmicpc.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

@Service
@Primary
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
  public List<ArticleListDTO> getArticleList(ArticleCondition condition,
      PageInfo pageInfo) throws AppException {
    condition.currentPage = pageInfo.getCurrentPage();
    condition.countPerPage = Global.RECORD_PER_PAGE;
    return articleDAO.findAll(ArticleListDTO.class, ArticleListDTO.builder(), condition.getCondition());
  }

  @Override
  public void operator(String field, String ids, String value) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    properties.put(field, value);
    articleDAO.updateEntitiesByField(properties, "articleId", ids);
  }


  @Override
  public Integer createNewArticle() throws AppException {
    Article article = new Article();
    article.setAuthor("");
    article.setClicked(0);
    article.setContent("");
    article.setIsNotice(false);
    article.setIsVisible(false);
    article.setOrder(0);
    article.setTime(new Timestamp(new Date().getTime()));
    article.setUserId(1);
    articleDAO.add(article);
    return article.getArticleId();
  }

  @Override
  public ArticleEditorShowDTO getArticleEditorShowDTO(Integer articleId)
      throws AppException {
    return articleDAO.getDTOByUniqueField(ArticleEditorShowDTO.class, ArticleEditorShowDTO.builder(), "articleId", articleId);
  }

}
