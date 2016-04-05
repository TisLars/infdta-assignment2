package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by larsh on 31-3-2016.
 */
public class UserPreference {

    TreeMap<Integer, Float> userRatingsMap = new TreeMap();

    public int userId;

    public UserPreference(int userId, int movieId, float rating) {
        this.userId = userId;
        userRatingsMap.put(movieId, rating);
    }

    public void AddRating(int movieId, float rating) {
        userRatingsMap.put(movieId, rating);
    }

    public void setUserRatingsMap(TreeMap map) {
        userRatingsMap = map;
    }

    public TreeMap<Integer, Float> getUserRatingsMap() {
        return userRatingsMap;
    }

    public List<Integer> NonCommonProducts(UserPreference y) {
        List<Integer> movieIds = new ArrayList<>();

        for (Map.Entry<Integer, Float> entry : y.getUserRatingsMap().entrySet()) {
            if (!this.userRatingsMap.containsKey(entry.getKey())) {
                movieIds.add(entry.getKey());
            }
        }

        return movieIds;
    }

    public void PrintMap() {
        System.out.println("User " + userId + ":");
        for (Map.Entry<Integer, Float> entry : userRatingsMap.entrySet()) {
            System.out.println("\t" + entry.getKey() + "\t" + entry.getValue());
        }
    }
}
