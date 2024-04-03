public class Box <T> {
    // This class can store any type of object 

    private T item; 

    public void Box() {
          item = null;
    }
    public void addItem(T item) {
        this.item = item;
    }
    public T returnItem() {
        T temp = item;
        this.item = null;
        return temp;
    }
    public boolean isEmpty() {
        if (this.item == null) {
            return true;
        }
        return false;
    }
}
