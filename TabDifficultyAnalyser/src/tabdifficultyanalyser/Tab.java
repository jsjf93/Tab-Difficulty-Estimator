/*
 * A class to represent an individual lute piece
 */
package tabdifficultyanalyser;

import java.util.ArrayList;

/**
 *
 * @author Joshua Foster
 */
public class Tab {
    private final ArrayList<String> instances;
    private int grade;
    
    public Tab(){
        instances = new ArrayList<>();
    }
    
    /**
     * Constructor that adds the instances of a piece into a Tab object
     * @param instances 
     */
    public Tab(ArrayList<String> instances){
        this.instances = new ArrayList<>();
        for(String s : instances){
            this.instances.add(s);
        }
    }
    
    /**
     * A constructor for a Tab that takes a list of instances and an int, grade
     * @param instances
     * @param grade 
     */
    public Tab(ArrayList<String> instances, int grade){
        this.instances = new ArrayList<>();
        for(String s : instances){
            this.instances.add(s);
        }
        this.grade = grade;
    }
    
    /**
     * Gets the list of instances in a Tab
     * @return instances
     */
    public ArrayList<String> getInstances(){
        return instances;
    }
    
    /**
     * Adds an instance to the list of instances
     * @param instance a musical instance
     * @return boolean
     */
    public boolean addInstance(String instance){
        return instances.add(instance);
    }
    
    /**
     * Return the grade of a piece
     * @return grade
     */
    public int getGrade(){
        return grade;
    }
    
    /**
     * Returns the number of notes played in a Tab
     * @return count
     */
    public int getNumberOfNotes(){
        int count = 0;
        for(String instance : instances){
            // Check that instance starts with a rhythm flag
            if(isRhythmFlag(instance)){
                // Loop through each course (pair of lute strings)
                for(int j = 1; j < instance.length(); j++){
                    // Check that current note is a valid note
                    if(isValidNote(instance, j)){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    /**
     * Static method that checks that a given line (instance) begins with a
     * valid rhythm flag
     * @param instance
     * @return boolean
     */
    private static boolean isRhythmFlag(String instance){
        return Character.isDigit(instance.charAt(0)) || 
                instance.charAt(0) == 'x' || 
                instance.charAt(0) == '#' ||
                instance.charAt(0) == 'Y' ||
                instance.charAt(0) == 'y';
    }
    
    /**
     * Static method that checks if a given character in an instance is a valid
     * note
     * @param instance the current line in a Tab
     * @param j the position that the note is being played (course)
     * @return boolean
     */
    private static boolean isValidNote(String instance, int j){
        return Character.isLetter(instance.charAt(j)) &&
                instance.charAt(j) != 'X' &&
                instance.charAt(j) != 'U' &&
                instance.charAt(j) != 'x' &&
                instance.charAt(j) != 't';
    }
    
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Grade: ");
        sb.append(getGrade()).append("\n");
        for(String s : instances){
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
