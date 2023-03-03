import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class FileOut {
    People people;

    FileOut(People people) {
        this.people = people;
    }

    private File writingToFile(File file) {
        try (BufferedWriter bufferedWriter =
                     Files.newBufferedWriter(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8)) {
            for (int i = 0; i < people.ecoPeople().length; i++) {
                bufferedWriter.write(people.ecoPeople()[i]);
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            e.getCause();
        }
        return file;
    }

    void createFile(String directory) {
        File file = new File(directory, "NewEcoPeople.csv");
        try {
            //записываем в файл код и создаём этот файл в директории
            boolean createdFile = writingToFile(file).createNewFile();
        } catch (IOException e) {
            e.getCause();
        }
    }
}
