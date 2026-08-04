# 2001: Unique Paths In Grid
https://leetcode.com/problems/unique-paths/

Complexity
R  : Exp | O(m+n)
M  : O(m*n) | O(m*n)+O(m+n)
T  : O(m*n) | O(m*n)
SO : O(m*n) | O(n)

Intuition
- Counting problem ⇒ Sum all choices.
- Think in reverse: Up & Left (instead of Down & Right).
- State = (i,j) ⇒ dp[m][n].
- Tabulation = Copy recursion as-is; don't derive a new approach.
- Boundary case (i<0 || j<0) becomes 0 in tabulation.

Mistakes
- Overwrote dp[0][0] after assigning base case.

# 2002: Unique Paths II in Grids (With Obstacles)
https://leetcode.com/problems/unique-paths-ii/description/

Intuition
- Same as Unique Paths.
- Obstacle ⇒ Treat as dead path (return 0).
- Check obstacle **before** base case.
- In tabulation, obstacle cell = 0.
- Remaining transition unchanged (up + left).

Mistake
- Misplaced the edge case of obstacle check.


# 2003: Minimum Path Sum in Grid
https://leetcode.com/problems/minimum-path-sum/description/

# 2004: Minimum Path in Triangle Grid
https://leetcode.com/problems/triangle/description/

Complexity
R  : Exp | O(n)
M  : O(n²) | O(n²)+O(n)
T  : O(n²) | O(n²)
SO : O(n²) | O(n)

Intuition
- Optimization problem → min(down, diagonal) + current.
- Start from (0,0) because destination can be any element in the last row.
- State = (i,j) → dp[n][n].
- Base case = last row → return current value.
- Tabulation starts from the last row (base case) and moves upward.

Mistakes
- Don't start from bottom like Unique Paths.
- Last row is the base case, not (0,0).
- Fill DP bottom → top in tabulation.