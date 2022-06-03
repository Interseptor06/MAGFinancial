package Banking.Business;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class Account {
    private final BigInteger AccountNumber; //  Bank generated, locally valid
    private final BigInteger IBAN; // For international transactions
    private BigDecimal CurrentBalance;
    private AccountHolder holder;
    private ArrayList<Transaction> transactionHistory;
    private final String CurrencyName;

    public Account(BigInteger accountNumber, BigInteger IBAN, String currencyName, AccountHolder accountHolder) {
        holder = accountHolder;
        AccountNumber = accountNumber;
        this.IBAN = IBAN;
        CurrencyName = currencyName;
        transactionHistory = new ArrayList<>(1000);
    }

    public Account(String firstName, String middleName, String lastName, String address, String country, String city, String phone, String email, BigInteger accountNumber, BigInteger IBAN, String currencyName) {
        CurrencyName = currencyName;
        this.holder = new AccountHolder(firstName, middleName, lastName, address, country, city, phone, email);
        AccountNumber = accountNumber;
        this.IBAN = IBAN;
        transactionHistory = new ArrayList<>(1000);
    }

    public void printTransactionHistory(){
        for (Transaction x: transactionHistory) {
            System.out.println(x.toString());
        }
    }

    public void addTransaction(BigDecimal amount, BigInteger inAccountID, BigInteger fromAccountID){
        transactionHistory.add(new Transaction(amount, inAccountID, fromAccountID));
    }

    public ArrayList<Transaction> getTransactionHistory(){
        return transactionHistory;
    }

    public BigInteger getAccountNumber() {
        return AccountNumber;
    }

    public BigInteger getIBAN() {
        return IBAN;
    }

    public BigDecimal getCurrentBalance() {
        return CurrentBalance;
    }

    public synchronized void setCurrentBalance(BigDecimal currentBalance) {
        CurrentBalance = currentBalance;
    }

    public synchronized String getFirstName() {
        return holder.getFirstName();
    }

    public synchronized String getMiddleName() {
        return holder.getMiddleName();
    }

    public synchronized String getLastName() {
        return holder.getLastName();
    }

    public synchronized String getAddress() {
        return holder.getAddress();
    }

    public synchronized String getCountry() {
        return holder.getCountry();
    }

    public synchronized String getCity() {
        return holder.getCity();
    }

    public synchronized String getPhone() {
        return holder.getPhone();
    }

    public synchronized String getEmail() {
        return holder.getEmail();
    }

    public synchronized void setFirstName(String firstName) {
        holder.setFirstName(firstName);
    }

    public synchronized void setMiddleName(String middleName) {
        holder.setMiddleName(middleName);
    }

    public synchronized void setLastName(String lastName) {
        holder.setLastName(lastName);
    }

    public synchronized void setAddress(String address) {
        holder.setAddress(address);
    }

    public synchronized void setCountry(String country) {
        holder.setCountry(country);
    }

    public synchronized void setCity(String city) {
        holder.setCity(city);
    }

    public synchronized void setPhone(String phone) {
        holder.setPhone(phone);
    }

    public synchronized void setEmail(String email) {
        holder.setEmail(email);
    }

    public String getCurrencyName() {
        return CurrencyName;
    }
}
