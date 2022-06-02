using GAMFinancial_WebApp;
using GAMFinancial_WebApp.Models;
using GAMFinancial_WebApp.Seeders;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using System.Text.Json.Serialization;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews().AddJsonOptions(options => {
    options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles;
});
builder.Services.AddServerSideBlazor();
builder.Services.AddDbContext<ApplicationDbContext>();
builder.Services.AddSingleton<OrderBook>();

var app = builder.Build();

// Configure the HTTP request pipeline.
using (var serviceScope = app.Services.CreateScope())
{
    /*var dbContext = serviceScope.ServiceProvider.GetRequiredService<ApplicationDbContext>();

    dbContext.Database.Migrate();*/
    var orderBook = serviceScope.ServiceProvider.GetService<OrderBook>();
    new OrdersSeeder().SeedOredersAsync(orderBook);
}

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.UseEndpoints(e =>
{
    e.MapBlazorHub();
});

app.Run();
