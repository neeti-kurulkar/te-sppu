class DisjointSet:
    def __init__(self, n):
        self.parent = list(range(n))
    
    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])  # Path compression
        return self.parent[x]
    
    def union(self, x, y):
        root_x = self.find(x)
        root_y = self.find(y)
        if root_x != root_y:
            self.parent[root_y] = root_x
            return True
        return False


class KruskalGraph:
    def __init__(self, vertices):
        self.V = vertices
        self.edges = []  # Store edges as (weight, u, v)

    def add_edge(self, u, v, weight):
        self.edges.append((weight, u, v))

    def kruskal_mst(self):
        # Step 1: Sort all edges by weight
        self.edges.sort()
        ds = DisjointSet(self.V)

        mst_weight, edge_count = 0, 0
        mst_edges = []

        for weight, u, v in self.edges:
            if ds.union(u, v):  # If no cycle
                mst_edges.append((u, v, weight))
                mst_weight += weight
                edge_count += 1
                if edge_count == self.V - 1:
                    break

        print("\nEdges in the Minimum Spanning Tree:")
        for u, v, w in mst_edges:
            print(f"{u} - {v} : {w}")
        print("Total weight of MST:", mst_weight)


# Example usage
n = int(input("Enter number of vertices: "))
g = KruskalGraph(n)
e = int(input("Enter number of edges: "))

print("Enter edges in format (u v w):")
for _ in range(e):
    u, v, w = map(int, input().split())
    g.add_edge(u, v, w)

g.kruskal_mst()