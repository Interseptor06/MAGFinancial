using System.ComponentModel.DataAnnotations;
using System.Numerics;

namespace GAMFinancial_WebApp.Models
{
    public class Order
    {
        public Order(OrderSide oSide, OrderClass oClass, int quantity,
                 decimal price, BigInteger orderID, BigInteger customerID, BigInteger instrumentID, bool isNew)
        {
            TimeStamp = DateTime.UtcNow.Ticks * 100; // Time in nanosconds
            this.OrderSide = oSide;
            this.OrderClass = oClass;
            this.Quantity = quantity;
            this.Price = Math.Round(price, 3); // Rounds to the nearest thousandth
            this.OrderID = orderID;
            this.CustomerID = customerID;
            this.PreviousOrder = null;
            this.NextOrder = null;
            this.InstrumentID = instrumentID;
            this.IsNew=isNew;
        }

        [Key]
        public BigInteger OrderID { get; set; }

        public bool IsNew { get; set; }

        public long TimeStamp { get; set; }

        public OrderSide OrderSide { get; }

        public OrderClass OrderClass { get; }

        public int Quantity { get; set; }

        public decimal Price { get; }
        

        public BigInteger CustomerID { get; set; }

        public BigInteger InstrumentID { get; set; }

        public Order? PreviousOrder { get; set; }

        public Order? NextOrder { get; set; }

        public Limit? ParentLimit { get; set; }

        public void updateQuantity(int newQuantity, long TimeStamp)
        {
            if ((newQuantity > this.Quantity) && (this.ParentLimit.TailOrder != this))
            {
                // Move order to the end of the list. i.e. loses time priority
                this.ParentLimit.movetoTail(this);
                this.TimeStamp = TimeStamp;
            }
            this.ParentLimit.Volume = this.ParentLimit.Volume-(this.Quantity- newQuantity);
            this.Quantity = newQuantity;
        }
    }
}
