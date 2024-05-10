import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws Exception {

        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);

        Integer[] numbers = {
            1, 2, 3, 4, 1
        };

        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        stringList.add("how");
        stringList.add("are");
        stringList.add("you");

        String [] txta = {"hello", "", "is", "it", "going", "hope", "it", "is", "going", "well", "isn't", "it"};
        List<Student> studentList = new LinkedList<>();
        
        studentList.add(new Student("Marco", 4, Arrays.asList(new Course("A1"), new Course("A2")), Optional.of(12)));
        studentList.add(new Student("Luca", 35, Collections.emptyList(), Optional.of(0)));
        studentList.add(new Student("Francesco", 72, Arrays.asList(new Course("F1"), new Course("ASD")), Optional.of(100)));
        studentList.add(new Student("Federica", 18, Collections.emptyList(), Optional.of(45)));
        studentList.add(new Student("Beatrice", 72, Arrays.asList(new Course("IAW")), Optional.of(23)));

        // Try to create and experiment with streams
        studentList.stream().map(Student::getAge).forEach(System.out::println);

        studentList.stream().map(Student::getCourses).forEach(System.out::println);

        studentList.stream().flatMap(s->s.getCourses().stream()).forEach(System.out::println);

        // Study collectors: collect takes as argument Collector<T, A, R> --> collect reduces a stream to a Collection

        class Acc {
            int n;
        }
        int n = values.stream().collect(Acc::new, (a, i) -> a.n += i, (a1, a2)->a1.n += a2.n).n;
        int c = Stream.of(numbers).collect(Acc::new, (a, i) -> a.n += i, (a1, a2)->a1.n += a2.n).n;

        System.out.println(n);
        System.out.println(c);
        
        /* This doesn't work 
        @SuppressWarnings("removal")
        Integer i = Stream.of(values).collect(()->new Integer(0), (Integer a, t) -> a = a + t, (a1, a2)->a1 = a1 + a2);
        /* This doesn't work 
        @SuppressWarnings("removal")
        Integer t = Stream.of(values).collect(()->new Integer(0), (b, c) -> b = b + c, (a12, a22) -> a12 + a22 );        // Supplier: creates the initial result container  // Accumulator: accumulates the elements // Combiner: combines the partial results
        */           
        
        /* This doesn't work 
        Student s = Stream.of(studentList).collect(()->new Student("", 0, Collections.emptyList()), (Student s, Student t)->s.setAge(s.getAge()+t.getAge()), (Student s, Student t)->s.setAge(s.getAge()+t.getAge()));
        */
        // A Collector must provide a supplier, an accumulator and a combiner

        /* This doesn't work  
        List<Integer> n = Stream.of(numbers).collect(LinkedList::new, List::Add, List::addAll);
        */
        
        // Let's try predefined collectors

        // 1) Summarizing collectors

        // 1.a) averagingInt

        double averageLength1 = stringList.stream().collect(averagingInt(String::length));
        System.out.println(averageLength1);


        double averageLength2 = Stream.of(txta).collect(averagingInt(String::length));
        System.out.println(averageLength2);

        double avgAge = studentList.stream().collect(averagingInt(Student::getAge));
        System.out.println(avgAge);

        // 1.b) maxBy

        // oldest by age and first in alphabetical order
        Optional<Student> oldest = studentList.stream().collect(maxBy(comparing(Student::getAge).thenComparing((s1, s2)-> s2.getName().compareTo(s1.getName()) ))); // oldest by Age sorted by alphabetical order
        System.out.println(oldest.isPresent() ? oldest.get().getName():"nulla");

        // 1.c) minby 
        Optional<Student> youngest = studentList.stream().collect(minBy(comparing(Student::getAge)));
        System.out.println(youngest.isPresent() ? youngest.get().getName():"nulla");

        // 2) Accumulating collectors

        // 2.a) toList()

        List<String> lista  = Stream.of(txta).collect(toList());
        System.out.println(lista);

        // 2.b) toSet()
    
        Set<Integer> numeri = Stream.of(numbers).collect(toSet());
        System.out.println(numeri);

        // 2.c) joining()

        String phrase = stringList.stream().collect(joining(" ", "\'", "?\'"));
        System.out.println(phrase);

        // 3) Grouping collectors

        // 3.a) partitioningBy

        Map<Boolean, List<Student>> partitioningStudentMap = studentList.stream().collect(partitioningBy(s-> s.getAge() > 18)); 
        for (Boolean key : partitioningStudentMap.keySet()) {
            System.out.print(key + ":");
            for (Student s : partitioningStudentMap.get(key)) {
                System.out.print(" " +s.getName());
            }
            System.out.println();
        }

        // 3.b) groupingBy

        Map<Integer, List<Student>> groupingStudentMap = studentList.stream().collect(groupingBy(Student::getAge));
        for (Integer key : groupingStudentMap.keySet()) {
            System.out.print(key);
            for (Student s : groupingStudentMap.get(key)) {
                System.out.print(" " +s.getName());
            }
            System.out.println();
        }     
        
        // Nested groupingBy

        Map<Integer,List<String>> byLength = Stream.of(txta).distinct().collect(groupingBy(String::length,()-> new TreeMap<>(reverseOrder()),toList()));
        for (Integer key : byLength.keySet()) {
            System.out.print(key);
            for (String s : byLength.get(key)) {
                System.out.print(" " + s);
            }
            System.out.println();
        }     

        // Counting

        Map<String, Long> frequency = Stream.of(txta).collect(groupingBy(w->w, counting()));
        for (String s : frequency.keySet()) {
            System.out.println( s + ": " + frequency.get(s));
        }
        
        // I wanna swap String and Long (this throws an error because you cannot create a map with duplicated keys)
        /*
        Map<Long, String> numbersFrequency = Stream.of(txta).collect(groupingBy(w->w, counting())).entrySet().stream().collect(toMap(Map.Entry::getValue, Map.Entry::getKey));
        for (Long s : numbersFrequency.keySet()) {
            System.out.println( s + ": " + numbersFrequency.get(s));
        }*/
        System.out.println("*******************************");
        // CollectingAndThen
        List<String> longestWords = Stream.of(txta).distinct().collect(collectingAndThen(groupingBy(String::length,()->new TreeMap<>(reverseOrder()), toList()), m -> m.entrySet().stream().limit(3).flatMap(e->e.getValue().stream()).collect(toList())));
        for ( String s: longestWords) {
            System.out.println(s);
        }

        //Map<String, Optional

        // I wanna create a Map<Long, List<String>>
        System.out.println("*******************************");

        Map <Student, Long> studentsAge = studentList.stream().collect(groupingBy(s->s, counting()));
        for (Student s : studentsAge.keySet()) {
            System.out.println(s + ": " + studentsAge.get(s));
        }
        /* for (Long l : frequencyListMap.keySet()) {
            System.out.println(l + ": ");
            for ( String s: frequencyListMap.get(l)) {
                System.out.println(s);
            }
        }*/
        
    }
}
