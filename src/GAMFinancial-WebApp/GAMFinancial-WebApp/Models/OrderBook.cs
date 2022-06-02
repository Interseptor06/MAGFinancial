using System.Numerics;

namespace GAMFinancial_WebApp.Models
{
    public class OrderBook
    {
        public OrderTree BuySide { get; private set; }
        public OrderTree SellSide { get; private set; }

        public OrderBook()
        {
            BuySide = new OrderTree();
            SellSide = new OrderTree();
        }

        public decimal getBestBid()
        {
            return BuySide.maxPrice();
        }

        public decimal getWorstBid()
        {
            return BuySide.minPrice();
        }

        public decimal getBestOffer()
        {
            return SellSide.minPrice();
        }

        public decimal getWorstOffer()
        {
            return SellSide.maxPrice();
        }

        public decimal getSpread()
        {
            return this.BuySide.minPrice() - this.SellSide.maxPrice();
        }

        public decimal getMid()
        {
            return this.getBestBid() + Math.Ceiling(this.getSpread() / 2);
        }

        public bool bidsAndAsksExist()
        {
            return ((this.BuySide.nOrders > 0) && (this.SellSide.nOrders > 0));
        }

        public int volumeOnSide(OrderSide Side)
        {
            if (Side.Equals(OrderSide.Buy))
            {
                return this.BuySide.getVolume();
            }
            else
            {
                return this.SellSide.getVolume();
            }
        }

        public int getVolumeAtPrice(OrderSide Side, decimal Price)
        {
            int Volume = 0;
            if (Side.Equals(OrderSide.Buy))
            {
                if (BuySide.priceExists(Math.Round(Price, 3, MidpointRounding.AwayFromZero)))
                {
                    Volume = BuySide.getLimit(Math.Round(Price, 3, MidpointRounding.AwayFromZero)).Volume;
                }
            }
            else
            {
                if (SellSide.priceExists(Math.Round(Price, 3, MidpointRounding.AwayFromZero)))
                {
                    Volume = SellSide.getLimit(Math.Round(Price, 3, MidpointRounding.AwayFromZero)).Volume;
                }
            }
            return Volume;
        }

        public void cancelOrder(BigInteger oId)
        {
            if (BuySide.orderExists(oId))
            {
                BuySide.removeOrderByID(oId);
            }
            else if (SellSide.orderExists(oId))
            {
                SellSide.removeOrderByID(oId);
            }
        }

        // Returns a report after the Add
        public void ProcessLimitOrder(Order incomingOrder)
        {
            OrderSide Side = incomingOrder.OrderSide;

            if (Side.Equals(OrderSide.Buy))
            {
                this.BuySide.insertOrder(incomingOrder);
            }
            else
            {
                this.SellSide.insertOrder(incomingOrder);
            }
        }

        public List<Order> GetTop30Buys()
        {
            var top30Buys = new List<Order>();

            int i = 0;
            foreach (var order in this.BuySide.OrderByDescending(x => x.Price))
            {
                if (i>=30)
                {
                    break;
                }
                top30Buys.Add(order);
                i++;
            }

            return top30Buys.ToList();
        }

        public List<Order> GetTop30Sales()
        {
            var top30Sales = new List<Order>();

            int i = 0;
            foreach (var order in this.SellSide.OrderBy(x => x.Price))
            {
                if (i>=30)
                {
                    break;
                }
                top30Sales.Add(order);
                i++;
            }

            return top30Sales.ToList();
        }
    }
}
