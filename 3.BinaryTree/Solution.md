<style>h3{margin-bottom:0}h3+p{margin-top:0}</style>

📘 [Fundamental.md](./Fundamental.md) · 📋 [Question.md](./Question.md) · `n` = nodes, `h` = height, `w` = max width

---

### 3001: DFS - Preorder Traversal
https://leetcode.com/problems/binary-tree-preorder-traversal/
Return node values in preorder — `Root → Left → Right`.

<details name="q3001">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- Iterative: **push right before left**. Stack is LIFO, so the last push pops first. Swapping them silently gives mirrored output.
- Preorder is the *only* traversal whose iterative form is trivial — you record a node the moment you see it, nothing is pending.
- Space is `O(h)`, but on a skewed tree `h = n`.
- Base case `if (node == null) return;` must be the first line.

</details>

<details name="q3001">
<summary><b>🌳 Examples</b></summary>

```
        1
      /   \
     2     3        preorder = [1, 2, 4, 5, 3]
    / \             (visit a node BEFORE its children)
   4   5
```

| Input | Output |
|---|---|
| `[1,null,2,3]` | `[1,2,3]` |
| `[]` | `[]` |
| `[1]` | `[1]` |

</details>

<details name="q3001">
<summary><b>💡 Intuition</b></summary>

Record the node **immediately**, then deal with its subtrees. Nothing is deferred, so there is no bookkeeping.

Recursive → the `add` sits **before** both calls.
Iterative → pop, record, push children (right first so left comes out first).

</details>

<details name="q3001">
<summary><b>👨‍💻 Code 1 — Recursive</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        dfs(root, out);
        return out;
    }

    private void dfs(TreeNode node, List<Integer> out) {
        if (node == null) return;

        out.add(node.val);        // <-- PREORDER position
        dfs(node.left,  out);
        dfs(node.right, out);
    }
}
```

</details>

<details name="q3001">
<summary><b>👨‍💻 Code 2 — Iterative (stack)</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> out = new ArrayList<>();
    if (root == null) return out;

    Deque<TreeNode> st = new ArrayDeque<>();
    st.push(root);

    while (!st.isEmpty()) {
        TreeNode node = st.pop();
        out.add(node.val);

        if (node.right != null) st.push(node.right);   // right BEFORE left
        if (node.left  != null) st.push(node.left);
    }
    return out;
}
```

**Dry run** — stack shown top-first:

| Pop | Output | Stack after pushes |
|-----|--------|--------------------|
| 1 | `[1]` | `2, 3` |
| 2 | `[1,2]` | `4, 5, 3` |
| 4 | `[1,2,4]` | `5, 3` |
| 5 | `[1,2,4,5]` | `3` |
| 3 | `[1,2,4,5,3]` | *(empty)* |

</details>

---

### 3002: DFS - Inorder Traversal
https://leetcode.com/problems/binary-tree-inorder-traversal/
Return node values in inorder — `Left → Root → Right`.

<details name="q3002">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Inorder is sorted only for a BST.** For a plain binary tree it is just an ordering.
- The recursive version differs from preorder by **one line's position**. Easy to write `add` before the left call and silently produce preorder — check against a 3-node tree before submitting.
- Iterative loop needs **both** conditions: `cur != null` (still descending) and `!st.isEmpty()` (descended fully, still have nodes to pop). Drop either → early termination.
- This iterative template is the base for BST problems: *k-th smallest*, *validate BST*, *two-sum in BST*.

</details>

<details name="q3002">
<summary><b>🌳 Examples</b></summary>

```
        1
      /   \
     2     3        inorder = [4, 2, 5, 1, 3]
    / \             (visit a node BETWEEN its two subtrees)
   4   5
```

| Input | Output |
|---|---|
| `[1,null,2,3]` | `[1,3,2]` |
| `[]` | `[]` |
| `[1]` | `[1]` |

</details>

<details name="q3002">
<summary><b>💡 Intuition</b></summary>

You **cannot** record a node when you first see it — its entire left subtree must come out first. So the node stays *pending*.

That pending set is exactly what the stack holds: dive left pushing everything, then pop → record → turn right.

</details>

<details name="q3002">
<summary><b>👨‍💻 Code 1 — Recursive</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        dfs(root, out);
        return out;
    }

    private void dfs(TreeNode node, List<Integer> out) {
        if (node == null) return;

        dfs(node.left,  out);
        out.add(node.val);        // <-- INORDER position
        dfs(node.right, out);
    }
}
```

</details>

<details name="q3002">
<summary><b>👨‍💻 Code 2 — Iterative (stack)</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> out = new ArrayList<>();
    Deque<TreeNode> st = new ArrayDeque<>();
    TreeNode cur = root;

    while (cur != null || !st.isEmpty()) {
        while (cur != null) {          // dive as far left as possible
            st.push(cur);
            cur = cur.left;
        }
        cur = st.pop();                // leftmost unvisited
        out.add(cur.val);
        cur = cur.right;               // now handle its right subtree
    }
    return out;
}
```

</details>

---

### 3003: DFS - Postorder Traversal
https://leetcode.com/problems/binary-tree-postorder-traversal/
Return node values in postorder — `Left → Right → Root`.

<details name="q3003">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Postorder is the most important traversal.** It is the shape of every bottom-up computation: height, diameter, balanced-check, max path sum.
- In the 2-stack version the pushes are **left then right** — the *opposite* of preorder. Getting it backwards yields a mirrored result that still looks plausible.
- `peek.right != lastVisited` is the entire 1-stack algorithm. Without it you loop forever on the right child.
- **In an interview write the 2-stack version** — 6 lines, impossible to get subtly wrong. Mention the 1-stack `O(h)` version exists.
- 2-stack costs `O(n)` space, not `O(h)`. That is the price of the simplicity.

</details>

<details name="q3003">
<summary><b>🌳 Examples</b></summary>

```
        1
      /   \
     2     3        postorder = [4, 5, 2, 3, 1]
    / \             (visit a node AFTER both children)
   4   5
```

| Input | Output |
|---|---|
| `[1,null,2,3]` | `[3,2,1]` |
| `[]` | `[]` |
| `[1]` | `[1]` |

</details>

<details name="q3003">
<summary><b>💡 Intuition</b></summary>

**The trick:** postorder `L → R → Root` is the exact **reverse** of `Root → R → L`.
And `Root → R → L` is just preorder with the two pushes swapped.

So: run modified preorder into a second stack, then drain it. No pending-state bookkeeping needed.

The 1-stack version instead tracks the last node emitted, so it can tell "descending into the right subtree" apart from "coming back up from it".

</details>

<details name="q3003">
<summary><b>👨‍💻 Code 1 — Recursive</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        dfs(root, out);
        return out;
    }

    private void dfs(TreeNode node, List<Integer> out) {
        if (node == null) return;

        dfs(node.left,  out);
        dfs(node.right, out);
        out.add(node.val);        // <-- POSTORDER position
    }
}
```

</details>

<details name="q3003">
<summary><b>👨‍💻 Code 2 — Iterative, 2 stacks</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> out = new ArrayList<>();
    if (root == null) return out;

    Deque<TreeNode> st = new ArrayDeque<>(), rev = new ArrayDeque<>();
    st.push(root);

    while (!st.isEmpty()) {
        TreeNode node = st.pop();
        rev.push(node);                                // collect Root -> R -> L

        if (node.left  != null) st.push(node.left);    // left BEFORE right
        if (node.right != null) st.push(node.right);   //   (opposite of preorder)
    }

    while (!rev.isEmpty()) out.add(rev.pop().val);     // reverse it
    return out;
}
```

</details>

<details name="q3003">
<summary><b>👨‍💻 Code 3 — Iterative, 1 stack</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

Only if the interviewer asks for `O(h)` space.

```java
public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> out = new ArrayList<>();
    Deque<TreeNode> st = new ArrayDeque<>();
    TreeNode cur = root, lastVisited = null;

    while (cur != null || !st.isEmpty()) {
        while (cur != null) { st.push(cur); cur = cur.left; }

        TreeNode peek = st.peek();
        if (peek.right != null && peek.right != lastVisited) {
            cur = peek.right;                  // right subtree not done yet
        } else {
            out.add(peek.val);
            lastVisited = st.pop();            // both children done -> emit
        }
    }
    return out;
}
```

</details>

---

### 3004: BFS - Level Order Traversal
https://leetcode.com/problems/binary-tree-level-order-traversal/
Return node values **grouped level by level**, top to bottom, left to right.

<details name="q3004">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **`int size = q.size()` before the inner loop is the whole trick.** Writing `for (int i = 0; i < q.size(); i++)` re-evaluates a growing queue → the loop swallows the next levels too.
- Forgetting the `root == null` guard returns `[[]]` instead of `[]`.
- **`ArrayDeque` rejects `null`.** Fine here, but if a variant needs a null level-marker, use `LinkedList`. (Bites in 3036 Serialize/Deserialize.)
- Space is `O(w)`; a perfect tree's last level is `n/2` → `O(n)`. BFS is **not** automatically cheaper than DFS.
- This template is reused almost verbatim in: zigzag · right/left side view · max width · average per level · minimum depth · bottom-up level order. Usually only *what you do with `level`* changes.

</details>

<details name="q3004">
<summary><b>🌳 Examples</b></summary>

```
        1
      /   \
     2     3        answer = [[1], [2,3], [4,5]]
    / \             (grouped, NOT a flat list)
   4   5
```

| Input | Output |
|---|---|
| `[3,9,20,null,null,15,7]` | `[[3],[9,20],[15,7]]` |
| `[]` | `[]` |
| `[1]` | `[[1]]` |

</details>

<details name="q3004">
<summary><b>💡 Intuition</b></summary>

A plain queue gives `[1,2,3,4,5]` — flat. The level boundaries are lost.

But at the top of each outer iteration the queue holds **exactly one complete level**. So snapshot `q.size()` first: that count tells you precisely where the level ends, no markers or extra passes needed.

</details>

<details name="q3004">
<summary><b>👨‍💻 Code — BFS with level-size snapshot</b> &nbsp; T: O(n) &nbsp; S: O(w)</summary>

