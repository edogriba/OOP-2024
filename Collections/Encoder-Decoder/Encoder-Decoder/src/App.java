import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class App {
    public static void main(String[] args) throws Exception {

        HashMap<Character, Character> enco = new HashMap<>();
        enco.put('a', '0');
        enco.put('e', '1');
        enco.put('i', '2');
        enco.put('o', '3');
        enco.put('u', '4');

        HashMap<Character, Character> deco = new HashMap<>();
        deco.put('0', 'a');
        deco.put('1', 'e');
        deco.put('2', 'i');
        deco.put('3', 'o');
        deco.put('4', 'u');

        for (HashMap.Entry<Character, Character> entry : enco.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        String secret = "Bears beets battlestar galactica";

        BiFunction<HashMap<Character, Character>, String, String> encoder;

        encoder = (encoding, str) -> {
            StringBuffer encrypted = new StringBuffer(str);
            for (int i=0; i <encrypted.length(); i++) {
                Character c = encrypted.charAt(i);
                if (encoding.containsKey(c)) {
                    encrypted.setCharAt(i, encoding.get(c));
                }
            }    
            return encrypted.toString();
        };

        String encrsecret = encoder.apply(enco, secret);
        System.out.println(encrsecret);


        BiFunction<HashMap<Character, Character>, String, String> decoder;

        decoder = (decoding, str) -> {
            StringBuffer newstring = new StringBuffer(str);
            for (int i=0; i<newstring.length(); i++) {
                Character c = newstring.charAt(i);
                if (decoding.containsKey(c)) {
                    newstring.setCharAt(i, decoding.get(c));
                }
            }
            return newstring.toString();
        };

        String decrsecret = decoder.apply(deco, encrsecret);
        System.out.println(decrsecret);
        
    }
}
