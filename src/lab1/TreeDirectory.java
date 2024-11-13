package lab1;

import java.io.File;
import java.util.Arrays;

public class TreeDirectory {
    public static void main(String[] args) {
        new TreeDirectory().dirTree("D:\\test");
    }

    private void dirTree(String folder) {
        File[] files = new File(folder).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("+-" + file.getName().toUpperCase());
                dirTree(file.getAbsolutePath());
                System.out.print("\t");
            } else {
                System.out.println("+-" + file.getName());
            }
        }
    }

}
