using GAMFinancial_WebApp.Models;

namespace GAMFinancial_WebApp.ViewModels
{
    public class IndexViewModel
    {
        public IndexViewModel(List<OrderViewModel> sales, List<OrderViewModel> buys)
        {
            this.Sales = sales;
            this.Buys = buys;
        }

        public List<OrderViewModel> Sales { get; set; }

        public List<OrderViewModel> Buys { get; set; }
    }
}
