package Exchange.Main.MatchingEngine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class OrderTree {
    TreeMap<BigDecimal, Limit> priceTree;
    HashMap<BigDecimal, Limit> priceMap;
    HashMap<BigInteger, Order> orderMap;
    Integer Volume;
    Integer nOrders;
    Integer Depth;

    OrderTree() {
        priceTree = new TreeMap<>();
        priceMap = new HashMap<>();
        orderMap = new HashMap<>();

        Volume = 0;
        nOrders = 0;
        Depth = 0;
    }

    public Integer length() {
        return orderMap.size();
    }

    /*
     * Returns the OrderList object associated with 'price'
     */
    public Limit getLimit(BigDecimal price) {

        return priceMap.get(price);
    }

    /*
     * Returns the order given the order id
     */
    public Order getOrder(BigInteger id) {

        return orderMap.get(id);
    }

    public void createPrice(BigDecimal price) {
        Depth += 1;
        Limit newList = new Limit(price);
        priceTree.put(price, newList);
        priceMap.put(price, newList);
    }

    public void removePrice(BigDecimal price) {
        Depth -= 1;
        priceTree.remove(price);
        priceMap.remove(price);
    }

    public boolean priceExists(BigDecimal price) {
        return priceMap.containsKey(price);
    }

    public boolean orderExists(BigInteger id) {
        return orderMap.containsKey(id);
    }

    public void insertOrder(Order incomingOrder) {
        BigInteger incomingOrderOrderID = incomingOrder.getOrderID();
        BigDecimal incomingOrderPrice = incomingOrder.getPrice();
        if (orderExists(incomingOrderOrderID)) {
            removeOrderByID(incomingOrderOrderID);
        }
        nOrders += 1;
        if (!priceExists(incomingOrderPrice)) {
            createPrice(incomingOrderPrice);
        }
        incomingOrder.setParentLimit(priceMap.get(incomingOrderPrice));
        priceMap.get(incomingOrderPrice).appendOrder(incomingOrder);
        orderMap.put(incomingOrderOrderID, incomingOrder);
        Volume += incomingOrder.getQuantity();
    }

    public void updateOrderQuantity(Integer newQuantity, BigInteger incomingOrderID) {
        Order order = this.orderMap.get(incomingOrderID);
        int initialVolume = order.getQuantity();
        order.updateQuantity(newQuantity, order.getTimeStamp());
        this.Volume += (order.getQuantity() - initialVolume);
    }

    public void removeOrderByID(BigInteger id) {
        Order order = orderMap.get(id);
        this.Volume -= order.getQuantity();
        order.getParentLimit().removeOrder(order);
        if (order.getParentLimit().getLength() == 0) {
            this.removePrice(order.getPrice());
        }
        this.orderMap.remove(id);
        this.nOrders -= 1;
    }

    public BigDecimal maxPrice() {
        if (this.Depth > 0) {
            return this.priceTree.lastKey();
        } else {
            return null;
        }
    }

    public BigDecimal minPrice() {
        if (this.Depth > 0) {
            return this.priceTree.firstKey();
        } else {
            return null;
        }
    }

    public Limit maxPriceList() {
        if (this.Depth > 0) {
            return this.getLimit(maxPrice());
        } else {
            return null;
        }
    }

    public Limit minPriceList() {
        if (this.Depth > 0) {
            return this.getLimit(minPrice());
        } else {
            return null;
        }
    }

    public String toString() {
        StringBuilder outString = new StringBuilder("| The Book:\n" +
                "| Max price = " + maxPrice() +
                "\n| Min price = " + minPrice() +
                "\n| Volume in book = " + getVolume() +
                "\n| Depth of book = " + getDepth() +
                "\n| Orders in book = " + getnOrders() +
                "\n| Length of tree = " + length() + "\n");
        for (Map.Entry<BigDecimal, Limit> entry : this.priceTree.entrySet()) {
            outString.append(entry.getValue().toString());
            outString.append("|\n");
        }
        return outString.toString();
    }

    public Integer getVolume() {
        return Volume;
    }

    public Integer getnOrders() {
        return nOrders;
    }

    public Integer getDepth() {
        return Depth;
    }
}
