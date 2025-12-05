package aed.trees;

public class main {

    public static void main(String[] args){
        UAlgTree<Integer, Integer> tree = new UAlgTree<>();
        int[] chaves = {15, 3, 22, 8, 29, 11, 19, 5, 26, 1};

        for(int k : chaves){
            tree.put(k, k);
        }

        for (int i : tree.values()){
            System.out.println("V-" + i);
        }

        tree.put(1, 10);
        tree.put(29, null);

        System.out.println("");


        for (int i : tree.values()){
            System.out.println("V-" + i);
        }


        //tree.bfs();
    }
}

// conserthehddemessage!
