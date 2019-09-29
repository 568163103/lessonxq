package hearfirst.three.service;

import hearfirst.LowerCaseInputStream;

import java.io.*;

public class InputTest {


    public static void main(String[] args) throws IOException {
        int c;
        byte [] b = new byte[3];
        b[0]='c';
        InputStream in = new LowerCaseInputStream(new BufferedInputStream(new FileInputStream("D:\\test.txt")));
         while ((in.read(b,1,2)) !=-1){
             for (int i = 0; i <b.length ; i++) {
                 System.out.println("c=="+(char) b[i]);
             }
         }

         in.close();
    }
}
