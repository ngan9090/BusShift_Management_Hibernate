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
        System.out.print("Nh???p id c???a t??i x???: ");
        int driver_id = 0;
        do {
            driver_id = CheckInput.checkNumber();
            if (DriverRepository.findById(driver_id) == 1) break;
            System.out.print("B???n c???n nh???p l???i s??? m?? t??i x??? ???? t???n t???i: ");
        } while (true);
        this.setDriverId(driver_id);
        System.out.print("Nh???p m?? c???a tuy???n: ");
        int busRoute_id = 0;
        do {
            busRoute_id = CheckInput.checkNumber();
            if (BusRouteRepository.findById(busRoute_id) == 1 &&
                    ScheduleManagementReposity.checkUnique(driver_id,busRoute_id) != 1) break;
            System.out.print("B???n c???n nh???p l???i s??? m?? tuy???n ???? t???n t???i, v?? kh??ng b??? tr??ng: ");
        } while (true);
        this.setBusRouteId(busRoute_id);
        System.out.print("Nh???p s??? l?????t ch???y c???a t??i x??? ??? tuy???n n??y: ");
        int turn = 0;
        do {
            turn = CheckInput.checkNumber();
            if (ScheduleManagementReposity.checkLimitTurn(turn,driver_id) <= 15) break;
            System.out.print("S??? l?????t ch???y ???? v?????t qu?? 15 l?????t. Xin vui l??ng nh???p l???i: ");
        } while (true);
        this.setTurnNumber(turn);
    }

}

