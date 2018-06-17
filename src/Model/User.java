package Model;

import javafx.beans.property.SimpleStringProperty;

public class User {

    private SimpleStringProperty user=new SimpleStringProperty();
    private SimpleStringProperty password=new SimpleStringProperty();
    private SimpleStringProperty name=new SimpleStringProperty();
    private SimpleStringProperty sex=new SimpleStringProperty();
    private SimpleStringProperty identity=new SimpleStringProperty();
    private SimpleStringProperty phone=new SimpleStringProperty();
    private  SimpleStringProperty email=new SimpleStringProperty();

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }



    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSex() {
        return sex.get();
    }

    public SimpleStringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getIdentity() {
        return identity.get();
    }

    public SimpleStringProperty identityProperty() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity.set(identity);
    }




}
