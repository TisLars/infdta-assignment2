import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by larsh on 31-3-2016.
 */
public class DataFileReader {

    Map<Integer, Map<Integer, Float>> readFile(String fileName, String delimiter) throws FileNotFoundException, IOException {

        Map<Integer, Map<Integer, Float>> result = new TreeMap<Integer, Map<Integer, Float>>();

        BufferedReader bf = new BufferedReader(new FileReader(new File(fileName)));
        String line = "";

        while ((line = bf.readLine()) != null) {
            String parts[] = line.split(delimiter);

            if (result.get(Integer.parseInt(parts[1])) == null) {
                Map<Integer, Float> temp = new TreeMap<Integer, Float>();
                temp.put(Integer.parseInt(parts[0]), Float.parseFloat(parts[2]));
                result.put(Integer.parseInt(parts[1]), temp);
            } else {
                Map<Integer, Float> temp = result.get(Integer.parseInt(parts[1]));
                temp.put(Integer.parseInt(parts[0]), Float.parseFloat(parts[2]));
                result.put(Integer.parseInt(parts[1]), temp);
            }
        }
        bf.close();
        return result;
    }
}
