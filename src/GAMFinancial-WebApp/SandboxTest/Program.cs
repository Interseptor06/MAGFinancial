using GAMFinancial_WebApp.Models;

OrderTree orderTree = new OrderTree();

Random random = new Random();


for (int i = 0; i < 100; i++)
{
    Order order = new Order(OrderSide.Sell, OrderClass.Market, 50, 1.5m, random.Next(int.MaxValue), random.Next(int.MaxValue), random.Next(int.MaxValue), true);
    orderTree.insertOrder(order);
}
Console.WriteLine();

