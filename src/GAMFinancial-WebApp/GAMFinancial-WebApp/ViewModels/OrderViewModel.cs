using System.Numerics;

namespace GAMFinancial_WebApp.ViewModels
{
    public class OrderViewModel
    {
        public int OrderID { get; set; }

        public decimal Price { get; set; }

        public int Quantity { get; set; }

        public bool IsNew { get; set; } = false;
    }
}
