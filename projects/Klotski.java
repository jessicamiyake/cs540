import java.util.*;

class Block {

}

class GameState {      
	public int[][] board = new int[5][4];
	public GameState parent = null;
	public int cost = 0;
	public int steps = 0;
	public ArrayList<int[]> empties = new ArrayList<int[]>();
	public int h = 0;

	public GameState(int[][] inputBoard, int steps) {
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
				this.board[i][j] = inputBoard[i][j];
		this.steps = steps;
	}

	public GameState(int[][] inputBoard) {
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
				this.board[i][j] = inputBoard[i][j];
	}
	public ArrayList<int[]> findEmptySpaces() {
		int[] coordinate = new int[2];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.board[i][j] == 0) {
					coordinate = new int[] {i,j};
					empties.add(coordinate);
				}
			}
		}
		return empties;
	}

	public int Manhattan() {
		int m;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.board[i][j] == 1 && this.board[i+1][j+1] == 1) {
					m = Math.abs(3-i)+Math.abs(1-j);
					return m;
				}
			}
		}
		return 0;
	}
	// get all successors and return them in sorted order
	public List<GameState> getNextStates() {
		List<GameState> successors = new ArrayList<>();
		//successors.add(sameState);
		ArrayList<int[]> emptySpaces = findEmptySpaces();
		//flying tiles
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				int currTile = this.board[i][j];
				if (currTile == 4) {
					for (int k = 0; k < 2; k++) {
						int[][] nextBoard = new int[5][4];
						for (int x = 0; x < 5; x++) {
							for (int y = 0; y < 4; y++) {
								nextBoard[x][y] = this.board[x][y];
							}
						}
						int tile = nextBoard[i][j];
						int space = nextBoard[emptySpaces.get(k)[0]][emptySpaces.get(k)[1]];
						nextBoard[i][j] = space;
						nextBoard[emptySpaces.get(k)[0]][emptySpaces.get(k)[1]] = tile;
						GameState nextState = new GameState(nextBoard);
						successors.add(nextState);
					}
				}
				if (currTile == 0) {
					int[][] anotherBoard = new int[5][4];
					for (int x = 0; x < 5; x++) {
						for (int y = 0; y < 4; y++) {
							anotherBoard[x][y] = this.board[x][y];
						}
					}
					//check up
					if (i > 0) {
						int blockAbove = anotherBoard[i-1][j];
						switch (blockAbove) {
						case 1:
							if (j < 3 && anotherBoard[i][j+1] == 0 && anotherBoard[i-1][j+1] == 1) {
								anotherBoard[i-2][j] = 0;
								anotherBoard[i-2][j+1] = 0;
								anotherBoard[i][j] = 1;
								anotherBoard[i][j+1] = 1;
								GameState move1down = new GameState(anotherBoard);
								successors.add(move1down);
							}
							break;
						case 2:
							anotherBoard[i-2][j] = 0;
							anotherBoard[i][j] = 2;
							GameState move2down = new GameState(anotherBoard);
							successors.add(move2down);
							break;
						case 3:
							if (j < 3 && anotherBoard[i][j+1] == 0 && anotherBoard[i-1][j+1] == 3) {
								anotherBoard[i-1][j] = 0;
								anotherBoard[i-1][j+1] = 0;
								anotherBoard[i][j] = 3;
								anotherBoard[i][j+1] = 3;
								GameState move3down = new GameState(anotherBoard);
								successors.add(move3down);
							}
							break;
						default:
							break;
						}
					}
					//check left
					if (j > 0) {
						int blockLeft = anotherBoard[i][j-1];
						switch (blockLeft) {
						case 1:
							if (i < 4 && anotherBoard[i+1][j] == 0 && anotherBoard[i+1][j-1] == 1) {
								anotherBoard[i][j-2] = 0;
								anotherBoard[i+1][j-2] = 0;
								anotherBoard[i][j] = 1;
								anotherBoard[i+1][j] = 1;
								GameState move1right = new GameState(anotherBoard);
								successors.add(move1right);
							}
							break;
						case 2:
							if (i < 4 && anotherBoard[i+1][j] == 0 && anotherBoard[i+1][j-1] == 2) {
								if (i > 0 && anotherBoard[i-1][j-1] == 2) {
									break;
								}
								anotherBoard[i][j] = 2;
								anotherBoard[i+1][j] = 2;
								anotherBoard[i][j-1] = 0;
								anotherBoard[i+1][j-1] = 0;
								GameState move2right = new GameState(anotherBoard);
								successors.add(move2right);
							}
							break;
						case 3:
							anotherBoard[i][j-2] = 0;
							anotherBoard[i][j] = 2;
							GameState move3right = new GameState(anotherBoard);
							successors.add(move3right);
							break;
						default:
							break;
						}
					}
					//check down
					if (i < 4) {
						int blockBelow = this.board[i+1][j];
						switch (blockBelow) {
						case 1:
							if (j < 3 && this.board[i][j+1] == 0 && this.board[i+1][j+1] == 1) {
								anotherBoard[i+2][j+1] = 0;
								anotherBoard[i+2][j] = 0;
								anotherBoard[i][j] = 1;
								anotherBoard[i][j+1] = 1;
								GameState move1up = new GameState(anotherBoard);
								successors.add(move1up);
							}
							break;
						case 2:
							anotherBoard[i+2][j] = 0;
							anotherBoard[i][j] = 2;
							GameState move2up = new GameState(anotherBoard);
							successors.add(move2up);
							break;
						case 3:
							if (j < 3 && this.board[i][j+1] == 0 && this.board[i+1][j+1] == 3) {
								anotherBoard[i+1][j] = 0;
								anotherBoard[i+1][j+1] = 0;
								anotherBoard[i][j+1] = 3;
								anotherBoard[i][j] = 3;
								GameState move3up = new GameState(anotherBoard);
								successors.add(move3up);
							}
							break;
						default:
							break;
						}
					}
					//check right
					if (j < 3) {
						int blockRight = this.board[i][j+1];
						switch (blockRight) {
						case 1:
							if (i < 4 && this.board[i+1][j] == 0 && this.board[i+1][j+1] == 1) {
								anotherBoard[i][j+2] = 0;
								anotherBoard[i+1][j+2] = 0;
								anotherBoard[i][j] = 1;
								anotherBoard[i+1][j] = 1;
								GameState move1left = new GameState(anotherBoard);
								successors.add(move1left);
							}
							break;
						case 2:
							if (i < 4 && this.board[i+1][j] == 0 && this.board[i+1][j+1] == 2) {
								if (i > 0 && this.board[i-1][j+1] == 2) {
									break;
								}
								anotherBoard[i+1][j] = 2;
								anotherBoard[i][j] = 2;
								anotherBoard[i][j+1] = 0;
								anotherBoard[i+1][j+1] = 0;
								GameState move2left = new GameState(anotherBoard);
								successors.add(move2left);
							}
							break;
						case 3:
							anotherBoard[i][j+2] = 0;
							anotherBoard[i][j] = 3;
							GameState move3left = new GameState(anotherBoard);
							successors.add(move3left);
							break;
						default:
							break;
						}
					}
				}
			}
		}
		Collections.sort(successors, GameState.stateComparator);
		return successors;
	}

	// return the 20-digit number as ID
	public String getStateID() {  
		String s = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++)
				s += this.board[i][j];
		}
		return s;
	}

	public void printBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++)
				System.out.print(this.board[i][j]);
			System.out.println();
		}
	}

	// check whether the current state is the goal
	public boolean goalCheck() {        
		if (this.board[4][1] == 1 && this.board[4][2] == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object x) {
		GameState y = (GameState) x;
		if (this.getStateID().equals(y.getStateID())) {
			return true;
		} else return false;
	}

	public static Comparator<GameState> stateComparator = new Comparator<GameState>() {
		public int compare(GameState o1, GameState o2) {
			return o1.getStateID().compareTo(o2.getStateID());
		}
	};
}

