import heapq

# Define constants for the board
PLAYER_X = 'X'
PLAYER_O = 'O'
EMPTY = ' '

# Goal State: If a player wins, it's a goal state
WINNING_COMBINATIONS = [
    [0, 1, 2],  # Rows
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],  # Columns
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],  # Diagonals
    [2, 4, 6]
]

# State representation: 9 positions (board)
def print_board(board):
    for i in range(0, 9, 3):
        print(board[i:i+3])

# Check if a player has won
def check_win(board, player):
    for combo in WINNING_COMBINATIONS:
        if all(board[i] == player for i in combo):
            return True
    return False

# Heuristic function: Number of lines where the player is closer to a win
def heuristic(board, player):
    score = 0
    opponent = PLAYER_X if player == PLAYER_O else PLAYER_O
    
    for combo in WINNING_COMBINATIONS:
        player_count = sum(1 for i in combo if board[i] == player)
        opponent_count = sum(1 for i in combo if board[i] == opponent)
        
        # If no opponent marks in the combination, count the lines where the player is closer to a win
        if opponent_count == 0:
            score += player_count
    
    return score

# Generate all possible moves from the current board state
def get_possible_moves(board):
    return [i for i, v in enumerate(board) if v == EMPTY]

# Perform a move on the board and return the new board state
def make_move(board, move, player):
    new_board = board[:]
    new_board[move] = player
    return new_board

# A* search algorithm for Tic-Tac-Toe
def a_star_search(start_board, player):
    open_list = []
    closed_list = set()
    
    # g(n): Cost to reach the current node (move count)
    # h(n): Heuristic value (how close we are to winning)
    start_node = (start_board, 0, heuristic(start_board, player), None)
    heapq.heappush(open_list, (start_node[1] + start_node[2], start_node))  # f(n) = g(n) + h(n)
    
    while open_list:
        # Get the node with the lowest f(n)
        _, current_node = heapq.heappop(open_list)
        current_board, g, h, parent = current_node
        
        # Check if we have already visited this state
        if tuple(current_board) in closed_list:
            continue
        
        # If the current state is a goal state (player wins), return the path
        if check_win(current_board, player):
            return current_board
        
        # Add current node to closed list
        closed_list.add(tuple(current_board))
        
        # Explore the possible moves
        for move in get_possible_moves(current_board):
            new_board = make_move(current_board, move, player)
            new_g = g + 1
            new_h = heuristic(new_board, player)
            new_node = (new_board, new_g, new_h, current_node)
            
            if tuple(new_board) not in closed_list:
                heapq.heappush(open_list, (new_g + new_h, new_node))
    
    return None  # No solution found (draw)

# Driver code
if __name__ == "__main__":
    initial_board = [EMPTY] * 9  # Empty Tic-Tac-Toe board
    player = PLAYER_X  # Starting with player X
    
    print("Tic-Tac-Toe A* Algorithm:")
    print_board(initial_board)
    
    solution = a_star_search(initial_board, player)
    
    if solution:
        print("\nSolution found:")
        print_board(solution)
    else:
        print("\nNo solution found.")