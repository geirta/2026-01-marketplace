package ee.geir.marketplace.service;

import ee.geir.marketplace.entity.Category;
import ee.geir.marketplace.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void validate(Category category) {
        if (category.getId() != null) {
            throw new RuntimeException("Cannot add a category with an ID");
        }
        Category dbCategory = categoryRepository.findByNameIgnoreCase(category.getName());
        if (dbCategory != null) {
            throw new RuntimeException("This category name already exists.");
        }
    }

}
