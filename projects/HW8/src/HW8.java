import java.io.File;
import java.io.IOException;
/**
 * Main class that acts as a launcher for your classification algorithm. 
 * !! DO NOT MODIFY !!
 *
 */

public class HW8 {
	/*
	 * Reads the command-line arguments, performs type conversion, 
   * calls classification algorithm and prints results to the console.
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 4) {
			System.out.println("Please enter 4 valid arguments:\n" +
				"<training file name> <test file name> <k> <Output flag>");
			return;
		}
		
		// Read training data
		String trainingFileName = args[0];
		Item[] trainingData = Utils.readInItem(new File(trainingFileName));
		
		// Read test data
		String testFileName = args[1];
		Item[] testData = Utils.readInItem(new File(testFileName));
		
		// Read k
		int k = Integer.valueOf(args[2]);
		
		// Read flag
		int flag = Integer.parseInt(args[3]);
		if(flag > 3 || flag < 1) {
			System.out.println("Output flag MUST be an integer from 1 to 3");
			return;
		}

		// Run classification algorithm
		KNN kNN = new KNN();
		KNNResult res = kNN.classify(trainingData, testData, k);
		
		// Check null result
		if(res == null) {
			System.out.println("Please implement the classification method to return a non-null result.");
			return;
		}

		// Print results
		if (flag == 1) {
			System.out.println(String.format("%.6f", res.accuracy));
		} else if(flag == 2 ) {
			for (String c : res.categoryAssignment)
				System.out.print(c + " ");
			System.out.println();
		} else if(flag == 3) {
			for (String[] nl : res.nearestNeighbors) {
				for(String n : nl) 
					System.out.print(n + " ");
				System.out.println();
			}
		}
	}
}
