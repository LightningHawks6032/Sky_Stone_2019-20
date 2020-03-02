package org.firstinspires.ftc.teamcode.TestOpmodes.AutoTesting;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.BeltBot.Auto;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldElement;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldMap;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

@Autonomous(name = "Auto Tester", group = "Autonomous Tests")
//@Disabled
public class MethodDumpingGrounds extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private FieldMap map;
    private Auto auto;
    private GeneralDetector detector;
    private final int QUADRANT = 1;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;

    public void runOpMode() throws InterruptedException{



        map = new FieldMap();
        detector = new GeneralDetector(hardwareMap, 0, 0);
        //detector.setupTracker();
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        hardware.initHardware();
        auto = new Auto(this, hardware);
        auto.setStartAngle(QUADRANT);
        auto.setStartPosition(QUADRANT);

        telemetry.addLine("Ready");
        //telemetry.addData("Theta of foundation: ", auto.fieldMap.get(FieldElement.BOUNDATION_GRAB_POINT).theta());
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());
        //detector.lookForTargets();
        hardware.drivetrain.strafeDistanceCorrectAngle(1, 12, 0.6);
        Thread.sleep(500);
        hardware.drivetrain.strafeDistanceCorrectAngle(-1, map.clawPhoneDist, 0.6);

        /*
        //auto.getFoundation(QUADRANT);
        //hardware.drivetrain.strafeDistance(1, map.SQUARE_LENGTH*2, 0.5);

        //hardware.drivetrain.face(map.get(FieldElement.NINNERPARK));

        //hardware.drivetrain.strafeDistanceCorrectAngle(1, auto.fieldMap.SQUARE_LENGTH, 0.6);
        hardware.drivetrain.turnToAngle(90);
        //hardware.drivetrain.turn(90, true);
        telemetry.addData("Angle", hardware.drivetrain.robotAngle);
        Thread.sleep(3000);
        //hardware.drivetrain.turnCorrectAngle(90, true);

        //hardware.drivetrain.turnCorrectAngle(190, false);
        */



    }

}
