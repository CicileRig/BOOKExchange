package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable{

    private String userId ;
    private String name;
    private String surname;
    private String mailAdress;
    private String age;
    private String password;
    private ArrayList<Book> booksList;


    public User(String name, String surname, String mailAdress, String password, String age, ArrayList<Book> booksList) {
        this.name = name;
        this.surname = surname;
        this.mailAdress = mailAdress;
        this.age = age;
        this.password = password;
        this.booksList = booksList;
    }

    public User(String userId, String name, String surname, String mailAdress, String password, String age) {
        this.name = name;
        this.userId = userId;
        this.surname = surname;
        this.mailAdress = mailAdress;
        this.age = age;
        this.password = password;
    }

    public User(String email, String userId) {
        this.mailAdress = email;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMailAdress() {
        return mailAdress;
    }

    public String getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Book> getBooksList() {
        return booksList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBooksList(ArrayList<Book> booksList) {
        this.booksList = booksList;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userId);
        result.put("name", name);
        result.put("surname", surname);
        result.put("mailAdress", mailAdress);
        result.put("password", password);
        result.put("age", age);

        return result;
    }
}
