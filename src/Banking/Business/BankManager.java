package Banking.Business;

import java.util.concurrent.atomic.AtomicLong;

public class BankManager {
    private AtomicLong LastUID = new AtomicLong(0);
    
    public Long getNewUID(){
        return LastUID.addAndGet(1);
    }
}
