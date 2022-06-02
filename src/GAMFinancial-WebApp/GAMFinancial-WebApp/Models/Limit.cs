using java.lang;
using java.util;
using java.util.function;
using System.Collections;

namespace GAMFinancial_WebApp.Models
{
    public class Limit
    {
        public Limit(decimal limitPrice)
        {
            this.LimitPrice = limitPrice;
            this.Volume = 0;
            this.Length = 0;

            this.HeadOrder = null;
            this.TailOrder = null;
        }

        public int Id { get; set; }

        public decimal LimitPrice { get; set; }

        public int Length { get; set; }

        public int Volume { get; set; }

        public Order? HeadOrder { get; set; }

        public Order? TailOrder { get; set; }

        public void appendOrder(Order incomingOrder)
        {
            incomingOrder.ParentLimit = this;
            if (Length.Equals(0))
            {
                incomingOrder.NextOrder = null;
                incomingOrder.PreviousOrder = null;
                HeadOrder = incomingOrder;
                TailOrder = incomingOrder;
            }
            else
            {
                incomingOrder.PreviousOrder = this.TailOrder;
                incomingOrder.NextOrder = null;
                this.TailOrder.NextOrder = incomingOrder; // LastAddedOrder.setNext(incomingOrder)
                TailOrder = incomingOrder;
            }
            Length+=1;
            Volume += incomingOrder.Quantity;
        }
        public void removeOrder(Order order)
        {
            if (this.Length == 0) 
            {
                throw new NoSuchElementException("Order Doesn't Exist");
            }

            Order tempNextOrder = order.NextOrder;
            Order tempPrevOrder = order.PreviousOrder;
            if ((tempNextOrder != null) && (tempPrevOrder != null)) 
            {
                tempNextOrder.PreviousOrder = tempPrevOrder;
                tempPrevOrder.NextOrder = tempNextOrder;
            } 
            else if (tempNextOrder != null)
            {
                    tempNextOrder.PreviousOrder = null;
                    this.HeadOrder = tempNextOrder;
            } 
            else if (tempPrevOrder != null)
            {
                tempPrevOrder.NextOrder = null;
                this.TailOrder = tempPrevOrder;
            }
            this.Volume -= order.Quantity;
            this.Length -= 1;
        }
        public void movetoTail(Order order)
        {
            /*
             * Move 'order' to the tail of the list (after modification for e.g.)
             */
            if (order.PreviousOrder != null)
            {
                order.PreviousOrder.NextOrder = order.NextOrder;
            }
            else
            {
                // Update head order
                this.HeadOrder = order.NextOrder;
            }
            order.NextOrder.PreviousOrder = order.PreviousOrder;
            // Set the previous tail's next order to this order
            this.TailOrder.NextOrder = order;
            order.PreviousOrder = this.TailOrder;
            this.TailOrder = order;
            order.NextOrder = null;
        }
    }
}
