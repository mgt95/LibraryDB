/**
 * Created by Meiram on 24.03.2017.
 */
import java.sql.*;
import java.util.Formatter;
import java.util.Scanner;


public class conn {

    private static Connection conn;
    private static Statement statmt;
    private static ResultSet resSet;
    private static String aName;
    private static String aType;
    private static int aPages;
    private static String aHouse;
    private static String aDate;

    public static void setName(){
        System.out.println("Enter name");
        Scanner in = new Scanner(System.in);
        aName = in.nextLine();
    }
    public static void setType(){
        System.out.println("Enter Type:");
        System.out.println("1 - Book;");
        System.out.println("2 - Journal;");
        System.out.println("3 - Brochure;");

        Scanner in = new Scanner(System.in);
        int type = in.nextInt();
        switch (type) {
            case 1:
                aType = "Book";
                break;
            case 2:
                aType = "Journal";
                break;
            case 3:
                aType = "Brochure";
                break;

        }
    }
    public static void setPages(){
        System.out.println("Enter number of pages");
        Scanner in = new Scanner(System.in);
        aPages = in.nextInt();
    }
    public static void setHouse(){
        System.out.println("Enter House name");
        Scanner in = new Scanner(System.in);
        aHouse = in.nextLine();
    }
    public static void setDate(){
        System.out.println("Enter Year for book");
        Scanner in = new Scanner(System.in);
        String year = in.nextLine();
        aDate = year;
    }
    public static void setMonth(){
        System.out.println("Enter year and month of journal/brochure");
        Scanner in = new Scanner(System.in);
        String year = in.nextLine();
        aDate = year;
            }

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        try {


            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:DataBaseForLibrary.s3db");

            System.out.println("База Подключена!");
        }catch (SQLException ex){
            System.out.println("Не удалось соединение");
            return;
        }
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'Lib' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Name' text, 'Type' text, 'Pages' INT, 'Publishing house' text );");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        setName();
        setType();
        setPages();
        setHouse();
        if(aType == "Book"){
            setDate();
        }
        else{
            setMonth();
        }
        //statmt.execute("INSERT INTO 'Lib' ('Name', 'Type', 'Pages', 'Publishing_house') VALUES ('Book1', 'Book', 1254, 'House1'); ");//
        String insert = "INSERT INTO 'Lib'('Name', 'Type', 'Pages', 'Publishing_house', 'Date_Of_Publication') VALUES(?, ?, ?, ?, ?)";
        PreparedStatement prepInsert = conn.prepareStatement(insert);
        prepInsert.setString(1,aName);
        prepInsert.setString(2,aType);
        prepInsert.setInt(3,aPages);
        prepInsert.setString(4,aHouse);
        prepInsert.setString(5,aDate);
        prepInsert.execute();
        System.out.println("Таблица заполнена");

        conn.close();
        statmt.close();
    }
    // --------Удаление издания--------
    public static void deleteRecordFromTable(int number) throws SQLException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        int a=number;

        try {
            conn = getDBConnection();
            preparedStatement = conn.prepareStatement("DELETE FROM Lib WHERE  id ="+a);
            //preparedStatement.setInt(1, 1001);

            // execute delete SQL stetement
            preparedStatement.executeUpdate();
            /*resSet = statmt.executeQuery("SELECT * FROM Lib");
            while(resSet.next())


            {



            }*/
            System.out.println("Запись удалена");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (conn != null) {
                conn.close();
            }

        }

    }

    private static Connection getDBConnection() {

        Connection conn = null;

        try {

            Class.forName("org.sqlite.JDBC");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            conn = DriverManager.getConnection("jdbc:sqlite:DataBaseForLibrary.s3db");
            return conn;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return conn;

    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM Lib");
        Formatter fmt = new Formatter();
        fmt.format("%5s %25s %15s %12s %20s %20s %5s\n", "#Num", "Name", "Type", "Pages", "Publishing house","Date_Of_Publication", "Id");
        System.out.println(fmt);
        int i=0;
        while(resSet.next())
        {

            i++;
            int id = resSet.getInt("Id");
            String  name = resSet.getString("Name");
            String  type = resSet.getString("Type");
            int pages = resSet.getInt("Pages");
            String dateofpub = resSet.getString("Date_Of_Publication");
            String house = resSet.getString("Publishing_House");
            Formatter fmt1 = new Formatter();
            fmt1.format("%5d %25s %15s %12d %20s %20s %5d\n", i, name, type, pages, house,  dateofpub, id);
            System.out.print(fmt1);


        }

        System.out.println("Таблица выведена");
    }

    //--------Просмотр информации выбранного издания--------
    public static void ShowLine(int i) throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM Lib");
        while(resSet.next())
        {
            int id = resSet.getInt("Id");
            if (id==i){
                String  name = resSet.getString("Name");
                String  type = resSet.getString("Type");
                int pages = resSet.getInt("Pages");
                String dateofpub = resSet.getString("Date_Of_Publication");
                String house = resSet.getString("Publishing_House");
                Formatter fmt1 = new Formatter();
                fmt1.format("%5d %25s %15s %12d %20s %20s\n", id, name, type, pages, house, dateofpub);
                System.out.print(fmt1);


            }

        }

    }


    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}