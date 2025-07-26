class Graph:
    def __init__(self, v):
        self.v = v
        self.adj_mat = [[0 for _ in range(v)] for _ in range(v)]
    
    def add_edge(self, a, b):
        self.adj_mat[a][b] = 1
        self.adj_mat[b][a] = 1
    
    def dfs(self, node, visited):
        """Recursive Depth First Search (DFS)."""
        if node not in visited:
            print(node, end=' ')
            visited.add(node)
            for neighbor in range(self.v):
                if self.adj_mat[node][neighbor] != 0 and neighbor not in visited:
                    self.dfs(neighbor, visited)
    
    def bfs(self, start):
        """Breadth First Search (BFS)"""
        visited = set()
        queue = [start]
        
        while queue:
            node = queue.pop(0)
            if node not in visited:
                print(node, end=' ')
                visited.add(node)
                for neighbor in range(self.v):
                    if self.adj_mat[node][neighbor] != 0 and neighbor not in visited:
                        queue.append(neighbor)

n = int(input("Enter number of vertices: "))
g = Graph(n)
e = int(input("Enter number of edges: "))

print("Enter edges (u v):")
for _ in range(e):
    u, v = map(int, input().split())
    g.add_edge(u, v)

start_node = int(input("Enter the starting node for traversal: "))

print("DFS:")
g.dfs(start_node, set())
print("\nBFS:")
g.bfs(start_node)