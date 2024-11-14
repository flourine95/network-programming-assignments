package lab7.v1;

import java.util.StringTokenizer;

public class Test {
    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("Hello, World! How are you?"," ");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }
}
