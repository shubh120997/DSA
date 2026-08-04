# Binary Trees - Revision Notes

## Terminology
- **Node**: Basic element of the tree.
- **Root**: Top-most node.
- **Parent / Child**: Direct connection.
- **Sibling**: Same parent.
- **Leaf**: No children.
- **Internal Node**: At least one child.
- **Ancestor / Descendant**: Upward / downward relationship.

---

# Binary Tree
- Each node has **at most 2 children**.
- Left and right child are different.
- No cycles.

### Types
- **Full** → Every node has 0 or 2 children.
- **Complete** → Last level filled left to right.
- **Perfect** → All leaves at same level & every internal node has 2 children.
- **Balanced** → Height = O(log n).
- **Skewed (Degenerate)** → Every node has only one child.

---

# Important Formulae
- Max nodes at level **L** = `2^L` *(Root at level 0)*
- Max nodes in height **H** = `2^(H+1) - 1`
- Min height for **N** nodes = `⌈log₂(N + 1)⌉ - 1`
- Max height = `N - 1`

---

# Java Representation

```java
class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }
}
```

---

# DFS Traversals

### Preorder
```
Root → Left → Right
```
- Copy tree
- Serialization

### Inorder
```
Left → Root → Right
```
- Sorted order **only in BST**

### Postorder
```
Left → Right → Root
```
- Delete tree
- Expression evaluation

---

# BFS (Level Order)

- Visit level by level.
- Uses **Queue**.

---

# Recursive Template

```java
void dfs(TreeNode root) {
    if (root == null) return;

    // Preorder

    dfs(root.left);

    // Inorder

    dfs(root.right);

    // Postorder
}
```

---

# Time & Space Complexity

| Traversal | Time | Extra Space |
|-----------|------|-------------|
| DFS | O(n) | O(h) |
| BFS | O(n) | O(w) |

- `h` = Height of tree
- `w` = Maximum width

---

# Pre + In + Post in One Traversal

**Data Structure**
```java
Stack<Pair<TreeNode, Integer>>
```

**State Meaning**
- `1` → Preorder
- `2` → Inorder
- `3` → Postorder

**Flow**
1. Pop node.
2. State 1 → Process preorder → Push (2) → Push left.
3. State 2 → Process inorder → Push (3) → Push right.
4. State 3 → Process postorder.

Time: **O(n)**

Space: **O(n)**

---

# Remember

- Base case → `if(root == null) return;`
- DFS recursion space depends on **height**, not number of nodes.
- Balanced tree → O(log n) recursion stack.
- Skewed tree → O(n) recursion stack.
- Inorder is sorted **only for BST**.
- Level Order always uses **Queue**.

---

# Common Mistakes

- Forgetting null check.
- Mixing traversal order.
- Confusing height with levels.
- Assuming inorder is always sorted.
- Ignoring recursion stack in skewed trees.