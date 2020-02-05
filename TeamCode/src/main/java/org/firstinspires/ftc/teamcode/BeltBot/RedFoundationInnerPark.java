package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@Autonomous(name = "Red Foundation Side Inner Park", group = "Autonomous")
//@Disabled
public class RedFoundationInnerPark extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private Auto auto;
    private final int QUADRANT = 4;
    private final int ALLIANCE = AutonomousData.RED_ALLIANCE;

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
        auto.getFoundation(QUADRANT, true);
        hardware.drivetrain.turn(90, false);
        //auto.strafeToPark(true, QUADRANT);

        hardware.intake.clampersDown();
        auto.nudgeFoundation(QUADRANT, true);
    }
}
