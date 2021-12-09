# Design Document

## SOLID Design Principles

## Clean Architecture

## Design Patterns
 - **<u>Builder</u>** – We implemented this design pattern to construct the game `Board` from various `Tile` types (`City`, `PublicProperty`, `SpecialTile`), since the **Builder** pattern is well-suited to the constructing complex objects by piecing together simpler elements with the same construction process. In the future, if we decide to implement a feature where users can pick from one of several `Board` themes  (ie. UofT-themed <span style="color:blue">**Monopoly**</span>, Halloween-themed <span style="color:Orange">**Monopoly**</span>, or regular <span style="color:yellow">**Monopoly**</span>), we’ll want to create different `Tile` names  (based on UofT buildings instead of cities). However, the construction process for each of these themed boards should the same – we layer `Tile`s on a board, differentiate some `Tile`s, add pieces to the `Tile`s, etc. (See https://github.com/CSC207-UofT/course-project-group-50/commit/8f1b862dce4b6049a5a0e640464efe1200d25202)
 
  - **<u>Dependency Injection</u>** – To avoid the hard dependency between `City` and `Building` classes, we created the `Building` objects outside the `City` class and pass each `Building` (or a list of `Building`s) into the `City`. (See https://github.com/CSC207-UofT/course-project-group-50/commit/80d377fad4e0fcf37e60484602ccd80946c5a937)
  
  - **<u>Facade</u>** - We used the Facade design pattern, TileManagerFacade, to provide a simple interface to the complex subsystem of Tiles and Tile interactions (concerning buying properties, paying rent, upgrading properties, auctioning properties). (See https://github.com/CSC207-UofT/course-project-group-50/commit/67f9b3bb0cc7fc97ad92ca6e9fa1f4902f682090)
  
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
    .
    We considered packaging by feature since it’s both simple and intuitive, with a clear connection to the business domain, but we don’t have enough features implemented yet for this strategy to be ideal. Ultimately, we felt that  packaging by layer better met our needs, at least for now. Packaging by layer offers simplicity, clear separation of architecture layer, and ease-of-use. Furthermore, our existing code isn’t nearly complex enough for the “***large buckets of code***” that often arise in packaging by layer to become a serious problem.

