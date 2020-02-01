import java.util.Arrays;

public class MyDSTemplates {

    public static void main(String args[]) {

        // SegmentTree check
        /*long arr[] = {1,3,2,8,7};
        SegmentTree segmentTree = new SegmentTree(arr);
        segmentTree.build(1, 0, arr.length-1);
        segmentTree.printTree();
        System.out.println(segmentTree.query(1, 0, arr.length-1, 1, 2));
        segmentTree.update(1, 0, arr.length-1, 0, 2);
        segmentTree.printTree();
        System.out.println(segmentTree.query(1, 0, arr.length-1, 0, 3));

        segmentTree.update(1, 0, arr.length-1, 0, 2, 1);
        segmentTree.printTree();
        System.out.println(segmentTree.query(1, 0, arr.length-1, 4)); */

        /* // BIT check
        long arr[] = {1,3,2,8,7};
        BIT bit = new BIT(arr);
        bit.printTree();
        System.out.println(bit.sum(1, 5)); */

        // SegmentTreeLazy check
        /* long arr[] = {1,3,2,8,7};
        SegmentTreeLazy stLazy = new SegmentTreeLazy(arr);
        stLazy.build(1, 0, arr.length-1);
        stLazy.printTree();
        System.out.println(stLazy.query(1, 0, arr.length-1, 0,4));
        stLazy.update(1, 0, arr.length-1, 0, 2, 12);
        stLazy.printTree();
        System.out.println(stLazy.query(1, 0, arr.length-1, 2, 2)); */

        /* //AvlTree Test
        AvlTree<Integer> tree = new AvlTree<>();
        tree.insert(0);
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(5);
        tree.insert(6);

        System.out.println(tree.getLowerBoundIndex(6));
        System.out.println(tree.contains(1));
        System.out.println(tree.contains(4));
        System.out.println(tree.getValueByIndex(3)); */

         // Treap tests
        Treap treap = new Treap();
        treap.insertLast(5);
        treap.insertLast(4);
        treap.insertLast(3);
        treap.insertLast(1);
        treap.insertLast(1);
        treap.insertLast(2);
        treap.insertAt(3, 9);
        treap.print();
        System.out.println(treap.isSorted());
        System.out.println(treap.getByIndex(3));
        treap.modify(4, 6);
        System.out.println(treap.getByIndex(4));
        treap.removeAt(5);
        treap.print();
        System.out.println(treap.sum(5, 5));

        treap.clear();
        treap.insert(5);
        treap.insert(4);
        treap.insert(6);
        treap.insert(3);
        treap.print();
        System.out.println(treap.isSorted());
        System.out.println(treap.getByIndex(4));
        System.out.println(treap.sum(1,4));
        treap.remove(4);
        treap.print();

        System.out.println(treap.sum(1,3));
        System.out.println(treap.contains(4) + " " + treap.contains(3));
        Treap.Pair pair = treap.lowerBound(4);
        System.out.println(pair.val + " " +  pair.index);

        pair = treap.upperBound(3);
        System.out.println(pair.val + " " +  pair.index);
    }
}

class BIT {
    int size;
    long[] table;

    public BIT(long[] arr) {
        // 1 based indexing
        table = new long[arr.length + 1];
        this.size = arr.length + 1;

        for (int i = 0; i < arr.length; i++) {
            update(i + 1, arr[i]);
        }
    }

    // 1 based index
    void update(int i, long delta) {
        while (i < size) {
            table[i] += delta;
            i += Integer.lowestOneBit(i);
        }
    }

    // 1 based index
    long sum(int i) {
        long sum = 0L;
        while (i > 0) {
            sum += table[i];
            i -= Integer.lowestOneBit(i);
        }
        return sum;
    }

    // 1 based index
    long sum(int l, int r) {
        return sum(r) - sum(l - 1);
    }

    void printTree() {
        System.out.println();
        for (int i = 0; i < table.length; i++) {
            System.out.print(" " + table[i] + " ");
        }
        System.out.println();
    }
}

