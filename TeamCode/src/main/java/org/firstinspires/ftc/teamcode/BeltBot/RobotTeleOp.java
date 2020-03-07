package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@TeleOp(name = "BeltBot TeleOp", group = "Iterative Opmode")
public class RobotTeleOp extends OpMode {
    private BeltBot_Hardware hardware;
    boolean limiting = true;

    public void init(){
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, false);
        hardware.initHardware();
    }

    public void loop(){
        hardware.manageTeleOp(limiting);

        //telemetry.addData("Left Alpha Amount", hardware.leftCS.alpha());
        //telemetry.addData("Right Alpha Amount", hardware.rightCS.alpha());

        //telemetry.addData("Gyro angle", hardware.drivetrain.gyro.getAngle());

        //telemetry.update();

        //off switch for slide limiting
        if(gamepad1.a && gamepad1.dpad_down){
            limiting = false;
        }
    }
}