```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();                      // SNAPSHOT — q grows below
            List<Integer> level = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);

                if (node.left  != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }
}
```

**Dry run:**

| `size` | Polled this round | Queue after | Result |
|--------|-------------------|-------------|--------|
| 1 | `1` | `2, 3` | `[[1]]` |
| 2 | `2, 3` | `4, 5` | `[[1],[2,3]]` |
| 2 | `4, 5` | *(empty)* | `[[1],[2,3],[4,5]]` |

</details>

---

### 3006: Maximum Depth of Binary Tree
https://leetcode.com/problems/maximum-depth-of-binary-tree/
Number of nodes along the longest path from root down to the farthest leaf.

<details name="q3006">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- LeetCode wants depth in **nodes** (`null → 0`, leaf → 1), so a single node answers `1`, not `0`. Diameter (3008) wants **edges**. Same tree, different number — see [Fundamental §2](./Fundamental.md#2-height-vs-depth-vs-level-️).
- This is the **base template** for the whole `1 + max(l, r)` family: 3007, 3008, 3009.
- BFS alternative is equally `O(n)` but uses `O(w)` space instead of `O(h)`. Prefer BFS only when the tree may be **skewed** and recursion would blow the stack.
- Minimum depth is **not** symmetric: you must ignore `null` children, else a one-sided node reports depth 1 wrongly.

</details>

<details name="q3006">
<summary><b>🌳 Examples</b></summary>

```
        3
      /   \
     9     20        depth = 3
          /  \       (path 3 -> 20 -> 15, counting NODES)
        15    7
```

| Input | Output |
|---|---|
| `[3,9,20,null,null,15,7]` | `3` |
| `[]` | `0` |
| `[1]` | `1` |
| `[1,2,null,3]` (skewed) | `3` |

</details>

<details name="q3006">
<summary><b>💡 Intuition</b></summary>

The depth of a node = 1 (itself) + the deeper of its two subtrees.

That sentence *is* the code. `null` contributes 0, which makes a leaf return `1 + max(0,0) = 1`.

Nothing extra is tracked here — unusually, the **answer and the return value are the same thing**. Every other problem in this family has to split them apart.

</details>

<details name="q3006">
<summary><b>👨‍💻 Code 1 — Recursive DFS</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        int l = maxDepth(root.left);
        int r = maxDepth(root.right);

        return 1 + Math.max(l, r);
    }
}
```

</details>

<details name="q3006">
<summary><b>👨‍💻 Code 2 — BFS level count</b> &nbsp; T: O(n) &nbsp; S: O(w)</summary>

Count how many times the level-order loop runs.

```java
public int maxDepth(TreeNode root) {
    if (root == null) return 0;

    Queue<TreeNode> q = new ArrayDeque<>();
    q.offer(root);
    int depth = 0;

    while (!q.isEmpty()) {
        int size = q.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = q.poll();
            if (node.left  != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        depth++;                       // one full level consumed
    }
    return depth;
}
```

</details>

---

### 3007: Check for Balanced Binary Tree
https://www.geeksforgeeks.org/problems/check-for-balanced-tree/1
Return true if **every** node has `|leftHeight - rightHeight| ≤ 1`.

<details name="q3007">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- The condition must hold at **every node**, not just the root. Checking only the root passes obviously-unbalanced trees.
- **`-1` is a sentinel, not a height.** It means "already unbalanced below me". Propagate it up immediately — that early exit is what turns `O(n²)` into `O(n)`.
- Check `l == -1` **right after** computing `l`, before even computing `r`. Skipping the whole right subtree is free speed.
- A perfectly balanced-*looking* tree can still fail deep down. Always test a tree that is balanced at the root but skewed at depth 2.
- Same skeleton as 3006/3008/3009 — only the middle line changes.

</details>

<details name="q3007">
<summary><b>🌳 Examples</b></summary>

```
      1                     1
    /   \                  / \
   2     3                2   3
  / \                    /
 4   5                  4
                       /
   balanced ✓         5        NOT balanced ✗
                      (node 2: lh=2, rh=0 -> diff 2)
```

| Input | Output |
|---|---|
| `[3,9,20,null,null,15,7]` | `true` |
| `[1,2,2,3,3,null,null,4,4]` | `false` |
| `[]` | `true` |
| `[1]` | `true` |

</details>

<details name="q3007">
<summary><b>💡 Intuition</b></summary>

**Brute force:** at every node, compute both subtree heights and compare. But `height()` re-walks each subtree → `O(n²)`.

**Optimal:** the height computation *already* visits every node. So make one pass do both jobs — but here the answer is a `boolean`, and the contract is an `int`.

Instead of a separate global, overload the return value: **`-1` means unbalanced**. Any real height is `≥ 0`, so the sentinel can never be confused with a valid answer.

> Sentinel-in-the-return-value is the alternative to the global variable. Use it when the failure is *terminal* — once unbalanced, nothing above can fix it.

</details>

<details name="q3007">
<summary><b>👨‍💻 Code 1 — Brute Force</b> &nbsp; T: O(n²) &nbsp; S: O(h)</summary>

```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;

        int l = height(root.left);
        int r = height(root.right);

        return Math.abs(l - r) <= 1
            && isBalanced(root.left)
            && isBalanced(root.right);
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
```

</details>

<details name="q3007">
<summary><b>👨‍💻 Code 2 — Optimal, -1 sentinel</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        return check(root) != -1;
    }

    // returns height, or -1 if ANY subtree below is unbalanced
    private int check(TreeNode node) {
        if (node == null) return 0;

        int l = check(node.left);
        if (l == -1) return -1;              // early exit: skip right entirely

        int r = check(node.right);
        if (r == -1) return -1;

        if (Math.abs(l - r) > 1) return -1;  // unbalanced HERE

        return 1 + Math.max(l, r);           // contract: real height
    }
}
```

**Dry run** on `[1,2,2,3,3,null,null,4,4]`:

| Node | l | r | result |
|---|---|---|---|
| 4, 4 | 0 | 0 | 1 |
| 3 (left) | 1 | 1 | 2 |
| 3 (right) | 0 | 0 | 1 |
| 2 (left) | 2 | 1 | 3 |
| 2 (right) | 0 | 0 | 1 |
| 1 | 3 | 1 | `abs(3-1) > 1` → **-1** |

</details>

---

### 3008: Diameter of Binary Tree
https://leetcode.com/problems/diameter-of-binary-tree/
Longest path between **any two nodes**, measured in **edges**.

<details name="q3008">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Edges, not nodes.** `nodes = edges + 1`. Answer consistently 1 too high → you counted nodes.
- **The path need not touch the root.** This is why a global max exists at all.
- **Never return `l + r` from `height()`.** The contract is `1 + max(l, r)` — what the parent depends on. Break it and every ancestor silently computes garbage.
- The answer is the **global**, not the return value. Returning `height(root)` by mistake is a common slip.
- Single node → `0`. Empty tree → `0`. Both fall out of `null → 0`.
- Skewed tree of `n` nodes → diameter `n-1`, recursion depth `n` (stack risk).
- Same function, one line different → **3006** Max Depth · **3007** Balanced · **3009** Max Path Sum. Learn one, get four.

| # | Combine line | Return (contract) |
|---|--------------|-------------------|
| 3006 | *(none — answer is the return)* | `1 + max(l, r)` |
| 3007 | `if (abs(l-r) > 1) return -1` | `1 + max(l, r)` |
| **3008** | `best = max(best, l + r)` | `1 + max(l, r)` |
| 3009 | `best = max(best, l + r + val)` | `val + max(l, r)` |

</details>

<details name="q3008">
<summary><b>🌳 Examples</b></summary>

```
        1                          1
      /   \                         \
     2     3                         2
    / \                             / \
   4   5                           3   4
                                  /     \
   answer = 3                    5       6      answer = 4
   path 4-2-1-3                  path 5-3-2-4-6   (root NOT on path)
```

The second tree is the whole point: `height(left) + height(right)` **at the root** is not the answer.

| Input | Output |
|---|---|
| `[1,2,3,4,5]` | `3` |
| `[1,2]` | `1` |
| `[1]` | `0` |

</details>

<details name="q3008">
<summary><b>💡 Intuition</b></summary>

For **any** node, the longest path whose highest point is that node is exactly:

```
leftHeight + rightHeight        (in edges)
```

So the answer = max of that over **every** node. The problem becomes: *visit every node, compute its left/right height, keep the max.*

Brute force does this literally — a fresh `height()` call per node, `O(n²)`.

But post-order recursion **already** hands you `l` and `r` for free. So compute the diameter on the way up as a side effect, and still return the height to the parent.

> One function, two jobs: **return** the height, **update** the answer.
> The single most reused trick in binary-tree problems — see [Fundamental §9](./Fundamental.md#9-the-recursion-contract-mental-model).

</details>

<details name="q3008">
<summary><b>👨‍💻 Code 1 — Brute Force</b> &nbsp; T: O(n²) &nbsp; S: O(h)</summary>

```java
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        int through = height(root.left) + height(root.right);
        int left    = diameterOfBinaryTree(root.left);
        int right   = diameterOfBinaryTree(root.right);

        return Math.max(through, Math.max(left, right));
    }

    private int height(TreeNode node) {       // height in NODES: null -> 0, leaf -> 1
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
```

**Why slow:** `height()` re-walks the whole subtree at every node.
Skewed → `O(n²)`. Balanced → `O(n log n)`.

</details>

<details name="q3008">
<summary><b>👨‍💻 Code 2 — Optimal, Single DFS</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return diameter;
    }

    // Returns height in NODES. Side effect: keeps `diameter` up to date.
    private int height(TreeNode node) {
        if (node == null) return 0;

        int l = height(node.left);
        int r = height(node.right);

        diameter = Math.max(diameter, l + r);   // edges through this node

        return 1 + Math.max(l, r);
    }
}
```

**Why `l + r` is edges, not nodes:** height is in nodes (leaf = 1). At node `2`: `l = 1` (node 4), `r = 1` (node 5), path `4-2-5` = 2 edges = `l + r`. ✓

**Without the instance variable** (interviewers ask):
```java
int[] best = new int[1];
height(root, best);
return best[0];
// inside: best[0] = Math.max(best[0], l + r);
```

**Dry run** — post-order, deepest first:

| Node | l | r | `l+r` | diameter after | returns |
|------|---|---|-------|----------------|---------|
| 4 | 0 | 0 | 0 | 0 | 1 |
| 5 | 0 | 0 | 0 | 0 | 1 |
| 2 | 1 | 1 | 2 | **2** | 2 |
| 3 | 0 | 0 | 0 | 2 | 1 |
| 1 | 2 | 1 | 3 | **3** | 3 |

Answer = 3 ✓

</details>
### 3009: Binary Tree Maximum Path Sum
https://leetcode.com/problems/binary-tree-maximum-path-sum/
Maximum sum of any path between any two nodes (values can be **negative**).

<details name="q3009">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Initialise `best = Integer.MIN_VALUE`, not 0.** A tree of all-negative values (`[-3]` → `-3`) breaks a `0` start.
- **Clamp each child with `Math.max(0, gain)`.** A negative subtree should be *dropped*, not added. This one line is the whole difference from 3008.
- The path must be **connected and non-branching**. At a node you may take left + node + right (it bends here), but you may only hand **one** side upward — a parent cannot use both.
- Return `val + max(l, r)`, never `val + l + r`. Returning the bent path breaks the contract and lets the parent build an illegal Y-shaped path.
- A path of a single node is valid, so the answer is never "empty".
- Same skeleton as 3006/3007/3008 — see the table in 3008's Remarks.

</details>

<details name="q3009">
<summary><b>🌳 Examples</b></summary>

```
      -10                    2
      /  \                  / \
     9    20               -1   3        best = 5
         /  \                              (path 2 -> 3, the -1 is dropped)
        15   7
   best = 42  (15 -> 20 -> 7, root NOT used)
