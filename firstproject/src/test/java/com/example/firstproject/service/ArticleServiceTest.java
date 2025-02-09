package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        Article a = new Article(1L, "가가가", "111");
        Article b = new Article(2L, "나나나", "222");
        Article c = new Article(3L, "다다다", "333");
        List<Article> articles = articleService.index();
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_success() {
        Long id = 1L;
        Article expected = new Article(id, "가가가", "111");
        Article article = articleService.show(id);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_fail() {
        Long id = -1L;
        Article expected = null;
        Article article = articleService.show(id);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_success() {
        String title = "라라라";
        String content = "444";
        ArticleForm articleForm = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        Article article = articleService.create(articleForm);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_fail() {
        Long id = 4L;
        String title = "라라라";
        String content = "444";
        ArticleForm articleForm = new ArticleForm(id, title, content);
        Article expected = null;
        Article article = articleService.create(articleForm);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_success_id_title_content() {
        Long id = 1L;
        String title = "테스트";
        String content = "Update";
        Article expected = new Article(id, title, content);

        ArticleForm articleForm = new ArticleForm(id, title, content);
        Article article = articleService.update(id, articleForm);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_success_id_title() {
        Long id = 1L;
        String title = "테스트";
        Article expected = new Article(id, title, "111");
        ArticleForm articleForm = new ArticleForm(id, title, null);
        Article article = articleService.update(id, articleForm);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void update_fail() {
        Long id = -1L;
        Article expected = null;
        ArticleForm articleForm = new ArticleForm(id, null, null);
        Article article = articleService.update(id, articleForm);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_success() {
        Long id = 1L;
        Article expected = new Article(1L, "가가가", "111");
        Article deleted = articleService.delete(id);

        assertEquals(expected.toString(), deleted.toString());

    }
    @Test
    @Transactional
    void delete_fail() {
        Long id = -1L;
        Article expected = null;
        Article deleted = articleService.delete(id);

        assertEquals(expected, deleted);
    }
}