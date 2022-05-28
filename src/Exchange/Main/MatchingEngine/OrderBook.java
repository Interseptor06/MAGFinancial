package Exchange.Main.MatchingEngine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

/*
 * This is basically BuySideOrderTree, SellSideOrderTree + MatchingEngine
 * At Testing Time We'll See How Efficient This Crack-Pot Is
 *
 * We attempt to match the order BEFORE we insert it into the limit order book
 */

public class OrderBook {
    // This Gets Released To The Public In Post-Trading Hours
    ArrayList<Trade> Tape;
    OrderTree BuySide;
    OrderTree SellSide;

    public OrderBook() {
        BuySide = new OrderTree();
        SellSide = new OrderTree();
    }

    public BigDecimal getBestBid() {
        return BuySide.maxPrice();
    }

    public BigDecimal getWorstBid() {
        return BuySide.minPrice();
    }

    public BigDecimal getBestOffer() {
        return SellSide.minPrice();
    }

    public BigDecimal getWorstOffer() {
        return SellSide.maxPrice();
    }

    public BigDecimal getSpread() {
        return this.BuySide.minPrice().subtract(this.SellSide.maxPrice());
    }

    public BigDecimal getMid() {
        return this.getBestBid().add(this.getSpread().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP));
    }

    public boolean bidsAndAsksExist() {
        return ((this.BuySide.nOrders > 0) && (this.SellSide.nOrders > 0));
    }

    public Integer volumeOnSide(OrderSide Side) {
        if (Side.equals(OrderSide.Buy)) {
            return this.BuySide.getVolume();
        } else {
            return this.SellSide.getVolume();
        }
    }

    public int getVolumeAtPrice(OrderSide Side, BigDecimal Price) {
        int Volume = 0;
        if (Side.equals(OrderSide.Buy)) {
            if (BuySide.priceExists(Price.setScale(3, RoundingMode.HALF_UP))) {
                Volume = BuySide.getLimit(Price.setScale(3, RoundingMode.HALF_UP)).getVolume();
            }
        } else {
            if (SellSide.priceExists(Price.setScale(3, RoundingMode.HALF_UP))) {
                Volume = SellSide.getLimit(Price.setScale(3, RoundingMode.HALF_UP)).getVolume();
            }
        }
        return Volume;
    }

    public void cancelOrder(BigInteger oId) {
        if (BuySide.orderExists(oId)) {
            BuySide.removeOrderByID(oId);
        } else if (SellSide.orderExists(oId)) {
            SellSide.removeOrderByID(oId);
        }
    }



    /*
     * Review This God Damn Code
     * -----------------------------------------------------------------------------------------------------------
     */

    // Returns a report after the Add
    public OrderReport ProcessLimitOrder(Order incomingOrder) {
        boolean orderInBook; // False by default
        ArrayList<Trade> trades = new ArrayList<>();
        OrderSide Side = incomingOrder.getoSide();
        Integer qtyRemaining = incomingOrder.getQuantity(); // How much of the incoming order is still remaining
        BigDecimal price = incomingOrder.getPrice();
        if (Side.equals(OrderSide.Buy)) {
            while ((this.SellSide.getnOrders() > 0) && (qtyRemaining > 0) && (price.compareTo(SellSide.minPrice()) >= 0)) {
                Limit ordersAtBest = SellSide.minPriceList();
                qtyRemaining = processOrderList(trades, ordersAtBest, qtyRemaining, incomingOrder);
            }
            // If volume remains, add order to book
            if (qtyRemaining > 0) {
                //quote.setqId(this.nextQuoteID);
                incomingOrder.setQuantity(qtyRemaining);
                this.BuySide.insertOrder(incomingOrder);
                orderInBook = true;
            } else {
                orderInBook = false;
            }
        } else {

            while ((this.BuySide.getnOrders() > 0) && (qtyRemaining > 0) && (price.compareTo(BuySide.maxPrice()) <= 0)) {
                Limit ordersAtBest = BuySide.maxPriceList();
                qtyRemaining = processOrderList(trades, ordersAtBest, qtyRemaining, incomingOrder);
            }
            // If volume remains, add to book
            if (qtyRemaining > 0) {
                incomingOrder.setQuantity(qtyRemaining);
                this.SellSide.insertOrder(incomingOrder);
                orderInBook = true;
            } else {
                orderInBook = false;
            }
        }
        OrderReport report = new OrderReport(trades, orderInBook);
        if (orderInBook) {
            report.setParentOrder(incomingOrder);
        }
        return report;
    }

    public OrderReport processMarketOrder(Order incomingOrder) {
        ArrayList<Trade> trades = new ArrayList<>();
        OrderSide side = incomingOrder.getoSide();
        Integer qtyRemaining = incomingOrder.getQuantity();
        if (side.equals(OrderSide.Buy)) {
            while ((qtyRemaining > 0) && (this.SellSide.getnOrders() > 0)) {
                Limit ordersAtBest = this.SellSide.minPriceList();
                qtyRemaining = processOrderList(trades, ordersAtBest, qtyRemaining, incomingOrder);
            }
        } else {
            while ((qtyRemaining > 0) && (this.BuySide.getnOrders() > 0)) {
                Limit ordersAtBest = this.BuySide.maxPriceList();
                qtyRemaining = processOrderList(trades, ordersAtBest, qtyRemaining, incomingOrder);
            }
        }
        return new OrderReport(trades, false);
    }

    private int processOrderList(ArrayList<Trade> trades, Limit orders,
                                 Integer qtyRemaining, Order incomingOrder) {
        OrderSide Side = incomingOrder.getoSide();
        BigInteger Buyer, Seller;
        BigInteger takerId = incomingOrder.getOrderID();
        long time = incomingOrder.getTimeStamp();
        while ((orders.getLength() > 0) && (qtyRemaining > 0)) {
            int qtyTraded = 0;
            Order headOrder = orders.getHeadOrder();
            if (qtyRemaining < headOrder.getQuantity()) {
                qtyTraded = qtyRemaining;
                if (Side.equals(OrderSide.Buy)) {
                    this.BuySide.updateOrderQuantity(headOrder.getQuantity() - qtyRemaining,
                            headOrder.getOrderID());
                } else {
                    this.SellSide.updateOrderQuantity(headOrder.getQuantity() - qtyRemaining,
                            headOrder.getOrderID());
                }
                qtyRemaining = 0;
            } else {
                qtyTraded = headOrder.getQuantity();
                if (Side.equals(OrderSide.Buy)) {
                    this.BuySide.removeOrderByID(headOrder.getOrderID());
                } else {
                    this.SellSide.removeOrderByID(headOrder.getOrderID());
                }
                qtyRemaining -= qtyTraded;
            }
            if (Side.equals(OrderSide.Buy)) {
                Buyer = headOrder.getCustomerID();
                Seller = takerId;
            } else {
                Buyer = takerId;
                Seller = headOrder.getCustomerID();
            }
            Trade trade = new Trade(time,
                    headOrder.getPrice(),
                    qtyTraded,
                    headOrder.getCustomerID(),
                    takerId,
                    Buyer,
                    Seller);
            trades.add(trade);
            this.Tape.add(trade);
        }
        return qtyRemaining;
    }

    public OrderReport processOrder(Order incomingOrder) {
        boolean isLimit = incomingOrder.getoClass().equals(OrderClass.Limit);
        OrderReport oReport;
        if (incomingOrder.getQuantity() <= 0 ) {
            throw new IllegalArgumentException("processOrder() given qty <= 0");
        }
        if (isLimit) {
            //double clippedPrice = clipPrice(incomingOrder.getPrice());
            //incomingOrder.setPrice(incomingOrder);
            oReport = ProcessLimitOrder(incomingOrder);
        } else {
            oReport = processMarketOrder(incomingOrder);
        }
        return oReport;
    }

}
