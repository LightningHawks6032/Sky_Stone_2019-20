package org.firstinspires.ftc.teamcode.WeekendBot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FieldMapping.FieldElement;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldMap;
import org.firstinspires.ftc.teamcode.FieldMapping.Vector;
import org.firstinspires.ftc.teamcode.Hardware.WeekendBot.WeekendBot_Hardware;


public class Auto {
    private LinearOpMode autonomous;
    private WeekendBot_Hardware hardware;
    public FieldMap fieldMap = new FieldMap();
    private long startTime;
    public Vector startImage;

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


    //sets the start position of the robot dependent
    public void setStartPosition(int quadrant) throws InterruptedException{

        Vector startPos;

        switch (quadrant) {
            case 1:
                startPos = fieldMap.get(FieldElement.BPOS2);
                hardware.drivetrain.setRobotPos(startPos);
                startImage = fieldMap.get(FieldElement.IMAGE6);
                break;
            case 2:
                startPos = fieldMap.get(FieldElement.BPOS1);
                hardware.drivetrain.setRobotPos(startPos);
                startImage = fieldMap.get(FieldElement.IMAGE4);
                break;
            case 3:
                startPos = fieldMap.get(FieldElement.RPOS1);
                hardware.drivetrain.setRobotPos(startPos);
                startImage = fieldMap.get(FieldElement.IMAGE3);
                break;
            case 4:
                startPos = fieldMap.get(FieldElement.RPOS2);
                hardware.drivetrain.setRobotPos(startPos);
                startImage = fieldMap.get(FieldElement.IMAGE5);
                break;
        }

    }

    //for simplicity of code, this method assumes that the robot is facing a nav target
    //facing: own (closest) wall by default; in method with facing as parameter:
    //                      1: closest wall 2: left (if forward is facing the wall) 3: farther wall 4: right
    public int startAngle(int quadrant){
        if(quadrant == 1 || quadrant == 2){
            return 90;
        }
        return 270;
    }

    public int startAngle(int quadrant, int facing) {
        if(facing == 1) {
            if (quadrant == 1 || quadrant == 2) {
                return 90;
            }
            return 270;
        }else if (facing == 3){
            if (quadrant == 1 || quadrant == 2){
                return 270;
            }
            return 90;
        }else if (facing == 2){
            if (quadrant == 1 || quadrant == 2){
                return 180;
            }
            return 0;
        }
        if (quadrant == 1 || quadrant == 2){
            return 0;
        }
        return 180;

    }



}
