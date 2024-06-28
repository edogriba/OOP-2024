package it.polito.oop.books;

import java.util.List;


public class Assignment {

    private String ID;
    private ExerciseChapter ec;
    private double totScore;

    public Assignment(String ID, ExerciseChapter ec) {
        this.ID = ID;
        this.ec = ec;
        totScore = 0.0;
    }
    

    public String getID() {
        return ID;
    }

    public ExerciseChapter getChapter() {
        return ec;
    }

    public double addResponse(Question q,List<String> answers) {

        int N = (int) q.numAnswers();

        int FP = 0;

        int VP = 0;

        for (String s : answers) {
            if (q.getCorrectAnswers().contains(s)) {
                VP++;
            }
            else if (q.getIncorrectAnswers().contains(s))  {
                FP++;
            }
        }
        int FN = q.getCorrectAnswers().size() - VP;

        double points = (double) (N-FP-FN)/N;
        totScore += points;
        return points;
    }
    
    public double totalScore() {
        return totScore;
    }

}
