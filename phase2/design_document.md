# Design Document

## Design Decisions

Our design decisions for Phase 2 lies on the development of the GUI of our program, Monopoly. As we finished Phase 1, our game was represented with the console, asking the user for inputs, and the inputs were processed through the program; however, our program had no visual representation of the board state, which we felt the need to create it. We all had no or very little prior experiences with GUI in java, but we did extensive research on it and was able to create some form of rudimentary GUI. 

We realized our number of managers were insufficient as many more tasks occurred while working on the GUI. For the sake of the SOLID Design principle, Single Responsibility Principle, we created more managers that takes care of actions, such as buying properties, paying rents and auctioning, to have a cleaner flow of data. This essentially allowed our program to be easier to implement as well as preventing unexpected side-effects for future changes. 

For our board state, we initially created a constant file that contained all the x-y coordinates of where each tiles are, and manually drew the board along with the names. However, we realized if we wanted to add more features, such as colors, text and buildings, we realized drawing the board is not the best choice. Therefore, we decided to add each tiles as an instance of JPanel, which will help us with the editing process and adding more features. 

For additional future design decisions, we wanted to have buttons that has save/load/quit functions, but rather than having buttons, it could be better if we had menus sliders that accommodates for those functions.

Also, we are currently typing our commands on the IDE console, but we have thought of a design where we employ JTextFields, where we can have it in the center of our game or separately on the side to help interacting and visually looking at the game.

## SOLID Design Principles

## Clean Architecture

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

In Phase 1, there were a few instances where we had a violation of the Clean Architecture. Specifically, we had a use case 
    `BoardManager` which communicated with the user directly (skipping controllers and UI) to output/input when dealing with
    different `Tile`s, directly asking the player if they wanted to buy a property, trade with another player, and so on. For Phase 2, we resolved this issue by with dependency inversion. Specifically, we created different output boundary interfaces (such as UseCaseOutputBoundary and GameOutputBoundary), which are implenented by classes in the interface adapters layer. This allowed us to pass raw, unformatted data across boundaries, from inner layers to outer layers, without violating Clean Architecture. 

## Design Patterns
 - **<u>Builder</u>** – We implemented this design pattern to construct the game `Board` from various `Tile` types (`City`, `PublicProperty`, `SpecialTile`), since the **Builder** pattern is well-suited to the constructing complex objects by piecing together simpler elements with the same construction process. In the future, if we decide to implement a feature where users can pick from one of several `Board` themes  (ie. UofT-themed <span style="color:blue">**Monopoly**</span>, Halloween-themed <span style="color:Orange">**Monopoly**</span>, or regular <span style="color:yellow">**Monopoly**</span>), we’ll want to create different `Tile` names  (based on UofT buildings instead of cities). However, the construction process for each of these themed boards should the same – we layer `Tile`s on a board, differentiate some `Tile`s, add pieces to the `Tile`s, etc. (See https://github.com/CSC207-UofT/course-project-group-50/commit/8f1b862dce4b6049a5a0e640464efe1200d25202)
 
  - **<u>Dependency Injection</u>** – To avoid the hard dependency between `City` and `Building` classes, we created the `Building` objects outside the `City` class and pass each `Building` (or a list of `Building`s) into the `City`. (See https://github.com/CSC207-UofT/course-project-group-50/commit/80d377fad4e0fcf37e60484602ccd80946c5a937)
  
  - **<u>Facade</u>** - We used the Facade design pattern, `TileManagerFacade`, to provide a simple interface to the complex subsystem of Tiles and Tile interactions (concerning buying properties, paying rent, upgrading properties, auctioning properties). (See https://github.com/CSC207-UofT/course-project-group-50/commit/67f9b3bb0cc7fc97ad92ca6e9fa1f4902f682090)
  
  - **<u>Factory Method</u>** – Due to time constraints, we didn't have a chance to implement a **Simple Factory Pattern** to manage the construction of `Building`s on a `City` tile.  In the future, we could consider this. Specifically, if a `Player` owns all `City` in a colour block, they can construct different types of buildings on the property,each with a different rental fee and purchase price. Let’s say we have classes for a `House`, `Hotel`, and `Castle`, in order of increasing rental/purchase price. Each of these classes implements a `Building` interface. We will create a `Factory` class, `BuildingFactory`, that contains a factory method to determine which of the buildings should be  instantiated (based on whether the Player can afford the building, and whether the player has indicated that they want to buy that particular building). This method will then create and return an instance of that specific `Building` class. 

## Testing

Our testing covers most entities, all but one non-constant use case, `GameController` and `GameSetUp`. The classes that were not tested were chosen not to be tested either because the class was just calling other methods which were already tested or they required user input or they were classes that implemented the GUI. Within the classes we did testing for, every method that was possible to test was tested with certain methods having separate tests for different outputs which we tailed the inputs to produce. The testing covers the Entities layer and UseCases almost completely and partly the Interface Adapters.
    
Methods that have multiple outputs dependent on user input is a test we were not sure how to complete without it being a complete time sink so we decided to test the actions the method takes in each situation. In this way we know the method call within the method work and we test the main method through playing the game. Taking information off the GUI is something we did not do as it not something we knew how to do and this also seemed like a major time sink.


## Refactoring

The following commits involved refactoring code to improve code style and eliminate code smells:
    
- https://github.com/CSC207-UofT/course-project-group-50/commit/75a8054855452ecb2c5a1d03a0a2d77901a933ce 
- https://github.com/CSC207-UofT/course-project-group-50/commit/97df45e63a921a8cbc35688658731f0a79423e6a
- https://github.com/CSC207-UofT/course-project-group-50/commit/dcca2d91cafb331a738519ce494bd962818436e5

## Code Organization
  
We considered packaging the program in a couple ways, the most basic of which we got to in Phase 1.The one we ended up implementing was organizing based on Clean Architecture by creating a separate folder for each level. This way, it is streamlined with dependencies. Another strategy we considered was by organizing by use cases; for example, separating the `Tile` superclass and all its subclasses along with `PropertyManager` in one folder. This way the  packages would not have as much overlap as the use cases would be contained in their own folder. All of which would be accessed by `GameController` which is an **Interface Adapter**. The main reason we didn’t spend more time planning and implementing a better strategy so far is the time constraint; for the sake of the scope of the project, we decided it would be better to devote our time into trying to have the best functioning program we could. This left us with a quite basic packaging structure that is functional and might change if we continue working on the project.
    
We considered packaging by feature since it’s both simple and intuitive, with a clear connection to the business domain, but we don’t have enough features implemented yet for this strategy to be ideal. Ultimately, we felt that  packaging by layer better met our needs, at least for now. Packaging by layer offers simplicity, clear separation of architecture layer, and ease-of-use. Furthermore, our existing code isn’t nearly complex enough for the “***large buckets of code***” that often arise in packaging by layer to become a serious problem.

## Future Directions

