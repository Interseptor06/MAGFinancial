using System.Collections;
using System.Numerics;

namespace GAMFinancial_WebApp.Models
{
    public class OrderTree : IEnumerable<Order>
    {
        SortedDictionary<decimal, Limit> priceTree;
        Dictionary<decimal, Limit> priceMap;
        Dictionary<BigInteger, Order> orderMap;
        int Volume;
        public int nOrders { get; private set; }
        int Depth;


        // CHANGED: Constructor to public
        public OrderTree()
        {
            priceTree = new SortedDictionary<decimal, Limit>();
            priceMap = new Dictionary<decimal, Limit>();
            orderMap = new Dictionary<BigInteger, Order>();

            Volume = 0;
            nOrders = 0;
            Depth = 0;
        }

        public int length()
        {
            return orderMap.Count;
        }

        /*
         * Returns the OrderList object associated with 'price'
         */
        public Limit getLimit(decimal price)
        {
            if (priceMap.ContainsKey(price))
            {
                return priceMap[price];
            }
            throw new KeyNotFoundException($"There is no order with price {price}");
        }

        /*
         * Returns the order given the order id
         */
        public Order getOrder(BigInteger id)
        {
            if (orderMap.ContainsKey(id))
            {
                return orderMap[id];
            }
            throw new KeyNotFoundException($"There is no order with id {id}");
        }

        public void createPrice(decimal price)
        {
            Depth += 1;
            Limit newList = new Limit(price);
            priceTree.Add(price, newList);
            priceMap.Add(price, newList);
        }

        public void removePrice(decimal price)
        {
            Depth -= 1;
            priceTree.Remove(price);
            priceMap.Remove(price);
        }

        public bool priceExists(decimal price)
        {
            return priceMap.ContainsKey(price);
        }

        public bool orderExists(BigInteger id)
        {
            return orderMap.ContainsKey(id);
        }

        public void insertOrder(Order incomingOrder)
        {
            BigInteger incomingOrderOrderID = incomingOrder.OrderID;
            decimal incomingOrderPrice = incomingOrder.Price;
            if (orderExists(incomingOrderOrderID))
            {
                removeOrderByID(incomingOrderOrderID);
            }
            nOrders += 1;
            if (!priceExists(incomingOrderPrice))
            {
                createPrice(incomingOrderPrice);
            }
            incomingOrder.ParentLimit = priceMap[incomingOrderPrice];
            priceMap[incomingOrderPrice].appendOrder(incomingOrder);
            orderMap.Add(incomingOrderOrderID, incomingOrder);
            Volume += incomingOrder.Quantity;
        }

        public void updateOrderQuantity(int newQuantity, BigInteger incomingOrderID)
        {
            Order order = this.orderMap[incomingOrderID];
            int initialVolume = order.Quantity;
            order.updateQuantity(newQuantity, order.TimeStamp);
            this.Volume += (order.Quantity - initialVolume);
        }

        public void removeOrderByID(BigInteger id)
        {
            Order order = orderMap[id];
            this.Volume -= order.Quantity;
            order.ParentLimit.removeOrder(order);
            if (order.ParentLimit.Length == 0)
            {
                this.removePrice(order.Price);
            }
            this.orderMap.Remove(id);
            this.nOrders -= 1;
        }

        public decimal maxPrice()
        {
            if (this.Depth > 0)
            {
                return this.priceTree.Last().Key;
            }
            else
            {
                throw new NullReferenceException("There are no orders in this limit");
            }
        }

        public decimal minPrice()
        {
            if (this.Depth > 0)
            {
                return this.priceTree.First().Key;
            }
            else
            {
                throw new NullReferenceException("There are no orders in this limit");
            }
        }

        public Limit maxPriceList()
        {
            if (this.Depth > 0)
            {
                return this.getLimit(maxPrice());
            }
            else
            {
                return null;
            }
        }

        public Limit minPriceList()
        {
            if (this.Depth > 0)
            {
                return this.getLimit(minPrice());
            }
            else
            {
                return null;
            }
        }

        public int getVolume()
        {
            return Volume;
        }

        public int getnOrders()
        {
            return nOrders;
        }

        public int getDepth()
        {
            return Depth;
        }

        public IEnumerator<Order> GetEnumerator()
        {
            foreach (var order in orderMap)
            {
                yield return order.Value;
            }
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }
    }
}
