# <span style="color:Violet">Design Document Phase 1

This is our design document, along with the updated specification and progress report. 

## <span style="color:Aquamarine">Specification

Our goal is to create a simplified version of the popular real-estate board game, <span style="color:yellow">
**Monopoly**</span>. Players participate in a game with up to three other players. Before the game begins, players can set a net worth goal (of either $3,000, $5,000,
or $7, 000) and a maximum number of rounds (at least 30). The objective of the game is to be the first player to reach 
this net worth goal within the specified maximum number of rounds. If all but one player has gone "bankrupt", the 
remaining non-bankrupt player wins. The game will proceed according to the following specifications: 

- The ***username***, ***cash***, and ***net worth*** of each `Player` will be displayed. Every `Player` starts with $1,000 in cash. A 
`Player`’s net worth is calculated as the **sum of their cash holdings and the total rent generated from their properties.** 
Since `Player`s start with no property, the initial net worth of each `Player` is also $1,000. Each `Player` also has a 
corresponding token on the game board.


- The order in which `Player`s take turns is **randomly determined** at the beginning of the game and does not change.


- The game board consists of **twenty-four tiles**. A tile may be either a `PropertyTile` or a `SpecialTile`. There are two types 
of properties: `PublicProperty` and `City`. Each property has a ***name***, ***price***, ***rent***, and ***sale_price***.
    - Each `City` tile has a ***colour***. If a player owns all cities of a particular colour, then the **rental price for each city
increases.**
    - If a player owns more than one public property, the **rental price** for each of them is **multiplied** by the number of 
  public properties the player owns.


- At the start of a `Player`’s turn:
  - They have the chance to sell any of their properties. However, the sell price of a property is calculated as **80%** of 
  the initial value of the property.
  - They can upgrade their existing city properties by adding buildings (*This is only an option if the player
owns all city properties of that colour and if they have enough money*). Each property can accommodate a maximum of 
**three** buildings. Each upgrade raises the rent associated with that property.


- Then, the `Player` rolls a single six-sided die and moves their `Token` a corresponding number of spaces forward on the 
game board. The player can take another turn **if they roll a 6**.


- If the `Player` lands on a property...
  - If a `Player` has enough money to buy the property they landed on (*and the property is not already owned by another 
  `Player`*), they will be given the option to purchase it. If they do so, the price of the property will be deducted from
  the `Player`’s cash and the `Player`’s net worth changes accordingly.
  - If the property is already owned by another `Player`, the `Player` must pay the specified rent amount to the owner. 
  This rental amount will be automatically deducted from the player’s cash and added to the owner’s cash amount.


- If the `Player` lands on a non-property tile…
  - `JailTile`: The `Player` will be given the option to remain in jail, i.e. ***skip***, for three turns or pay a $150 fee to be released 
  after one turn. If the `Player` has less than $150 in cash, they are automatically stuck in jail for three turns.
  - `SurpriseTile`: The `Player` will experience a random effect, which may be beneficial (ie. cash from the bank), harmful 
  (ie. charged additional fees by the bank), or neutral (transported to another tile).
  - `AuctionTile`: The `Player` will be given the option to trade one of their properties in exchange for a property from 
  another`Player`. 
  - `StartTile`: The `Player` earns a $200 salary if a `Player` lands on it or passes it.


- If a `Player`’s cash drops below **-$500** or their net worth falls to **$0**, they are bankrupt and eliminated from the game. 
Any properties owned by that `Player` become available for the remaining players to buy if they land on that property.

Time permitting, add-on features might include a leaderboard to rank players across multiple games or a chat for in-game
conversation. 

## <span style="color:Aquamarine">Design Questions

### Design Decisions

Our first major decision we made was how to save the ongoing game. Initially for `CMDLineUI`, we instantiated a 
`GameController` within our `startGame()` method. We realized that while this may run our game, there was no way to load
a saved game from the game filepath. Now the `startGame()` method has a parameter ***gc*** that takes in `GameController`
object, which allows us to load games freely; however, the problem didn't end there.

A bug occurred while saving a game. `NotSerializableException` was thrown while saving and loading the game. We realized
after quick search on the javadoc:
    
    Serializability of a class is enabled by the class implementing the java.io.Serializable interface. Classes that do 
    not implement this interface will not have any of their state serialized or deserialized. All subtypes of a serializable
    class are themselves serializable.

`GameController` had serializable implementation, but the objects which the `GameController` was using did not. Therefore,
we implemented serializable implementation to all classes that needed it and the bug was resolved.

### Clean Architecture

