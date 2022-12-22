import java.io.*;
import java.util.*;


//ERROR LINES: 183-184, 323, 351-355, 368-372

//HERO CLASS
public class Hero {
	
	String name;
	String type;
	double cost;
	double attack_point;
	boolean selected;
	
	public Hero(String name,String type,double cost,double attack_point) {
		this.name=name;
		this.type=type;
		this.cost=cost;
		this.attack_point=attack_point;
		selected=false;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public double getCost() {
		return cost;
	}

	public double getAttack_point() {
		return attack_point;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}

import java.io.*;
import java.util.*;

public class Muzaffer_Sevili_2019510069 {

	// Global Variables
	static int GOLD_AMOUNT;
	static int MAX_LEVEL_ALLOWED;
	static int NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;
	static int COST_GA;
	static int COST_DP;
	static int COST_RA;

	// Random Approach
	public static int randomApproach(Hero[] heroes, int gold_RA, Hero[] random_heroes) {

		setStatus(heroes);

		int total_point = 0;
		Random rand = new Random();
		int random = -1;

		for (int i = 0; i < MAX_LEVEL_ALLOWED; i++) {

			int selection_level = 0;
			do {
				random = rand.nextInt(0, heroes.length);
				String hero_type = heroes[random].getType();
				selection_level = LevelType(hero_type);
			} while (heroes[random].isSelected());

			if (heroes[random].getCost() <= gold_RA) {

				for (int j = 0; j < NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL; j++) {
					heroes[(selection_level - 1) * NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL + j].setSelected(true);
				}
				random_heroes[i] = heroes[random];
				gold_RA -= heroes[random].getCost();
				total_point += heroes[random].getAttack_point();

			} else {
				break;
			}

		}
		COST_RA = GOLD_AMOUNT - gold_RA;
		return total_point;
	}

	// Greedy Approach
	public static int greedyApproach(Hero[] heroes, int gold_GA, Hero[] greedy_heroes) {

		setStatus(heroes);

		int total_point = 0;
		double best_profit_ratio;
		double hero_profit_ratio;
		int selection_num = -1;
		int selection_level;

		for (int i = 0; i < MAX_LEVEL_ALLOWED; i++) {
			best_profit_ratio = 0;
			selection_num = -1;

			for (int j = 0; j < heroes.length; j++) {

				hero_profit_ratio = heroes[j].getAttack_point() / heroes[j].getCost();

				if (((hero_profit_ratio > best_profit_ratio && !heroes[j].isSelected())
						|| (hero_profit_ratio == best_profit_ratio
								&& heroes[j].getAttack_point() >= heroes[selection_num].getAttack_point()))
						&& heroes[j].getCost() <= gold_GA) {

					best_profit_ratio = hero_profit_ratio;
					selection_num = j;
				}
			}

			if (selection_num != -1) {
				if (heroes[selection_num].getCost() <= gold_GA && !heroes[selection_num].isSelected()) {

					String hero_type = heroes[selection_num].getType();
					selection_level = LevelType(hero_type);

					for (int j = 0; j < NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL; j++) {
						heroes[(selection_level - 1) * NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL + j].setSelected(true);
					}

					greedy_heroes[i] = heroes[selection_num];
					gold_GA -= heroes[selection_num].getCost();
					total_point += heroes[selection_num].getAttack_point();

				} else
					break;
			} else
				break;
		}

		COST_GA = GOLD_AMOUNT - gold_GA;
		return total_point;
	}

	// Dynamic Programming Approach
	public static int dynamicProgrammingApproach(Hero[] heroes, int gold_DP, Hero[] dynamic_heroes) {

		setStatus(heroes);
		int length = heroes.length;
		double[] attack_points = new double[length];
		double[] costs = new double[length];

		for (int i = 0; i < length; i++) {
			attack_points[i] = heroes[i].getAttack_point();
			costs[i] = heroes[i].getCost();
		}

		int i, j;
		int Army[][] = new int[length + 1][gold_DP + 1];
		
		// Build table K[][] in bottom up manner
		for (i = 0; i <= length; i++) {

			for (j = 0; j <= gold_DP; j++) {
				if (i == 0 || j == 0)
					Army[i][j] = 0;
				else if (costs[i - 1] <= j) 
					Army[i][j] = max(attack_points[i - 1] + Army[i - 1][(int) (j - costs[i - 1])], Army[i - 1][j]);
				
				else 
					Army[i][j] = Army[i - 1][j];
			}

		}

		//WRONG RESULT - I can't measure the cost
		//COST_DP = GOLD_AMOUNT - gold_DP;
		return Army[length][gold_DP];

	}

	//Value Comparison
	public static int max(double d, int b) {
		return (int) ((d > b) ? d : b);
	}

	//Reset selections
	public static void setStatus(Hero[] heroes) {
		for (int i = 0; i < heroes.length; i++) {
			heroes[i].setSelected(false);
		}
	}

	// Convertion level of heros to Integer (For choosing only one hero for each level)
	public static int LevelType(String hero_type) {
		if ("Pawn".equals(hero_type))
			return 1;
		else if ("Rook".equals(hero_type))
			return 2;
		else if ("Archer".equals(hero_type))
			return 3;
		else if ("Knight".equals(hero_type))
			return 4;
		else if ("Bishop".equals(hero_type))
			return 5;
		else if ("War_ship".equals(hero_type))
			return 6;
		else if ("Siege".equals(hero_type))
			return 7;
		else if ("Queen".equals(hero_type))
			return 8;
		else if ("King".equals(hero_type))
			return 9;
		else
			return 0;
	}

	// Input Line Counter
	public static int CSVLineCounter() throws IOException {
		int line_counter = 0;
		FileReader fileReader = new FileReader("input_1.csv");
		@SuppressWarnings("unused")
		String line;
		BufferedReader br = new BufferedReader(fileReader);

		while ((line = br.readLine()) != null) {
			line_counter++;
		}
		br.close();
		return line_counter - 1;
	}

	// Reading File
	public static void readFile(Hero[] heroes, int input_size, int heroes_size) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("input_1.csv"));

		String line;
		line = sc.nextLine();
		int counter = 0;
		int i = 0;

		while (sc.hasNextLine() && heroes_size != i) {

			if (counter != NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL) {
				line = sc.nextLine();
				String[] input = line.split(",");
				heroes[i] = new Hero(input[0], input[1], Double.parseDouble(input[2]), Double.parseDouble(input[3]));
				counter++;
				i++;
			} else {
				if (NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL != input_size / 9) {
					int piece_size = (input_size / 9);
					for (int x = 0; x < piece_size - counter; x++) {
						line = sc.nextLine();
					}
				}
				counter = 0;
			}
		}
		sc.close();
	}

