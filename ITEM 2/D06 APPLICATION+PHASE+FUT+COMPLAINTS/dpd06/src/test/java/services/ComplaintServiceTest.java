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

import domain.Application;
import domain.Complaint;

import utilities.AbstractTest;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest  extends AbstractTest{
	@Autowired
	private ComplaintService complaintService;
	@Test 
	public void testSaveComplaints(){
		//Actor
		Collection<Complaint> complaints = new ArrayList<>();
		Complaint guardando=complaintService.findOne(super.getEntityId("complaint1"));
		complaintService.save(guardando);
		complaints.add(guardando);
		Assert.isTrue(complaints.contains(guardando));
		
	}
	
	@Test
	public void  findOneOk(){
		Complaint find = complaintService.findOne(super.getEntityId("complaint1"));
		 int  findId= find.getId();
		 Assert.notNull(complaintService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Complaint> complaints;
		complaints = this.complaintService.findAll();
		Assert.isTrue(!complaints.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Complaint  create= complaintService.create();
		Assert.notNull(create);
		
	}
	

}
