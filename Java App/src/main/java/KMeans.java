import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KMeans {

	FileIO fileIO;

	int k;

	public KMeans(int k) {

		fileIO = new FileIO();

		String fileName;
		fileName = "./synthetic_control_data.txt";
		
//		fileName = "./class_example.txt"; k = 3;
		
		Map<Integer, List<List<Double>>> clusters = new HashMap<Integer, List<List<Double>>>();
		
		List<List<Double>> dataLines = fileIO.readDataFromFile(fileName);

		Random rand = new Random();
		int N = dataLines.size();
		List<Integer> randomIndexes = new ArrayList<Integer>();
		for (int i = 0; i < k; i++) {
			int n = rand.nextInt(N);
			while (randomIndexes.contains(n)){
				n = rand.nextInt(N);
			}
			randomIndexes.add(n);
			List<List<Double>> container = new ArrayList<List<Double>>();
			container.add(dataLines.get(n));
			System.out.println("adding " + dataLines.get(n) + " at cluster " + n);
			clusters.put(n, container);
		}

		System.out.println("Total rows: " + N);
		System.out.println(randomIndexes);
		System.out.println();
		
		
		for (int j = 0; j < dataLines.size(); j++) {
			
			double minDistance = Double.MAX_VALUE;
			int minIndex = randomIndexes.get(0);
			
			for (int i = 0; i < k; i++) {
				if (!randomIndexes.contains(j)) {
					
					double d = distance(dataLines.get(j), dataLines.get(randomIndexes.get(i)));
//					System.out.println("comparing " + dataLines.get(j) + " and " + dataLines.get(randomIndexes.get(i)) + " d: " + d);
					if (d < minDistance) {
						minDistance = d;
						minIndex = randomIndexes.get(i);
						
					}
				}
			}
			
//			System.out.println("minIndex: " + minIndex);
			if (!randomIndexes.contains(j) && !clusters.get(minIndex).contains(dataLines.get(j))) {
				clusters.get(minIndex).add(dataLines.get(j));
				System.out.println("adding " + dataLines.get(j) + " to cluster " + minIndex);
			}
//			System.out.println("min distance: " + minDistance);
		}
		
		for (int i : clusters.keySet()) {
			System.out.println("cluster " + i);
			List<List<Double>> data = clusters.get(i);
			for (List<Double> l : data) {
				System.out.println(l);
			}
			System.out.println("centroid: " + centroid(data));
			System.out.println();
		}
		
		
		
		boolean changed = true;
		while (changed) {
			
			changed = false;
			
			for (int j = 0; j < dataLines.size(); j++) {
				
				int minIndex = 0;
				double minDistance = Double.MAX_VALUE;
				for (int i : clusters.keySet()) {
					List<Double> centroidValue = centroid(clusters.get(i));
					double d = distance(dataLines.get(j), centroidValue);
					if (d < minDistance) {
						minDistance = d;
						minIndex = i;
					}
				}
				if (clusters.get(minIndex).contains(dataLines.get(j))) { // this tuple is already in the correct cluster
					
				} else {
					changed = true;
					for (int i : clusters.keySet()) {
						if (clusters.get(i).contains(dataLines.get(j))) {
							clusters.get(i).remove(dataLines.get(j));
							System.out.println("removing " + dataLines.get(j) + " from cluster " + i + " and adding to cluster " + minIndex + " clusters.get(i): " + clusters.get(i));
						}
					}
					clusters.get(minIndex).add(dataLines.get(j));
					
				}
				
				
			}
			
			
		}
		
		
		
		
		for (int i : clusters.keySet()) {
			System.out.println("cluster " + i);
			List<List<Double>> data = clusters.get(i);
			for (List<Double> l : data) {
				System.out.println(l);
			}
			System.out.println("centroid: " + centroid(data));
			System.out.println();
			
			fileIO.printDataToFile(clusters.get(i).toString().replaceAll(",", "").replaceAll("[\\[\\]]",  ""), "" + i);
		}

	}

	public double distance(List<Double> list1, List<Double> list2) {
		
		if (list1.size() != list2.size()) {
			return Double.MAX_VALUE;
		}
		double sum = 0;
		for (int i = 0; i < list1.size(); i++) {
			sum += Math.pow(list1.get(i) - list2.get(i), 2);
		}
		return Math.sqrt(sum);
	}
	
	public List<Double> centroid(List<List<Double>> list) {
		
		List<Double> sumValues = new ArrayList<Double>();
		List<Double> centroidValues = new ArrayList<Double>();
		
		for (int j = 0; j < list.get(0).size(); j++) {
			sumValues.add(j, 0.0);
			centroidValues.add(j, 0.0);
		}
		
		for (int i = 0; i < list.size(); i++) {
			
			for (int j = 0; j < list.get(i).size(); j++) {
				double sum = sumValues.get(j);
				sum += list.get(i).get(j);
				sumValues.set(j, sum);
			}
		}
		
		double N = (double)list.size();
		for (int i = 0; i < centroidValues.size(); i++) {
			centroidValues.set(i, sumValues.get(i) / N);
		}
		
//		System.out.println("sumValues: " + sumValues);
//		System.out.println("centroidValues: " + centroidValues);
		
		return centroidValues;
	}
}
