/**
 * A kNN classification algorithm implementation.
 * 
 */

import java.security.KeyStore.Entry;
import java.util.*;

public class KNN {

	/**
	 * In this method, you should implement the kNN algorithm. You can add 
	 * other methods in this class, or create a new class to facilitate your
	 * work. If you create other classes, DO NOT FORGET to include those java
   * files when preparing your code for hand in.
   *
	 * Also, Please DO NOT MODIFY the parameters or return values of this method,
   * or any other provided code.  Again, create your own methods or classes as
   * you need them.
	 * 
	 * @param trainingData
	 * 		An Item array of training data
	 * @param testData
	 * 		An Item array of test data
	 * @param k
	 * 		The number of neighbors to use for classification
	 * @return
	 * 		The object KNNResult contains classification accuracy, 
	 * 		category assignment, etc.
	 */
	public KNNResult classify(Item[] trainingData, Item[] testData, int k) {

		KNNResult results = new KNNResult();
		results.nearestNeighbors = new String[testData.length][k];
		results.categoryAssignment = new String[testData.length];
		Map<String, Double> neighbors = new HashMap<>();
		for(int i = 0; i < testData.length; i += 1) {
			for(int j = 0; j < trainingData.length; j += 1) {
				double d = Math.sqrt(Math.pow(testData[i].features[0] - trainingData[j].features[0], 2)
						+ Math.pow(testData[i].features[1] - trainingData[j].features[1], 2)
						+ Math.pow(testData[i].features[2] - trainingData[j].features[2], 2));
				neighbors.put(trainingData[j].category +" "+ trainingData[j].name, d);
			}
			List<Map.Entry<String, Double>> li = new ArrayList<Map.Entry<String, Double>>(neighbors.entrySet());
			Collections.sort(li, new Comparator<Map.Entry<String, Double>>() {
	            public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
	                return e1.getValue().compareTo(e2.getValue());
	             }});//TODO: possibly sorted in the wrong order
			int fruit = 0, machine = 0, nation = 0;
			for(int x = 0; x < k; x += 1) {
				 String[] s = li.get(x).getKey().split(" ", 2);
				 switch (s[0]) {
				 case "fruit": fruit += 1; break;
				 case "machine": machine += 1; break;
				 case "nation": nation += 1; break;
				 }
				 results.nearestNeighbors[i][x] = s[1];
			}
			if(nation >= machine && nation >= fruit) {
				results.categoryAssignment[i] = "nation";
			} else if(machine > nation && machine >= fruit) {
				results.categoryAssignment[i] = "machine";
			} else if(fruit > nation && fruit > machine) {
				results.categoryAssignment[i] = "fruit";
			}
		}
		int score = 0;
		for(int y = 0; y < testData.length; y += 1) {
			if(testData[y].category.equals(results.categoryAssignment[y])) {
				score += 1;
			}
		}
		results.accuracy = (double) score/testData.length;
		return results;
	}
}
