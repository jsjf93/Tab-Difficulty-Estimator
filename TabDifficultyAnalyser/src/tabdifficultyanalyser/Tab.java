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