```

| Input | Output | Why |
|---|---|---|
| `[1,2,3]` | `6` | whole tree bends at root |
| `[-10,9,20,null,null,15,7]` | `42` | `15+20+7`, skips the `-10` |
| `[-3]` | `-3` | all negative — must still return a node |
| `[2,-1,3]` | `5` | `-1` clamped to 0 |

</details>

<details name="q3009">
<summary><b>💡 Intuition</b></summary>

Same reframe as 3008: for **any** node, the best path *bending* at that node is

```
node.val + max(0, leftGain) + max(0, rightGain)
```

Take the max of that over every node → answer.

Two separate quantities again:

| | |
|---|---|
| **Answer** (global) | `val + l + r` — the path bends here, both arms used |
| **Contract** (return) | `val + max(l, r)` — only one arm can continue upward |

The `max(0, ...)` clamp says: *if a subtree's best contribution is negative, contribute nothing instead* — you are always free to stop the path at this node.

</details>

<details name="q3009">
<summary><b>👨‍💻 Code — Optimal, Single DFS</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    private int best = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        gain(root);
        return best;
    }

    // returns the best DOWNWARD path sum starting at `node`
    private int gain(TreeNode node) {
        if (node == null) return 0;

        int l = Math.max(0, gain(node.left));    // drop negative subtrees
        int r = Math.max(0, gain(node.right));

        best = Math.max(best, node.val + l + r); // answer: path bends HERE

        return node.val + Math.max(l, r);        // contract: one arm upward
    }
}
```

**Dry run** on `[-10,9,20,null,null,15,7]`:

| Node | l | r | `val+l+r` | best after | returns |
|---|---|---|---|---|---|
| 9 | 0 | 0 | 9 | 9 | 9 |
| 15 | 0 | 0 | 15 | 15 | 15 |
| 7 | 0 | 0 | 7 | 15 | 7 |
| 20 | 15 | 7 | 42 | **42** | 35 |
| -10 | 9 | 35 | 34 | 42 | 25 |

Answer = 42 ✓

</details>

---

### 3010: Same Tree
https://leetcode.com/problems/same-tree/
Return true if two trees have identical **structure and values**.

<details name="q3010">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Check nulls before values.** `p.val` on a null `p` is an NPE. The guard `if (p == null || q == null) return p == q;` handles all three null cases in one line.
- Structure matters: `[1,2]` and `[1,null,2]` are **not** the same tree even though both hold `{1,2}`.
- Compare `left↔left` and `right↔right`. Crossing them gives **3016 Symmetric**, which is the mirror version of this exact function.
- Both trees are walked in lockstep — one traversal, `O(n)` where `n` is the smaller tree (it short-circuits on the first mismatch).

</details>

<details name="q3010">
<summary><b>🌳 Examples</b></summary>

```
   1        1              1        1
  / \      / \            / \        \
 2   3    2   3          2   3        2

  same ✓                  different ✗  (structure)
```

| p | q | Output |
|---|---|---|
| `[1,2,3]` | `[1,2,3]` | `true` |
| `[1,2]` | `[1,null,2]` | `false` |
| `[1,2,1]` | `[1,1,2]` | `false` |
| `[]` | `[]` | `true` |

</details>

<details name="q3010">
<summary><b>💡 Intuition</b></summary>

Two trees are the same when: **roots match**, and their left subtrees are the same, and their right subtrees are the same.

That is a direct recursive definition — the code is one line of logic.

Order the checks so the cheap ones short-circuit first: nulls → value → recurse.

</details>

<details name="q3010">
<summary><b>👨‍💻 Code — Recursive</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;   // both null -> true, one null -> false

        return p.val == q.val
            && isSameTree(p.left,  q.left)
            && isSameTree(p.right, q.right);
    }
}
```

`return p == q` when at least one is null is the trick: if both are null it is `true`, otherwise `false`. No separate cases needed.

</details>

---

### 3011: Zig-Zag Level Order Traversal
https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
Level order, but alternate direction each level: L→R, R→L, L→R …

<details name="q3011">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Never reverse the queue.** Traverse normally and reverse only the *output list* for that level — the queue order must stay L→R or children get enqueued wrong.
- `LinkedList.addFirst()` builds the reversed level in `O(1)` per node. `Collections.reverse()` is also fine, both are `O(n)` overall.
- Declare `List<Integer> level = new LinkedList<>();` — `ArrayList.add(0, x)` is `O(n)` per insert, making the level `O(n²)`.
- Flip the flag **once per level**, outside the inner loop. Flipping inside is the classic bug.
- Level 0 (root) is L→R, so start `leftToRight = true`.
- Base template is 3004 — only the insert position changes.

</details>

<details name="q3011">
<summary><b>🌳 Examples</b></summary>

```
        3            level 0  L->R   [3]
      /   \          level 1  R->L   [20, 9]
     9     20        level 2  L->R   [15, 7]
          /  \
        15    7      answer = [[3], [20,9], [15,7]]
```

| Input | Output |
|---|---|
| `[3,9,20,null,null,15,7]` | `[[3],[20,9],[15,7]]` |
| `[1]` | `[[1]]` |
| `[]` | `[]` |

</details>

<details name="q3011">
<summary><b>💡 Intuition</b></summary>

This is **3004 with one line changed**.

BFS already produces every level left-to-right. Zig-zag does not change *how you walk* the tree — only *where you put each value* in the level list.

So keep the queue untouched, and on odd levels push to the **front** of the level list instead of the back.

</details>

<details name="q3011">
<summary><b>👨‍💻 Code — BFS + direction flag</b> &nbsp; T: O(n) &nbsp; S: O(w)</summary>

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        boolean leftToRight = true;

        while (!q.isEmpty()) {
            int size = q.size();
            LinkedList<Integer> level = new LinkedList<>();   // NOT ArrayList

            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();

                if (leftToRight) level.addLast(node.val);
                else             level.addFirst(node.val);    // <-- the only change

                if (node.left  != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }

            result.add(level);
            leftToRight = !leftToRight;      // flip ONCE per level
        }
        return result;
    }
}
```

</details>

---

### 3012: Vertical Order Traversal of a Binary Tree
https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
Group nodes by **column**, top to bottom; ties at the same cell sorted by **value**.

<details name="q3012">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **The LeetCode version is much harder than the GFG one.** LC adds the rule: two nodes at the *same* `(row, col)` must be output in **ascending value** order. Miss this and you pass ~80% of tests.
- Track **both** coordinates: `col` (left `-1`, right `+1`) and `row` (depth `+1`). Column alone is not enough to order the output.
- **`TreeMap` is doing the sorting for you** — it iterates keys in ascending order. That is the entire reason to reach for it over `HashMap`.
- Nesting: `TreeMap<col, TreeMap<row, PriorityQueue<val>>>`. Read it as *column → row → tied values*.
- With **BFS** the rows arrive in increasing order automatically, so the inner structure could be a plain list — but you still need the `PriorityQueue` for same-cell ties.
- With **DFS** rows do **not** arrive in order, so you must sort. That is fine; just do not assume DFS order is usable directly.
- Contrast: **3013 Top View / 3014 Bottom View** need only `col → value` — one flat `TreeMap`, no row, no ties. Do those first.

</details>

<details name="q3012">
<summary><b>🌳 Examples</b></summary>

```
 col:  -1    0    +1                     row
              3                            0
            /   \
           9     20                        1
          / \   /  \
        4   5 2     7                      2
              ^     ^
      5 and 2 are both at (row 2, col 0)  -> sorted by value: 2 then 5
```

```
        1
      /   \
     2     3         answer = [[4],[2],[1,5,6],[3],[7]]
    / \   / \
   4   5 6   7       5 (row 2, col 0) and 6 (row 2, col 0) tie -> 5 before 6
```

| Input | Output |
|---|---|
| `[3,9,20,null,null,15,7]` | `[[9],[3,15],[20],[7]]` |
| `[1,2,3,4,5,6,7]` | `[[4],[2],[1,5,6],[3],[7]]` |
| `[1]` | `[[1]]` |

</details>

<details name="q3012">
<summary><b>💡 Intuition</b></summary>

Give every node a coordinate:

```
root      = (row 0, col 0)
left      = (row+1, col-1)
right     = (row+1, col+1)
```

