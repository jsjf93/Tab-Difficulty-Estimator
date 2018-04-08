/*
 * The main class that initiates the program.
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
        // Print out a tab to check that it has been read correctly
        //db.printPiece(0);
        
        FeatureExtractor ft = new FeatureExtractor();
        ft.noteCount(db);
        ft.highestFret(db);
        ft.noteCountAndHighestFret(db);
        ft.fretCount(db);
        ft.chordCount(db);
        ft.rhythmFlagCount(db);
        ft.advancedFretCount(db);
        ft.totalNoteCount(db);
    }
    
}
