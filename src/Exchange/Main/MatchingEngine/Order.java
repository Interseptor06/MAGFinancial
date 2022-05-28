package Exchange.Main.MatchingEngine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Order {
    private long TimeStamp;
    private OrderSide oSide; // Buy or Sell
    private OrderClass oClass; // Market or Limit
    private Integer Quantity;
    private BigDecimal Price;
    private BigInteger orderID;
    private BigInteger customerID;
    private BigInteger instrumentID;
    private Order PreviousOrder;
    private Order NextOrder;
    private Limit parentLimit;

    public Order(OrderSide oSide, OrderClass oClass, Integer quantity,
                 BigDecimal price, BigInteger orderID, BigInteger customerID, BigInteger instrumentID) {
        TimeStamp = System.nanoTime();
        this.oSide = oSide;
        this.oClass = oClass;
        Quantity = quantity;
        Price = price.setScale(3, RoundingMode.HALF_UP); // Rounds to the nearest thousandth
        this.orderID = orderID;
        this.customerID = customerID;
        this.PreviousOrder = null;
        this.NextOrder = null;
        this.instrumentID = instrumentID;
    }

    public Limit getParentLimit() {
        return parentLimit;
    }

    public void setParentLimit(Limit parentLimit) {
        this.parentLimit = parentLimit;
    }

    public long getTimeStamp() {
        return TimeStamp;
    }

    public OrderSide getoSide() {
        return oSide;
    }

    public OrderClass getoClass() {
        return oClass;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    // Always rounds so 20.1 is actually 20.100
    public BigDecimal getPrice() {
        return Price;
    }

    public BigInteger getOrderID() {
        return orderID;
    }

    public BigInteger getCustomerID() {
        return customerID;
    }

    public Order getPreviousOrder() {
        return PreviousOrder;
    }

    public void setPreviousOrder(Order previousOrder) {
        PreviousOrder = previousOrder;
    }

    public Order getNextOrder() {
        return NextOrder;
    }

    public void setNextOrder(Order nextOrder) {
        NextOrder = nextOrder;
    }

    @Override
    public String toString() {
        return "Order{" +
                "TimeStamp=" + TimeStamp +
                ", oSide=" + oSide +
                ", oClass=" + oClass +
                ", Quantity=" + Quantity +
                ", Price=" + Price +
                ", orderID=" + orderID +
                ", customerID=" + customerID +
                ", instrumentID=" + instrumentID +
                '}';
    }

    public void setOrderID(BigInteger orderID) {
        this.orderID = orderID;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public void updateQuantity(Integer newQuantity, long TimeStamp) {
        if ((newQuantity > this.Quantity) && (this.parentLimit.getTailOrder() != this)) {
            // Move order to the end of the list. i.e. loses time priority
            this.parentLimit.movetoTail(this);
            this.TimeStamp = TimeStamp;
        }
        parentLimit.setVolume(parentLimit.getVolume()-(this.Quantity- newQuantity));
        this.Quantity = newQuantity;
    }

    public BigInteger getInstrumentID() {
        return instrumentID;
    }
}
