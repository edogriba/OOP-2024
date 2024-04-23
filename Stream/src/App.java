import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
        // Method 2 : straight off a list of parameters
        Stream<String> fruitsStream = Stream.of("Apple", "Orange", "Lemon" );
        fruitsStream.forEach(System.out::println);
        // Method 3: from a collection ( Sets, lists, queue) -->maps are not a collection -> so not valid
        List<Dummy<String>> dummyList = new LinkedList<>();
        dummyList.add(new Dummy<String>("a"));
        dummyList.add(new Dummy<String>("ab"));
        dummyList.add(new Dummy<String>("abc"));
        dummyList.add(new Dummy<String>("abcd"));
        Stream<Dummy<String>> dummyStream = dummyList.stream();
        dummyStream.forEach(x -> System.out.println(x.getDummy()));
    
        // Method 4: Source generation

        //Case a : create an empty string

        Stream emptyStream = Stream.empty();
        if (emptyStream.count() == 0) {
            System.out.println("ops, this stream is empty");
        }
        // Case b: use a Supplier to generate elements

        Stream <Double> doubleStream = Stream.generate(Math::random); // thisi is infinitely long
        doubleStream.limit(10).forEach(System.out::println);
         
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

        // Case c: Use a UnaryOperator interface and generate stream form
    
        Stream<Integer> by2CounterStream = Stream.iterate(0, x->x+2);
        by2CounterStream.limit(25).forEach(System.out::println);
    
        // Intermediate operations

        Stream<String> familyStream = Stream.of("mother", "father", "son", "niece", "uncle", "grandfather", "grandmother");
        familyStream.filter(x->x.contains("mn")).forEach(System.out::println);
        
        System.out.println("******************");
        // Given a stream of integers, skip first 2 elements, sort values, filter values bigger than hundred and smaller than 10, remove duplicates
        Stream<Integer> intStream = Stream.of(9, 34, 34, 42, 15, 8, 8, 235, 2, 42, 34);
        intStream.skip(2).sorted().filter(x->x>10 && x<100).distinct().forEach(System.out::println);
    }
}
