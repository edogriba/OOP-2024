import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws Exception {
    String input = "Please contanct john@example.com. If possible contact also rossi.paolo@gmail.com and matt.smith@yahoo.com.";
    String patternString = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
    Pattern emailPattern = Pattern.compile(patternString);
    Matcher emailMatcher =  emailPattern.matcher(input);

    while(emailMatcher.find()) {
        String email = emailMatcher.group();
        System.out.println(email);
    }

    }
}
