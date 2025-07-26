class Graph:
    def __init__(self, adj_list, h_dist):
        self.adj_list = adj_list
        self.h_dist = h_dist

    def find_neighbours(self, v):
        """Find the neighbours of a node"""
        return self.adj_list.get(v, [])

    def h(self, n):
        """Return the heuristic distance from current node to goal node"""
        return self.h_dist[n]

    def a_star(self, start, goal):
        """Return the path from start node to goal node (if exists)"""
        open_set = set(start)
        closed_set = set()
        g, parents = {}, {}
        g[start] = 0
        parents[start] = start

        while open_set:
            print(f"\nOpen Set: {open_set}")
            print(f"Closed Set: {closed_set}")

            # Select node with the smallest f = g + h
            current = min(open_set, key=lambda v: g[v] + self.h(v))
            print(f"Processing Node: {current}, f = g + h = {g[current]} + {self.h(current)} = {g[current] + self.h(current)}")

            if current == goal:
                path = []
                while parents[current] != current:
                    path.append(current)
                    current = parents[current]
                path.append(start)
                path.reverse()

                print(f"\nPath Found: {path}")
                return path

            open_set.remove(current)
            closed_set.add(current)

            for neighbour, weight in self.find_neighbours(current):
                if neighbour in closed_set:
                    continue

                tentative_g = g[current] + weight
                if neighbour not in open_set or tentative_g < g.get(neighbour, float('inf')):
                    parents[neighbour] = current
                    g[neighbour] = tentative_g
                    open_set.add(neighbour)

            # Move this inside the main loop to avoid redundant prints
            if open_set:
                next_node = min(open_set, key=lambda v: g[v] + self.h(v))
                print(f"Next Selected Node: {next_node}, f = g + h = {g[next_node]} + {self.h(next_node)} = {g[next_node] + self.h(next_node)}")

        print("\nPath Does not exist!")
        return None

# Adjacency list representation of the graph
adj_list = {
    'S': [('A', 4), ('B', 3)],
    'A': [('D', 6), ('C', 12)],
    'B': [('C', 10), ('E', 7)],
    'C': [('G', 5)],
    'D': [('G', 16)],
    'E': [('C', 2)],
    'G': []
}

# Heuristic distances from each node to the goal
h_dist = {
    'S': 14,
    'A': 12,
    'B': 11,
    'C': 4,
    'D': 11,
    'E': 6,
    'G': 0
}

# Create the graph and execute A* search
g = Graph(adj_list, h_dist)
g.a_star('A', 'E')