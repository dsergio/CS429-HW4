import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
	
	public List<List<Double>> readDataFromFile(String fileName) {
		
		List<List<Double>> data = new ArrayList<List<Double>>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			
			
			while ((line = br.readLine()) != null) {
				
				ArrayList<Double> lineData = new ArrayList<Double>();
				for (String s : line.split("\\s+")) {
					lineData.add(Double.parseDouble(s));
				}
				data.add(lineData);
				
			}
			br.close();
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return data;
	}
	
	public void printDataToFile(String s, String outputFileName) {
		
		FileWriter fileWriter;
		
		try {
			
			fileWriter = new FileWriter(outputFileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.print(s);
		    printWriter.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}
