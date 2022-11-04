package ru.yarm.eshop5.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.Category;
import ru.yarm.eshop5.Repositories.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategorySortedById() {
        return categoryRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public void addCategoryToDB(Category category) {
        categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Transactional
    public void updateCategoryToDB(Category category) {
        categoryRepository.save(category);
    }
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.delete(getCategoryById(id));
    }
}
