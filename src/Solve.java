import Classes.Solution;
import GUI.UI;

import javax.swing.*;
import java.util.List;

public class Solve {
	static final UI ui = new UI(new JFrame());
	private static int solutionsGenerated = 0;

	public static void main(String[] args){
		Solve s = new Solve();
		s.solver();
	}
	private void solver(){
		boolean validCommand = false;
		ui.displayStart();
		do {
			String command = ui.getCommand().toLowerCase();
			if (command.equals("sa")) {
				validCommand = true;
				simulatedAnnealing();
				ui.displayFinish();
			}
			if (command.equals("ga")) {
				validCommand = true;
				geneticAlgorithm();
				ui.displayFinish();
			}
			if (command.equals("quit")) {
				ui.quit();
			}
			if(!validCommand){
				ui.displayInfoString("\n\nINVALID INPUT:\nPlease enter either 'GA' or 'SA'");
			}
		}while(!validCommand);
		String command = ui.getCommand().toLowerCase();
		if (command.equals("quit")) {
			ui.quit();
		}
		if(command.equals("restart")){
			solver();
		}
	}

	private void geneticAlgorithm(){
		ui.displayGAInfo();
		try {
			GeneticAlgorithm GA = new GeneticAlgorithm();
			int popNumber=0;
			double matePercentage=0;
			double cullPercentage=0;
			int numGenerations=0;
			boolean isIntOne;
			boolean isIntTwo;
			boolean isDoubleOne;
			boolean isDoubleTwo;
			ui.displayInfoString("\nPlease Enter Population Size");
			do{
				String command = ui.getCommand();
				isIntOne = checkInt(command);
				if(isIntOne)
					popNumber = Integer.parseInt(command);
				ui.displayInfoString("Population Size: "+command);
				ui.displayInfoString("\nPlease Enter Mate Percentage");
				command = ui.getCommand();
				isDoubleOne = checkDouble(command);
				if(isDoubleOne)
					matePercentage = Double.parseDouble(command);
				ui.displayInfoString("Mate Percentage: "+command+"%");
				ui.displayInfoString("\nPlease Enter Cull Percentage");
				command = ui.getCommand();
				isDoubleTwo = checkDouble(command);
				if(isDoubleTwo)
					cullPercentage = Double.parseDouble(command);
				ui.displayInfoString("Cull Percentage: "+command+"%");
				ui.displayInfoString("\nPlease Number of Generations");
				command = ui.getCommand();
				isIntTwo = checkInt(command);
				if(isIntTwo)
					numGenerations = Integer.parseInt(command);
				ui.displayInfoString("Number of Generations: "+command);
				if(!isIntOne || !isIntTwo || !isDoubleOne || !isDoubleTwo){
					ui.displayInfoString("\n\nWARNING: INVALID INPUT\nPlease re-enter your values\n\n");
				}
			}while(!isIntOne || !isIntTwo || !isDoubleOne || !isDoubleTwo);
			ui.displayInfoString("Please wait as this will take a few moments\n");
			List<Solution> sol = GA.solve(popNumber, matePercentage, cullPercentage, numGenerations);
			solutionsGenerated++;
			GeneticAlgorithm.createSolutionFile(sol, "Solutions("+solutionsGenerated+").xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void simulatedAnnealing(){
		ui.displaySAInfo();
		try {
			SimulatedAnnealing SA = new SimulatedAnnealing();
			List<Solution> sol = SA.solve();
			solutionsGenerated++;
			GeneticAlgorithm.createSolutionFile(sol, "Solutions("+solutionsGenerated+").xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkInt(String string){
		try {
			Integer.parseInt(string);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	private boolean checkDouble(String string){
		try {
			Double.parseDouble(string);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}



}
