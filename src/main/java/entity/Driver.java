package entity;

import Main.MainRun;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.sound.midi.Soundbank;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

@Entity
@Table(name = "DRIVER")
public class Driver implements AutoIdIncreasable, NewDataCreatable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DRIVER_SEQ")
    @SequenceGenerator(name = "DRIVER_SEQ", sequenceName = "DRIVER_SEQ",allocationSize = 0)
    @Column(nullable = false)
    private int ids;

    private static final String A = "LEVEL_A";
    private static final String B = "LEVEL_B";
    private static final String C = "LEVEL_C";
    private static final String D = "LEVEL_D";
    private static final String E = "LEVEL_E";
    private static final String F = "LEVEL_F";


    @Column (name = "LEVELS", nullable = true)
    private String level;

    @Column(name = "NAMES", nullable = false)
    protected String name;
    @Column(name = "ADDRESS", nullable = false)
    protected String address;
    @Column(name = "PHONENUMBER", nullable = false)
    protected String phone;

    public Driver() {

    }

    public Driver(String name, String address, String phone, String level) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.level = level;
    }

    @Override
    public final void increaseId() {

    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + ids +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", level=" + level +
                '}';
    }

    @Override
    public void inputNewData() {
        System.out.print("Nhập tên của tài xế: ");
        this.setName(new Scanner(System.in).nextLine());
        System.out.print("Nhập địa chỉ của tài xế: ");
        this.setAddress(new Scanner(System.in).nextLine());
        System.out.print("Nhập số điện thoại của tài xế: ");
        this.setPhone(new Scanner(System.in).nextLine());
        System.out.println("Nhập trình độ của tài xế, chọn 1 trong các trình độ dưới đây: ");
        System.out.println("1. Loại A");
        System.out.println("2. Loại B");
        System.out.println("3. Loại C");
        System.out.println("4. Loại D");
        System.out.println("5. Loại E");
        System.out.println("6. Loại F");
        System.out.print("Vui lòng Chọn trình độ: ");
        int driverLevel = -1;
        do {
            try {
                driverLevel = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Chức năng cần chọn là 1 số nguyên, yêu cầu nhập lại: ");
                continue;
            }
            if (driverLevel >= 1 && driverLevel <= 6) {
                break;
            }
            System.out.print("Chức năng vừa chọn không hợp lệ, vui lòng nhập lại: ");
        } while (true);
        switch (driverLevel) {
            case 1:
                this.setLevel(A);
                break;
            case 2:
                this.setLevel(B);
                break;
            case 3:
                this.setLevel(C);
                break;
            case 4:
                this.setLevel(D);
                break;
            case 5:
                this.setLevel(E);
                break;
            case 6:
                this.setLevel(F);
                break;
        }
    }
}