// id -> 1 index
// t1, tr, l, r -> 0 index
class SegmentTree {
    int n;
    long[] arr;
    long[] t;

    public SegmentTree(long[] arr) {
        this.arr = arr;
        this.n = arr.length;
        t = new long[4 * n];
    }

    long combine(long lft, long rgt) {
        return lft + rgt;
    }

    void build(int id, int tl, int tr) {
        if (tl == tr) {
            t[id] = arr[tl];
            return;
        }

        int tm = (tl + tr) / 2;
        build(id << 1, tl, tm);
        build(id << 1 | 1, tm + 1, tr);
        t[id] = combine(t[id << 1], t[id << 1 | 1]);

    }

    void update(int id, int tl, int tr, int pos, long val) {
        if (tl == tr) {
            t[id] = val;
            return;
        }

        int tm = (tl + tr) / 2;
        if (pos <= tm) {
            update(id << 1, tl, tm, pos, val);
        } else {
            update(id << 1 | 1, tm + 1, tr, pos, val);
        }

        t[id] = combine(t[id << 1], t[id << 1 | 1]);
    }

    void update(int id, int tl, int tr, int l, int r, int val) {
        if (l > r) {
            return;
        }

        if (tl == tr) {
            t[id] = val;
            return;
        }

        int tm = (tl + tr) / 2;
        update(id << 1, tl, tm, l, Math.min(r, tm), val);
        update(id << 1 | 1, tm + 1, tr, Math.max(l, tm + 1), r, val);

        t[id] = combine(t[id << 1], t[id << 1 | 1]);
    }

    long query(int id, int tl, int tr, int l, int r) {
        if (l > r) {
            return 0;
        }

        if (l <= tl && r >= tr) {
            return t[id];
        }

        int tm = (tl + tr) / 2;
        return combine(query(id << 1, tl, tm, l, Math.min(r, tm)),
                query(id << 1 | 1, tm + 1, tr, Math.max(l, tm + 1), r));
    }

    long query(int id, int tl, int tr, int pos) {
        if (tl == tr) {
            return t[id];
        }

        int tm = (tl + tr) / 2;
        if (pos <= tm) {
            return query(id << 1, tl, tm, pos);
        } else {
            return query(id << 1 | 1, tm + 1, tr, pos);
        }
    }

    void printTree() {
        System.out.println();
        for (int i = 0; i < t.length; i++) {
            System.out.print(" " + t[i] + " ");
        }
        System.out.println();
    }
}

// id -> 1 index
// t1, tr, l, r -> 0 index
class SegmentTreeLazy {
    int n;
    long[] arr;
    long[] t;
    long[] lazy;
    boolean[] marked;

    public SegmentTreeLazy(long[] arr) {
        this.arr = arr;
        this.n = arr.length;
        t = new long[4 * n];
        lazy = new long[4 * n];
        marked = new boolean[4 * n];
    }

    long combine(long lft, long rgt) {
        return Math.max(lft, rgt);
    }

    void build(int id, int tl, int tr) {
        if (tl == tr) {
            t[id] = arr[tl];
            return;
        }

        int tm = (tl + tr) / 2;
        build(id << 1, tl, tm);
        build(id << 1 | 1, tm + 1, tr);
        t[id] = combine(t[id << 1], t[id << 1 | 1]);
    }

    void push(int id) {
        if (marked[id]) {
            t[id << 1] = apply(t[id << 1], lazy[id]);
            lazy[id << 1] = apply(lazy[id << 1], lazy[id]);

            t[id << 1 | 1] = apply(t[id << 1 | 1], lazy[id]);
            lazy[id << 1 | 1] = apply(lazy[id << 1 | 1], lazy[id]);
            marked[id << 1] = marked[id << 1 | 1] = true;

            lazy[id] = 0;
            marked[id] = false;
        }
    }

    // can change it to assignment
    long apply(long currentVal, long newVal) {
        return currentVal + newVal;
        // return newVal;
    }

