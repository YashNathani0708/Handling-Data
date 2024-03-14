package input;

import java.io.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import Exceptions.*;
import java.util.Arrays;
import java.util.List;

public class driver {
	/**
	 * creating print writers and object output streams, in static
	 */

	static PrintWriter cartoonPrinter = null;
	static PrintWriter hobbiesPrinter = null;
	static PrintWriter moviesPrinter = null;
	static PrintWriter musicPrinter = null;
	static PrintWriter nostalgiaPrinter = null;
	static PrintWriter oldPrinter = null;
	static PrintWriter sportPrinter = null;
	static PrintWriter trainsPrinter = null;
	static PrintWriter syntaxPrinter = null;
	static PrintWriter semanticPrinter = null;

	static ObjectOutputStream cartoonsObjectPrinter = null;
	static ObjectOutputStream hobbiesObjectPrinter = null;
	static ObjectOutputStream moviesObjectPrinter = null;
	static ObjectOutputStream musicObjectPrinter = null;
	static ObjectOutputStream nostalgiaObjectPrinter = null;
	static ObjectOutputStream oldObjectPrinter = null;
	static ObjectOutputStream sportObjectPrinter = null;
	static ObjectOutputStream trainsObjectPrinter = null;

	static int missing = 0;
	static int fewFields = 0;
	static int moreFields = 0;
	static int unknowGenre = 0;

