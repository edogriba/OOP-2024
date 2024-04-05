import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {

        Set<String> cinnamonRolls = new HashSet<>();
        cinnamonRolls.add("Flour");
        cinnamonRolls.add("Milk");
        cinnamonRolls.add("Butter");
        cinnamonRolls.add("Sugar");
        cinnamonRolls.add("Salt");
        cinnamonRolls.add("Cinnamon");
        cinnamonRolls.add("Yeast");
        cinnamonRolls.add("Yeast");

        System.out.println(cinnamonRolls.toString());

        Set<String> atHome = new HashSet<>();
        atHome.add("Flour");
        atHome.add("Eggs");
        atHome.add("Butter");
        atHome.add("Cookies");
        atHome.add("Salt");
        atHome.add("Cocoa powder");
        atHome.add("Raisin");
        atHome.add("Almonds");

        System.out.println(atHome.toString());

        Set<String> grocerySet = new HashSet<String>();

        cinnamonRolls.forEach(
            x ->
            {
                if (!(atHome.contains(x))) {
                    grocerySet.add(x);
                }
            }
        );
        grocerySet.forEach(System.out::println);

        



    }
}
