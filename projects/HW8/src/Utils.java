import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class contains some basic methods 
 * 
 */

public class Utils {
	
	// Splits a string into an array of doubles
	public static double[] splitToDouble(String s, String sep) {
		String[] ss = s.split(sep);
		double[] is = new double[ss.length];
		for (int i = 0; i < is.length; i++)
			is[i] = Double.parseDouble(ss[i]);
		return is;
	}

	// Converts an array of string to an array of doubles
	public static double[][] toDoubles(String[][] data) {
		double[][] ds = new double[data.length][];
		for (int i = 0; i < ds.length; i++) {
			int n = data[i].length;
			ds[i] = new double[n];
			for (int j = 0; j < n; j++) {
				ds[i][j] = Double.parseDouble(data[i][j]);
			}
		}
		return ds;
	}

	// Reads a file into an array of strings, tab-separated
	public static String[][] readInString(File f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String s;
		List<String[]> data = new ArrayList<String[]>();
		while ((s = br.readLine()) != null) {
			String[] fields = s.split("\t");
			data.add(fields);
		}
		br.close();
		String[][] results = new String[data.size()][];
		for (int i = 0; i < data.size(); i++)
			results[i] = data.get(i);
		return results;
	}
	
	// Reads a tab separated file into an array of items
	public static Item[] readInItem(File f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String s;
		List<String[]> data = new ArrayList<String[]>();
		while ((s = br.readLine()) != null) {
			String[] fields = s.split("\t");
			data.add(fields);
		}
		br.close();
		Item[] items = new Item[data.size()];
		for (int i = 0; i < data.size(); i++) {
			items[i] = new Item();
			items[i].category = data.get(i)[0];
			items[i].name = data.get(i)[1];
			for (int j = 2; j < data.get(i).length; j ++) {
				items[i].features[j - 2] = Double.valueOf(data.get(i)[j]);
			}
		}
		return items;
	}
	
	// Formats a double array
	public static void print(double[] c) {
		String format = "%." + 3 + "f";
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(format, c[0]));
		for (int j = 1; j < c.length; j++) {
			sb.append(",");
			sb.append(String.format(format, c[j]));
		}
		System.out.println(sb.toString());
	}
}
