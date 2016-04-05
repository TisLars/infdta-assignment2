/**
 * Created by larsh on 5-4-2016.
 */
public class Deviation {

    private int userAmount;
    private float totalDifference;

    public Deviation(int users, float total) {
        userAmount = users;
        totalDifference = total;
    }

    public float calculateAverage() {
        if (userAmount == 0)
            return 0f;

        return totalDifference / userAmount;
    }

    public void updateDeviation() {
    }

    public void setTotalDifference(float t) {
        totalDifference = t;
    }

    public float getTotalDifference() {
        return totalDifference;
    }

    public void setUserAmount(int u) {
        userAmount = u;
    }

    public int getUserAmount() {
        return userAmount;
    }
}
