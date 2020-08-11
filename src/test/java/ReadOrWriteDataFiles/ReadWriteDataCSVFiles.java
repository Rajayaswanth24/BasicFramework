package ReadOrWriteDataFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class ReadWriteDataCSVFiles {
	public void readCSVFile(String FilePath, int CollumnNumber) {

		String csvFilePath = FilePath;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFilePath));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] country = line.split(cvsSplitBy);

				System.out.println("Country [code= " + country[3] + " , name=" + country[4] + "]");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}

	public void writeDataLineByLine(String filePath) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);

			// adding header to csv
			String[] header = { "Name", "Class", "Marks" };
			writer.writeNext(header);

			// add data to csv
			String[] data1 = { "Aman", "10", "620" };
			writer.writeNext(data1);
			String[] data2 = { "Suraj", "10", "630" };
			writer.writeNext(data2);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
