package org.firstinspires.ftc.teamcode.TestOpmodes.AutoTesting;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldElement;
import org.firstinspires.ftc.teamcode.Hardware.WeekendBot.WeekendBot_Hardware;
import org.firstinspires.ftc.teamcode.WeekendBot.Auto;

@Autonomous(name = "GoTo Test", group = "Test Opmode")
@Disabled
public class GoToPleaseWork extends LinearOpMode {

    private Auto auto;
    private WeekendBot_Hardware hardware;
    private final int QUADRANT = 2;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;

    public void runOpMode() throws InterruptedException{
        hardware = new WeekendBot_Hardware(hardwareMap,gamepad1,gamepad2,true);
        auto = new Auto(this, hardware);
        hardware.initHardware();
        hardware.drivetrain.setRobotAngle(90);

        telemetry.addLine("Successfully set up");
        telemetry.update();

        waitForStart();
        auto.setStartTime(System.currentTimeMillis());
        hardware.drivetrain.gyro.zero();
        hardware.drivetrain.encoderSetup();

        hardware.drivetrain.setRobotPos(auto.fieldMap.get(FieldElement.BPOS1));
        hardware.drivetrain.face(auto.fieldMap.get(FieldElement.NINNERPARK));
    }
}
