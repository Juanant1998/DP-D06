package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Category;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService categoryService;
	
	@Test
	public void testSaveCategories(){
		Category saved = this.categoryService.findOne(super.getEntityId("category1"));
		Collection<Category> categories = new ArrayList<>();
		
		this.categoryService.save(saved);
		categories.add(saved);
		
		Assert.isTrue(categories.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		Category saved = this.categoryService.findOne(super.getEntityId("category1"));
		
		this.categoryService.delete(saved);
		Assert.isNull(this.categoryService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<Category> categories;
		categories = this.categoryService.findAll();
		Assert.isTrue(!categories.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		Category c;
		c = this.categoryService.findOne(super.getEntityId("category2"));
		int findId = c.getId();
		Assert.notNull(this.categoryService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		Category c = this.categoryService.create();
		Assert.notNull(c);
	}

}
