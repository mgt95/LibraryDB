/**
 * Created by Meiram on 24.03.2017.
 */
import java.sql.SQLException;
import java.util.Scanner;

public class db {



    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        conn.Conn();
        conn.CreateDB();
        Extra.showComm();
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();



       switch (input){
            //--------Просмотр таблицы--------
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

           //--------Запись в таблицу--------
            case 2:conn.WriteDB();
                break;

           //--------Просмотр информации выбранного издания--------
            case 3:conn.ReadDB();
                System.out.println("Какое издание вам нужно?\n Выберите номер:");
                Scanner integ = new Scanner(System.in);
                int inp=integ.nextInt();
                conn.ShowLine(inp);
                break;

           //--------Удаление информации выбранного издания--------
            case 4:
                conn.ReadDB();
                System.out.println("Какое издание вы хотите удалить?\n Выберите номер столбца Id:");
                Scanner intd = new Scanner(System.in);
                int aDel=intd.nextInt();// Число Id
                conn.deleteRecordFromTable(aDel);
                break;
           //--------Выход--------
           case 5:
                break;
            default:
                System.out.println("We haven't this command, try again and choose 1-5");



        }







    }
}