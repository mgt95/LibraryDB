/**
 * Created by Meiram on 24.03.2017.
 */
import java.sql.SQLException;
import java.util.Scanner;

public class db {



    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        conn.Conn();
        conn.CreateDB();

        System.out.println("1.Просмотр зарегистрированных изданий в фонде");
        System.out.println("2.Добавление нового издания в фонд");
        System.out.println("3.Просмотр информации выбранного издания");
        System.out.println("4.Удаление выбранного издания");
        System.out.println("5.Выход");
        System.out.println("What command do you need?");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number of command: ");
        int input = in.nextInt();




       switch (input){

            case 1:conn.ReadDB();
                System.out.println("Do you want to continue?");
                System.out.println("y/n");
                Scanner inu = new Scanner(System.in);
                String instring=inu.nextLine();
                switch (instring){
                    case "y":
                        break;
                    case "n":conn.CloseDB();
                        break;

                }
                break;
            case 2:conn.WriteDB();
                break;
            case 3:
                break;
            case 4:conn.deleteRecordFromTable();
                break;
            case 5:
                break;
            default:
                System.out.println("We haven't this command, try again and choose 1-5");



        }







    }
}