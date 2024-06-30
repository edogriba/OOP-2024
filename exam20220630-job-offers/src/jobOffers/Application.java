package jobOffers;

public class Application implements Comparable<Application>{

    private String candidate;
    private String position;

    public Application(String candidate, String position) {
        this.candidate = candidate;
        this.position = position;
    }

    public String getCandidate() {
        return candidate;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public int compareTo(Application a) {
        if (candidate.compareTo(a.getCandidate()) == 0) {
            return position.compareTo(a.getPosition());
        }
        else {
            return candidate.compareTo(a.getCandidate());
        }
    }

    @Override
    public String toString() {
        return candidate + ":" + position;
    }
}
