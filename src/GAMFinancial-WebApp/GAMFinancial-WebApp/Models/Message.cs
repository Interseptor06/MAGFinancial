using System.Numerics;

namespace GAMFinancial_WebApp.Models
{
    public class Message
    {
        private MessageType Type; // Add or Delete
        private OrderSide oSide; // Buy or Sell
        private OrderClass oClass; // Market or Limit
        private int Quantity;
        private decimal Price;
        private BigInteger oldOrderID;
        private BigInteger orderID;
        private BigInteger customerID;
        private BigInteger instrumentID;
        private readonly string messageString;

        public Message(string message)
        {
            messageString = message;
            this.orderID = new Random().Next(int.MaxValue);
        }

        public MessageType getType()
        {
            return Type;
        }

        public void setType(MessageType type)
        {
            Type = type;
        }

        public OrderSide getoSide()
        {
            return oSide;
        }

        public void setoSide(OrderSide oSide)
        {
            this.oSide = oSide;
        }

        public OrderClass getoClass()
        {
            return oClass;
        }

        public void setoClass(OrderClass oClass)
        {
            this.oClass = oClass;
        }

        public int getQuantity()
        {
            return Quantity;
        }

        public void setQuantity(int quantity)
        {
            Quantity = quantity;
        }

        public decimal getPrice()
        {
            return Price;
        }

        public void setPrice(decimal price)
        {
            Price = price;
        }

        public BigInteger getOldOrderID()
        {
            return oldOrderID;
        }

        public void setOldOrderID(BigInteger oldOrderID)
        {
            this.oldOrderID = oldOrderID;
        }

        public BigInteger getOrderID()
        {
            return orderID;
        }

        public void setOrderID(BigInteger orderID)
        {
            this.orderID = orderID;
        }

        public BigInteger getCustomerID()
        {
            return customerID;
        }

        public void setCustomerID(BigInteger customerID)
        {
            this.customerID = customerID;
        }

        public BigInteger getInstrumentID()
        {
            return instrumentID;
        }

        public void setInstrumentID(BigInteger instrumentID)
        {
            this.instrumentID = instrumentID;
        }

        public String getMessage()
        {
            return messageString;
        }
    }
}
