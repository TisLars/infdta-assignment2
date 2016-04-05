import java.io.IOException;
import java.util.*;

/**
 * Created by larsh on 5-4-2016.
 */
public class Program {

    public static void main(String[] args) {
        int userChoice = 186;
        int totalRecommendations = 5;

        /**
         * paste one of the following file:
         * ("../resources/userItem.data", ",")
         * ("../resources/u.data", "\t")
         */
        Map<Integer, Map<Integer, Float>> itemRatings = new TreeMap<>();

        DataFileReader reader = new DataFileReader();
        try {
            itemRatings = reader.readFile("../resources/u.data", "\t");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Fill map with deviations
         */
        Map<Integer, Map<Integer, Deviation>> deviationMap = new TreeMap<Integer, Map<Integer, Deviation>>();
        List<Integer> nonRatedItems = new ArrayList<>();
        List<Integer> ratedItems = new ArrayList<>();

        for (Map.Entry<Integer, Map<Integer, Float>> entry : itemRatings.entrySet()) {
            int i = entry.getKey();

            // add item id to ratedItems if user rated
            if (entry.getValue().containsKey(userChoice))
                ratedItems.add(i);
            else
                nonRatedItems.add(i);

            for (Map.Entry<Integer, Map<Integer, Float>> entry2 : itemRatings.entrySet()) {
                int j = entry2.getKey();

                // when the item is identical, skip this loop
                if (i == j)
                    continue;

                int userAmount = 0;
                float totalDifference = 0;

                for (Map.Entry<Integer, Float> user : entry2.getValue().entrySet()) {
                    int userId = user.getKey();
                    if (!entry.getValue().containsKey(userId))
                        continue;

                    totalDifference += user.getValue() - entry.getValue().get(userId);
                    userAmount++;
                }

                Deviation deviation = new Deviation(userAmount, totalDifference);

                if (deviationMap.get(i) == null) {
                    Map<Integer, Deviation> temp = new TreeMap<>();
                    temp.put(j, deviation);
                    deviationMap.put(i, temp);
                } else {
                    deviationMap.get(i).put(j, deviation);
                }
            }
        }

        Map<Integer, Float> recommendation = new TreeMap<>();

        for (int nr : nonRatedItems) {
            float res = 0;
            float totalUserCount = 0;
            for (int r : ratedItems) {
                res += (itemRatings.get(r).get(userChoice) + deviationMap.get(r).get(nr).calculateAverage()) * deviationMap.get(r).get(nr).getUserAmount();
                totalUserCount += deviationMap.get(r).get(nr).getUserAmount();
            }
            float rec = res / totalUserCount;
            if (rec > 5) rec = 5;
            recommendation.put(nr, rec);
        }

        //recommendation = sortByValue(recommendation);
        /*for (Map.Entry entry : recommendation.entrySet())
            System.out.println("Key: " + entry.getKey() + " -- value: " + entry.getValue());*/


        System.out.println("============== recommendations ==============");
        recommendation = sortByValue(generateTopResults(totalRecommendations, recommendation));
        /*for (Map.Entry entry : recommendation.entrySet())
            System.out.println("Key: " + entry.getKey() + " -- value: " + entry.getValue());*/
    }

    /**
     * source:
     * http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
     */
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static Map<Integer, Float> generateTopResults(int amount, Map<Integer, Float> map) {
        Map<Integer, Float> result = new HashMap<>();
        int i = 0;

        for (Map.Entry<Integer, Float> entry : map.entrySet()) {

            result.put(entry.getKey(), entry.getValue());

            i++;
            if (i == amount)
                break;
        }
        return result;
    }
}
