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
        telemetry.addData("Pow: ", hardware.outtake.debugPow);
        telemetry.addData("Lift: ", hardware.outtake.debugLift);
        telemetry.addData("Low: ", hardware.outtake.debugLow);
        telemetry.addData("Encoder val: ", hardware.outtake.debugEncoder);

        //off switch for slide limiting
        if(gamepad1.a && gamepad1.dpad_down){
            limiting = false;
        }
    }
}
