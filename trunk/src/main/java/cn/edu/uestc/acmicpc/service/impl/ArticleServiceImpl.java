package cn.edu.uestc.acmicpc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
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

}
