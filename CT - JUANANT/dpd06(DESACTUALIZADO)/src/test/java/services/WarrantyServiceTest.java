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

import domain.Actor;
import domain.Warranty;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class WarrantyServiceTest extends AbstractTest {
	@Autowired
	private WarrantyService warrantyService;
	@Test 
	public void testSaveWarranties(){
		//Actor
		Collection<Warranty> warranties = new ArrayList<>();
		Warranty guardando=warrantyService.findOne(super.getEntityId("warranty3"));
		warrantyService.save(guardando);
		warranties.add(guardando);
		Assert.isTrue(warranties.contains(guardando));
		
	}
	@Test
	public void  testDeleteWarranty(){
		Warranty borrando= warrantyService.findOne(super.getEntityId("warranty3"));
			warrantyService.delete(borrando);
			Assert.isNull(warrantyService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Warranty  find = warrantyService.findOne(super.getEntityId("warranty3"));
		 int  findId= find.getId();
		 Assert.notNull(warrantyService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Warranty> warranties;
		warranties = this.warrantyService.findAll();
		Assert.isTrue(!warranties.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Warranty  create= warrantyService.create();
		Assert.notNull(create);
		
	}
	
}
