package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@Autonomous(name = "Go Forwards and RIGHT", group = "Panic Autonomous")
public class GoForwardStrafeRight extends LinearOpMode {
    public BeltBot_Hardware hardware;
    public Auto auto;

    public void runOpMode() throws InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        auto = new Auto(this, hardware);
        hardware.initHardware();
        auto.setStartAngle(1);

        hardware.sounds.playMegalovenia();
        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        hardware.drivetrain.driveDistance(1, auto.fieldMap.SQUARE_LENGTH, 0.5);
        hardware.drivetrain.strafeDistance(1, auto.fieldMap.SQUARE_LENGTH, 0.5);
    }
}