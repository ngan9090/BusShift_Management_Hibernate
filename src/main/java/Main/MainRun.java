package Main;


import repository.BusRouteRepository;
import repository.DriverRepository;
import repository.ScheduleManagementReposity;

import java.util.InputMismatchException;
import java.util.Scanner;


public class MainRun {

    public static void main(String[] args) {
        initData();
        menu();
    }

    //Create and check table
    public static void initData() {
        DriverRepository.createTable();
        DriverRepository.createTableSQE();
        BusRouteRepository.createTable();
        BusRouteRepository.createTableSQE();
        ScheduleManagementReposity.createTable();
        ScheduleManagementReposity.createTableSQE();
    }

    private static void menu() {
        do {
            int functionChoice = showMenu();
            switch (functionChoice) {
                case 1:
                    DriverRepository.createNewDriver();
                    break;
                case 2:
                    DriverRepository.showDriver();
                    break;
                case 3:
                    BusRouteRepository.createNewBusRoute();
                    break;
                case 4:
                    BusRouteRepository.showBusRoute();
                    break;
                case 5:
                    ScheduleManagementReposity.createNewSchedule();
                    break;
                case 6:
                    ScheduleManagementReposity.showSchedule();
                    break;
                case 7:
                    //ScheduleManagementRepository.sortRouteManagerment();
                    break;
                case 8:
                    //ScheduleManagementRepository.calculateTotalRound();
                    break;
                case 9:
                    return;
            }
        } while (true);
    }

    private static int showMenu() {
        printMenu();
        int functionChoice = -1;
        do {
            try {
                functionChoice = new Scanner(System.in).nextInt();
            } catch (InputMismatchException ex) {
                System.out.print("Ch???c n??ng c???n ch???n l?? 1 s??? nguy??n, y??u c???u nh???p l???i: ");
                continue;
            }
            if (functionChoice >= 1 && functionChoice <= 9) {
                break;
            }
            System.out.print("Ch???c n??ng v???a ch???n kh??ng h???p l???, vui l??ng nh???p l???i: ");
        } while (true);
        return functionChoice;
    }

    private static void printMenu() {
        System.out.println("--------PH???N M???M QU???N L?? PH??N C??NG L??T XE BU??T------");
        System.out.println("1. Nh???p danh s??ch l??i xe m???i.");
        System.out.println("2. In ra danh s??ch l??i xe m???i.");
        System.out.println("3. Nh???p danh s??ch tuy???n xe m???i.");
        System.out.println("4. In ra danh s??ch tuy???n xe m???i.");
        System.out.println("5. Ph??n c??ng l??i xe cho c??c t??i x???.");
        System.out.println("6. In ra danh s??ch ???? ph??n c??ng.");
        System.out.println("7. S???p x???p danh s??ch ph??n c??ng l??i xe.");
        System.out.println("8. L???p b???ng th???ng k?? t???ng kho???ng c??ch ch???y xe trong ng??y c???a t???ng l??i xe.");
        System.out.println("9. Tho??t");
        System.out.print(" Xin m???i ch???n ch???c n??ng: ");
    }
}
