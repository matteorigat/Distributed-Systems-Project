package Greenfield;

import Greenfield.CleaningRobot.CleaningRobotController;

public class CleaningRobotStart {

    public static void main(String[] args) throws InterruptedException {
        CleaningRobotController cr = new CleaningRobotController();
        cr.Init();
        System.exit(0);
    }
}
