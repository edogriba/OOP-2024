package operations;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class Client {
    private String name;
    private int discount;
    private List<String> orderedStuff;
    private boolean ordered;
    private int totOrdered;
    private int maxExpense;

	private Map<String, Integer> eval;

	public Client(String name, int discount) {
		this.name = name;
		this.discount = discount;
        eval = new HashMap<>();
        orderedStuff = new LinkedList<>();
        ordered = false;
        totOrdered = 0;
        maxExpense = 0;
	}


	public void addDiscount(int discount) {
		this.discount += discount;
	}
	public String getName() {
		return name;
	}
	public int getDiscount() {
		return discount;
	}

    public void addEval(String s, int i) {
        eval.put(s, i);
    }
        
	public Map<String, Integer> getEval() {
		return eval;
	}

    public void addOrderedStuff(String s) {
        orderedStuff.add(s);
    }

    public List<String> getOrderedStuff() {
		return orderedStuff;
	}

    public void setOrdered() {
        ordered = true;
    }


    public boolean getOrdered() {
        return ordered;
    }

    public void addTotOrdered(int i) {
        totOrdered += i;
    }

    public int getTotOrdered() {
        return totOrdered;
    }   

    public void setMaxExpense(int i) {
        if (maxExpense < i) {
            maxExpense = i;
        }
    }

    public int getMaxExpense() {
        return maxExpense;
    }
}
