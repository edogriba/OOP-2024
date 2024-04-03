public class App {
    public static void main(String[] args) throws Exception {
        Box<Integer> intBox = new Box<Integer>();
        System.out.println("Is the box empty? " +intBox.isEmpty());
        intBox.addItem(1);
        System.out.println("Is the box empty? " +intBox.isEmpty());
        int i = intBox.returnItem();
        System.out.println(i);
        System.out.println("Is the box empty? " +intBox.isEmpty());

    }
}