class AStarSearch{
	Queue<GameState> openSet;
	Set<GameState> closedSet;

	//Comparator for the GameState
	public Comparator<GameState> stateComparator = new Comparator<GameState>() {
		@Override
		public int compare(GameState o1, GameState o2) {
			if (o1.cost - o2.cost != 0)
				return o1.cost - o2.cost;
			else
				return o1.getStateID().compareTo(o2.getStateID());
		}
	};

	public int compare(GameState o1, GameState o2) {
		if (o1.cost - o2.cost != 0)
			return o1.cost - o2.cost;
		else
			return o1.getStateID().compareTo(o2.getStateID());
	}

	public boolean checkOpen(Queue<GameState> o, GameState g) {
		for (GameState q : o) {
			if (g.equals(q)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkClosed(Set<GameState> c, GameState g) {
		for (GameState h : c) {
			if (g.equals(h)) {
				return true;
			}
		}
		return false;
	}

	//print the states of board in open  set
	public void printOpenList(int flag, GameState state) {
		if (flag == 200 || flag == 400) {
			System.out.println("OPEN");
			for (GameState node : openSet) {
				node.cost = node.steps + node.h;
				System.out.println(node.getStateID());
				node.printBoard();
				System.out.println(node.cost+" "+node.steps+" "+node.h);
				if (node.parent == null) {
					System.out.println("null");
				} else System.out.println(node.parent.getStateID());
			}
		}
	}

	public void printClosedList(int flag, GameState state) {
		if (flag == 200 || flag == 400) {
			System.out.println("CLOSED");
			for (GameState node : closedSet) {
				node.cost = node.steps + node.h;
				System.out.println(node.getStateID());
				node.printBoard();
				System.out.println(node.cost+" "+node.steps+" "+node.h);
				if (node.parent == null) {
					System.out.println("null");
				} else System.out.println(node.parent.getStateID());
			}
		}
	}


// implement the A* search
public GameState aStarSearch(int flag, GameState state) {
	openSet = new PriorityQueue<>(stateComparator);
	closedSet = new HashSet<>();
	int goalCheck = 0;
	int maxOPEN = -1;
	int maxCLOSED = -1;
	int steps = 0;
	int i = 1;
	GameState goal = state;
	openSet.add(state);
	while (!openSet.isEmpty()) {
		GameState n = openSet.remove();
		if (flag == 400 || flag == 500) {
			n.h = n.Manhattan();
		}
		n.cost = n.steps + n.h;
		maxCLOSED = Integer.max(maxCLOSED, closedSet.size());
		if (flag == 200 || flag == 400) {
			System.out.println("iteration "+i);
			System.out.println(n.getStateID());
			n.printBoard();
			System.out.println(n.cost+" "+n.steps+" "+n.h);
			if (n.parent == null) {
				System.out.println("null");
			} else System.out.println(n.parent.getStateID());
		}
		goalCheck += 1;
		if (n.goalCheck()) {
			goal = n;
			break;
		}
		closedSet.add(n);
		List<GameState> successors = n.getNextStates();
		for (GameState nchild : successors) {
			nchild.parent = n;
			if (flag == 400 || flag == 500) {
				nchild.h = nchild.Manhattan();
			}
			nchild.steps = nchild.parent.steps + 1;
			nchild.cost = nchild.steps + nchild.h;
			//				if (nchild.getStateID().equals("22332202442201140114")) {
			//					System.out.println("INVALID BOARD!!");
			//					nchild.printBoard();
			//					System.out.println("parent is:");
			//					nchild.parent.printBoard();
			//					System.exit(0);
			//				}
			if (checkOpen(openSet, nchild)) {
				for (GameState node : openSet) {
					if (node.equals(nchild)) {
						if (compare(node, nchild) > 0) {
							node.cost = nchild.cost;
							node.parent = nchild.parent;
						}
					}
				}
			}
			else if (checkClosed(closedSet, nchild)) {
				for (GameState node : closedSet) {
					if (node.equals(nchild)) {
						if (compare(node, nchild) > 0) {
							closedSet.remove(node);
							openSet.add(nchild);
							maxOPEN = Integer.max(maxOPEN, openSet.size());
						}
					}
				}
			} else {
				openSet.add(nchild);
				maxOPEN = Integer.max(maxOPEN, openSet.size());
			}
		}
		printOpenList(flag, state);
		printClosedList(flag, state);
		i += 1;
	}
	if (flag == 300 || flag == 500) {
		List<GameState> solutionPath = new ArrayList<GameState>();
		while (goal != null) {
			solutionPath.add(goal);
			goal.printBoard();
			System.out.println();
			goal = goal.parent;
		}
		steps = solutionPath.size() - 1;
		System.out.println("goalCheckTimes " + goalCheck);
		System.out.println("maxOPENSize " + maxOPEN);
		System.out.println("maxCLOSEDSize " + maxCLOSED);
		System.out.println("steps " + steps);
	}
	return state;
}
}

public class Klotski {
	public static void printNextStates(GameState s) {
		List<GameState> states = s.getNextStates();
		for (GameState state: states) {
			state.printBoard();
			System.out.println();
		}
	}

	public static void main(String[] args) {
		if (args == null || args.length < 21) {
			return;
		}
		int flag = Integer.parseInt(args[0]);
		int[][] board = new int[5][4];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = Integer.parseInt(args[i * 4 + j + 1]);
			}                
		}        
		GameState s = new GameState(board, 0);

		if (flag == 100) {
			printNextStates(s);
			return;
		}

		AStarSearch search = new AStarSearch();        
		search.aStarSearch(flag, s);  

	}

}
