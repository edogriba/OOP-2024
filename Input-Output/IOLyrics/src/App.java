public class App {
    public static void main(String[] args) throws Exception {
        IO io = new IO();
        io.readWriteEven("f1.txt", "f2.txt");

        KeyboardIO kio = new KeyboardIO();
        kio.readWriteText();
        kio.closeStreams();
    }
}