Our project, <span style="color:yellow">**Monopoly**</span>, adheres to Clean Architecture as it incorporates the different layers that the architecture 
    deems necessary. First, we will have an overview of the Clean Architecture in our project. Our project consists of 
    multiple **entities** (Enterprise Business Rules), including but not limited to `Player`, `Tile`, `Token`, `Board`, and so 
    on, that are at the core of the program and contain the necessary information. Then we have our use cases (Application
    Business Rules) that interact with those entities to ensure the game runs smoothly. These **use cases** include 
    `BankManager`, `BoardManager`, and `PropertyManager`. Next, we have **Controllers** (Interface Adapters) that interact
    with the **use cases** to access or manipulate **entities**. The **controller** in our project responsible for the 
    interaction is`GameController`. Lastly, we have the **UI** (Frameworks and Drivers) as the outermost core of the 
    program which is responsible for communicating with the user and delegating work to the `GameController`. In our 
    project, the **UI** responsible for this is the `CMDLineUI`. These are all the layers, from the innermost being the entities
    to the outermost being the UI, that work together to ensure that our project runs smoothly!

When it comes to adhering to Clean Architecture, we have made sure that the flow of control flows from the outermost
    layer (`CMDLineUI`) to the innermost layer (**entities**). We made sure that we did not have any inner layer accessing or 
    communicating with any outer layers except when returning an output. Layers are only allowed to access/talk to its own
    layer, and the layers inside it. For example, we made sure that any entities, for instance `Player`, did not 
    communicate/used any of the use cases; instead, we rethought the design and structure and moved such operations from 
    `Player` to a use case since use cases are able to communicate with each other. We also made sure that in the flow of 
    control from outside to inside, no layers were being skipped; layers were only allowed to communicate to the inner 
    layer directly connected to it. For example, when the `GameController` runs the game, instead of dealing with all the 
    `Player`s and turns itself, it delegates the work to a use case to ensure that no layers are being skipped in the flow. 
    Like in this example, if the `GameController` dealt with all the `Player`s and the turns itself, then it would be 
    **communicating directly** with the `Player` entity and skipping over use cases which is a violation of Clean Architecture. 
    This ensures that the principles of Clean Architecture are adhered to, which ultimately leads to a more elegant and 
    clean program.

Although there are a few instances where we notice a violation of the Clean Architecture, we have a use case 
    `BoardManager` which communicates with the user directly (skipping controllers and UI) to output/input when dealing with
    different `Tile`s. This includes requiring an input asking the player if they want to buy a property, requiring an input
    asking the player who they want to trade with, and so on. We are unsure how to fix this as of now, but we plan on 
    fixing this by the end of Phase 2.


###SOLID design principles

We were trying our best throughout the implementation of the program to stay consistent with the **SOLID** principles, 
  and we will highlight some situations in which all the principles are present. I will also highlight some rule breaks 
  and why we are not sure how to fix them. Through the entities to the use cases, all files have been monitored so that 
  each method only has one job. For any methods that we noticed that were getting outside the scope of one job, we used the 
  extract method helper from **IntelliJ** to help streamline the process. This is one way we were adhering to the **Single 
  Responsibility Principle**. With the use of interfaces, we have left much room for extension to add different types of 
  not only `Tile`s, but also `PropertyTile` for new different types of properties. We didn’t 
  explicitly plan for closed for modification, but this is something we plan to keep in mind for the extensions of the 
  code we plan for phase 2. This is how we went about the `Open-closed Principle`. The **Liskov Principle** was most thought 
  about during the implementation of the superclass `PropertyTile` and the subclasses of `City` and `PublicProperty`. Both 
  subclasses are not only theoretically substitutable to the super class, but also we have done this in many situations by 
  casting it to the higher `PropertyTile` class. This is specifically used in the `payRent` function. Our group also chose 
  to make our interfaces very bare bones to not limit which types of classes could access these and a great example for 
  this is all our implemented interfaces; for example, the actionable interface has no methods leaving complete freedom 
  of which types of properties can be auctioned. Another example is the `Buyable` interface which only implements `getPrice`
  and `getSalePrice`. We definitely could have added the ***rent*** methods and even an ***owned*** method, but we decided to limit as
  little as possible for the interface to be applied to a `Tile`. The **Dependency Inversion Principle** is adhered to for the
  example of the `Tile` abstract class and all the entities that are `Tile`s such as `PropertyTile` and `JailTile` are all 
  subclasses of this abstract class. <br/><br/>
  We do have some violations for which we know how to solve, and we plan to solve by phase 2. However, there is the 
  violation of the **Single responsibility Principle** in the `GameController` and `cmdLineUI` for which there is not a clear 
  solution. For the `Gamecontroller`, we have a `runGame` method which carries out more than one task, but as this is the main loop
  that keeps the game running and checks for winners. There is not any simple and elegant solutions that are very 
  apparent, which we wish to discuss further. Additionally, in the `cmdLineUI`, we have methods that both take input and perform some basic actions on those 
  inputs, but these are not major problems, so we left them in the `cmdLineUI` for simplicity as it would add complexity to fix these. 
  One thing that is quite closed to extension is the `runGame` loop and new types of `Tile`s. In our Phase 1 
  implementation, we have multiple ***if*** statements checking the type of `Tile` and perform actions based on the ***if*** 
  statements. If someone were to add a <span style="color:orange">new</span> `Tile` type and extend the program, it would not be easy as making a <span style="color:orange">new</span> `Tile` 
  and assigning it to the `Board` as the `runGame` would have no way of detecting it. This is something as well whose 
  solution is quite time-consuming and due to the time constraint, we decided to leave this to change until the second 
  phase.