	//Print Arrays which selected heros
	public static void selected_heroes(Hero[] selectedApproach) {
		for (int i = 0; i < selectedApproach.length; i++) {
			if (selectedApproach[i] != null) {
				System.out.println(" " + selectedApproach[i].getName() + " (Type: " + selectedApproach[i].getType()
						+ " , Cost: " + selectedApproach[i].getCost() + " , Attack Point: "
						+ selectedApproach[i].getAttack_point());
			} else
				break;
		}
	}

	// Controlling Inputs
	public static void Control_Inputs() {
		Scanner sc = new Scanner(System.in);

		while (GOLD_AMOUNT < 5 || GOLD_AMOUNT > 1200) {
			System.out.println("Please enter the Gold Amount: ");
			GOLD_AMOUNT = sc.nextInt();

			if (GOLD_AMOUNT < 5 || GOLD_AMOUNT > 1200) {
				System.out.println("Wrong Gold Amount! Try again.\n");
			}
		}

		while (MAX_LEVEL_ALLOWED < 1 || MAX_LEVEL_ALLOWED > 9) {
			System.out.println("Please enter the Maximum Allowed Level: ");
			MAX_LEVEL_ALLOWED = sc.nextInt();

			if (MAX_LEVEL_ALLOWED < 1 || MAX_LEVEL_ALLOWED > 9) {
				System.out.println("Wrong Maximum Allowed Level! Try again.\n");
			}
		}

		while (NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL < 1 || NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL > 25) {
			System.out.println("Please enter the Number of Pieces For Each Level: ");
			NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL = sc.nextInt();

			if (NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL < 1 || NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL > 25) {
				System.out.println("Wrong Number of Pieces For Each Level! Try again.\n");
			}
		}
		sc.close();
	}

