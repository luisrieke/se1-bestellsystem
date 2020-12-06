package system;

import java.util.Date;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;
import datamodel.RawDataFactory;


/**
 * Implementation class of the DataFactory interface. DataFactory interface for
 * creating business objects from the datamodel package. DataFactory is the only
 * component in the system that can create datamodel Objects.
 *
 * @author svgr64
 *
 */
final class DataFactory implements Components.DataFactory {

    /*
     * Dependencies.
     */
    private final RawDataFactory.RawDataFactoryIntf objectRawFactory;

    private final Components.InventoryManager inventoryMgr;

    private final Components.OutputProcessor outputProcessor;

    /*
     * Inner dependencies.
     */
    private final IDGenerator customerIdGenerator;

    private final IDGenerator articleIdGenerator;

    private final IDGenerator orderIdGenerator;


    /**
     * Protected constructor.
     *
     * @param objectRawFactory injected dependency to the objectRawFactory
     * @param inventoryMgr injected dependency to the inventoryMgr
     * @param customerNameMapper injected dependency to the customerNameMapper
     */
    DataFactory( RawDataFactory.RawDataFactoryIntf objectRawFactory,
                 Components.InventoryManager inventoryMgr,
                 Components.OutputProcessor outputProcessor
    ) {
        this.objectRawFactory = objectRawFactory;
        this.inventoryMgr = inventoryMgr;
        this.outputProcessor = outputProcessor;
        this.customerIdGenerator = new IDGenerator( "C", IDTYPE.NUM, 5 );
        this.articleIdGenerator = new IDGenerator( "SKU-", IDTYPE.NUM, 6 );
        this.orderIdGenerator = new IDGenerator( IDTYPE.NUM, 10 );
    }


    /**
     * Create new Customer object.
     *
     * @param name single-String name that is split into first and last name
     * @param contact customer's contact information
     * @return new Customer object
     */
    @Override
    public Customer createCustomer( String name, String contact ) {
        String id = customerIdGenerator.nextId();
        Customer customer = objectRawFactory.createCustomer( id, name, contact );
        outputProcessor.splitName( customer, name );
        return customer;
    }


    /**
     * Create new Article object.
     *
     * @param descr article description
     * @param price article price
     * @param units units of article in store
     * @return new Article object
     */
    @Override
    public Article createArticle( String descr, long price, int units ) {
        String id = "";
        for( id = articleIdGenerator.nextId(); inventoryMgr.containsArticle( id ); ) { }

        price = price < 0? 0 : price;
        units = units < 0? 0 : units;
        Article article = objectRawFactory.createArticle( id, descr, price, units );

        inventoryMgr.add( article );

        return article;
    }


    /**
     * Create new Order object.
     *
     * @param customer customer to whom the order is associated, must not be null
     * @return new Order object
     */
    @Override
    public Order createOrder( Customer customer ) {
        Order order = null;
        if( customer != null ) {
            long id = orderIdGenerator.nextIdAsLong();
            Date date = new Date();		// record order creation date
            order = objectRawFactory.createOrder( id, date, customer );
        }
        return order;
    }


    /**
     * Create new OrderItem object.
     *
     * @param descr description of ordered item, usually article description
     * @param article article that is referred to in orderItem
     * @param units units ordered
     * @return new OrderItem object
     */
    @Override
    public OrderItem createOrderItem( String descr, Article article, int units ) {
        OrderItem orderItem = null;
        if( article != null && units > 0 ) {
            orderItem = objectRawFactory.createOrderItem( descr, article, units );
        }
        return orderItem;
    }


    /**
     * Private ID-Generator using random numbers and supporting a variety of formats
     * such as ALPHANUM, AIRLINE, NUM, HEX, BIN.
     *
     * Examples are:
     *  - "C.ED84DX" using prefix "C." followed by a random number of type AIRLINE-code
     *  - "3450629369" as simple 10-digit decimal number
     *  - "A8C86ED4D8" as 10-digit hex number.
     *
     * @author sgra64
     *
     */

    enum IDTYPE { ALPHANUM, AIRLINE, NUM, HEX, BIN };

    private class IDGenerator {
        private final String prefix;
        private final int len;
        private final String alphabet;

        private final String[] alphabets = new String[] {
                "0123456789" + "ABCDEGFHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuwvxyz",
                "0123456789" + "ABCDEGFHIJKLMNOPQRSTUVWXYZ",
                "0123456789",
                "0123456789ABCDEF",
                "01"
        };


        /**
         * Public constructor.
         * @param _prefix prefix followed by random number.
         * @param type one of the IDTYPE's.
         * @param len total number of digits.
         */
        public IDGenerator( final IDTYPE type, final int len ) {
            this( "", type, len );
        }

        public IDGenerator( final String prefix, final IDTYPE type, final int len ) {
            this.prefix = prefix;
            this.len = len;
            this.alphabet = alphabets[ type.ordinal() ];
        }

        /**
         * Generate next id.
         * @return next id according to the format specified in the constructor.
         */
        public String nextId() {
            final int alphaLen = alphabet.length();
            final int chunkSize = 10;	// generate + append chunks of up to 10 digits (long limit)
            StringBuffer sb = new StringBuffer();
            if( prefix != null ) {
                sb.append( prefix );
            }
            for( int l1 = len; l1 > 0; l1 -= chunkSize ) {
                int l2 = Math.min( chunkSize, l1 );
                long rnd = (long)( Math.random() * Math.pow( alphaLen, l2 ) );
                for( int i=0; i++ < l2; rnd /= alphaLen ) {
                    int k1 = (int)( rnd % alphaLen );
                    sb.append( alphabet.charAt( (int)k1 ) );
                }
            }
            for( int i=sb.length(); i < len; i++ ) {
                sb.insert( 0, "0" );
            }
            return sb.toString();
        }

        public long nextIdAsLong() {
            long n = 0L;
            if( len >= 1 ) {
                double lower = Math.pow( 10, len - 1 );
                double upper = Math.pow( 10, len );
                double d1 = 0.0;
                for( int i=0; d1 < lower || d1 >= upper; i++ ) {

                    d1 = Math.random() * upper;

                    if( d1 < lower || d1 >= upper ) {
                        d1 += lower;
                    }

                    if( i > 0 ) {
                        System.out.print( " * " );
                    }
                }
                n = (long)d1;
            }
            return n;
        }
    }
}
