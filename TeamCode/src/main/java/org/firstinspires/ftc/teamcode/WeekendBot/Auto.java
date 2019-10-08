package org.firstinspires.ftc.teamcode.WeekendBot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FieldMapping.FieldMap;
import org.firstinspires.ftc.teamcode.Hardware.WeekendBot_Hardware;


public class Auto {
    private LinearOpMode autonomous;
    private WeekendBot_Hardware hardware;
    public FieldMap fieldMap = new FieldMap();
    private long startTime;

    public Auto(LinearOpMode auto, WeekendBot_Hardware hardware){
        autonomous = auto;
        this.hardware = hardware;

        hardware.drivetrain.setAuto(auto);
        hardware.intake.setAuto(auto);
        hardware.outtake.setAuto(auto);
    }

    public void setStartTime(long time) {
        startTime = time;
        hardware.drivetrain.setStartTime(time);
        hardware.intake.setStartTime(time);
        hardware.outtake.setStartTime(time);
    }



}
