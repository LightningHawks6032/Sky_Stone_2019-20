package org.firstinspires.ftc.teamcode.BeltBot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldElement;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldMap;
import org.firstinspires.ftc.teamcode.FieldMapping.Vector;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Vision.DogeCVDetectorMethods;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

import java.util.logging.Handler;


public class Auto {
    private LinearOpMode autonomous;
    private BeltBot_Hardware hardware;
    //public DogeCVDetectorMethods dogeCV;
    public FieldMap fieldMap = new FieldMap();
    private long startTime;
    public Vector startImage;

    public Auto(LinearOpMode auto, BeltBot_Hardware hardware){
        autonomous = auto;
        this.hardware = hardware;
        hardware.drivetrain.setAuto(auto);
        hardware.intake.setAuto(auto);
        hardware.outtake.setAuto(auto);
        //dogeCV = hardware.dogeCV;
    }

    public void setStartTime(long time) {
        startTime = time;
        hardware.drivetrain.setStartTime(time);
        hardware.intake.setStartTime(time);
        hardware.outtake.setStartTime(time);
        //dogeCV.setStartTime(time);
    }

    //Precondition: edge of the robot is on the edge of the field closer to the park (robot is 1 square from park)
    public void depotSidePark(boolean inner, int alliance) throws InterruptedException{
        if(inner) hardware.drivetrain.driveDistance(1, fieldMap.SQUARE_LENGTH, 0.6);
        hardware.drivetrain.turn(90, alliance == AutonomousData.RED_ALLIANCE);
        hardware.drivetrain.driveDistance(1, fieldMap.SQUARE_LENGTH, 0.6);
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

    //for simplicity of code, this method assumes that the robot is facing forwards in regards to the wall
    //facing: own (closest) wall by default; in method with facing as parameter:
    //                      1: closest wall 2: left (if forward is facing the wall) 3: farther wall 4: right
    public void setStartAngle(int quadrant){
        /*
        if(quadrant == 1 || quadrant == 2){
            hardware.drivetrain.setInitialRobotAngle(270);
        }else {
            hardware.drivetrain.setInitialRobotAngle(90);
        }
        */

        hardware.drivetrain.setInitialRobotAngle(0);
        hardware.drivetrain.setInitialIMUHeading();
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

    public void getFoundation (int quadrant, boolean inner) throws InterruptedException{
        hardware.intake.clampersUp();

        int targetY = (int) fieldMap.SQUARE_LENGTH;


        double distance = (Math.abs(hardware.drivetrain.robotPos.getY()) - targetY);
        int directionS = 0;

        if(quadrant == 1) directionS = -1; else directionS = 1;

        //utilized to get in place
        hardware.drivetrain.strafeDistance(directionS, fieldMap.HALF_SQUARE_LENGTH*0.75, 0.5);

        hardware.drivetrain.driveDistance(1, distance, 0.3);
        //Vector pos = new Vector (hardware.drivetrain.robotPos.getX(), hardware.drivetrain.robotPos.getY());
        //hardware.drivetrain.goToLerp(new Vector(hardware.drivetrain.robotPos.getX(), fieldMap.get(FieldElement.BOUNDATION_GRAB_POINT).getY()), 0.5);


        /*
        hardware.intake.clampersDown();
        //jimmies forward
        hardware.drivetrain.driveDistance(1, 0.5, 0.7);
        //jimmies back
        hardware.drivetrain.driveDistance(-1, fieldMap.SQUARE_LENGTH*1.1, 0.6);

        hardware.drivetrain.turn(85, quadrant != 1);
        hardware.intake.clampersUp();
        hardware.drivetrain.driveDistance(1, 10, 0.7);
        if(!inner) hardware.drivetrain.strafeDistance(directionS, distance, 0.8);
        */

        secondHalfOfFoundation(inner, quadrant);



        /*

        //Old code that had the robot back up with the foundation

        hardware.drivetrain.driveDistance(-1, distance-4, 0.2);
        //hardware.drivetrain.goToBackwards(pos, 0.2);

        hardware.intake.clampersUp();

        hardware.drivetrain.driveDistance(-1, 4, 0.3);

        //hardware.drivetrain.strafeDistance(-directionS, fieldMap.HALF_SQUARE_LENGTH, 0.5);


        //hardware.drivetrain.driveDistance(1,fieldMap.SQUARE_LENGTH, 0.5);

        */
    }

    public void secondHalfOfFoundation(boolean inner, int quadrant) throws InterruptedException{
        int targetY = (int) fieldMap.SQUARE_LENGTH;
        double distance = (Math.abs(hardware.drivetrain.robotPos.getY()) - targetY);
        int directionS = 0;

        if(quadrant == 1) directionS = -1; else directionS = 1;

        hardware.intake.clampersDown();
        //jimmies forward
        hardware.drivetrain.driveDistance(1, 0.5, 0.6);
        //jimmies back
        hardware.drivetrain.driveDistance(-1, fieldMap.SQUARE_LENGTH*1.1, 0.6);

        hardware.drivetrain.turn(85, (quadrant != 1) && (quadrant != 2));
        hardware.intake.clampersUp();
        hardware.drivetrain.driveDistance(1, 10, 0.7);
        if(!inner) hardware.drivetrain.strafeDistance(directionS, distance, 0.8);
    }

    public void getFoundationFromStone (boolean inner ,int quadrant) throws InterruptedException{
        if(quadrant == 1|| quadrant ==2) {
            hardware.drivetrain.driveDistance(1, 0.75 * fieldMap.SQUARE_LENGTH, 0.6);
        }else{
            hardware.drivetrain.driveDistance(1, 1 * fieldMap.SQUARE_LENGTH, 0.6);
        }

        hardware.drivetrain.turnToAngle(180);
        //hardware.intake.ungrabStone();
        hardware.drivetrain.turnToAngle(180);

        hardware.drivetrain.lerpDriveDistance(1, 0.5*fieldMap.SQUARE_LENGTH, 0.4);
        secondHalfOfFoundation(inner, quadrant);
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


    // @Param angleToTurnRight: angle to turn to face foundation, negative if left
    public void driveToParkFromStone (boolean inner, int alliance) throws InterruptedException{
        hardware.drivetrain.turn(180, true);
        if(alliance == AutonomousData.BLUE_ALLIANCE){
            hardware.drivetrain.turnToAngle(270);
        }else{
            hardware.drivetrain.turnToAngle(90);
        }
        hardware.intake.ungrabStone();
        hardware.drivetrain.driveDistance(1, 0.50*fieldMap.SQUARE_LENGTH, 0.5);
        int direction = -1;
        if((inner && alliance == AutonomousData.RED_ALLIANCE) || (!inner && alliance == AutonomousData.BLUE_ALLIANCE)){
            direction = 1;
        }
        hardware.drivetrain.strafeDistance(direction, fieldMap.STONE_WIDTH*2, 0.6);
    }

    public void updatePosFromNav(GeneralDetector detector){
        detector.lookForTargets();
        hardware.drivetrain.setRobotPos(detector.getRobotPosition());
    }

    public void rest() throws InterruptedException{
        Thread.sleep(0*1000);
    }

    public boolean autoRunning() {
        return System.currentTimeMillis() - startTime <= AutonomousData.TIME_LIMIT && !autonomous.isStopRequested();
    }

    //Precondition: starting w/ camera centered on center stone (RED), or left stone (BLUE); detector is inited
    //Return: 0 (left), 1 (center), or 2 (right)
    //Alliance: red = 1, blue = 2; will check away from skybridge first
    public int vuforiaStone(int alliance, GeneralDetector detector) throws InterruptedException{

        double strafeMod = 1.5;
        int stoneNum = 2;
        int direction = -1;
        double distToBridge = fieldMap.HALF_ROBOT_WIDTH+fieldMap.SQUARE_LENGTH; //Distance to under the bridge from the center of the robot
        if(alliance == AutonomousData.RED_ALLIANCE){
            direction = 1;
            distToBridge = (5/3)*fieldMap.SQUARE_LENGTH-fieldMap.HALF_ROBOT_WIDTH;
        }



        detector.lookForTargets();

        /*
         * Precondition: robot camera is centered on left stone (closest to skybridge)
         * Tests left, goes right (strafes left). If center isn't a stone, assumes right
         * Strafes right (goes left), strafes again if stone is right
        */
        if(alliance == AutonomousData.BLUE_ALLIANCE) {
            //Detection
            Thread.sleep(1000);
            if (detector.stoneVisible()) {
                stoneNum = 0;
            } else {
                hardware.drivetrain.strafeDistanceCorrectAngle(direction, strafeMod*fieldMap.STONE_WIDTH, 0.5);
                distToBridge += fieldMap.STONE_WIDTH;
                Thread.sleep(1000);
                detector.lookForTargets();
                if (detector.stoneVisible()) {
                    stoneNum = 1;
                }else{
                    stoneNum = 2;
                }
            }

            // Debug: pretend you saw a stone
            //stoneNum = 1;

            //Positioning

            if (stoneNum == 1) {
                hardware.drivetrain.strafeDistanceCorrectAngle(1, 1.35*fieldMap.clawPhoneDist, 0.6);
                distToBridge -= 1.5*fieldMap.STONE_WIDTH;
            } else if (stoneNum == 0) {
                hardware.drivetrain.strafeDistanceCorrectAngle(1, 1.45*fieldMap.clawPhoneDist, 0.5);
                distToBridge -= 1.5*fieldMap.STONE_WIDTH;
            }
            else{
                hardware.drivetrain.strafeDistanceCorrectAngle(1, 0.4*fieldMap.STONE_WIDTH, 0.5);
                distToBridge -= 0.5*fieldMap.STONE_WIDTH;
            }


       /*
        * Precondition: robot camera is centered on center stone
        * Tests center, goes left (strafes right). If left isn't a stone, assumes right
        * Strafes left (goes right) depending on stone pos
        */
        }else{ //Red
            //Detection
            Thread.sleep(1000);
            if(detector.stoneVisible()){
                stoneNum = 1;
            }else{
                hardware.drivetrain.strafeDistanceCorrectAngle(direction, strafeMod* fieldMap.STONE_WIDTH, 0.5);
                distToBridge += fieldMap.STONE_WIDTH;
                Thread.sleep(1000);
                detector.lookForTargets();
                if(detector.stoneVisible()){
                    stoneNum = 0;
                }
            }

            //Positioning
            if(stoneNum == 0){
                hardware.drivetrain.strafeDistanceCorrectAngle(1, 1.4*fieldMap.clawPhoneDist, 0.5);
            }else if(stoneNum == 1){
                hardware.drivetrain.strafeDistanceCorrectAngle(1, 1.5*fieldMap.clawPhoneDist, 0.5);
            }else{
                hardware.drivetrain.strafeDistanceCorrectAngle(-1, 0.65*fieldMap.clawPhoneDist, 0.5);
            }
        }

        //                  |  ||
        //                  || |_

        //hardware.drivetrain.turnToAngle(0);
        int targetY = (int) fieldMap.SQUARE_LENGTH;
        double distance = (Math.abs(targetY - Math.abs(hardware.drivetrain.robotPos.getY()))-1*fieldMap.STONE_WIDTH);
        hardware.drivetrain.lerpDriveDistance(-1, distance, 0.5);
        hardware.drivetrain.turnToAngle(0);


        hardware.intake.grabStone();
        Thread.sleep(500);

        hardware.drivetrain.driveDistance(-1, 0.7*fieldMap.STONE_WIDTH, 0.5);

        //hardware.drivetrain.strafeDistance(direction,2,0.3);
        hardware.intake.grabStone();
        //hardware.drivetrain.strafeDistance(-direction,2,0.3);



        hardware.drivetrain.driveDistance(1, 1.85*fieldMap.STONE_WIDTH, 0.5);

        hardware.drivetrain.turn(85, alliance != AutonomousData.RED_ALLIANCE);

        if(alliance == AutonomousData.BLUE_ALLIANCE){
            hardware.drivetrain.turnToAngle(90);
        }else{
            hardware.drivetrain.turnToAngle(270);
        }

        hardware.drivetrain.driveDistance(1, distToBridge+1.3*fieldMap.SQUARE_LENGTH, 0.6);
        hardware.intake.clampersUp();
        //hardware.intake.ungrabStone();


        return stoneNum;
    }

}

