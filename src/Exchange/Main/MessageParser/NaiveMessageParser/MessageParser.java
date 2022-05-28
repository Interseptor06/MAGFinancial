package Exchange.Main.MessageParser.NaiveMessageParser;

import Exchange.Main.MatchingEngine.OrderClass;
import Exchange.Main.MatchingEngine.OrderSide;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

public class MessageParser {
    public static Message ParseMSG(String strMSG){
        Message ResMsg = new Message(strMSG);
        String[] SplitMSG = strMSG.split("/");
        if(SplitMSG.length == 6 || SplitMSG.length == 3) {
            throw new IllegalArgumentException("Number of arguments should be 6 for Add and 4 for Cancel Messages");
        }
        if(SplitMSG[0].equals("Add")){
            try {
                SplitMSG[1] = SplitMSG[1].split("=")[1];
                SplitMSG[2] = SplitMSG[2].split("=")[1];
                SplitMSG[3] = SplitMSG[3].split("=")[1];
                SplitMSG[4] = SplitMSG[4].split("=")[1];
                SplitMSG[5] = SplitMSG[5].split("=")[1];
                SplitMSG[6] = SplitMSG[6].split("=")[1];
            }
            catch (Exception ignored){
                throw new IllegalArgumentException("Wrong Format");
            }

            if(SplitMSG[1].equals("Buy")){
                ResMsg.setoSide(OrderSide.Buy);
            } else if (SplitMSG[1].equals("Sell")) {
                ResMsg.setoSide(OrderSide.Sell);
            } else {
                throw new IllegalArgumentException("Only Buy and Sell Operations Supported");
            }

            if(SplitMSG[2].equals("Market")){
                ResMsg.setoClass(OrderClass.Market);
            } else if (SplitMSG[2].equals("Limit")) {
                ResMsg.setoClass(OrderClass.Limit);
            } else {
                throw new IllegalArgumentException("Only Market and Limit Orders Supported");
            }
            ResMsg.setType(MessageType.Add);
            ResMsg.setQuantity(Integer.valueOf(SplitMSG[3]));
            ResMsg.setPrice(new BigDecimal(SplitMSG[4]));
            ResMsg.setCustomerID(BigInteger.valueOf(Long.parseLong(SplitMSG[5])));
            ResMsg.setInstrumentID(BigInteger.valueOf(Long.parseLong(SplitMSG[6])));
        }
        else if (SplitMSG[0].equals("Delete")) {
            try {
                SplitMSG[1] = SplitMSG[1].split("=")[1];
                SplitMSG[2] = SplitMSG[2].split("=")[1];
                SplitMSG[3] = SplitMSG[3].split("=")[1];
            }
            catch (Exception ignored){
                throw new IllegalArgumentException("Wrong Format");
            }
            ResMsg.setType(MessageType.Add);
            ResMsg.setOldOrderID(BigInteger.valueOf(Long.parseLong(SplitMSG[1])));
            ResMsg.setInstrumentID(BigInteger.valueOf(Long.parseLong(SplitMSG[2])));
            ResMsg.setCustomerID(BigInteger.valueOf(Long.parseLong(SplitMSG[3])));
        }
        else {
            throw new IllegalArgumentException("Operation should be add or cancel, not " + SplitMSG[0]);
        }
        return ResMsg;
    }
    @Test
    public void TestAdd(){
        String MSG = "Add/Side=Buy/Class=Limit/Quantity=200/Price=200.010/customerID=10001/instrumentID=10001";
        Message msgObject = ParseMSG(MSG);
        System.out.println(msgObject.getType());
        assertEquals(msgObject.getoSide(), OrderSide.Buy);
        assertEquals(msgObject.getoClass(), OrderClass.Limit);
        assertEquals(msgObject.getQuantity(), 200);
        assertEquals(msgObject.getPrice(), new BigDecimal("200.010"));
        assertEquals(msgObject.getCustomerID(), BigInteger.valueOf(10001));
        assertEquals(msgObject.getInstrumentID(), BigInteger.valueOf(10001));
    }
    @Test
    public void TestCancel(){
        String MSG = "Delete/oldOrderID=1001/customerID=10001/instrumentID=10001";
        Message msgObject = ParseMSG(MSG);
        System.out.println(msgObject.getType());
        assertEquals(msgObject.getOldOrderID(), BigInteger.valueOf(1001));
        assertEquals(msgObject.getCustomerID(), BigInteger.valueOf(10001));
        assertEquals(msgObject.getInstrumentID(), BigInteger.valueOf(10001));
    }
}