### Packaging Strategies

We considered packaging the program in a couple ways, the most basic of which we got to in Phase 1, but we 
  plan to spend more time planning and brainstorming better ways to package which we will implement for Phase 2.
  The one we ended up implementing was organizing based on Clean Architecture by creating a separate folder for each 
  leve. This way, it is streamlined with dependencies. Another strategy we considered was by organizing by use cases; for 
  example, separating the `Tile` superclass and all its subclasses along with `PropertyManager` in one folder. This way the 
  packages would not have as much overlap as the use cases would be contained in their own folder. All of which would be
  accessed by `GameController` which is an **Interface Adapter**. The main reason we didn’t spend more time planning and 
  implementing a better strategy so far is the time constraint; for the sake of the scope of the project, we decided it would be 
  better to devote our time into trying to have the best functioning program we could. This left us with a quite basic 
  packaging structure that is functional and is not final.

  We considered packaging by feature since it’s both simple and intuitive, a clear connection to the business 
  domain, but unfortunately we don’t have enough features implemented yet for this strategy to be ideal. We also 
  considered packaging by component, but we felt that  packaging by layer better met our needs, at least for now. 
  Packaging by layer offers simplicity, clear separation of architecture layer, and ease-of-use. Furthermore, our 
  existing code isn’t nearly complex enough for the “***large buckets of code***” that often arise in packaging by layer to 
  become a serious problem.

### Design Pattern

  - **<u>Builder</u>** – We implemented this design pattern to construct the game `Board` from various `Tile` types 
  (`City`, `PublicProperty`, `SpecialTile`), since the **Builder** pattern is well-suited to the constructing complex objects 
  by piecing together simpler elements with the same construction process. In the future, if we decide to implement a 
  feature where users can pick from one of several `Board` themes  (ie. UofT-themed <span style="color:blue">**Monopoly**</span>, Halloween-themed
    <span style="color:Orange">**Monopoly**</span>, or regular <span style="color:yellow">**Monopoly**</span>), we’ll want to create different `Tile` names  (based on UofT buildings instead of cities). 
  However, the construction process for each of these themed boards should the same – we layer `Tile`s on a
  board, differentiate some `Tile`s, add pieces to the `Tile`s, etc.
  - **<u>Dependency Injection</u>** – To avoid the hard dependency between `City` and `Building` classes, we created the `Building` 
  objects outside the `City` class and pass each `Building` (or a list of `Building`s) into the `City`.
  - **<u>Factory Method</u>** – We plan to implement a **Simple Factory Pattern** to manage the construction of `Building`s on a `City` 
  tile. If a `Player` owns all `City` in a colour block, they can construct different types of buildings on the property, 
  each with a different rental fee and purchase price. Specifically, let’s say we have classes for a `House`, `Hotel`, and 
  `Castle`, in order of increasing rental/purchase price. Each of these classes implements a `Building` interface. We will 
  create a `Factory` class, `BuildingFactory`, that contains a factory method to determine which of the buildings should be 
  instantiated (based on whether the Player can afford the building, and whether the player has indicated that they want
  to buy that particular building). This method will then create and return an instance of that specific `Building` class. 

### Progress Report

Parth: Worked on Serialization of the `GameController` object that allows the game to be saved and loaded, worked on 
`CMDLineUI`, `Board`, `GameController`, `JailTile`, and `StartTile` among other classes. Plan on cleaning up the code 
and adding necessary features and bug fixes for Phase 2.

Jacqueline: Worked on `City` and `Building` classes. Implemented Builder design pattern (including `Director` and `BoardBuilder`
classes,` Builder` interface, and modifications to `Board` and `BoardManager`). Refactored and organized packaging by layer. 
Going forward, I will continue flagging/correcting violations of Clean Architecture, implementing relevant design 
patterns (ie. Simple Factory).

Samraj: Implemented `PropertyManage`, `BankManager`, `PropertyTile`, and helped with `GameController`, `BoardManager` and smaller
other methods across various classes. Plan on working on GUI as well as cleaning up the code and adding features for 
phase 2.

Steve: Implemented `SurpriseTile`, `Card` and `CardDeck` in the entities layer. Plan on adding additional features to
`SurpriseTile`, GUI, and cleaning up the code.