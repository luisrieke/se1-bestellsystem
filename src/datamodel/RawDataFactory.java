package datamodel;

import java.util.Date;

import system.ComponentFactory;


/**
 * Public factory class to create "raw" objects of datamodel classes. Access to
 * raw object creation is restricted to system.DataFactory. Due to this restriction
 * system.DataFactory must be the only component that can create datamodel objects.
 *
 * Datamodel classes have protected constructors that prohibit direct instantiation
 * with new outside the datamodel package. RawDataFactory is therefore the only
 * component to create datamodel objects - and system.DataFactory the only system
 * component that can use RawDataFactory's object creation interface.
 *
 * No public create-methods are therefore exposed from RawDataFactory and bound
 * to the RawDataFactoryIntf, for which only one internal implementation instance
 * exists that is only passed to system.DataFactory at the first invocation.
 *
 * @author svgr64
 *
 */
final public class RawDataFactory {

    private static RawDataFactory instance = null;

    private RawDataFactoryIntf rawFactory;


    /**
     * Interface with raw data creation methods. This interface is ONLY available
     * to system.DataFactory, which centrally coordinates the creation of business
     * objects.
     *
     */
    public interface RawDataFactoryIntf {

        Customer createCustomer( String id, String name, String contact );

        Article createArticle( String id, String descr, long price, int units );

        Order createOrder( long id, Date date, Customer customer );

        OrderItem createOrderItem( String descr, Article article, int units );

    }


    /**
     * Private constructor.
     */
    private RawDataFactory() {

        /*
         * Creation of only instance of RawDataFactoryIntf with actual object
         * creations with new.
         */
        this.rawFactory = new RawDataFactoryIntf() {

            @Override
            public Customer createCustomer( String id, String name, String contact ) {
                return new Customer( id, name, contact );
            }

            @Override
            public Article createArticle( String id, String descr, long price, int units ) {
                return new Article( id, descr, price, units);
            }

            @Override
            public Order createOrder( long id, Date date, Customer customer ) {
                return new Order( id, date, customer );
            }

            @Override
            public OrderItem createOrderItem( String descr, Article article, int units ) {
                return new OrderItem( descr, article, units );
            }

        };
    }

    /**
     * Public static access methods to pass RawDataFactoryIntf instance only at first
     * invocation.
     *
     * @param factory to validate that system.Factory is the first invoking instance
     * @return RawDataFactoryIntf instance only at first invocation, return null otherwise
     */
    public static RawDataFactoryIntf getInstance( ComponentFactory factory ) {

        if( instance == null && factory != null ) {
            instance = new RawDataFactory();
            // return RawDataFactory instance only on first invocation to prevent instance leakage
            return instance.rawFactory;
        }

        return null;
    }

}
