import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number:");
        int numColumns = Integer.parseInt(scanner.nextLine());
        try {
            if (numColumns <= 0) {
                System.err.println("Error: Number of independent variables must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input. Please enter a number.");
        }

        System.out.println("Enter the file path:");
        String filePath = scanner.nextLine();

        BigDecimal[][] originalMatrix = readBigDecimalMatrixFromFile(filePath, numColumns);
        if (originalMatrix != null) {

            for (BigDecimal[] row : originalMatrix) {
                for (BigDecimal value : row) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        }

        assert originalMatrix != null;
        BigDecimal[][] transformedMatrix = transformMatrix(originalMatrix);

        for (BigDecimal[] row : transformedMatrix) {
            for (BigDecimal value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        BigDecimal[] coefficients = solveSystem(transformedMatrix);
        printResults(coefficients);
    }

    public static BigDecimal[] solveSystem(BigDecimal[][] matrix) {
        int n = matrix.length;
        BigDecimal[] coefficients = new BigDecimal[n];

        // Perform Gaussian elimination
        for (int col = 0; col < n; col++) {
            for (int row = col + 1; row < n; row++) {
                BigDecimal factor = matrix[row][col].divide(matrix[col][col], 20, BigDecimal.ROUND_HALF_UP);
                for (int k = col; k < n + 1; k++) {
                    matrix[row][k] = matrix[row][k].subtract(factor.multiply(matrix[col][k]));
                }
            }
        }

        // Back-substitution to find coefficients
        for (int i = n - 1; i >= 0; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j < n; j++) {
                sum = sum.add(matrix[i][j].multiply(coefficients[j]));
            }
            coefficients[i] = matrix[i][n].subtract(sum).divide(matrix[i][i], 20, BigDecimal.ROUND_HALF_UP);
        }

        return coefficients;
    }

    public static void printResults(BigDecimal[] coefficients) {
        System.out.println("Results:");
        for (int i = 0; i < coefficients.length; i++) {
            System.out.printf("b%d = %.6f%n", i, coefficients[i]);
        }
    }

    public static BigDecimal[][] readBigDecimalMatrixFromFile(String filePath, int numCols) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int numRows = 0;

            // Count the number of rows in the file
            while (reader.readLine() != null) {
                numRows++;
            }

            // Reset the reader to read from the beginning
            reader.close();
            BufferedReader reader1 = new BufferedReader(new FileReader(filePath));

            BigDecimal[][] matrix = new BigDecimal[numRows][numCols];

            // Read matrix values from the file
            for (int i = 0; i < numRows; i++) {
                line = reader1.readLine();
                String[] values = line.split("\\s+"); // Split by whitespace

                if (values.length != numCols) {
                    System.err.println("Error: Incorrect number of columns at row " + (i + 1));
                    return null;
                }

                for (int j = 0; j < numCols; j++) {
                    matrix[i][j] = new BigDecimal(values[j]);
                }
            }

            return matrix;
        } catch (IOException e) {
            System.err.println("Error reading matrix from file: " + e.getMessage());
            return null;
        }
    }

    public static BigDecimal[][] transformMatrix(BigDecimal[][] originalMatrix) {
        int rows = originalMatrix.length - 1;
        int cols = originalMatrix[0].length;

        BigDecimal[][] transformedMatrix = new BigDecimal[cols][rows];
        transformedMatrix[0][0] = BigDecimal.valueOf(originalMatrix.length);

        for (int j = 0; j < cols; j++) {
            transformedMatrix[0][j + 1] = sum(originalMatrix, j);
        }

        for (int i = 0; i < cols - 1; i++) {
            transformedMatrix[i + 1][0] = sum(originalMatrix, i);
            for (int j = 1; j < rows; j++) {
                transformedMatrix[i + 1][j] = sumOfNumbers(originalMatrix, i, j - 1);
            }
        }

        return transformedMatrix;
    }

    public static BigDecimal sum(BigDecimal[][] matrix, int col) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal[] row : matrix) {
            sum = sum.add(row[col]);
        }
        return sum;
    }

    public static BigDecimal sumOfNumbers(BigDecimal[][] matrix, int col, int row) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal[] r : matrix) {
            sum = sum.add(r[col].multiply(r[row]));
        }
        return sum;
    }
}
