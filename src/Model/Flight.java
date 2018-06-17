package Model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Flight {

    private SimpleStringProperty id= new SimpleStringProperty();
    private SimpleStringProperty com =new SimpleStringProperty();
    private SimpleStringProperty model =new SimpleStringProperty();

    private SimpleStringProperty stime =new SimpleStringProperty();
    private SimpleStringProperty etime =new SimpleStringProperty();

    private SimpleStringProperty start =new SimpleStringProperty();
    private SimpleStringProperty dist =new SimpleStringProperty();

    private SimpleFloatProperty price =new SimpleFloatProperty();
    private SimpleIntegerProperty left=new SimpleIntegerProperty();


    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }



    public void setId(String id) {
        this.id.set(id);
    }



    public String getCom() {
        return com.get();
    }

    public SimpleStringProperty comProperty() {
        return com;
    }

    public void setCom(String com) {
        this.com.set(com);
    }



    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }



    public String getStime() {
        return stime.get();
    }

    public SimpleStringProperty stimeProperty() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime.set(stime);
    }



    public String getEtime() {
        return etime.get();
    }

    public SimpleStringProperty etimeProperty() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime.set(etime);
    }



    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set(start);
    }



    public String getDist() {
        return dist.get();
    }

    public SimpleStringProperty distProperty() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist.set(dist);
    }



    public float getPrice() {
        return price.get();
    }

    public SimpleFloatProperty priceProperty() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }


    public int getLeft() {
        return left.get();
    }

    public SimpleIntegerProperty leftProperty() {
        return left;
    }

    public void setLeft(int left) {
        this.left.set(left);
    }


















}