	// Analysis of Algorithms
	public static void Algorithm_Analysis(Hero[] heroes) {

		int gold_GA = GOLD_AMOUNT;
		int gold_DP = GOLD_AMOUNT;
		int gold_RA = GOLD_AMOUNT;

		Hero[] greedy_heroes = new Hero[MAX_LEVEL_ALLOWED];
		Hero[] dynamic_approach_heroes = new Hero[MAX_LEVEL_ALLOWED];//Couldn't Get Correct Results
		Hero[] random_approach_heroes = new Hero[MAX_LEVEL_ALLOWED];

		// Greedy Approach Time Calculation
		long greedy_startTime = System.currentTimeMillis();
		int attack_point_GA = greedyApproach(heroes, gold_GA, greedy_heroes);
		long greedy_estimatedTime = System.currentTimeMillis() - greedy_startTime;

		long dynamic_startTime = System.currentTimeMillis();
		int attack_point_DA = dynamicProgrammingApproach(heroes, gold_DP, dynamic_approach_heroes);
		long dynamic_estimatedTime = System.currentTimeMillis() - dynamic_startTime;

		// Random Approach Time Calculation
		long random_startTime = System.currentTimeMillis();
		int attack_point_RA = randomApproach(heroes, gold_RA, random_approach_heroes);
		long random_estimatedTime = System.currentTimeMillis() - random_startTime;

		System.out.println("\n\n\n============== TRIAL #1 ==============");

		System.out.println("\n--Computer's Greedy Approach Results--");
		System.out.println("---Attack Point: " + attack_point_GA);
		System.out.println("---Cost of Army: " + COST_GA);
		System.out.println("---Heroes: ");
		selected_heroes(greedy_heroes);
		System.out.println("---Execution Time: " + greedy_estimatedTime + " seconds");

		System.out.println("\n\n--User's Dynamic Programming Results--:");
		System.out.println("---Attack Point: " + attack_point_DA);
		/*Couldn't Get Correct Results
		System.out.println("---Cost of Army: " + COST_DP);
		System.out.println("---Heroes: ");
		selected_heroes(dynamic_approach_heroes);
		*/
		System.out.println("---Execution Time: " + dynamic_estimatedTime + " seconds");

		System.out.println("\n\n\n============== TRIAL #2 ==============\n");
		System.out.println("Computer's Random Approach Point Results--");
		System.out.println("---Attack Point: " + attack_point_RA);
		System.out.println("---Cost of Army: " + COST_RA);
		System.out.println("---Heroes: ");
		selected_heroes(random_approach_heroes);
		System.out.println("---Execution Time: " + random_estimatedTime+ " seconds");

		System.out.println("\n\n--User's Dynamic Programming Results--:");
		System.out.println("---Attack Point: " + attack_point_DA);
		/*Couldn't Get Correct Results
		System.out.println("---Cost of Army: " + COST_DP);
		System.out.println("---Heroes: ");
		selected_heroes(dynamic_approach_heroes);
		*/
		System.out.println("---Execution Time: " + dynamic_estimatedTime + " seconds");

	}

	public static void main(String[] args) throws IOException {

		Control_Inputs();

		int heroes_size = MAX_LEVEL_ALLOWED * NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;

		Hero[] heroes = new Hero[heroes_size];

		int input_size = CSVLineCounter();
		readFile(heroes, input_size, heroes_size);

		Algorithm_Analysis(heroes);

	}
}
