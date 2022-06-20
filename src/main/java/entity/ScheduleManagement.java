package entity;

import repository.BusRouteRepository;
import repository.DriverRepository;
import repository.ScheduleManagementReposity;
import util.CheckInput;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SCHEDULE_MANAGEMENT")
public class ScheduleManagement implements AutoIdIncreasable, NewDataCreatable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULE_SEQ")
    @SequenceGenerator(name = "SCHEDULE_SEQ", sequenceName = "SCHEDULE_SEQ",allocationSize = 0)
    @Column(nullable = false)
    private int ids;

    @Id
    @ManyToOne(targetEntity = entity.Driver.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "driverid", nullable = false)
    Driver driver;

    @Transient
    int driverId;

    @Id
    @ManyToOne(targetEntity = entity.BusRoute.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "busrouteid", nullable = false)
    BusRoute busRoute;

    @Transient
    int busRouteId;

    @Column (name = "TURNNUMBER", nullable = true)
    private int turnNumber;

    public ScheduleManagement() {

    }

    public ScheduleManagement(int driverId, int busRouteId, int turnNumber) {
        this.driverId = driverId;
        this.busRouteId = busRouteId;
        this.turnNumber = turnNumber;
    }

    public int getId() {
        return ids;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(int busRouteId) {
        this.busRouteId = busRouteId;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    @Override
    public String toString() {
        return "ScheduleManagement{" +
                "id=" + ids +
                ", driverId=" + this.driver.getIds() +
                ", busRouteId=" + this.busRoute.getId() +
                ", routeNumber=" + turnNumber +
                '}';
    }

    @Override
    public void increaseId() {
    }

    @Override
    public void inputNewData() {
        System.out.print("Nhập id của tài xế: ");
        int driver_id = 0;
        do {
            driver_id = CheckInput.checkNumber();
            if (DriverRepository.findById(driver_id) == 1) break;
            System.out.print("Bạn cần nhập lại số mã tài xế đã tồn tại: ");
        } while (true);
        this.setDriverId(driver_id);
        System.out.print("Nhập mã của tuyến: ");
        int busRoute_id = 0;
        do {
            busRoute_id = CheckInput.checkNumber();
            if (BusRouteRepository.findById(busRoute_id) == 1 &&
                    ScheduleManagementReposity.checkUnique(driver_id,busRoute_id) != 1) break;
            System.out.print("Bạn cần nhập lại số mã tuyến đã tồn tại, và không bị trùng: ");
        } while (true);
        this.setBusRouteId(busRoute_id);
        System.out.print("Nhập số lượt chạy của tài xế ở tuyến này: ");
        int turn = 0;
        do {
            turn = CheckInput.checkNumber();
            if (ScheduleManagementReposity.checkLimitTurn(turn,driver_id) <= 15) break;
            System.out.print("Số lượt chạy đã vượt quá 15 lượt. Xin vui lòng nhập lại: ");
        } while (true);
        this.setTurnNumber(turn);
    }

}

