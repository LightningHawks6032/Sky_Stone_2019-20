package org.firstinspires.ftc.teamcode.WeekendBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.WeekendBot.WeekendBot_Hardware;
import org.firstinspires.ftc.teamcode.Misc.Sounds;

@Autonomous(name="Auto that just moves forward", group = AutonomousData.OFFICIAL_GROUP)
public class AutoThatJustMovesForward extends LinearOpMode {
    private WeekendBot_Hardware hardware;
    private Auto auto;
    //private Sounds haha;

    public void runOpMode() throws InterruptedException{

        hardware = new WeekendBot_Hardware(hardwareMap, gamepad1, gamepad2, false);
        //haha = new Sounds(hardwareMap);
        auto = new Auto(this, hardware);
        hardware.initHardware();

        //haha.playMegalovenia();
        telemetry.addLine("Autonomous Ready");
        telemetry.update();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        //haha.playMegalovenia();
        sleep(500);
        //haha.playMegalovenia();
        hardware.drivetrain.driveForTime(0.2, 1);
        /*
        hardware.drivetrain.leftBack.setPower(.3);
        hardware.drivetrain.rightBack.setPower(.3);
        hardware.drivetrain.leftFront.setPower(.3);
        hardware.drivetrain.rightFront.setPower(.3);
        //haha.playMegalovenia();
        sleep(2000);
        hardware.drivetrain.leftBack.setPower(0);
        hardware.drivetrain.rightBack.setPower(0);
        hardware.drivetrain.leftFront.setPower(0);
        hardware.drivetrain.rightFront.setPower(0);

         */
    }
}
