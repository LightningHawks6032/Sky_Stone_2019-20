package org.firstinspires.ftc.teamcode.TestOpmodes.AutoTesting;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.BeltBot.Auto;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldElement;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldMap;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;

@Autonomous(name = "Auto Tester", group = "Autonomous Tests")
@Disabled
public class MethodDumpingGrounds extends LinearOpMode {
    private BeltBot_Hardware hardware;
    private FieldMap map;
    private Auto auto;
    private final int QUADRANT = 1;
    private final int ALLIANCE = AutonomousData.BLUE_ALLIANCE;


    public void runOpMode() throws InterruptedException{
        hardware = new BeltBot_Hardware(hardwareMap, gamepad1, gamepad2, true);
        map = new FieldMap();
        auto = new Auto(this, hardware);
        hardware.initHardware();

        auto.setStartAngle(QUADRANT);
        auto.setStartPosition(QUADRANT);
        telemetry.addData("Theta of foundation: ", auto.fieldMap.get(FieldElement.BOUNDATION_GRAB_POINT).theta());
        waitForStart();
        auto.setStartTime(System.currentTimeMillis());

        //auto.getFoundation(QUADRANT);
        //hardware.drivetrain.strafeDistance(1, map.SQUARE_LENGTH*2, 0.5);

        hardware.drivetrain.face(map.get(FieldElement.NINNERPARK));
    }

}
