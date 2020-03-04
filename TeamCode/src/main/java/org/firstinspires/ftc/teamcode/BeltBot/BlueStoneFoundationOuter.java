package org.firstinspires.ftc.teamcode.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

@Autonomous(name = "Blue Stone-Foundation Outer Park", group = "Autonomous")
public class BlueStoneFoundationOuter extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private GeneralDetector detector;
    private Auto auto;
    private final int QUADRANT = 2;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;

    public void runOpMode() throws  InterruptedException{
        detector = new GeneralDetector(hardwareMap,0,0);

        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        auto = new Auto(this, hardware);
        hardware.initHardware();
        detector.setupTracker();
        auto.setStartAngle(QUADRANT);
        auto.setStartPosition(QUADRANT);
        hardware.sounds.playMegalovenia();
        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        auto.rest();
        int stone = auto.vuforiaStone(ALLIANCE, detector);
        //hardware.drivetrain.driveDistance(1, auto.fieldMap.STONE_WIDTH, 0.5);
        auto.getFoundationFromStone(false, QUADRANT);


        hardware.drivetrain.driveDistance(-1, auto.fieldMap.SQUARE_LENGTH, 0.5);
        auto.driveToParkFromStone(false, ALLIANCE);

        hardware.intake.clampersDown();
    }
}
