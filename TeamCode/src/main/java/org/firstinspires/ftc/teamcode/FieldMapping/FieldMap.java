package org.firstinspires.ftc.teamcode.FieldMapping;

import java.util.HashMap;
import java.util.Map;

public class FieldMap {
    //creates a collection of vector values correlating with important locations on the map

    private Map<FieldElement, Vector> map = new HashMap<FieldElement, Vector>();

    public final double FULL_FIELD_LENGTH = 144;
    public final double QUADRANT_LENGTH = FULL_FIELD_LENGTH/2;
    public final double SQUARE_LENGTH = FULL_FIELD_LENGTH/6;
    public final double HALF_SQUARE_LENGTH = SQUARE_LENGTH/2;
    public final double MID_QUADRANT = QUADRANT_LENGTH/2;

    public final double ROBOT_LENGTH = 17.5;
    public final double HALF_ROBOT_LENGTH = ROBOT_LENGTH/2;
    public final double ROBOT_WIDTH = 16;
    public final double HALF_ROBOT_WIDTH = ROBOT_WIDTH/2;

    public final double STONE_WIDTH = SQUARE_LENGTH/3;

    public double pos2X = (SQUARE_LENGTH*2-HALF_ROBOT_WIDTH);
    public double pos2Y = 72-HALF_ROBOT_LENGTH;

    // Constructor generates the map
    public FieldMap() {
        generate();
    }

    private void generate() {
        //Quad 1 (+, +)
        add(FieldElement.BITE, new Vector (5*HALF_SQUARE_LENGTH, 5*HALF_SQUARE_LENGTH));

        add(FieldElement.BPOS2, new Vector(pos2X, pos2Y ));

        add(FieldElement.BOUNDATION_GRAB_POINT, new Vector(MID_QUADRANT, SQUARE_LENGTH));

        add(FieldElement.IMAGE8, new Vector(QUADRANT_LENGTH, MID_QUADRANT));
        add(FieldElement.IMAGE6, new Vector(MID_QUADRANT, QUADRANT_LENGTH));

        //Quad 2 (-, +)
        add(FieldElement.REPOT, new Vector (-5*HALF_SQUARE_LENGTH, 5*HALF_SQUARE_LENGTH));

        add(FieldElement.BPOS1, new Vector(-MID_QUADRANT, (72-(HALF_ROBOT_LENGTH) )));

        add(FieldElement.IMAGE2, new Vector(-QUADRANT_LENGTH, MID_QUADRANT));
        add(FieldElement.IMAGE4, new Vector(-MID_QUADRANT, QUADRANT_LENGTH));

        //Quad 3 (-, -)
        add(FieldElement.BEPOT, new Vector(-5*HALF_SQUARE_LENGTH, -5*HALF_SQUARE_LENGTH));

        add(FieldElement.RPOS1, new Vector(-MID_QUADRANT, -(72-(HALF_ROBOT_LENGTH) )));

        add(FieldElement.IMAGE1, new Vector(-QUADRANT_LENGTH, -MID_QUADRANT));
        add(FieldElement.IMAGE3, new Vector(-MID_QUADRANT, -QUADRANT_LENGTH));

        //Quad 4 (+, -)
        add(FieldElement.RITE, new Vector (5*HALF_SQUARE_LENGTH, -5*HALF_SQUARE_LENGTH));

        add(FieldElement.RPOS2, new Vector(pos2X, -pos2Y ));

        add(FieldElement.ROUNDATION_GRAB_POINT, new Vector(MID_QUADRANT, -SQUARE_LENGTH));

        add(FieldElement.IMAGE7, new Vector(QUADRANT_LENGTH, -MID_QUADRANT));
        add(FieldElement.IMAGE5, new Vector(MID_QUADRANT, -QUADRANT_LENGTH));

        //Between quads

            //red
        add(FieldElement.RINNERPARK, new Vector (0, -(72-(HALF_SQUARE_LENGTH+SQUARE_LENGTH) ) ) );
        add(FieldElement.ROUTERPARK, new Vector(0, -(72-(HALF_SQUARE_LENGTH) ) ) );

            //blue
        add(FieldElement.BINNERPARK, new Vector (0, (72-(HALF_SQUARE_LENGTH+SQUARE_LENGTH))));
        add(FieldElement.BOUTERPARK, new Vector(0, (72-(HALF_SQUARE_LENGTH) ) ) );

            //neutral
        add(FieldElement.NINNERPARK, new Vector(0, 0));
    }

    // Accesses the position of a specific element based on quadrant and name
    public Vector get(FieldElement element) {
        return map.get(element);
    }

    // Adds an element to the field map with quadrant, name, and vector position
    private void add(FieldElement element, Vector position) {
        map.put(element, position);
    }
}

