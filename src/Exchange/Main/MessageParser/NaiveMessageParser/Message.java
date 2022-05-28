package Exchange.Main.MessageParser.NaiveMessageParser;

import Exchange.Main.MatchingEngine.OrderClass;
import Exchange.Main.MatchingEngine.OrderSide;

import java.math.BigDecimal;
import java.math.BigInteger;

/*
 * Message Format:
 * Add/Side=Buy or Sell/Class=Market or Limit/Quantity=200/Price=200.010/customerID=10001->5 DIGIT NUMBER/instrumentID=10001->5 Digit Number
 * Delete/ oldOrderID=xxxx->Some Number / instrumentID=5 Digit Number / customerID= 5 Digit Number
 *
 */
public class Message {
    private MessageType Type; // Add or Delete
    private OrderSide oSide; // Buy or Sell
    private OrderClass oClass; // Market or Limit
    private Integer Quantity;
    private BigDecimal Price;
    private BigInteger oldOrderID;
    private BigInteger orderID;
    private BigInteger customerID;
    private BigInteger instrumentID;
    private final String Message;

    public Message(String message) {
        Message = message;
        Type = null; // Add or Delete
        oSide = null; // Buy or Sell
        oClass = null; // Market or Limit
        Quantity = null;
        Price = null;
        oldOrderID = null;
        orderID = null;
        customerID = null;
        instrumentID = null;
    }

    public MessageType getType() {
        return Type;
    }

    public void setType(MessageType type) {
        Type = type;
    }

    public OrderSide getoSide() {
        return oSide;
    }

    public void setoSide(OrderSide oSide) {
        this.oSide = oSide;
    }

    public OrderClass getoClass() {
        return oClass;
    }

    public void setoClass(OrderClass oClass) {
        this.oClass = oClass;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public BigInteger getOldOrderID() {
        return oldOrderID;
    }

    public void setOldOrderID(BigInteger oldOrderID) {
        this.oldOrderID = oldOrderID;
    }

    public BigInteger getOrderID() {
        return orderID;
    }

    public void setOrderID(BigInteger orderID) {
        this.orderID = orderID;
    }

    public BigInteger getCustomerID() {
        return customerID;
    }

    public void setCustomerID(BigInteger customerID) {
        this.customerID = customerID;
    }

    public BigInteger getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(BigInteger instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getMessage() {
        return Message;
    }
}
