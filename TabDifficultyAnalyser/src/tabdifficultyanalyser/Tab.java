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
    //private String name;
    // Temporary version just stores notes as strings
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
    
    public Tab(ArrayList<String> instances, int grade){
        this.instances = new ArrayList<>();
        for(String s : instances){
            this.instances.add(s);
        }
        this.grade = grade;
    }
    
    public ArrayList<String> getInstances(){
        return instances;
    }
    
    public boolean addInstance(String instance){
        return instances.add(instance);
    }
    
    public int getGrade(){
        return grade;
    }
    
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
     * @param instance
     * @param course
     * @param j
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
