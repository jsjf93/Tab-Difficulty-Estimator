/*
 * A class that represents a list of all the input Pieces
 */
package tabdifficultyanalyser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Joshua Foster
 */
public class TabDatabase {
    private ArrayList<Tab> tabDatabase;
    
    /**
     * Default constructor for a TabDatabase
     */
    public TabDatabase(){
        tabDatabase = new ArrayList<>();
    }
    
    /**
     * A method that adds a Tab object to the TabDatabase
     * @param tab
     * @return 
     */
    public boolean add(Tab tab){
        return tabDatabase.add(tab);
    }
    
    /**
     * A method that returns a tab from a specified place in the database
     * @param position
     * @return 
     */
    public Tab getTab(int position){
        if(position > getSize() || position < 0){
            System.out.println("Out of bounds");
            return null;
        }
        else{
            return tabDatabase.get(position);
        }
    }
    
    /**
     * Print the contents of a Tab at the specified place in the database
     * @param position 
     */
    public void printPiece(int position){
        if(position > getSize() || position < 0){
            System.out.println("Out of bounds");
        }
        else{
            System.out.println(tabDatabase.get(position));
        }
    }
    
    /**
     * Return the size of the TabDatabase
     * @return 
     */
    public int getSize(){
        return tabDatabase.size();
    }
    
    /**
     * Reads a file path and records each tab file as a Tab along with the grade
     * @param rootFolder
     * @throws Exception 
     */
    public void readInTabDatabase(String rootFolder) throws Exception{
        // Create a list of all the pathways for each piece
        final ArrayList<String> pathList = walkDirTree(rootFolder);
        for(String path : pathList){
            tabDatabase.add(new Tab(tabReader(path), getGrade(path)));
        }
    }
    
    /**
     * A method that returns an ArrayList of Strings containing musical 
     * instances
     * @param file
     * @return 
     */
    private ArrayList<String> tabReader(String file){
        String line = null;
        ArrayList<String> instances = new ArrayList<>();
        System.out.println(file);
        
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine()) != null){
                if(!line.isEmpty()){
                    instances.add(line);
                }
            }
            br.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Unable to open " + file);
        }
        catch(IOException e){
            System.out.println("Unable to read file " + file);
        }
        return instances;
    }
    
    /**
     * A method for retrieving the path for every file in the subfolders
     * of the "pieces" folder (for example, "grade1", "grade2",..., "grade8"
     * @param rootFolder
     * @throws Exception
     * @return ArrayList<String> - ArrayList of pathways
     */
    private static ArrayList<String> walkDirTree(String rootFolder) throws Exception{
        ArrayList<String> pathList = new ArrayList<>();
        Files.walk(Paths.get(rootFolder)).forEach(path ->{
            if(path.toString().contains(".tab")){
                pathList.add(path.toString());
            }
        });
        return pathList;
    }
    
    /**
     * A method that extracts the grade of a piece from the given file path
     * @param path
     * @return grade of a piece as an int
     */
    private static int getGrade(String path){
        int pos = 0;
        while(path.charAt(pos) != 'g'){
            pos++;
        }
        pos+=5;
        return path.charAt(pos)-'0';
    }
}
