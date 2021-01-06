package datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;

import system.ComponentFactory;
import system.Components;


/**
 * 
 * JUnit4 test code for RawDataFactory class.
 * 
 * Use of assertions, see:
 *   https://junit.org/junit4/javadoc/latest/org/junit/Assert.html
 * 
 * @author sgra64
 */
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class RawDataFactoryTest {


	/*
	 * Test fixtures - objects needed to perform the tests
	 */
	private final ComponentFactory componentFactory = ComponentFactory.getInstance();

	private final Components.DataFactory dataFactory = componentFactory.getDataFactory();


	@Test
	public void test000_CreateInstance() {
		RawDataFactory.RawDataFactoryIntf rdf = RawDataFactory.getInstance( componentFactory );
		assertNull( rdf );		// must be null
	}

	@Test
	public void test010_CreateCustomer() {
		Customer c = dataFactory.createCustomer( "Eric Schulz-Mueller", "eric2346@gmail.com" );
		String id = c.getId();
		assertNotNull( id );						// id not null
		assertTrue( id.matches( "C\\d{5}" ) );		// id starts with "C" followed by 5 digits
		assertEquals( c.getFirstName(), "Eric" );
		assertEquals( c.getLastName(), "Schulz-Mueller" );
		assertEquals( c.getContact(), "eric2346@gmail.com" );
	}

	@Test
	public void test020_CreateArticle() {
		Article a = dataFactory.createArticle( "Tasse", 299, 2000  );
		String id = a.getId();
		assertNotNull( id );						// id not null
		assertTrue( id.matches( "SKU-\\d{6}" ) );	// id starts with "SKU-" followed by 6 digits
		assertEquals( a.getDescription(), "Tasse" );
		assertEquals( a.getUnitPrice(), 299L );
		assertEquals( a.getUnitsInStore(), 2000 );
	}

	@Test
	public void test030_CreateOrder() {
		Customer c = dataFactory.createCustomer( "Eric Schulz-Mueller", "eric2346@gmail.com" );
		Order o = dataFactory.createOrder( c );
		long id = o.getId();
		assertTrue( id > 0 );
		String idStr = String.valueOf( id );
		assertTrue( idStr.matches( "\\d{10}" ) );	// is is positive, 10 digit number, e.g. 8592356245L
		Date d = o.getDate();
		assertNotNull( d );
		assertSame( o.getCustomer(), c );
		Iterable<OrderItem> oi = o.getItems();
		assertNotNull( oi );
	}

	@Test
	public void test040_CreateOrderItem() {
		Article a = dataFactory.createArticle( "Tasse", 299, 2000  );
		OrderItem oi = dataFactory.createOrderItem( a.getDescription(), a, 12 );
		assertSame( oi.getArticle(), a );
		assertEquals( oi.getDescription(), "Tasse" );
		assertTrue( oi.getUnitsOrdered() == 12 );
	}

}
