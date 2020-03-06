package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Misc.Sounds;

@Autonomous (name = "Blue Foundation Inner", group = "Autonomous")
public class BlueFoundationInnerPark extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private Auto auto;
    private final int QUADRANT = 1;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;

    public void runOpMode() throws  InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        auto = new Auto(this, hardware);
        hardware.initHardware();

        auto.setStartAngle(QUADRANT);
        auto.setStartPosition(QUADRANT);
        hardware.sounds.playMegalovenia();
        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        auto.rest();
        auto.getFoundation(QUADRANT, true);
        //hardware.drivetrain.turn(90, true);
        //auto.strafeToPark(true, QUADRANT);

        //hardware.intake.clampersUp();
        //auto.nudgeFoundation(QUADRANT, true);

        //hardware.drivetrain.strafeDistance(1, auto.fieldMap.SQUARE_LENGTH, 0.5);


        hardware.drivetrain.driveDistance(-1, auto.fieldMap.SQUARE_LENGTH*2, 0.5);
        hardware.drivetrain.strafeDistance(1, auto.fieldMap.STONE_WIDTH*2, 0.6);

        hardware.intake.clampersDown();
    }
}
