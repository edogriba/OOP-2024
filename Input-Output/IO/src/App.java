import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException  {
        // Example with StringWriter and StringReader
        try (StringWriter stringWriter = new StringWriter(); PrintWriter printWriter= new PrintWriter(stringWriter)) {
            // Writing data to StringWriter
            printWriter.print("Hello");
            printWriter.print(" ");
            printWriter.print("World!");

            String output = stringWriter.toString();
            System.out.println("Output from StringWriter");
            System.out.println(output);

            // Read forom a String using the StringReader
            StringReader stringReader= new StringReader(output);
            BufferedReader bufferedReader = new BufferedReader(stringReader); // buffered reader will read from string reader who will read from output we are having different layers -> encapulating

            System.out.println("\nReading data using StringReader in a buffered fashion:");
            String line;
            while ((line=bufferedReader.readLine())!=null) {
                System.out.println(line);
            }

        }
        catch (IOException eio) {
            eio.printStackTrace();
        }

        // Example with err

        System.err.println("This is an error message"); // should print in red even if in VSCode doesn't work
        
        // Example with inputStream

        System.out.println("\nGive me a string");
         
        /*byte [] buffer = new byte [4096];
        int correctReading = System.in.read(buffer);
        if (correctReading != -1) {
            System.out.print(new String(buffer));
        }*/

        // Reading and Writing files -> FileReader and FileWriter
        long start1 = System.nanoTime();
        try (Reader src = new FileReader("example.txt"); Writer dest = new FileWriter("output_example.txt");) {
            
            int in;
            while ((in = src.read())!= -1)  {
                dest.write(in);
            }

        }
        catch (IOException eio) {
            eio.printStackTrace();
        }
        long end1 = System.nanoTime();

        long start2 = System.nanoTime();
        try (Reader src = new FileReader("example.txt"); Writer dest = new FileWriter("output_examplebuff.txt");) {
            int in;
            char [] buffer = new char [4096];
            while ((in = src.read(buffer))!= -1)  {
                dest.write(buffer, 0, in);
            }
        }
        catch (IOException eio) {
            eio.printStackTrace();
        }
        long end2 = System.nanoTime();
        System.out.println("Copying file using 1 char at a time takes "+(end1-start1)+" nanoseconds");          
        System.out.println("Copying file using a buffer of 4096 char takes "+ (end2-start2)+" nanoseconds");
        //buffer is much faster

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.bin"))) {
            int intValue = 42;
            double doubleValue = 3.14159;
            boolean booleanValue = true;

            dos.writeInt(intValue);
            dos.writeDouble(doubleValue);
            dos.writeBoolean(booleanValue);
            System.out.println("Data has been written to the bin file ");

        }
        catch (IOException eio) {
            eio.printStackTrace();
        }
        try (DataInputStream dis = new DataInputStream(new FileInputStream("data.bin"))) {
            int intValue = dis.readInt();
            double doubleValue = dis.readDouble();
            boolean booleanValue = dis.readBoolean();

            System.out.println("Data has been read from the bin file ");
            System.out.println("int: " + intValue);
            System.out.println("double: " + doubleValue);
            System.out.println("boolean: "+ booleanValue);

        }
        catch (IOException eio) {
            eio.printStackTrace();
        }

        // Serialization

        class Person implements Serializable{
            private String name;
            private int age;
            private List<String> hobbies; 
            public Person(String name, int age, List<String> hobbies) {
                this.name = name;
                this.age = age;
                this.hobbies = hobbies;
            }
            public int getAge() {
                return this.age;
            }
            public String getName() {
                return this.name;
            }
            public List<String> getHobbies() {
                return this.hobbies;
            }
        }

        List<Person> somePeople = Arrays.asList(new Person("Gloria", 40, Arrays.asList("Singing", "Cooking")),
                                    new Person("Phil", 42, Arrays.asList("Running", "Do magic tricks")), 
                                    new Person("Cam", 40, Arrays.asList("Singing")),
                                    new Person("Manny", 13, Arrays.asList("Poetry", "Accounting")), 
                                    new Person("Luke", 13, Collections.emptyList())
                                    );
        try (ObjectOutputStream serializer = new ObjectOutputStream(new FileOutputStream("people.ser"))) {
            serializer.writeObject(somePeople);
        }
        catch (IOException eio) {
            eio.printStackTrace();

        }
        List<Person> peopleList = new ArrayList();
        try (ObjectInputStream deserializer = new ObjectInputStream(new FileInputStream("people.ser"))) {
            peopleList = (List<Person>)deserializer.readObject();
            peopleList.forEach(p->System.out.println(p.getName()));
        }
        catch (IOException | ClassNotFoundException eio) {
            eio.printStackTrace();
        }
        // Example of using FIles for reading a file -> this is the easiest way to read a file (like in Pyhton) -> all lines together

        Path filePath = Path.of("example.txt"); // this is a relative path 

        try {
            List<String> lines = Files.readAllLines(filePath);
            System.out.println("Lines read from the file:");
            lines.forEach(System.out::println);  // easier and faster       
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // We want to get all the lines of the file into a stream and perform some operations on them 

        try {

            // files reading with streams very likely at the exam
            // count how many occurrences wee have in the file for each word
            Map<String, Long> occurrences = Files.lines(filePath).flatMap(line -> Stream.of(line.split("\\s+"))).collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting())); // this will return a stream of lines
            System.out.println(occurrences);

            Path outFilepath = Path.of("word_counts.txt");

            StringBuilder content = new StringBuilder();
            for (Map.Entry<String, Long> entry : occurrences.entrySet()) {
                content.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");           
            }
            Files.write(outFilepath, content.toString().getBytes());
        }
        catch (IOException ioe) { 
            ioe.printStackTrace();
        }

        // String tokenizer example 
        System.out.println("\nThis is an example of StringTokenizer:");
        String sentence = "Hello world! This is a sample sentence";
        StringTokenizer tokenizer = new StringTokenizer(sentence, " ");

        // Iterate through tokens

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            System.out.println(token);
        }

        //Try it out
        Path peopleFilePath =Path.of("People.txt"); // Relative filepath in this case because the file is in the same directory of this file
        try {
            List<Person> people = Files.lines(peopleFilePath).map(line->{
                String []  parts = line.split(", ");
                String name =parts[0];
                int age = Integer.parseInt(parts[1]);
                List<String> hobbies = Arrays.asList(parts[2].split(" "));
                return new Person(name, age, hobbies);
            }).toList();
            people.forEach(p->System.out.println(p.getName()));

            // most popular hobby --> very similar to what we can get at the exam
            Map.Entry<String, Long> mostPopularHobby =people.stream().
                                                            flatMap(p->p.getHobbies()
                                                            .stream())
                                                            .collect(Collectors.groupingBy(hobby->hobby, Collectors.counting()))
                                                            .entrySet()
                                                            .stream()
                                                            .max(Map.Entry.comparingByValue())
                                                            .orElse(Map.entry("", 0L));
        
            // people with that hobby

            List<Person> peopleWithMostPopularHobby = people.stream()
                                                            .filter(person-> person.getHobbies().contains(mostPopularHobby.getKey()))
                                                            .collect(Collectors.toList());
            
            // oldest person having that hobby

            Optional <Person> oldestPerson = peopleWithMostPopularHobby.stream().max(Comparator.comparing(Person::getAge));

            // Youngest person having that hobby

            Optional <Person> youngestPerson = peopleWithMostPopularHobby.stream().min(Comparator.comparing(Person::getAge));

            System.out.println("People analysis");
            System.out.println("Most popular hobby: " + mostPopularHobby.getKey());
            oldestPerson.ifPresent(p->System.out.println("Oldest person " + p.getName()));
            youngestPerson.ifPresent(p->System.out.println("Youngest person "+ p.getName()));
        
            }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
