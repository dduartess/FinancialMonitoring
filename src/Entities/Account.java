package Entities;

import java.time.LocalDateTime;

public class Account {

    private int id;
    private double balance;
    private double value;
    private LocalDateTime date;
    

    public Account() {
    }

    public Account(int id, double balance, double value, LocalDateTime date) {
        this.id = id;
        this.balance = balance;
        this.value = value;
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
		return balance;
	}

    public void setBalance(double balance) {
    this.balance = balance;
    }

	public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void deposit(double value){
        if(value > 0){
            this.balance += value;
            System.out.println("Concluído!");
        } else{
            System.out.println("Valor inválido!");
        }
    }
    
    public void withdrawal(double value){
        if(value <= balance){
            this.balance -= value;
            System.out.println("Concluído!");
        } else{
            System.out.println("Valor inválido!");
        }
    }


}
