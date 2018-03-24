/*
 * A class that analyses each piece of lute tablature and records its 
 * attributes.
 * Once it has analysed a particular component of the tablature it then calls
 * the appropriate method to print to .arff
 */
package tabdifficultyanalyser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Joshua Foster
 */
public class FeatureExtractor {
    private static final String[] CHROMATIC_SCALE = {"a","a#","b","c",
                                                    "c#","d","d#","e",
                                                    "f","f#","g","g#"};
    // noteCount is an array of size 12. noteCount[0] represents the note A
    // noteCount[1] represents A# and noteCount[11] represents G#
    private final int[] noteCount; // note order; A,A#,B,C,C#,D,D#,E,F,F#,G,G#
    private int highestFret;
    private final int[][] fretCount;
    private int chordCount;
    private int totalNoteCount; // the total number of notes played in a piece
    //private String[] arff; 
    private int[] rhythmFlagCount; // 2W, W, 1/2, 1/4, 1/8, 1/16, 1/32, 1/64, 1/128, triplet
    // A instance of ArffUtility used to create and write arffs
    private final ArffUtility arffUtility;
    
    /**
     * Default constructor for a FeatureExtractor object
     */
    public FeatureExtractor(){
        noteCount = new int[12];
        fretCount = new int[10][16]; // note: may need to adjust for extra courses
                                    // or frets if more Tabs are added to db
        chordCount = 0;
        totalNoteCount = 0;
        rhythmFlagCount = new int[10];
        arffUtility = new ArffUtility();
    }
    
    private void resetNoteCount(){
        for(int i = 0; i < noteCount.length; i++){
            noteCount[i] = 0;
        }
    }
    
    private void resetFretCount(){
        for(int i = 0; i < fretCount.length; i++){
            for(int j = 0; j < fretCount[0].length; j++){
                fretCount[i][j] = 0;
            }
        }
    }
    
    private void resetRhythmFlagCount(){
        for(int i = 0; i < rhythmFlagCount.length; i++){
            rhythmFlagCount[i] = 0;
        }
    }
    
    /**
     * A simple method that takes an instance of a TabDatabase and analyses
     * the note counts for each piece of lute tablature. The counts are stored
     * in noteCount.
     * It is a simple counter for the prototype as it simply checks which notes 
     * are used in a piece and their frequency. It passes the notes to the
     * private void method checkNote().
     * It also constructs a second arff file called totalNoteCount. This is
     * the total number of note played in a piece
     * @param tabDatabase 
     */
    public void noteCount(TabDatabase tabDatabase){
        // Sets up the arff file headers
        //prepareNoteCountArff();
        arffUtility.prepareNoteCountArff();
        
        //prepareTotalNoteCountArff();
        arffUtility.prepareTotalNoteCountArff();
        
        // Go through each Tab in the TabDatabase
        for(int i = 0; i < tabDatabase.getSize(); i++){
            // Scan through each line of the Tab
            for(String instance : tabDatabase.getTab(i).getInstances()){
                // Checks that the line starts with a rhythm flag
                if(isRhythmFlag(instance)){
                    int course = 1; // course refers to pair of strings
                    for(int j = 1; j < instance.length(); j++){
                        if(instance.charAt(j)==' ' || instance.charAt(j)=='/'){
                            course++;
                        }
                        else if(isValidNote(instance, j) && course <= 10){
                            int pos = checkNote(instance.charAt(j), course);
                            noteCount[pos]++;
                            course++;
                        }
                    }
                }
            }
            //noteCountToArff(tabDatabase.getTab(i).getGrade());
            arffUtility.noteCountToArff(noteCount, tabDatabase.getTab(i).getGrade());
            //totalNoteCountToArff(tabDatabase.getTab(i).getGrade());
            arffUtility.totalNoteCountToArff(noteCount, totalNoteCount, 
                    tabDatabase.getTab(i).getGrade());
            resetNoteCount();
        }
    }
    
