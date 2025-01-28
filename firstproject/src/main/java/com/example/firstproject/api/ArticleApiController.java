package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/articles")
    public Article create(@RequestBody  ArticleForm articleForm){
        Article article = articleForm.toEntity();
        return articleRepository.save(article);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(
        @RequestBody  ArticleForm articleForm,
        @PathVariable Long id
    ){
        Article article = articleForm.toEntity();
        Article updateArticle = articleRepository.findById(id).orElse(null);
        if (updateArticle == null || id != article.getId()) {
            log.info("잘못된 요청! id: {}, article: {}", id, article);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        updateArticle.patch(article);
        Article updated = articleRepository.save(updateArticle);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        articleRepository.delete(article);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
