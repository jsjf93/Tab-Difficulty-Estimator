/*
 * The main class that initiates the program.
 * It creates an instance of a TabDatabase after being passed the root folder 
 * for the graded pieces and then creates an instance of the FeatureExtractor
 * and creates each of the ARFFs ready to be used by weka.
 */
package tabdifficultyanalyser;

import java.io.FileReader;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

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
        // Generate the ARFFs
        generateArffs(db);
        
        String[] arffs = {"noteCount.arff", "highestFret.arff", 
                          "noteCountHighestFret.arff", "fretCount.arff",
                          "chordCount.arff", "rhythmFlagCount.arff",
                          "advancedFretCount.arff", "totalNoteCount.arff",
                          "numberOfBars.arff", "combined.arff"};
        
        // LOOCV for each arff
//        for(String arff : arffs){
//            System.out.println();
//            System.out.println("*************** " + arff + " ***************");
//            // Load arff
//            Instances data = loadData(arff);
//            
//            // Create instance of Naive Bayes classifier and Evaluation object
//            NaiveBayes nb = new NaiveBayes();
//            //J48 tree = new J48();
//            Evaluation e = new Evaluation(data);
//            // Number of folds for cross validation
//            int folds = data.size()-1;
//            // Cross validate
//            System.out.println("Naive Bayes");
//            e.crossValidateModel(nb, data, folds, new Random(1));
//            // Output results
//            System.out.println(e.toSummaryString());
//            System.out.println("Error rate: " + e.errorRate());
//            System.out.println();
//            System.out.println(e.toMatrixString("=== Confusion Matrix ==="));
//            
//            /*System.out.println("Tree");
//            e = new Evaluation(data);
//            e.crossValidateModel(tree, data, folds, new Random(1));
//            // Output results
//            System.out.println(e.toSummaryString());
//            System.out.println("Error rate: " + e.errorRate());
//            System.out.println();
//            System.out.println(e.toMatrixString("=== Confusion Matrix ==="));*/
//            
//        }
        
        // Demonstration
        // Read in training data
        TabDatabase trainDB = new TabDatabase();
        trainDB.readInTabDatabase("demo/pieces");
        // Create arffs and load instances from combined.arff
        generateArffs(trainDB);
        Instances train = loadData("combined.arff");
        // Build classifier
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(train);
        // Read in test data (2 examples) and produce arffs
        TabDatabase testDB = new TabDatabase();
        testDB.readInTabDatabase("demo/demo");
        generateArffs(testDB);
        Instances test = loadData("combined.arff");
        System.out.println(nb.classifyInstance(test.instance(0))+1);
        System.out.println(nb.classifyInstance(test.instance(1))+1);

        System.out.println(test.instance(1));
    }
    
    /**
     * Generates all of the ARFF files
     * @param db the database of tabs
     */
    public static void generateArffs(TabDatabase db){
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
    
    /**
     * A method that takes a file path as a String and reads the data into an
     * Instances object.
     * @param path
     * @return i
     */
    public static Instances loadData(String path){
        Instances i = null;
        try{
            FileReader fr = new FileReader(path);
            i = new Instances(fr);
            i.setClassIndex(i.numAttributes()-1);
        }
        catch(Exception e){
            System.out.println("Unable to read file. Exception: " + e);
        }
        return i;
    }
}
