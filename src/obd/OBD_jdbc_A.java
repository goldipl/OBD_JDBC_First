package obd;

/* 
 * PROGRAM:
 *  - TWORZACY BAZE DANYCH
 *  - UZUPELNIAJACY TABELE nauczyciel, uczen, przedmiot, ocena
 *  - UZUPELNIAJACY TABELE ocenianie Z POZIOMU KONSOLI IDE
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OBD_jdbc_A {

	static class Namiary {
		static String url = "*****@******";
		static String uzytkownik = "*******";
		static String haslo		 = "*******";
	}
	
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
					
			//Definicja wyjatku ORA-00955 “nazwa jest obecnie uzywana przez istniejacy obiekt”
			
			String sql1 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE nauczyciel(idn integer not null, nazwisko_nauczyciela char(30) not null, imie_nauczyciela char(20) not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO nauczyciel VALUES (1, ''MARKOWSKI'', ''KRZYSZTOF'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (2, ''ADAMOWSKI'', ''DAMIAN'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (3, ''WOLSKA'', ''ANNA'')'; \r\n" +
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
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
			
			String sql3 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE ocena(ido integer not null, wartosc_opisowa char(20) not null, wartosc_numeryczna float not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO ocena VALUES (4, ''dstplus'', 3.5F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (5, ''dobry'', 4.0F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (6, ''dbplus'', 4.5F)'; \r\n" +
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
			polaczenie.close();
			
		} catch (SQLException e) {
			
			System.out.println("B³¹d programu!");
			e.printStackTrace();
			return;
		}
		
        Scanner scn1 = new Scanner(System.in);
        int idu;
        do {
            System.out.println("Wprowadz id ucznia (cyfra od 1 do 3!)");
            while (!scn1.hasNext("[123]")) {
                System.out.println("Niepoprawne dane! Wprowadz ponownie id ucznia (cyfra od 1 do 3!)");
                scn1.next(); 
            }
            idu = scn1.nextInt();
        } while (idu <= 0);
        
        Scanner scn2 = new Scanner(System.in);
        int ido;
        do {
            System.out.println("Wprowadz id oceny (cyfra od 4 do 6!)");
            while (!scn2.hasNext("[456]")) {
                System.out.println("Niepoprawne dane! Wprowadz ponownie id oceny (cyfra od 4 do 6!)");
                scn2.next(); 
            }
            ido = scn2.nextInt();
        } while (ido <= 0);
        
        Scanner scn3 = new Scanner(System.in);
        int idp;
        do {
            System.out.println("Wprowadz id przedmiotu (cyfra od 1 do 3!)");
            while (!scn3.hasNext("[123]")) {
                System.out.println("Niepoprawne dane! Wprowadz ponownie id przedmiotu (cyfra od 1 do 3!)");
                scn3.next(); 
            }
            idp = scn3.nextInt();
        } while (idp <= 0);     

        Scanner scn4 = new Scanner(System.in);
        int idn;
        do {
            System.out.println("Wprowadz id nauczyciela (cyfra od 1 do 3!)");
            while (!scn4.hasNext("[123]")) {
                System.out.println("Niepoprawne dane! Wprowadz ponownie id nauczyciela (cyfra od 1 do 3!)");
                scn4.next(); 
            }
            idn = scn4.nextInt();
        } while (idn <= 0); 
      
        Scanner scn5 = new Scanner(System.in);
        System.out.println("Wprowadz rodzaj oceny: 'S' - semestralna, 'C' - czastkowa");
        while (!scn5.hasNext("[SC]")) {
            System.out.println("Niepoprawne dane! Wprowadz poprawny rodzaj oceny: 'S' - semestralna, 'C' - czastkowa");
            scn5.next();
        }
        char rodzaj_oceny = scn5.next().charAt(0);
        
        scn1.close();
        scn2.close();
        scn3.close();
        scn4.close();
        scn5.close();
		
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
