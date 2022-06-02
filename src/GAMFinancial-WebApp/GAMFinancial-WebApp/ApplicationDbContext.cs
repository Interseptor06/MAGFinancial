using Microsoft.EntityFrameworkCore;

namespace GAMFinancial_WebApp
{
    public class ApplicationDbContext : DbContext
    {
        private readonly string connectionString;

        public ApplicationDbContext()
        {
            this.connectionString = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .Build()
                .GetConnectionString("DefaultConnection");
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .Build();

            optionsBuilder.UseSqlServer(this.connectionString);
        }


    }
}
