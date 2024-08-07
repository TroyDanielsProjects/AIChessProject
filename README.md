# AIChessProject

**Author:** Troy Daniels  
**Description:** A Java implementation of chess featuring an alpha-beta pruning algorithm as an opponent.

## Overview

This project implements chess in Java, utilizing an alpha-beta pruning algorithm for the opponent. The game can be played through the console. An additional reinforcement learning opponent was created but is not included due to unsatisfactory results. For further details, please see the attached PDF.

## How to Play

The folder **"play"** contains all necessary classes to engage in gameplay.

<img width="364" alt="Screen Shot 2024-08-05 at 2 27 56 PM" src="https://github.com/user-attachments/assets/54f5149f-f4a7-46fa-90ca-218ad684aeb6">

### Available Modes

1. **Human vs. Human**
2. **Human vs. Alpha-Beta Pruning Algorithm**

The game is displayed and played through the console. To make a move, input the coordinates of the piece you wish to move and the destination space in the format `e2e4`.

<img width="487" alt="image" src="https://github.com/user-attachments/assets/1236a451-e202-4044-bda6-876130f3c7bd">

### Game Mechanics

- The displayed value after each move indicates the confidence of the alpha-beta player’s current position based on the feature vector. 
  - A **positive value** suggests the algorithm believes it is winning.

### Chess Rules

This chess game implements all standard rules, with the exception of en passant. Key features include:

- **Legal Moves:** Any illegal move will be prevented, including moves that put a player in check.
- **Pawn Promotion:** Pawns automatically promote to queens upon reaching the opponent's back rank.
- **Castling:** Enabled with all constraints.

To start a game, select the desired opponent file and run it. Options include:
- `HumanVsHuman`
- `HumanVsMinimax` (alpha-beta player)
- `HumanVsRL`

For the RL opponent, specify a parameter containing the filename with the trained weights. The coordinates are labeled with numbers for rows and letters for columns. 

### Complexity of Chess for AI

Chess presents a significant challenge for AI due to its large state space and complex rules. The average branching factor is around **35**, leading to rapid growth in the alpha-beta player's runtime as search depth increases. The reinforcement learner also faces slow convergence due to the vast state space.

## Alpha-Beta Algorithm

### Value Function

The alpha-beta player's value function considers:
- **Piece Value Comparison:**
  - Queen = 9
  - Rook = 5
  - Bishop = 3
  - Knight = 3
  - Pawn = 1
  - The king is not included in the comparison.

- **Central Control:** Weighed less than piece comparison, calculated as follows:
  - **Outside squares:** 0.01
  - **One square inside:** 0.02
  - **Two squares inside:** 0.03
  - **Center squares:** 0.05

### Performance

The alpha-beta player can be adjusted for search depth, with a maximum reasonable depth of **4**. Performance observations:
- Depth 4: Competitive against a player rated around 1400, with the player often winning.
- Depth 3: Still decent but easier to beat.
- Depths below 3: Generally easy to defeat.

### Move Ordering

Ordering moves by prioritizing attacks improves runtime due to quicker pruning.

## Reinforcement Learning

Two feature vectors are used for training:
1. **General Feature Vector (G):** Length of 6, includes:
   - Piece comparison
   - Central control
   - Number of pawns around the king (king defense)
   - Number of squares attacked
   - Number of opponent pieces attacked
   - Number of player's pieces under attack

2. **Encoded Feature Vector (E):** Length of 70, includes:
   - Same 6 features as the general vector
   - 64 features encoding board positions (positive for own pieces, negative for opponent’s).

Values are scaled between 0-1.

### Training

Training was challenging, with around **150K games** played.

### Observations

- The best performing player was `QEncodedPlayer#2`, but overall performance remains subpar:
  - 90% of games end in a draw (with an additional constraint of a draw if moves exceed 2000).
  - Reinforcement players lost every match (excluding draws) against the depth 1 alpha-beta player.

### Future Work

Considerations for future improvements:
- Increase training for better performance.
- Investigate negative weights for seemingly positive features.
- Develop a neural network for a more effective function approximator.

See the attached PDF for more information.
