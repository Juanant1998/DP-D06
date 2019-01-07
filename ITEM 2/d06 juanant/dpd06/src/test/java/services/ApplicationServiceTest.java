
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;


	@Test
	public void testSaveActors() {
		//Actor

		final Application guardando = this.applicationService.findOne(super.getEntityId("application1"));
		final Application fin = this.applicationService.save(guardando);

		final Collection<Application> applications = this.applicationService.findAll();

		Assert.isTrue(applications.contains(fin));

		/*
		 * final Application guardando = new Application();
		 * 
		 * guardando.setOfferedPrice(125.0);
		 * guardando.setStatus("PENDING");
		 * 
		 * guardando.setComments("comentarios");
		 * 
		 * final Application guardada = this.applicationService.save(guardando);
		 * 
		 * final Collection<Application> apps = this.applicationService.findAll();
		 * 
		 * Assert.isTrue(apps.contains(guardada));
		 */
	}
	@Test
	public void testDeleteActor() {
		final Application borrando = this.applicationService.findOne(super.getEntityId("application1"));
		this.applicationService.delete(borrando);
		Assert.isNull(this.applicationService.findOne(borrando.getId()));
	}
	@Test
	public void findOneOk() {
		final Application find = this.applicationService.findOne(super.getEntityId("application1"));
		final int findId = find.getId();
		Assert.notNull(this.applicationService.findOne(findId));
	}
	@Test
	public void FindAll() {
		Collection<Application> applications;
		applications = this.applicationService.findAll();
		Assert.isTrue(!applications.isEmpty());

	}
	@Test
	public void CreateTest() {
		final Application create = this.applicationService.create();
		Assert.notNull(create);

	}

}
