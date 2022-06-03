package Banking.Business;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {
    public AtomicInteger LastAccountNumber;
    private String Name;
    private final BigInteger BankNumber;
    private HashMap<Long, Client> Users; // UUID, Client
    private HashMap<BigInteger, Account> Accounts; //<accountID, Account>
    private ArrayList<Transaction> TransactionLog; // Just the transactions in the order that they were submitted by
    private final BankManager parentBankManager;

    public Bank(String name, BigInteger bankNumber, BankManager ParentManager) {
        Name = name;
        BankNumber = bankNumber;
        Users = new HashMap<>();
        Accounts = new HashMap<>();
        TransactionLog = new ArrayList<>();
        parentBankManager = ParentManager;
    }
    public long getNewUID() {
        return parentBankManager.getNewUID();
    }
    public void AddUser(String FirstName, String MiddleName, String LastName, String Address, String Country, String City, String Phone, String Email, short Pin){
        AccountHolder clientInfo = new AccountHolder(FirstName, MiddleName, LastName, Address, Country, City, Phone, Email);
        Client client = new Client(this, Pin, clientInfo);
        Users.put(client.getUUID(), client);
    }
    public void RemoveUser(Long uUID, short Pin){
        Client toBeRemoved = Users.get(uUID);
        if(toBeRemoved != null && toBeRemoved.getPin() == Pin){

        }
    }
    public void UpdateUser(UserUpdate Type, String newType){}

    public BigInteger AddAccount(long uUID, String CurrencyName){} // Returns AccountNumber
    public void RemoveAccount(long uUID, BigInteger AccountNumber){}
}
