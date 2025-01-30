package de.telran.bankapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
//@Getter
@Setter
public class Client {

    private String id;
    private String lastName;
    private String firstName;
    private String taxCode;
    private String email;
    private String address;
    private String phone;
    private ClientStatus status;

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public ClientStatus getStatus() {
        return status;
    }
}
