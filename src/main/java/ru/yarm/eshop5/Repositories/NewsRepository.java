package ru.yarm.eshop5.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.eshop5.Models.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