	/**
	 * opening print writers
	 */
	public static void openPrinter() {
		try {
			cartoonPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/Cartoons_Comics.csv", true));
			hobbiesPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/Hobbies_Collectibles.csv",
					true));
			moviesPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/Movies_TV_Books.csv", true));
			musicPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/Music_Radio_Books.csv", true));
			nostalgiaPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/Nostalgia_Electronic_Books.csv",
					true));
			oldPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/Old_Time_Radio_Books.csv",
					true));
			sportPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/Sports_Memorabilia.csv", true));
			trainsPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/Trains_Planes_Automobiles.csv",
					true));
			syntaxPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile/syntax_error_file.txt", true));
		} catch (Exception e) {
			// System.out.println("Could not find one or more of the output files (Part
			// 1)");
			System.exit(0);
		}
	}

	/**
	 * closing print writers
	 */

	public static void closePrinter() {
		cartoonPrinter.close();
		hobbiesPrinter.close();
		moviesPrinter.close();
		musicPrinter.close();
		nostalgiaPrinter.close();
		oldPrinter.close();
		sportPrinter.close();
		trainsPrinter.close();
		syntaxPrinter.close();
	}

	/**
	 * syntax text printer
	 * 
	 * @param x    = for passing a file
	 * @param s    = for passing a string
	 * @param line = for passing the line to print, as well as the path
	 */
	public static void syntaxerr(File x, String s, String line) {

		syntaxPrinter.println("\nsyntax Error in file: " + x.getName());
		syntaxPrinter.println("======================");
		syntaxPrinter.println("Error: " + s);
		syntaxPrinter.println("Record: " + line);
	}

	/**
	 * semantic text printer
	 * 
	 * @param x    = for passing a file
	 * @param s    = for passing a string
	 * @param line = for passing the line to print, as well as the path
	 */
	public static void semanticerr(File x, String s, String line) {

		semanticPrinter.println("\nsemantic Error in file: " + x.getName());
		semanticPrinter.println("======================");
		semanticPrinter.println("Error: " + s);
		semanticPrinter.println("Record: " + line);
	}

	/**
	 * method for validating syntax errors
	 */
	public static void validate(File x) throws FileNotFoundException {

		String filename = x.toString();
		Scanner sc;
		sc = new Scanner(new FileInputStream(filename));
		String line = null;
		int c = 0;

		while (sc.hasNextLine()) {
			int count = 0;
			c++;
			line = sc.nextLine();
			String[] entries = line.split(",");
			/**
			 * validation for all missing fields
			 */
			try {
				// 1st Method - Verifing fields - DONE
				if (validateTotalFields(entries, line, x)) {
					// 2. Validate if year is missing or It has a bad Year - DONE
					validateYearField(entries, line, x);

					// 3. Validate if price is missing - DONE
					validatePriceField(entries, line, x);

					// 4. Validate ISBN - DONE
					validateISBN(entries, line, x);

					// 5. Validate CNN and assign in respective file
					validateCNN(entries, line, x);

					validateMissing(entries, line, x);
				}
			} catch (Exception e) {
				continue;
			}

		}
	}

	private static void validateMissing(String[] entries, String line, File x) {
		String authorString = entries[entries.length - 5].trim();
		try {

			if (authorString.isEmpty()) {
				// System.out.println("\n" + line);
				missing++;
				throw new missingFieldException("Missing Field - Author");

			}
		} catch (missingFieldException e) {
			syntaxerr(x, e.getMessage(), line);
			// // System.out.println(e.getMessage());
		}
	}

	/**
	 * validating for genre
	 * 
	 * @param entries
	 * @param line
	 * @param x
	 * @throws unkownGenreException
	 */
	private static void validateCNN(String[] entries, String line, File x) throws unkownGenreException {
		int temp = 0;
		String isCNN = entries[entries.length - 2].trim();
		try {
			if (isCNN.isEmpty() || isCNN.length() != 3) {
				// // System.out.println("\n" + line);
				temp++;
				missing++;
				throw new missingFieldException("Missing Field - Genre");

			}
		} catch (missingFieldException e) {
			syntaxerr(x, e.getMessage(), line);
		}
		try {
			if (temp == 0)

			{
				// // System.out.println(isCNN);
				if (isCNN.equals("CCB"))
					cartoonPrinter.println(line);
				else if (isCNN.equals("HCB"))
					hobbiesPrinter.println(line);
				else if (isCNN.equals("MTV"))
					moviesPrinter.println(line);
				else if (isCNN.equals("MRB"))
					musicPrinter.println(line);
				else if (isCNN.equals("NEB"))
					nostalgiaPrinter.println(line);
				else if (isCNN.equals("OTR"))
					oldPrinter.println(line);
				else if (isCNN.equals("SSM"))
					sportPrinter.println(line);
				else if (isCNN.equals("TPA"))
					trainsPrinter.println(line);
				else
					throw new unkownGenreException();
			}
		} catch (unkownGenreException e) {
			unknowGenre++;
			// // System.out.println("\n" + line);
			// syntaxerr(x, e.getMessage(), line);
			// // // System.out.println(e.getMessage());
		}
	}

	/**
	 * validating for missing ISBN
	 * 
	 * @param entries
	 * @param line
	 * @param x
	 */
	private static void validateISBN(String[] entries, String line, File x) {
		String isbnString = entries[entries.length - 3].trim();
		int temp = 0;
		try {

			if (isbnString.isEmpty()) {
				// // System.out.println("ISBN is missing in line: " + line);
				temp++;
				missing++;
				throw new missingFieldException("Missing Field - ISBN");

			}
		} catch (missingFieldException e) {
			syntaxerr(x, e.getMessage(), line);
			// // System.out.println(e.getMessage());
		}
	}

	/**
	 * validating for invalid ISBN
	 * 
	 * @param entries
	 * @param line
	 * @param x
	 */
	public static void badISBN(String[] entries, String line, File x) {
		String isbnString = entries[entries.length - 3].trim();
		try {
			if (isbnString.length() == 10) {
				int sum = 0;
				for (int i = 0; i < 10; i++) {
					char c = isbnString.charAt(i);
					if (i == 9 && c == 'X') {
						sum += 10;
					} else if (Character.isDigit(c)) {
						int digit = Character.getNumericValue(c);
						sum += (10 - i) * digit;
					} else {
						// // System.out.println("Invalid ISBN format: " + isbnString);
					}
				}
				if (sum % 11 != 0) {
					throw new BadIsbn10Exception();
				}
			} else if (isbnString.length() == 13) {
				int sum = 0;
				for (int i = 0; i < 13; i++) {
					char c = isbnString.charAt(i);
					if (i == 12 && c == 'X') {
						sum += 10;
					} else if (Character.isDigit(c)) {
						int digit = Character.getNumericValue(c);
						sum += ((i % 2 == 0) ? 1 : 3) * digit;
					} else {
						// // System.out.println("Invalid ISBN format: " + isbnString);
					}
				}
				if (sum % 10 != 0) {
					throw new BadIsbn13Exception();
				}
			} else {
				// // System.out.println("Invalid ISBN format: " + isbnString);
			}
		} catch (BadIsbn10Exception e) {
			semanticerr(x, e.getMessage(), line);
			// // System.out.println(e.getMessage());
		} catch (BadIsbn13Exception e) {
			semanticerr(x, e.getMessage(), line);
			// // System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * validating for missing price field
	 * 
	 * @param entries
	 * @param line
	 * @param x
	 */
	private static void validatePriceField(String[] entries, String line, File x) {
		String priceString = entries[entries.length - 4].trim();
		int temp = 0;
		try {

			if (priceString.isEmpty()) {
				// // System.out.println("Price is missing in line: " + line);
				temp++;
				missing++;
				throw new missingFieldException("Missing Field - Price");

			}
		} catch (missingFieldException e) {
			syntaxerr(x, e.getMessage(), line);
			// // System.out.println(e.getMessage());
		}
	}

	/**
	 * validating for invalid price value
	 * 
	 * @param entries
	 * @param line
	 * @param x
	 */
	private static void badPrice(String[] entries, String line, File x) {
		String priceString = entries[entries.length - 4].trim();
		try {
			double price = Double.parseDouble(priceString);
			if (price < 0) {
				// // System.out.println("Price is invalid in line: " + line);
				throw new BadPriceException();
			}
		} catch (BadPriceException e) {
			semanticerr(x, e.getMessage(), line);
			// // System.out.println(e.getMessage());
		}
	}

	/**
	 * validating for missing price
	 * 
	 * @param entries
	 * @param line
	 * @param x
	 */
	private static void validateYearField(String[] entries, String line, File x) {
		String yearString = entries[entries.length - 1].trim();
		int temp = 0;

		try {

			if (yearString.isEmpty() || !yearString.matches("\\d{4}")) {
				// // // System.out.println("Year is missing in line: " + line);
				temp = 1;
				missing++;
				throw new missingFieldException("Missing Field - Year	");

			}
		} catch (missingFieldException e) {
			syntaxerr(x, e.getMessage(), line);
			// // System.out.println(e.getMessage());
		}
	}

	/**
	 * validating for invalid year
	 * 
	 * @param entries
	 * @param line
	 * @param x
	 */
	public static void badYear(String[] entries, String line, File x) {
		String yearString = entries[entries.length - 1].trim();
		int temp = 0;
		try {
			int year = Integer.parseInt(yearString);
			if (year < 1995 || year > 2010) {
				// // // System.out.println("Year is invalid in line: " + line);
				throw new BadYearException(line);
			}
		} catch (BadYearException e) {
			semanticerr(x, e.getMessage(), line);
			// // System.out.println(e.getMessage());
		}
	}

	public static boolean validateTotalFields(String[] entries, String line, File x) {
		int count = 0;
		if (line.contains("\"")) {

			for (String s : entries) {
				if (s.startsWith("\"")) {
					count++;
				} else if (count != 0 && s.endsWith("\"")) {
					break;
				} else if (count != 0) {
					count++;
				}
			}
		}
		try { // Validate if there are exactly 6 words separated by comma
			if (entries.length - count < 6) {
				// // // System.out.println("count " + count);
				// // // System.out.println("length " + entries.length);
				// // System.out.println("Invalid line: " + line + " (less than 6 entries)");
				throw new tooFewFieldsException();
			}
			if (entries.length - count > 6) {
				// // // System.out.println("count " + count);
				// // // System.out.println("length " + entries.length);
				// // System.out.println("Invalid line: " + line + " (More than 6 entries)");
				throw new tooManyFieldsException();
			}
		} catch (tooFewFieldsException e) {
			// // System.out.println(e.getMessage());
			syntaxerr(x, e.getMessage(), line);
			fewFields++;
			return false;
		} catch (tooManyFieldsException e) {
			// System.out.println(e.getMessage());
			syntaxerr(x, e.getMessage(), line);
			moreFields++;
			return false;
		}
		return true;
	}

	/**
	 * method for reading the file from the given file - part1_input_file
	 * 
	 * @param dr
	 */
	public static void readFile(File dr) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(dr + "/part1_input_file_names.txt"));
			String line;
			openPrinter();
			int i = Integer.parseInt(reader.readLine());
			for (int j = 0; j < i; j++) {
				line = dr + "/" + reader.readLine();
				try (BufferedReader csvReader = new BufferedReader(new FileReader(line))) {
					File file = new File(line);
					validate(file);
				} catch (Exception e) {
					continue;
				}
			}
			closePrinter();
			reader.close();
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
		System.out.println(
				"Unknown genres - " + unknowGenre + "\nFew Feilds errors - " + fewFields + "\nMore Feilds error - " + moreFields + "\nTotal Missing Fields error- " + missing+"\n\n");		
	}
