package aed.tables;
import java.util.ArrayList;
import java.util.function.Function;

/*

Métodos a Fazer

resize - FEITO
construtor - FEITO
size() - FEITO
getMainCapacity - FEITO
getTotalCapacity - FEITO
getLoadFactor - FEITO
getDeletedRemoved - FEITO
getSubTable - FEITO
containsKey - FEITO
get - FEITO
put - FEITO
fastput - TESTANDO
delete - FEITO



 */

class UAlshBucket<Key,Value> implements IUAlshBucket<Key,Value> {
    //devem guardar no balde tudo o que for necessário para implementar
    //as funções públicas da interface. Mas podem também adicionar outros
    //campos e métodos (incluindo o construtor), caso o entendam.

    private Key key;
    private Value value;
    private int maxSharedTable;
    private int hc1;
    private int hc2;


    public UAlshBucket(Key key, Value value, int max){
        this.key = key;
        this.value = value;
        this.maxSharedTable = max;
        this.hc1 = -1;
        this.hc2 = -1;
    }

    @Override
    public Key getKey() {
        return this.key;
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    @Override
    public boolean isEmpty() {
        return this.key == null;
    }

    @Override
    public boolean isDeleted() {
        return this.value == null;
    }

    public int getMaxSharedTable(){
        return this.maxSharedTable;
    }

    public void setValue(Value v){
        this.value = v;
    }

    public void setMaxSharedTable(int m){
        this.maxSharedTable = m;
    }

    public void setKey(Key k){
        this.key = k;
    }

    public int getHc1(){
        return this.hc1;
    }

    public void setHc1(int hc1){
        this.hc1 = hc1;
    }

    public int getHc2(){
        return this.hc2;
    }

    public void setHc2(int hc2){
        this.hc2 = hc2;
    }
}

public class UAlshTable<Key,Value> {

    private int indexPrimos;
    private int naoApagado;
    private int sizeT[];
    private int capacidadeT[];

    private Function<Key, Integer> hashCode2;
    private UAlshBucket<Key, Value>[] T1;
    private UAlshBucket<Key, Value>[] T2;
    private UAlshBucket<Key, Value>[] T3;
    private UAlshBucket<Key, Value>[] T4;
    private UAlshBucket<Key, Value>[] T5;



    //mudei de ideais relativamente aos primos iniciais, iremos usar
    //37, 17, 11, 7, e 5. Esta mudança não tem qualquer impacto significativo
    private static final int[] primes = { 5, 7, 11, 17, 37, 79, 163, 331,
            673, 1361, 2729, 5471, 10949,
            21911, 43853, 87719, 175447, 350899,
            701819, 1403641, 2807303, 5614657,
            11229331, 22458671, 44917381, 89834777, 179669557
    };


    @SuppressWarnings("unchecked")
    public UAlshTable(Function<Key,Integer> hc2)
    {
        this.naoApagado = 0;
        this.indexPrimos = 4;
        this.hashCode2 = hc2;

        this.capacidadeT = new int[6];
        capacidadeT[0] = 0;
        for (int i = 1; i < 6; i++){
            capacidadeT[i] = primes[indexPrimos - i + 1];
        }

        this.T1 = new UAlshBucket[capacidadeT[1]];
        this.T2 = new UAlshBucket[capacidadeT[2]];
        this.T3 = new UAlshBucket[capacidadeT[3]];
        this.T4 = new UAlshBucket[capacidadeT[4]];
        this.T5 = new UAlshBucket[capacidadeT[5]];

        this.sizeT = new int[6];
        for (int i = 0; i < 6; i++){
            sizeT[i] = 0;
        }


    }

