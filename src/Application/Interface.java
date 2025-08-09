package Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import Db.DAO;
import Db.DB;
import Entities.Account;
import Entities.Transactions;
import Enum.Status;

public class Interface {
    DAO dao = new DAO();
    Scanner sc = new Scanner(System.in);
    Transactions tr = new Transactions();
    double value;
    String desc;
    int i;

    Connection conn = DB.getConnection();
    PreparedStatement st = null;

    public void mainMenu() {
        System.out.println("==============================");
        System.out.println("       Financial Monitoring   ");
        System.out.println("==============================");
        System.out.println("1 - Deposito ");
        System.out.println("2 - Saque ");
        System.out.println("3 - Relatórios ");
        System.out.println("4 - Sair ");
        System.out.println("==============================");
    }

    public void deposit(Account acc) {
        System.out.println("==============================");
        System.out.println("           Deposito           ");
        System.out.println("==============================");
        System.out.printf("\nDigite o valor que deseja depositar: R$");
        value = sc.nextDouble();
        acc.deposit(value);
        System.out.println("Digite a descrição da operação: ");
        sc.nextLine();
        desc = sc.nextLine();
        LocalDateTime now = LocalDateTime.now();
        dao.insertTransactions(value, now, desc, Status.DEPOSITO);
        dao.insertValue(value);
    }

    public void withdrawal(Account acc) {
        System.out.println("==============================");
        System.out.println("            Saque             ");
        System.out.println("==============================");
        System.out.printf("\nDigite o valor que deseja sacar: R$");
        value = sc.nextDouble();
        acc.withdrawal(value);
        System.out.println("Digite a descrição da operação: ");
        sc.nextLine();
        desc = sc.nextLine();
        LocalDateTime now = LocalDateTime.now();
        dao.insertTransactions(value, now, desc, Status.SAQUE);
        dao.insertValue(value);
    }

    public void relatorios(Account acc) {
        System.out.println("==============================");
        System.out.println("          Relatórios          ");
        System.out.println("==============================");
        System.out.printf("\nSaldo R$ %.2f \n", acc.getBalance());

        List<Transactions> historico = dao.recoveryTransactions();
        for (Transactions t : historico) {
            System.out.println(t);
        }
    }
}
