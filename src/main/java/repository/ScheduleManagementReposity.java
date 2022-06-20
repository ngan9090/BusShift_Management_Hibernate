package repository;

import constant.Database;
import dto.Total;
import entity.Driver;
import entity.ScheduleManagement;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import util.DatabaseConnection;
import util.HibernateUtil;

import javax.xml.transform.Transformer;
import java.sql.*;
import java.lang.String;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ScheduleManagementReposity {
    public static final String SCHEDULE_TABLE_NAME = "SCHEDULE_MANAGEMENT";
    public static final String SEQUENCE_TABLE = "SCHEDULE_SEQ";
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
                            + " START WITH  1 "
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

    public static void createTable() {
        DatabaseMetaData dbm = null;
        try {
            dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, Database.USERNAME, SCHEDULE_TABLE_NAME, null);
            if (!tables.next()) {
                try {
                    PreparedStatement pre = connection.prepareStatement("CREATE TABLE " + SCHEDULE_TABLE_NAME + "("
                            + "Ids number primary key, "
                            + "driverId number not null constraint driverId references driver(ids), "
                            + "BusRouteId number not null constraint BusRouteId references bus_Route(ids), "
                            + "turnNumber number not null)");
                    pre.executeQuery();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void showSchedule() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM " + SCHEDULE_TABLE_NAME;
        NativeQuery<ScheduleManagement> nativeQuery = session.createSQLQuery(sql).addEntity(ScheduleManagement.class);
        nativeQuery.list().forEach(System.out::println);
    }

    protected static void addNew(ScheduleManagement scheduleManagement) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(scheduleManagement);
        session.getTransaction().commit();
    }

    public static void createNewSchedule() {
        ScheduleManagementReposity scheduleManagementReposity = new ScheduleManagementReposity();
        System.out.print("Bạn muốn nhập thêm mấy lịch chạy mới: ");
        int newScheduleNumber = 0;
        do {
            try {
                newScheduleNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Số lượng lịch chạy phải là số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (newScheduleNumber > 0) {
                break;
            }
            System.out.print("Số lượng lịch chạy KHÔNG được là số âm, yêu cầu nhập lại: ");
        } while (true);
        for (int i = 0; i < newScheduleNumber; i++) {
            System.out.println("Nhập thông tin lịch chạy thứ " + (i + 1));
            ScheduleManagement scheduleManagement = new ScheduleManagement();
            scheduleManagement.inputNewData();
            scheduleManagementReposity.addNew(scheduleManagement);
        }
    }

    public static int checkUnique(int driverId, int busRouteId) {
        int num = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM " + SCHEDULE_TABLE_NAME + " WHERE driverId = '" + driverId
                + "' AND busRouteId = '" + busRouteId + "'";
        NativeQuery<Driver> nativeQuery = session.createSQLQuery(sql).addEntity(ScheduleManagement.class);
        if (!nativeQuery.list().isEmpty()) {
            System.out.println(nativeQuery.list());
            num = 1;
        }
        System.out.println(num + "num");
        return num;
    }

    public static int checkLimitTurn(int turnNumber, int driverId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql  = "SELECT  *  FROM "
                + SCHEDULE_TABLE_NAME
                + " WHERE driverid = '"+driverId+"' ";
                //+ "GROUP BY driverid";
        NativeQuery<ScheduleManagement> nativeQuery = session.createSQLQuery(sql).addEntity(ScheduleManagement.class);
        nativeQuery.list().forEach(System.out::println);
        return 1;
    }


}
