package Exchange.Main.MatchingEngine;

import Exchange.Main.MessageParser.NaiveMessageParser.Message;
import Exchange.Main.MessageParser.NaiveMessageParser.MessageParser;
import Exchange.Main.MessageParser.NaiveMessageParser.MessageType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderMatchingEngine {
    HashMap<BigInteger, OrderBook> OrderBookMap; // <instrumentID, OrderBook>

    AtomicInteger LastOrderID;

    public OrderMatchingEngine() {
        OrderBookMap = new HashMap<>();
        LastOrderID = new AtomicInteger(0);
    }

    public OrderReport processMessage(String MSG){
        try {
            Message Msg = MessageParser.ParseMSG(MSG);
            OrderReport oReport;
            if (Msg.getType() == MessageType.Add) {
                Order incomingOrder = new Order(Msg.getoSide(), Msg.getoClass(), Msg.getQuantity(), Msg.getPrice(),
                        BigInteger.valueOf(LastOrderID.addAndGet(1)), Msg.getInstrumentID(), Msg.getCustomerID());
                oReport = placeOrder(incomingOrder);
            } else {
                cancelOrder(Msg.getOrderID(), Msg.getInstrumentID());
                oReport = null;
            }
            return oReport; // Could be null
        }
        catch (Exception ignored){ return null; }
    }

    private OrderBook getOrderBook(BigInteger symbolID) {
        OrderBook orderBook = OrderBookMap.get(symbolID);
        if (orderBook == null) {
            orderBook = new OrderBook();
            OrderBookMap.put(symbolID, orderBook);
        }
        return orderBook;
    }

    private OrderReport placeOrder(Order order) {
        OrderBook orderBook = getOrderBook(order.getInstrumentID());
        return orderBook.processOrder(order);
    }

    private void cancelOrder(BigInteger orderID, BigInteger instrumentID){
        OrderBook specifiedOB = OrderBookMap.get(instrumentID);
        specifiedOB.cancelOrder(orderID);
    }

    private BigDecimal getBestBid(BigInteger instrumentID) {
        return OrderBookMap.get(instrumentID).BuySide.maxPrice();
    }

    private BigDecimal getWorstBid(BigInteger instrumentID) {
        return OrderBookMap.get(instrumentID).BuySide.minPrice();
    }

    private BigDecimal getBestOffer(BigInteger instrumentID) {
        return OrderBookMap.get(instrumentID).SellSide.minPrice();
    }

    private BigDecimal getWorstOffer(BigInteger instrumentID) {
        return OrderBookMap.get(instrumentID).SellSide.maxPrice();
    }

    private BigDecimal getSpread(BigInteger instrumentID) {
        return OrderBookMap.get(instrumentID).BuySide.minPrice().subtract(OrderBookMap.get(instrumentID).SellSide.maxPrice());
    }

    private BigDecimal getMid(BigInteger instrumentID) {
        return OrderBookMap.get(instrumentID).getBestBid().add(OrderBookMap.get(instrumentID).getSpread().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP));
    }

    private boolean bidsAndAsksExist(BigInteger instrumentID) {
        return ((OrderBookMap.get(instrumentID).BuySide.nOrders > 0) && (OrderBookMap.get(instrumentID).SellSide.nOrders > 0));
    }

    private Integer volumeOnSide(BigInteger instrumentID, OrderSide Side) {
        if (Side.equals(OrderSide.Buy)) {
            return OrderBookMap.get(instrumentID).BuySide.getVolume();
        } else {
            return OrderBookMap.get(instrumentID).SellSide.getVolume();
        }
    }

    private  int getVolumeAtPrice(BigInteger instrumentID, OrderSide Side, BigDecimal Price) {
        int Volume = 0;
        if (Side.equals(OrderSide.Buy)) {
            if (OrderBookMap.get(instrumentID).BuySide.priceExists(Price.setScale(3, RoundingMode.HALF_UP))) {
                Volume = OrderBookMap.get(instrumentID).BuySide.getLimit(Price.setScale(3, RoundingMode.HALF_UP)).getVolume();
            }
        } else {
            if (OrderBookMap.get(instrumentID).SellSide.priceExists(Price.setScale(3, RoundingMode.HALF_UP))) {
                Volume = OrderBookMap.get(instrumentID).SellSide.getLimit(Price.setScale(3, RoundingMode.HALF_UP)).getVolume();
            }
        }
        return Volume;
    }
}
