# !pip install scikit-fuzzy matplotlib

import numpy as np
import skfuzzy as fuzz
import matplotlib.pyplot as plt

# Function to plot membership functions
def plot_membership_function(mf_type, params):
    x = np.linspace(0, 10, 1000)  # Universe of discourse (0 to 10)

    if mf_type == "triangular":
        mf = fuzz.trimf(x, params)
        label = f"Triangular {params}"

    elif mf_type == "trapezoidal":
        mf = fuzz.trapmf(x, params)
        label = f"Trapezoidal {params}"

    elif mf_type == "gaussian":
        mf = fuzz.gaussmf(x, params[0], params[1])  # mean, sigma
        label = f"Gaussian mean={params[0]}, sigma={params[1]}"

    elif mf_type == "gbell":
        mf = fuzz.gbellmf(x, params[0], params[1], params[2])  # a, b, c
        label = f"Generalized Bell {params}"

    elif mf_type == "sigmoid":
        mf = fuzz.sigmf(x, params[0], params[1])  # x0, slope
        label = f"Sigmoid x0={params[0]}, slope={params[1]}"

    else:
        print("Invalid membership function type.")
        return

    # Plot
    plt.figure(figsize=(7, 4))
    plt.plot(x, mf, "b", linewidth=2, label=label)
    plt.title(f"{mf_type.capitalize()} Membership Function")
    plt.xlabel("Universe (x)")
    plt.ylabel("Membership Degree")
    plt.ylim(-0.05, 1.05)
    plt.legend()
    plt.grid(True)
    plt.show()


# ---------- User Input ----------
print("Choose Membership Function Type: triangular / trapezoidal / gaussian / gbell / sigmoid")
mf_type = input("Enter type: ").strip().lower()

if mf_type == "triangular":
    params = list(map(float, input("Enter 3 points (a b c): ").split()))
    plot_membership_function(mf_type, params)

elif mf_type == "trapezoidal":
    params = list(map(float, input("Enter 4 points (a b c d): ").split()))
    plot_membership_function(mf_type, params)

elif mf_type == "gaussian":
    mean = float(input("Enter mean: "))
    sigma = float(input("Enter sigma: "))
    plot_membership_function(mf_type, [mean, sigma])

elif mf_type == "gbell":
    a = float(input("Enter parameter a (width): "))
    b = float(input("Enter parameter b (slope): "))
    c = float(input("Enter parameter c (center): "))
    plot_membership_function(mf_type, [a, b, c])

elif mf_type == "sigmoid":
    x0 = float(input("Enter parameter x0 (center): "))
    slope = float(input("Enter slope: "))
    plot_membership_function(mf_type, [x0, slope])

else:
    print("Invalid choice! Please run again.")