/**
 * for writing object to serialized file.
 * @param line
 * @throws IOException
 */
	public static void writingObjectFile(String line) throws IOException {


		String[] entries = line.split(",");
		int temp = 0;
		String isCNN = entries[entries.length - 2].trim();

		if (temp == 0) {
			if (isCNN.equals("CCB"))
				//TODO - Name 
				cartoonsObjectPrinter.writeObject(new Book(entries[entries.length - 6], entries[entries.length - 6],
						entries[entries.length - 5], entries[entries.length - 4], entries[entries.length - 3],
						entries[entries.length - 2], entries[entries.length - 1]));
			else if (isCNN.equals("HCB"))
				hobbiesObjectPrinter.writeObject(new Book(entries[entries.length - 6], entries[entries.length - 6],
						entries[entries.length - 5], entries[entries.length - 4], entries[entries.length - 3],
						entries[entries.length - 2], entries[entries.length - 1]));
			else if (isCNN.equals("MTV"))
				moviesObjectPrinter.writeObject(new Book(entries[entries.length - 6], entries[entries.length - 6],
						entries[entries.length - 5], entries[entries.length - 4], entries[entries.length - 3],
						entries[entries.length - 2], entries[entries.length - 1]));
			else if (isCNN.equals("MRB"))
				musicObjectPrinter.writeObject(new Book(entries[entries.length - 6], entries[entries.length - 6],
						entries[entries.length - 5], entries[entries.length - 4], entries[entries.length - 3],
						entries[entries.length - 2], entries[entries.length - 1]));
			else if (isCNN.equals("NEB"))
				nostalgiaObjectPrinter.writeObject(new Book(entries[entries.length - 6], entries[entries.length - 6],
						entries[entries.length - 5], entries[entries.length - 4], entries[entries.length - 3],
						entries[entries.length - 2], entries[entries.length - 1]));
			else if (isCNN.equals("OTR"))
				oldObjectPrinter.writeObject(new Book(entries[entries.length - 6], entries[entries.length - 6],
						entries[entries.length - 5], entries[entries.length - 4], entries[entries.length - 3],
						entries[entries.length - 2], entries[entries.length - 1]));
			else if (isCNN.equals("SSM"))
				sportObjectPrinter.writeObject(new Book(entries[entries.length - 6], entries[entries.length - 6],
						entries[entries.length - 5], entries[entries.length - 4], entries[entries.length - 3],
						entries[entries.length - 2], entries[entries.length - 1]));
			else if (isCNN.equals("TPA"))
				trainsObjectPrinter.writeObject(new Book(entries[entries.length - 6], entries[entries.length - 6],
						entries[entries.length - 5], entries[entries.length - 4], entries[entries.length - 3],
						entries[entries.length - 2], entries[entries.length - 1]));
		}
	}

	/**
	 * creating objectwriters
	 */
	public static void openObjectWriter() {
		try {
			cartoonsObjectPrinter = new ObjectOutputStream(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Cartoons_Comics.csv.ser",
					true));
			hobbiesObjectPrinter = new ObjectOutputStream(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Hobbies_Collectibles.csv.ser",
					true));
			moviesObjectPrinter = new ObjectOutputStream(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Movies_TV_Books.csv.ser",
					true));
			musicObjectPrinter = new ObjectOutputStream(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Music_Radio_Books.csv.ser",
					true));
			nostalgiaObjectPrinter = new ObjectOutputStream(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Nostalgia_Electronic_Books.csv.ser",
					true));
			oldObjectPrinter = new ObjectOutputStream(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Old_Time_Radio_Books.csv.ser",
					true));
			sportObjectPrinter = new ObjectOutputStream(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Sports_Memorabilia.csv.ser",
					true));
			trainsObjectPrinter = new ObjectOutputStream(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Trains_Planes_Automobiles.csv.ser",
					true));
			semanticPrinter = new PrintWriter(new FileOutputStream(
					"/Users/vasuchampaneria/eclipse-workspace/Assignment_3/semantic_error_file.txt",
					true));
		} catch (Exception e) {
			System.out.println("Could not find one or more of the output files (Part 2)");
			System.exit(0);
		}
	}

	private static void closeObjectWriter() throws IOException {
		cartoonsObjectPrinter.close();
		hobbiesObjectPrinter.close();
		moviesObjectPrinter.close();
		musicObjectPrinter.close();
		nostalgiaObjectPrinter.close();
		oldObjectPrinter.close();
		sportObjectPrinter.close();
		trainsObjectPrinter.close();
		semanticPrinter.close();
		semanticPrinter.close();
	}

	/**
	 * validating for semantic errors
	 * 
	 * @param x
	 * @throws FileNotFoundException
	 */
	public static void validatePart2(File x) throws FileNotFoundException {

		String filename = x.toString();
		Scanner sc;
		sc = new Scanner(new FileInputStream(filename));
		String line;
		int c = 0;

		while (sc.hasNextLine()) {
			line = sc.nextLine();
			String entries[] = line.split(",");

			try {// Bad Year
				badYear(entries, line, x);
				// Bad ISBN
				badISBN(entries, line, x);
				// Bad Price
				badPrice(entries, line, x);
				// Write as per CNN
				writingObjectFile(line);
			} catch (Exception e) {
				continue;
			}
		}
	}

	/**
	 * method for part 1
	 */
	public static void do_part1() {
		try {
			File directoryPath = new File("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/Files");
			readFile(directoryPath);
		} catch (Exception e) {
			System.out.println("Error from main method - Sorry cannot read the file");
		}
	}

	/**
	 * method for part 2
	 */
	public static void do_part2() {
		try {
			File directoryPath = new File("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/src/InputFile");
			File[] allFile = directoryPath.listFiles();
			openObjectWriter();
			for (int i = 0; i < allFile.length; i++) {
				if (allFile[i].getName() != "semantic_error_file.txt") {
					validatePart2(allFile[i]);
				}
			}
			closeObjectWriter();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

/**
 * getting the filename and printing the records of that file which is passed
 * @param fileName
 * @return
 */
	public static int getRecords(String fileName) {
		int numberOfRecords=0;
		ObjectInputStream ois=null;
		//run through the desired file to get the amount of objects present in it
		try
		{
			ois = new ObjectInputStream(new FileInputStream(fileName));
				
			
			while(true){
					ois.readObject();	
					numberOfRecords++;
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File: " + fileName + " could not been found.");
		}
		catch(EOFException e)
		{
		}
		catch(Exception e)
		{
			System.out.println("Please delete the old binary files");
		}
		return numberOfRecords;
	}
/**
 * method for displaying records for the selected file.
 * @param path
 */
	public static void viewSelectedFile(String path)
	{
		int n=1;
		int currentPoint = 0;
		int length = 0;
		System.out.println(path);
		int records = getRecords(path);
		System.out.println(records);
		try{
			ObjectInputStream objPrinter = new ObjectInputStream(new FileInputStream(path));
			Book[] b = new Book[records];
			for(int i=0;i<records;i++)
			{
				b[i] = (Book)objPrinter.readObject();
			}

			while(n!=0)
			{
			
				System.out.println("How many records you want - ");
				Scanner sc = new Scanner(System.in);
				n = sc.nextInt();

				length = currentPoint+n;
				if(n>0)
				{
					for(int i = currentPoint;i<length;i++)
					{
						System.out.println(b[i].toString());
						currentPoint++;
						if(currentPoint==records)
						{
							System.out.println("End of the file");
							currentPoint=records-1;
							break;
						}
					}
				}
				else if(n<0)
				{
					for(int i = currentPoint;i>length;i--)
					{
						System.out.println(b[i].toString());
						currentPoint--;
						if(currentPoint==0)
						{
							System.out.println("Beginning of the file");
							currentPoint=-1;
							break;
						}
					}
				}
				else {
					n=0;
				}
			}
			objPrinter.close();

		}

		catch(EOFException e)
		{
			System.out.println("EOF of file");
		}
		catch(Exception e)
		{
			System.out.println("BOF of file");
		}
		}

/**
 * method for displaying serialized files and selecting an option from them
 * @return
 */
		public static String selectFile()
		{
			int index=0;
			Scanner kb=new Scanner(System.in);
			boolean exitSMenu=false;
			while(!exitSMenu) {
				System.out.println("\n-----------------------------");
				System.out.println("           Options");
				System.out.println("-----------------------------");
				System.out.println("1 Cartoons_Comics.csv.ser           ( "+getRecords("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Cartoons_Comics.csv.ser")+" records)");
				System.out.println("2 Hobbies_Collectibles.csv.ser      ( "+getRecords("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Hobbies_Collectibles.csv.ser")+" records)");
				System.out.println("3 Movies_TV.csv.ser                 ( "+getRecords("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Movies_TV_Books.csv.ser")+" records)");
				System.out.println("4 Music_Radio.csv.ser               ( "+getRecords("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Music_Radio_Books.csv.ser")+" records)");
				System.out.println("5 Nostalgia_Eclectic.csv.ser        ( "+getRecords("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Nostalgia_Electronic_Books.csv.ser")+" records)");
				System.out.println("6 Old_Time_Radio.csv.ser            ( "+getRecords("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Old_Time_Radio_Books.csv.ser")+" records)");
				System.out.println("7 Sports_Sports_Memorabilia.csv.ser ( "+getRecords("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Sports_Memorabilia.csv.ser")+" records)");
				System.out.println("8 Trains_Planes_Automobiles.csv.ser ( "+getRecords("/Users/vasuchampaneria/eclipse-workspace/Assignment_3/Trains_Planes_Automobiles.csv.ser")+" records)");
				System.out.println("9 Exit");
				System.out.println("-----------------------------");
				System.out.print("Enter the appropriate number: ");
				index=kb.nextInt();
				
				//selection menu
				
				if(index==1) {
					return "Cartoons_Comics.csv.ser";
				}else if(index==2) {
					return "Hobbies_Collectibles.csv.ser";
				}else if(index==3) {
					return "Movies_TV_Books.csv.ser";
				}else if(index==4) {
					return "Music_Radio_Books.csv.ser";
				}else if(index==5) {
					return "Nostalgia_Electronic_Books.csv.ser";
				}else if(index==6) {
					return "Old_Time_Radio_Books.csv.ser";
				}else if(index==7) {
					return "Sports_Memorabilia.csv.ser";
				}else if(index==8) {
					return "Trains_Planes_Automobiles.csv.ser";
				}else if(index==9) {
					System.out.println("Exiting the menu.");
					exitSMenu=true;
				}else {
					System.out.print("\nPlease enter an appropriate value.");
					exitSMenu=false;
				}
			}
			return "Nothing to return";	
			}
/**
 * part 3
 * method for displaying main menu
 */
			public static void do_part3() {
				Scanner kb=new Scanner(System.in);
				String s="";
				String pathString = "Cartoons_Comics.csv.ser";
				boolean exit=true;

				while(exit) {

					System.out.println("-----------------------------");
					System.out.println("          Main Menu");
					System.out.println("-----------------------------");
					System.out.println("v View the selected file: " + pathString);
					System.out.println("s Select a file to view");
					System.out.println("e Exit");
					System.out.println("-----------------------------");
					System.out.print("Enter your choice: ");

					s=kb.nextLine();
					if(s.equals("v")) {
						viewSelectedFile(pathString);

					}else if(s.equals("s")) {
						pathString = selectFile();
						System.out.println(pathString);
					}
					else if(s.equals("e")) {
						exit=false;
					}else {
						System.out.print("\nEnter the appropriate value: ");	
					}
				}
				kb.close();
			}

			public static void main(String args[]) throws IOException {
				do_part1();
				do_part2();
				do_part3();

			}
		}
