package cn.edu.uestc.acmicpc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

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

}
