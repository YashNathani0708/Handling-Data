# Handling Data

This project is designed to read and validate data from CSV files, categorize the data based on genre, handle exceptions, and write the processed data to different files. It also includes serialization of the data for further processing.

## Features

- **Validation**: The program performs syntax and semantic validation on the input data.
- **Categorization**: Based on the genre, the program categorizes and writes the data into separate files.
- **Exception Handling**: Handles various exceptions like missing fields, unknown genres, bad ISBN, and more.
- **Serialization**: Serializes the validated and categorized data for further use.

## Getting Started

### Prerequisites

- Java 8 or above

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/YashNathani0708/Handling-Data.git
   cd Handling-Data
   ```

2. Open the project in your preferred IDE (e.g., Eclipse, IntelliJ IDEA).

3. Ensure the necessary directories and files are in place as per the code paths.

### Running the Program

1. Set up the input files:
   - Place the `part1_input_file_names.txt` file in the root directory.
   - The file should list the names of the CSV files to be processed.

2. Run the `driver` class.

### Example

The program reads the list of input file names, validates each file, and categorizes the data into separate CSV files based on the genre. It also logs syntax and semantic errors encountered during validation.

## Code Overview

### `driver` Class

- **Static Variables**: Declares `PrintWriter` and `ObjectOutputStream` instances for various categories.
- **openPrinter()**: Opens the print writers for the categorized files.
- **closePrinter()**: Closes the print writers.
- **syntaxerr() & semanticerr()**: Logs syntax and semantic errors respectively.
- **validate()**: Validates the input files for syntax errors.
- **readFile()**: Reads the list of input file names and processes each file.
- **writingObjectFile()**: Writes objects to serialized files.
- **openObjectWriter()**: Opens object writers for serialization.
- **closeObjectWriter()**: Closes object writers.

### Validation Methods

- **validateTotalFields()**: Validates the number of fields in each record.
- **validateYearField()**: Validates the year field.
- **validatePriceField()**: Validates the price field.
- **validateISBN()**: Validates the ISBN field.
- **validateCNN()**: Validates and categorizes based on the genre.
- **badYear(), badISBN(), badPrice()**: Checks for semantic errors in the year, ISBN, and price fields respectively.

## Exceptions

- **missingFieldException**: Thrown when a required field is missing.
- **tooFewFieldsException**: Thrown when there are too few fields in a record.
- **tooManyFieldsException**: Thrown when there are too many fields in a record.
- **unkownGenreException**: Thrown when an unknown genre is encountered.
- **BadIsbn10Exception & BadIsbn13Exception**: Thrown for invalid ISBN-10 and ISBN-13 formats respectively.
- **BadPriceException**: Thrown for invalid price values.
- **BadYearException**: Thrown for invalid year values.

## Contributors

- [Yash Nathani](https://github.com/YashNathani0708)
- Vasu Champaneria

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

For more information, visit the [project repository](https://github.com/YashNathani0708/Handling-Data).
