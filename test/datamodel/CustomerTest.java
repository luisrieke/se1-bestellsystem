package datamodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;


/**
 * 
 * JUnit4 test code for Customer class.
 * 
 * Use of assertions, see:
 *   https://junit.org/junit4/javadoc/latest/org/junit/Assert.html
 * 
 * @author sgra64
 */
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class CustomerTest {

	/*
	 * Test fixtures - objects needed to perform the test
	 */
	private final String cEric_id		= "C86516";
	private final String cEric_name		= "Eric Schulz-Mueller";
	private final String cEric_contact	= "eric2346@gmail.com";

	private final Customer cEric = new Customer( cEric_id, cEric_name, cEric_contact );


	/**
	 * JUnit4 Test Setup Code
	 * ------------------------------------------------------------------------
	 * Setup method called once before any @Test method is executed.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println( CustomerTest.class.getSimpleName() + ".setUpBeforeClass() called." );
	}

	/**
	 * Setup method called each time before a @Test method executes.
	 * Each @Test method executes with a new instance of this class.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		//System.out.println( this.getClass().getSimpleName() + ".setUp() called." );
	}


	/**
	 * JUnit4 Test Code
	 * ------------------------------------------------------------------------
	 */

	/*
	 * Test constructor, regular case.
	 */
	@Test
	public void test001_RegularConstructor() {
		Customer c = new Customer( cEric_id, cEric_name, cEric_contact );
		assertNotNull( c.getId() );					// assert that id is not null
		assertEquals( c.getId(), cEric_id );		// assert that cEric_id is returned
		assertSame( c.getId(), cEric_id );			// assert that same ("==") id is returned
		assertEquals( c.getFirstName(), "" );		// "name" is assigned to lastName, firstName: ""
		assertEquals( c.getLastName(), "Eric Schulz-Mueller" );
		assertEquals( c.getLastName(), cEric_name );	// better to use constant;
		assertEquals( c.getContact(), cEric_contact );
	}

	/*
	 * Test constructor, special case with empty String arguments.
	 */
	@Test
	public void test002_EmptyArgumentConstructor() {
		Customer c = new Customer( "", "", "" );
		assertEquals( c.getId(), "" );
		assertEquals( c.getFirstName(), "" );
		assertEquals( c.getLastName(), "" );
		assertEquals( c.getContact(), "" );
	}

	/*
	 * Test constructor, Test special case with null arguments.
	 */
	@Test
	public void test003_NullArgumentConstructor() {
		Customer c = new Customer( null, null, null );
		assertNull( c.getId() );					// assert null for id
		assertEquals( c.getFirstName(), "" );		// "" returned for firstName: null
		assertEquals( c.getLastName(), "" );		// "" returned for lastName: null
		assertEquals( c.getContact(), "" );			// "" returned for contact: null
	}

	@Test
	public void test010_SetFirstName() {
		final String firstName = "Max";
		cEric.setFirstName( firstName );			// regular case
		assertEquals( cEric.getFirstName(), firstName );

		cEric.setFirstName( "" );					// special case: ""
		assertEquals( cEric.getFirstName(), "" );	// -> ""

		cEric.setFirstName( null );					// special case: null
		assertEquals( cEric.getFirstName(), "" );	// -> ""
	}

	@Test
	public void test011_SetLastName() {
		final String lastName = "Meier";
		cEric.setLastName( "Meier" );				// regular case
		assertEquals( cEric.getLastName(), lastName );

		cEric.setLastName( "" );					// special case: ""
		assertEquals( cEric.getLastName(), "" );	// -> ""

		cEric.setLastName( null );					// special case: null
		assertEquals( cEric.getLastName(), "" );	// -> ""
	}

	@Test
	public void test012_SetContact() {
		final String contact = "0170-2345924";
		cEric.setContact( contact );				// regular case
		assertEquals( cEric.getContact(), contact );

		cEric.setContact( "" );						// special case: ""
		assertEquals( cEric.getContact(), "" );		// -> ""

		cEric.setContact( null );					// special case: null
		assertEquals( cEric.getContact(), "" );		// -> ""
	}


	/**
	 * JUnit4 Test Tear-down Code
	 * ------------------------------------------------------------------------
	 * 
	 * Tear-down method called each time after a @Test method has finished.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		//System.out.println( this.getClass().getSimpleName() + ".tearDown() called." );
	}

	/**
	 * Tear-down method called after all @Test methods in this class have finished.
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println( CustomerTest.class.getSimpleName() + ".tearDownAfterClass() called." );
	}

}
