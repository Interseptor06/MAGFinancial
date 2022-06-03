package Banking.Business;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Client {
    private AccountHolder ClientInfo;
    private long UUID;
    private short Pin;
    private HashMap<BigInteger, Account> Accounts; // <AccountNumber, newAccount>
    private ArrayList<Transaction> TransactionData;
    private Bank parentBank; // Has setter in case of entire account transfer to a different bank

    public Client(Bank ParentBank, short Pin, String firstName, String middleName, String lastName, String address, String country, String city, String phone, String email){
        parentBank = ParentBank;
        this.Pin = Pin;
        ClientInfo = new AccountHolder(firstName, middleName, lastName, address, country, city, phone, email);
        UUID = parentBank.getNewUID();
    }
    public Client(Bank ParentBank, short Pin, AccountHolder Holder){
        parentBank = ParentBank;
        this.Pin = Pin;
        ClientInfo = Holder;
        UUID = parentBank.getNewUID();
    }
    public long getUUID() {
        return UUID;
    }

    public short getPin() {
        return Pin;
    }

    public void setPin(short pin) {
        Pin = pin;
    }

    public Bank getParentBank() {
        return parentBank;
    }

    public void setParentBank(Bank parentBank) {
        this.parentBank = parentBank;
    }

    public void addAccount(Account newAccount){
        Accounts.put(newAccount.getAccountNumber(), newAccount);
    }
    public boolean deleteAccount(BigInteger oldAccountID, short _Pin){
        if(Pin == _Pin){
            Accounts.remove(oldAccountID);
            return true;
        }
        else{
            return false;
        }
    }
    public Set<BigInteger> getOrders(){
        return Accounts.keySet();
    }

    public ArrayList<Transaction> getTransactionHistory(BigInteger AccountID){
        return Accounts.get(AccountID).getTransactionHistory();
    }

    public BigInteger getAccountNumber(BigInteger AccountID) {
        return Accounts.get(AccountID).getAccountNumber();
    }

    public BigInteger getIBAN(BigInteger AccountID) {
        return Accounts.get(AccountID).getIBAN();
    }

    public BigDecimal getCurrentBalance(BigInteger AccountID) {
        return Accounts.get(AccountID).getCurrentBalance();
    }

    public synchronized void setCurrentBalance(BigDecimal currentBalance, BigInteger AccountID) {
        Accounts.get(AccountID).setCurrentBalance(currentBalance);
    }

    public synchronized String getFirstName(BigInteger AccountID) {
        return Accounts.get(AccountID).getFirstName();
    }

    public synchronized String getMiddleName(BigInteger AccountID) {
        return Accounts.get(AccountID).getMiddleName();
    }

    public synchronized String getLastName(BigInteger AccountID) {
        return Accounts.get(AccountID).getLastName();
    }

    public synchronized String getAddress(BigInteger AccountID) {
        return Accounts.get(AccountID).getAddress();
    }

    public synchronized String getCountry(BigInteger AccountID) {
        return Accounts.get(AccountID).getCountry();
    }

    public synchronized String getCity(BigInteger AccountID) {
        return Accounts.get(AccountID).getCity();
    }

    public synchronized String getPhone(BigInteger AccountID) {
        return Accounts.get(AccountID).getPhone();
    }

    public synchronized String getEmail(BigInteger AccountID) {
        return Accounts.get(AccountID).getEmail();
    }

    public synchronized void setFirstName(BigInteger AccountID, String firstName) {
        Accounts.get(AccountID).setFirstName(firstName);
    }

    public synchronized void setMiddleName(BigInteger AccountID, String middleName) {
        Accounts.get(AccountID).setMiddleName(middleName);
    }

    public synchronized void setLastName(BigInteger AccountID, String lastName) {
        Accounts.get(AccountID).setLastName(lastName);
    }

    public synchronized void setAddress(BigInteger AccountID, String address) {
        Accounts.get(AccountID).setAddress(address);
    }

    public synchronized void setCountry(BigInteger AccountID, String country) {
        Accounts.get(AccountID).setCountry(country);
    }

    public synchronized void setCity(BigInteger AccountID, String city) {
        Accounts.get(AccountID).setCity(city);
    }

    public synchronized void setPhone(BigInteger AccountID, String phone) {
        Accounts.get(AccountID).setPhone(phone);
    }

    public synchronized void setEmail(BigInteger AccountID, String email) {
        Accounts.get(AccountID).setEmail(email);
    }

    public String getCurrencyName(BigInteger AccountID) {
        return Accounts.get(AccountID).getCurrencyName();
    }
}
