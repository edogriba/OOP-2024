import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.*;
import java.util.stream.Stream;

public class App {
    static class Dummy<T> {
        private T dummyValue;

        public Dummy(T dummyValue) {
            this.dummyValue = dummyValue;
        }

        public T getDummy() {
            return dummyValue;
        }
    }

    
    public static void main(String[] args) throws Exception {
        // Method 1: using arrays
        String [] animals = {
            "Lion", "Monkey", "Dog", "Cat"
        };
        Stream<String> animalsStream = Arrays.stream(animals);
        animalsStream.forEach(System.out::println);
        System.out.println("*******************************************************");
        // Method 2 : straight off a list of parameters
        Stream<String> fruitsStream = Stream.of("Apple", "Orange", "Lemon" );
        fruitsStream.forEach(System.out::println);
        System.out.println("*******************************************************");
        // Method 3: from a collection ( Sets, lists, queue) -->maps are not a collection -> so not valid
        List<Dummy<String>> dummyList = new LinkedList<>();
        dummyList.add(new Dummy<String>("a"));
        dummyList.add(new Dummy<String>("ab"));
        dummyList.add(new Dummy<String>("abc"));
        dummyList.add(new Dummy<String>("abcd"));
        Stream<Dummy<String>> dummyStream = dummyList.stream();
        dummyStream.forEach(x -> System.out.println(x.getDummy()));
        System.out.println("*******************************************************");

        // Method 4: Source generation

        //Case a : create an empty string

        /*Stream emptyStream = Stream.empty();
        if (emptyStream.count() == 0) {
            System.out.println("ops, this stream is empty");
        }*/
        // Case b: use a Supplier to generate elements

        Stream <Double> doubleStream = Stream.generate(Math::random); // thisi is infinitely long
        doubleStream.limit(10).forEach(System.out::println);
        System.out.println("*******************************************************");

        // another example with Supplier

        Random random = new Random();
        int low = 97;  // 97 corresponds to 'a'
        int high = 122; // 122 corresponds to 'z'
        Stream <String> randomStrings = Stream.generate(
            () -> new StringBuilder()
            .append ((char) (random.nextInt(high-low)+low))
            .append ((char) (random.nextInt(high-low)+low))
            .append ((char) (random.nextInt(high-low)+low))
            .append ((char) (random.nextInt(high-low)+low))
            .toString()
        );
        randomStrings.limit(10).forEach(System.out::println);

        System.out.println("*******************************************************");

        // Case c: Use a UnaryOperator interface and generate stream form
    
        Stream<Integer> by2CounterStream = Stream.iterate(0, x->x+2);
        by2CounterStream.limit(25).forEach(System.out::println);
    
        System.out.println("*******************************************************");

        // Intermediate operations

        Stream<String> familyStream = Stream.of("mother", "father", "son", "niece", "uncle", "grandfather", "grandmother");
        familyStream.filter(x->x.contains("mn")).forEach(System.out::println);
        
        System.out.println("******************");
        // Given a stream of integers, skip first 2 elements, sort values, filter values bigger than hundred and smaller than 10, remove duplicates
        Stream<Integer> intStream = Stream.of(9, 34, 34, 42, 15, 8, 8, 235, 2, 42, 34);
        intStream.skip(2).sorted().filter(x->x>10 && x<100).distinct().forEach(System.out::println);
    
        System.out.println("*******************************************************");

        // Map example

        // Example 1: print all dummies using map

        Stream<Dummy<Integer>> dummies = Stream.of(new Dummy<Integer>(9), new Dummy<Integer>(8), new Dummy<Integer>(11),new Dummy<Integer>(10));
        dummies.map(Dummy::getDummy).forEach(System.out::println);
        System.out.println();

        System.out.println("*******************************************************");

        // Example 2: map each STring in an array of String of their length

        Stream<String> vegStream = Stream.of("carrot", "onion", "pumpkin");
        vegStream.map(String::length).distinct().sorted().forEach(System.out::println);

        System.out.println("*******************************************************");
        
        // Example 3: with doble mapping
        Stream<Dummy<String>> anotherDummyStream = dummyList.stream();
        anotherDummyStream.map(Dummy::getDummy).mapToInt(String::length).forEach(System.out::println);
        System.out.println();

        Stream<String> StringStream = Stream.of("mother", "mother", "daughter", "father", "son", "daughter", "hi", "onion", "glass");
        StringStream.filter(a -> a.length() > 4 ).sorted((a,b) -> (b.length()-a.length())).distinct().mapToInt(String::length).forEach(System.out::println);
        
        System.out.println("*******************************************************");
        
        
        HashMap<Character, Character> enco = new HashMap<>();
        enco.put('a', '0');
        enco.put('e', '1');
        enco.put('i', '2');
        enco.put('o', '3');
        enco.put('u', '4');
        
        Function<String, String> encoder;

        encoder = (str) -> {
            StringBuffer encrypted = new StringBuffer(str);
            for (int i=0; i <encrypted.length(); i++) {
                Character c = encrypted.charAt(i);
                if (enco.containsKey(c)) {
                    encrypted.setCharAt(i, enco.get(c));
                }
            }    
            return encrypted.toString();
        };

        Stream<String> encStringStream = Stream.of("efe", "tefo", "brofeli", "potfaueto", "couiefte", "cafife");

        encStringStream.map(encoder)
                        .filter(s-> {
                            int count=0;
                            for (int i=0; i<s.length(); i++) {
                                if (Character.isDigit(s.charAt(i))) {
                                    count++;
                                }
                            }
                            return count >2 ;
                        })
                        .forEach(System.out::println);

        System.out.println("*******************************************************");
        


        // Example 1: class Person contains name, age o fthe prson and a list of hobbies using flatmap dìfind all the unique hobbies



        class Person {
            private String name;
            private int age;
            private List<String>hobbies;


            public Person(int age) {
                this.age = age;
            }
            public Person(String name, int age, List<String> hobbies) {
                this.name = name;
                this.age = age;
                this.hobbies = hobbies;
            }
            public int getAge() {
                return this.age;
            }
            public List<String> getHobbies() {
                return hobbies;
            }
            public String getName() {
                return this.name;
            }
        }

        Person[] persone = {
            new Person("Alice", 37, Arrays.asList("Running","Meditating")),
            new Person("Bob", 24, Arrays.asList("Cooking", "Meditating")),
            new Person("Paul", 16, Arrays.asList("Singing", "Meditating")),
        };

        Stream<Person> personeStream = Arrays.stream(persone);
        personeStream.map(Person::getHobbies).flatMap(Collection::stream).distinct().forEach(System.out::println);
        
        System.out.println("*******************************************************");
        

        // Example 2 Flatmap
        // Given a list of sentences, generate a stream containing each word in the sentence 
        List<String> sentences = new LinkedList<String>();
        sentences.add("The quick brown fox");
        sentences.add("jumps over the lazy dog");
        sentences.add("The dog sleeps");
        sentences.stream().flatMap((sentence) -> { return Arrays.stream(sentence.split(" "));
        }).map(s->{
            StringBuffer sb = new StringBuffer(s);
            for (int i=0; i<sb.length(); i++) {
                if (Character.isUpperCase(sb.charAt(i))) {
                    sb.setCharAt(i, Character.toLowerCase(sb.charAt(i))); 
                } 
            }
            return sb.toString();
        }).distinct().forEach(System.out::println); // the and the are considered different words because it's case sensitive



        System.out.println("*******************************************************");
        // Example 1: a stream of numbers to reduce in the sum of even number
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sumOfEvenNumbers = numbers.stream().filter(x-> x%2 == 0).reduce(0, (n1, n2)->n1+n2);
        
        System.out.println(sumOfEvenNumbers);
        
        System.out.println("*******************************************************");

        // Example 2 concatenate single words in a sentence

        List<String> words = Arrays.asList("hi", "how", "are", "you");
        String sentence = words.stream().reduce("", (w1, w2) -> w1.isEmpty() ? w2 : w1 + " " + w2);

        System.out.println(sentence);
        
        System.out.println("*******************************************************");
        
        // Example 3 sum all ages of POerson in a list 

        List<Person> people = Arrays.asList(new Person(4), new Person(80), new Person(5), new Person(1), new Person(67), new Person(90));

        people.stream().mapToInt(Person::getAge).reduce(0, (a1, a2)-> a1 + a2);

        System.out.println();

        System.out.println("*******************************************************");

        // Example 1 Counting

        List<Integer> numbers2 = Arrays.asList(1,2,3,4,5,6);
        long count = numbers2.stream().collect(Collectors.counting());
        long count2 = numbers2.stream().count();
        long sum = numbers.stream().collect(Collectors.summingInt(Integer::new));
        System.out.println(count);
        System.out.println(count2);
        System.out.println(sum);
        
        System.out.println("*******************************************************");
        
        // Example 3 Summarizing

        IntSummaryStatistics intStats = numbers2.stream().collect(Collectors.summarizingInt(Integer::new));

        System.out.println(intStats);
        
        System.out.println("*******************************************************");

        // Example 4 Transform again the stream into a collection

        List<Integer> evenNumbers  = numbers.stream().filter(n->n%2==0).collect(Collectors.toList());
        System.out.println(evenNumbers);
        
        System.out.println("*******************************************************");

        // Example 5  joining strings

        Stream<String> words2 = Stream.of("hey", "how", "are", "you");
        String sentence2 = words2.collect(Collectors.joining(" ", "-", "?"));
        System.out.println(sentence2);
        class Persona {
            private String name;
            private int age;
            private List<String> hobbies; 
            public Persona(String name, int age, List<String> hobbies) {
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
        Persona [] arrayOfPersons = {new Persona("Gloria", 40, Arrays.asList("Singing", "Cooking")),
                                    new Persona("Phil", 42, Arrays.asList("Running", "Do magic tricks")), 
                                    new Persona("Cam", 40, Arrays.asList("Singing")),
                                    new Persona("Manny", 13, Arrays.asList("Poetry", "Accounting")), 
                                    new Persona("Luke", 13, Collections.emptyList())
                                    };

        // Grouping by age: version 1

        //Map<Integer, List<Persona>> personByAge= Arrays.stream(arrayOfPersons).collect(Collectors.groupingBy(Persona::getAge, TreeMap::new, Collectors.toList()));
        //System.out.println(personByAge);

        // GroupingBy: version3 use Collectors.mapping! the mapping is applied before grouping
        Map<Integer, List<String>> personByAge= Arrays.stream(arrayOfPersons).collect(Collectors.groupingBy(Persona::getAge, Collectors.mapping(Persona::getName, Collectors.toList())));
        System.out.println(personByAge);

        
        System.out.println("*******************************************************");
        
        // Try it out-group according to hobby --we need to

        // partition by example: Partition by having singing as an hobby
        Map<Boolean, List<String>> personsSinging = Arrays.stream(arrayOfPersons).collect(Collectors.partitioningBy(p-> p.getHobbies().contains("Singing"), Collectors.collectingAndThen(Collectors.mapping(Persona::getName, Collectors.toList()), LinkedList::new)));
        System.out.println(personsSinging);
        
        System.out.println("*******************************************************");
        
    }
}
