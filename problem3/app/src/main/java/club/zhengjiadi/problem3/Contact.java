package club.zhengjiadi.problem3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjdzn on 2018/6/23.
 */


public class Contact {
    private String name = "";
    private List<String> number = new ArrayList<>();
    private List<String> email = new ArrayList<>();

    public Contact() {
    }

    public Contact(String name, List<String> number, List<String> email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }


    public Contact(String name, String number, String email) {
        this.name = name;
        this.number.add(number);
        this.email.add(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNumber() {
        return number;
    }

    public void setNumber(List<String> number) {
        this.number = number;
    }

    public void setNumber(String number) {
        this.number.add(number);
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public void setEmail(String email) {
        this.email.add(email);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", email=" + email +
                '}';
    }
}
