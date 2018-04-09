/*
 * The main class that initiates the program.
 * It creates an instance of a TabDatabase after being passed the root folder 
 * for the graded pieces and then creates an instance of the FeatureExtractor
 * and creates each of the ARFFs ready to be used by weka.
 */
package tabdifficultyanalyser;

/**
 *
 * @author Joshua Foster
 */
public class Main{
    public static void main(String[] args) throws Exception {
        // The root folder for each of the graded piece subfolders
        final String rootFolder = "pieces";
        // Create a new db object for storing tab objects
        TabDatabase db = new TabDatabase();
        // Read all the tabs into the database
        db.readInTabDatabase(rootFolder);
        // Print out the size of the database
        System.out.println(db.getSize());
        // Create an instance of the FeatureExtractor
        FeatureExtractor ft = new FeatureExtractor();
        // Create the ARFFs
        ft.noteCount(db);
        ft.highestFret(db);
        ft.noteCountAndHighestFret(db);
        ft.fretCount(db);
        ft.chordCount(db);
        ft.rhythmFlagCount(db);
        ft.advancedFretCount(db);
        ft.totalNoteCount(db);
        ft.numberOfBars(db);
        ft.combined(db);
    } 
}
