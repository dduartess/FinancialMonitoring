package Application;

import java.util.Scanner;

import Db.DAO;
import Entities.Account;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Interface in = new Interface();
		Account acc = new Account();
		int r;
		Boolean loop = true;
		DAO dao = new DAO();


		dao.createDb();
		dao.recoverDates(acc);

		while (loop == true) {

			in.mainMenu();
			r = sc.nextInt();
			switch (r) {
				case 1:
					in.deposit(acc);
					break;
				case 2:
					in.withdrawal(acc);
					break;
				case 3:
					in.relatorios(acc);
					break;
				case 4:
					loop = false;
					System.out.println("Saindo...");
			}
		}
	}
}
