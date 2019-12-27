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
    }

    public void loop(){
        hardware.manageTeleOp();
    }
}
