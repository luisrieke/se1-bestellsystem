package system;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;

/*
 * Non‐public implementation class.
 */
final class OutputProcessor implements Components.OutputProcessor {

    // Attribute:
    private final OrderProcessor orderProcessor;
    private final InventoryManager inventoryManager;

    // Konstruktoren in den Komponenten‐Klasse OutputProcessor:
    public OutputProcessor(InventoryManager inventoryManager, OrderProcessor orderProcessor) {
        this.inventoryManager = new InventoryManager();
        this.orderProcessor = new OrderProcessor(inventoryManager);
    }

    public OutputProcessor() {
        inventoryManager = null;
        orderProcessor = null;
    }

    // print Orders Methode:

    @Override
    public void printOrders(List<Order> orders, boolean printVAT) {

        // Aus Application 1:
        final int printLineWidth = 84;
        StringBuffer sbAllOrders = new StringBuffer("-------------");
        StringBuffer sbLineItem = new StringBuffer();

        int gesamtSumme = 0;
        long vat = 0;

        for (Order order : orders) {
            int summe = 0;
            String output = "";

            // Per Order alle Produkte durchlaufen:
            for (OrderItem item : order.getItems()) {
                summe += item.getUnitsOrdered() * item.getArticle().getUnitPrice();
                output += " " + item.getUnitsOrdered() + "x " + item.getDescription();

                // Steuer berechnen:
                if(printVAT) {
                    vat += orderProcessor.vat(item.getUnitsOrdered() * item.getArticle().getUnitPrice(),1);
                }
            }

            // Gesamtpreis ermitteln:
            gesamtSumme += summe;

            // Auf Konsole auswerfen:
            Customer customer = order.getCustomer();
            String customerName = splitName(customer, customer.getFirstName() + " " + customer.getLastName());
            sbLineItem = fmtLine("#" + order.getId() + ", " + customerName + "'s Bestellung:" + output,
                    fmtPrice(summe, "EUR", 14), printLineWidth);
            sbAllOrders.append("\n");
            sbAllOrders.append(sbLineItem);
        }

        // convert price (long: 2345 in cent) to String of length 14, right-aligned -> "     23,45 EUR"
        String fmtPrice1 = fmtPrice(2345, "EUR", 14);

        // calculate total price
        String fmtPriceTotal = pad(fmtPrice(gesamtSumme, "", " EUR"), 14, true);

        // append final line with totals
        sbAllOrders.append("\n")
                .append(fmtLine("-------------", "------------- -------------", printLineWidth))
                .append("\n")
                .append(fmtLine("Gesamtwert aller Bestellungen:", fmtPriceTotal, printLineWidth));

        // Steuersatz zum Druck hinzufügen:
        if(printVAT) {
            String fmtPriceVAT = pad(fmtPrice(vat, "", " EUR"), 14, true);
            sbAllOrders
                    .append("\n")
                    .append(fmtLine("Im Gesamtbetrag enthaltene Mehrwertsteuer (19%):", fmtPriceVAT, printLineWidth));
        }

        // print sbAllOrders StringBuffer with all output to System.out
        System.out.println(sbAllOrders.toString());

    }

    // neu verschobene Methoden:

    /**
     * Format long-price in 1/100 (cents) to String using DecimalFormatter.
     * For example, 299 -> "2,99"
     *
     * @param sb    StringBuffer to which price is added
     * @param price price as long in 1/100 (cents)
     * @return StringBuffer with added price
     */
    public StringBuffer fmtPrice(StringBuffer sb, long price) {
        if (sb == null) {
            sb = new StringBuffer();
        }
        double dblPrice = ((double) price) / 100.0;            // convert cent to Euro
        DecimalFormat df = new DecimalFormat("#,##0.00",
                new DecimalFormatSymbols(new Locale("de")));    // rounds double to 2 digits

        String fmtPrice = df.format(dblPrice);                // convert result to String in DecimalFormat
        sb.append(fmtPrice);
        return sb;
    }


    /**
     * Pad string to minimum width, either right-aligned or left-aligned
     *
     * @param str          String to pad
     * @param width        minimum width to which result is padded
     * @param rightAligned flag to chose left- or right-alignment
     * @return padded String
     */
    public String pad(String str, int width, boolean rightAligned) {
        String fmtter = (rightAligned ? "%" : "%-") + width + "s";
        String padded = String.format(fmtter, str);
        return padded;
    }

    /**
     * Format long-price in 1/100 (cents) to String using DecimalFormatter.
     * Append currency. For example, 299, "EUR" -> "2,99 EUR"
     *
     * @param price    price as long in 1/100 (cents)
     * @param currency currency as String, e.g. "EUR"
     * @return price as String with currency and padded to minimum width
     */
    @Override
    public String fmtPrice(long price, String currency) {
        String fmtPrice = pad(fmtPrice(price, "", " " + currency), 14, true);
        return fmtPrice;
    }

