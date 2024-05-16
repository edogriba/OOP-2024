import java.io.*;

public class IO {
    public void readWriteEven(String fin, String fout) {
        try {

            String s;
            int i =1;
            FileReader fr = new FileReader(fin);
            BufferedReader br = new BufferedReader(fr);
            FileOutputStream f =  new FileOutputStream(fout);
            PrintStream os = new PrintStream(f);

            while((s = br.readLine()) != null) {
                if (i% 2 == 0) { // accessing an even line 
                    os.print(s);
                }
                i++;

            }
            br.close(); // prima chiudere quello esterno 
            fr.close();
            f.close();
            os.close();
        } 
        catch (IOException e) {
            System.out.println("Error input/otput");
        }

    }
}