    // Adding on segments
    void update(int id, int tl, int tr, int l, int r, int val) {
        if (l > r) {
            return;
        }

        if (l == tl && tr == r) {
            t[id] = apply(t[id], val);
            lazy[id] = apply(lazy[id], val);
            marked[id] = true;
            return;
        }

        push(id);
        int tm = (tl + tr) / 2;
        update(id << 1, tl, tm, l, Math.min(r, tm), val);
        update(id << 1 | 1, tm + 1, tr, Math.max(l, tm + 1), r, val);
        t[id] = combine(t[id << 1], t[id << 1 | 1]);
    }

    long query(int id, int tl, int tr, int l, int r) {
        if (l > r) {
            return Long.MIN_VALUE;
        }

        if (l <= tl && tr <= r) {
            return t[id];
        }

        push(id);
        int tm = (tl + tr) / 2;
        return combine(query(id << 1, tl, tm, l, Math.min(r, tm)),
                query(id << 1 | 1, tm + 1, tr, Math.max(l, tm + 1), r));
    }


    void printTree() {
        System.out.println();
        for (int i = 0; i < t.length; i++) {
            System.out.print(" " + t[i] + " ");
        }
        System.out.println();
    }
}

class AvlTree<T extends Comparable> {

    private class Node<T> {
        T val;
        Node<T> left;
        Node<T> right;
        long leftCount;
        long rightCount;
        long height;
        long count;

        public Node(T val) {
            this.val = val;
            this.left = null;
            this.right = null;
            this.leftCount = 0;
            this.rightCount = 0;
            this.height = 1;
            this.count = 1;
        }
    }

    private Node<T> root;
    private long size;

    public AvlTree() {
        root = null;
        size = 0;
    }

    // index of element equal to the data or first element greater than data
    public long getLowerBoundIndex(T data) {
        Node<T> currNode = root;
        long index = 0;

        while (currNode != null) {
            int comparedValue = data.compareTo(currNode.val);
            if (comparedValue < 0) {
                currNode = currNode.left;
            } else if (comparedValue > 0) {
                index += (currNode.count + currNode.leftCount);
                currNode = currNode.right;
            } else {
                index += (currNode.leftCount);
                break;
            }
        }
        return index;
    }

    public long getHeight(Node<T> root) {
        if (root == null) return 0;
        return root.height;
    }

    public T getValueByIndex(long index) {
        Node<T> currNode = root;
        long currCount = 0;

        while (currNode != null) {
            if (currNode.left == null) {
                if (currNode.count + currCount > index) return currNode.val;
                else {
                    currCount += currNode.count;
                    currNode = currNode.right;
                }
            } else {
                if (currCount + currNode.leftCount > index) currNode = currNode.left;
                else if (currCount + currNode.leftCount + currNode.count > index)
                    return currNode.val;
                else {
                    currCount += currNode.count + currNode.leftCount;
                    currNode = currNode.right;
                }
            }
        }

        return null;
    }

    public boolean contains(T data) {
        Node<T> currNode = root;
        while (currNode != null) {
            int comparedValue = data.compareTo(currNode.val);
            if (comparedValue == 0) {
                return true;
            }

            if (comparedValue < 0) {
                currNode = currNode.left;
            } else {
                currNode = currNode.right;
            }
        }
        return false;
    }

    public long getSize() {
        return this.size;
    }

    public void insert(T data) {
        root = insert(root, data);

    }

    public void remove(T data) {
        root = remove(root, data);
    }

    private Node<T> remove(Node<T> root, T data) {
        if (root == null) {
            return null;
        }

        int comparedValue = data.compareTo(root.val);
        if (comparedValue < 0) {
            root.left = remove(root.left, data);
        } else if (comparedValue > 0) {
            root.right = remove(root.right, data);
        } else {
            size -= root.count;
            if (root.left == null && root.right == null) return null;
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            Node<T> temp = minValue(root.right);
            root.val = temp.val;
            root.count = temp.count;
            root.right = remove(root.right, temp.val);
            size += root.count;

        }

        // Update count
        updateCount(root);
        updateHeight(root);

        //Balancing
        long diff = getHeightDiff(root);
        root = balanceRemove(root, diff);
        return root;
    }