Now the problem is pure sorting: **order all nodes by `col`, then `row`, then `value`**, and group by `col`.

Two ways to get that ordering:

- **Collect + sort** — gather every `(col, row, val)` triple with any traversal, then sort with a 3-key comparator. Obvious, easy to reason about.
- **TreeMap nesting** — insert into `TreeMap<col, TreeMap<row, PriorityQueue<val>>>`. Each container sorts its own level, so simply iterating the structure emits the answer. No comparator to get wrong.

</details>

<details name="q3012">
<summary><b>👨‍💻 Code 1 — DFS + explicit sort</b> &nbsp; T: O(n log n) &nbsp; S: O(n)</summary>

```java
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<int[]> nodes = new ArrayList<>();       // {col, row, val}
        dfs(root, 0, 0, nodes);

        nodes.sort((a, b) ->
              a[0] != b[0] ? a[0] - b[0]             // 1. column
            : a[1] != b[1] ? a[1] - b[1]             // 2. row
            :                a[2] - b[2]);           // 3. value

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nodes.size(); ) {
            List<Integer> col = new ArrayList<>();
            int c = nodes.get(i)[0];
            while (i < nodes.size() && nodes.get(i)[0] == c) col.add(nodes.get(i++)[2]);
            result.add(col);
        }
        return result;
    }

    private void dfs(TreeNode node, int row, int col, List<int[]> out) {
        if (node == null) return;
        out.add(new int[]{col, row, node.val});
        dfs(node.left,  row + 1, col - 1, out);
        dfs(node.right, row + 1, col + 1, out);
    }
}
```

</details>

<details name="q3012">
<summary><b>👨‍💻 Code 2 — TreeMap + BFS (no manual sort)</b> &nbsp; T: O(n log n) &nbsp; S: O(n)</summary>

```java
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // column -> row -> values tied at that exact cell
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();

        Queue<Object[]> q = new LinkedList<>();       // {node, row, col}
        q.offer(new Object[]{root, 0, 0});

        while (!q.isEmpty()) {
            Object[] cur = q.poll();
            TreeNode node = (TreeNode) cur[0];
            int row = (int) cur[1], col = (int) cur[2];

            map.computeIfAbsent(col, k -> new TreeMap<>())
               .computeIfAbsent(row, k -> new PriorityQueue<>())
               .offer(node.val);

            if (node.left  != null) q.offer(new Object[]{node.left,  row + 1, col - 1});
            if (node.right != null) q.offer(new Object[]{node.right, row + 1, col + 1});
        }

        List<List<Integer>> result = new ArrayList<>();
        for (TreeMap<Integer, PriorityQueue<Integer>> rows : map.values()) {   // cols ascending
            List<Integer> col = new ArrayList<>();
            for (PriorityQueue<Integer> pq : rows.values())                     // rows ascending
                while (!pq.isEmpty()) col.add(pq.poll());                       // values ascending
            result.add(col);
        }
        return result;
    }
}
```

Iterating `map.values()` walks columns in ascending order — that is the whole payoff of `TreeMap`.

</details>

---

### 3013: Top View of Binary Tree
https://www.geeksforgeeks.org/problems/top-view-of-binary-tree/1
For each column, the node that is **highest** (closest to the root).

<details name="q3013">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Must use BFS.** With DFS you might reach a column via a deep left path *before* a shallow right path, and record the wrong node. BFS visits strictly by depth, so the first node seen in a column is always the topmost.
- `map.putIfAbsent(col, val)` — **first write wins**. That one word is the entire difference from **3014 Bottom View**, which uses `put` (last write wins).
- `TreeMap` keeps columns sorted left→right, so the output needs no sorting.
- Store `Integer` values, not nodes — you only ever need the value.
- Do **not** reset the column counter between levels; it is inherited from the parent.

</details>

<details name="q3013">
<summary><b>🌳 Examples</b></summary>

```
 col:  -2   -1    0   +1   +2

              1                    top view = [4, 2, 1, 3, 7]
            /   \
           2     3                 col -1 -> 2  (5 is below it, ignored)
          / \   / \                col  0 -> 1  (root wins, 5 and 6 are lower)
         4   5 6   7               col +1 -> 3
```

| Input | Output |
|---|---|
| `[1,2,3,4,5,6,7]` | `[4,2,1,3,7]` |
| `[1,2,3,null,4,null,null,null,5]` | `[1,2,3]` … 4 and 5 hide behind |
| `[1]` | `[1]` |

</details>

<details name="q3013">
<summary><b>💡 Intuition</b></summary>

Stand above the tree and look down. In each column you see only the **shallowest** node — everything below it is hidden.

BFS visits level by level, so **the first time a column appears, that node is the top one**. Every later node in that column is deeper and therefore invisible.

So: BFS, and record a column only if it has not been recorded yet.

</details>

<details name="q3013">
<summary><b>👨‍💻 Code — BFS + TreeMap, first write wins</b> &nbsp; T: O(n log n) &nbsp; S: O(n)</summary>

```java
class Solution {
    public List<Integer> topView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        TreeMap<Integer, Integer> map = new TreeMap<>();     // col -> value
        Queue<Object[]> q = new LinkedList<>();              // {node, col}
        q.offer(new Object[]{root, 0});

        while (!q.isEmpty()) {
            Object[] cur = q.poll();
            TreeNode node = (TreeNode) cur[0];
            int col = (int) cur[1];

            map.putIfAbsent(col, node.val);                  // FIRST wins

            if (node.left  != null) q.offer(new Object[]{node.left,  col - 1});
            if (node.right != null) q.offer(new Object[]{node.right, col + 1});
        }

        result.addAll(map.values());                          // cols already sorted
        return result;
    }
}
```

</details>

---

### 3014: Bottom View of Binary Tree
https://www.geeksforgeeks.org/problems/bottom-view-of-binary-tree/1
For each column, the node that is **lowest** (farthest from the root).

<details name="q3014">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Identical to 3013 except `putIfAbsent` becomes `put`.** Last write wins instead of first. Literally one word.
- Still **must be BFS** — with `put` and DFS, the last node written in a column would be whichever the traversal happened to reach last, not the deepest.
- When two nodes share the same column *and* the same depth, BFS's left-to-right order means the **rightmost** one is kept. That matches the expected output on GFG.
- Same `TreeMap` gives sorted columns for free.

</details>

<details name="q3014">
<summary><b>🌳 Examples</b></summary>

```
 col:  -2   -1    0   +1   +2

              1                    bottom view = [4, 2, 6, 3, 7]
            /   \
           2     3                 col -1 -> 2  (nothing below it)
          / \   / \                col  0 -> 6  (root 1 is hidden by 6)
         4   5 6   7               col +1 -> 3
```

Note col `0`: root `1`, then `5` and `6` both at depth 2. BFS writes `5` then `6`, so `6` survives.

| Input | Output |
|---|---|
| `[1,2,3,4,5,6,7]` | `[4,2,6,3,7]` |
| `[1]` | `[1]` |

</details>

<details name="q3014">
<summary><b>💡 Intuition</b></summary>

Same coordinate idea as top view, but you are now looking **up from below** — in each column you see the deepest node.

BFS goes shallow → deep, so the **last** node written into a column is the deepest one. Just overwrite every time.

> Top view = `putIfAbsent`. Bottom view = `put`. Remember them as a pair; you will never mix them up again.

</details>

<details name="q3014">
<summary><b>👨‍💻 Code — BFS + TreeMap, last write wins</b> &nbsp; T: O(n log n) &nbsp; S: O(n)</summary>

```java
class Solution {
    public List<Integer> bottomView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        TreeMap<Integer, Integer> map = new TreeMap<>();     // col -> value
        Queue<Object[]> q = new LinkedList<>();              // {node, col}
        q.offer(new Object[]{root, 0});

        while (!q.isEmpty()) {
            Object[] cur = q.poll();
            TreeNode node = (TreeNode) cur[0];
            int col = (int) cur[1];

            map.put(col, node.val);                          // LAST wins

            if (node.left  != null) q.offer(new Object[]{node.left,  col - 1});
            if (node.right != null) q.offer(new Object[]{node.right, col + 1});
        }

        result.addAll(map.values());
        return result;
    }
}
```

</details>

---

### 3015: Left / Right View of Binary Tree
https://leetcode.com/problems/binary-tree-right-side-view/ · https://www.geeksforgeeks.org/problems/left-view-of-binary-tree/1
The nodes visible when standing to the **right** (or **left**) of the tree — one node per level.

<details name="q3015">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- One node **per level**, not per column. This is a *level* problem (3004 family), unlike top/bottom view which are *column* problems (3013/3014).
- BFS: right view = **last** node of each level (`i == size - 1`); left view = **first** (`i == 0`). One character apart.
- DFS: recurse **right child first** for the right view, and record when `level == result.size()` — i.e. the first node you meet at a brand-new depth. Flip to left-first for the left view.
- The DFS version is `O(h)` space vs BFS's `O(w)` — better on wide trees, worse on skewed ones.
- Common bug: `level == result.size()` written as `>=` or with the level counter incremented in the wrong place, which records several nodes per level.
- The last node of a level is **not** always the rightmost *looking* node — but it is, because BFS enqueues left before right at every step. Keep that ordering.

</details>

<details name="q3015">
<summary><b>🌳 Examples</b></summary>

```
        1              right view = [1, 3, 4]
      /   \            left  view = [1, 2, 4]
     2     3
      \                level 2 has only ONE node (4),
       4               so it appears in BOTH views
```

| Input | Right view | Left view |
|---|---|---|
| `[1,2,3,null,5,null,4]` | `[1,3,4]` | `[1,2,5]` |
| `[1,null,3]` | `[1,3]` | `[1,3]` |
| `[]` | `[]` | `[]` |

</details>

<details name="q3015">
<summary><b>💡 Intuition</b></summary>

**BFS view:** you already build each level as a list in 3004. The right view is just the last element of each of those lists — so do not even build the list, keep only the last node.

**DFS view:** walk `Root → Right → Left`. The *first* node you ever reach at a given depth must be the rightmost one at that depth, because you always tried the right branch first. Track the deepest level recorded so far; if the current level is new, this node is visible.

