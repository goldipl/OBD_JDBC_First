package obd;

//KLASA UZUPELNIAJACA TABELE nauczyciel, uczen, przedmiot, ocena

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OBD_jdbc_B {

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
			String sql1 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela)" + "VALUES (1, 'WOLSKI', 'MARCIN')";
			String sql2 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia)" + "VALUES (1, 'GODLEWSKI', 'ARTUR')";
			String sql3 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu)" + "VALUES (1, 'MATEMATYKA')";
			String sql4 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna)" + "VALUES (1, 'bdb', '5')";
			String sql5 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela)" + "VALUES (2, 'KOWALSKI', 'MARIAN')";
			String sql6 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia)" + "VALUES (2, 'GODLEWSKI', 'ADRIAN')";
			String sql7 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu)" + "VALUES (2, 'INFORMATYKA')";
			String sql8 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna)" + "VALUES (2, 'db', '4')";
			String sql9 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela)" + "VALUES (3, 'MARKOWSKI', 'KRZYSZTOF')";
			String sql10 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia)" + "VALUES (3, 'GODLEWSKI', 'ZYGMUNT')";
			String sql11 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu)" + "VALUES (3, 'BAZY DANYCH')";
			String sql12 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna)" + "VALUES (3, 'bdb', '5')";
							
			Connection polaczenie = DriverManager.getConnection(url, uzytkownik, haslo);
			System.out.println("AutoCommit: " + polaczenie.getAutoCommit());
			Statement polecenie = polaczenie.createStatement();
			System.out.println("execute: " + polecenie.executeUpdate(sql1));
			System.out.println("execute: " + polecenie.executeUpdate(sql2));
			System.out.println("execute: " + polecenie.executeUpdate(sql3));
			System.out.println("execute: " + polecenie.executeUpdate(sql4));
			System.out.println("execute: " + polecenie.executeUpdate(sql5));
			System.out.println("execute: " + polecenie.executeUpdate(sql6));
			System.out.println("execute: " + polecenie.executeUpdate(sql7));
			System.out.println("execute: " + polecenie.executeUpdate(sql8));
			System.out.println("execute: " + polecenie.executeUpdate(sql9));
			System.out.println("execute: " + polecenie.executeUpdate(sql10));
			System.out.println("execute: " + polecenie.executeUpdate(sql11));
			System.out.println("execute: " + polecenie.executeUpdate(sql12));
			polaczenie.close();
			
		} catch (SQLException e) {
			
			System.out.println("B³¹d programu!");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Sukces.");
	}

}
