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
import domain.Profile;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class ProfileServiceTest  extends AbstractTest{
	@Autowired
	private ProfileService profileService;
	@Test 
	public void testSaveProfiles(){
		//Actor
		Collection<Profile> profiles = new ArrayList<>();
		Profile guardando=profileService.findOne(super.getEntityId("profile3"));
		profileService.save(guardando);
		profiles.add(guardando);
		Assert.isTrue(profiles.contains(guardando));
		
	}
	@Test
	public void  testDeleteProfile(){
		Profile borrando= profileService.findOne(super.getEntityId("profile3"));
			profileService.delete(borrando);
			Assert.isNull(profileService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Profile  find = profileService.findOne(super.getEntityId("profile3"));
		 int  findId= find.getId();
		 Assert.notNull(profileService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Profile> profiles;
		profiles = this.profileService.findAll();
		Assert.isTrue(!profiles.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Profile  create= profileService.create();
		Assert.notNull(create);
		
	}
}
