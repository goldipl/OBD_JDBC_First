package obd;

// KLASA TWORZACA BAZE DANYCH

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OBD_jdbc_A {

	public static void main(String[] args) {
		
		String nazwaSterownika = "oracle.jdbc.driver.OracleDriver";
		
		try {
			// Laduj sterownik JDBC
			Class<?> c = Class.forName(nazwaSterownika);
			System.out.println("Pakiet     : " + c.getPackage());
			System.out.println("Nazwa klasy: " + c.getName());
		
		} catch (Exception e) {
			// Sterownik nieodnaleziony
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}
			
		try {
			
			String url = "url";
			String uzytkownik = "nick";
			String haslo      = "haslo";
			String sql1 = "CREATE TABLE nauczyciel (idn integer not null, nazwisko_nauczyciela char(30), imie_nauczyciela char(20) not null)";
			String sql2 = "CREATE TABLE przedmiot (idp integer not null, nazwa_przedmiotu char(20))";
			String sql3 = "CREATE TABLE ocena (ido integer not null, wartosc_opisowa char(20), wartosc_numeryczna float)";
			String sql4 = "CREATE TABLE uczen (idu integer not null, nazwisko_ucznia char(30), imie_ucznia char(20) not null)";
			String sql5 = "CREATE TABLE ocenianie (idu integer not null, ido integer not null, idp integer not null, idn integer not null, rodzaj_oceny char(1))";

							
			Connection polaczenie = DriverManager.getConnection(url, uzytkownik, haslo);
			System.out.println("AutoCommit: " + polaczenie.getAutoCommit());
			Statement polecenie = polaczenie.createStatement();
			System.out.println("execute: " + polecenie.executeUpdate(sql1));
			System.out.println("execute: " + polecenie.executeUpdate(sql2));
			System.out.println("execute: " + polecenie.executeUpdate(sql3));
			System.out.println("execute: " + polecenie.executeUpdate(sql4));
			System.out.println("execute: " + polecenie.executeUpdate(sql5));
			polaczenie.close();
			
		} catch (SQLException e) {
			
			System.out.println("B³¹d programu!");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Sukces.");
	}

}
