import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

public class MainTests {

    //Проверка на printResults
    @Test
    public void testPrintResults() {
        // Redirect console output to a String
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BigDecimal[] coefficients = {new BigDecimal("1.23"), new BigDecimal("4.56"), new BigDecimal("7.89")};
        Main.printResults(coefficients);

        // Restore console output
        System.setOut(System.out);

        String actualOutput = outContent.toString().trim();
        String expectedOutput = "Results:\r\nb0 = 1.230000\r\nb1 = 4.560000\r\nb2 = 7.890000";

        assertEquals(expectedOutput, actualOutput);
    }

    //Проверка на readBigDecimalMatrixFromFile с правилен файл
    @Test
    public void testReadBigDecimalMatrixFromFile_correctFile() {
        String filePath = "C:\\Users\\RalitsaG\\OneDrive\\Documents\\test.txt";
        int numCols = 3;

        BigDecimal[][] expectedMatrix = {{BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3)}, {BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, {BigDecimal.valueOf(7), BigDecimal.valueOf(8), BigDecimal.valueOf(9)}};

        BigDecimal[][] actualMatrix = Main.readBigDecimalMatrixFromFile(filePath, numCols);

        assertArrayEquals(expectedMatrix, actualMatrix);
    }

    //Проверка на readBigDecimalMatrixFromFile с несъществуващ файл
    @Test
    public void testReadBigDecimalMatrixFromFile_nonexistentFile() {
        String filePath = "test_data/nonexistent_file.txt";
        int numCols = 3;

        BigDecimal[][] actualMatrix = Main.readBigDecimalMatrixFromFile(filePath, numCols);

        assertNull(actualMatrix);
    }

    // Тест за проверка на сумата на колона в матрицата
    @Test
    public void testSum() {
        BigDecimal[][] matrix = {
                {BigDecimal.valueOf(1), BigDecimal.valueOf(2)},
                {BigDecimal.valueOf(3), BigDecimal.valueOf(4)},
                {BigDecimal.valueOf(5), BigDecimal.valueOf(6)}
        };
        BigDecimal expectedSum = BigDecimal.valueOf(9);
        BigDecimal actualSum = Main.sum(matrix, 0);
        assertEquals(expectedSum, actualSum);
    }

//Проверка на transformMatrix
    @Test
    public void testTransformMatrix() {
        BigDecimal[][] originalMatrix = {{BigDecimal.valueOf(1142), BigDecimal.valueOf(1060), BigDecimal.valueOf(325), BigDecimal.valueOf(201)},
                {BigDecimal.valueOf(863), BigDecimal.valueOf(995), BigDecimal.valueOf(98), BigDecimal.valueOf(98)},
                {BigDecimal.valueOf(1065), BigDecimal.valueOf(3205), BigDecimal.valueOf(23), BigDecimal.valueOf(162)},
                {BigDecimal.valueOf(554), BigDecimal.valueOf(120), BigDecimal.valueOf(0), BigDecimal.valueOf(54)},
                {BigDecimal.valueOf(983), BigDecimal.valueOf(2896), BigDecimal.valueOf(120), BigDecimal.valueOf(138)},
                {BigDecimal.valueOf(256), BigDecimal.valueOf(485), BigDecimal.valueOf(88), BigDecimal.valueOf(61)}};

        BigDecimal[][] expectedTransformedMatrix = {{BigDecimal.valueOf(6), BigDecimal.valueOf(4863), BigDecimal.valueOf(8761), BigDecimal.valueOf(654), BigDecimal.valueOf(714)},
                {BigDecimal.valueOf(4863), BigDecimal.valueOf(4521899), BigDecimal.valueOf(8519938), BigDecimal.valueOf(620707), BigDecimal.valueOf(667832)},
                {BigDecimal.valueOf(8761), BigDecimal.valueOf(8519938), BigDecimal.valueOf(21022091), BigDecimal.valueOf(905925), BigDecimal.valueOf(1265493)},
                {BigDecimal.valueOf(654), BigDecimal.valueOf(620707), BigDecimal.valueOf(905925), BigDecimal.valueOf(137902), BigDecimal.valueOf(100583)}};
        BigDecimal[][] actualTransformedMatrix = Main.transformMatrix(originalMatrix);

        assertArrayEquals(expectedTransformedMatrix, actualTransformedMatrix);
    }

   // Проверка на Метод на Гаус с правилна матрица
    @Test
    public void testSolveSystem_correctMatrix() {
        BigDecimal[][] matrix = {{BigDecimal.valueOf(6), BigDecimal.valueOf(4863), BigDecimal.valueOf(8761), BigDecimal.valueOf(654), BigDecimal.valueOf(714)},
                {BigDecimal.valueOf(4863), BigDecimal.valueOf(4521899), BigDecimal.valueOf(8519938), BigDecimal.valueOf(620707), BigDecimal.valueOf(667832)},
                {BigDecimal.valueOf(8761), BigDecimal.valueOf(8519938), BigDecimal.valueOf(21022091), BigDecimal.valueOf(905925), BigDecimal.valueOf(1265493)},
                {BigDecimal.valueOf(654), BigDecimal.valueOf(620707), BigDecimal.valueOf(905925), BigDecimal.valueOf(137902), BigDecimal.valueOf(100583)}};
        BigDecimal[] expectedCoefficients = {new BigDecimal("6.70133653638910269530"), new BigDecimal("0.07836603673386545559"), new BigDecimal("0.01504133119934494389"), new BigDecimal("0.24605633258014773681")};

        BigDecimal[] actualCoefficients = Main.solveSystem(matrix);

        assertArrayEquals(expectedCoefficients, actualCoefficients);
    }
    // Тест за проверка на валиден вход за броя на независимите променливи
    @Test
    public void testValidNumColumnsInput() {
        String input = "2";
        assertEquals(2, Integer.parseInt(input));
    }

}
