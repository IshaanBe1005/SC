import numpy as np
import matplotlib.pyplot as plt

# Example interactive input assumed; here we keep the same interface
n = int(input("Enter number of rating levels to evaluate: "))

story_ratings, acting_ratings = [], []
story_mem, acting_mem = [], []

print("\n--- Story Ratings (Set A) ---")
for i in range(n):
    r = float(input(f"Enter story rating (0–100): "))
    m = float(input("Enter corresponding membership value (0–1): "))
    story_ratings.append(r); story_mem.append(m)

print("\n--- Acting Ratings (Set B) ---")
for i in range(n):
    r = float(input(f"Enter acting rating (0–100): "))
    m = float(input("Enter corresponding membership value (0–1): "))
    acting_ratings.append(r); acting_mem.append(m)

# make common x-axis (sorted unique) and interpolate memberships onto it
common_x = np.linspace(min(min(story_ratings), min(acting_ratings)),
                       max(max(story_ratings), max(acting_ratings)), 200)

# sort inputs for safe interpolation
s_idx = np.argsort(story_ratings)
a_idx = np.argsort(acting_ratings)

sr_sorted = np.array(story_ratings)[s_idx]; sm_sorted = np.array(story_mem)[s_idx]
ar_sorted = np.array(acting_ratings)[a_idx]; am_sorted = np.array(acting_mem)[a_idx]

story_interp = np.clip(np.interp(common_x, sr_sorted, sm_sorted, left=sm_sorted[0], right=sm_sorted[-1]), 0, 1)
acting_interp = np.clip(np.interp(common_x, ar_sorted, am_sorted, left=am_sorted[0], right=am_sorted[-1]), 0, 1)

# elementwise fuzzy ops on interpolated arrays
overall_union = np.maximum(story_interp, acting_interp)
weak_inter = np.minimum(story_interp, acting_interp)
story_comp = 1 - story_interp

# Plot
plt.figure(figsize=(10,6))
plt.plot(common_x, story_interp, label="Story (interp)", linewidth=2)
plt.plot(common_x, acting_interp, label="Acting (interp)", linewidth=2)
plt.plot(common_x, overall_union, '--', label="Union (overall)", linewidth=2)
plt.plot(common_x, weak_inter, '--', label="Intersection (weakest)", linewidth=2)
plt.plot(common_x, story_comp, ':', label="Story Complement", linewidth=2)

# optional shading to visualize union/intersection
plt.fill_between(common_x, story_interp, acting_interp, where=(overall_union==story_interp)| (overall_union==acting_interp),
                 color='gray', alpha=0.08)

plt.ylim(0,1)
plt.xlim(common_x.min(), common_x.max())
plt.xlabel("Rating (0-100)")
plt.ylabel("Membership (0-1)")
plt.title("Fuzzy Movie Recommendation (aligned & interpolated)")
plt.grid(True, linestyle='--', alpha=0.4)
plt.legend(loc='upper right')
plt.show()
