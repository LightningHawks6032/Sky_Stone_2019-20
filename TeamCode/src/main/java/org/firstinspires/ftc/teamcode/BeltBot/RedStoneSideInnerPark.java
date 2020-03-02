package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

@Autonomous(name = "Red Depot Side Inner Park", group = "Autonomous")
//@Disabled
public class RedStoneSideInnerPark extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private Auto auto;
    private GeneralDetector detector;
    private final int QUADRANT = 3;
    private final int ALLIANCE = AutonomousData.RED_ALLIANCE;

    public void runOpMode() throws  InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        detector = new GeneralDetector(hardwareMap, 0, 0);
        auto = new Auto(this, hardware);
        hardware.initHardware();
        detector.setupTracker();

        auto.setStartAngle(QUADRANT);
        auto.setStartPosition(QUADRANT);
        hardware.sounds.playMegalovenia();
        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        auto.rest();
        int stone = auto.vuforiaStone(ALLIANCE, detector);
        //auto.getFoundationFromStone(true, QUADRANT);
        telemetry.addData("Stone num", stone);
        telemetry.update();

        //int stoneNum = auto.dogeCV.detectSkyStoneAlign();
        //auto.grabFirstStonePark(stoneNum, ALLIANCE, true);
        Thread.sleep(10000);
        //auto.depotSidePark(true, ALLIANCE);
    }
}
