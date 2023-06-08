- Added a shipJson() method in the Ship class that converts a Ship object to a ShipJson
- Added a isShipSunk() method in the Ship class that determines if a ship is sunk
- Added a coordJson() method in the Coord class that converts a Coord object into a CoordJson
- Added a private intializeAllShots() method in the AiPlayer class to keep track of the possible shots that
can be made
- Added implementation to the reportDamage() method in each of the classes to change the value of hit
for the Coord on a Ship if it has been hit
- Deleted the countEmptyCoordinates() method in the Board class because it was not necessary
- Deleted the shipsNotSunk() method in the GameOver class because it was not necessary
- Changed the implementation of the isGameOver() method in the GameOver class so that it implements
the new isShipSunk() method