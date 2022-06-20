package repository;

import constant.Database;
import entity.BusRoute;
import entity.Driver;
import util.DatabaseConnection;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import util.HibernateUtil;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BusRouteRepository {
    public static final String BUS_TABLE_NAME = "BUS_ROUTE";
    public static final String SEQUENCE_TABLE = "BUS_ROUTE_SEQ";
    private static final Connection connection;

    static {
        connection = DatabaseConnection.openConnection(Database.DRIVER_STRING, Database.URL, Database.USERNAME, Database.PASSWORD);
    }


    public static void createTableSQE() {
        try {
            PreparedStatement pres = connection.prepareStatement("SELECT last_number "
                    + " FROM user_sequences"
                    + " WHERE sequence_name = '" + SEQUENCE_TABLE + "'");
            pres.executeQuery();
            if (!pres.executeQuery().next()) {
                try {
                    PreparedStatement pre = connection.prepareStatement("CREATE SEQUENCE " + SEQUENCE_TABLE
                            + " START WITH  100 "
                            + " INCREMENT BY 1 "
                            + " NOCACHE "
                            + " NOCYCLE");
                    pre.executeQuery();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createTable() {
        DatabaseMetaData dbm = null;
        try {
            dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, Database.USERNAME, BUS_TABLE_NAME, null);
            if (!tables.next()) {
                try {
                    PreparedStatement pre = connection.prepareStatement("CREATE TABLE " + BUS_TABLE_NAME + "("
                            + " Ids number primary key, "
                            + " Distances number NOT NULL, "
                            + " Busstop number NOT NULL)");
                    pre.executeQuery();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void showBusRoute() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM " + BUS_TABLE_NAME;
        NativeQuery<BusRoute> nativeQuery = session.createSQLQuery(sql).addEntity(BusRoute.class);
        nativeQuery.list().forEach(System.out::println);
    }

    protected static void addNew(BusRoute busRoute) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(busRoute);
        session.getTransaction().commit();
    }

    public static void createNewBusRoute() {
        BusRouteRepository busRouteRepository = new BusRouteRepository();
        System.out.print("Bạn muốn nhập thêm mấy tuyến mới: ");
        int newBusRouteNumber = 0;
        do {
            try {
                newBusRouteNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Số lượng tuyến phải là số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (newBusRouteNumber > 0) {
                break;
            }
            System.out.print("Số lượng tài xế KHÔNG được là số âm, yêu cầu nhập lại: ");
        } while (true);
        for (int i = 0; i < newBusRouteNumber; i++) {
            System.out.println("Nhập thông tin tuyến thứ " + (i + 1));
            BusRoute busRoute = new BusRoute();
            busRoute.inputNewData();
            BusRouteRepository.addNew(busRoute);
        }
    }

    //Find id
    public static int findById(int id) {
        int num = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM " + BUS_TABLE_NAME + " WHERE ids = '" + id +"'";
        NativeQuery<BusRoute> nativeQuery = session.createSQLQuery(sql).addEntity(BusRoute.class);
        if(!nativeQuery.list().isEmpty()){
            num = 1;
        }
        return num;
    }
}
