package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.BeltBot.Auto;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@Autonomous(name = "flip flippers")
public class LitterallyJustTheFlippers extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private Auto auto;

    public void runOpMode() throws  InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        auto = new Auto(this, hardware);
        hardware.initHardware();

        hardware.sounds.playMegalovenia();
        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        auto.rest();
        hardware.intake.clampersDown();
    }
}
