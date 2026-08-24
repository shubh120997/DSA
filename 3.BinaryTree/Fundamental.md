# Binary Tree — Fundamentals

> Prerequisite reading for every `3xxx` question file in this folder.
> Read top-to-bottom on R1. On R2/R3, skim the **bold** lines and the tables.

---

## 1. Terminology

| Term | Meaning |
|------|---------|
| **Node** | Basic element — holds a value + links to children |
| **Root** | Top-most node. The only node with no parent |
| **Parent / Child** | Direct link, one level apart |
| **Sibling** | Two nodes sharing the same parent |
| **Leaf** | Node with **no children** (`left == null && right == null`) |
| **Internal node** | Has at least one child |
| **Ancestor / Descendant** | Any node above / below on the same root-path |
| **Subtree** | A node **plus everything under it** — itself a valid tree |

**The most useful idea here:** every node is the root of its own subtree.
That is *why* recursion works on trees — the recursive call gets a smaller version of the exact same problem.

---

## 2. Height vs Depth vs Level ⚠️

The #1 source of off-by-one bugs. They are **three different things**.

```
                1          <- level 0, depth 0, height 2
              /   \
             2     3       <- level 1, depth 1, height(2)=1  height(3)=0
            / \
           4   5           <- level 2, depth 2, height 0
```

| | Measured from | Direction | Leaf value | Root value |
|---|---|---|---|---|
| **Depth** | root → node | downward | 2 | 0 |
| **Height** | node → deepest leaf | upward | 0 | 2 |
| **Level** | same as depth | downward | 2 | 0 |

**Height of the tree = height of the root.**

### The nodes-vs-edges trap

Both can be counted in **edges** or in **nodes**, and problems disagree:

| Convention | `null` returns | leaf returns | tree above |
|---|---|---|---|
| **Edges** | `-1` | `0` | 2 |
| **Nodes** | `0` | `1` | 3 |

- LeetCode **"Maximum Depth"** (3006) wants **nodes** → answer `3`
- LeetCode **"Diameter"** (3008) wants **edges** → answer `3` (coincidence here)

> **Rule:** pick the *nodes* convention (`null → 0`) as your default — it is the simpler base case. Convert at the end if the problem wants edges: `edges = nodes - 1`.

---

## 3. Types of Binary Trees

| Type | Definition | Why it matters |
|---|---|---|
| **Full** (strict) | Every node has **0 or 2** children | No node has exactly one child |
| **Complete** | All levels full except possibly the last, which fills **left to right** | Enables array indexing → heaps, 3033 |
| **Perfect** | All leaves at same level, every internal node has 2 children | `n = 2^(h+1) - 1` |
| **Balanced** | Height is `O(log n)` (LC 3007: `|lh - rh| ≤ 1` at every node) | Guarantees `O(log n)` operations |
| **Skewed** (degenerate) | Every node has one child — a linked list | **Worst case**: `h = n-1`, stack overflow risk |

Every complexity you write should be sanity-checked against **skewed**, not balanced.

---

## 4. Formulae

Root at **level 0**, height in **edges**.

| Quantity | Formula |
|---|---|
| Max nodes at level `L` | `2^L` |
| Max nodes in a tree of height `H` | `2^(H+1) - 1` |
| Min height for `N` nodes | `⌈log₂(N + 1)⌉ - 1` |
| Max height for `N` nodes | `N - 1` (skewed) |
| Leaves in a **full** tree with `I` internal nodes | `I + 1` |

---

## 5. Java Representation

```java
class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val; this.left = left; this.right = right;
    }
}
```

<details name="fund">
<summary>Building a test tree by hand (for local debugging)</summary>

```java
//       1
//     /   \
//    2     3
//   / \
//  4   5
TreeNode root = new TreeNode(1,
                    new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                    new TreeNode(3));
```

LeetCode's `[1,2,3,4,5]` is **level-order with nulls**, not preorder. `[1,null,2]` means root `1` with only a *right* child.

</details>

---

## 6. DFS Traversals

Only the **position of the `visit`** changes. The recursion is identical.

| Order | Sequence | Primary use |
|---|---|---|
| **Preorder** | `Root → Left → Right` | Copy a tree, serialize, build from preorder+inorder |
| **Inorder** | `Left → Root → Right` | **Sorted output — only for BST** |
| **Postorder** | `Left → Right → Root` | Delete a tree, evaluate expressions, **any bottom-up computation** |

**Postorder is the workhorse.** Height, diameter, balanced-check, max path sum — all postorder, because each needs its children's results *before* it can compute its own.

### Recursive template

```java
void dfs(TreeNode node) {
    if (node == null) return;        // base case — ALWAYS first

    // (1) PREORDER position — before descending
    dfs(node.left);
    // (2) INORDER position — between children
    dfs(node.right);
    // (3) POSTORDER position — after both, results available here
}
```

