package org.firstinspires.ftc.teamcode.BeltBot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldElement;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldMap;
import org.firstinspires.ftc.teamcode.FieldMapping.Vector;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Vision.DogeCVDetectorMethods;

import java.util.logging.Handler;


public class Auto {
    private LinearOpMode autonomous;
    private BeltBot_Hardware hardware;
    public DogeCVDetectorMethods dogeCV;
    public FieldMap fieldMap = new FieldMap();
    private long startTime;
    public Vector startImage;

    public Auto(LinearOpMode auto, BeltBot_Hardware hardware){
        autonomous = auto;
        this.hardware = hardware;
        hardware.drivetrain.setAuto(auto);
        hardware.intake.setAuto(auto);
        hardware.outtake.setAuto(auto);
        dogeCV = hardware.dogeCV;
    }

    public void setStartTime(long time) {
        startTime = time;
        hardware.drivetrain.setStartTime(time);
        hardware.intake.setStartTime(time);
        hardware.outtake.setStartTime(time);
        dogeCV.setStartTime(time);
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


        hardware.detector.setupTracker();
    }

    //for simplicity of code, this method assumes that the robot is facing forwards in regards to the wall
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

        hardware.intake.clampersDown();
        //jimmies forward
        hardware.drivetrain.driveDistance(1, 0.5, 0.7);
        //jimmies back
        hardware.drivetrain.driveDistance(-1, fieldMap.SQUARE_LENGTH*1.1, 0.6);

        hardware.drivetrain.turn(85, quadrant != 1);
        hardware.intake.clampersUp();
        hardware.drivetrain.driveDistance(1, 10, 0.7);
        if(!inner) hardware.drivetrain.strafeDistance(directionS, distance, 0.8);




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


    // @Param angleToTurnRight: angle to turn to face foundation, negative if left
    private void driveToParkFromStone (int angleToTurnRight , double fromX, double fromY, boolean inner, int alliance) throws InterruptedException{
        Vector target;

        if(alliance == AutonomousData.RED_ALLIANCE){
            if(inner) target = fieldMap.get(FieldElement.RINNERPARK);
            else target = fieldMap.get(FieldElement.ROUTERPARK);
        }else{
            if(inner) target = fieldMap.get(FieldElement.BINNERPARK);
            else target = fieldMap.get(FieldElement.BOUTERPARK);
        }

        boolean right = true;
        int turnAngle = angleToTurnRight;
        if(turnAngle <0) {
            right = false;
            turnAngle = Math.abs(turnAngle);
        }
        int strafe = 1;
        if(target.getY() > fromY) strafe = -1;

        hardware.drivetrain.turn(turnAngle, right);
        hardware.drivetrain.strafeDistance(strafe, target.getY() - fromY, 0.6);
        hardware.drivetrain.driveDistance(1, Math.abs(target.getX()-fromX), 0.6);
    }

    // *includes moving to nudging position and parking afterwards
    public void nudgeFoundation(int quadrant, boolean toInner) throws InterruptedException{
        //assumes init pos as pos after foundation move, facing the respective park
        int strafeDirection = 1;
        double nudgeDist = fieldMap.SQUARE_LENGTH*.6;
        if (quadrant == 1) strafeDirection = -1;
        hardware.drivetrain.driveDistance(1, fieldMap.SQUARE_LENGTH*1.5, 0.3);

        //hardware.drivetrain.strafeDistance(strafeDirection, fieldMap.SQUARE_LENGTH*1.5, 0.5);

        hardware.drivetrain.turn(90, quadrant == 4);
        hardware.drivetrain.driveDistance(1, fieldMap.SQUARE_LENGTH*1.1, 0.5);
        hardware.drivetrain.turn(87, (quadrant == 4));

        hardware.intake.clampersUp();
        hardware.drivetrain.driveDistance(1, nudgeDist, 0.3);

        //New: grabbing and rotating foundation
        hardware.intake.clampersDown();
        hardware.drivetrain.turn(45, quadrant == 4);
        hardware.intake.clampersUp();
        hardware.drivetrain.turn(55, !(quadrant == 4));

        hardware.drivetrain.driveDistance(-1, nudgeDist, 0.3);
        hardware.outtake.horizontalSlidePowers(-1);
        if(toInner){
            hardware.drivetrain.driveDistance(-1, 7, 0.5);
        }else{
            if(quadrant == 1) {
                hardware.drivetrain.strafeDistance(-1, fieldMap.SQUARE_LENGTH*1.5, 0.5);
            }else{
                hardware.drivetrain.strafeDistance(1, fieldMap.SQUARE_LENGTH*1.5, 0.5);
            }
            hardware.drivetrain.driveDistance(-1, 7, 0.5);
        }
        Thread.sleep(3500);
        hardware.outtake.horizontalSlidePowers(0);
    }

