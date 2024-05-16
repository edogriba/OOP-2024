import java.io.*;

public class KeyboardIO {

    private InputStreamReader inputStreamReader;
    private BufferedReader reader;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter writer;

    public KeyboardIO () {
        // not in a try segment just building the object doesn't raise any error --> it's the operations that create errors
        inputStreamReader = new InputStreamReader(System.in); // System.in stream specialized in getting info from keyboard
        reader = new BufferedReader(inputStreamReader);

        outputStreamWriter = new OutputStreamWriter(System.out); // same thing as above
        writer = new BufferedWriter(outputStreamWriter);
    }

    public void readWriteText() {
        try {
            writer.write("Enter some text (type exit to quit)");
            writer.newLine();
            //writer.flush(); // Buffered writer not the easiest way to have simpole IO on the screen PrintStream it's easier 
            String inputLine;

            while (!(inputLine = reader.readLine()).equalsIgnoreCase("exit")) {
                writer.write("You entered; "+ inputLine);
                writer.newLine();
                //writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void closeStreams() {
        try {
            reader.close();
            inputStreamReader.close();
            writer.close();
            outputStreamWriter.close();
        } 
        catch (IOException e) {
            System.out.println("Error");
        }
    }

    
}