</details>

<details name="q3015">
<summary><b>👨‍💻 Code 1 — BFS, last node per level</b> &nbsp; T: O(n) &nbsp; S: O(w)</summary>

```java
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();

                if (i == size - 1) result.add(node.val);   // LEFT view: i == 0

                if (node.left  != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }
        return result;
    }
}
```

</details>

<details name="q3015">
<summary><b>👨‍💻 Code 2 — DFS, right child first</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }

    private void dfs(TreeNode node, int level, List<Integer> result) {
        if (node == null) return;

        if (level == result.size()) result.add(node.val);   // first node at a NEW depth

        dfs(node.right, level + 1, result);                 // LEFT view: swap these
        dfs(node.left,  level + 1, result);                 //   two lines
    }
}
```

</details>

---

### 3016: Symmetric Tree
https://leetcode.com/problems/symmetric-tree/
Return true if the tree is a **mirror of itself** around the root.

<details name="q3016">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **This is 3010 Same Tree with the recursion crossed.** Compare `left.left ↔ right.right` and `left.right ↔ right.left`. That crossing is the whole problem.
- Do **not** compare a level's values against their reverse — `[1,2,2,null,3,null,3]` produces a palindromic level list but is **not** symmetric. Structure has to be mirrored too, not just values.
- An empty tree is symmetric → `true`.
- Start the recursion on `root.left, root.right` — never on `root, root` after the top-level call, or you compare a node with itself.
- Iterative version: push pairs into a queue/stack **in mirrored order** and pop two at a time.

</details>

<details name="q3016">
<summary><b>🌳 Examples</b></summary>

```
        1                        1
      /   \                    /   \
     2     2                  2     2
    / \   / \                  \     \
   3   4 4   3                  3     3

   symmetric ✓                  NOT symmetric ✗
                                (both 3s are RIGHT children)
```

| Input | Output |
|---|---|
| `[1,2,2,3,4,4,3]` | `true` |
| `[1,2,2,null,3,null,3]` | `false` |
| `[]` | `true` |
| `[1]` | `true` |

</details>

<details name="q3016">
<summary><b>💡 Intuition</b></summary>

Two subtrees mirror each other when their roots match **and** the outer pair mirrors and the inner pair mirrors:

```
   a          b
  / \        / \
 al ar      bl br

 mirror(a, b)  =  a.val == b.val
               && mirror(al, br)      <- OUTER pair
               && mirror(ar, bl)      <- INNER pair
```

Compare that with 3010, where it is `same(al, bl) && same(ar, br)`. Same function, arguments crossed.

</details>

<details name="q3016">
<summary><b>👨‍💻 Code 1 — Recursive mirror</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return root == null || mirror(root.left, root.right);
    }

    private boolean mirror(TreeNode a, TreeNode b) {
        if (a == null || b == null) return a == b;

        return a.val == b.val
            && mirror(a.left,  b.right)     // outer
            && mirror(a.right, b.left);     // inner
    }
}
```

</details>

<details name="q3016">
<summary><b>👨‍💻 Code 2 — Iterative (queue of pairs)</b> &nbsp; T: O(n) &nbsp; S: O(w)</summary>

```java
public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;

    Queue<TreeNode> q = new LinkedList<>();     // LinkedList: must accept nulls
    q.offer(root.left);
    q.offer(root.right);

    while (!q.isEmpty()) {
        TreeNode a = q.poll(), b = q.poll();    // always pop TWO

        if (a == null && b == null) continue;
        if (a == null || b == null) return false;
        if (a.val != b.val)         return false;

        q.offer(a.left);  q.offer(b.right);     // outer pair
        q.offer(a.right); q.offer(b.left);      // inner pair
    }
    return true;
}
```

`ArrayDeque` would throw here — nulls are deliberately enqueued to keep the pairing aligned.

</details>

---

### 3017: Binary Tree Paths (Root to Leaf)
https://leetcode.com/problems/binary-tree-paths/ · https://www.interviewbit.com/problems/path-to-given-node/
All root-to-leaf paths; and the single path from root to a given node.

<details name="q3017">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **A leaf is `left == null && right == null`** — not "a node with a null child". A node with one child is *not* a leaf, and treating it as one produces phantom paths.
- Passing a `String` down copies it at each call, so **no backtracking is needed** — Java strings are immutable. Simple, but `O(n·h)` total work.
- If you use a shared `List<Integer>` instead, you **must** `remove(size-1)` after both recursive calls. Forgetting that single line is the most common bug in every path problem.
- **Path to a given node** returns `boolean`: `true` propagates up and freezes the list; `false` triggers the backtrack pop. Once found, short-circuit with `||` so the right subtree is never explored.
- These two are the foundation for **3018 LCA**, **3021 nodes at distance K**, and root-to-leaf sum variants.
- Check the root-is-null case before starting; `[]` should yield `[]`, not a one-element path.

</details>

<details name="q3017">
<summary><b>🌳 Examples</b></summary>

```
      1              all root-to-leaf paths:
    /   \              "1->2->5"
   2     3             "1->3"
    \
     5               path to node 5 = [1, 2, 5]
                     path to node 3 = [1, 3]
                     path to node 9 = []      (absent)
```

| Input | Output |
|---|---|
| `[1,2,3,null,5]` | `["1->2->5","1->3"]` |
| `[1]` | `["1"]` |
| `[]` | `[]` |

</details>

<details name="q3017">
<summary><b>💡 Intuition</b></summary>

Carry the path built so far as you descend. At a **leaf**, the path is complete — record it.

The only real decision is how to carry it:

| | Cost | Backtracking |
|---|---|---|
| Immutable `String` | copy per call | not needed |
| Shared `List` | `O(1)` per call | **required** — pop on the way out |

For *path to a given node*, you additionally need to know whether the search **succeeded below you**. That is what the `boolean` return carries: `true` means "stop popping, the answer is in the list".

</details>

<details name="q3017">
<summary><b>👨‍💻 Code 1 — All root-to-leaf paths</b> &nbsp; T: O(n·h) &nbsp; S: O(h)</summary>

```java
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> out = new ArrayList<>();
        if (root != null) dfs(root, "", out);
        return out;
    }

    private void dfs(TreeNode node, String path, List<String> out) {
        path += node.val;

        if (node.left == null && node.right == null) {   // LEAF -> path complete
            out.add(path);
            return;
        }

        path += "->";
        if (node.left  != null) dfs(node.left,  path, out);
        if (node.right != null) dfs(node.right, path, out);
    }
}
```

</details>

<details name="q3017">
<summary><b>👨‍💻 Code 2 — Path to a given node (with backtracking)</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
public List<Integer> pathTo(TreeNode root, int x) {
    List<Integer> path = new ArrayList<>();
    if (root != null) find(root, x, path);
    return path;                                  // empty if x is absent
}

private boolean find(TreeNode node, int x, List<Integer> path) {
    if (node == null) return false;

    path.add(node.val);                           // choose

    if (node.val == x) return true;               // found -> freeze the list
    if (find(node.left, x, path)) return true;    // short-circuits the right subtree
    if (find(node.right, x, path)) return true;

    path.remove(path.size() - 1);                 // BACKTRACK — the line people forget
    return false;
}
```

</details>

---

### 3018: Lowest Common Ancestor of a Binary Tree
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
Deepest node that has both `p` and `q` as descendants (a node is its own descendant).

<details name="q3018">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **This is a plain binary tree, not a BST.** There is no `val` comparison to guide you — you must search both sides.
- The whole algorithm is 4 lines. Memorise the shape, not the reasoning: `null → null`, `found → return self`, `both sides non-null → I am the LCA`, `one side non-null → pass it up`.
- **The base case `root == p || root == q` returns immediately**, without checking whether the other node is below. That looks wrong but is correct: if `q` is inside `p`'s subtree, `p` *is* the LCA.
- Assumes **both nodes exist** in the tree (LeetCode guarantees it). If they might be absent you need extra flags, or the answer is meaningless.
- Compares by **reference** (`root == p`), not by value. Values may repeat in a general binary tree.
- Brute-force alternative: get both root-to-node paths (3017) and walk them until they diverge — `O(n)` time but `O(h)` extra lists, and much more code.

</details>

<details name="q3018">
<summary><b>🌳 Examples</b></summary>

```
          3
        /   \
       5     1          LCA(5, 1) = 3   -> split at the root
      / \   / \
     6   2 0   8        LCA(6, 4) = 5   -> both in the left subtree
        / \
       7   4            LCA(5, 4) = 5   -> 4 is BELOW 5, so 5 itself
```

| p | q | Output |
|---|---|---|
| `5` | `1` | `3` |
| `5` | `4` | `5` |
| `7` | `4` | `2` |
| `[1,2]`, p=`1`, q=`2` | | `1` |

</details>

<details name="q3018">
<summary><b>💡 Intuition</b></summary>

Ask each node one question: **"did you find `p` or `q` anywhere below (or at) you?"**

Each node reports back:

- `null` → found nothing
- a non-null node → found something; this is the best candidate from my side

Now at any node, look at the two reports:

| left | right | meaning |
|---|---|---|
| non-null | non-null | one target on each side → **I am the LCA** |
| non-null | `null` | everything is on the left → pass the left report up |
| `null` | non-null | pass the right report up |
| `null` | `null` | nothing here → report `null` |

The first node where both sides report non-null is, by definition, the deepest node containing both.

</details>

<details name="q3018">
<summary><b>👨‍💻 Code — Recursive</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left  = lowestCommonAncestor(root.left,  p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;   // split here -> LCA
        return left != null ? left : right;               // pass the found side up
    }
}
```

</details>

---

### 3019: Maximum Width of Binary Tree
https://leetcode.com/problems/maximum-width-of-binary-tree/
Widest level, counting the `null` gaps **between** the leftmost and rightmost node.

