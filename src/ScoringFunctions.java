import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScoringFunctions {
    private static int[] prefs = new int[11];

    public static void main(String[] args) throws IOException {
        //List<Solution> solutions = GenerateSolution.genSolution();
        //change(solutions);
        testSuite();
        //analyze(solutions);
    }

    private static void change(List<Solution> solutions){
        //making list for solutions which did not get a preference
        List<Solution> notGotPref = new ArrayList<>();
        List<Solution> temp = new ArrayList<>();
        for (Solution solution : solutions) {
            if (solution.getPrefGotten() == 0) {
                notGotPref.add(solution);
            }
        }
        //going through each student that got a preference
        for (int i = 0; i < solutions.size(); i++) {                                                        //Takes studentA that got a preferential project and goes through
            if (solutions.get(i).getPrefGotten() != 0) {                                                    //all the students whos project was not on their preference list
                temp.add(solutions.get(i));                                                                 // but had studentA's project on their list
                for(int j = 0; j < notGotPref.size(); j++){
                    if(checkForPref(solutions.get(j).getProjectTitle(), notGotPref.get(j).getStudent())){
                        temp.add(notGotPref.get(j));
                    }
                }
                if(temp.size() > 1) {                                                                       //studentA's project is randomly assigned
                    Random r = new Random();
                    int random = r.nextInt(temp.size() - 1);
                    if(random != 0) {                                                                       //if the project is not randomly assigned to the student who already had it
                        temp.get(random).getStudent().setPrefGotten(getPrefNumber(temp.get(0).getProjectTitle(), temp.get(random).getStudent()));//student gets a random project in their stream
                        temp.get(random).getProject().setTaken(false);                                                                           //then a random student who had the project on their
                        temp.get(0).getStudent().setPrefGotten(0);                                                                               //list gets it
                        solutions.get(returnNumber(solutions, temp.get(random))).setProject(temp.get(0).getProject());
                        solutions.get(returnNumber(solutions, temp.get(0))).setProject(GenerateSolution.giveRandomProject(temp.get(0).getStudent().getStream()));
                        solutions.get(returnNumber(solutions, temp.get(0))).getProject().setTaken(true);
                        temp.clear();
                    }
                }
            }
        }


    }

    //method for checking if a student had a project in their preferences
    private static boolean checkForPref(String project, Student student){
        for(int i = 0; i < 10; i++){
            if(student.getPreference(i).equals(project)){
                return true;
            }
        }
        return false;
    }

    //method for checking what number the student had the preference at
    private static int getPrefNumber(String project, Student student){
        for(int i = 0; i < 10; i++){
            if(student.getPreference(i).equals(project)){
                return i;
            }
        }
        return 10;
    }

    //method which returns which number of the list a solution is on
    private static int returnNumber(List<Solution> solutions, Solution s){
        for(int i = 0; i < solutions.size(); i++){
            if(solutions.get(i).getProjectTitle().equals(s.getProjectTitle())){
                return i;
            }
        }
        return 0;
    }

    //method for analyzing a solution
    private static void analyze(List<Solution> solutions){
        int check = 0;
        double gotPrefs = 0;
        for(int i = 0; i < 11; i++){
            prefs[i] = 0;
        }
        for (Solution value : solutions) {
            prefs[value.getPrefGotten()]++;
        }
        System.out.println(prefs[1] + " students got their first preference");
        System.out.println(prefs[2] + " students got their second preference");
        System.out.println(prefs[3] + " students got their third preference");
        System.out.println(prefs[4] + " students got their fourth preference");
        System.out.println(prefs[5] + " students got their fifth preference");
        System.out.println(prefs[0] + " students got no preference" + "\n");

        double size = solutions.size();
        for(int i = 1; i < 6; i++){
            gotPrefs += prefs[i];
        }

        System.out.println((Math.round((prefs[1]/size)* 100.0)) + "% of students got their first preference");
        System.out.println((Math.round((gotPrefs/size)* 100.0)) + "% of students got one of their top five preferences"+ "\n");

        for (Solution solution : solutions) {
            System.out.println(solution.getStudentName() + ": " + solution.getProjectTitle());
        }


    }

//----------------------------------------------------------------------------------------------------------------------------------//
    //TEST METHODS

    //method which tests the checkForPref method
    private static String testCheckForPref(){
        List<String> preferences = new ArrayList<String>();
        preferences.add("test");
        preferences.add("z");
        preferences.add("x");
        preferences.add("y");

        Student student = new Student("check", "CS", 0, preferences, false, 0, 4.0);

        if(checkForPref("test", student)){
            return "checkForPref method is working";
        }else{
            return "error in method checkForPref";
        }
    }

    //method which tests the getPrefNumber method
    private static String testGetPrefNumber(){
        List<String> preferences = new ArrayList<String>();
        preferences.add("z");
        preferences.add("test");
        preferences.add("x");
        preferences.add("y");

        Student student = new Student("check", "CS", 0, preferences, false, 0, 4.0);

        if(getPrefNumber("test", student) == 1){
            return "getPrefNumber method is working";
        }else{
            return "error in method getPrefNumber";
        }
    }

    //method which tests the returnNumber method
    private static String testReturnNumber(){
        List<Solution> testSolutions = new ArrayList<>();
        List<String> preferences = new ArrayList<String>();

        Student studentOne = new Student("check", "CS", 0, preferences, false, 0, 4.0);
        Student studentTwo = new Student("chuck", "DS", 1, preferences, false, 0, 1.0);
        Student studentThree = new Student("cheek", "CS", 2, preferences, false, 0, 2.0);
        Student studentFour = new Student("cheese", "DS", 3, preferences, false, 0, 3.0);

        Project project = new Project("x", "y", "z", false);

        Solution two = new Solution(studentTwo, project, 0);
        testSolutions.add(two);
        testSolutions.add(new Solution(studentOne, project, 1));
        testSolutions.add(new Solution(studentThree, project, 1));
        testSolutions.add(new Solution(studentFour, project, 1));

        if(returnNumber(testSolutions, two) == 0){
            return "returnNumber method is working";
        }else{
            return "error in method returnNumber";
        }
    }

    //method for calling all the test methods
    private static void testSuite(){
        System.out.println("\n");
        System.out.println("Test Results from class ScoringFunctions:");
        System.out.println(testCheckForPref());
        System.out.println(testGetPrefNumber());
        System.out.println(testReturnNumber());
        System.out.println("\n");
        System.out.println("Test Results from class GenerateSolution:");
        System.out.println(GenerateSolution.testGenGPA());
        System.out.println(GenerateSolution.testCheckForOthers());
        System.out.println("\n");
    }
}