<details name="fund">
<summary>Iterative templates — preorder / inorder / postorder</summary>

**Preorder** — push right first, so left pops first.
```java
Deque<TreeNode> st = new ArrayDeque<>();
if (root != null) st.push(root);
while (!st.isEmpty()) {
    TreeNode node = st.pop();
    out.add(node.val);
    if (node.right != null) st.push(node.right);   // right BEFORE left
    if (node.left  != null) st.push(node.left);
}
```

**Inorder** — go left as far as possible, then process, then go right.
```java
Deque<TreeNode> st = new ArrayDeque<>();
TreeNode cur = root;
while (cur != null || !st.isEmpty()) {
    while (cur != null) { st.push(cur); cur = cur.left; }
    cur = st.pop();
    out.add(cur.val);
    cur = cur.right;
}
```

**Postorder (2-stack)** — easiest to remember: reverse of `Root → Right → Left`.
```java
Deque<TreeNode> st = new ArrayDeque<>(), out2 = new ArrayDeque<>();
if (root != null) st.push(root);
while (!st.isEmpty()) {
    TreeNode node = st.pop();
    out2.push(node);
    if (node.left  != null) st.push(node.left);
    if (node.right != null) st.push(node.right);
}
while (!out2.isEmpty()) out.add(out2.pop().val);
```

</details>

<details name="fund">
<summary>All three orders in ONE traversal (state machine)</summary>

`Stack<Pair<TreeNode, Integer>>` where the int is the **state**:

| State | Meaning | Action |
|---|---|---|
| `1` | first visit | add to **preorder**, bump to `2`, push `left` |
| `2` | back from left | add to **inorder**, bump to `3`, push `right` |
| `3` | back from right | add to **postorder**, pop for good |

Time `O(n)` · Space `O(n)`.
Useful when you need all three but can only walk the tree once.

</details>

---

## 7. BFS — Level Order

Uses a **Queue**, never a stack.

```java
Queue<TreeNode> q = new ArrayDeque<>();
if (root != null) q.offer(root);

while (!q.isEmpty()) {
    int size = q.size();              // ⚠️ SNAPSHOT before the loop — q grows inside
    List<Integer> level = new ArrayList<>();

    for (int i = 0; i < size; i++) {
        TreeNode node = q.poll();
        level.add(node.val);
        if (node.left  != null) q.offer(node.left);
        if (node.right != null) q.offer(node.right);
    }
    result.add(level);
}
```

**The `int size = q.size()` line is the whole trick.** Without it you cannot tell where one level ends.

> ⚠️ `ArrayDeque` **cannot hold `null`**. If a problem needs null markers in the queue (e.g. 3036 Serialize/Deserialize), use `LinkedList` instead.

---

## 8. Complexity

| | Time | Extra Space | Worst-case space |
|---|---|---|---|
| **DFS** (recursive) | `O(n)` | `O(h)` — call stack | `O(n)` skewed |
| **DFS** (iterative) | `O(n)` | `O(h)` — explicit stack | `O(n)` skewed |
| **BFS** | `O(n)` | `O(w)` — queue | `O(n/2) = O(n)` at the last level of a perfect tree |

`n` = nodes · `h` = height · `w` = max width

- Balanced → `h = O(log n)` → DFS stack is cheap
- Skewed → `h = O(n)` → DFS can blow the stack; BFS is safer
- **Every node is visited exactly once** in all of these → time is always `O(n)`

---

## 9. The Recursion Contract (mental model)

Before writing any tree recursion, answer three questions:

1. **Base case** — what do I return for `null`? (usually `0`, `true`, or `null`)
2. **Return value** — what does my *parent* need from me? This is a **contract**.
3. **Combine** — given `l` and `r` from my children, what do I compute?

> **Never bend the return value to make the answer come out.**
> If the answer isn't the same shape as the contract, track it in a **separate variable** (field, or `int[1]`) and update it on the way up.

That split — *return the contract, track the answer* — is the core of 3006–3009 and half of this folder.

---

## 10. Remember

- Base case first: `if (node == null) return ...;`
- DFS stack depth follows **height**, not node count.
- Inorder is sorted **only for a BST**.
- Level order always uses a **Queue**; DFS always uses a **Stack** (or recursion).
- A subtree is a full tree — recurse without special-casing.
- `n` nodes → **`n-1` edges**, always.

---

## 11. Common Mistakes

- Forgetting the `null` check → `NullPointerException` on the first leaf.
- Not snapshotting `q.size()` in BFS → levels merge into one blob.
- Confusing **height** (up, edges) with **depth** (down) with **level order count**.
- Assuming inorder is sorted for a plain binary tree.
- Returning the *global answer* instead of the *contract* from a recursive helper (or vice versa).
- Using `ArrayDeque` where `null` must be stored.
- Ignoring the skewed-tree case when stating space complexity.
