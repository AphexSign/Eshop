package ru.yarm.eshop5.Services;

import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.News;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Repositories.NewsRepository;
import ru.yarm.eshop5.Repositories.UserRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public NewsService(NewsRepository newsRepository, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addNewsToDB(News news, Principal principal) {

        news.setChanged(LocalDateTime.now());
        news.setCreated(LocalDateTime.now());
        news.setActive(true);
        news.setUser(userRepository.findByName(principal.getName()).get());
        newsRepository.save(news);
    }

    public List<News> getAllNews() {
       return newsRepository.findAll();
    }


    public List<News> getAllNewsAndSortByIdAsc() {

        List<News> newsList=newsRepository.findAll();
        Comparator<News> comparator = new Comparator<News>() {
            @Override
            public int compare(News left, News right) {
                return Long.compare(left.getId(),right.getId());
            }
        };
        Collections.sort(newsList, comparator);
        newsList.sort(comparator);
        return newsList;

    }


    public List<News> getAllNewsAndSortByIdDesc() {
        List<News> newsList=newsRepository.findAll();
        Comparator<News> comparator = new Comparator<News>() {
            @Override
            public int compare(News left, News right) {
                return Long.compare(right.getId(),left.getId());
            }
        };
        Collections.sort(newsList, comparator);
        newsList.sort(comparator);
        return newsList;
    }


    public List<News> getAllNewsSortByDescAndActive(){
        List<News> activeNews=new ArrayList<>();
        List<News> tmpNews=getAllNewsAndSortByIdDesc();
        for(News news : tmpNews){
            if(news.isActive()){
                activeNews.add(news);
            }
        }
        return activeNews;
    }



    @Transactional
    public void deactiveNews(Long id) {
        News news=newsRepository.getReferenceById(id);
        news.setActive(false);
        newsRepository.save(news);
    }


    @Transactional
    public void activeNews(Long id) {
        News news=newsRepository.getReferenceById(id);
        news.setActive(true);
        newsRepository.save(news);
    }

    @Transactional
    public void deleteNews(Long id) {
        newsRepository.delete(newsRepository.getReferenceById(id));
    }


    public News getProductByID(Long id) {
       return newsRepository.getReferenceById(id);
    }

    @Transactional
    public void updateNewsToDB(News news) {
        News oldNews=newsRepository.getReferenceById(news.getId());
        news.setUser(oldNews.getUser());
        news.setCreated(oldNews.getCreated());
        news.setActive(oldNews.isActive());
        newsRepository.save(news);
    }
}