<details name="q3019">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Not the node count of a level.** Nulls sitting *between* two real nodes count; nulls outside them do not. `q.size()` is the wrong answer.
- Index nodes as if the tree were a perfect array: `left = 2i + 1`, `right = 2i + 2`. Width of a level = `lastIndex - firstIndex + 1`.
- **Indices overflow `int` fast** — a skewed tree of depth 60 exceeds `long`. Fix: **normalise each level** by subtracting the level's first index before enqueuing children. Then indices restart near 0 every level.
- Without normalisation you must use `long`, and even that fails on adversarial LeetCode cases. Normalise; it costs one line.
- `mmin` must be read **before** the inner loop starts polling, since the first element is the level's minimum index.
- BFS only — DFS would need a min-index-per-depth map and is far more fiddly.

</details>

<details name="q3019">
<summary><b>🌳 Examples</b></summary>

```
        1                idx 0                width 1
      /   \
     3     2             idx 1, 2             width 2
    / \     \
   5   3     9           idx 3, 4, _, 6       width 4  <- 6 - 3 + 1
                                                (the missing idx 5 counts)
   answer = 4
```

| Input | Output |
|---|---|
| `[1,3,2,5,3,null,9]` | `4` |
| `[1,3,2,5,null,null,9,6,null,null,7]` | `7` |
| `[1,3,null,5,3]` | `2` |
| `[1]` | `1` |

</details>

<details name="q3019">
<summary><b>💡 Intuition</b></summary>

Pretend the tree is stored in an array like a heap. Then every node has a fixed **position**, whether or not its siblings exist:

```
root = 0,   left = 2i + 1,   right = 2i + 2
```

A level's width is simply `last - first + 1` of those positions — the gaps are counted automatically because the indices skip over them.

The only catch is size. Indices double every level, so they explode. But width is a *difference*, and differences are unaffected by a constant shift — so subtract the level's smallest index from everything before passing it down. The relative spacing is preserved and the numbers stay small.

</details>

<details name="q3019">
<summary><b>👨‍💻 Code — BFS + normalised indices</b> &nbsp; T: O(n) &nbsp; S: O(w)</summary>

```java
class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        int width = 0;
        Queue<Object[]> q = new LinkedList<>();      // {node, index}
        q.offer(new Object[]{root, 0});

        while (!q.isEmpty()) {
            int size = q.size();
            int mmin = (int) q.peek()[1];            // level's first index — read BEFORE polling
            int first = 0, last = 0;

            for (int i = 0; i < size; i++) {
                Object[] cur = q.poll();
                TreeNode node = (TreeNode) cur[0];
                int idx = (int) cur[1] - mmin;       // NORMALISE -> no overflow

                if (i == 0)        first = idx;
                if (i == size - 1) last  = idx;

                if (node.left  != null) q.offer(new Object[]{node.left,  2 * idx + 1});
                if (node.right != null) q.offer(new Object[]{node.right, 2 * idx + 2});
            }
            width = Math.max(width, last - first + 1);
        }
        return width;
    }
}
```

</details>

---

### 3020: Children Sum Property
https://www.geeksforgeeks.org/problems/children-sum-parent/1
Every node's value must equal the sum of its children. **Check** it, then **convert** a tree to satisfy it (values may only be increased).

<details name="q3020">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Two different problems share this name.** GFG asks you to *check*; the interview favourite (Striver) asks you to *convert*. Know which one is being asked.
- **Leaves are always valid** — a leaf has no children, so it trivially satisfies the property. Do not try to zero them.
- Convert rule, top-down: if `childSum >= node.val`, raise the **node** to `childSum`. Otherwise raise **both children** to `node.val`.
- Then on the way back up, **recompute** `node.val = left.val + right.val`. Without this second pass the lower levels are correct but the parent is stale.
- **Only increases are allowed.** That is why the `>=` branch raises the parent and the `<` branch raises the children — never decrease.
- Handle single-child nodes: treat the missing child's contribution as `0`, and only assign to children that exist.
- The convert version is one of the few tree problems that is **both** top-down (push values down) **and** bottom-up (pull sums back up).

</details>

<details name="q3020">
<summary><b>🌳 Examples</b></summary>

```
CHECK:
      10                  35
     /  \                /  \
    8    2    ✓         20   15    ✗   (35 != 20 + 15 = 35 ok, but 20 != 2+3)
                       /  \
                      2    3

CONVERT:
      40                      40                    (40 >= 8+2 -> children raised)
     /  \        ---->       /  \
    8    2                  40   40
   / \                     / \
  4   5                   40  40   ->  recompute upward -> 80 / ...
```

| Input | Check |
|---|---|
| `[10,8,2]` | `true` |
| `[10,8,4]` | `false` |
| `[1]` | `true` (leaf) |
| `[]` | `true` |

</details>

<details name="q3020">
<summary><b>💡 Intuition</b></summary>

**Check** is a plain postorder: compute the children's sum, compare with the node, and require every subtree to pass too.

**Convert** is the interesting one. You are never allowed to *decrease* a value, so at each node take the larger of the two sides and push it to the smaller:

```
childSum >= node.val   ->  node.val = childSum        (parent was too small)
childSum <  node.val   ->  left.val = right.val = node.val   (children were too small)
```

Copying the parent's value into *both* children over-shoots, but that is fine — it only ever increases, and the **bottom-up recompute** afterwards (`node.val = left.val + right.val`) fixes every parent to the exact sum of what its children ended up as.

Down first to guarantee children are big enough, up second to make parents exact.

</details>

<details name="q3020">
<summary><b>👨‍💻 Code 1 — Check</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
public boolean isSumProperty(TreeNode node) {
    if (node == null) return true;
    if (node.left == null && node.right == null) return true;    // leaf: always valid

    int sum = 0;
    if (node.left  != null) sum += node.left.val;
    if (node.right != null) sum += node.right.val;

    return node.val == sum
        && isSumProperty(node.left)
        && isSumProperty(node.right);
}
```

</details>

<details name="q3020">
<summary><b>👨‍💻 Code 2 — Convert (increase-only)</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
public void changeTree(TreeNode node) {
    if (node == null) return;

    // ---- going DOWN: make sure children are at least as big as the parent ----
    int childSum = 0;
    if (node.left  != null) childSum += node.left.val;
    if (node.right != null) childSum += node.right.val;

    if (childSum >= node.val) {
        node.val = childSum;                       // raise the parent
    } else {
        if (node.left  != null) node.left.val  = node.val;   // raise the children
        if (node.right != null) node.right.val = node.val;
    }

    changeTree(node.left);
    changeTree(node.right);

    // ---- coming UP: make the parent exactly the sum of its final children ----
    int total = 0;
    if (node.left  != null) total += node.left.val;
    if (node.right != null) total += node.right.val;

    if (node.left != null || node.right != null) node.val = total;   // skip leaves
}
```

The final `if` guard matters: without it, every **leaf** would be overwritten with `0`.

</details>

---

### 3021: All Nodes at Distance K in Binary Tree
https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
All node values exactly `k` edges away from a given `target` node.

<details name="q3021">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **A binary tree has no upward links.** Distance can go *up* through the parent, so first build a `Map<TreeNode, TreeNode> parent` — then the tree behaves like an undirected graph.
- Once parents exist, this is **plain BFS on a graph**, not a tree problem. Each node has up to 3 neighbours: `left`, `right`, `parent`.
- **A `visited` set is mandatory.** Without it you walk down to the parent and straight back to the child forever.
- `visited.add(x)` returns `false` if already present — use it as the check and the insert in one expression.
- Stop when `dist == k` and return **whatever is still in the queue**. Do not drain it.
- `k = 0` returns the target itself. Handle it — the loop must not run at all.
- Keys are `TreeNode` references, so `HashMap` uses identity hashing. Duplicate *values* in the tree are harmless.
- Same parent-map trick powers **3022 Burn Tree**.

</details>

<details name="q3021">
<summary><b>🌳 Examples</b></summary>

```
          3                 target = 5, k = 2
        /   \
       5     1              distance 1 from 5 : 6, 2, 3   (3 is the PARENT)
      / \   / \             distance 2 from 5 : 7, 4, 1
     6   2 0   8
        / \                 answer = [7, 4, 1]
       7   4
```

| target | k | Output |
|---|---|---|
| `5` | `2` | `[7,4,1]` |
| `5` | `0` | `[5]` |
| `1` | `3` | `[]` … nothing that far |
| `[1]`, target `1` | `3` | `[]` |

</details>

<details name="q3021">
<summary><b>💡 Intuition</b></summary>

In a tree you can only walk downward, but "distance K" spreads in **all** directions — like a ripple.

So convert the tree into an undirected graph by recording each node's parent. Now every node has up to three neighbours and the question becomes trivial: **BFS outward from the target, stop after `k` levels**.

The `visited` set is what keeps the ripple moving outward instead of bouncing back and forth between a node and its parent.

</details>

<details name="q3021">
<summary><b>👨‍💻 Code — Parent map + BFS</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
class Solution {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        markParents(root, null, parent);

        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(target);
        visited.add(target);

        int dist = 0;
        while (!q.isEmpty() && dist < k) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();

                if (node.left  != null && visited.add(node.left))  q.offer(node.left);
                if (node.right != null && visited.add(node.right)) q.offer(node.right);

                TreeNode p = parent.get(node);
                if (p != null && visited.add(p)) q.offer(p);       // the UPWARD move
            }
            dist++;
        }

        List<Integer> result = new ArrayList<>();
        for (TreeNode node : q) result.add(node.val);              // whatever is left
        return result;
    }

    private void markParents(TreeNode node, TreeNode par, Map<TreeNode, TreeNode> parent) {
        if (node == null) return;
        parent.put(node, par);
        markParents(node.left,  node, parent);
        markParents(node.right, node, parent);
    }
}
```

</details>

---

### 3022: Minimum Time to Burn a Binary Tree
https://www.geeksforgeeks.org/problems/burning-tree/1
Fire starts at a target node and spreads to `left`, `right` and `parent` each second. Time to burn the whole tree.

<details name="q3022">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Same machinery as 3021** — parent map + BFS + visited. Only the accounting differs: 3021 stops at `k`, this one runs to exhaustion and counts the seconds.
- Answer = **number of BFS levels minus 1**, because level 0 (the target itself) costs no time. Off-by-one here is the classic mistake.
- Guard the increment: only `time++` if this round actually **spread to something new**. Otherwise the final empty round adds a phantom second.
- The target is given as a **value** on GFG (not a node reference), so find the node while building the parent map.
- Answer equals the **eccentricity** of the target — its distance to the farthest node. A target at a leaf of a skewed tree gives `n-1`.
- Single-node tree → `0`.

</details>

<details name="q3022">
<summary><b>🌳 Examples</b></summary>

```
          1                target = 8
        /   \
       2     3             t=0 : 8
      / \     \            t=1 : 5
     4   5     6           t=2 : 2, 7
        / \                t=3 : 4, 1
       7   8               t=4 : 3
                           t=5 : 6      -> answer = 5
