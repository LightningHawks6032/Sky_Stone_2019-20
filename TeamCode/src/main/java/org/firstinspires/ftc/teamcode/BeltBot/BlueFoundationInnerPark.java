package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@Autonomous (name = "Boundation Binner", group = "Autonomous")
public class BlueFoundationInnerPark extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private Auto auto;
    private final int QUADRANT = 1;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;

    public void runOpMode() throws  InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        auto = new Auto(this, hardware);
        hardware.initHardware();

        auto.setStartAngle(QUADRANT);
        auto.setStartPosition(QUADRANT);
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        telemetry.addData("Robot Pos (starting): ", hardware.drivetrain.robotPos.toString());
        telemetry.update();
        auto.getFoundation(QUADRANT);
        telemetry.addData("Robot Pos (after grab): ", hardware.drivetrain.robotPos.toString());
        telemetry.update();
        hardware.drivetrain.turn(90, true);
        //auto.strafeToPark(true, QUADRANT);
    }
}
