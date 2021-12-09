# Progress Report

For Phase 2, we add four major focuses:

1) Implementing an intuitive GUI to display board states, token locations, and player statistics

2) Addressing violations of Clean Architecture (such as BoardManager communicating directly with the user, skipping controllers and UI) and SOLID principles (such as GameController's violation of SRP) identified in the Phase 1 feedback

3) Adding documentation and refactoring to fix naming convention violations.

4) Incorporating issues and pull request reviews in our workflow.

### Summary of work done

- **Parth**: Worked on the GUI, GameController, CMDLineUI among other classes. Added missing documentation in a few places. Ensured that SOLID design principles and clean architecture is being followed.
Danny: Added missing documentations; fixed minor errors such as fixing variable names to follow Java conventions;

- **Samraj**: Worked on restructuring the code in adherence to clean architecture, significant files include PropertyManager, BankManager, TileManager, BoardManager and GameController. As well as major testing for Use Cases and Interface Adapters.

- **Jacqueline**: Worked on major restructuring of the GUI, including changes to Presenter and introduction of BoardPanel, PlayerPanel, and GamePanel. Resolved issues with GUI refeshing by creating a GameOutputBoundary interface and passing data transfer objects between layers according to Clean Architecture. Refactored City, Tile, Builder design pattern classes, and added documentation.

- **Steve**: Worked on the GUI.  Fixed minor bugs throughout the program.

- **Anis**: Created the Presenter class and a rudimentary GUI that worked. Re-structured the code so that it followed Clean Architecture. Bug fixes and refactoring.

- **Danny**: Worked on documentation as well fixing minor errors such as naming conventions. 

### Significant pull requests
- **Parth**: https://github.com/CSC207-UofT/course-project-group-50/pull/24
    I worked on save and load and fixed a bug that was stopping them from working as desired, which allowed the game to be saved and loaded correctly. They play an important role in the program by giving the players the flexibility to stop and come back to the game later. I also added the quitGame method, which interacts with the player about saving the game when they quit. Lastly, to make sure that everything was being serialized correctly, I made the necessary instance variables of the class, GameController, being saved.

- **Anis**: https://github.com/CSC207-UofT/course-project-group-50/pull/54

    This pull request introduced a basic GUI that was able to display the monopoly game board visually to the user. It made the game much easier to understand from a user’s point of view.

- **Jacqueline**: https://github.com/CSC207-UofT/course-project-group-50/pull/78

    This pull request introduced significant changes to the way we formatted and updated the GUI, allowing for a more inuitive, informative representation of the board and player stats. By introducing an output boundary interface and data transfer objects, this pull request also resolved issues with refreshing the GUI after each turn.

- **Steve**: https://github.com/CSC207-UofT/course-project-group-50/pull/85 https://github.com/CSC207-UofT/course-project-group-50/pull/55
    
    This pull request was important to me as this was my very first attempt at creating the GUI of the program. Although
    it hasn't made it through our final version, my intuition for the GUI has improved and was able to help creating the GUI.
    Also, discussing managers was not my key strengths but with this merge, I was able to know what the managers tasks are and what layers it communicates to.
- **Danny**: https://github.com/CSC207-UofT/course-project-group-50/pull/14

    This pull request implemented AuctionTile by expanding the PropertyManager class, allowing players on the board to trade when they land on the AuctionTile. 

- **Samraj**: https://github.com/CSC207-UofT/course-project-group-50/commit/413fc05a2878c00182fbf429a63616d0004adf98 and https://github.com/CSC207-UofT/course-project-group-50/pull/58

    The commit was an accident made while working in main. This “pull” was major refactoring for clean architecture more specifically for BoardManager, TileManager which was changed to a facade later, GameController and PropertyManager. The pull (58) is one of 3 which includes all the testing done on use cases, entities and certain interface adapters. 


