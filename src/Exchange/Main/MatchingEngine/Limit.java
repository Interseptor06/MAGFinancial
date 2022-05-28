package Exchange.Main.MatchingEngine;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.NoSuchElementException;

// LinkedList
// Tail.getNext = null always
public class Limit implements Iterable<Order>, Iterator<Order> {
    private BigDecimal LimitPrice;
    private Integer Volume; // Number of outstanding shares
    private Integer Length; // Number of outstanding orders

    private Order HeadOrder;
    private Order TailOrder;
    private Order Last; // last = last element that was returned by an iterator-returning-function

    public Limit(BigDecimal LimitPrice){
        this.LimitPrice = LimitPrice;
        Volume = 0;
        Length = 0;

        HeadOrder = null;
        TailOrder = null;
        Last = null;
    }

    @Override
    public Iterator<Order> iterator() {
        this.Last = HeadOrder;
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.Last != null;
    }

    @Override
    public Order next() {
        if (this.Last == null) {
            throw new NoSuchElementException();
        }
        Order returnVal = this.Last;
        this.Last = this.Last.getNextOrder();
        return returnVal;
    }
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void appendOrder(Order incomingOrder){
        incomingOrder.setParentLimit(this);
        if (Length.equals(0)) {
            incomingOrder.setNextOrder(null);
            incomingOrder.setPreviousOrder(null);
            HeadOrder = incomingOrder;
            TailOrder = incomingOrder;
        } else{
            incomingOrder.setPreviousOrder(TailOrder);
            incomingOrder.setNextOrder(null);
            TailOrder.setNextOrder(incomingOrder); // LastAddedOrder.setNext(incomingOrder)
            TailOrder = incomingOrder;
        }
        Length+=1;
        Volume += incomingOrder.getQuantity();
    }
    public void removeOrder(Order order) throws NoSuchElementException{
        if (this.Length == 0) {
            throw new NoSuchElementException("Order Doesn't Exist");
        }
        Order tempNextOrder = order.getNextOrder();
        Order tempPrevOrder = order.getPreviousOrder();
        if ((tempNextOrder != null) && (tempPrevOrder != null)) {
            tempNextOrder.setPreviousOrder(tempPrevOrder);
            tempPrevOrder.setNextOrder(tempNextOrder);
        } else if (tempNextOrder != null){
            tempNextOrder.setPreviousOrder(null);
            this.HeadOrder = tempNextOrder;
        } else if (tempPrevOrder != null){
            tempPrevOrder.setNextOrder(null);
            this.TailOrder = tempPrevOrder;
        }
        this.Volume -= order.getQuantity();
        this.Length -= 1;
    }
    public void movetoTail(Order order) {
        /*
         * Move 'order' to the tail of the list (after modification for e.g.)
         */
        if (order.getPreviousOrder() != null) {
            order.getPreviousOrder().setNextOrder(order.getNextOrder());
        } else {
            // Update head order
            this.HeadOrder = order.getNextOrder();
        }
        order.getNextOrder().setPreviousOrder(order.getPreviousOrder());
        // Set the previous tail's next order to this order
        this.TailOrder.setNextOrder(order);
        order.setPreviousOrder(this.TailOrder);
        this.TailOrder = order;
        order.setNextOrder(null);
    }
    public String toString() {
        StringBuilder outString = new StringBuilder();
        for (Order o : this) {
            outString.append("| ").append(o.toString()).append("\n");
        }
        return outString.toString();
    }

    public Integer getVolume() {
        return Volume;
    }

    public Integer getLength() {
        return Length;
    }

    public BigDecimal getLimitPrice() {
        return LimitPrice;
    }

    public Order getHeadOrder() {
        return HeadOrder;
    }

    public Order getTailOrder() {
        return TailOrder;
    }

    public void setVolume(Integer volume) {
        Volume = volume;
    }
}