    @SuppressWarnings("unchecked")
    private void resize(int capacidade){

        System.out.println("->  Resize  <-");

        if (capacidadeT[1] < capacidade){ // TODO: Aumentar
            indexPrimos++;
        }else{
            indexPrimos--;
        }

        UAlshBucket<Key, Value>[] t1 = this.T1;
        UAlshBucket<Key, Value>[] t2 = this.T2;
        UAlshBucket<Key, Value>[] t3 = this.T3;
        UAlshBucket<Key, Value>[] t4 = this.T4;
        UAlshBucket<Key, Value>[] t5 = this.T5;

        int[] capacityt = this.capacidadeT;

        this.capacidadeT = new int[6];
        capacidadeT[0] = 0;
        for (int i = 1; i < 6; i++){
            capacidadeT[i] = primes[indexPrimos - i + 1];
        }

        this.T1 = new UAlshBucket[capacidadeT[1]];
        this.T2 = new UAlshBucket[capacidadeT[2]];
        this.T3 = new UAlshBucket[capacidadeT[3]];
        this.T4 = new UAlshBucket[capacidadeT[4]];
        this.T5 = new UAlshBucket[capacidadeT[5]];


        this.naoApagado = 0;

        this.sizeT = new int[6];
        for (int i = 0; i < 6; i++){
            sizeT[i] = 0;
        }

        reinserir(t1, capacityt[1]);
        reinserir(t2, capacityt[2]);
        reinserir(t3, capacityt[3]);
        reinserir(t4, capacityt[4]);
        reinserir(t5, capacityt[5]);
    }

    private void reinserir(UAlshBucket<Key, Value>[] T, int size){
        for (int i = 0; i < size; i++){
            if(T[i] == null || T[i].getValue() == null){
                continue;
            }
            fastPut(T[i].getKey(), T[i].getValue(), T[i].getHc1(), T[i].getHc2());
        }
    }



    public int hash(int hc1, int hc2, int tabela){
        return ((hc1 + tabela * hc2) & 0x7fffffff) % tablecapacidade(tabela);
    }



    public int size()
    {
        int soma = 0;
        for (int i = 1; i < 6; i++){
            soma = soma + this.sizeT[i];
        }
        return soma;
    }

    public int getMainCapacity()
    {
        return this.capacidadeT[1];
    }

    public int getTotalCapacity()
    {
        int soma = 0;
        for (int i = 1; i < 6; i++){
           soma = soma + capacidadeT[i];

        }
        return soma;
    }

    public float getLoadFactor()
    {
        if(size() == 0) return 0;
        return (float) (size() + naoApagado) / getTotalCapacity() ;
    }

    public int getDeletedNotRemoved()
    {
        return this.naoApagado;
    }

    public IUAlshBucket<Key,Value>[] getSubTable(int i)
    {
        if (i == 1) return T1;
        else if(i == 2) return T2;
        else if(i == 3) return T3;
        else if(i == 4) return T4;
        else if(i == 5) return T5;

        return null;
    }

    public boolean containsKey(Key k)
    {
        return get(k) != null;
    }

    @SuppressWarnings("unchecked")
    public Value get(Key k)
    {
        int hc1 = k.hashCode();
        int hc2 = hashCode2.apply(k);

        // ir buscar os indexes
        int[] indexs = new int[6];
        for (int i = 1; i < 6; i++){
            indexs[i] = hash(hc1,hc2, i);
        }

        // ir buscar os baldes
        UAlshBucket<Key, Value>[] baldes = new UAlshBucket[6];
        for (int i = 1; i < 6; i++){
            baldes[i] = indexBalde(i, indexs[i]);
        }

        // procurar o menor maxST
        int z = 10;
        for (int i = 1; i < 6; i++){
            int a;
            if (baldes[i] == null){
                a = 0;
            }else {
                a = baldes[i].getMaxSharedTable();
            }
            z = Math.min(z, a);
        }

        if(z == 0) return null;


        for (int i = z; i > 0; i--){
            if(baldes[i] == null || baldes[i].getKey() == null) continue;
            if (baldes[i].getValue() == null) continue;
            if (baldes[i].getHc1() != hc1) continue;
            if (baldes[i].getHc2() != hc2) continue;

            if (baldes[i].getKey().equals(k)){
                return baldes[i].getValue();
            }
        }

        return null;
    }


