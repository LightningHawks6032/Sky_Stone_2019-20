package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@TeleOp(name = "BeltBot TeleOp", group = "Iterative Opmode")
public class RobotTeleOp extends OpMode {
    private BeltBot_Hardware hardware;

    public void init(){
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, false);
        hardware.initHardware();
        //hardware.drivetrain.runWithEncoders();
        //hardware.drivetrain.gyro.calibrate();
    }

    public void loop(){
        hardware.manageTeleOp();
        /*
        telemetry.addData("FL Encoder Val: ", hardware.drivetrain.leftFrontEncoder.getEncoderCount());
        telemetry.addData("FR Encoder Val: ", hardware.drivetrain.rightFrontEncoder.getEncoderCount());
        telemetry.addData("BL Encoder Val: ", hardware.drivetrain.leftBackEncoder.getEncoderCount());
        telemetry.addData("BR Encoder Val: ", hardware.drivetrain.rightBackEncoder.getEncoderCount());
        telemetry.addData("Gyro heading: ", hardware.drivetrain.gyro.getHeading());
        telemetry.addData("Gyro Angle: ", hardware.drivetrain.gyro.getAngle());
        telemetry.update();
        */
    }
}
