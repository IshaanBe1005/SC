import numpy as np
import matplotlib.pyplot as plt

# Sets
SetA = ["Y1", "Y2", "Y3"]
SetB = ["Sports", "Study", "Rest"]

R1 = np.array([
    [0.8, 0.6, 0.5],
    [0.7, 0.9, 0.4],
    [0.5, 0.7, 0.8]
])

R2 = np.array([
    [0.6, 0.4, 0.7],
    [0.8, 0.5, 0.6],
    [0.7, 0.6, 0.5]
])

def max_min_composition(A, B):
    m, k = A.shape
    k2, n = B.shape
    if k != k2:
        raise ValueError("Inner dimensions must match for composition.")
    result = np.zeros((m, n))
    for i in range(m):
        for j in range(n):
            result[i, j] = np.max(np.minimum(A[i, :], B[:, j]))
    return result

def min_max_composition(A, B):
    m, k = A.shape
    k2, n = B.shape
    if k != k2:
        raise ValueError("Inner dimensions must match for composition.")
    result = np.zeros((m, n))
    for i in range(m):
        for j in range(n):
            result[i, j] = np.min(np.maximum(A[i, :], B[:, j]))
    return result

max_min = max_min_composition(R1, R2)
min_max = min_max_composition(R1, R2)

# --- Numeric printout (helpful) ---
print("R1:\n", np.round(R1, 3))
print("R2:\n", np.round(R2, 3))
print("Max-Min composition (R1 ○ R2):\n", np.round(max_min, 3))
print("Min-Max composition (R1 ∘ R2):\n", np.round(min_max, 3))

# --- Bar plot comparing Y1 only (meaningful comparison) ---
x = np.arange(len(SetB))
width = 0.25

plt.figure(figsize=(8, 4))
plt.bar(x - width, R1[0], width, label="R1 (Y1 → B)")
plt.bar(x, max_min[0], width, label="Max–Min (Y1 → B)")
plt.bar(x + width, min_max[0], width, label="Min–Max (Y1 → B)")

plt.xticks(x, SetB)
plt.ylim(0, 1)
plt.ylabel("Membership")
plt.title("Comparison for Y1 (rows compared correctly)")
plt.legend()
plt.grid(axis="y", linestyle='--', alpha=0.4)
plt.tight_layout()
plt.show()

# --- Heatmaps for full matrices (side-by-side) ---
fig, axes = plt.subplots(1, 4, figsize=(14, 3.5))
matrices = [R1, R2, max_min, min_max]
titles = ["R1 (A→B)", "R2 (B?→?)", "Max–Min (R1 ○ R2)", "Min–Max (R1 ∘ R2)"]

for ax, M, t in zip(axes, matrices, titles):
    im = ax.imshow(M, vmin=0, vmax=1, aspect='auto', origin='upper')
    ax.set_xticks(range(len(SetB))); ax.set_xticklabels(SetB)
    ax.set_yticks(range(len(SetA))); ax.set_yticklabels(SetA)
    ax.set_title(t)
    # annotate values
    for i in range(M.shape[0]):
        for j in range(M.shape[1]):
            ax.text(j, i, f"{M[i,j]:.2f}", ha='center', va='center', color='w' if M[i,j]>0.5 else 'k', fontsize=9)

fig.colorbar(im, ax=axes.ravel().tolist(), shrink=0.6)
plt.suptitle("Fuzzy Relations and Compositions (heatmaps)")
plt.tight_layout(rect=[0, 0, 1, 0.95])
plt.show()
