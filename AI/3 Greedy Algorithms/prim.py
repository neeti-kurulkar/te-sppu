class Graph:
    def __init__(self, v):
        self.v = v
        self.adj_mat = [[0 for _ in range(v)] for _ in range(v)]
    
    def add_edge(self, a, b, w):
        self.adj_mat[a][b] = w
        self.adj_mat[b][a] = w
    
    def prim_mst(self, start):
        visited = [False] * self.v
        visited[start] = True
        print("\nEdge : Weight")
            
        edge_count = 0
        while edge_count < self.v - 1:
            minimum = float('inf')
            x, y = -1, -1
                        
            for i in range(self.v):
                if visited[i]:
                    for j in range(self.v):
                        if not visited[j] and self.adj_mat[i][j] != 0:
                            if minimum > self.adj_mat[i][j]:
                                minimum = self.adj_mat[i][j]
                                x, y = i, j
                        
            print(f"{x}-{y}: {minimum}")
            visited[y] = True
            edge_count += 1   

n = int(input("Enter number of vertices: "))
g = Graph(n)
e = int(input("Enter number of edges: "))

print("Enter edges (u v w):")
for _ in range(e):
    u, v, w = map(int, input().split())
    g.add_edge(u, v, w)

start = int(input("\nEnter starting vertex for MST: "))
g.prim_mst(start)