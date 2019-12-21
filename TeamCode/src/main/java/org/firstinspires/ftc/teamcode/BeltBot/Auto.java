package org.firstinspires.ftc.teamcode.BeltBot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FieldMapping.FieldElement;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldMap;
import org.firstinspires.ftc.teamcode.FieldMapping.Vector;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;


public class Auto {
    private LinearOpMode autonomous;
    private BeltBot_Hardware hardware;
    public FieldMap fieldMap = new FieldMap();
    private long startTime;
    public Vector startImage;

    public Auto(LinearOpMode auto, BeltBot_Hardware hardware){
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
    public void setStartAngle(int quadrant){
        if(quadrant == 1 || quadrant == 2){
            hardware.drivetrain.setInitialRobotAngle(270);
        }else {
            hardware.drivetrain.setInitialRobotAngle(90);
        }
    }

    public void setStartAngle(int quadrant, int facing) {
        if(facing == 1) {
            if (quadrant == 1 || quadrant == 2) {
                hardware.drivetrain.setInitialRobotAngle(90);
            }else {
                hardware.drivetrain.setInitialRobotAngle(270);
            }
        }else if (facing == 3){
            if (quadrant == 1 || quadrant == 2){
                hardware.drivetrain.setInitialRobotAngle(270);
            }else {
                hardware.drivetrain.setInitialRobotAngle(90);
            }
        }else if (facing == 2){
            if (quadrant == 1 || quadrant == 2){
                hardware.drivetrain.setInitialRobotAngle(180);
            }else {
                hardware.drivetrain.setInitialRobotAngle(0);
            }
        }else{
            if (quadrant == 1 || quadrant == 2){
                hardware.drivetrain.setInitialRobotAngle(0);
            }else{
                hardware.drivetrain.setInitialRobotAngle(180);
            }
        }

    }

    public void getFoundation (int quadrant) throws InterruptedException{
        int targetY = 24;


        double distance = (Math.abs(hardware.drivetrain.robotPos.getY()) - targetY);

        hardware.drivetrain.driveDistance(1, distance, 0.5);
        hardware.intake.clampersDown();
        hardware.drivetrain.driveDistance(-1, distance, 0.2);
    }

    public void getFoundationStrafe (int quadrant) throws InterruptedException{
        int directionMult = -1;
        int targetY = 24;


        if (quadrant == 1){
            directionMult = 1;
        }

        double distance = (Math.abs(hardware.drivetrain.robotPos.getY()) - targetY);

        hardware.drivetrain.strafeDistance(directionMult, distance, 0.5);
        hardware.intake.clampersDown();
        hardware.drivetrain.strafeDistance(directionMult*-1, distance, 0.2);
    }

    public void moveToPark (boolean inner, int quadrant) throws InterruptedException{
        int direction;
        double distance;
        if(quadrant == 1 || quadrant == 3) direction = 1;
        else direction = -1;

        distance = Math.abs(hardware.drivetrain.robotPos.getX());
        hardware.drivetrain.strafeDistance(direction, distance, 0.5);

        if(inner) hardware.drivetrain.driveDistance(1, fieldMap.SQUARE_LENGTH, 0.5);
    }

    public void updatePosFromNav(){
        hardware.detector.lookForTargets();
        hardware.drivetrain.setRobotPos(hardware.detector.getRobotPosition());
    }

}

