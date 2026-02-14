package net.greenjab.nekomasfixed.util;

public class ModColors {
    // Creating new Mod colors, new colors can be added later...
    public static final ModColors AMBER = new ModColors("amber", 0xE0AF0B);
    public static final ModColors AQUA = new ModColors("aqua", 0xE0AF0B);

    private final String name; // Instance field
    private final int color;   // Instance field

    // Constructor
    public ModColors(String name, int color){
        this.name = name;
        this.color = color;
    }

    public  String getName(){
        return name;
    }

    public  int getColor(){
        return color;
    }
}
