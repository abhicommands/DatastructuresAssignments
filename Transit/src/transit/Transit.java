package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		trainZero = new TNode();
		trainZero.setDown(new TNode());
		TNode trainNode = trainZero;
		TNode busNode = trainZero.getDown();
		busNode.setDown(new TNode());
		TNode walking = busNode.getDown();
		trainNode.setNext(new TNode(trainStations[0]));
		trainNode = trainNode.getNext();
		busNode.setNext(new TNode(busStops[0]));
		busNode = busNode.getNext();
		int i = 0;
		int j = 0;
		int k = 0;
		while(k < locations.length) {
			walking.setNext(new TNode(locations[k]));
			walking = walking.getNext();
			if(j < busStops.length && busStops[j] == locations[k]) {	
				busNode.setDown(walking);
				if((i < trainStations.length && j < busStops.length) && trainStations[i] == busStops[j]) {
					trainNode.setDown(busNode);
					i++;
					if(i < trainStations.length) {
						trainNode.setNext(new TNode(trainStations[i]));
						trainNode = trainNode.getNext();
					}
				}
				j++;
				if(j < busStops.length){ 
					busNode.setNext(new TNode(busStops[j]));
					busNode = busNode.getNext();
				}
			}
			
			k++;
		}

	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		TNode pointer = trainZero;
		TNode nextVal = pointer.getNext();
		while(nextVal != null) {
			if(nextVal.getLocation() == station) {
				pointer.setNext(nextVal.getNext());
				return;
			}
			pointer = pointer.getNext();
			nextVal = pointer.getNext();
		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
		TNode busNode = trainZero.getDown();
		TNode nextVal = busNode.getNext();
		TNode walk = busNode.getDown();
		while(nextVal != null) {
			if(nextVal.getLocation() == busStop)
				return;
			else if(nextVal.getLocation() > busStop) {
				for(int i = 0; i < busStop; i++) 
					walk = walk.getNext();
				busNode.setNext(new TNode(busStop, nextVal, walk));
				return;
			}
			busNode = nextVal;
			nextVal = nextVal.getNext();
		}
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> path= new ArrayList<TNode>();
		TNode nodePath = trainZero;
		TNode nextVal = nodePath.getNext();
		while(nodePath!= null) {
			path.add(nodePath);
			if(nextVal == null) {
				if(!(nodePath.getDown() == null)) {
					nextVal = nodePath.getDown();
				}
				else return path;
			}
			if(nextVal.getLocation() > destination) {
				nextVal = nodePath.getDown();
			}
			if(nextVal.getLocation() == destination) {
				path.add(nextVal);
				nextVal = nextVal.getDown();
				while(nextVal!= null) {
					path.add(nextVal);
					nextVal = nextVal.getDown();
				}
				return path;
			}
			nodePath = nextVal;
			nextVal = nextVal.getNext();
		}
		return null;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		TNode zeroTrain= trainZero;
		TNode zeroBus = zeroTrain.getDown();
		TNode zeroWalk = zeroBus.getDown();
		TNode head = new TNode(trainZero.getLocation());
		TNode train = head;
		TNode bus = new TNode();
		TNode walk = new TNode();
		while(walk!= null) {
			if(bus!= null && walk.getLocation() == bus.getLocation()) {
				bus.setDown(walk);
				if(train!= null && bus.getLocation() == train.getLocation()) {
					train.setDown(bus);
					zeroBus = zeroBus.getNext();
					if(zeroBus != null) 
						bus.setNext(new TNode(zeroBus.getLocation()));
					else
						bus.setNext(null);
					bus = bus.getNext();
					zeroTrain = zeroTrain.getNext();
					if(zeroTrain!=null) 
						train.setNext(new TNode(zeroTrain.getLocation()));
					else 
						train.setNext(null);
					train = train.getNext();
					
				}
				else {
					zeroBus = zeroBus.getNext();
					if(zeroBus!= null) 
						bus.setNext(new TNode(zeroBus.getLocation()));
					else 
						bus.setNext(null);	
					bus = bus.getNext();
				}
			}
			zeroWalk = zeroWalk.getNext();
			if(zeroWalk != null)
				walk.setNext(new TNode(zeroWalk.getLocation()));
			else
				walk.setDown(null);
			walk = walk.getNext();
			
		}
		return head;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
		TNode bus = trainZero.getDown();
		TNode scooter = new TNode();
		TNode scooter2 = scooter;
		TNode walk = bus.getDown();
		bus.setDown(scooter);
		int i = 0;
		while(scooter2!=null) {
			if(scooter2.getLocation() == bus.getLocation()) {
				bus.setDown(scooter2);
				if(bus.getNext()!= null)
					bus = bus.getNext();
			}
			if(!(i == scooterStops.length))
				scooter2.setNext(new TNode(scooterStops[i]));
			else
			scooter2.setNext(null);

			scooter2 = scooter2.getNext();
			i++;
		}
		while (walk !=  null) {
			if(walk.getLocation() == scooter.getLocation()) {
				scooter.setDown(walk);
				if(scooter.getNext() != null)
					scooter = scooter.getNext();
			}
			walk = walk.getNext();
		}
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
