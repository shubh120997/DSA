📋 [Questions.md](./Questions.md) — *practice list only; every link is also below*

<!-- #region ⚡ Quick Revision -->
<details>
<summary><b>⚡ Quick Revision — all 11 in one screen</b></summary>

- **4001 · Assign Cookies** &nbsp;·&nbsp; `O(n log n)` &nbsp;·&nbsp; [🔗](https://leetcode.com/problems/assign-cookies/)<br>
  *maximise matches between two sorted needs* → sort both, two pointers, cookie always moves and child moves only on a feed
- **4003 · Shortest Job First (SJF)** &nbsp;·&nbsp; `O(n log n)` &nbsp;·&nbsp; [🔗](https://www.geeksforgeeks.org/problems/shortest-job-first/1)<br>
  *minimise the average wait* → sort bursts ascending, running prefix sum of waits, divide by n
- **4004 · Jump Game I** &nbsp;·&nbsp; `O(n)` &nbsp;·&nbsp; [🔗](https://leetcode.com/problems/jump-game/description/)<br>
  *reachability, not path* → sweep tracking farthest reach, fail the moment `i > farthest`
- **4006 · Job Sequencing Problem** &nbsp;·&nbsp; `O(n log n + n·maxD)` &nbsp;·&nbsp; [🔗](https://www.geeksforgeeks.org/problems/job-sequencing-problem-1587115620/1)<br>
  *pick best subset + place it* → sort by profit desc, put each job in the latest free slot before its deadline
- **4007 · Maximum Meetings in One Room** &nbsp;·&nbsp; `O(n log n)` &nbsp;·&nbsp; [🔗](http://geeksforgeeks.org/problems/maximum-meetings-in-one-room/1)<br>
  *most non-overlapping intervals* → sort by end time, take any meeting starting after the last one ended
- **4008 · Non-overlapping Intervals** &nbsp;·&nbsp; `O(n log n)` &nbsp;·&nbsp; [🔗](https://leetcode.com/problems/non-overlapping-intervals/description/)<br>
  *fewest deletions to remove overlap* → same as 4007 — maximise keeps by end time, answer is `n - kept`
- **4009 · Insert Interval** &nbsp;·&nbsp; `O(n)` &nbsp;·&nbsp; [🔗](https://leetcode.com/problems/insert-interval/description/)<br>
  *sorted list, one insertion* → three loops — emit before, widen while touching, emit after
- **4010 · Minimum Platforms** &nbsp;·&nbsp; `O(n log n)` &nbsp;·&nbsp; [🔗](https://www.geeksforgeeks.org/problems/minimum-platforms-1587115620/1)<br>
  *peak concurrency* → sort arrivals and departures separately, sweep, track max occupancy
- **4011 · Valid Parenthesis String** &nbsp;·&nbsp; `O(n)` &nbsp;·&nbsp; [🔗](https://leetcode.com/problems/valid-parenthesis-string/description/)<br>
  *wildcard, exponential branching* → carry `[low, high]` range of open counts, clamp low at 0, end at `low == 0`
- **4012 · Candy** &nbsp;·&nbsp; `O(n)` &nbsp;·&nbsp; [🔗](https://leetcode.com/problems/candy/description/)<br>
  *each element constrained by both neighbours* → two sweeps, left then right, merge with `max`
- **4013 · Fractional Knapsack** &nbsp;·&nbsp; `O(n log n)` &nbsp;·&nbsp; [🔗](https://www.geeksforgeeks.org/problems/fractional-knapsack-1587115620/1)<br>
  *scarce capacity, divisible items* → sort by value/weight desc, take whole items then one fraction

<sub>Read a line → can you write the code? Yes, skip. No, open that question below. 🔗 goes straight to the judge — no need to leave this file.</sub>

</details>

---
<!-- #endregion -->

<!-- #region 4001 · Assign Cookies -->
<details name="greedy">
<summary><b>4001 · Assign Cookies</b></summary>

---

🔗 https://leetcode.com/problems/assign-cookies/

Each child needs a cookie of size ≥ its greed; maximise children fed.

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Sort both arrays** — greedy only holds on sorted input.
- Cookie pointer `j` always moves; child pointer `i` moves **only on a feed**.
- `s[j] >= g[i]` — exact fit counts. `>` is the off-by-one.
- Answer is `i` itself. No counter.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

**Trace** — skips and surplus, not just feeds:

```
g = [1, 3, 5]   s = [2, 2, 4, 6]

i=0 g=1 | j=0 s=2  2>=1  FEED  i=1 j=1
i=1 g=3 | j=1 s=2  2<3   SKIP  i=1 j=2
i=1 g=3 | j=2 s=4  4>=3  FEED  i=2 j=3
i=2 g=5 | j=3 s=6  6>=5  FEED  i=3 j=4

i = 3 -> all three fed
```

**Why not "biggest cookie to the least greedy child?"**

```
g = [1, 5]   s = [2, 6]

biggest-first:
  6 -> child(1)  ✅
  2 -> child(5)  2 < 5  ❌   = 1 fed

smallest-fit:
  2 -> child(1)  ✅
  6 -> child(5)  ✅          = 2 fed
```

Spending a size-6 cookie on a child who needs 1 throws away the
only cookie that could feed child 5.

**Why sorting is not optional:**

```
g = [10, 1]   s = [1, 10]      (unsorted)

two pointers on raw input:
  g=10 | s=1   skip cookie
  g=10 | s=10  FEED           = 1 fed ❌

after sorting g=[1,10] s=[1,10]:
  1>=1 FEED, 10>=10 FEED     = 2 fed ✅
```

**The pointer bug** — advancing `i` on a failed feed:

```
g = [2, 2]   s = [1, 2, 2]

correct : s=1<2 skip
          s=2 FEED, s=2 FEED     = 2 ✅
i++ bug : s=1<2 but i++ anyway
          child 1 abandoned      = 1 ❌
```

- **`g=[1,3,5]`, `s=[2,2,4,6]`** — `3` — surplus cookies are simply skipped
- **`g=[1,5]`, `s=[2,6]`** — `2` — only smallest-fit gets both
- **`g=[10,1]`, `s=[1,10]`** — `2` — wrong answer `1` if unsorted
- **`g=[2,2]`, `s=[1,2,2]`** — `2` — catches the `i++` bug
- **`g=[2]`, `s=[2]`** — `1` — exact fit counts, so `>=` not `>`
- **`g=[5]`, `s=[1,2,3]`** — `0` — `j` runs off the end, loop guard matters

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Sort both arrays ascending.
2. Two pointers — child `i`, cookie `j`.
3. Give the least greedy unfed child the smallest cookie that fits.
4. A cookie too small now is too small for everyone after → `j++` always.
5. Return `i` — the number of children passed.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Brute Force</b> &nbsp; T: O(n·m) &nbsp; S: O(m)</summary>

```java
private int bruteForce(int[] g, int[] s) {
    boolean[] used = new boolean[s.length];
    int count = 0;

    for (int i = 0; i < g.length; i++) {
        int best = -1;
        for (int j = 0; j < s.length; j++) {
            // smallest unused cookie that still fits child i
            if (!used[j] && g[i] <= s[j]
                    && (best == -1 || s[best] > s[j])) {
                best = j;
            }
        }
        if (best != -1) {
            used[best] = true;
            count++;
        }
    }
    return count;
}
```

Rescans every cookie per child. Sorting removes the rescan.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — Sort + Two Pointers</b> &nbsp; T: O(n log n) &nbsp; S: O(1)</summary>

```java
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        // i = child, j = cookie
        int i = 0, j = 0;
        while (i < g.length && j < s.length) {
            // fed -> next child
            if (s[j] >= g[i]) {
                i++;
            }
            // cookie spent either way
            j++;
        }
        return i;
    }
}
```

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4003 · Shortest Job First (SJF) -->
<details name="greedy">
<summary><b>4003 · Shortest Job First (SJF)</b></summary>

---

🔗 https://www.geeksforgeeks.org/problems/shortest-job-first/1

All processes arrive at time 0; return the **average waiting time** (floor).

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Sort burst times ascending.** `bt[i]` delays every process after it, so the smallest burst must carry the largest multiplier.
- Waiting time = **sum of bursts before you**, not including your own. First process waits `0`.
- Add `wait` to the total **before** adding `bt[i]` to `wait` — swapping the two lines counts your own burst.
- Return `total / n` — integer division, GFG expects the floor.
- Waiting = turnaround − burst. Problem asks for waiting; don't return turnaround.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
bt = [4,3,7,1,2] -> sorted [1,2,3,4,7]

job    1   2   3   4   7
wait   0   1   3   6  10

total = 20  ->  20 / 5 = 4
```

- **`[4,3,7,1,2]`** — `4`
- **`[1]`** — `0` (nobody waits)
- **`[5,5,5]`** — `5` — `(0+5+10)/3`
- **`[1,2,3]`** — `1` — `(0+1+3)/3 = 4/3 = 1` (floor)

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Sort burst times ascending.
2. Each job's wait = sum of all bursts before it.
3. Sweep once: add `wait` to `total`, then add this burst to `wait`.
4. Return `total / n` (floor).

Why ascending: `bt[i]` is paid by the `n-1-i` jobs behind it — small numbers deserve the big multiplier.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Sort + Running Wait</b> &nbsp; T: O(n log n) &nbsp; S: O(1)</summary>

```java
static int solve(int bt[]) {
    Arrays.sort(bt);
    int wait = 0, total = 0;
    for (int b : bt) {
        // BEFORE — you don't wait on yourself
        total += wait;
        wait  += b;
    }
    return total / bt.length;
}
```

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — Sort + Weighted Sum</b> &nbsp; T: O(n log n) &nbsp; S: O(1)</summary>

```java
static int solve(int bt[]) {
    Arrays.sort(bt);
    int n = bt.length, total = 0;
    // burst paid by everyone after it
    for (int i = 0; i < n; i++) {
        total += bt[i] * (n - 1 - i);
    }
    return total / n;
}
```

Same answer, states the "small numbers get the big multiplier" idea directly. Watch overflow if bursts are large — use `long total`.

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4004 · Jump Game I -->
<details name="greedy">
<summary><b>4004 · Jump Game I</b></summary>

---

🔗 https://leetcode.com/problems/jump-game/

`nums[i]` is the **max** jump length from `i` — can you reach the last index?

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- Track **farthest reachable index**, not the path. You never decide *how far* to jump — only whether `i` is still reachable.
- Bail the moment `i > farthest` — that index is unreachable, so everything after it is too.
- `nums[i]` is a **maximum**, not an exact step. Any length `1..nums[i]` is allowed, which is why farthest-reach works.
- A `0` is only fatal if nothing can jump **over** it. `[3,0,0,0]` reaches the end fine.
- Success test is `farthest >= n-1`, not `== n-1`.
- No DP needed. Return type is boolean — reachability, not jump count (that is Jump Game II).

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
nums = [2, 3, 1, 1, 4]
i  0  1  2  3  4
f  2  4  4  4  4
-> 4 >= 4   reachable  ✅

nums = [3, 2, 1, 0, 4]
i  0  1  2  3
f  3  3  3  3
-> i=4 > f=3   STUCK   ❌
```

- **`[2,3,1,1,4]`** — `true`
- **`[3,2,1,0,4]`** — `false` — the `0` at index 3 can't be jumped over
- **`[0]`** — `true` — already at the last index
- **`[0,1]`** — `false`
- **`[3,0,0,0]`** — `true` — index 0 jumps clear over every zero

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Track the farthest index reachable so far.
2. Sweep left to right.
3. `i > farthest` → wall, nothing beyond is reachable → `false`.
4. Otherwise extend: `farthest = max(farthest, i + nums[i])`.
5. Survive the sweep → `true`.

Backward variant: hold the leftmost index known to reach the end, walk right-to-left, shrink the goal whenever `i + nums[i] >= goal`.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Forward Greedy (farthest reach)</b> &nbsp; T: O(n) &nbsp; S: O(1)</summary>

```java
public boolean canJump(int[] nums) {
    int farthest = 0;
    for (int i = 0; i < nums.length; i++) {
        // wall — can't even stand here
        if (i > farthest) {
            return false;
        }
        farthest = Math.max(farthest, i + nums[i]);
    }
    // survived the sweep
    return true;
}
```

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — Backward Greedy (shrinking goal)</b> &nbsp; T: O(n) &nbsp; S: O(1)</summary>

```java
public boolean canJump(int[] nums) {
    int goal = nums.length - 1;
    for (int i = nums.length - 2; i >= 0; i--) {
        // i can reach goal -> i is the new goal
    }
    if (i + nums[i] >= goal) {
        goal = i;
    }
    return goal == 0;
}
```

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4006 · Job Sequencing Problem -->
<details name="greedy">
<summary><b>4006 · Job Sequencing Problem</b></summary>

---

🔗 https://www.geeksforgeeks.org/problems/job-sequencing-problem-1587115620/1

Each job takes 1 unit of time and must finish by its deadline; maximise total profit.

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Sort by profit descending**, not by deadline. Deadline only decides *where* a job goes, profit decides *whether* it gets a slot.
- Place each job in the **latest free slot ≤ its deadline**. Placing it early wastes a slot a tighter job may need.
- Slots are **1-indexed** — deadline `2` means slot 1 or 2. Sizing the array `maxDeadline` instead of `maxDeadline + 1` is the off-by-one.
- A job that finds no free slot is simply **dropped**, not retried later.
- Answer is a pair: **count of jobs done + total profit**. Easy to return only the profit.
- ⚠️ **Most optimal approach left out: DSU.** `find(d)` returns the latest free slot ≤ `d` in near-`O(1)`, turning the inner downward scan into `O(n log n)` overall instead of `O(n · maxDeadline)`. Come back and write it after studying Disjoint Set Union — the greedy choice is identical, only the slot lookup changes.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
jobs (id, deadline, profit)
 (1,4,20) (2,1,10) (3,1,40) (4,1,30)

by profit desc:
 40/d1   30/d1   20/d4   10/d1

40 -> slot 1 free    [40][ ][ ][ ]   ✅
30 -> d1 slot taken                  ❌
20 -> slot 4 free    [40][ ][ ][20]  ✅
10 -> d1 slot taken                  ❌

count = 2, profit = 60
```

- **`(4,20) (1,10) (1,40) (1,30)`** — `2  60`
- **`(2,100) (1,19) (2,27) (1,25) (1,15)`** — `2  127`
- **all deadline `1`** — `1  maxProfit` — only one slot exists
- **deadlines ≥ n** — `n  sumOfAll` — everything fits

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Create Job objects (deadline, profit).
2. Sort by profit descending — profit decides *which* jobs run, deadline decides *where* they sit.
3. Size the slot array to the max deadline, 1-indexed.
4. For each job, scan down from its deadline for the latest free slot.
5. Free slot → occupy it, add the profit. No free slot → drop the job.
6. Return count and total profit.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Sort + Backward Slot Scan</b> &nbsp; T: O(n log n + n·maxD) &nbsp; S: O(maxD)</summary>

```java
// jobs[i] = {deadline, profit}
static int[] jobSequencing(int[][] jobs) {
    // profit DESC
    Arrays.sort(jobs, (a, b) -> b[1] - a[1]);

    int maxD = 0;
    for (int[] j : jobs) {
        maxD = Math.max(maxD, j[0]);
    }
    // slot[0] unused — 1-indexed
    boolean[] slot = new boolean[maxD + 1];

    int count = 0, profit = 0;
    for (int[] j : jobs) {
        // LATEST free slot first
    }
    for (int t = j[0]; t >= 1; t--) {
        if (!slot[t]) {
            slot[t] = true; count++; profit += j[1]; break;
        }
    }

    return new int[]{count, profit};
}
```

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — DSU</b> &nbsp; T: O(n log n) &nbsp; S: O(maxD) &nbsp; ⏳ not studied yet</summary>

Deliberately left unwritten — see Remarks. Same sort, same greedy; only the "find latest free slot ≤ d" step is replaced by a Disjoint Set Union `find` with path compression, so the inner `for t--` loop disappears.

Write this section once DSU is covered.

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4007 · Maximum Meetings in One Room -->
<details name="greedy">
<summary><b>4007 · Maximum Meetings in One Room</b></summary>

---

🔗 http://geeksforgeeks.org/problems/maximum-meetings-in-one-room/1

One room, N meetings `(start, end)` — attend the most meetings and report which ones.

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Sort by end time, never by start or by duration.** Finishing earliest leaves the most room behind it. Sorting by start or shortest-duration both fail.
- Carry the **original 1-based index** inside the object — sorting destroys input order and the answer is the meeting *numbers*.
- Condition is `start > lastEnd`, **strictly greater**. GFG forbids a meeting starting exactly when the previous ends. (LeetCode interval variants usually allow `>=` — check per problem.)
- Tie on end time → **smaller index first** in the comparator, or the reported order is wrong even though the count is right.
- `lastEnd = -1` to start, so meeting at time `0` is still selectable.
- Sort the collected indices ascending before returning — the count is what's scored, the order is what the checker compares.
- Same skeleton as [[4006]] job sequencing: sort by the right key, then one linear pass of accept/reject.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
meeting #   1  2  3  4  5  6
start       1  3  0  5  8  5
end         2  4  6  7  9  9

sorted by end:
 #1(1,2) #2(3,4) #3(0,6)
 #4(5,7) #5(8,9) #6(5,9)

#1  1 > -1  ✅  last=2
#2  3 >  2  ✅  last=4
#3  0 >  4  ❌
#4  5 >  4  ✅  last=7
#5  8 >  7  ✅  last=9
#6  5 >  9  ❌

4 meetings ->  1 2 4 5
```

- **above** — `1 2 4 5`
- **`start=[10,12,20]`, `end=[20,25,30]`** — `1 3` — #2 overlaps #1
- **one meeting** — that meeting
- **all identical times** — `1` — only one fits
- **`end` of A `==` `start` of B** — B is **rejected** (strict `>`)

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Create Meeting objects carrying `start`, `end` and the original 1-based position.
2. Sort by finish time ascending, ties broken by smaller position.
3. Walk once, greedily selecting the next meeting whose `start > lastEnd`, and update `lastEnd`.
4. Sort the selected positions before returning.

Why earliest-finish wins: whichever meeting you take, the only thing that matters afterwards is *when the room frees up*. So take the one that frees it soonest — every schedule that picks something else can be swapped to this choice without losing a meeting.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Sort by End + Greedy Pick</b> &nbsp; T: O(n log n) &nbsp; S: O(n)</summary>

```java
static ArrayList<Integer> maxMeetings(int n, int start[], int end[]) {
    // {end, start, index}
    int[][] m = new int[n][3];
    for (int i = 0; i < n; i++) {
        m[i] = new int[]{end[i], start[i], i + 1};
    }

    // end ASC, then index
    Arrays.sort(m, (a, b) ->
            a[0] != b[0] ? a[0] - b[0] : a[2] - b[2]);

    ArrayList<Integer> ans = new ArrayList<>();
    int lastEnd = -1;
    for (int[] mt : m) {
        // strictly greater
    }
    if (mt[1] > lastEnd) {
        ans.add(mt[2]);
        lastEnd = mt[0];
    }

    Collections.sort(ans);
    return ans;
}
```

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4008 · Non-overlapping Intervals -->
<details name="greedy">
<summary><b>4008 · Non-overlapping Intervals</b></summary>

---

🔗 https://leetcode.com/problems/non-overlapping-intervals/

Remove the fewest intervals so none of the rest overlap.

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Don't count removals — count keeps.** Maximise how many intervals survive, then `answer = n - kept`. Trying to greedily pick what to delete is the trap.
- Sort by **end**, exactly as in [[4007]]. This *is* activity selection wearing a different question.
- Condition is `start >= lastEnd`, **`>=` here**. `[1,2]` and `[2,3]` touch but do not overlap. (Contrast [[4007]], where GFG demands strict `>` — same algorithm, opposite boundary.)
- Sorting by **start** feels natural and is wrong: one huge early interval then eats everything.
- `lastEnd = Integer.MIN_VALUE` so the first interval is always kept.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
[[1,2],[2,3],[3,4],[1,3]]
sorted by end:
 [1,2] [1,3] [2,3] [3,4]

[1,2]  1 >= MIN  ✅ keep  last=2
[1,3]  1 >=  2   ❌
[2,3]  2 >=  2   ✅ keep  last=3
[3,4]  3 >=  3   ✅ keep  last=4

kept = 3  ->  4 - 3 = 1 removal
```

Why sorting by start fails:

```
[[1,100],[2,3],[4,5]]

by start: keep [1,100]      -> 2 removals
by end  : keep [2,3],[4,5]  -> 1 removal ✅
```

- **`[[1,2],[2,3],[3,4],[1,3]]`** — `1`
- **`[[1,2],[1,2],[1,2]]`** — `2`
- **`[[1,2],[2,3]]`** — `0` — touching is fine
- **single interval** — `0`

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Sort intervals by end time ascending.
2. Keep a `lastEnd` cursor, starting at negative infinity.
3. Walk once — if `start >= lastEnd` keep the interval and move `lastEnd` to its end, else skip it.
4. Return `n - kept`.

Fewest removals and most keeps are the same question asked backwards, and "most keeps" is plain activity selection: finishing earliest leaves the most room for what follows.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Brute Force (keep or remove)</b> &nbsp; T: O(2ⁿ) &nbsp; S: O(n)</summary>

```java
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals,
            (a, b) -> Integer.compare(a[0], b[0]));
    return intervals.length - solve(intervals, 0, -1);
}

// max intervals KEPT
private int solve(int[][] iv, int i, int prev) {
    if (i == iv.length) {
        return 0;
    }
    int skip = solve(iv, i + 1, prev);
    int take = 0;
    if (prev == -1 || iv[i][0] >= iv[prev][1]) {
        take = 1 + solve(iv, i + 1, i);
    }
    return Math.max(take, skip);
}
```

Every interval is kept or dropped — the full `2ⁿ` decision tree.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — DP (longest compatible chain)</b> &nbsp; T: O(n²) &nbsp; S: O(n)</summary>

```java
public int eraseOverlapIntervals(int[][] intervals) {
    int n = intervals.length;
    if (n == 0) {
        return 0;
    }
    Arrays.sort(intervals,
            (a, b) -> Integer.compare(a[0], b[0]));

    // dp[i] = best chain ending at i
    int[] dp = new int[n];
    Arrays.fill(dp, 1);
    int best = 1;
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (intervals[j][1] <= intervals[i][0]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        best = Math.max(best, dp[i]);
    }
    return n - best;
}
```

Literally Longest Increasing Subsequence with "non-overlapping" as the comparison. Correct, but the greedy makes the inner loop unnecessary.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 3 — Sort by End + Count Keeps</b> &nbsp; T: O(n log n) &nbsp; S: O(1)</summary>

```java
public int eraseOverlapIntervals(int[][] intervals) {
    // end ASC
    Arrays.sort(intervals,
            (a, b) -> Integer.compare(a[1], b[1]));

    int kept = 0, lastEnd = Integer.MIN_VALUE;
    for (int[] it : intervals) {
        // >= : touching is OK
    }
    if (it[0] >= lastEnd) {
        kept++;
        lastEnd = it[1];
    }

    return intervals.length - kept;
}
```

`Integer.compare` over `a[1] - b[1]` — subtraction overflows on extreme values.

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4009 · Insert Interval -->
<details name="greedy">
<summary><b>4009 · Insert Interval</b></summary>

---

🔗 https://leetcode.com/problems/insert-interval/

Insert one interval into an already-sorted non-overlapping list, merging what it touches.

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- Input is **already sorted and non-overlapping** — do not re-sort. That gift is what makes this `O(n)`.
- **Three separate `while` loops, not an if/else chain**: before → merge → after. Each runs on the same `i`.
- Phase 1 test is `end < newStart` (strictly before). Phase 2 test is `start <= newEnd` (touching merges). Mixing up `<` and `<=` is the whole bug.
- Merge by **widening**, not replacing: `s = min(s, cur.start)`, `e = max(e, cur.end)`. The new interval can be swallowed by a bigger existing one.
- Add the merged interval **once**, after phase 2 — not inside the loop.
- Don't mutate `newInterval` in place; copy into locals `s`, `e`.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
intervals:
 [1,2] [3,5] [6,7] [8,10] [12,16]
new = [4,8]

ph1  [1,2]     end 2 < 4    -> emit
ph2  [3,5]      3 <= 8  merge s=3 e=8
     [6,7]      6 <= 8  merge s=3 e=8
     [8,10]     8 <= 8  merge s=3 e=10
     [12,16]   12 > 10  stop
                        emit [3,10]
ph3  [12,16]                -> emit

result = [1,2] [3,10] [12,16]
```

- **`[[1,3],[6,9]]`, `new=[2,5]`** — `[[1,5],[6,9]]`
- **`[]`, `new=[5,7]`** — `[[5,7]]` — all three loops skip
- **`[[1,5]]`, `new=[2,3]`** — `[[1,5]]` — new one is swallowed
- **`[[1,5]]`, `new=[6,8]`** — `[[1,5],[6,8]]` — appended at the end
- **`[[3,5]]`, `new=[1,2]`** — `[[1,2],[3,5]]` — inserted at the front

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Emit every interval that ends strictly before the new one starts — untouched.
2. While the next interval starts at or before the new one's end, absorb it by widening `s` and `e`.
3. Emit the single widened interval.
4. Emit the remaining intervals unchanged.

Sorted input means everything that overlaps the new interval is one contiguous block. So the list splits into three runs, and only the middle one needs work.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Append, Sort, Merge All</b> &nbsp; T: O(n log n) &nbsp; S: O(n)</summary>

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> all = new ArrayList<>(Arrays.asList(intervals));
    all.add(newInterval);
    all.sort((a, b) -> Integer.compare(a[0], b[0]));

    List<int[]> res = new ArrayList<>();
    for (int[] it : all) {
        int[] last = res.isEmpty()
                ? null : res.get(res.size() - 1);
        // overlap -> widen
        if (last != null && it[0] <= last[1]) {
            last[1] = Math.max(last[1], it[1]);
        }
        // copy, don't alias
        else {
            res.add(new int[]{it[0], it[1]});
        }
    }
    return res.toArray(new int[0][]);
}
```

Throws away the fact that the input is already sorted, and pays `O(n log n)` to re-learn it. Good fallback if you blank on the three-phase version — this is just Merge Intervals (LC 56).

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — Three-Phase Linear Scan</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> res = new ArrayList<>();
    int i = 0, n = intervals.length;

    // strictly before
    while (i < n && intervals[i][1] < newInterval[0]) {
        res.add(intervals[i++]);
    }

    int s = newInterval[0], e = newInterval[1];
    // touching merges
    while (i < n && intervals[i][0] <= e) {
        s = Math.min(s, intervals[i][0]);
        e = Math.max(e, intervals[i][1]);
        i++;
    }
    res.add(new int[]{s, e});

    while (i < n) {
        res.add(intervals[i++]);
    }

    return res.toArray(new int[0][]);
}
```

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4010 · Minimum Platforms -->
<details name="greedy">
<summary><b>4010 · Minimum Platforms</b></summary>

---

🔗 https://www.geeksforgeeks.org/problems/minimum-platforms-1587115620/1

Fewest railway platforms so no train ever waits.

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- Answer = **maximum number of trains present at the same instant**, nothing to do with which train pairs with which.
- **Sort `arr[]` and `dep[]` independently.** This deliberately destroys the arrival–departure pairing, and that is fine — only the *counts* over time matter. This feels illegal on first read; it is the whole trick.
- Compare `arr[i] <= dep[j]` — **`<=`**. A train arriving exactly when another departs still needs its own platform (GFG's rule).
- Loop while `i < n` only. Once all arrivals are consumed the count can only fall, so `j` running off the end is impossible.
- Update `max` **only on arrival** — departures never raise the peak.
- Times are `HHMM` integers, not minutes. Numeric comparison still works; don't try to convert.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
arr = [900, 940, 950,1100,1500,1800]
dep = [910,1200,1120,1130,1900,2000]

 900 <= 910   arrive  cur=1  max=1
 940 <= 910?  no      depart cur=0
 940 <= 1120  arrive  cur=1
 950 <= 1120  arrive  cur=2  max=2
1100 <= 1120  arrive  cur=3  max=3  <- peak
1500 <= 1120? no      depart cur=2
1500 <= 1130? no      depart cur=1
1500 <= 1900  arrive  cur=2
1800 <= 1900  arrive  cur=3  max=3

answer = 3
```

- **above** — `3`
- **`arr=[900]`, `dep=[910]`** — `1`
- **`arr=[900,910]`, `dep=[910,920]`** — `2` — exact touch still needs a platform
- **all trains disjoint in time** — `1`
- **all trains identical times** — `n`

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Sort arrivals and departures as two independent sorted lists.
2. Two pointers walk them in merged time order.
3. Next event is an arrival → `cur++` and record the new max; otherwise it is a departure → `cur--`.
4. Return the highest `cur` ever reached.

Forget trains, watch the clock. Sweeping through time, every arrival adds one occupant and every departure removes one — the peak occupancy is exactly how many platforms you must build.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Brute Force (count overlaps per train)</b> &nbsp; T: O(n²) &nbsp; S: O(1)</summary>

```java
static int findPlatform(int arr[], int dep[]) {
    int n = arr.length, max = 0;
    for (int i = 0; i < n; i++) {
        // train i itself
        int count = 1;
        for (int j = 0; j < n; j++) {
            // j still on a platform
        }
        if (j != i && arr[j] <= arr[i]
                && dep[j] >= arr[i]) count++;
        max = Math.max(max, count);
    }
    return max;
}
```

The peak can only happen **at some arrival**, so checking every arrival instant is enough — no need to scan all times.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — Sort Both + Two-Pointer Sweep</b> &nbsp; T: O(n log n) &nbsp; S: O(1)</summary>

```java
static int findPlatform(int arr[], int dep[]) {
    Arrays.sort(arr);
    // pairing is intentionally broken
    Arrays.sort(dep);

    int i = 0, j = 0, cur = 0, max = 0, n = arr.length;
    while (i < n) {
        // <= : touch needs a platform
        if (arr[i] <= dep[j]) {
            cur++; i++;
            max = Math.max(max, cur);
        } else {
            cur--; j++;
        }
    }
    return max;
}
```

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 3 — Difference Array (no sort)</b> &nbsp; T: O(n + T) &nbsp; S: O(T)</summary>

```java
static int findPlatform(int arr[], int dep[]) {
    // times are HHMM, max 2359
    int[] diff = new int[2405];
    for (int i = 0; i < arr.length; i++) {
        diff[arr[i]]++;
        // +1 : still occupied at the departure minute
        diff[dep[i] + 1]--;
    }
    int cur = 0, max = 0;
    for (int t = 0; t < diff.length; t++) {
        cur += diff[t];
        max = Math.max(max, cur);
    }
    return max;
}
```

Beats the sort when `n` is large, but only because the clock is bounded. Useless if times were arbitrary integers.

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4011 · Valid Parenthesis String -->
<details name="greedy">
<summary><b>4011 · Valid Parenthesis String</b></summary>

---

🔗 https://leetcode.com/problems/valid-parenthesis-string/

`*` is a wildcard for `(`, `)` or empty — can the string be made valid?

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Don't decide what each `*` is.** Track a *range* `[low, high]` of how many open brackets could be outstanding.
- `(` → both `+1`. `)` → both `-1`. `*` → `low--`, `high++` (it widens the range in both directions at once).
- `high < 0` → return **false immediately**. Even treating every `*` as `(` cannot save it.
- **Clamp `low` at 0** every step. A negative `low` means "assume some `*` was empty instead" — without the clamp the final check is wrong.
- Final answer is `low == 0`, **not** `high == 0`. `high` counts the optimistic case, which is almost always non-zero.
- Ladder: `3ⁿ` recursion → `O(n²)` memo → two-stack `O(n)` → two-counter `O(1)`. Only the last one is worth writing in an interview; the rest are there to explain *why* it works.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
s = "(*))"

char   low  high
  (     1    1
  *     0    2    low clamped, high widens
  )    -1    1    clamp low -> 0
  )    -1    0    clamp low -> 0

low == 0  ->  true ✅

"((("  -> low 3, high 3 -> false
"))("  -> high = -2 < 0 -> false early
```

- **`"()"`** — `true`
- **`"(*)"`** — `true`
- **`"(*))"`** — `true`
- **`"*)("`** — `false` — order matters, counts alone are not enough
- **`"("`** — `false`
- **`""`** — `true`
- **`"***"`** — `true` — all empty

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Keep two counters — `low` (fewest opens still possible) and `high` (most opens still possible).
2. `(` raises both, `)` lowers both, `*` lowers `low` and raises `high`.
3. If `high` ever drops below zero, too many `)` exist even in the best case — return false.
4. Clamp `low` at zero after every character.
5. Valid exactly when `low` finishes at zero.

Every `*` forks the string into three futures, so brute force is `3^n`. But those futures only ever differ in *how many opens are pending*, and that set is always a contiguous range — so carrying its two endpoints carries all of them.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Brute Force (try all three)</b> &nbsp; T: O(3ⁿ) &nbsp; S: O(n)</summary>

```java
public boolean checkValidString(String s) {
    return solve(s, 0, 0);
}

private boolean solve(String s, int i, int open) {
    // prune: already impossible
    if (open < 0) {
        return false;
    }
    if (i == s.length()) {
        return open == 0;
    }

    char c = s.charAt(i);
    if (c == '(') {
        return solve(s, i + 1, open + 1);
    }
    if (c == ')') {
        return solve(s, i + 1, open - 1);
    }
    // '*' as '(' , as ')' , or as empty
    return solve(s, i + 1, open + 1)
            || solve(s, i + 1, open - 1)
            || solve(s, i + 1, open);
}
```

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — Memoization</b> &nbsp; T: O(n²) &nbsp; S: O(n²)</summary>

```java
public boolean checkValidString(String s) {
    return solve(s, 0, 0, new Boolean[s.length()][s.length() + 1]);
}

private boolean solve(String s, int i, int open, Boolean[][] memo) {
    if (open < 0) {
        return false;
    }
    if (i == s.length()) {
        return open == 0;
    }
    if (memo[i][open] != null) {
        return memo[i][open];
    }

    char c = s.charAt(i);
    boolean ok;
    if (c == '(') {
        ok = solve(s, i + 1, open + 1, memo);
    } else if (c == ')') {
        ok = solve(s, i + 1, open - 1, memo);
    } else {
        ok = solve(s, i + 1, open + 1, memo)
                || solve(s, i + 1, open - 1, memo)
                || solve(s, i + 1, open, memo);
    }

    return memo[i][open] = ok;
}
```

State is `(index, open)` — `open` never exceeds `n`, so the table is `n × (n+1)`. Memo **after** the `open < 0` guard, or you index negatively.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 3 — Two Stacks (indices)</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
public boolean checkValidString(String s) {
    Deque<Integer> open = new ArrayDeque<>();
    Deque<Integer> star = new ArrayDeque<>();

    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == '(') {
            open.push(i);
        } else if (c == '*') {
            star.push(i);
        } else {
            if (!open.isEmpty()) {
                open.pop();
            } else if (!star.isEmpty()) {
                star.pop();
            } else {
                return false;
            }
        }
    }
    while (!open.isEmpty() && !star.isEmpty()) {
        // '*' sits LEFT of '(' — cannot close it
        if (open.peek() > star.peek()) {
            return false;
        }
        open.pop();
        star.pop();
    }
    return open.isEmpty();
}
```

Store **indices, not counts** — the leftover `*` must appear *after* the leftover `(` to close it. This is why `"*("` fails.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 4 — Two-Counter Range Sweep</b> &nbsp; T: O(n) &nbsp; S: O(1)</summary>

```java
public boolean checkValidString(String s) {
    // possible open-bracket range
    int low = 0, high = 0;

    for (char c : s.toCharArray()) {
        if (c == '(') {
            low++;
            high++;
        } else if (c == ')') {
            low--;
            high--;
        } else {
            // '*' widens both ways
            low--;
            high++;
        }

        // unfixable, stop now
        if (high < 0) {
            return false;
        }
        // never fewer than zero opens
        low = Math.max(low, 0);
    }
    return low == 0;
}
```

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4012 · Candy -->
<details name="greedy">
<summary><b>4012 · Candy</b></summary>

---

🔗 https://leetcode.com/problems/candy/

Every child gets ≥1 candy, and a higher-rated child gets more than each neighbour — minimise the total.

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Two passes, one per direction.** One pass can only satisfy one neighbour; the left-to-right pass fixes the left rule, right-to-left fixes the right rule.
- Second pass must use **`Math.max(c[i], c[i+1] + 1)`**. Plain assignment overwrites the first pass and silently breaks the left constraint — this is *the* bug in this problem.
- Initialise everything to **1**, not 0.
- **Equal ratings impose nothing.** `[1,2,2]` → `[1,2,1]`, total 4. Only strict `>` creates an obligation, so a tie is free to reset.
- First pass condition compares to `i-1`, second compares to `i+1`. Direction and index must match or you re-derive the same pass twice.
- `O(1)` space exists (Code 3, slope counting) but is fiddly. Code 2 is the answer to give; keep Code 3 only for "can you drop the array?".

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
ratings = [1, 0, 2]
init      1  1  1
L -> R    1  1  2
R <- L    2  1  2      total = 5

ratings = [1,2,87,87,87,2,1]
init      1 1 1 1 1 1 1
L -> R    1 2 3 1 1 1 1   ties reset
R <- L    1 2 3 1 3 2 1   max() keeps 3

total = 13
```

- **`[1,0,2]`** — `5`
- **`[1,2,2]`** — `4` — the tie gets just 1
- **`[1,2,3,4]`** — `10` — one long climb
- **`[4,3,2,1]`** — `10` — the descent, caught only by pass 2
- **`[5]`** — `1`
- **`[3,3,3]`** — `3`

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Give every child 1 candy.
2. Sweep left to right — if a child out-rates the one on their left, give them one more than that neighbour.
3. Sweep right to left — if a child out-rates the one on their right, raise them to `max(current, right + 1)`.
4. Sum the array.

Each child has two independent obligations, and no single direction can see both — going left to right you cannot yet know what is on the right. So satisfy one rule per pass, and let `max` merge the two answers instead of letting the second pass overwrite the first.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Two Separate Arrays</b> &nbsp; T: O(n) &nbsp; S: O(2n)</summary>

```java
public int candy(int[] ratings) {
    int n = ratings.length;
    int[] left = new int[n], right = new int[n];
    Arrays.fill(left, 1);
    Arrays.fill(right, 1);

    for (int i = 1; i < n; i++) {
        if (ratings[i] > ratings[i - 1]) {
            left[i] = left[i - 1] + 1;
        }
    }
    for (int i = n - 2; i >= 0; i--) {
        if (ratings[i] > ratings[i + 1]) {
            right[i] = right[i + 1] + 1;
        }
    }

    int sum = 0;
    // satisfy both rules
    for (int i = 0; i < n; i++) {
        sum += Math.max(left[i], right[i]);
    }
    return sum;
}
```

Clearest version — each rule owns its array, `max` merges them at the end. Code 2 folds `right` into `left`, which is where the `Math.max` trap comes from.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 2 — Two-Pass Sweep (one array)</b> &nbsp; T: O(n) &nbsp; S: O(n)</summary>

```java
public int candy(int[] ratings) {
    int n = ratings.length;
    int[] c = new int[n];
    Arrays.fill(c, 1);

    // left neighbour rule
    for (int i = 1; i < n; i++) {
        if (ratings[i] > ratings[i - 1]) {
            c[i] = c[i - 1] + 1;
        }
    }

    // right neighbour rule
    for (int i = n - 2; i >= 0; i--) {
        // max, NOT assign
    }
    if (ratings[i] > ratings[i + 1]) {
        c[i] = Math.max(c[i], c[i + 1] + 1);
    }

    int sum = 0;
    for (int x : c) {
        sum += x;
    }
    return sum;
}
```

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 3 — Slope Counting (constant space)</b> &nbsp; T: O(n) &nbsp; S: O(1)</summary>

```java
public int candy(int[] ratings) {
    int n = ratings.length, sum = 1, i = 1;

    while (i < n) {
        // tie resets to 1
        if (ratings[i] == ratings[i - 1]) {
            sum += 1; i++; continue;
        }

        // climb
        int peak = 1;
        while (i < n && ratings[i] > ratings[i - 1]) {
            peak++; sum += peak; i++;
        }

        // descent
        int down = 1;
        while (i < n && ratings[i] < ratings[i - 1]) {
            sum += down; down++; i++;
        }

        // valley longer than the climb -> raise the peak
        if (down > peak) {
            sum += down - peak;
        }
    }
    return sum;
}
```

Every sequence is climbs, descents and ties. A climb of length `k` costs `1+2+...+k`; a descent costs the same going down. They share the peak — and if the descent is longer, the peak must be lifted by the difference.

Fiddly and rarely required. Know Code 2 cold; keep this one as the "can you do O(1) space?" answer.

</details>

</details>

---
<!-- #endregion -->

<!-- #region 4013 · Fractional Knapsack -->
<details name="greedy">
<summary><b>4013 · Fractional Knapsack</b></summary>

---

🔗 https://www.geeksforgeeks.org/problems/fractional-knapsack-1587115620/1

Fill a bag of capacity `W` for maximum value — items may be broken into fractions.

<details>
<summary>&nbsp;&nbsp;<b>📌 Remarks</b></summary>

**Revision:** R1 ` ` · R2 ` ` · R3 ` `

- **Sort by `value / weight` ratio descending.** Sorting by value or by weight both fail — one ignores cost, the other ignores worth.
- **Cast to `double` before dividing.** `val[i] / wt[i]` on ints silently truncates and wrecks the ordering.
- Comparator must be `Double.compare(r2, r1)`. Returning `(int)(r2 - r1)` collapses ratios like `6.4` and `6.1` to `0` — they compare as equal and the sort is wrong.
- Once an item is taken **fractionally the bag is full** — `break` immediately, never continue the loop.
- Fraction value is `value * remaining / weight`, using the item's *full* value and weight.
- ⚠️ **Greedy only works because fractions are allowed.** 0/1 Knapsack (whole items only) is **DP, not greedy** — the ratio choice provably fails there. This is the single most tested distinction in the topic.
- Return type is `double`; GFG compares to a fixed precision.

</details>

<details>
<summary>&nbsp;&nbsp;<b>🌳 Examples</b></summary>

```
W = 50
items (value, weight):
 (60,10)  (100,20)  (120,30)
ratio  6.0    5.0      4.0

(60,10)   whole  -> 60    left 40
(100,20)  whole  -> 160   left 20
(120,30)  too big:
   120 * 20/30 = 80  -> 240, break

answer = 240.0
```

Why greedy breaks for 0/1 (whole items only):

```
W = 6   items (7,6) (5,4)
ratio          1.17   1.25

ratio picks (5,4) = 5
  -> leftover capacity 2 is unusable
whole item (7,6) = 7 was better ✅
```

- **`W=50`, `(60,10)(100,20)(120,30)`** — `240.0`
- **`W=100`, everything fits** — sum of all values
- **`W=0`** — `0.0`
- **one item heavier than `W`** — `value * W / weight`
- **items with equal ratios** — any order, same total

</details>

<details>
<summary>&nbsp;&nbsp;<b>💡 Intuition</b></summary>

1. Compute each item's value-per-unit-weight.
2. Sort items by that ratio, highest first.
3. Walk the list taking whole items while they fit, subtracting from the remaining capacity.
4. On the first item that does not fit, take the fraction that does and stop.

Capacity is the scarce resource, so buy the densest value per kilo first. Fractions are what make it safe: there is never a leftover gap to regret, so the locally best kilo is always part of the globally best bag.

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Code 1 — Sort by Ratio + Fill</b> &nbsp; T: O(n log n) &nbsp; S: O(n)</summary>

```java
static double fractionalKnapsack(int[] val, int[] wt, int capacity) {
    int n = val.length;
    Integer[] idx = new Integer[n];
    for (int i = 0; i < n; i++) {
        idx[i] = i;
    }

    // ratio DESC — Double.compare, never (int)(r2 - r1)
    Arrays.sort(idx, (a, b) -> Double.compare(
            (double) val[b] / wt[b],
            (double) val[a] / wt[a]));

    double total = 0;
    int remaining = capacity;
    for (int i : idx) {
        // whole item
        if (wt[i] <= remaining) {
            total += val[i];
            remaining -= wt[i];
        } else {
            // fraction of the last item, then done
            total += (double) val[i] * remaining / wt[i];
            break;
        }
    }
    return total;
}
```

</details>

<details>
<summary>&nbsp;&nbsp;<b>👨‍💻 Why there is no second approach</b></summary>

There is no meaningful brute force here. "Try every subset" is the **0/1** problem, and 0/1 is a different problem with a different answer — it cannot be fractional knapsack's brute force.

The only variation is mechanical: a max-heap keyed on ratio instead of a sort. Same `O(n log n)`, same greedy, more code. Not worth learning as a separate approach.

The approach ladder you actually want here is the **contrast**: fractional → greedy, 0/1 → DP. Know why the greedy fails the moment items stop being divisible.

</details>

</details>

---
<!-- #endregion -->
