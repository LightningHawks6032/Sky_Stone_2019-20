package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@Autonomous(name = "Blue Depot Side Outer Park", group = "Autonomous")
public class BlueStoneSideOuterPark extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private Auto auto;
    private final int QUADRANT = 2;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;

    public void runOpMode() throws  InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        auto = new Auto(this, hardware);
        hardware.initHardware();

        auto.setStartAngle(QUADRANT);
        auto.setStartPosition(QUADRANT);
        hardware.sounds.playMegalovenia();
        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        auto.rest();
        int stoneNum = auto.dogeCV.detectSkyStoneAlign();
        auto.grabFirstStonePark(stoneNum, ALLIANCE, false);
    }
}