    public void grabFirstStonePark(int stone, int alliance, boolean inner) throws InterruptedException{
        //Int stone: 0 = Left, 1 = center, 2 = right
        double x = -1, y = alliance-2;
        int toTurn = -(alliance-2);
        int buffer = 5;
        int strafeAwayDist = 5;
        int nudgeDist = 4;
        /* Plan of action: approach the stone to the side of the skystone that is facing
         * the bridges, knock it out of place, strafe away from the skystone, turn towards the
         * skystone, engage the intake flippers, and intake it
         *   -If the chosen is the one closest to the center of the field, then approach it at a
         *    45 degree angle and intake it from there
         * park (or at least get into a common area) and deposit the stone afterwards
         */

        //absolute value of distance needed to travel to get into place
        //considering that the starting x is in the middle of the middle square
        double strafeDist = buffer + Math.abs(hardware.drivetrain.robotPos.getX())+fieldMap.HALF_ROBOT_WIDTH-fieldMap.SQUARE_LENGTH;
        int strafeDirection = -1;
        int innerStone = 0;

        //initializes the direction of the target strafe/stone based off of the alliance (right if red)
        if(alliance == AutonomousData.RED_ALLIANCE){
            strafeDirection = 1;
            innerStone = 2;
        }

        //adjustment to move to the proper stone
        /*
        if(stone == innerStone){
            //no movement
        }else if(stone == 1){
            strafeDist += fieldMap.STONE_WIDTH;
        }else{
            strafeDist += 2*fieldMap.STONE_WIDTH;
        }
        */


        //strafes appropriate distance and direction
        hardware.drivetrain.strafeDistance(strafeDirection, strafeDist, 0.6);

        //intake procedure

        if(stone == innerStone){
            //grab from edge of stone formation, different procedure
            //positioning:
            hardware.drivetrain.driveDistance(1, (1.5*fieldMap.SQUARE_LENGTH-fieldMap.HALF_ROBOT_LENGTH), 0.6);
            hardware.drivetrain.turn(45, strafeDirection == -1);
            hardware.intake.clampersDown();
            //hardware.intake.clampersUp();
            hardware.intake.intakePowers(1);
            hardware.drivetrain.driveDistance(1, fieldMap.SQUARE_LENGTH*2, 0.4);
            hardware.intake.intakePowers(0);
            hardware.drivetrain.driveDistance(-1, fieldMap.SQUARE_LENGTH*2, 0.4);
            y *= (1.5*fieldMap.SQUARE_LENGTH);
            x *= fieldMap.get(FieldElement.BPOS1).getX()+(strafeDist);
            toTurn *= 135;
        }else{
            //moves appropriate distance and direction (with nudge)
            hardware.drivetrain.driveDistance(1, (2*fieldMap.SQUARE_LENGTH-fieldMap.HALF_ROBOT_LENGTH) + nudgeDist, 0.6);
            //grabbing stone
            hardware.drivetrain.strafeDistance(strafeDirection, strafeAwayDist, 0.6);
            hardware.drivetrain.turn(90, strafeDirection == -1);
            hardware.intake.clampersDown();
            //hardware.intake.clampersUp();
            hardware.intake.intakePowers(1);
            hardware.drivetrain.driveDistance(1, strafeAwayDist+fieldMap.STONE_WIDTH+(2*buffer), 0.4);
            hardware.intake.intakePowers(0);
            hardware.drivetrain.driveDistance(-1, strafeAwayDist+fieldMap.STONE_WIDTH+(2*buffer), 0.4);
            x *= fieldMap.get(FieldElement.BPOS1).getX()+(strafeDist+strafeAwayDist);
            y *= 2*fieldMap.SQUARE_LENGTH;
            toTurn *= 180;
        }
        //driveToParkFromStone(toTurn, x, y, inner, alliance);
    }

