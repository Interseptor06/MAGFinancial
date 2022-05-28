package Exchange.Test.MatchingEngine;

import Exchange.Main.MatchingEngine.Limit;
import Exchange.Main.MatchingEngine.Order;
import Exchange.Main.MatchingEngine.OrderClass;
import Exchange.Main.MatchingEngine.OrderSide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    BigDecimal LimitPrice;
    Limit TestLimit;
    Order TestBuyOrder_First;
    Order TestBuyOrder_Second;
    @BeforeEach
    void setUp(){
        LimitPrice = BigDecimal.valueOf(20.112);
        TestLimit = new Limit(LimitPrice);
        TestBuyOrder_First = new Order(OrderSide.Buy, OrderClass.Limit, 20,
                LimitPrice, BigInteger.valueOf(1), BigInteger.valueOf(100), BigInteger.valueOf(1));
        TestBuyOrder_Second = new Order(OrderSide.Buy, OrderClass.Limit, 30,
                LimitPrice, BigInteger.valueOf(2), BigInteger.valueOf(101), BigInteger.valueOf(2));

        TestLimit.appendOrder(TestBuyOrder_First);
        TestLimit.appendOrder(TestBuyOrder_Second);
    }

    @Test
    void getParentLimit() {
        BigDecimal lPrice = TestBuyOrder_First.getParentLimit().getLimitPrice();
        assertEquals(lPrice, LimitPrice);
    }

    @Test
    void getoSide() {
        assertEquals(TestBuyOrder_First.getoSide(), OrderSide.Buy);
    }

    @Test
    void getoClass() {
        assertEquals(TestBuyOrder_First.getoClass(), OrderClass.Limit);
    }

    @Test
    void getQuantity() {
        TestBuyOrder_First.setQuantity(15);
        assertEquals(TestBuyOrder_First.getQuantity(), 15);
    }

    @Test
    void getPrice() {
        assertEquals(TestBuyOrder_First.getPrice(), LimitPrice);
    }

    @Test
    void getOrderID() {
        assertEquals(TestBuyOrder_First.getOrderID(), BigInteger.valueOf(1));
    }

    @Test
    void getCustomerID() {
        assertEquals(TestBuyOrder_First.getCustomerID(), BigInteger.valueOf(100));
    }

    @Test
    void getPreviousOrder() {
        assertEquals(TestBuyOrder_First.getPreviousOrder(), TestBuyOrder_Second);
    }

    @Test
    void getNextOrder() {
        assertNull(TestBuyOrder_First.getPreviousOrder());
    }

    @Test
    void testToString() {
        System.out.println(TestBuyOrder_First.toString());
    }

    @Test
    void setOrderID() {
        TestBuyOrder_First.setOrderID(BigInteger.valueOf(2));
        assertEquals(TestBuyOrder_First.getOrderID(), BigInteger.valueOf(2));
    }

    @Test
    void setQuantity() {
        TestBuyOrder_First.setQuantity(10);
        assertEquals(TestBuyOrder_First.getQuantity(), 10);
    }

    @Test
    void updateQuantity() {
        long time = System.nanoTime();
        TestBuyOrder_First.updateQuantity(50, time);
        assertNull(TestBuyOrder_First.getNextOrder());
        assertEquals(TestBuyOrder_First.getQuantity(), 50);
        assertEquals(TestBuyOrder_First.getTimeStamp(), time);
    }
}