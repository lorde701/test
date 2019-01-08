import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Иванка on 13.10.2018.
 */
public class Main {

    static ArrayList<String> processed = new ArrayList<String>();

    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>> graph = new HashMap<String, HashMap<String, Integer>>();

        HashMap<String, Integer> items = new HashMap<String, Integer>();
        items.put("A", 6);
        items.put("B", 2);
        graph.put("start", items);
        items.clear();
        items.put("finish", 1);
        graph.put("A", items);
        items.clear();
        items.put("A", 3);
        items.put("finish", 5);
        graph.put("B", items);
        items.clear();
        graph.put("finish", items);

        HashMap<String, Integer> costs = new HashMap<String, Integer>();
        costs.put("A", 6);
        costs.put("B", 2);
        costs.put("finish", Integer.MAX_VALUE);

        HashMap<String, String> parents = new HashMap<String, String>();
        parents.put("A", "start");
        parents.put("B", "start");
        parents.put("finish", null);

        String node = findLowestCost(costs);
        while (node != null) {
            
        }

        System.out.println(findLowestCost(costs));
        System.out.println(findLowestCost(costs));
        System.out.println(findLowestCost(costs));



//        graph.put("start", (LinkedList<String>) Arrays.asList("A", "B"));
//        graph.put("A", (LinkedList<String>) Arrays.asList("finish", "pejjy"));
//        graph.put("bob", (LinkedList<String>) Arrays.asList("anuj", "pejjy"));

        int i = 0;
//        List<Integer> list = new ArrayList<Integer>();
//        for (int i = 0; i < 15; i++) {
//            Random random = new Random();
//            list.add(random.nextInt(20));
//            System.out.println(list.get(i));
//        }
//        System.out.println();
//        list = (List<Integer>) quickSort(list);
//        for (Integer i : list) {
//            System.out.println(i);
//        }
    }

    private static String findLowestCost(HashMap<String, Integer> costs) {
        int lowestCost = Integer.MAX_VALUE;
        String lowestCostNode = null;
        for (HashMap.Entry<String,Integer> it : costs.entrySet()) {
            if ((it.getValue() < lowestCost) && (!processed.contains(it.getKey()))) {
                lowestCost = it.getValue();
                lowestCostNode = it.getKey();
            }
        }
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            int cost =  costs.get(it);
//            if ((cost < lowestCost) && (!processed.contains(cost))) {
//                lowestCost = cost;
//            }
//        }
        processed.add(lowestCostNode);
        return lowestCostNode;
    }

    static List<?> quickSort(List<? extends Comparable> list) {
        if (list.size() < 2) {
            return list;
        }
        else {
            List<Comparable> right = new ArrayList<Comparable>();
            List<Comparable> left = new ArrayList<Comparable>();
            int currentIndex = new Random().nextInt(list.size());
            for (int i = 1; i < list.size(); i++) {
                int compare = list.get(currentIndex).compareTo(list.get(i));
                if (compare > 0)
                    right.add(list.get(i));
                else
                    left.add(list.get(i));
            }

            left = (List<Comparable>) quickSort(left);
            left.add(list.get(currentIndex));
            left.addAll((List<Comparable>) quickSort(right));
            return left;
        }
    }

}
