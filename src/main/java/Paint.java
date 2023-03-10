import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Paint {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("src/main/resources/about.txt"));
        StringBuilder about = new StringBuilder();

        while (scan.hasNext()) {
            about.append(scan.nextLine()).append("\n");
        }
        new Frame(about.toString());
  }
}
