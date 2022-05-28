package Exchange.Main.MatchingEngine;

import java.util.ArrayList;

public class OrderReport {
    /*
     * Return after an parentOrder is submitted to the lob. Contains:
     * 	- trades:
     *
     * 	- orderInBook
     */
    private ArrayList<Trade> trades;
    private boolean orderInBook;
    private Order parentOrder;

    public OrderReport(ArrayList<Trade> trades,
                       boolean orderInBook) {
        this.trades = trades;
        this.orderInBook = orderInBook;
    }

    public Order getParentOrder() {
        return parentOrder;
    }

    public void setParentOrder(Order order) {
        this.parentOrder = order;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public boolean isOrderInBook() {
        return orderInBook;
    }

    public String toString() {
        StringBuilder retString = new StringBuilder("--- Order Report ---:\nTrades:\n");
        for (Trade t : trades) {
            retString.append("\n").append(t.toString());
        }
        retString.append("parentOrder in book? ").append(orderInBook).append("\n");
        retString.append("\nOrders:\n");
        retString.append(parentOrder.toString());
        return  retString + "\n--------------------------";
    }

}

