package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.List;
@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm articleForm) {
        Article article = articleForm.toEntity();
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm articleForm) {
        Article article = articleForm.toEntity();
        Article updateArticle = articleRepository.findById(id).orElse(null);
        if (updateArticle == null || id != article.getId()) {
            log.info("잘못된 요청! id: {}, article: {}", id, article);
            return null;
        }
        updateArticle.patch(article);
        Article updated = articleRepository.save(updateArticle);
        return updated;
    }

    public Article delete(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }
        articleRepository.delete(article);
        return article;
    }
}
