package com.example.demo.services;

import com.example.demo.model.Profile;
import com.example.demo.model.apis.newsapi.Article;
import com.example.demo.model.apis.newsapi.Articles;
import com.example.demo.model.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class NewsService {
    @Autowired
    ProfileRepository profiles;

    public Iterable<Article> articlesByCategory(String category){
        RestTemplate fromApi = new RestTemplate();
        Articles articles = fromApi.getForObject("https://newsapi.org/v2/top-headlines?category="+category+"&apiKey=5257f0a045ad4bc79397653e4210f74b", Articles.class);
        return articles.getArticles();
    }
    public Iterable<Article> articlesByInterests(String interests){
        RestTemplate fromApi = new RestTemplate();
        Articles articles = fromApi.getForObject("https://newsapi.org/v2/top-headlines?q="+interests+"&apiKey=5257f0a045ad4bc79397653e4210f74b", Articles.class);
        return articles.getArticles();
    }
    public Iterable<Article> articlesBySearch(String searchterms){
        RestTemplate fromApi = new RestTemplate();
        Articles articles = fromApi.getForObject("https://newsapi.org/v2/everything?q=" + searchterms + "&from=" + LocalDate.now().minusDays(1) + "&sortBy=relevancy&apiKey=5257f0a045ad4bc79397653e4210f74b", Articles.class);

        return articles.getArticles();
    }

    public Iterable<Article> defaultArticles(){
        RestTemplate fromApi = new RestTemplate();
        Articles articles = fromApi.getForObject("https://newsapi.org/v2/top-headlines?country=us&apiKey=5257f0a045ad4bc79397653e4210f74b", Articles.class);

        return articles.getArticles();
    }

    public Iterable<Article> personalized(Authentication authentication){
        Profile thisProfile = profiles.findByProfileOwner_Username(authentication.getName());
        Set<Article> articles = new HashSet<>();

        for (String category : thisProfile.getCategories()){
            for (Article article : articlesByCategory(category))
                articles.add(article);
        }
        for (Article article : articlesByInterests(thisProfile.getFreeTextOfInterests()))
            articles.add(article);

        return articles;
    }

//    @Override
//    public String toString(){//for testing
//        String newsText = "";
//        for (Article article : articles()){
////            System.out.println("one article: " + article.toString());
//            newsText += article.toString() + "\n\n";
//        }
//        return newsText;
//    }
}
