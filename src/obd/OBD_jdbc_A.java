package obd;

/* 
 * PROGRAM:
 *  - TWORZACY BAZE DANYCH
 *  - UZUPELNIAJACY TABELE nauczyciel, uczen, przedmiot, ocena
 *  - UZUPELNIAJACY TABELE ocenianie Z POZIOMU KONSOLI IDE
 *  - POSIADA ZABEZPIECZENIE PRZED DUPLIKACJA TABELI I DANYCH
 *    PO PONOWNYM URUCHOMIENIU
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OBD_jdbc_A {

	static class Namiary {
		static String url = "******@******";
		static String uzytkownik = "******";
		static String haslo		 = "******";
	}
	
	public static void main(String[] args) throws NullPointerException {
		
		Scanner scn;
		String nazwaSterownika = "oracle.jdbc.driver.OracleDriver";
		
		try {
			// Laduj sterownik JDBC
			Class<?> c = Class.forName(nazwaSterownika);
			System.out.println("Pakiet     : " + c.getPackage());
			System.out.println("Nazwa klasy: " + c.getName());
		
			
			//Definicja wyjatku ORA-00955 “nazwa jest obecnie uzywana przez istniejacy obiekt”
			
			String sql1 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE nauczyciel(idn integer not null, nazwisko_nauczyciela char(30) not null, imie_nauczyciela char(20) not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO nauczyciel VALUES (1, ''MARKOWSKI'', ''KRZYSZTOF'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (2, ''ADAMOWSKI'', ''DAMIAN'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (3, ''WOLSKA'', ''ANNA'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (4, ''ADAMOWSKA'', ''ANNA'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (5, ''KUZNIAR'', ''TOMASZ'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (6, ''WOLF'', ''STEFAN'')'; \r\n" +
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
			
			String sql2 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE przedmiot(idp integer not null, nazwa_przedmiotu char(20) not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO przedmiot VALUES (1, ''BAZY DANYCH'')'; \r\n" +
					"  execute immediate 'INSERT INTO przedmiot VALUES (2, ''JAVA'')'; \r\n" +
					"  execute immediate 'INSERT INTO przedmiot VALUES (3, ''C++'')'; \r\n" +
					"  execute immediate 'INSERT INTO przedmiot VALUES (4, ''C#'')'; \r\n" +
					"  execute immediate 'INSERT INTO przedmiot VALUES (5, ''PYTHON'')'; \r\n" +
					"  execute immediate 'INSERT INTO przedmiot VALUES (6, ''JAVASCRIPT'')'; \r\n" +
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
			
			String sql3 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE ocena(ido integer not null, wartosc_opisowa char(20) not null, wartosc_numeryczna float not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO ocena VALUES (1, ''ndop'', 2.0F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (2, ''dst'', 3.0F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (3, ''dstplus'', 3.5F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (4, ''dobry'', 4.0F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (5, ''dbplus'', 4.5F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (6, ''bdb'', 5.0F)'; \r\n" +
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
			
			String sql4 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE uczen(idu integer not null, nazwisko_ucznia char(30) not null, imie_ucznia char(20) not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO uczen VALUES (1, ''MICHALSKI'', ''KRZYSZTOF'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (2, ''WOLSKI'', ''DANIEL'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (3, ''KOWALSKA'', ''ANETA'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (4, ''JAKUBOWSKI'', ''JAN'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (5, ''DOBRY'', ''ADAM'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (6, ''PYSZNY'', ''ROBERT'')'; \r\n" +
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
			
			String sql5 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE ocenianie(idu integer not null, ido integer not null, idp integer not null, idn integer not null, rodzaj_oceny char(1) not null)'; \r\n" + 
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
			
			Connection polaczenie = DriverManager.getConnection(Namiary.url, Namiary.uzytkownik, Namiary.haslo);
			System.out.println("AutoCommit: " + polaczenie.getAutoCommit());
			Statement polecenie = polaczenie.createStatement();
			System.out.println("execute: " + polecenie.executeUpdate(sql1));
			System.out.println("execute: " + polecenie.executeUpdate(sql2));
			System.out.println("execute: " + polecenie.executeUpdate(sql3));
			System.out.println("execute: " + polecenie.executeUpdate(sql4));
			System.out.println("execute: " + polecenie.executeUpdate(sql5));
			
			// Wprowadzanie danych / ocenianie z klawiatury
			// Program sprawdza, czy dane faktycznie znajduja sie w bazie danych
			// Inaczej uniemozliwa ocenianie
			
			int idn = 0;
			int idu = 0;
			int ido = 0;
			int idp = 0;
			
			String sql6;
			String sql7;
			String sql8;
			String sql9;
			
			ResultSet result1 = null;	
			ResultSet result2 = null;
			ResultSet result3 = null;
			ResultSet result4 = null;
			
			String sql = "INSERT INTO ocenianie(idu, ido, idp, idn, rodzaj_oceny) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement polecenie2;

			while (true) {
					System.out.println("");
					System.out.println("||||||||||||O-C-E-N-I-A-N-I-E|||||||||||||");
					System.out.println("Wprowadzenie nowej oceny. Wcisnij - 'ENTER'");
					System.out.println("Zakonczenie oceniania.    Wcisnij - 'Q' nastepnie 'ENTER'");
					scn = new Scanner(System.in);
					String in = scn.nextLine();
						
					if (in.equals("q") || in.equals("Q")) {
						System.out.println("Koniec dzialania programu");
						scn.close();
						polecenie.close();
						polaczenie.close();
						break;
					}

					System.out.println("Wprowadz ID ucznia");
					idu = scn.nextInt();
					sql6 = "SELECT idu FROM uczen WHERE idu='" + idu + "'";
					result1 = polecenie.executeQuery(sql6);
					if (result1.next()) {
						System.out.println("Jest w bazie. Kolejno");
					} else {
						System.out.println("Niepoprawne dane! ORA-02291: naruszono wiêzy spójnoœci (MGODLEWS.IDUFK) - nie znaleziono klucza nadrzêdnego");
						continue;
					}
				
					System.out.println("wprowadz ID oceny");
					ido = scn.nextInt();	
					sql7 = "SELECT ido FROM ocena WHERE ido='" + ido + "'";
					result2 = polecenie.executeQuery(sql7);	
					if (result2.next()) {
						System.out.println("Jest w bazie. Kolejno");
					} else {
						System.out.println("Niepoprawne dane! ORA-02291: naruszono wiêzy spójnoœci (MGODLEWS.IDOFK) - nie znaleziono klucza nadrzêdnego");
						continue;
					}	
												
					System.out.println("wprowadz ID przedmiotu");
					idp = scn.nextInt();	
					sql8 = "SELECT idp FROM przedmiot WHERE idp='" + idp + "'";
					result3 = polecenie.executeQuery(sql8);
					if (result3.next()) {
						System.out.println("Jest w bazie. Kolejno");
					} else {
						System.out.println("Niepoprawne dane! ORA-02291: naruszono wiêzy spójnoœci (MGODLEWS.IDPFK) - nie znaleziono klucza nadrzêdnego");
						continue;
					}

					System.out.println("wprowadz ID nauczyciela");
					idn = scn.nextInt();	
					sql9 = "SELECT idn FROM nauczyciel WHERE idn='" + idn + "'";
					result4 = polecenie.executeQuery(sql9);
					if (result4.next()) {
						System.out.println("Jest w bazie. Kolejno");
					} else {
						System.out.println("Niepoprawne dane! ORA-02291: naruszono wiêzy spójnoœci (MGODLEWS.IDNFK) - nie znaleziono klucza nadrzêdnego");
						continue;
					}							

					System.out.println("wprowadz rodzaj oceny: 'S' - semestralna, 'C' - czastkowa");
					while (!scn.hasNext("[SC]")) {
					    System.out.println("Niepoprawne dane! Wprowadz poprawny rodzaj oceny: 'S' - semestralna, 'C' - czastkowa. RODZAJ OCENY WIELKIMI LITERAMI!");
					    scn.next();
					} 
								
				char rodzaj_oceny = scn.next().charAt(0); 	        
				polecenie2 = polaczenie.prepareStatement(sql);
				polecenie2.setInt(1, idu);
				polecenie2.setInt(2, ido);
				polecenie2.setInt(3, idp);
				polecenie2.setInt(4, idn);
				polecenie2.setString(5, (Character.toString(rodzaj_oceny)));
				System.out.println("execute: " + polecenie2.executeUpdate());

				polecenie2.close();
				
			}
			
			polaczenie.close();
			polecenie.close();
			scn.close();
				
		    if (result1 != null) {
		    	result1.close();
		    }
		    if (result2 != null) {
		    	result2.close();
		    }
		    if (result3 != null) {
		    	result3.close();
		    }
		    if (result4 != null) {
		    	result4.close();
		    }
		    
		} catch (SQLException e) {
				
			System.out.println("Blad programu! Komunikat b³êdu:");
			e.printStackTrace();
			return;
		
		} catch (InputMismatchException e) {
			
			System.out.println("Blad programu! Wpisa³eœ/aœ litery zamiast cyfer. Komunikat b³êdu:");
			e.printStackTrace();
			return;
					
		} catch (Exception e) {
			// Sterownik nieodnaleziony
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
			
		}
		
	}
}