    public void rotateFoundation(int quadrant) throws InterruptedException{
        hardware.intake.clampersDown();
        hardware.drivetrain.turn(90, (quadrant == 4));
        hardware.intake.clampersUp();
    }

    public void updatePosFromNav(){
        hardware.detector.lookForTargets();
        hardware.drivetrain.setRobotPos(hardware.detector.getRobotPosition());
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
    public int vuforiaStone(int alliance) throws InterruptedException{

        int stoneNum = 2;
        int direction = -1;
        if(alliance == AutonomousData.RED_ALLIANCE){
            direction = 1;
        }


        hardware.detector.lookForTargets();

        /*
         * Precondition: robot camera is centered on left stone (closest to skybridge)
         * Tests left, goes right (strafes left). If center isn't a stone, assumes right
         * Strafes right (goes left), strafes again if stone is right
        */
        if(alliance == AutonomousData.BLUE_ALLIANCE) {
            //Detection
            Thread.sleep(500);
            if (hardware.detector.stoneVisible()) {
                stoneNum = 0;
            } else {
                hardware.drivetrain.strafeDistanceCorrectAngle(direction, fieldMap.STONE_WIDTH, 0.7);
                Thread.sleep(500);
                if (hardware.detector.stoneVisible()) {
                    stoneNum = 1;
                }
            }

            // Debug: pretend you saw a stone
            //stoneNum = 1;

            //Positioning
            if (stoneNum == 1) {
                hardware.drivetrain.strafeDistanceCorrectAngle(1, 0.5 * fieldMap.STONE_WIDTH, 0.5);
            } else if (stoneNum == 0) {
                hardware.drivetrain.strafeDistanceCorrectAngle(1, 1.5 * fieldMap.STONE_WIDTH, 0.5);
            }
            else{
                hardware.drivetrain.strafeDistanceCorrectAngle(-1, 0.5*fieldMap.STONE_WIDTH, 0.5);
            }


       /*
        * Precondition: robot camera is centered on center stone
        * Tests center, goes left (strafes right). If left isn't a stone, assumes right
        * Strafes left (goes right),
        */
        }else{ //Red
            //Detection
            Thread.sleep(500);
            if(hardware.detector.stoneVisible()){
                stoneNum = 1;
            }else{
                hardware.drivetrain.strafeDistance(direction, fieldMap.STONE_WIDTH, 0.5);
            }
        }


        /*
        hardware.detector.setupTracker();
        hardware.detector.lookForTargets();
        if(hardware.detector.stoneDetected){
            stoneNum = 1;
        }else{
            hardware.drivetrain.strafeDistance(direction, fieldMap.STONE_WIDTH, 0.4);
            Thread.sleep(2000);
            if(hardware.detector.stoneDetected) stoneNum = 1-direction;
        }



        double strafeDist = fieldMap.STONE_WIDTH;
        int strafeDirect = 1;
        if (stoneNum == 2 && alliance == AutonomousData.RED_ALLIANCE){
            strafeDirect = -1;
        }else if (stoneNum == 0 && alliance == AutonomousData.BLUE_ALLIANCE){
            strafeDist += 2*fieldMap.STONE_WIDTH;
        }
        hardware.drivetrain.strafeDistance(strafeDirect, strafeDist, 0.4);
*/

        int targetY = (int) fieldMap.SQUARE_LENGTH;
        double distance = (Math.abs(targetY - Math.abs(hardware.drivetrain.robotPos.getY()))-1.5*fieldMap.STONE_WIDTH);
        hardware.drivetrain.driveDistance(-1, distance, 0.5);

        hardware.intake.grabStone();
        Thread.sleep(500);

        hardware.drivetrain.driveDistance(-1, 0.5*fieldMap.STONE_WIDTH, 0.5);
        hardware.intake.grabStone();

        hardware.drivetrain.driveDistance(1, 1.3*fieldMap.STONE_WIDTH, 0.5);

        hardware.drivetrain.turn(90, alliance == AutonomousData.RED_ALLIANCE);
        hardware.drivetrain.driveDistance(-1, fieldMap.SQUARE_LENGTH, 0.6);
        hardware.intake.ungrabStone();

        return stoneNum;
    }

}

