package obd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OBD_jdbc_A {

    static String url = "******@******";
    static String uzytkownik ="******";
    static String haslo = "******";
    static Connection polaczenie;
    static Statement polecenie;
    static Scanner scn;

    public static void main(String[] args) {
       
        try {
            polaczzBazaDanych();
            stworzTabelezDanymi();
            uruchomProgram();
            zamknijPolaczenie();
        }
        catch(Exception e) {
            System.out.println("Blad. Program nie dziala. Sprawdz polaczenie internetowe.");
        }

    }

    static void uruchomProgram() throws Exception {
        scn = new Scanner(System.in);
        String kontynuuj;
        List<String> ocenianie;
        do {
            ocenianie = rozpocznijOcenianie();
            dodajOceny(ocenianie);
            do {
                System.out.println("Czy chcesz dodac kolejny rekord? Wcisnij 'T' jak tak lub 'N' jak nie.");
                kontynuuj = scn.nextLine();
                if(!kontynuuj.equals("N") && !kontynuuj.equals("T"))
                    System.out.println("Podales/as niepoprawna wartosc. Podaj jeszcze raz WIELKIMI LITERAMI!");
            }
            while(!kontynuuj.equals("N") && !kontynuuj.equals("T"));
        }
        while(kontynuuj.equals("T"));
        System.out.println("Koniec dzialania programu");
    }

    static List<String> rozpocznijOcenianie() {
        List<String> ocena = new ArrayList<>();
        String rodzajOceny;
        boolean jestOK;
        System.out.println("");
		System.out.println("||||||||||||O-C-E-N-I-A-N-I-E|||||||||||||");
        do {
            System.out.println("wprowadz rodzaj oceny: 'S' - semestralna, 'C' - czastkowa");
            rodzajOceny = scn.nextLine();
            jestOK = rodzajOceny.equals("S") || rodzajOceny.equals("C");
            if(!jestOK) {
                System.out.println("Blad! Niepoprawne dane! Rodzaj oceny powinien byc wprowadzony WIELKIMI LITERAMI!");
                rozpocznijOcenianie();
            }
        } while(!jestOK);


        ocena.add(rodzajOceny);

        sprawdzID(ocena, "ucznia: ");
        sprawdzID(ocena, "oceny: ");
        sprawdzID(ocena, "przedmiotu: ");
        sprawdzID(ocena, "nauczyciela: ");

        return ocena;
    }

    static void sprawdzID(List<String> ocena, String nazwaTabeli) {
        String id;
        boolean jestOK;
        
        do {
            jestOK = true;
            System.out.println("Podaj id " + nazwaTabeli);
            id = scn.nextLine();
            try {
                Integer.parseInt(id);
            }
            catch (NumberFormatException e) {
                System.out.println("SQL Error: ORA-00984: w tym miejscu, kolumna jest niedozwolona");
                rozpocznijOcenianie();
            }
        }
        while(!jestOK);
        ocena.add(id);
    }


    
    static void polaczzBazaDanych() throws Exception {
    	String nazwaSterownika = "oracle.jdbc.driver.OracleDriver";  	
        try {
        	Class<?> c = Class.forName(nazwaSterownika);
			System.out.println("Pakiet     : " + c.getPackage());
			System.out.println("Nazwa klasy: " + c.getName());
            polaczenie = DriverManager.getConnection(url, uzytkownik, haslo);
            System.out.println("AutoCommit: " + polaczenie.getAutoCommit());
            polecenie = polaczenie.createStatement();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Brak sterownika JDBC");
            throw new Exception("");
        }
        catch(SQLException e) {
            System.out.println("Nieudane polaczenie z baza danych");
            throw new Exception("");
        }
        catch(Exception e) {
            throw new Exception("");
        }
        System.out.println("Polaczenie z baza danych: OK");
    }

    static void stworzTabelezDanymi() throws Exception {
        try {
            String sql1 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE ocenianie(rodzaj_oceny char(1) not null, idu integer not null, "
					+ "ido integer not null, idp integer not null, idn integer not null)'; \r\n" + 
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
            String sql2 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE nauczyciel(idn integer not null, nazwisko_nauczyciela char(30) not null, "
					+ "imie_nauczyciela char(20) not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO nauczyciel VALUES (1, ''MARKOWSKI'', ''KRZYSZTOF'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (2, ''ADAMOWSKI'', ''DAMIAN'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (3, ''WOLSKA'', ''ANNA'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (4, ''ADAMOWSKA'', ''ANNA'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (5, ''KUZNIAR'', ''TOMASZ'')'; \r\n" +
					"  execute immediate 'INSERT INTO nauczyciel VALUES (6, ''WOLF'', ''STEFAN'')'; \r\n" +
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
            String sql3 = "declare\r\n" + 
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
            String sql4 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE ocena(ido integer not null, wartosc_opisowa char(20) not null, "
					+ "wartosc_numeryczna float not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO ocena VALUES (1, ''ndop'', 2.0F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (2, ''dst'', 3.0F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (3, ''dstplus'', 3.5F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (4, ''dobry'', 4.0F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (5, ''dbplus'', 4.5F)'; \r\n" +
					"  execute immediate 'INSERT INTO ocena VALUES (6, ''bdb'', 5.0F)'; \r\n" +
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
            String sql5 = "declare\r\n" + 
					"  eAlreadyExists exception;\r\n" + 
					"  pragma exception_init(eAlreadyExists, -00955);\r\n" + 
					"begin\r\n" + 
					"  execute immediate 'CREATE TABLE uczen(idu integer not null, nazwisko_ucznia char(30) not null, "
					+ "imie_ucznia char(20) not null)'; \r\n" + 
					"  execute immediate 'INSERT INTO uczen VALUES (1, ''MICHALSKI'', ''KRZYSZTOF'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (2, ''WOLSKI'', ''DANIEL'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (3, ''KOWALSKA'', ''ANETA'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (4, ''JAKUBOWSKI'', ''JAN'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (5, ''DOBRY'', ''ADAM'')'; \r\n" +
					"  execute immediate 'INSERT INTO uczen VALUES (6, ''PYSZNY'', ''ROBERT'')'; \r\n" +
					"  exception when eAlreadyExists then \r\n" + 
					"      null; \r\n" + 
					"end;";
         
            System.out.println("execute: " + polecenie.executeUpdate(sql1));
			System.out.println("execute: " + polecenie.executeUpdate(sql2));
			System.out.println("execute: " + polecenie.executeUpdate(sql3));
			System.out.println("execute: " + polecenie.executeUpdate(sql4));
			System.out.println("execute: " + polecenie.executeUpdate(sql5));
        }
        catch(SQLException e) {
            System.out.println("Nie udalo sie stworzyc tabel bazy danych");
            throw new Exception();
        }

    }

    static void zamknijPolaczenie() throws Exception{
        try {
            polaczenie.close();
        }
        catch(SQLException e) {
            System.out.println("Nieudane zamkniecie polaczenia");
            throw new Exception();
        }
        System.out.println("Polaczenie zakonczone");
    }

	static void dodajOceny(List<String> ocena) throws Exception{
    	
        try {
        	String result1 = String.format("SELECT idu FROM uczen WHERE idu=" + ocena.get(1));
        	String result2 = String.format("SELECT ido FROM ocena WHERE ido=" + ocena.get(2));
        	String result3 = String.format("SELECT idp FROM przedmiot WHERE idp=" + ocena.get(3));
        	String result4 = String.format("SELECT idn FROM nauczyciel WHERE idn=" + ocena.get(4));
            String errorMessage = "";
            boolean isError = false;
            
            if(polecenie.executeUpdate(result4) != 1) {
                isError = true;
                errorMessage = "IDNFK";
            }
            
            else if(polecenie.executeUpdate(result3) != 1) {
                isError = true;
                errorMessage = "IDPFK";
            }
            
            else if(polecenie.executeUpdate(result2) != 1) {
                isError = true;
                errorMessage = "IDOFK";
            }
            
            else if(polecenie.executeUpdate(result1) != 1) {
                isError = true;
                errorMessage = "IDUFK";
            }
            if(isError) {
                System.out.println("ORA-02291: naruszono wiêzy spójnoœci"+" (MGODLEWS."+errorMessage+") - nie znaleziono klucza nadrzêdnego");
                return;
            }
            polecenie.executeUpdate(String.format("insert into ocenianie (rodzaj_oceny, idu, ido, idp, idn) values ('%s', %s, %s, %s, %s)"
          		  , ocena.get(0), ocena.get(1), ocena.get(2), ocena.get(3), ocena.get(4)));
        }
        catch(SQLRecoverableException | SQLSyntaxErrorException e) {
            throw new Exception();
        }
        catch(SQLException e) {
            System.out.println("Blad. Rekord niedodany.");

        }
    } 
      
}
