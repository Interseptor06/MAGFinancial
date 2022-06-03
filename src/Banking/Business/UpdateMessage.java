package Banking.Business;

public class UpdateMessage {
    private final UserUpdate UpdateType;
    private String newFirstName;
    private String newMiddleName;
    private String newLastName;
    private String newAddress;
    private String newCountry;
    private String newCity;
    private String newPhone;
    private String newEmail;
    public UpdateMessage(UserUpdate Type, String newType) {
        newFirstName = null;
        newMiddleName = null;
        newLastName = null;
        newAddress = null;
        newCountry = null;
        newCity = null;
        newPhone = null;
        newEmail = null;
        UpdateType = Type;
        switch (UpdateType){
            case City -> newCity = newType;
            case Email -> newEmail = newType;
            case Phone -> newPhone = newType;
            case Address-> newAddress = newType;
            case LastName -> newLastName = newType;
            case Country -> newCountry = newType;
            case FirstName -> newFirstName = newType;
            case MiddleName -> newMiddleName = newType;
        }
    }

    public String getNewFirstName() {
        return newFirstName;
    }

    public String getNewMiddleName() {
        return newMiddleName;
    }

    public String getNewLastName() {
        return newLastName;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public String getNewCountry() {
        return newCountry;
    }

    public String getNewCity() {
        return newCity;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public UserUpdate getUpdateType() {
        return UpdateType;
    }
}
