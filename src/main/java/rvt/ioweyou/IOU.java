package rvt.ioweyou;

import java.util.HashMap;

public class IOU {

    private final HashMap<String, Double> debts;

    public IOU() {
        this.debts = new HashMap<>();
    }

    public void setSum(String toWhom, double amount) {
        this.debts.put(toWhom, amount);
    }

    public double howMuchDoIOweTo(String toWhom) {
        return this.debts.getOrDefault(toWhom, 0.0);
    }
    public static void main(String[] args) {
        IOU mattsIOU = new IOU();

        mattsIOU.setSum("Arthur", 51.5);
        mattsIOU.setSum("Michael", 30);
        mattsIOU.setSum("Maksims", 13.5);

        System.out.println(mattsIOU.howMuchDoIOweTo("Arthur"));  
        System.out.println(mattsIOU.howMuchDoIOweTo("Michael")); 
        System.out.println(mattsIOU.howMuchDoIOweTo("Maksims"));
    }
}
