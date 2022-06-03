package Banking.Business;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Transaction {

    private final BigDecimal Amount;

    private final Date Timestamp;

    private final BigInteger inAccountID;
    private final BigInteger fromAccountID;

    // Both the sender and receiver get an equivalent Transaction object
    public Transaction(BigDecimal amount, BigInteger inAccountID, BigInteger fromAccountID){

        this.Amount = amount;
        this.inAccountID = inAccountID;
        this.Timestamp = new Date();
        this.fromAccountID = fromAccountID;

    }

    public String getSummaryLine(){
        if (this.Amount.compareTo(BigDecimal.valueOf(0)) >= 0){
            return String.format("%s : $%.02f",this.Timestamp.toString(),this.Amount);
        } else{
            return String.format("%s : $(%.02f)",this.Timestamp.toString(),this.Amount);
        }
    }

    public BigDecimal getAmount() {
        return Amount;
    }
    public Date getTimestamp() {
        return Timestamp;
    }

    public BigInteger getInAccountID() {
        return inAccountID;
    }

    public BigInteger getFromAccountID() {
        return fromAccountID;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "Amount=" + Amount +
                ", Timestamp=" + Timestamp +
                ", inAccountID=" + inAccountID +
                ", fromAccountID=" + fromAccountID +
                '}';
    }
}
