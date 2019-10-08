package org.firstinspires.ftc.teamcode.WeekendBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.WeekendBot_Hardware;


@TeleOp(name = "WeekendBot TeleOp", group = "Iterative Opmode")
public class RobotTeleOp extends OpMode {
    private WeekendBot_Hardware hardware;

    public void init(){
        hardware = new WeekendBot_Hardware(hardwareMap, gamepad1, gamepad2, false);
    }

    public void loop(){
        hardware.drivetrain.manageTeleOp();
        hardware.outtake.manageTeleOp();
        hardware.intake.manageTeleOp();
    }
}
