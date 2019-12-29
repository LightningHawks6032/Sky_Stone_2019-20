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

        int targetY = (int) fieldMap.SQUARE_LENGTH;


        double distance = (Math.abs(hardware.drivetrain.robotPos.getY()) - targetY);
        int directionS = 0;

        if(quadrant == 1) directionS = -1; else directionS = 1;

        //utilized to get in place
        hardware.drivetrain.strafeDistance(directionS, fieldMap.HALF_SQUARE_LENGTH, 0.5);
        //post-strafe angle correction
        hardware.drivetrain.turn((int)hardware.drivetrain.robotAngle, (hardware.drivetrain.robotAngle<180));

        hardware.drivetrain.driveDistance(1, distance, 0.3);
        //Vector pos = new Vector (hardware.drivetrain.robotPos.getX(), hardware.drivetrain.robotPos.getY());
        //hardware.drivetrain.goToLerp(new Vector(hardware.drivetrain.robotPos.getX(), fieldMap.get(FieldElement.BOUNDATION_GRAB_POINT).getY()), 0.5);

        hardware.intake.clampersDown();
        //jimmies forward
        hardware.drivetrain.driveDistance(1, 0.5, 0.7);
        //

        hardware.drivetrain.driveDistance(-1, distance+3, 0.2);
        //hardware.drivetrain.goToBackwards(pos, 0.2);

        hardware.intake.clampersUp();

        //hardware.drivetrain.strafeDistance(-directionS, fieldMap.HALF_SQUARE_LENGTH, 0.5);


        //hardware.drivetrain.driveDistance(1,fieldMap.SQUARE_LENGTH, 0.5);
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

    public void strafeToPark (boolean inner, int quadrant) throws InterruptedException{
        int direction;
        double distance;
        if(quadrant == 1 || quadrant == 3) direction = 1;
        else direction = -1;

        //distance = Math.abs(hardware.drivetrain.robotPos.getX());
        distance = fieldMap.SQUARE_LENGTH*2;
        hardware.drivetrain.strafeDistance(direction, distance, 0.5);

        if(inner) hardware.drivetrain.driveDistance(1, fieldMap.SQUARE_LENGTH, 0.5);
    }

    public void driveToPark (boolean inner, boolean fromInner, int quadrant) throws InterruptedException{
        double distance = Math.abs(hardware.drivetrain.robotPos.getX());
        hardware.drivetrain.driveDistance(1, distance, 0.5);
        double absY = Math.abs(hardware.drivetrain.robotPos.getY());
        double targetY = 0;
        if(inner) targetY = fieldMap.get(FieldElement.BINNERPARK).getY();
        else targetY = fieldMap.get(FieldElement.BOUTERPARK).getY();
        if(!((inner && fromInner) ||(!inner && !fromInner))) {
            if (quadrant == 1 || quadrant == 3)
                hardware.drivetrain.strafeDistance(1, absY - targetY, 0.5);
            else hardware.drivetrain.strafeDistance(-1, absY - targetY, 0.5);
        }
    }

    public void updatePosFromNav(){
        hardware.detector.lookForTargets();
        hardware.drivetrain.setRobotPos(hardware.detector.getRobotPosition());
    }

}

