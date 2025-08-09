package Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Entities.Account;
import Entities.Transactions;
import Enum.Status;

public class DAO {

    List<Transactions> list = new ArrayList<>();
    LocalDateTime date = LocalDateTime.now();
    double value;
    String desc;
    Transactions tr = new Transactions();

    public void createDb() {
        try {
            Connection conn = DB.getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS transactions ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "value DECIMAL(10, 2) NOT NULL, "
                    + "date DATE NOT NULL, "
                    + "description VARCHAR(255) NOT NULL, "
                    + "status VARCHAR(50) NOT NULL"
                    + ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS account ("
                    + "id INT PRIMARY KEY, "
                    + "value DECIMAL(10, 2) not null"
                    + ")");
            PreparedStatement st1 = conn.prepareStatement(
                    "INSERT INTO account (id, value) VALUES (1, 0) ON DUPLICATE KEY UPDATE value = value");
            st1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTransactions(double value, LocalDateTime date, String desc, Status status) {
        try {
            Connection conn = DB.getConnection();

            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO transactions (value, date, description, status) VALUES (?, ?, ?, ?)");

            st.setDouble(1, value);
            st.setTimestamp(2, Timestamp.valueOf(date));
            st.setString(3, desc);
            st.setString(4, status.name());
            st.executeUpdate();
            tr = new Transactions(value, date, desc, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertValue(double value) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE account SET value = value + ? WHERE id = 1");
            st.setDouble(1, value);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transação registrada com sucesso");
                list.add(tr);
            } else {
                System.out.println("Erro!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dbRemoveValue(double value) {
        PreparedStatement st = null;
        Connection conn = DB.getConnection();
        try {
            st = conn.prepareStatement(
                    "UPDATE account SET value = value - ? WHERE id = 1");
            st.setDouble(1, value);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transação registrada com sucesso");
                list.add(tr);
            } else {
                System.out.println("Saldo insuficiente para saque");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recoverDates(Account acc) {
        recoveryTransactions();
        double saldoBanco = recoveryBalance();
        acc.setBalance(saldoBanco);
    }

    public double recoveryBalance() {
        PreparedStatement st = null;
        ResultSet rs = null;
        double saldo = 0.0;

        try {
            Connection conn = DB.getConnection();
            st = conn.prepareStatement("SELECT value FROM account WHERE id = 1");
            rs = st.executeQuery();

            if (rs.next()) {
                saldo = rs.getDouble("value");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return saldo;
    }

    public List<Transactions> recoveryTransactions() {
        Connection conn = DB.getConnection();
        List<Transactions> list = new ArrayList<>();
        String sql = "SELECT value, date, description, status FROM transactions ORDER BY date DESC";
        

        try (Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                double value = rs.getDouble("value");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                String description = rs.getString("description");
                Status status = Status.valueOf(rs.getString("status"));
                list.add(new Transactions(value, date, description, status));
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return list;
    }

    public List<Transactions> getList() {
        return list;
    }
}