    private void prepareNoteCountArff(){
        String fileName = "noteCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation noteCount");
            bw.newLine();
            bw.newLine();
            bw.write("@attribute a numeric");
            bw.newLine();
            bw.write("@attribute a# numeric");
            bw.newLine();
            bw.write("@attribute b numeric");
            bw.newLine();
            bw.write("@attribute c numeric");
            bw.newLine();
            bw.write("@attribute c# numeric");
            bw.newLine();
            bw.write("@attribute d numeric");
            bw.newLine();
            bw.write("@attribute d# numeric");
            bw.newLine();
            bw.write("@attribute e numeric");
            bw.newLine();
            bw.write("@attribute f numeric");
            bw.newLine();
            bw.write("@attribute f# numeric");
            bw.newLine();
            bw.write("@attribute g numeric");
            bw.newLine();
            bw.write("@attribute g# numeric");
            bw.newLine();
            bw.write("@attribute grade {1,2,3,4,5,6,7,8}");
            bw.newLine();
            bw.newLine();
            bw.write("@data");
            bw.newLine();
            
            bw.close();
        }
        catch(IOException e){
            System.out.println("e");
        }
    }
    
    private void noteCountToArff(int grade){
        String fileName = "noteCount.arff";
        
        try{
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < noteCount.length; i++){
                bw.write(noteCount[i]+",");
            }
            bw.write(new Integer(grade).toString());
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println("e");
        }
    } 
    
    /**
     * A method that checks the given fret and course so it can return the 
     * CHROMATIC_SCALE variable array position of the note being played.
     * @param c
     * @param course 
     * @return note position
     */
    private static int checkNote(char c, int course){
        c = Character.toLowerCase(c);
        int asciiValue = (int)c;
        
        int fret = asciiValue-97; // note that fret 'a' refers to open string, 
                                  // not first fret which is why -97 is used                       
        String open; // used to indicate the open course note
        int i = 0;   // used to indicate the open course note position in the
                     // constant CHROMATIC_SCALE String array
        switch(course){
            case 1:
                open = "g";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 2:
                open = "d";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 3:
                open = "a";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 4:
                open = "f";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 5:
                open = "c";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 6:
                open = "g";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 7:
                open = "f";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 8:
                open = "d#";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 9:
                open = "d";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            case 10:
                open = "c";
                while(!open.equals(CHROMATIC_SCALE[i])) i++;
                break;
            default:
                System.out.println("There was a problem");
                break;
        }
        return (i + fret) % 12; // note position in the CHROMATIC_SCALE array
                                // plus the fret. Modulus 12 (number of notes
                                // in the chromatic scale) to give the note
                                // played in the CHROMATIC_SCALE
    }
    
    
    public void highestFret(TabDatabase tabDatabase){
        arffUtility.prepareHighestFretArff();
        
        for(int i = 0; i < tabDatabase.getSize(); i++){
            // Go through each line (or instance) of the Tab
            for(String instance : tabDatabase.getTab(i).getInstances()){
                // Checks that the line starts with a rhythm flag
                if(isRhythmFlag(instance)){
                    for(int j = 1; j < instance.length(); j++){
                        if(isValidNote(instance, j)){
                            char c = Character.toLowerCase(instance.charAt(j));
                            int asciiValue = (int)c;
                            int fret = asciiValue-97;
                            if(fret > highestFret){
                                highestFret = fret;
                            }
                        }
                    }
                }
            }
            arffUtility.highestFretToArff(highestFret, tabDatabase.getTab(i).getGrade());
            highestFret = 0;
        }
    }
    
    
    public void noteCountAndHighestFret(TabDatabase tabDatabase){
        // Sets up the arff file headers
        arffUtility.prepareNoteCountHighestFretArff();
        
        // Go through each Tab in the TabDatabase
        for(int i = 0; i < tabDatabase.getSize(); i++){
            // Scan through each line of the Tab
            for(String instance : tabDatabase.getTab(i).getInstances()){
                // Checks that the line starts with a rhythm flag
                if(isRhythmFlag(instance)){  
                    int course = 1; // course refers to pair of strings
                    for(int j = 1; j < instance.length(); j++){
                        if(instance.charAt(j) == ' ' || 
                                instance.charAt(j) == '/'){
                            course++;
                        }
                        else if(isValidNote(instance, j) &&
                                course <= 10){
                            int pos = checkNote(instance.charAt(j), course);
                            noteCount[pos]++;
                            course++;
                            
                            // Check highest fret
                            char c = Character.toLowerCase(instance.charAt(j));
                            int asciiValue = (int)c;
                            int fret = asciiValue-97;
                            if(fret > highestFret){
                                highestFret = fret;
                            }
                        }
                    }
                }
            }
            arffUtility.noteCountHighestFretToArff(noteCount, highestFret, 
                    tabDatabase.getTab(i).getGrade());
            resetNoteCount();
            highestFret = 0;
        }
    }
    
    
    public void fretCount(TabDatabase tabDatabase){
        // Sets up the arff file headers
        arffUtility.prepareFretCountArff(fretCount.length, fretCount[0].length);
        
        // Go through each Tab in the TabDatabase
        for(int i = 0; i < tabDatabase.getSize(); i++){
            // Scan through each line of the Tab
            for(String instance : tabDatabase.getTab(i).getInstances()){
                // Checks that the line starts with a rhythm flag
                if(isRhythmFlag(instance)){  
                    int course = 1; // course refers to pair of strings
                    for(int j = 1; j < instance.length(); j++){
                        // Checks course
                        if(instance.charAt(j) == ' ' || 
                                instance.charAt(j) == '/'){
                            course++;
                        }
                        // Checks for letter indicating fret
                        else if(isValidNote(instance, j) &&
                                course <= 10){
                            char c = Character.toLowerCase(instance.charAt(j));
                            int asciiValue = (int)c;
                            int fret = asciiValue - 97;
                            fretCount[course-1][fret]++;
                            course++;
                        }
                    }
                }
            }
            arffUtility.fretCountToArff(fretCount, tabDatabase.getTab(i).getGrade());
            resetFretCount();
        }
    }
    
    public void chordCount(TabDatabase tabDatabase){
        arffUtility.prepareChordCountArff();
        
        for(int i = 0; i < tabDatabase.getSize(); i++){
            // Go through each line (or instance) of the Tab
            for(String instance : tabDatabase.getTab(i).getInstances()){
                // Checks that the line starts with a rhythm flag
                if(isRhythmFlag(instance)){
                    int numNotesInInstance = 0;
                    for(int j = 1; j < instance.length(); j++){
                        if(isValidNote(instance, j)){
                            numNotesInInstance++;
                        }
                    }
                    if(numNotesInInstance >= 3){
                        chordCount++;
                    }
                }
            }
            arffUtility.chordCountToArff(chordCount, tabDatabase.getTab(i).getGrade());
            chordCount = 0;
        }
    }
    
    public void rhythmFlagCount(TabDatabase tabDatabase){
        // Sets up the arff file headers
        arffUtility.prepareRhythmFlagCountArff();
        
        // Go through each Tab in the TabDatabase
        for(int i = 0; i < tabDatabase.getSize(); i++){
            // previousFlag used to store the previous note if it was given 'x'
            // which indicates grouping
            char previousFlag = ' ';
            // Scan through each line of the Tab
            for(String instance : tabDatabase.getTab(i).getInstances()){
                // Checks that the line starts with a rhythm flag
                if(isRhythmFlag(instance)){  
                    if(Character.isDigit(instance.charAt(0))){
                        checkRhythmFlag(instance.charAt(0));
                        previousFlag = instance.charAt(0);
                    }
                    // currently a problem with a few inputs here as previousFlag
                    // is considered as ' ' 
                    else if(instance.charAt(0) == 'x'){
                        checkRhythmFlag(previousFlag);
                    }
                    else if(instance.charAt(0) == '#' &&
                            Character.isDigit(instance.charAt(1))){
                        checkRhythmFlag(instance.charAt(1));
                        previousFlag = instance.charAt(1);
                    }
                }
            }
            arffUtility.rhythmFlagCountToArff(rhythmFlagCount, tabDatabase.getTab(i).getGrade());
            resetRhythmFlagCount();
        }
    }
    
    private boolean checkRhythmFlag(char c){
        
        switch(c){
            case 'W':
                rhythmFlagCount[0]++;
                break;
            case 'w':
                rhythmFlagCount[1]++;
                break;
            case '0':
                rhythmFlagCount[2]++;
                break;
            case '1':
                rhythmFlagCount[3]++;
                break;
            case '2':
                rhythmFlagCount[4]++;
                break;
            case '3':
                rhythmFlagCount[5]++;
                break;
            case '4':
                rhythmFlagCount[6]++;
                break;
            case '5':
                rhythmFlagCount[7]++;
                break;
            case '6':
                rhythmFlagCount[8]++;
                break;
            case 't':
                rhythmFlagCount[9]++;
                break;
            default:
                System.out.println("Invalid input: " + c);
                return false;
        }
        return true;
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
}