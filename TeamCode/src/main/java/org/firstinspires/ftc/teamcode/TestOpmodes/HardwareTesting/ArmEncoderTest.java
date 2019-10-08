package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.WeekendBot_Hardware;
import org.firstinspires.ftc.teamcode.Hardware.WeekendBot_Outtake;


@TeleOp(name = "Weekend Raise Encoder Test", group = "Test Opmode")
public class ArmEncoderTest extends OpMode {

    WeekendBot_Hardware hardware;
    WeekendBot_Outtake outtake;

    public void init(){
        hardware = new WeekendBot_Hardware(hardwareMap, gamepad1, gamepad2, false);
        hardware.outtake.raiseEncoder.reset();
        hardware.initHardware();
        outtake = hardware.outtake;
    }

    public void loop(){
        double pow = gamepad1.left_stick_y*0.1;
        outtake.raiseMotor.setPower(pow);

        telemetry.addLine("Use gamepad1 left stick");
        telemetry.addData("Raise power = ",outtake.raiseMotor.getPower());
        telemetry.addData("Raise encoder count = ", outtake.raiseEncoder.getEncoderCount());
        telemetry.update();
    }
}