    private Node<T> balanceRemove(Node<T> root, long diff) {
        if (diff > 1) {
            if (getHeightDiff(root.left) >= 0) {
                // left-left case
                root = rightRotate(root);
            } else {
                // left-right case
                root.left = leftRotate(root.left);
                root = rightRotate(root);
            }
        } else if (diff < -1) {
            if (getHeightDiff(root.right) <= 0) {
                // right right case
                root = leftRotate(root);
            } else {
                // right left case
                root.right = rightRotate(root.right);
                root = leftRotate(root);
            }
        }
        return root;
    }

    private Node<T> minValue(Node<T> root) {
        Node<T> currNode = root;
        while (currNode.left != null) {
            currNode = currNode.left;
        }

        return currNode;
    }

    private Node<T> insert(Node<T> root, T data) {
        if (root == null) {
            size++;
            return new Node<>(data);
        }

        int comparedValue = data.compareTo(root.val);
        if (comparedValue < 0) root.left = insert(root.left, data);
        else if (comparedValue > 0) root.right = insert(root.right, data);

        // update count for the root
        updateCount(root);
        updateHeight(root);

        //Balancing
        long diff = getHeightDiff(root);
        root = balanceInsert(root, data, diff);

        return root;

    }

    private Node<T> balanceInsert(Node<T> root, T data, long diff) {
        if (diff > 1) {
            if (data.compareTo(root.left.val) < 0) {
                // left left case
                root = rightRotate(root);
            } else if (data.compareTo(root.left.val) > 0) {
                // left right case
                root.left = leftRotate(root.left);
                root = rightRotate(root);
            }
        } else if (diff < -1) {
            if (data.compareTo(root.right.val) > 0) {
                // right right case
                root = leftRotate(root);
            } else if (data.compareTo(root.right.val) < 0) {
                // right left case
                root.right = rightRotate(root.right);
                root = leftRotate(root);
            }
        }
        return root;
    }

    private Node<T> rightRotate(Node<T> currRoot) {
        Node<T> newRoot = currRoot.left;
        Node<T> currRootLeft = newRoot.right;

        newRoot.right = currRoot;
        currRoot.left = currRootLeft;

        // Update height for newRoot and currentRoot
        updateHeight(currRoot);
        updateHeight(newRoot);

        // Update count for newRoot and currRoot
        updateCount(currRoot);
        updateCount(newRoot);

        return newRoot;
    }

    private Node<T> leftRotate(Node<T> currRoot) {
        Node<T> newRoot = currRoot.right;
        Node<T> currRootRight = newRoot.left;

        newRoot.left = currRoot;
        currRoot.right = currRootRight;

        // Update height for newRoot and currentRoot
        updateHeight(currRoot);
        updateHeight(newRoot);

        // Update count for newRoot and currRoot
        updateCount(currRoot);
        updateCount(newRoot);

        return newRoot;
    }

    private void updateCount(Node<T> currRoot) {
        currRoot.leftCount = 0;
        if (currRoot.left != null) {
            currRoot.leftCount = currRoot.left.leftCount + currRoot.left.rightCount + currRoot.left.count;
        }

        currRoot.rightCount = 0;
        if (currRoot.right != null) {
            currRoot.rightCount = currRoot.right.leftCount + currRoot.right.rightCount + currRoot.right.count;
        }
    }

    private void updateHeight(Node<T> currRoot) {
        currRoot.height = Math.max(getHeight(currRoot.left), getHeight(currRoot.right)) + 1;
    }

    private long getHeightDiff(Node<T> root) {
        if (root == null) return 0;
        return getHeight(root.left) - getHeight(root.right);
    }
}

class DSU {
    int[] parent;
    int[] size;

    DSU(int n) {
        this.parent = new int[n];
        this.size = new int[n];
        Arrays.fill(parent, -1);
    }

