package aed.trees;

// w(n) corresponde ao número de ponteiros "null" existente na sub-árvore que começa em "n", w(null) = 1
// w(n) = w(filho_esq(n) + w(filho_der(n)))


/*

Nestor:

W(n)
- Se n for null, então
        W(n) = W(null) = 1
- Se n for diferente de null, então
        W(n) = W(filho_esq(n)) + W(filho_der(n))
 */

import java.util.*;

class UAlgTreeNode<Key extends Comparable<Key>,Value> implements IUAlgTreeNode<Key, Value>
{
    public Key key;
    public Value value;
    public UAlgTreeNode<Key, Value> left;
    public UAlgTreeNode<Key, Value> right;
    public int size;
    public int weight;


    public UAlgTreeNode(Key key, Value value){
        this.value = value;
        this.key = key;
        this.left = null;
        this.right = null;
        this.size = 1;
        this.weight = 2;
    }

    @Override
    public IUAlgTreeNode<Key, Value> getLeft() {
        return this.left;
    }

    @Override
    public IUAlgTreeNode<Key, Value> getRight() {
        return this.right;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public Key getKey() {
        return this.key;
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    public void setKey(Key k){
        this.key = k;
    }

    public void setValue(Value value){
        this.value = value;
    }
}

public class UAlgTree<Key extends Comparable<Key>,Value> {

    private UAlgTreeNode<Key, Value> root;
    private boolean rodou;
    private Value value;

    public UAlgTree()
    {
        this.root = null;
        this.rodou = false;
    }

    public IUAlgTreeNode<Key,Value> getRoot()
    {
        return this.root;
    }

    public int size()
    {
        if (this.root == null) return 0;
        return this.root.size;
    }

    public int size(UAlgTreeNode<Key, Value> node){
        if (node == null){
            return 0;
        }

        return node.size;
    }


    // https://www.geeksforgeeks.org/dsa/inorder-traversal-of-binary-tree/
    // https://www.geeksforgeeks.org/dsa/inorder-traversal-of-binary-tree/
    public Iterable<Key> keys()
    {
        if (this.root == null) return null;

        ArrayList<Key> arr = new ArrayList<>();
        ordenarKeys(arr, this.root);

        return arr;
    }

    public void ordenarKeys(ArrayList<Key> arr, UAlgTreeNode<Key, Value> node){
        if (node == null){
            return;
        }

        ordenarKeys(arr, node.left);
        arr.add(node.getKey());
        ordenarKeys(arr, node.right);
    }

    // https://www.geeksforgeeks.org/dsa/inorder-traversal-of-binary-tree/
    public Iterable<Value> values()
    {
        if (this.root == null) return null;
        ArrayList<Value> arr = new ArrayList<>();
        KeysValue(arr, this.root);
        return arr;
    }

    public void KeysValue(ArrayList<Value> arr, UAlgTreeNode<Key, Value> node){
        if (node == null){
            return;
        }

        KeysValue(arr, node.left);
        arr.add(node.getValue());
        KeysValue(arr, node.right);
    }


    public UAlgTree<Key,Value> shallowCopy()
    {
        if (this.root == null) {
            return null;
        }

        UAlgTree<Key, Value> tree = new UAlgTree<>();
        tree.value = null;
        tree.rodou = false;

        tree.root = nodeCopy(this.root);

        return tree;
    }

    public UAlgTreeNode<Key, Value> nodeCopy(UAlgTreeNode<Key, Value> node){
        if (node == null) {
            return null;
        }

        UAlgTreeNode<Key, Value> nodecopy = new UAlgTreeNode<>(node.key, node.value);
        nodecopy.size = node.size;
        nodecopy.weight = node.weight;

        nodecopy.right = nodeCopy(node.right);
        nodecopy.left = nodeCopy(node.left);

        return nodecopy;
    }


    // TODO: Auto Balanceamento------------------------------------------------------------


    public int W(UAlgTreeNode<Key, Value> node){
        if (node == null) return 1;
        return node.weight;
    }

    public void Pesos(UAlgTreeNode<Key, Value> node){
        if (node == null) return;

        node.weight = W(node.left) + W(node.right);
    }

    public void Sizes(UAlgTreeNode<Key, Value> node){
        if (node == null) return;

        node.size = size(node.left) + size(node.right) + 1;
    }

    public UAlgTreeNode<Key, Value> AutoBalanceamento(UAlgTreeNode<Key, Value> node){
        if (node == null) return null;

        // Esquerta < 0.4 * Direita
        if (W(node.left) < 0.4 * W(node.right)){
            // Esquerda > 1.5 * direita
            if (W(node.right.left) > 1.5 * W(node.right.right)){
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            } else{
                node = rotateLeft(node);
            }

        }else if(W(node.right) < 0.4 * W(node.left)){

            // Direita > 1.5 * Esquerda
            if (W(node.left.right) > 1.5 * W(node.left.left)){
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            } else{
                node = rotateRight(node);
            }
        }

        return node;
    }

    // TODO: Inserção ------------------------------------------------------------

    public UAlgTreeNode<Key, Value> rotateLeft(UAlgTreeNode<Key, Value> leftNode){
        UAlgTreeNode<Key, Value> rightNode = leftNode.right;
        leftNode.right = rightNode.left;
        rightNode.left = leftNode;

        Pesos(leftNode);
        Sizes(leftNode);
        Pesos(rightNode);
        Sizes(rightNode);
        return rightNode;
    }

    public UAlgTreeNode<Key, Value> rotateRight(UAlgTreeNode<Key, Value> rightNode){
        UAlgTreeNode<Key, Value> leftNode = rightNode.left;
        rightNode.left = leftNode.right;
        leftNode.right = rightNode;

        Pesos(rightNode);
        Sizes(rightNode);
        Pesos(leftNode);
        Sizes(leftNode);
        return leftNode;
    }

    public void put(Key k, Value v)
    {
        if(v == null){
            delete(k);
            return;
        }

        this.root = put(this.root, k, v);
    }

    public UAlgTreeNode<Key, Value> put(UAlgTreeNode<Key, Value> node, Key k, Value v){
        if (node == null) return new UAlgTreeNode<>(k, v);


        int cmp = k.compareTo(node.key);
        if (cmp == 0){
            node.value = v;
            return node;
        }
        else if(cmp < 0){
            node.left = put(node.left, k, v);
        }else{
            node.right = put(node.right, k, v);
        }

        Pesos(node);
        Sizes(node);

        return AutoBalanceamento(node);
    }



    // TODO: Remoção ---------------------------------------------------------

    public void delete(Key k)
    {
        if (this.root == null) return;

        this.root = delete(this.root, k);
    }

    public UAlgTreeNode<Key, Value> delete(UAlgTreeNode<Key, Value> node, Key k){
        if (node == null) return null;

        int cmp = k.compareTo(node.key);
        if (cmp == 0){
            if(node.left == null){ // Caso em que é null no filho esquerdo
                node = node.right;
            }
            else if (node.right == null){ // Caso em que é null no filho direito
                node = node.left;
            } else{ // Caso em que nenhum é null

                UAlgTreeNode<Key, Value> menor = node.right;

                while(menor.left != null){
                    menor = menor.left;
                }

                node.key = menor.key;
                node.value = menor.value;

                node.right = delete(node.right, menor.key);

                Pesos(node);
                Sizes(node);

            }
        }else if(cmp < 0){
            node.left = delete(node.left, k);
        }else{
            node.right = delete(node.right, k);
        }

        Pesos(node);
        Sizes(node);

        return AutoBalanceamento(node);
    }


    // TODO: -- Pesquisa ------------------------------------------------------------------------


    private Value search(Key k, UAlgTreeNode<Key, Value> node){
        if (node == null) return null;

        Key node_key = node.getKey();

        int cmp = k.compareTo(node_key);
        if (cmp == 0){
            return node.getValue();
        }else if (cmp < 0){
            return search(k, node.left);
        }else {
            return search(k, node.right);
        }
    }

    public Value get(Key k)
    {
        if(this.root == null) return null;

        this.value = null;
        this.rodou = false;

        this.root = get(k, this.root);

        return this.value;
    }

    public UAlgTreeNode<Key, Value> get(Key k, UAlgTreeNode<Key, Value> node){
        if (node == null){
            return null;
        }

            int cmp = k.compareTo(node.key);
            if (cmp == 0) {
                this.value = node.value;
                return node;
            } else if (cmp < 0) {
                node.left = get(k, node.left);

                if (this.value != null && !rodou){
                    if (rotacaoDireitaSegura(node) || this.root == node){
                        node = rotateRight(node); // o rotation cuida dos pesos e sizes
                        this.rodou = true;
                    }
                }

            } else {
                node.right = get(k, node.right);

                if(this.value != null && !rodou){
                    if(rotacaoEsquerdaSegura(node) || this.root == node){
                        node = rotateLeft(node); // o ratate cuida dos pessos e sizes
                        this.rodou = true;
                    }
                }
            }


        return node;
    }

    public boolean contains(Key k)
    {
        return search(k, this.root) != null;
    }

    public boolean rotacaoDireitaSegura(UAlgTreeNode<Key, Value> p){
        UAlgTreeNode<Key, Value> n = p.left;
        if(W(p) <= 3.5 * W(n.right) && W(p) <= 3.5 * W(n.left) + W(n.right)){

                return true;
        }

        return false;
    }

    public boolean rotacaoEsquerdaSegura(UAlgTreeNode<Key, Value> p){
        UAlgTreeNode<Key, Value> n = p.right;
        if(W(p) <= 3.5 * W(n.left) &&  W(p) <= 3.5 * W(n.right) + W(n.left)){
            return true;
        }

        return false;
    }

    // TODO: Métodos para testes -----------------------------------------------------------

    public void bfs(){

        Queue<UAlgTreeNode<Key, Value>> queue = new LinkedList<>();
        if(this.root == null) return;

        queue.add(this.root);

        while (!queue.isEmpty()){
            UAlgTreeNode<Key, Value> node = queue.poll();
            System.out.print(node.key);
            if(node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
            System.out.println("");
        }
    }



    public int bfsMin (){
        if (this.root == null) return 0;

        int ans = 1;

        Queue<UAlgTreeNode<Key, Value>> queue = new LinkedList<>();
        queue.add(this.root);

        while (!queue.isEmpty()){
            int s = queue.size();

            for (int i = 0; i < s; i++){
                UAlgTreeNode<Key, Value> node = queue.poll();
                if (node.left == null && node.right == null) return ans;
                if(node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }

            ans++;
        }

        return 0;
    }

    public int dfsMax(){
        return dfsMax(this.root);
    }

    public int dfsMax(UAlgTreeNode<Key, Value> node){
        if (node == null) return 0;

        return Math.max(dfsMax(node.right), dfsMax(node.left)) + 1;
    }


    public float racio(int min, int max){
        return (float) min /max;
    }


}

