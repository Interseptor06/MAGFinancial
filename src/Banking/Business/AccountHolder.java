package Banking.Business;

import java.util.Objects;

public class AccountHolder {
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String Address;
    private String Country;
    private String City;
    private String Phone;
    private String Email;

    public AccountHolder(String firstName, String middleName, String lastName, String address, String country, String city, String phone, String email) {
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        Address = address;
        Country = country;
        City = city;
        Phone = phone;
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getAddress() {
        return Address;
    }

    public String getCountry() {
        return Country;
    }

    public String getCity() {
        return City;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHolder that = (AccountHolder) o;
        return FirstName.equals(that.FirstName) &&
                MiddleName.equals(that.MiddleName) &&
                LastName.equals(that.LastName) &&
                Address.equals(that.Address) &&
                Country.equals(that.Country) &&
                City.equals(that.City) &&
                Phone.equals(that.Phone) &&
                Email.equals(that.Email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FirstName, MiddleName, LastName, Address, Country, City, Phone, Email);
    }

    @Override
    public String toString() {
        return "AccountHolder{" +
                "FirstName='" + FirstName + '\'' +
                ", MiddleName='" + MiddleName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Address='" + Address + '\'' +
                ", Country='" + Country + '\'' +
                ", City='" + City + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
