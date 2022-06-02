using GAMFinancial_WebApp.Models;

namespace GAMFinancial_WebApp.Seeders
{
    public class OrdersSeeder
    {
        public void SeedOredersAsync(OrderBook orderBook)
        {
            Random random = new Random();

            for (int i = 0; i < 100; i++)
            {
                var order = new Order(i < 50 ? OrderSide.Sell : OrderSide.Buy, OrderClass.Limit, random.Next(50), (decimal)random.Next(1000)/100, random.Next(int.MaxValue), random.Next(int.MaxValue), random.Next(int.MaxValue), true);

                if (i<50)
                {
                    orderBook.SellSide.insertOrder(order);
                }
                else
                {
                    orderBook.BuySide.insertOrder(order);
                }
            }
            Console.WriteLine();
        }
    }
}
