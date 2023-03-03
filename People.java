import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class People extends Tax {
    private int[] id;
    private String[] name;
    private int[] waterCount;
    private int[] gasCount1;
    private int[] gasCount2;
    private int[] electroCount1;
    private int[] electroCount2;

    People(String pathToFile, String ecoNumber) {
        super(pathToFile, ecoNumber);
        this.id = super.getId();
        this.name = super.getName();
        this.waterCount = super.getWaterCount();
        this.gasCount1 = super.getGasCount1();
        this.gasCount2 = super.getGasCount2();
        this.electroCount1 = super.getElectroCount1();
        this.electroCount2 = super.getElectroCount2();
    }

    int[] getGasCount() {
        return sumDoubleCount(gasCount1, gasCount2);
    }

    int[] getElectroCount() {
        return sumDoubleCount(electroCount1, electroCount2);
    }

    String[] ecoPeople() {
        String[] ecoPeople = new String[getHeatNumber() + indexOfEcoPeople().length];
        ecoPeople[0] = getHeat(); //записываем шапку в первый жлемент массива
        int j = getHeatNumber();
        for (int i : indexOfEcoPeople()) {  //записываем данные экологичных людей
            ecoPeople[j] = id[i] + "|" + name[i] + "|" + waterCount[i] + "|" +
                    gasCount1[i] + "|" + gasCount2[i] + "|" + electroCount1[i] + "|" + electroCount2[i];
            j++;
        }
        return ecoPeople;
    }

    private int[] indexOfEcoPeople() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < waterCount.length; i++) {
            if (waterCount[i] < getEcoNumber() & getGasCount()[i] < getEcoNumber()
                    & getElectroCount()[i] < getEcoNumber()) {
                integerList.add(i);
            }
        }
        int[] indexOfEcoPeople = new int[integerList.size()]; //превращаем список в массив строк
        for (int i = 0; i < integerList.size(); i++) {
            indexOfEcoPeople[i] = integerList.get(i);
        }
        return indexOfEcoPeople;
    }

    private int[] sumDoubleCount(int[] count1, int[] count2) {
        int[] sumCount = new int[count1.length];
        for (int i = 0; i < sumCount.length; i++) {
            sumCount[i] = count1[i] + count2[i];
        }
        return sumCount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pathToFile = scanner.nextLine();         //путь к файлу
        String ecoNumberScanner = scanner.nextLine();   //критерий оценки экологичности

        People people = new People(pathToFile, ecoNumberScanner);

        FileOut file = new FileOut(people);
        file.createFile(people.getPathToFolder());
    }

}
