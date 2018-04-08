/*
 * A class that analyses each piece of lute tablature and records its 
 * attributes.
 * Once it has analysed a particular component of the tablature it then calls
 * the appropriate method to print to .arff
 */
package tabdifficultyanalyser;


/**
 *
 * @author Joshua Foster
 */
public class FeatureExtractor {
    private final int courses = 10;
    private final int frets = 16;
    // A instance of ArffUtility used to create and write arffs
    private final ArffUtility arffUtility;
    
    /**
     * Default constructor for a FeatureExtractor object
     */
    public FeatureExtractor(){
        arffUtility = new ArffUtility();
    }
    
    /**
     * A simple method that takes an Tab of a TabDatabase and analyses
     * the note counts. It then writes the results to an ARFF file
     * @param tabDatabase 
     */
    public void noteCount(TabDatabase tabDatabase){
        int[] noteCount; // note order; A,A#,B,C,C#,D,D#,E,F,F#,G,G#
        // Sets up the arff file headers
        arffUtility.prepareNoteCountArff();
        // Go through each Tab in the TabDatabase
        for(int i = 0; i < tabDatabase.getSize(); i++){
            noteCount = tabDatabase.getTab(i).getNoteCount();
            arffUtility.noteCountToArff(noteCount, tabDatabase.getTab(i).getGrade());
        }
    }
    
    /**
     * Finds the highest note played in a Tab and writes to an arff
     * @param tabDatabase the database of tabs
     */
    public void highestFret(TabDatabase tabDatabase){
        int highestFret;
        arffUtility.prepareHighestFretArff();
        
        for(int i = 0; i < tabDatabase.getSize(); i++){
            highestFret = tabDatabase.getTab(i).getHighestFret();
            arffUtility.highestFretToArff(highestFret, tabDatabase.getTab(i).getGrade());
            highestFret = 0;
        }
    }
    
    /**
     * Finds the noteCount and highest fret and combines theses in an arff
     * @param tabDatabase the Tab database
     */
    public void noteCountAndHighestFret(TabDatabase tabDatabase){
        int[] noteCount;
        int highestFret;
        // Sets up the arff file headers
        arffUtility.prepareNoteCountHighestFretArff();
        
        // Go through each Tab in the TabDatabase
        for(int i = 0; i < tabDatabase.getSize(); i++){
            noteCount = tabDatabase.getTab(i).getNoteCount();
            highestFret = tabDatabase.getTab(i).getHighestFret();
            arffUtility.noteCountHighestFretToArff(noteCount, highestFret, 
                    tabDatabase.getTab(i).getGrade());
        }
    }
    
    /**
     * Finds the count of notes played on each fret of the lute and records
     * in fretCount
     * @param tabDatabase the Tab database
     */
    public void fretCount(TabDatabase tabDatabase){
        int[][] fretCount;
        // Sets up the arff file headers
        arffUtility.prepareFretCountArff(courses, frets);
        
        // Go through each Tab in the TabDatabase
        for(int i = 0; i < tabDatabase.getSize(); i++){
            fretCount = tabDatabase.getTab(i).getFretCount();
            arffUtility.fretCountToArff(fretCount, tabDatabase.getTab(i).getGrade());
        }
    }
    
    /**
     * Finds the number of instances in each Tab that contain more than 3 notes
     * and writes to an arff
     * @param tabDatabase 
     */
    public void chordCount(TabDatabase tabDatabase){
        int chordCount;
        arffUtility.prepareChordCountArff();
        
        for(int i = 0; i < tabDatabase.getSize(); i++){
            chordCount = tabDatabase.getTab(i).getChordCount();
            arffUtility.chordCountToArff(chordCount, tabDatabase.getTab(i).getGrade());
        }
    }
    
    /**
     * *******This still needs looking at*********
     * @param tabDatabase 
     */
    public void rhythmFlagCount(TabDatabase tabDatabase){
        int[] rhythmFlagCount;
        // Sets up the arff file headers
        arffUtility.prepareRhythmFlagCountArff();
        
        // Go through each Tab in the TabDatabase
        for(int i = 0; i < tabDatabase.getSize(); i++){
            rhythmFlagCount = tabDatabase.getTab(i).getRhythmFlagCount();
            arffUtility.rhythmFlagCountToArff(rhythmFlagCount, tabDatabase.getTab(i).getGrade());
        }
    }
        
    
    /**
     * A method that seeks the bar of a Tab that features the highest number of
     * notes. It then takes the count that each fret is played.
     * @param tabDatabase 
     */
    public void advancedFretCount(TabDatabase tabDatabase){
        int[][] advancedFretCount;
        arffUtility.prepareAdvancedFretCountArff(courses, frets);
        
        // Find the most bar with the most notes
        for(int i = 0; i < tabDatabase.getSize(); i++){
            advancedFretCount = tabDatabase.getTab(i).getAdvancedFretCount();
            arffUtility.advancedFretCountToArff(advancedFretCount, tabDatabase.getTab(i).getGrade());
        }
    }
    
    /**
     * Writes an arff containing the total number of notes played in each Tab
     * @param tabDatabase 
     */
    public void totalNoteCount(TabDatabase tabDatabase){
        int totalNoteCount;
        arffUtility.prepareTotalNoteCountArff();
        
        for(int i = 0; i < tabDatabase.getSize(); i++){
            totalNoteCount = tabDatabase.getTab(i).getTotalNoteCount();
            arffUtility.totalNoteCountToArff(totalNoteCount, tabDatabase.getTab(i).getGrade());
        }
    }
}