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
    
    
    private static final String[] CHROMATIC_SCALE = {"a","a#","b","c",
                                                    "c#","d","d#","e",
                                                    "f","f#","g","g#"};
    
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
     * A simple method that takes an instance of a TabDatabase and analyses
     * the note counts for each piece of lute tablature. The counts are stored
     * in noteCount.
     * It is a simple counter for the prototype as it simply checks which notes 
     * are used in a piece and their frequency. It passes the notes to the
     * private void method checkNote().
     */
    public int[] getNoteCount(){
        // note order; A,A#,B,C,C#,D,D#,E,F,F#,G,G#
        int[] noteCount = new int[12]; 
        // Scan through each line of the Tab
        for(String instance : instances){
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
        return noteCount;
    }
    
    /**
     * Finds the highest note played in a Tab and writes to an arff
     * @param tabDatabase the database of tabs
     */
    public int getHighestFret(){
        int highestFret = 0;
        // Go through each line (or instance) of the Tab
        for(String instance : instances){
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
        return highestFret;
    }
    
    /**
     * Finds the count of notes played on each fret of the lute and records
     * in fretCount
     * @return fretCount
     */
    public int[][] getFretCount(){
        int[][] fretCount = new int[10][16];
        // Scan through each line of the Tab
        for(String instance : instances){
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
                    else if(isValidNote(instance, j) && course <= 10){
                        char c = Character.toLowerCase(instance.charAt(j));
                        int asciiValue = (int)c;
                        int fret = asciiValue - 97;
                        fretCount[course-1][fret]++;
                        course++;
                    }
                }
            }
        }
        return fretCount;
    }
    
    /**
     * Finds the number of instances in each Tab that contain more than 3 notes
     * and writes to an arff
     * @return chordCount
     */
    public int getChordCount(){
        int chordCount = 0;
        // Go through each line (or instance) of the Tab
        for(String instance : instances){
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
        return chordCount;
    }
    
    /**
     * *******This still needs looking at*********
     * @return rhythmFlagCount
     */
    public int[] getRhythmFlagCount(){
        int[] rhythmFlagCount = new int[10];
        // previousFlag used to check flag for 'x' which indicates grouping
        char previousFlag = ' ';
        // Scan through each line of the Tab
        for(String instance : instances){
            // Checks that the line starts with a rhythm flag
            if(isRhythmFlag(instance)){  
                if(Character.isDigit(instance.charAt(0))){
                    checkRhythmFlag(rhythmFlagCount, instance.charAt(0));
                    previousFlag = instance.charAt(0);
                }
                // currently a problem with a few inputs here as previousFlag
                // is considered as ' ' very occasionally 
                else if(instance.charAt(0) == 'x'){
                    checkRhythmFlag(rhythmFlagCount, previousFlag);
                }
                else if(instance.charAt(0) == '#' &&
                        Character.isDigit(instance.charAt(1))){
                    checkRhythmFlag(rhythmFlagCount, instance.charAt(1));
                    previousFlag = instance.charAt(1);
                }
            }
        }
        return rhythmFlagCount;
    }
    
    /**
     * A method that seeks the bar of a Tab that features the highest number of
     * notes. It then takes the count that each fret is played.
     * @return 
     */
    public int[][] getAdvancedFretCount(){
        Tab current = new Tab(); 
        Tab mostNotes = new Tab();
        // Extract a bar
        for(int j = 0; j < instances.size(); j++){
            String instance = instances.get(j);

            if(isRhythmFlag(instance)){
                current.addInstance(instance);
            }
            else if(instance.charAt(0) == 'b'){
                if(mostNotes.getTotalNoteCount() < current.getTotalNoteCount()){
                    mostNotes = current;
                }
                current = new Tab();
            }
        }
        // Get the Fret Count for the bar with the highest number
        // of notes
        return mostNotes.getFretCount();
    }
    
    
    /**
     * Returns the number of notes played in a Tab
     * @return count
     */
    public int getTotalNoteCount(){
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
    
    /**
     * A method that checks the given fret and course so it can return the 
     * CHROMATIC_SCALE variable array position of the note being played.
     * @param c the current fret in the instance to be checked
     * @param course the current course (pair of lute strings)
     * @return note position in CHROMATIC_SCALE 
     */
    private static int checkNote(char c, int course){
        c = Character.toLowerCase(c);
        int asciiValue = (int)c;
        // Find the fret
        int fret = asciiValue-97;                      
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
        // note position in the CHROMATIC_SCALE array plus the fret. 
        // Modulus 12 (number of notes in the chromatic scale) to give the note
        // played in the CHROMATIC_SCALE
        return (i + fret) % 12;
    }
    
    /**
     * Checks which rhythm flag is being used in an instance and increments the
     * corresponding value of rhythmFlagCount
     * @param c a character representing the rhythmFlag
     * @return true or false
     */
    private boolean checkRhythmFlag(int[] rhythmFlagCount, char c){
        
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
