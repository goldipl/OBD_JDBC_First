package obd;

//KLASA UZUPELNIAJACA TABELE ocenianie Z POZIOMU KONSOLI IDE

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class OBD_jdbc_C {

	static class Namiary {
		static String url = "url";
		static String uzytkownik = "nick";
		static String haslo		 = "haslo";
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {

        Scanner scn1 = new Scanner(System.in);
        int idu;
        do {
            System.out.println("Wprowadz id ucznia (liczba dodatnia!)");
            while (!scn1.hasNextInt()) {
                System.out.println("To nie jest liczba! Wprowadz ponownie id ucznia (liczba dodatnia)");
                scn1.next(); 
            }
            idu = scn1.nextInt();
        } while (idu <= 0);
        
        Scanner scn2 = new Scanner(System.in);
        int ido;
        do {
            System.out.println("Wprowadz id oceny (liczba dodatnia!)");
            while (!scn2.hasNextInt()) {
                System.out.println("To nie jest liczba! Wprowadz ponownie id oceny (liczba dodatnia)");
                scn2.next(); 
            }
            ido = scn2.nextInt();
        } while (ido <= 0);
        
        Scanner scn3 = new Scanner(System.in);
        int idp;
        do {
            System.out.println("Wprowadz id przedmiotu (liczba dodatnia!)");
            while (!scn3.hasNextInt()) {
                System.out.println("To nie jest liczba! Wprowadz ponownie id przedmiotu (liczba dodatnia)");
                scn3.next(); 
            }
            idp = scn3.nextInt();
        } while (idp <= 0);     

        Scanner scn4 = new Scanner(System.in);
        int idn;
        do {
            System.out.println("Wprowadz id nauczyciela (liczba dodatnia!)");
            while (!scn4.hasNextInt()) {
                System.out.println("To nie jest liczba! Wprowadz ponownie id nauczyciela (liczba dodatnia)");
                scn4.next(); 
            }
            idn = scn4.nextInt();
        } while (idn <= 0); 
      
        Scanner scn5 = new Scanner(System.in);
        System.out.println("Wprowadz rodzaj oceny: 'S' - semestralna, 'C' - czastkowa");
        while (!scn5.hasNext("[SCsc]")) {
            System.out.println("Prosze, wprowadz poprawny rodzaj oceny: 'S' - semestralna, 'C' - czastkowa");
            scn5.next();
        }
        char rodzaj_oceny = scn5.next().charAt(0);
        
		try {

			String sql = "INSERT INTO ocenianie(idu, ido, idp, idn, rodzaj_oceny) VALUES (?, ?, ?, ?, ?)";
							
			Connection polaczenie = DriverManager.getConnection(Namiary.url, Namiary.uzytkownik, Namiary.haslo);
			System.out.println("AutoCommit: " + polaczenie.getAutoCommit());
			PreparedStatement polecenie = polaczenie.prepareStatement(sql);
			polecenie.setInt(1, idu);
			polecenie.setInt(2, ido);
			polecenie.setInt(3, idp);
			polecenie.setInt(4, idn);
			polecenie.setString(5, (Character.toString(rodzaj_oceny)));
			System.out.println("execute: " + polecenie.executeUpdate());
			polaczenie.close();
			
		} catch (SQLException e) {
			
			System.out.println("B³¹d programu!");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Sukces.");
	
	}
}
