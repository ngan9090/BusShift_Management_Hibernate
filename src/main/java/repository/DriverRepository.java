package repository;

import Main.MainRun;
import constant.Database;
import entity.Driver;
import util.DatabaseConnection;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import util.HibernateUtil;

import javax.sound.midi.Sequence;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DriverRepository {
    public static final String DRIVER_TABLE_NAME = "DRIVER";
    public static final String SEQUENCE_TABLE = "DRIVER_SEQ";
    private static final Connection connection;


    static {
        connection = DatabaseConnection.openConnection(Database.DRIVER_STRING, Database.URL, Database.USERNAME, Database.PASSWORD);
    }


    public static void createTableSQE(){
        try {
            PreparedStatement pres = connection.prepareStatement("SELECT last_number "
                    + " FROM user_sequences"
                    + " WHERE sequence_name = '" + SEQUENCE_TABLE + "'");
            pres.executeQuery();
            if(!pres.executeQuery().next()) {
                try {
                    PreparedStatement pre = connection.prepareStatement("CREATE SEQUENCE " + SEQUENCE_TABLE
                            + " START WITH  10000 "
                            + " INCREMENT BY 1 "
                            + " NOCACHE "
                            + " NOCYCLE ");
                    pre.executeQuery();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createTable(){
        DatabaseMetaData dbm = null;
        try {
            dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, Database.USERNAME, DRIVER_TABLE_NAME , null);
            if(!tables.next()) {
                try {
                    PreparedStatement pre = connection.prepareStatement("CREATE TABLE " + DRIVER_TABLE_NAME + "("
                            + "Ids number primary key, "
                            + "Names VARCHAR (20) NOT NULL, "
                            + "Address VARCHAR (100) NOT NULL, "
                            + "PhoneNumber VARCHAR (20) NOT NULL, "
                            + "Levels VARCHAR (20))");
                    pre.executeQuery();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void showDriver() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM " + DRIVER_TABLE_NAME;
        NativeQuery<Driver> nativeQuery = session.createSQLQuery(sql).addEntity(Driver.class);
        nativeQuery.list().forEach(System.out::println);
    }

    protected static void addNew(Driver driver) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(driver);
        session.getTransaction().commit();
    }

    public static void createNewDriver() {
        DriverRepository driverRepository = new DriverRepository();
        System.out.print("B???n mu???n nh???p th??m m???y l??i xe m???i: ");
        int newDriverNumber = 0;
        do {
            try {
                newDriverNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("S??? l?????ng t??i x??? ph???i l?? s??? nguy??n, y??u c???u nh???p l???i: ");
                continue;
            }
            if (newDriverNumber > 0) {
                break;
            }
            System.out.print("S??? l?????ng t??i x??? KH??NG ???????c l?? s??? ??m, y??u c???u nh???p l???i: ");
        } while (true);
        for (int i = 0; i < newDriverNumber; i++) {
            System.out.println("Nh???p th??ng tin t??i x??? th??? " + (i + 1));
            Driver driver = new Driver();
            driver.inputNewData();
            driverRepository.addNew(driver);
        }
    }

    public static int findById(int id) {
        int num = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM " + DRIVER_TABLE_NAME + " WHERE ids = '" + id +"'";
        NativeQuery<Driver> nativeQuery = session.createSQLQuery(sql).addEntity(Driver.class);
        if(!nativeQuery.list().isEmpty()){
            System.out.println(nativeQuery.list() + "kt");
            num = 1;
        }
        System.out.println(num + "num");
        return num;
    }
}
