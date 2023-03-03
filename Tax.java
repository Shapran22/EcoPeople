import java.io.*;
import java.nio.charset.StandardCharsets;

class Tax {
    private final int ecoNumber;
    private final String pathToFile;

    Tax(String pathToFile, String ecoNumber) {
        this.pathToFile = pathToFile;
        this.ecoNumber = Integer.parseInt(ecoNumber);
    }

    String getPathToFolder() {
        return new File(pathToFile).getParent();
    }

    int getEcoNumber() {
        return ecoNumber;
    }

    int getHeatNumber() {
        return returnHeatNumber(pathToFile);
    }

    String getHeat() {
        return returnHeat();
    }

    int[] getId() {
        return returnCount("id");
    }

    String[] getName() {
        return returnNames();
    }

    int[] getWaterCount() {
        return returnCount("waterCount");
    }

    int[] getGasCount1() {
        return returnCount("gasCount1");
    }

    int[] getGasCount2() {
        return returnCount("gasCount2");
    }

    int[] getElectroCount1() {
        return returnCount("electroCount1");
    }

    int[] getElectroCount2() {
        return returnCount("electroCount2");
    }

    //Считываем входнума матрицу и делаем её типа StringBuilder
    private StringBuilder dataFromFile(String pathToFile) {
        StringBuilder dataFromFile = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(pathToFile), StandardCharsets.UTF_8))) {
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                dataFromFile.append(row);
                dataFromFile.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataFromFile;
    }

    //Считанную матрицу разбиваем на строки по "\n" и на столбцы по "\\|"
    private String[][] createMatrix(StringBuilder data) {
        String[] dataFromFileToStringArray = data.toString().split("\n");
        String[][] dataFromFileToStringMatrix = new String[dataFromFileToStringArray.length][];
        for (int i = 0; i < dataFromFileToStringArray.length; i++) {
            dataFromFileToStringMatrix[i] = dataFromFileToStringArray[i].split("\\|");
        }
        return dataFromFileToStringMatrix;
    }

    //Читаем файл и получаем разбитую по элементам матрицу исходных данных
    private String[][] createMatrixFromFile(String pathToFile) {
        StringBuilder dataFromFile = dataFromFile(pathToFile);
        return createMatrix(dataFromFile);
    }

    //Получаем сколько строк отведено под шапку
    private int returnHeatNumber(String pathToFile) {
        final int idColumn = 0;

        int heat = 0;
        for (String[] row : createMatrixFromFile(pathToFile)) {
            for (int i = 0; i < row[idColumn].length(); i++) {
                if (!Character.isDigit(row[idColumn].charAt(i))) {
                    heat++;
                    break;
                }
            }
        }
        return heat;
    }

    private String returnHeat() {
        String[] dataFromFileToStringArray = dataFromFile(pathToFile).toString().split("\n");
        return dataFromFileToStringArray[0];
    }

    //Возвращаем из шапки нужны нам "столбец"
    private int returnColumn(String column) {
        int columnIndex = 0;
        for (int i = 0; i < createMatrixFromFile(pathToFile)[0].length; i++) {//определяем индекс столбца
            if (createMatrixFromFile(pathToFile)[0][i].contains(column)) {
                columnIndex = i;
            }
        }
        return columnIndex;
    }

    //Возвращаем массив имён из исходного файла
    private String[] returnNames() {
        int nameIndex = returnColumn("name");
        String[] names = new String[createMatrixFromFile(pathToFile).length - getHeatNumber()];
        for (int i = 0; i < names.length; i++) {
            names[i] = createMatrixFromFile(pathToFile)[getHeatNumber() + i][nameIndex];
        }
        return names;
    }

    //Возвращаем массив счетов по имени счёта
    private int[] returnCount(String someCount) {
        int countIndex = returnColumn(someCount);
        int[] counts = new int[createMatrixFromFile(pathToFile).length - getHeatNumber()];
        for (int i = 0; i < counts.length; i++) {
            counts[i] =
                    Integer.parseInt(createMatrixFromFile(pathToFile)[getHeatNumber() + i][countIndex]);
        }
        return counts;
    }
}