    public void makeSet(int v) {
        parent[v] = v;
        size[v] = 1;
    }

    public int findSet(int v) {
        if (v == parent[v]) return v;
        return parent[v] = findSet(parent[v]);
    }

    public void unionSets(int a, int b) {
        a = findSet(a);
        b = findSet(b);
        if (a != b) {
            if (size[a] < size[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            parent[b] = a;
            size[a] += size[b];
        }
    }
}

// Implicit treap with 1 index.
// comment sum logic or modify with min or other things
// modify the interval logic: https://cp-algorithms.com/data_structures/treap.html#toc-tgt-6
class Treap {
    Node root;
    boolean isSorted = true;

    static class Node {
        int size;
        long val;
        long sum;
        Node left, right, parent;
        double prior;

        public Node(int val) {
            this.val = val;
            this.sum = 0;
            size = 1;
            // randomized binary search tree guarantees O(log2 n) amortized complexity
            prior = Math.random();
        }
    }

    static class NodePair {
        Node left, right;

        public NodePair(Node l, Node r) {
            left = l;
            right = r;
        }
    }

    static class Pair {
        long val;
        int index;

        public Pair(long val, int index) {
            this.val = val;
            this.index = index;
        }
    }

    public int size() {
        return size(root);
    }

    public boolean isSorted() {
        return isSorted;
    }

    public Node insert(int val) {
        NodePair n = splitByKey(root, val);
        Node newNode = new Node(val);
        root = merge(n.left, merge(newNode, n.right));
        return newNode;
    }

    public void remove(int val) {
        root = remove(root, val);
    }

    public Node insertAt(int index, int val) {
        NodePair n = splitBySize(root, index - 1);
        Node newNode = new Node(val);
        root = merge(n.left, merge(newNode, n.right));

        if (index > 0 && isSorted) {
            if (getByIndex(root, index-1) >= get (root, index)) isSorted = false;
        }
        if (index < size()-1 && isSorted) {
            if (getByIndex(root, index) >= get (root, index+1)) isSorted = false;
        }
        return newNode;
    }

    public Node insertLast(int val) {
        return insertAt(size() + 1, val);
    }

    public Node removeAt(int index) {
        NodePair res1 = splitBySize(root, index - 1);
        NodePair res2 = splitBySize(res1.right, 1);
        root = merge(res1.left, res2.right);
        return res2.left;
    }

    public long getByIndex(int index) {
        if (index > size(root) || index <= 0) throw new IllegalArgumentException();
        return getByIndex(root, index);
    }

    public boolean contains(long val) {
        if (get(root, val) != Long.MIN_VALUE) {
            return true;
        }

        return false;
    }

    public Pair lowerBound(long val) {
        if (!isSorted) throw new IllegalStateException("lower bound called on unsorted treap");
        return lowerBound(root, val, 1);
    }

    public Pair upperBound(long val) {
        if (!isSorted) throw new IllegalStateException("lower bound called on unsorted treap");
        return upperBound(root, val, 1);
    }

    public void modify(int index, long val) {
        modify(root, index, val);
    }

    public void clear() {
        root = null;
        isSorted = true;
    }

    public void print() {
        print(root);
        if (root != null) System.out.println();
    }

    // 1 index
    public long sum(int l, int r) {
        return sum(root, l, r);
    }

   private long sum(Node curr, int l, int r) {
       NodePair pair1 = splitBySize(curr, l-1);
       NodePair pair2 = splitBySize(pair1.right, r-l+1);

       long result = pair2.left.sum;
       root = merge(merge(pair1.left, pair2.left), pair2.right);

       return result;
    }


    private Pair lowerBound(Node node, long key, int index) {
        if (node == null) return new Pair(Long.MIN_VALUE, -1);
        if (node.val == key) return new Pair(node.val, index + size(node.left));

        if (node.val > key) {
            Pair pair = lowerBound(node.left, key, index);
            if (pair.index == -1)  pair = new Pair(node.val, index + size(node.left));
            return pair;
        }

        return lowerBound(node.right, key, index + size(node.left) + 1);
    }

    private Pair upperBound(Node node, long key, int index) {
        if (node == null) return new Pair(Long.MIN_VALUE, -1);

        if (node.val > key) {
            Pair pair = upperBound(node.left, key, index);
            if (pair.index == -1)  pair = new Pair(node.val, index + size(node.left));
            return pair;
        }

        return upperBound(node.right, key, index + size(node.left) + 1);
    }

    // untested
    public int indexOf(Node cur) {
        Node fa = cur.parent;
        int ret = size(cur.left) + 1;
        while (fa != null) {
            if (cur != fa.left) ret += size(fa.left) + 1;
            cur = fa;
            fa = cur.parent;
        }
        return ret;
    }

    private int size(Node t) {
        if (t == null) return 0;
        else return t.size;
    }

    private long sum(Node t) {
        if (t == null) return 0;
        else return t.sum;
    }

    private void update(Node t) {
        if (t != null) {
            t.size = size(t.left) + 1 + size(t.right);
            t.sum = sum(t.left) + t.val + sum(t.right);
            t.parent = null;
        }
    }

    private void setParent(Node child, Node parent) {
        if (child != null) child.parent = parent;
    }

    long getByIndex(Node n, int index) {
        if (n == null) return Long.MIN_VALUE;
        int key = size(n.left) + 1;
        if (key > index) return getByIndex(n.left, index);
        else if (key < index) return getByIndex(n.right, index - key);
        return n.val;
    }

    long get(Node n, long key) {
        if (n == null) return Long.MIN_VALUE;
        if (n.val > key) return get(n.left, key);
        else if (n.val < key) return get(n.right, key);
        return n.val;
    }

    private void modify(Node n, int index, long val) {
        int key = size(n.left) + 1;
        if (key == index) n.val = val;
        else if (index < key) modify(n.left, index, val);
        else modify(n.right, index - key, val);
        update(n);
    }

    private Node remove(Node node, int val) {
        if (node == null) return null;

        if (node.val == val) {
            return merge(node.left, node.right);
        }

        if (node.val > val) node.left = remove(node.left, val);
        else node.right = remove(node.right, val);

        update(node);
        return node;
    }

    // regular treap for insert by value
    private NodePair splitByKey(Node n, int k) {
        NodePair res = new NodePair(null, null);
        if (n == null) return res;

        if (n.val > k) {
            res = splitByKey(n.left, k);
            n.left = res.right;
            setParent(res.right, n);
            res.right = n;
        } else {
            res = splitByKey(n.right, k);
            n.right = res.left;
            setParent(res.left, n);
            res.left = n;
        }

        update(res.left);
        update(res.right);
        return res;
    }

    //implicit treap for insert at pos
    private NodePair splitBySize(Node n, int k) {
        NodePair res = new NodePair(null, null);
        if (n == null) return res;
        int key = size(n.left) + 1;
        if (key > k) {
            res = splitBySize(n.left, k);
            n.left = res.right;
            setParent(res.right, n);
            res.right = n;
        } else {
            res = splitBySize(n.right, k - key);
            n.right = res.left;
            setParent(res.left, n);
            res.left = n;
        }

        update(res.left);
        update(res.right);
        return res;
    }

    private Node merge(Node t1, Node t2) {
        if (t1 == null) return t2;
        else if (t2 == null) return t1;
        Node newRoot = null;
        if (t1.prior > t2.prior) {
            Node temp = merge(t1.right, t2);
            t1.right = temp;
            setParent(temp, t1);
            newRoot = t1;
        } else {
            Node temp = merge(t1, t2.left);
            t2.left = temp;
            setParent(temp, t2);
            newRoot = t2;
        }
        update(newRoot);
        return newRoot;
    }

    private void print(Node t) {
        if (t == null) return;
        print(t.left);
        System.out.print(t.val + " ");
        print(t.right);
    }
}

