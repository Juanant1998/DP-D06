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

import utilities.AbstractTest;

import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class CreditCardServiceTest extends AbstractTest {

	@Autowired
	private CreditCardService creditCardService;
	
	@Test
	public void testSaveCreditCards(){
		CreditCard saved = this.creditCardService.findOne(super.getEntityId("creditCard1"));
		Collection<CreditCard> creditCards = new ArrayList<>();
		
		this.creditCardService.save(saved);
		creditCards.add(saved);
	
		Assert.isTrue(creditCards.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		CreditCard saved = this.creditCardService.findOne(super.getEntityId("creditCard1"));
		
		this.creditCardService.delete(saved);
		Assert.isNull(this.creditCardService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<CreditCard> creditCards;
		creditCards = this.creditCardService.findAll();
		Assert.isTrue(!creditCards.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		CreditCard cc;
		cc = this.creditCardService.findOne(super.getEntityId("creditCard2"));
		int findId = cc.getId();
		Assert.notNull(this.creditCardService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		CreditCard cc = this.creditCardService.create();
		Assert.notNull(cc);
	}

}