    /**
     * Format long-price in 1/100 (cents) to String using DecimalFormatter, add
     * currency and pad to minimum width right-aligned.
     * For example, 299, "EUR", 12 -> "    2,99 EUR"
     *
     * @param price    price as long in 1/100 (cents)
     * @param currency currency as String, e.g. "EUR"
     * @param width    minimum width to which result is padded
     * @return price as String with currency and padded to minimum width
     */
    @Override
    public String fmtPrice(long price, String currency, int width) {
        String fmtPrice = pad(fmtPrice(price, "", " " + currency), 14, true);
        return fmtPrice;
    }

    /**
     * Format long-price in 1/100 (cents) to String using DecimalFormatter and
     * prepend prefix and append postfix String.
     * For example, 299, "(", ")" -> "(2,99)"
     *
     * @param price   price as long in 1/100 (cents)
     * @param prefix  String to prepend before price
     * @param postfix String to append after price
     * @return price as String
     */
    public String fmtPrice(long price, String prefix, String postfix) {
        StringBuffer fmtPriceSB = new StringBuffer();
        if (prefix != null) {
            fmtPriceSB.append(prefix);
        }

        fmtPriceSB = fmtPrice(fmtPriceSB, price);

        if (postfix != null) {
            fmtPriceSB.append(postfix);
        }
        return fmtPriceSB.toString();
    }

    /**
     * Format line to a left-aligned part followed by a right-aligned part padded to
     * a minimum width.
     * For example:
     * <p>
     * <left-aligned part>                          <>       <right-aligned part>
     * <p>
     * "#5234968294, Eric's Bestellung: 1x Kanne         20,00 EUR   (3,19 MwSt)"
     * <p>
     * |<-------------------------------<width>--------------------------------->|
     *
     * @param leftStr    left-aligned String
     * @param rightStr   right-aligned String
     * @param totalWidth minimum width to which result is padded
     * @return String with left- and right-aligned parts padded to minimum width
     */
    @Override
    public StringBuffer fmtLine(String leftStr, String rightStr, int totalWidth) {
        StringBuffer sb = new StringBuffer(leftStr);
        int shiftable = 0;        // leading spaces before first digit
        for (int i = 1; rightStr.charAt(i) == ' ' && i < rightStr.length(); i++) {
            shiftable++;
        }
        final int tab1 = totalWidth - rightStr.length() + 1;    // - ( seperator? 3 : 0 );
        int sbLen = sb.length();
        int excess = sbLen - tab1 + 1;
        int shift2 = excess - Math.max(0, excess - shiftable);
        if (shift2 > 0) {
            rightStr = rightStr.substring(shift2, rightStr.length());
            excess -= shift2;
        }
        if (excess > 0) {
            switch (excess) {
                case 1:
                    sb.delete(sbLen - excess, sbLen);
                    break;
                case 2:
                    sb.delete(sbLen - excess - 2, sbLen);
                    sb.append("..");
                    break;
                default:
                    sb.delete(sbLen - excess - 3, sbLen);
                    sb.append("...");
                    break;
            }
        }
        String strLineItem = String.format("%-" + (tab1 - 1) + "s%s", sb.toString(), rightStr);
        sb.setLength(0);
        sb.append(strLineItem);
        return sb;
    }

    // Aufgabe 3:

    /**
     * Split single‐String name to first‐ and last name and set to the customer
     * object, e.g. single‐String "Eric Meyer" is split into "Eric" and "Meyer".
     *
     * @param customer object for which first‐ and lastName are set
     * @param name     single‐String name that is split into first‐ and last name
     * @return returns single‐String name extracted from customer object
     */
    @Override
    public String splitName(Customer customer, String name) {

        String[] split = name.split(" ");

        // wenn Komma:
        if (split[0].contains(",")) {
            customer.setLastName(split[0].replace(",", ""));
            customer.setFirstName(split[1]);

            // wenn kein Komma und 2 Namen:
        } else if (split.length != 3) {

            customer.setFirstName(split[0]);
            customer.setLastName(split[1]);

            // wenn kein Komma und 3 Namen:
        } else {
            customer.setFirstName(split[0] + " " + split[1].trim());
            customer.setLastName(split[2]);
        }
        return singleName(customer);
    }

    /**
     * Returns single‐String name obtained from first‐ and lastName attributes of
     * Customer object, e.g. "Eric", "Meyer" returns single‐String "Meyer, Eric".
     *
     * @param customer object referred to
     * @return sanitized name derived from first‐ and lastName attributes
     */
    @Override
    public String singleName(Customer customer) {
        String name = customer.getLastName() + ", " + customer.getFirstName();
        return name;
    }

    // nicht angepasste Methoden:
    @Override
    public void printInventory() {

    }

}