```

Fire climbs upward as well as down: `8 → 5 → 2 → 1 → 3 → 6`. The longest of those chains is the answer.

| Input | target | Output |
|---|---|---|
| `[1,2,3,4,5,null,6,null,null,7,8]` | `8` | `5` |
| `[1,2,3]` | `1` | `1` |
| `[1]` | `1` | `0` |

</details>

<details name="q3022">
<summary><b>💡 Intuition</b></summary>

Fire spreads to every neighbour each second — exactly a **BFS ripple** from the target, in the same undirected graph as 3021.

Each BFS level = one second. So the answer is simply *how many levels the ripple takes to cover everything*, minus the starting level.

The only care needed is not counting a second in which nothing new caught fire.

</details>

<details name="q3022">
<summary><b>👨‍💻 Code — Parent map + BFS level count</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
public int minTime(TreeNode root, int target) {
    Map<TreeNode, TreeNode> parent = new HashMap<>();
    TreeNode start = markParents(root, null, parent, target);

    Set<TreeNode> burnt = new HashSet<>();
    Queue<TreeNode> q = new ArrayDeque<>();
    q.offer(start);
    burnt.add(start);

    int time = 0;
    while (!q.isEmpty()) {
        int size = q.size();
        boolean spread = false;

        for (int i = 0; i < size; i++) {
            TreeNode node = q.poll();

            if (node.left  != null && burnt.add(node.left))  { q.offer(node.left);  spread = true; }
            if (node.right != null && burnt.add(node.right)) { q.offer(node.right); spread = true; }

            TreeNode p = parent.get(node);
            if (p != null && burnt.add(p))                   { q.offer(p);          spread = true; }
        }
        if (spread) time++;             // only count a second if the fire moved
    }
    return time;
}

private TreeNode markParents(TreeNode node, TreeNode par,
                             Map<TreeNode, TreeNode> parent, int target) {
    if (node == null) return null;
    parent.put(node, par);

    TreeNode found = (node.val == target) ? node : null;
    TreeNode l = markParents(node.left,  node, parent, target);
    TreeNode r = markParents(node.right, node, parent, target);

    return found != null ? found : (l != null ? l : r);
}
```

</details>

---

### 3023: Boundary Traversal of Binary Tree
https://www.geeksforgeeks.org/problems/boundary-traversal-of-binary-tree/1
Anti-clockwise boundary: left edge (top-down), then all leaves (left-right), then right edge (bottom-up).

<details name="q3023">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Renumbered from 3011** — the old list used `3011` for both Zig-Zag and Boundary. This is the Boundary one.
- **Three independent walks**, each excluding leaves, then stitched: left boundary → leaves → reversed right boundary.
- **Exclude leaves from the left and right walks**, otherwise the corner leaves appear twice. The `if (!isLeaf(cur))` guard in each walk is doing that job.
- **The root is added separately**, and only if it is *not* a leaf. A single-node tree must output `[1]`, not `[1,1]`.
- Left walk: prefer `cur.left`, fall back to `cur.right`. Right walk: prefer `cur.right`, fall back to `cur.left`. Getting the fallback wrong silently skips nodes on zig-zag edges.
- **The right boundary must be reversed** — collect into a temp list and add it backwards, or use a stack.
- The leaves walk is any DFS that appends only leaves; preorder keeps them in left-to-right order.
- If the root has no left child, the left boundary is empty — do not force-add anything.

</details>

<details name="q3023">
<summary><b>🌳 Examples</b></summary>

```
              1                 left boundary  (no leaves) : 1, 2
            /   \               leaves        (L -> R)     : 4, 5, 6, 7
           2     3              right boundary (reversed)  : 3
          / \   / \
         4   5 6   7            answer = [1, 2, 4, 5, 6, 7, 3]
```

```
        1
       / \                      answer = [1, 2, 4, 6, 7, 5, 3]
      2   3
     /   /
    4   5                       note 6 and 7 are leaves, 4 is NOT a leaf
   / \                          so 4 appears in the left-boundary walk
  6   7
```

| Input | Output |
|---|---|
| `[1,2,3,4,5,6,7]` | `[1,2,4,5,6,7,3]` |
| `[1]` | `[1]` |
| `[1,2]` | `[1,2]` |

</details>

<details name="q3023">
<summary><b>💡 Intuition</b></summary>

Walk the outline of the tree anti-clockwise. The outline is made of three disjoint pieces:

```
        root
       /    \
   left      right        1. down the LEFT edge   (top -> bottom)
   edge      edge         2. across the LEAVES    (left -> right)
       \    /             3. up the RIGHT edge    (bottom -> top)
        leaves
```

Handle them as three separate, simple traversals rather than one clever one. The only shared rule is **a leaf belongs to piece 2 and nowhere else** — that single exclusion prevents every duplicate.

Piece 3 is naturally collected top-down, so reverse it before appending.

</details>

<details name="q3023">
<summary><b>👨‍💻 Code — Three walks</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
class Solution {
    public List<Integer> boundary(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        if (!isLeaf(root)) res.add(root.val);     // root: only if not a leaf

        addLeftBoundary(root, res);
        addLeaves(root, res);
        addRightBoundary(root, res);
        return res;
    }

    private boolean isLeaf(TreeNode n) {
        return n.left == null && n.right == null;
    }

    private void addLeftBoundary(TreeNode root, List<Integer> res) {
        TreeNode cur = root.left;
        while (cur != null) {
            if (!isLeaf(cur)) res.add(cur.val);           // leaves handled separately
            cur = (cur.left != null) ? cur.left : cur.right;   // prefer LEFT
        }
    }

    private void addLeaves(TreeNode node, List<Integer> res) {
        if (node == null) return;
        if (isLeaf(node)) { res.add(node.val); return; }
        addLeaves(node.left,  res);
        addLeaves(node.right, res);
    }

    private void addRightBoundary(TreeNode root, List<Integer> res) {
        TreeNode cur = root.right;
        List<Integer> tmp = new ArrayList<>();
        while (cur != null) {
            if (!isLeaf(cur)) tmp.add(cur.val);
            cur = (cur.right != null) ? cur.right : cur.left;  // prefer RIGHT
        }
        for (int i = tmp.size() - 1; i >= 0; i--) res.add(tmp.get(i));   // REVERSED
    }
}
```

</details>

---

### 3033: Count Nodes in a Complete Binary Tree
https://leetcode.com/problems/count-complete-tree-nodes/
Count all nodes in a **complete** binary tree in better than `O(n)`.

<details name="q3033">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- Plain DFS/BFS is `O(n)` and passes — but the whole point of the question is **exploiting completeness**. If you answer `O(n)` you have answered a different question.
- **`leftHeight == rightHeight` ⟹ the subtree is perfect** ⟹ `2^h - 1` nodes, computed instantly. That is the entire optimisation.
- Heights here are counted in **nodes** (`null → 0`), so a perfect subtree of height `h` has `2^h - 1` nodes. With the edges convention the formula becomes `2^(h+1) - 1` — pick one and stay consistent.
- Use `(1 << h) - 1`, not `Math.pow` — `pow` returns a `double` and rounds badly at scale.
- Complexity is **`O(log²n)`**: `O(log n)` levels of recursion, each doing an `O(log n)` height probe. Not `O(log n)`.
- At most **one** node per level has mismatched heights, so only one branch recurses deeply — the other returns in `O(1)`.
- Works only for **complete** trees. On an arbitrary tree it is still correct but degrades to `O(n log n)`.

</details>

<details name="q3033">
<summary><b>🌳 Examples</b></summary>

```
          1                lh(root) = 3 (1->2->4),  rh(root) = 3 (1->3->6)
        /   \              equal -> PERFECT -> 2^3 - 1 = 7 nodes, done
       2     3
      / \   / \
     4   5 6   7

          1                lh = 3 (1->2->4),  rh = 2 (1->3)
        /   \              NOT equal -> recurse:
       2     3               left  subtree {2,4,5} -> perfect -> 3
      / \                    right subtree {3}     -> perfect -> 1
     4   5                 total = 1 + 3 + 1 = 5
