package Entities;

import java.time.LocalDateTime;

import Enum.Status;



public class Transactions {

    Account acc = new Account();

    private int id;
    private double value;
    private LocalDateTime date;
    private String desc;
    private Status status;
    
    
    public Transactions() {
    }

    public Transactions(double value, LocalDateTime date, String desc, Status status) {
        this.value = value;
        this.date = date;
        this.desc = desc;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
        	"Valor: " +
             String.format("%.2f", value) + ", "
             + date + ", "
             + desc + ", "
             + status;
    }

    
    

}
