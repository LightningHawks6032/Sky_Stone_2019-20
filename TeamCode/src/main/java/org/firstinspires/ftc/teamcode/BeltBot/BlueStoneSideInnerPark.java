package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@Autonomous(name = "Blue Depot Side Inner Park", group = "Autonomous")
//@Disabled
public class BlueStoneSideInnerPark extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private Auto auto;
    private final int QUADRANT = 2;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;

    public void runOpMode() throws  InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        auto = new Auto(this, hardware);
        hardware.initHardware();
        hardware.detector.setupTracker();

        auto.setStartAngle(QUADRANT);
        auto.setStartPosition(QUADRANT);
        hardware.sounds.playMegalovenia();
        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        auto.rest();
        int stone = auto.vuforiaStone(ALLIANCE);
        /*
        auto.getFoundationFromStone(true, QUADRANT);

        hardware.drivetrain.driveDistance(-1, auto.fieldMap.SQUARE_LENGTH*2, 0.5);
        hardware.drivetrain.strafeDistance(1, auto.fieldMap.STONE_WIDTH*2, 0.6);

        hardware.intake.clampersDown();
        */
        telemetry.addData("Stone num", stone);
        telemetry.update();

        //int stoneNum = auto.dogeCV.detectSkyStoneAlign();
        //auto.grabFirstStonePark(stoneNum, ALLIANCE, true);
        Thread.sleep(10000);
        //auto.depotSidePark(true, ALLIANCE);
    }
}
