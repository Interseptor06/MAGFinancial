package Exchange.Main.MatchingEngine;

import java.math.BigDecimal;
import java.math.BigInteger;

// New one of these bad boys gets constructed when a trade happens
public class Trade {
    private long TimeStamp;
    private BigDecimal Price;
    private Integer Quantity;

    private BigInteger Buyer; // Buyer customerID
    private BigInteger Seller; // Seller customerID

    private BigInteger buyOrderID;
    private BigInteger sellOrderID;

    public Trade(long timeStamp, BigDecimal price, Integer quantity, BigInteger Buyer,
                 BigInteger Seller, BigInteger buyOrderID, BigInteger sellOrderID) {
        TimeStamp = timeStamp;
        Price = price;
        Quantity = quantity;
        this.Buyer = Buyer;
        this.Seller = Seller;
        this.buyOrderID = buyOrderID;
        this.sellOrderID = sellOrderID;
    }

    @Override
    public String toString() {
        return ("\n| TRADE\tt= " + TimeStamp +
                "\tprice = " + Price +
                "\tquantity = " + Quantity +
                "\tBuyer = " + Buyer +
                "\tSeller = " + Seller);
    }

    public String toCSV() {
        return (TimeStamp + ", " +
                Price + ", " +
                Quantity + ", " +
                Buyer + ", " +
                Seller + "\n");
    }

    public long getTimeStamp() {
        return TimeStamp;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public BigInteger getBuyer() {
        return Buyer;
    }

    public BigInteger getSeller() {
        return Seller;
    }

    public BigInteger getBuyOrderID() {
        return buyOrderID;
    }

    public BigInteger getSellOrderID() {
        return sellOrderID;
    }
}
