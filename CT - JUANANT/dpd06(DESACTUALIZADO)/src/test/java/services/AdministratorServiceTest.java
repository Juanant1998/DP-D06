package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Message;
import domain.MessageBox;
import domain.Referee;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class AdministratorServiceTest extends AbstractTest {

	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private WarrantyService warrantyService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;

	@Autowired
	private RefereeService rs;
	
	
	@Test
	public void testSaveAdministrators(){
		Administrator saved = this.administratorService.findOne(super.getEntityId("administrator1"));
		Collection<Administrator> administrators = new ArrayList<>();
		
		this.administratorService.save(saved);
		administrators.add(saved);
		
		Assert.isTrue(administrators.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		Administrator saved = this.administratorService.findOne(super.getEntityId("administrator1"));
		
		this.administratorService.delete(saved);
		Assert.isNull(this.administratorService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<Administrator> administrators;
		administrators = this.administratorService.findAll();
		Assert.isTrue(!administrators.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		Administrator a;
		a = this.administratorService.findOne(super.getEntityId("administrator2"));
		int findId = a.getId();
		Assert.notNull(this.administratorService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		Administrator a = this.administratorService.create();
		Assert.notNull(a);
	}

	/*@Test
	public void createAdminTest(){
		Administrator a = this.administratorService.createAdmin();
		Assert.notNull(a);
	}*/
	
	@Test
	public void CategoryListTest(){
		super.authenticate("administrator1");
		Assert.notNull(this.administratorService.categoryList());
		super.authenticate(null);
	}
	
	@Test
	public void showCategoryTest(){
		super.authenticate("administrator1");
		Category c = this.categoryService.findOne(super.getEntityId("category1"));
		Assert.notNull(this.administratorService.showCategory(c.getId()));
		super.authenticate(null);
	}

	@Test
	public void createCategoryTest(){
		super.authenticate("administrator1");
		Category c = this.administratorService.createCategory();
		Assert.notNull(c);
		super.authenticate(null);
	}

	@Test
	public void updateCategoryTest(){
		super.authenticate("administrator1");
		Category c = this.categoryService.findOne(super.getEntityId("category2"));
		String name = c.getName();
		c.setName("jajas");
		Category cat = this.administratorService.updateCategory(c);
		Assert.isTrue(!(name.equals(cat.getName())));
		super.authenticate(null);
	}
	
	@Test
	public void deleteCategoryTest(){
		Category c = this.categoryService.findOne(super.getEntityId("category2"));
		this.administratorService.deleteCategory(c);
		Assert.isNull(this.categoryService.findOne(c.getId()));
	}
	
	@Test
	public void warrantyListTest(){
		super.authenticate("administrator1");
		Assert.notNull(this.administratorService.warrantyList());
		super.authenticate(null);
	}
	
	@Test
	public void showWarrantyTest(){
		super.authenticate("administrator1");
		Warranty w = this.warrantyService.findOne(super.getEntityId("warranty1"));
		Assert.notNull(this.administratorService.showWarranty(w.getId()));
		super.authenticate(null);
	}

	@Test
	public void createWarrantyTest(){
		super.authenticate("administrator1");
		Warranty a = this.administratorService.createWarranty();
		Assert.notNull(a);
		super.authenticate(null);
	}

	@Test
	public void updateWarrantyTest(){
		super.authenticate("administrator1");
		Warranty w = this.warrantyService.findOne(super.getEntityId("warranty2"));
		String title = w.getTitle();
		w.setTitle("jajas");
		Warranty war = this.administratorService.updateWarranty(w);
		Assert.isTrue(!(title.equals(war.getTitle())));
		super.authenticate(null);
	}
	
	@Test
	public void deleteWarrantyTest(){
		Warranty w = this.warrantyService.findOne(super.getEntityId("warranty2"));
		this.administratorService.deleteWarranty(w);
		Assert.isNull(this.warrantyService.findOne(w.getId()));
	}
	@Test
	public void broadcastMessageTest(){
		Message m = this.messageService.findOne(super.getEntityId("message2"));
		this.administratorService.broadcastMessage(m);
		final List<Actor> listaActores = (List<Actor>) this.actorService.findAll();
		for(Actor a : listaActores){
			for (final MessageBox msb : a.getMessageBoxes()){
				if (msb.isSystemBox() == true && msb.getName().endsWith("in")) {
					Assert.isTrue(msb.getMessages().contains(m));
				}
			}
		}
	}

	@Test
	public void createRefereeTest(){
		super.authenticate("administrator1");
		Referee a = this.rs.findOne(super.getEntityId("referee1"));
		a.setEmail("nuevoemail@gmail.com");
		
		UserAccount ua = new UserAccount();
		ua.setUsername("nuevouser");
		ua.setPassword("nfmeiwo82394u2NIF");
		Authority auth = new Authority();
		Collection<Authority> ca = new ArrayList<Authority>();
		ca.add(auth);
		
		a.setUserAccount(ua);
		
		Referee registrado = this.administratorService.createRefereeAccount(a);
		
		Assert.isTrue(a.getEmail().equals(registrado.getEmail()));
		
		
		
		
		
		super.authenticate(null);
	}
	
	@Test
	public void getBannedTest(){
		super.authenticate("administrator1");

		Referee a = this.rs.findOne(super.getEntityId("referee1"));

		a.setIsSuspicious(true);
		
		Boolean comparator = a.getIsBanned();
		Referee banhammer = (Referee) this.administratorService.banActor(a);
		
		Assert.isTrue(banhammer.getIsBanned()!= comparator);
		
		super.authenticate(null);

	}
	
	@Test
	public void getUnBannedTest(){
		super.authenticate("administrator1");

		Referee a = this.rs.findOne(super.getEntityId("referee1"));
		
		a.setIsBanned(true);
		Boolean comparator = a.getIsBanned();

		Referee banhammer = (Referee) this.administratorService.unbanActor(a);
		
		Assert.isTrue(banhammer.getIsBanned()!= comparator);
		
		super.authenticate(null);

	}
	
	

}