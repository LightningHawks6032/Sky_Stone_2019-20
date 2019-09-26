package main.java.org.firstinspires.ftc.teamcode.FieldMapping;

import java.util.HashMap;
import java.util.Map;

public class FieldMap {
    //creates a collection of vector values correlating with important locations on the map

    private Map<FieldElement, Vector> map = new HashMap<>();

    public final double FULL_FIELD_LENGTH = 144;
    public final double QUADRANT_LENGTH = FULL_FIELD_LENGTH/2;
    public final double SQUARE_LENGTH = FULL_FIELD_LENGTH/6;
    public final double HALF_SQUARE_LENGTH = SQUARE_LENGTH/2;
    public final double MID_QUADRANT = QUADRANT_LENGTH/2;


    // Constructor generates the map
    public FieldMap() {
        generate();
    }

    private void generate() {

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

