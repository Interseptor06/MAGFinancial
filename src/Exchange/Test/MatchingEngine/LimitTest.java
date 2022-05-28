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
// Testing Functionality Instead Of Getters and Setters
class LimitTest {
    BigDecimal LimitPrice;
    Limit TestLimit;
    Order TestBuyOrder_First;
    Order TestBuyOrder_Second;
    Order TestBuyOrder_Third;

    @BeforeEach
    void setUp(){
        LimitPrice = BigDecimal.valueOf(20.112);
        TestLimit = new Limit(LimitPrice);
        TestBuyOrder_First = new Order(OrderSide.Buy, OrderClass.Limit, 20,
                LimitPrice, BigInteger.valueOf(1), BigInteger.valueOf(100), BigInteger.valueOf(1));
        TestBuyOrder_Second = new Order(OrderSide.Buy, OrderClass.Limit, 30,
                LimitPrice, BigInteger.valueOf(2), BigInteger.valueOf(101), BigInteger.valueOf(2));
        TestBuyOrder_Third = new Order(OrderSide.Buy, OrderClass.Limit, 40,
                LimitPrice, BigInteger.valueOf(3), BigInteger.valueOf(102), BigInteger.valueOf(3));

        TestLimit.appendOrder(TestBuyOrder_First);
        TestLimit.appendOrder(TestBuyOrder_Second);
        //TestLimit.appendOrder(TestBuyOrder_Third);
    }

    @Test
    void appendOrder() {
        TestLimit.appendOrder(TestBuyOrder_Third);
        assertEquals(TestLimit.getLength(), 3);
        assertEquals(TestLimit.getTailOrder(), TestBuyOrder_Third);
        assertEquals(TestLimit.getVolume(), TestBuyOrder_First.getQuantity() + TestBuyOrder_Second.getQuantity() + TestBuyOrder_Third.getQuantity());
    }

    @Test
    void removeOrder() {
        TestLimit.appendOrder(TestBuyOrder_Third);
        TestLimit.removeOrder(TestBuyOrder_Third);
        assertEquals(TestLimit.getLength(), 2);
        assertEquals(TestLimit.getTailOrder(), TestBuyOrder_Second);
        assertEquals(TestLimit.getVolume(), TestBuyOrder_First.getQuantity() + TestBuyOrder_Second.getQuantity());
    }

    @Test
    void moveTail() {
        TestLimit.appendOrder(TestBuyOrder_Third);
        TestLimit.movetoTail(TestBuyOrder_First);
        assertEquals(TestLimit.getTailOrder(), TestBuyOrder_First);
        assertEquals(TestLimit.getHeadOrder(), TestBuyOrder_Second);
        assertNull(TestBuyOrder_First.getNextOrder());
        assertEquals(TestBuyOrder_First.getPreviousOrder(), TestBuyOrder_Third);
        TestLimit.movetoTail(TestBuyOrder_Third);// This is the second element(neither head nor tail)
        assertEquals(TestLimit.getTailOrder(), TestBuyOrder_Third);
        assertEquals(TestLimit.getHeadOrder(), TestBuyOrder_Second);
        assertNull(TestBuyOrder_Third.getNextOrder());
        assertEquals(TestBuyOrder_Third.getPreviousOrder(), TestBuyOrder_First);
    }

    @Test
    void testToString() {
    }

}