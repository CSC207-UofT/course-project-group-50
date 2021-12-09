## Overview 
For Phase 2, we focused on adding a GUI for a more immersive and intuitive gaming experience. In particular, the GUI displays 

- A **visual representation of the game board** and its consituent tiles
- Labels indicating the **name, price, and rent of each tile**, updated after each action
- An overview of **player statistics** (ie. cash and net worth), updated after each action
- Current location of each player's **token** on the game board
- Color scheme **optimized for colour-blind individuals**

![GUI](https://github.com/CSC207-UofT/course-project-group-50/blob/main/phase2/simplifiedMonopoly.png)

## Updated Specification
Our goal was to create a simplified version of the popular real-estate board game, Monopoly. Players participate in a game with up to three other players.The objective of the game is to be the first player to reach a net worth of $5000. If all but one player has gone bankrupt, the remaining non-bankrupt player wins. The game will proceed according to the following specifications: 

- The username, cash, and net worth of each player will be displayed. Every player starts with \$1,000 in cash. A player’s net worth is calculated as the sum of their cash holdings and the total rent generated from their properties. Since players start with no property, the initial net worth of each player is also $1,000. Each player also has a corresponding token on the game board.

- The order in which players take turns is randomly determined at the beginning of the game and does not change.

- The game board consists of twenty-eight tiles. A tile may be either a property or a special tile. There are two types of properties: public property and cities. Each city has a name, price, rent amount, and sell price. Each public property has a name and rent amount.

  - Each city tile has a colour. If a player owns all cities of a particular colour, then the rental price for each city increases.
  
- At the start of a player’s turn, they can upgrade their existing city properties by adding buildings. Note that this is only an option if the player owns all city properties of that colour and if they have enough money. Each property can accommodate a maximum of three buildings. Each upgrade raises the rent associated with that property.

- Then, the player rolls a single six-sided die and moves their token a corresponding number of spaces forward on the game board.

- If the player lands on a property...

  - If a player has enough money to buy the property they landed on (and the property is not already owned by another player), they will be given the option to purchase it. If they do so, the price of the property will be deducted from the player’s cash and the player’s net worth changes accordingly.
  
  - If the property is already owned by another player, the player must pay the specified rent amount to the owner. This rental amount will be automatically deducted from the player’s cash and added to the owner’s cash amount.
  
- If the player lands on a non-property tile…
  - *Jail*: The player will be given the option to remain in jail (ie. skip) three turns or pay a \$50 fee to be released after one turn. If the player has less than \$50 in cash, they are automatically stuck in jail for three turns.
  
  - *Surprise*: The player will experience a random effect, which may be beneficial (ie. cash from the bank), harmful (ie. charged additional fees), or neutral (transported to another tile).
  
  - *Auction*: The player will be given the option to trade one of their properties in exchange for a property from another player. 
  
  - *Start Tile*: The player earns a \$200 salary.
  
- If a player’s cash drops below -\$500 or their net worth falls to \$0, they are bankrupt and eliminated from the game. Any properties owned by that player become available for the remaining players to buy if they land on such a tile.

In the future, we could create a leaderboard to rank players across multiple games or a chat for in-game conversation. 

