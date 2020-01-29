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
        treap.add(5);
        treap.add(4);
        treap.add(3);
        treap.add(1);
        treap.add(1);
        treap.add(2);
        treap.print();
        System.out.println(treap.get(3));
        treap.modify(4, 6);
        System.out.println(treap.get(4));
        treap.remove(5);
        treap.print();

        System.out.println(treap.sum(5, 5));
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

    static class Pair {
        Node left, right;

        public Pair(Node l, Node r) {
            left = l;
            right = r;
        }
    }

    public int size() {
        return size(root);
    }

    public Node add(int index, int val) {
        Pair n = split(root, index - 1);
        Node newNode = new Node(val);
        root = merge(n.left, merge(newNode, n.right));
        return newNode;
    }

    public Node add(int val) {
        return add(size() + 1, val);
    }

    public Node add(Node val) {
        return add(size() + 1, val);
    }

    public Node remove(int k) {
        Pair res1 = split(root, k - 1);
        Pair res2 = split(res1.right, 1);
        root = merge(res1.left, res2.right);
        return res2.left;
    }

    public long get(int k) {
        if (k > size(root) || k <= 0) throw new IllegalArgumentException();
        return get(root, k);
    }

    public void modify(int key, int val) {
        modify(root, key, val);
    }

    public void clear() {
        root = null;
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
       Pair pair1 = split(curr, l-1);
       Pair pair2 = split(pair1.right, r-l+1);

       long result = pair2.left.sum;
       root = merge(merge(pair1.left, pair2.left), pair2.right);

       return result;
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

    long get(Node n, int k) {
        if (n == null) return -1;
        int key = size(n.left) + 1;
        if (key > k) return get(n.left, k);
        else if (key < k) return get(n.right, k - key);
        return n.val;
    }

    private Node add(int index, Node val) {
        Pair n = split(root, index - 1);
        root = merge(n.left, merge(val, n.right));
        return val;
    }

    private void modify(Node n, int k, int val) {
        int key = size(n.left) + 1;
        if (key == k) n.val = val;
        else if (k < key) modify(n.left, k, val);
        else modify(n.right, k - key, val);
        update(n);
    }

    private Pair split(Node n, int k) {
        Pair res = new Pair(null, null);
        if (n == null) return res;
        int key = size(n.left) + 1;
        if (key > k) {
            res = split(n.left, k);
            n.left = res.right;
            setParent(res.right, n);
            res.right = n;
        } else {
            res = split(n.right, k - key);
            n.right = res.left;
            setParent(res.left, n);
            res.left = n;
        }
        //update(n);
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

