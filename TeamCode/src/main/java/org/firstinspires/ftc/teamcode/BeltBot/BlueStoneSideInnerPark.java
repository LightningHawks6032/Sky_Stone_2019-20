package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@Autonomous(name = "Bones Binner", group = "Autonomous")
public class BlueStoneSideInnerPark extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private Auto auto;
    private final int QUADRANT = 2;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;

    public void runOpMode() throws  InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        auto = new Auto(this, hardware);
        hardware.initHardware();

        auto.setStartAngle(QUADRANT, 4);
        auto.setStartPosition(QUADRANT);
        hardware.sounds.playMegalovenia();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        hardware.drivetrain.strafeDistance(1, auto.fieldMap.SQUARE_LENGTH*1.5, 0.5);
        hardware.drivetrain.driveDistance(1, auto.fieldMap.SQUARE_LENGTH, 0.5);
        hardware.sounds.playMegalovenia();
    }
}
