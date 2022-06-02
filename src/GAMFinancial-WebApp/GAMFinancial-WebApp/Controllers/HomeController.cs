using GAMFinancial_WebApp.Models;
using GAMFinancial_WebApp.ViewModels;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;

namespace GAMFinancial_WebApp.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly OrderBook orderBook;

        public HomeController(ILogger<HomeController> logger, OrderBook orderBook)
        {
            _logger = logger;
            this.orderBook = orderBook;
        }

        public IActionResult Index()
        {
            var buys = orderBook.GetTop30Buys()
                .Select(x => new OrderViewModel
                {
                    OrderID = (int)x.OrderID,
                    Price = x.Price,
                    Quantity = x.Quantity,
                }).ToList();
            var sales = orderBook.GetTop30Sales()
                .Select(x => new OrderViewModel
                {
                    OrderID = (int)x.OrderID,
                    Price = x.Price,
                    Quantity = x.Quantity,
                }).ToList();

            return View(new IndexViewModel(sales, buys));
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}