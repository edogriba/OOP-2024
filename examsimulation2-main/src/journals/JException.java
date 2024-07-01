package journals;
@SuppressWarnings ("serial")
public class JException extends Exception {
	public JException () {
		super ("unknown reason");
	}
	public JException (String reason) {
		super (reason);
	}
}
