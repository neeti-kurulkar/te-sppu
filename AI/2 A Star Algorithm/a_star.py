class EightPuzzle:
    def __init__(self, start_state, goal_state):
        self.start_state = tuple(map(tuple, start_state))  # Convert to tuple
        self.goal_state = tuple(map(tuple, goal_state))    
        
    def find_blank(self, state):
        """ Find the position of the blank (zero) tile """
        for i in range(3):
            for j in range(3):
                if state[i][j] == 0:
                    return i, j

    def get_neighbors(self, state):
        """ Generate possible next states by moving the blank space """
        neighbors = []
        moves = {'Up': (-1, 0), 'Down': (1, 0), 'Left': (0, -1), 'Right': (0, 1)}
        x, y = self.find_blank(state)

        for move, (dx, dy) in moves.items():
            new_x, new_y = x + dx, y + dy
            if 0 <= new_x < 3 and 0 <= new_y < 3:
                new_state = [list(row) for row in state]  # Convert tuple back to list
                new_state[x][y], new_state[new_x][new_y] = new_state[new_x][new_y], new_state[x][y]
                neighbors.append((tuple(map(tuple, new_state)), move))  # Convert back to tuple
        
        return neighbors

    def h(self, state):
        """ Manhattan Distance heuristic function """
        goal_positions = {1: (0, 0), 2: (0, 1), 3: (0, 2),
                          4: (1, 0), 5: (1, 1), 6: (1, 2),
                          7: (2, 0), 8: (2, 1), 0: (2, 2)}  # Goal state positions
        
        h_value = 0
        for i in range(3):
            for j in range(3):
                value = state[i][j]
                if value != 0:
                    goal_x, goal_y = goal_positions[value]
                    h_value += abs(i - goal_x) + abs(j - goal_y)
        
        return h_value

    def a_star(self):
        """ A* search algorithm """
        open_set = {self.start_state}
        closed_set = set()
        g = {self.start_state: 0}
        parents = {self.start_state: None}
        moves = {self.start_state: None}

        while open_set:
            # Select node with the lowest f(n) = g(n) + h(n)
            n = min(open_set, key=lambda state: g[state] + self.h(state))
            
            if n == self.goal_state:
                return self.reconstruct_path(parents, moves, n)

            open_set.remove(n)
            closed_set.add(n)

            for neighbor, move in self.get_neighbors(n):
                if neighbor in closed_set:
                    continue

                tentative_g = g[n] + 1

                if neighbor not in open_set or tentative_g < g[neighbor]:
                    g[neighbor] = tentative_g
                    parents[neighbor] = n
                    moves[neighbor] = move
                    open_set.add(neighbor)

        return None  # No solution found

    def reconstruct_path(self, parents, moves, node):
        """ Reconstruct the path from the goal to the start state with states printed """
        path = []
        state_sequence = []

        while parents[node] is not None:
            path.append(moves[node])
            state_sequence.append(node)
            node = parents[node]

        path.reverse()
        state_sequence.reverse()

        print("Solution Steps:")
        print_state(self.start_state)
        print()

        current_state = self.start_state
        for move, state in zip(path, state_sequence):
            print(f"Move: {move}")
            print_state(state)
            print()

        return path

def print_state(state):
    """ Utility function to print the puzzle state """
    for row in state:
        print(" ".join(str(cell) if cell != 0 else " " for cell in row))

start_state = [[1, 2, 3],
               [4, 0, 5],
               [7, 8, 6]]

goal_state = [[1, 2, 3],
              [4, 5, 6],
              [7, 8, 0]]

solver = EightPuzzle(start_state, goal_state)
solution = solver.a_star()

if solution:
    print("\nSolution Found!")
    print("Steps to solve:", solution)
else:
    print("No solution exists.")