```

| Input | Output |
|---|---|
| `[1,2,3,4,5,6]` | `6` |
| `[]` | `0` |
| `[1]` | `1` |

</details>

<details name="q3033">
<summary><b>💡 Intuition</b></summary>

A complete tree is *almost* perfect — only the last level is partial. And for a **perfect** subtree you never need to count: the height alone gives the node count.

So probe cheaply. Walk only the far-left spine and only the far-right spine:

- **Equal heights** → nothing is missing anywhere → perfect → return `2^h - 1` without touching a single interior node.
- **Different heights** → the gap is somewhere below → fall back to `1 + count(left) + count(right)`.

The magic is that in a complete tree, at every level **at most one child is imperfect**. The other returns instantly by formula, so you only ever descend along a single path.

</details>

<details name="q3033">
<summary><b>👨‍💻 Code 1 — Brute Force</b> &nbsp; T: O(n) &nbsp; S: O(h)</summary>

```java
public int countNodes(TreeNode root) {
    if (root == null) return 0;
    return 1 + countNodes(root.left) + countNodes(root.right);
}
```

</details>

<details name="q3033">
<summary><b>👨‍💻 Code 2 — Optimal, height probe</b> &nbsp; T: O(log²n) &nbsp; S: O(log n)</summary>

```java
class Solution {
    public int countNodes(TreeNode root) {
        if (root == null) return 0;

        int lh = leftHeight(root);
        int rh = rightHeight(root);

        if (lh == rh) return (1 << lh) - 1;        // PERFECT subtree -> 2^h - 1

        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private int leftHeight(TreeNode node) {        // walk the far-left spine
        int h = 0;
        while (node != null) { h++; node = node.left; }
        return h;
    }

    private int rightHeight(TreeNode node) {       // walk the far-right spine
        int h = 0;
        while (node != null) { h++; node = node.right; }
        return h;
    }
}
```

</details>

---

### 3034: Construct Binary Tree from Preorder and Inorder
https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
Rebuild the unique tree from its preorder and inorder traversals.

<details name="q3034">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Preorder gives you the root; inorder gives you the split.** That sentence is the whole algorithm.
- **Build a `HashMap<value, inorderIndex>` once, up front.** Scanning inorder for the root each time makes it `O(n²)`.
- **`numsLeft = inRoot - inStart` is the index-shifting formula everyone gets wrong.** Derive it every time rather than memorising the four bounds: *the left subtree has `numsLeft` elements, so it occupies the next `numsLeft` slots of preorder.*
- Preorder bounds: left = `[preStart+1, preStart+numsLeft]`, right = `[preStart+numsLeft+1, preEnd]`.
- Inorder bounds: left = `[inStart, inRoot-1]`, right = `[inRoot+1, inEnd]`.
- **Requires unique values** — the map would collide otherwise.
- **Preorder + postorder is NOT enough** to rebuild a tree uniquely. Inorder is what disambiguates. Common interview follow-up.
- Base case is `preStart > preEnd` (empty range), not `preStart == preEnd`.

</details>

<details name="q3034">
<summary><b>🌳 Examples</b></summary>

```
preorder = [3, 9, 20, 15, 7]
inorder  = [9, 3, 15, 20, 7]

3 is the root (first in preorder)
find 3 in inorder at index 1
   -> left  subtree inorder = [9]          (1 element)
   -> right subtree inorder = [15, 20, 7]  (3 elements)
   -> so preorder splits as [3] [9] [20, 15, 7]

            3
          /   \
         9     20
              /  \
            15    7
```

| preorder | inorder | Output |
|---|---|---|
| `[3,9,20,15,7]` | `[9,3,15,20,7]` | `[3,9,20,null,null,15,7]` |
| `[-1]` | `[-1]` | `[-1]` |
| `[]` | `[]` | `[]` |

</details>

<details name="q3034">
<summary><b>💡 Intuition</b></summary>

Each traversal tells you something the other cannot:

| | tells you |
|---|---|
| **preorder** | which node is the **root** (it comes first) |
| **inorder** | **where the root splits** left from right |

So: take the front of preorder → that is the root. Look it up in inorder → everything to its left is the left subtree, everything to its right is the right subtree. Count the left part, use that count to cut preorder into the matching two pieces, and recurse.

Every recursive call is the same problem on a smaller range, so pass **index ranges** instead of copying arrays.

</details>

<details name="q3034">
<summary><b>👨‍💻 Code — Recursive with index bounds</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> idx = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) idx.put(inorder[i], i);

        return build(preorder, 0, preorder.length - 1,
                     inorder,  0, inorder.length  - 1, idx);
    }

    private TreeNode build(int[] pre, int preStart, int preEnd,
                           int[] in,  int inStart,  int inEnd,
                           Map<Integer, Integer> idx) {

        if (preStart > preEnd || inStart > inEnd) return null;

        TreeNode root  = new TreeNode(pre[preStart]);   // preorder -> ROOT
        int inRoot     = idx.get(root.val);             // inorder  -> SPLIT
        int numsLeft   = inRoot - inStart;              // size of the left subtree

        root.left  = build(pre, preStart + 1, preStart + numsLeft,
                           in,  inStart,      inRoot - 1, idx);

        root.right = build(pre, preStart + numsLeft + 1, preEnd,
                           in,  inRoot + 1,              inEnd, idx);

        return root;
    }
}
```

</details>

---

### 3035: Construct Binary Tree from Inorder and Postorder
https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
Same rebuild, but from inorder and postorder.

<details name="q3035">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **The root is the LAST element of postorder**, not the first. Everything else mirrors 3034.
- **This is where the index shifting bites.** Postorder is laid out `[left..., right..., root]`, so the left block starts at `postStart` and the right block ends at `postEnd - 1` (the `-1` skips the root).
- Postorder bounds: left = `[postStart, postStart + numsLeft - 1]`, right = `[postStart + numsLeft, postEnd - 1]`.
- Derive them from `numsLeft` rather than memorising: *left subtree has `numsLeft` nodes, so it fills the first `numsLeft` slots; the right subtree fills the rest except the final root slot.*
- Building **right before left** is a common alternative when consuming postorder from the back with a moving pointer — but with explicit bounds the order does not matter.
- Same requirements as 3034: unique values, `HashMap` for `O(1)` lookup.

</details>

<details name="q3035">
<summary><b>🌳 Examples</b></summary>

```
inorder   = [9, 3, 15, 20, 7]
postorder = [9, 15, 7, 20, 3]
                            ^ root is LAST

3 is the root; find it in inorder at index 1
   -> left  = [9]           (numsLeft = 1)
   -> right = [15, 20, 7]

postorder splits as  [9] [15, 7, 20] [3]
                      ^left  ^right   ^root

            3
          /   \
         9     20
              /  \
            15    7
```

| inorder | postorder | Output |
|---|---|---|
| `[9,3,15,20,7]` | `[9,15,7,20,3]` | `[3,9,20,null,null,15,7]` |
| `[-1]` | `[-1]` | `[-1]` |

</details>

<details name="q3035">
<summary><b>💡 Intuition</b></summary>

Identical to 3034 with one substitution:

| | 3034 | 3035 |
|---|---|---|
| root comes from | `preorder[preStart]` | `postorder[postEnd]` |
| layout | `[root, left..., right...]` | `[left..., right..., root]` |

Inorder still does the splitting; `numsLeft` still drives every bound. Only the arithmetic on the other array changes, because the root moved from the front to the back.

</details>

<details name="q3035">
<summary><b>👨‍💻 Code — Recursive with index bounds</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> idx = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) idx.put(inorder[i], i);

        return build(inorder,   0, inorder.length   - 1,
                     postorder, 0, postorder.length - 1, idx);
    }

    private TreeNode build(int[] in,   int inStart,   int inEnd,
                           int[] post, int postStart, int postEnd,
                           Map<Integer, Integer> idx) {

        if (inStart > inEnd || postStart > postEnd) return null;

        TreeNode root = new TreeNode(post[postEnd]);    // postorder -> ROOT is LAST
        int inRoot    = idx.get(root.val);
        int numsLeft  = inRoot - inStart;

        root.left  = build(in,   inStart,   inRoot - 1,
                           post, postStart, postStart + numsLeft - 1, idx);

        root.right = build(in,   inRoot + 1,            inEnd,
                           post, postStart + numsLeft,  postEnd - 1, idx);

        return root;
    }
}
```

</details>

---

### 3036: Serialize and Deserialize Binary Tree
https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
Encode a tree to a string and rebuild the identical tree from it.

<details name="q3036">
<summary><b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **`ArrayDeque` cannot hold `null`.** Use `LinkedList` for the queue — this is the single most common crash in this problem.
- **Null markers are mandatory.** Without a placeholder for missing children the string is ambiguous and the tree cannot be rebuilt. `"#"` or `"null"` both work.
- Deserialize consumes the tokens **in the exact order** serialize produced them. The queue in `deserialize` holds *nodes waiting for their two children*, which arrive as the next two tokens.
- Handle the **empty tree** at both ends: serialize `null` to `""`, and return `null` on an empty string.
- Values can be **negative or multi-digit**, so split on the delimiter — never read character by character.
- A DFS/preorder encoding works too and is shorter, but BFS output is human-readable and matches LeetCode's own `[1,2,3,null,null,4,5]` format.
- The pairing invariant: after popping a node you read **exactly two** tokens. Break that symmetry and everything after it shifts.

</details>

<details name="q3036">
<summary><b>🌳 Examples</b></summary>

```
        1
      /   \                serialize -> "1,2,3,#,#,4,5,#,#,#,#"
     2     3
          / \              2 is a leaf  -> emits "#,#"
         4   5             4 and 5 are leaves -> emit "#,#" each
```

| Input | Serialized | Round-trip |
|---|---|---|
| `[1,2,3,null,null,4,5]` | `1,2,3,#,#,4,5,#,#,#,#` | identical tree |
| `[]` | `""` | `null` |
| `[1]` | `1,#,#` | `[1]` |

</details>

<details name="q3036">
<summary><b>💡 Intuition</b></summary>

**Serialize:** run ordinary BFS, but emit a marker for every `null` child instead of skipping it. The markers are what make the shape recoverable.

**Deserialize:** read the first token as the root and push it onto a queue. Then repeatedly: pop a node, read the next two tokens — those are its left and right children. Attach the non-null ones and push them, since they in turn are waiting for *their* two tokens.

The queue holds exactly the nodes whose children have not been read yet, so the token stream and the queue stay in lockstep automatically.

</details>

<details name="q3036">
<summary><b>👨‍💻 Code — BFS with null markers</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
public class Codec {

    public String serialize(TreeNode root) {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();      // LinkedList: must accept nulls
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode node = q.poll();

            if (node == null) { sb.append("#,"); continue; }

            sb.append(node.val).append(',');
            q.offer(node.left);                      // enqueue nulls too
            q.offer(node.right);
        }
        return sb.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] tokens = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;

        while (!q.isEmpty() && i < tokens.length) {
            TreeNode parent = q.poll();

            if (!tokens[i].equals("#")) {            // LEFT child
                parent.left = new TreeNode(Integer.parseInt(tokens[i]));
                q.offer(parent.left);
            }
            i++;

            if (i < tokens.length && !tokens[i].equals("#")) {   // RIGHT child
                parent.right = new TreeNode(Integer.parseInt(tokens[i]));
                q.offer(parent.right);
            }
            i++;
        }
        return root;
    }
}
```

</details>