    @SuppressWarnings("unchecked")
    public void put(Key k, Value v) {
        if (v == null){
            delete(k);
            return;
        }

        if(size() >= 0.85 * this.capacidadeT[1]){
            resize(2 * this.capacidadeT[1]);
        }

        int hc1 = k.hashCode();
        int hc2 = hashCode2.apply(k);

        // ir buscar os indexes
        int[] indexs = new int[6];
        for (int i = 1; i < 6; i++){
            indexs[i] = hash(hc1,hc2, i);
        }

        // ir buscar os baldes associados aos indexes
        UAlshBucket<Key, Value>[] baldes = new UAlshBucket[6];
        for (int i = 1; i < 6; i++){
            baldes[i] = indexBalde(i, indexs[i]);
        }

        // Procurar menor z
        int z = 10;
        for (int i = 1; i < 6; i++){
            int a;
            if (baldes[i] == null){
                a = 0;
            }else {
                a = baldes[i].getMaxSharedTable();
            }
            z = Math.min(z, a);
        }

        if(z > 0) { // TODO: AtualizarG
            for (int i = z; i > 0; i--){
                if (baldes[i] == null || baldes[i].getKey() == null) continue;
                if (baldes[i].getHc1() != hc1) continue;
                if (baldes[i].getHc2() != hc2) continue;

                if (baldes[i].getKey().equals(k)){
                    if (baldes[i].getValue() == null){
                        naoApagado--;
                        alterarSize(i, 1);
                    }

                    baldes[i].setValue(v);
                    return;
                }
            }
        }

        // TODO: Inserir Novo elemento


        int j = 0;
        for (int i = 1; i < 6; i++){
            if (baldes[i] == null || baldes[i].getKey() == null){
                j = i;
                break;
            }
        }

        if (j == 0){
            resize(this.capacidadeT[1] * 2);
            fastPut(k, v, hc1, hc2);
            return;
        }

        if (baldes[j] == null){
            baldes[j] = criarBalde(j, indexs[j], null, null);
        }



        baldes[j].setValue(v);
        baldes[j].setKey(k);
        baldes[j].setHc1(hc1);
        baldes[j].setHc2(hc2);
        alterarSize(j, 1);

        for (int i = 1; i < 6; i++){
            if(baldes[i] != null) {
                if (j > baldes[i].getMaxSharedTable()) {
                    baldes[i].setMaxSharedTable(j);
                }
            }else{
                baldes[i] = criarBalde(i, indexs[i], null, null);
                baldes[i].setMaxSharedTable(j);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void fastPut(Key k, Value v)
    {
        int hc1 = k.hashCode();
        int hc2 = hashCode2.apply(k);

        fastPut(k, v, hc1, hc2);
    }

    @SuppressWarnings("unchecked")
    public void fastPut(Key k, Value v, int hc1, int hc2){
        // ir buscar os indexes
        int[] indexs = new int[6];
        for (int i = 1; i < 6; i++){
            indexs[i] = hash(hc1,hc2, i);
        }


        UAlshBucket<Key, Value>[] baldes = new UAlshBucket[6];
        for (int i = 1; i < 6; i++){
            baldes[i] = indexBalde(i, indexs[i]);
        }

        int z = 10;
        for (int i = 1; i < 6; i++){
            int a;
            if (baldes[i] == null){
                a = 0;
            }else {
                a = baldes[i].getMaxSharedTable();
            }

            z = Math.min(z, a);
        }


        int j = 0;
        for (int i = 1; i < 6; i++){
            if (baldes[i] == null){
                j = i;
                break;
            }else if (baldes[i].getKey() == null){
                j = i;
                break;
            }
        }

        if (j == 0){
            resize(this.capacidadeT[1] * 2);
            fastPut(k, v, hc1, hc2);
            return;
        }

        if (baldes[j] == null){
            baldes[j] = criarBalde(j, indexs[j], null, null);
        }

        baldes[j].setValue(v);
        baldes[j].setKey(k);
        baldes[j].setHc1(hc1);
        baldes[j].setHc2(hc2);
        alterarSize(j, 1);

        for (int i = 1; i < 6; i++){
            if(baldes[i] != null) {
                if (j > baldes[i].getMaxSharedTable()) {
                    baldes[i].setMaxSharedTable(j);
                }
            }else{
                baldes[i] = criarBalde(i, indexs[i], null, null);
                baldes[i].setMaxSharedTable(j);
            }
        }

    }


    @SuppressWarnings("unchecked")
    public void delete(Key k)
    {
        if (size() < this.capacidadeT[1] * 0.25 && indexPrimos > 4){
            resize(this.capacidadeT[1] / 2);
        }

        int hc1 = k.hashCode();
        int hc2 = hashCode2.apply(k);

        // ir buscar os indexes
        int[] indexs = new int[6];
        for (int i = 1; i < 6; i++){
            indexs[i] = hash(hc1,hc2, i);
        }
        UAlshBucket<Key, Value>[] baldes = new UAlshBucket[6];
        for (int i = 1; i < 6; i++){
            baldes[i] = indexBalde(i, indexs[i]);
        }

        int z = 10;
        for (int i = 1; i < 6; i++){
            int a;
            if (baldes[i] == null){
                a = 0;
            }else {
                a = baldes[i].getMaxSharedTable();
            }
            z = Math.min(z, a);
        }

        if (z == 0) return; // nada a remover

        int j = 0;
        for (int i = z; i > 0; i--){
            if (baldes[i] == null || baldes[i].getKey() == null) continue;
            if (baldes[i].getValue() == null) continue;
            if (baldes[i].getHc1() != hc1) continue;
            if (baldes[i].getHc2() != hc2) continue;

            if (baldes[i] != null && baldes[i].getValue() != null){
                if (baldes[i].getKey().equals(k)){
                    j = i;
                    break;
                }
            }
        }

        if (j == 0) return;

        baldes[j].setValue(null);
        alterarSize(j, -1);
        naoApagado++;
    }

    public Iterable<Key> keys()
    {
        ArrayList<Key> arr = new ArrayList<>();

        for (int i = 1; i < 6; i++){
            for (int j = 0; j < tablecapacidade(i); j++){
                UAlshBucket<Key, Value> balde = indexBalde(i, j);
                if(balde == null) continue;
                if (balde.getValue() == null) continue;

                arr.add(balde.getKey());
            }
        }
        return arr;
    }



    // TODO: métodos auxiliares ----------------------------------------------------------------------------------------


    public UAlshBucket<Key, Value> indexBalde(int tabela, int index){
        if(tabela == 1) return T1[index];
        else if(tabela == 2) return T2[index];
        else if(tabela == 3) return T3[index];
        else if(tabela == 4) return T4[index];
        else if(tabela == 5) return T5[index];

        return null;
    }



    public UAlshBucket<Key, Value> criarBalde(int tabela, int index, Key key, Value value){
        if(tabela == 1) return T1[index] = new UAlshBucket<>(key, value, 0);
        else if(tabela == 2) return T2[index] = new UAlshBucket<>(key, value, 0);
        else if(tabela == 3) return T3[index] = new UAlshBucket<>(key, value, 0);
        else if(tabela == 4) return T4[index] = new UAlshBucket<>(key, value, 0);
        else if(tabela == 5) return T5[index] = new UAlshBucket<>(key, value, 0);

        return null;
    }

    public int tablecapacidade(int tabela){
        if (tabela < 1 || tabela > 5) return 0;
        return capacidadeT[tabela];
    }


    public void alterarSize(int tabela, int x){
        if (tabela < 1 || tabela > 5) return;

        sizeT[tabela] = sizeT[tabela] + x;
    }
}




