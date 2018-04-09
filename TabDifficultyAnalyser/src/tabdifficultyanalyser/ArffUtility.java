/*
 * A class that produces and writes data in ARFF format.
 */
package tabdifficultyanalyser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Joshua Foster
 */
public class ArffUtility {
    private static final String[] CHROMATIC_SCALE = {"a","a#","b","c",
                                                    "c#","d","d#","e",
                                                    "f","f#","g","g#"};
    
    /**
     * A method that creates and prepares the ARFF for noteCount.
     */
    public void prepareNoteCountArff(){
        String fileName = "noteCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation noteCount");
            bw.newLine();
            bw.newLine();
            for (String s : CHROMATIC_SCALE) {
                bw.write("@attribute " + s + " numeric");
                bw.newLine();
            }
            
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that takes a Tab files noteCount and grade, and then
     * writes the data to the ARFF.
     * @param noteCount
     * @param grade 
     */
    public void noteCountToArff(int[] noteCount, int grade){
        String fileName = "noteCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < noteCount.length; i++){
                bw.write(noteCount[i]+",");
            }
            bw.write(Integer.toString(grade));
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    } 
    
    /**
     * A method that prepares the ARFF for the highest fret used in a
     * Tab.
     */
    public void prepareHighestFretArff(){
        String fileName = "highestFret.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation highestFret");
            bw.newLine();
            bw.newLine();
            bw.write("@attribute highestFret numeric");
            bw.newLine();
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }   
        
    /**
     * A method that writes the highestFret data of a Tab to the ARFF
     * @param highestFret
     * @param grade 
     */
    public void highestFretToArff(int highestFret, int grade){
        String fileName = "highestFret.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(highestFret+",");
            bw.write(Integer.toString(grade));
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method for producing an ARFF that is a combination of
     * noteCount and highestFret
     */
    public void prepareNoteCountHighestFretArff(){
        String fileName = "noteCountHighestFret.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation noteCountHighestFret");
            bw.newLine();
            bw.newLine();
            
            for (String s : CHROMATIC_SCALE) {
                bw.write("@attribute " + s + " numeric");
                bw.newLine();
            }
            bw.write("@attribute highestFret numeric");
            bw.newLine();
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that writes both the noteCount and highestFret in one ARFF
     * @param noteCount
     * @param highestFret
     * @param grade 
     */
    public void noteCountHighestFretToArff(int[] noteCount, int highestFret, int grade){
        String fileName = "noteCountHighestFret.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < noteCount.length; i++){
                bw.write(noteCount[i]+",");
            }
            bw.write(highestFret+",");
            bw.write(new Integer(grade).toString());
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
        
    }
    
    /**
     * A method that takes two parameters, m and n and creates an ARFF for
     * fretCount.
     * @param m - the number of rows in fretCount
     * @param n - the number of columns in fretCount
     */
    public void prepareFretCountArff(int m, int n){
        String fileName = "fretCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation fretCount");
            bw.newLine();
            bw.newLine();
            for(int i = 1; i <= m; i++){
                for(int j = 0; j < n; j++){
                    bw.write("@attribute [" + i + "][" + j + "] numeric");
                    bw.newLine();
                }
            }
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that writes the fretCount data to an ARFF
     * @param fretCount
     * @param grade 
     */
    public void fretCountToArff(int[][] fretCount, int grade){
        String fileName = "fretCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < fretCount.length; i++){
                for(int j = 0; j < fretCount[0].length; j++){
                    bw.write(fretCount[i][j]+",");
                }
            }
            bw.write(Integer.toString(grade));
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that creates an ARFF for chordCount
     */
    public void prepareChordCountArff(){
        String fileName = "chordCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation chordCount");
            bw.newLine();
            bw.newLine();
            bw.write("@attribute chordCount numeric");
            bw.newLine();
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that writes the chordCount data to an ARFF
     * @param chordCount
     * @param grade 
     */
    public void chordCountToArff(int chordCount, int grade){
        String fileName = "chordCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(chordCount + ",");
            bw.write(Integer.toString(grade));
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that prepares the totalNoteCount ARFF
     */
    public void prepareTotalNoteCountArff(){
        String fileName = "totalNoteCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation totalNoteCount");
            bw.newLine();
            bw.newLine();
            bw.write("@attribute totalNoteCount numeric");
            bw.newLine();
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that writes the totalNoteCount data to an ARFF
     * @param totalNoteCount
     * @param grade 
     */
    public void totalNoteCountToArff(int totalNoteCount, int grade){
        String fileName = "totalNoteCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(totalNoteCount + ",");
            bw.write(new Integer(grade).toString());
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that creates the rhythmFlagCount ARFF
     */
    public void prepareRhythmFlagCountArff(){
        String fileName = "rhythmFlagCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation rhythmFlagCount");
            bw.newLine();
            bw.newLine();
            bw.write("@attribute 2W numeric");
            bw.newLine();
            bw.write("@attribute W numeric");
            bw.newLine();
            bw.write("@attribute 1/2 numeric");
            bw.newLine();
            bw.write("@attribute 1/4 numeric");
            bw.newLine();
            bw.write("@attribute 1/8 numeric");
            bw.newLine();
            bw.write("@attribute 1/16 numeric");
            bw.newLine();
            bw.write("@attribute 1/32 numeric");
            bw.newLine();
            bw.write("@attribute 1/64 numeric");
            bw.newLine();
            bw.write("@attribute 1/128 numeric");
            bw.newLine();
            bw.write("@attribute triplet numeric");
            bw.newLine();
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that writes the rhythmFlagCount data to an ARFF
     * @param rhythmFlagCount
     * @param grade 
     */
    public void rhythmFlagCountToArff(int[] rhythmFlagCount, int grade){
        String fileName = "rhythmFlagCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < rhythmFlagCount.length; i++){
                bw.write(rhythmFlagCount[i]+",");
            }
            bw.write(Integer.toString(grade));
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    } 
    
    /**
     * A method that takes two parameters, m and n and creates an ARFF for
     * fretCount.
     * @param m - the number of rows in fretCount
     * @param n - the number of columns in fretCount
     */
    public void prepareAdvancedFretCountArff(int m, int n){
        String fileName = "advancedFretCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation advancedFretCount");
            bw.newLine();
            bw.newLine();
            for(int i = 1; i <= m; i++){
                for(int j = 0; j < n; j++){
                    bw.write("@attribute [" + i + "][" + j + "] numeric");
                    bw.newLine();
                }
            }
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * A method that writes the fretCount data to an ARFF
     * @param advancedFretCount
     * @param grade 
     */
    public void advancedFretCountToArff(int[][] advancedFretCount, int grade){
        String fileName = "advancedFretCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < advancedFretCount.length; i++){
                for(int j = 0; j < advancedFretCount[0].length; j++){
                    bw.write(advancedFretCount[i][j]+",");
                }
            }
            bw.write(Integer.toString(grade));
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * Creates an arff file for numberOfBars
     */
    public void prepareNumberOfBarsArff(){
        String fileName = "numberOfBars.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation numberOfBars");
            bw.newLine();
            bw.newLine();bw.write("@attribute bars numeric");
            bw.newLine();
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    /**
     * Writes the data from numberOfBars to numberOfBars.arff
     * @param numberOfBars
     * @param grade 
     */
    public void numberOfBarsToArff(int numberOfBars, int grade){
        String fileName = "numberOfBars.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(numberOfBars + ",");
            bw.write(new Integer(grade).toString());
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    
    public void prepareCombinedArff(int m, int n){
        String fileName = "combined.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation combined");
            bw.newLine();
            bw.newLine();
            for(int i = 1; i <= m; i++){
                for(int j = 0; j < n; j++){
                    bw.write("@attribute [" + i + "][" + j + "] numeric");
                    bw.newLine();
                }
            }
            bw.newLine();
            bw.write("@attribute chordCount numeric");
            bw.newLine();
            bw.write("@attribute totalNoteCount numeric");
            bw.newLine();
            bw.write("@attribute bars numeric");
            bw.newLine();
            bw.write("@attribute highestFret numeric");
            bw.newLine();
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    public void combinedToArff(int[][] fretCount, int chordCount, 
            int totalNoteCount, int numberOfBars, int highestFret, int grade){
        String fileName = "combined.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < fretCount.length; i++){
                for(int j = 0; j < fretCount[0].length; j++){
                    bw.write(fretCount[i][j] + ",");
                }
            }
            bw.write(chordCount + ",");
            bw.write(totalNoteCount + ",");
            bw.write(numberOfBars + ",");
            bw.write(highestFret + ",");
            bw.write(Integer.toString(grade));
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
