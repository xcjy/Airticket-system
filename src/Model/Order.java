package Model;

import javafx.beans.property.SimpleStringProperty;

public class Order {
    private SimpleStringProperty orderid= new SimpleStringProperty();
    private SimpleStringProperty p_name= new SimpleStringProperty();
    private SimpleStringProperty p_id= new SimpleStringProperty();
    private SimpleStringProperty f_id= new SimpleStringProperty();
    private SimpleStringProperty f_com= new SimpleStringProperty();
    private SimpleStringProperty f_model= new SimpleStringProperty();
    private SimpleStringProperty f_stime= new SimpleStringProperty();
    private SimpleStringProperty f_etime= new SimpleStringProperty();
    private SimpleStringProperty f_start= new SimpleStringProperty();
    private SimpleStringProperty f_end= new SimpleStringProperty();
    private SimpleStringProperty f_price= new SimpleStringProperty();


    public String getOrderid() {
        return orderid.get();
    }

    public SimpleStringProperty orderidProperty() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid.set(orderid);
    }

    public String getP_name() {
        return p_name.get();
    }

    public SimpleStringProperty p_nameProperty() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name.set(p_name);
    }

    public String getP_id() {
        return p_id.get();
    }

    public SimpleStringProperty p_idProperty() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id.set(p_id);
    }

    public String getF_id() {
        return f_id.get();
    }

    public SimpleStringProperty f_idProperty() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id.set(f_id);
    }

    public String getF_com() {
        return f_com.get();
    }

    public SimpleStringProperty f_comProperty() {
        return f_com;
    }

    public void setF_com(String f_com) {
        this.f_com.set(f_com);
    }

    public String getF_model() {
        return f_model.get();
    }

    public SimpleStringProperty f_modelProperty() {
        return f_model;
    }

    public void setF_model(String f_model) {
        this.f_model.set(f_model);
    }

    public String getF_stime() {
        return f_stime.get();
    }

    public SimpleStringProperty f_stimeProperty() {
        return f_stime;
    }

    public void setF_stime(String f_stime) {
        this.f_stime.set(f_stime);
    }

    public String getF_etime() {
        return f_etime.get();
    }

    public SimpleStringProperty f_etimeProperty() {
        return f_etime;
    }

    public void setF_etime(String f_etime) {
        this.f_etime.set(f_etime);
    }

    public String getF_start() {
        return f_start.get();
    }

    public SimpleStringProperty f_startProperty() {
        return f_start;
    }

    public void setF_start(String f_start) {
        this.f_start.set(f_start);
    }

    public String getF_end() {
        return f_end.get();
    }

    public SimpleStringProperty f_endProperty() {
        return f_end;
    }

    public void setF_end(String f_end) {
        this.f_end.set(f_end);
    }

    public String getF_price() {
        return f_price.get();
    }

    public SimpleStringProperty f_priceProperty() {
        return f_price;
    }

    public void setF_price(String f_price) {
        this.f_price.set(f_price);
    }


}
