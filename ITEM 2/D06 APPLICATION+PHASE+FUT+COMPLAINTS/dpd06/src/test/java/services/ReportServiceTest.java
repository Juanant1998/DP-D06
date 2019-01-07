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
import domain.Report;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class ReportServiceTest extends AbstractTest {
	@Autowired
	private ReportService reportService;

	@Test 
	public void testSaveReports(){
		//Actor
		Collection<Report> reports = new ArrayList<>();
		Report guardando=reportService.findOne(super.getEntityId("report3"));
		reportService.save(guardando);
		reports.add(guardando);
		Assert.isTrue(reports.contains(guardando));
		
	}
	@Test
	public void  testDeleteReport(){
		Report borrando= reportService.findOne(super.getEntityId("report3"));
			reportService.delete(borrando);
			Assert.isNull(reportService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Report  find = reportService.findOne(super.getEntityId("report3"));
		 int  findId= find.getId();
		 Assert.notNull(reportService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Report> reports;
		reports = this.reportService.findAll();
		Assert.isTrue(!reports.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Report  create= reportService.create();
		Assert.notNull(create);
		
	}
}
