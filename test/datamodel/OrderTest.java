package datamodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.FixMethodOrder;


/**
 * 
 * JUnit4 test code for Order class.
 * 
 * Use of assertions, see:
 *   https://junit.org/junit4/javadoc/latest/org/junit/Assert.html
 * 
 * @author sgra64
 */
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class OrderTest {

	/*
	 * Test fixtures - objects needed to perform the tests
	 */
	private final Customer cEric = new Customer( "C86516", "Eric Schulz-Mueller", "eric2346@gmail.com" );

	private final Article aToaster = new Article( "SKU-868682", "Toaster", 2499, 1200 );
	private final Article aTeller = new Article( "SKU-638035", "Teller", 649, 7000 );

	private final OrderItem oiToaster_3x = new OrderItem( aToaster.getDescription(), aToaster, 3 );
	private final OrderItem oiTeller_4x = new OrderItem( aTeller.getDescription(), aTeller, 4 );
	private final OrderItem oiTeller_12x = new OrderItem( aTeller.getDescription(), aTeller, 12 );

	private final Date oToasterOrderDate = new Date();
	private final Order oToaster = new Order( 5234968294L, oToasterOrderDate, cEric );


	/*
	 * Test constructor, regular case.
	 */
	@Test
	public void test001_RegularConstructor() {
		Order o = new Order( 5234968294L, oToasterOrderDate, cEric );
		assertEquals( o.getId(), 5234968294L );			// equal id
		assertSame( o.getDate(), oToasterOrderDate );	// same Date-Object
		assertSame( o.getCustomer(), cEric );			// same Customer-Object
		assertEquals( o.count(), 0 );					// empty Iterable<OrderItems>
		Object oi = o.getItems();
		assertTrue( oi instanceof Iterable<?> );
	}

	/*
	 * Test constructor, special case with empty String and 0 - arguments.
	 */
	@Test
	public void test002_EmptyArgumentConstructor() {
		Order o = new Order( 0L, oToasterOrderDate, cEric );
		assertEquals( o.getId(), 0L );			// equal id
	}

	/*
	 * Test constructor, Test special case with null and < 0 - arguments.
	 */
	@Test
	public void test003_NullArgumentConstructor() {
		Order o = new Order( -1L, null, null );
		assertEquals( o.getId(), -1L );					// negative id accepted

		Date oDate = o.getDate();
		assertNotNull( oDate );							// Date() instance returned
		Date now = new Date();
		// time difference in milliseconds 
        long timeDiff = now.getTime() - oDate.getTime(); 
        assertTrue( "", timeDiff < 100 );					// date() just created less than 100ms ago

		assertNull( o.getCustomer() );					// null returned

		assertEquals( o.count(), 0 );					// empty Iterable<OrderItems>
		Object oItems = o.getItems();
		assertNotNull( oItems );	
		assertTrue( oItems instanceof Iterable<?> );
	}

	@Test
	public void test010_AddItemsTests() {
		Order o = oToaster;
		assertEquals( o.count(), 0 );
		Iterable<OrderItem> oItems = o.getItems();
		assertNotNull( oItems );	

		o.addItem( oiToaster_3x );
		assertEquals( o.count(), 1 );

		o.addItem( oiTeller_4x );
		assertEquals( o.count(), 2 );

		o.addItem( oiTeller_12x );
		assertEquals( o.count(), 3 );

		o.addItem( oiTeller_12x );		// attempt to add same item, list unchanged
		assertEquals( o.count(), 3 );

		o.addItem( null );				// attempt to add null, list unchanged
		assertEquals( o.count(), 3 );

		// resulting OrderItem list:
		OrderItem[] oiRV = new OrderItem[] {
			oiToaster_3x,
			oiTeller_4x,
			oiTeller_12x
		};
		int i = 0;	// verify order of OrderItems as added to Order
		for( OrderItem oi : o.getItems() ) {
			assertSame( oi, oiRV[ i++ ] );
		}
	}

	@Test
	public void test011_RemoveItemsTests() {
		Order o = oToaster;
		assertEquals( o.count(), 0 );

		o.addItem( oiToaster_3x );
		o.addItem( oiTeller_4x );
		o.addItem( oiTeller_12x );
		assertEquals( o.count(), 3 );

		o.removeItem( oiToaster_3x );
		assertEquals( o.count(), 2 );

		o.removeItem( oiToaster_3x );		// attempt of double removal, list unchanged
		assertEquals( o.count(), 2 );

		// resulting OrderItem list:
		OrderItem[] oiRV = new OrderItem[] {
			oiTeller_4x,
			oiTeller_12x
		};
		int i = 0;		// test remaining list
		for( OrderItem oi : o.getItems() ) {
			assertSame( oi, oiRV[ i++ ] );
		}

		o.removeItem( oiTeller_12x );		// remove oiTeller_12x
		assertEquals( o.count(), 1 );
		o.getItems().forEach( oi -> {
			assertSame( oi, oiTeller_4x );	// last remaining orderItem
		});

		o.removeItem( oiTeller_4x );		// remove last remaining element
		assertEquals( o.count(), 0 );		// -> list empty
	}

	@Test
	public void test012_ClearItemsTests() {
		Order o = oToaster;
		assertEquals( o.count(), 0 );

		o.addItem( oiToaster_3x );
		o.addItem( oiTeller_4x );
		o.addItem( oiTeller_12x );
		assertEquals( o.count(), 3 );

		o.clearItems();
		assertEquals( o.count(), 0 );

		o.getItems().forEach( oi -> {
			fail( "Should never execuot on cleared list" );
		});

		o.addItem( oiToaster_3x );			// list can be rebuild after clearance
		o.addItem( oiTeller_4x );
		o.addItem( oiTeller_12x );
		assertEquals( o.count(), 3 );

		o.clearItems();
		assertEquals( o.count(), 0 );
	}

}
