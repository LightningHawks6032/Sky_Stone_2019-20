package org.firstinspires.ftc.teamcode.BeltBot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldElement;
import org.firstinspires.ftc.teamcode.FieldMapping.FieldMap;
import org.firstinspires.ftc.teamcode.FieldMapping.Vector;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Vision.DogeCVDetectorMethods;


public class Auto {
    private LinearOpMode autonomous;
    private BeltBot_Hardware hardware;
    private DogeCVDetectorMethods dogeCV;
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
        dogeCV.setStartTime(time);
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
        hardware.intake.clampersUp();

        int targetY = (int) fieldMap.SQUARE_LENGTH;


        double distance = (Math.abs(hardware.drivetrain.robotPos.getY()) - targetY);
        int directionS = 0;

        if(quadrant == 1) directionS = -1; else directionS = 1;

        //utilized to get in place
        hardware.drivetrain.strafeDistance(directionS, fieldMap.HALF_SQUARE_LENGTH, 0.5);

        hardware.drivetrain.driveDistance(1, distance, 0.3);
        //Vector pos = new Vector (hardware.drivetrain.robotPos.getX(), hardware.drivetrain.robotPos.getY());
        //hardware.drivetrain.goToLerp(new Vector(hardware.drivetrain.robotPos.getX(), fieldMap.get(FieldElement.BOUNDATION_GRAB_POINT).getY()), 0.5);

        hardware.intake.clampersDown();
        //jimmies forward
        hardware.drivetrain.driveDistance(1, 0.5, 0.7);
        //

        hardware.drivetrain.turn(90, quadrant != 1);
        hardware.drivetrain.strafeDistance(directionS, distance, 0.8);
        hardware.intake.clampersUp();



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

    public void grabFirstStone(int stone, int alliance) throws InterruptedException{
        //Int stone: 0 = Left, 1 = center, 2 = right

        int buffer = 5;
        int strafeAwayDist = 5;
        int nudgeDist = 4;
        /* Plan of action: approach the stone (or air) to the side of the skystone that is facing
         * the bridges, knock it out of place, strafe away from the skystone, turn towards the
         * skystone, engage the intake flippers, and intake it
         */

        //absolute value of distance needed to travel to get into place
        double strafeDist = buffer + Math.abs(hardware.drivetrain.robotPos.getX())+fieldMap.HALF_ROBOT_WIDTH-fieldMap.SQUARE_LENGTH;
        int strafeDirection = -1;
        int innerStone = 0;

        //initializes the direction of the target strafe/stone based off of the alliance (right if red)
        if(alliance == AutonomousData.RED_ALLIANCE){
            strafeDirection = 1;
            innerStone = 2;
        }

        //adjustment to move to the proper stone
        if(stone == innerStone){
            //no movement
        }else if(stone == 1){
            strafeDist += fieldMap.STONE_WIDTH;
        }else{
            strafeDist += 2*fieldMap.STONE_WIDTH;
        }

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
        }

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

}

