
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;


	public Category create() {
		return new Category();
	}
	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}
	public Category findOne(final int categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}
	public Category save(final Category category) {
		return this.categoryRepository.save(category);
	}
	public void delete(final Category category) {
		final Collection<Category> categories = this.categoryRepository.findAll();
		for (final Category c : categories)
			if (c.getDescendants().contains(category))
				c.getDescendants().remove(category);
		this.categoryRepository.delete(category);
	}

}
