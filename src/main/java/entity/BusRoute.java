package entity;

import util.CheckInput;

import javax.persistence.*;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

@Entity
@Table(name = "BUS_ROUTE")
public class BusRoute implements AutoIdIncreasable, NewDataCreatable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUS_ROUTE_SEQ")
    @SequenceGenerator(name = "BUS_ROUTE_SEQ", sequenceName = "BUS_ROUTE_SEQ", allocationSize = 0)
    @Column(nullable = false)
    private int ids;

    @Column(name = "DISTANCES", nullable = false)
    private float distance;

    @Column(name = "BUSSTOP", nullable = false)
    private int stopStationNumber;

    public BusRoute() {
    }

    public BusRoute(float distance, int stopStationNumber) {
        this.distance = distance;
        this.stopStationNumber = stopStationNumber;
    }

    @Override
    public final void increaseId() {
        this.ids = ids;
    }

    public int getId() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getStopStationNumber() {
        return stopStationNumber;
    }

    public void setStopStationNumber(int stopStationNumber) {
        this.stopStationNumber = stopStationNumber;
    }

    @Override
    public String toString() {
        return "BusRoute{" +
                "id=" + ids +
                ", distance=" + distance +
                ", stopStationNumber=" + stopStationNumber +
                '}';
    }

    @Override
    public void inputNewData() {
        System.out.print("Nhập khoảng cách của tuyến xe: ");
        float distance = 0;
        do {
            try {
                distance = new Scanner(System.in).nextFloat();
            } catch (InputMismatchException ex) {
                System.out.print("Khoảng cách của phải là số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (distance > 0) {
                this.setDistance(distance);
                break;
            }
            System.out.print("Khoảng cách của tuyến mới KHÔNG được là số âm, yêu cầu nhập lại: ");
        } while (true);

        System.out.print("Nhập số điểm dừng của tuyến xe: ");
        int stopStationNumber = CheckInput.checkNumber();
        this.setStopStationNumber(stopStationNumber);
    }
}
