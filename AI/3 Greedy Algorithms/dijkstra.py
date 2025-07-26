import heapq

class DijkstraGraph:
    def __init__(self, vertices):
        self.V = vertices
        self.adj = [[] for _ in range(vertices)]  # Adjacency list

    def add_edge(self, u, v, weight):
        self.adj[u].append((v, weight))
        self.adj[v].append((u, weight))  # For undirected graph

    def dijkstra(self, start):
        dist = [float('inf')] * self.V
        dist[start] = 0
        min_heap = [(0, start)]  # (distance, vertex)

        while min_heap:
            current_dist, u = heapq.heappop(min_heap)

            for neighbor, weight in self.adj[u]:
                if dist[u] + weight < dist[neighbor]:
                    dist[neighbor] = dist[u] + weight
                    heapq.heappush(min_heap, (dist[neighbor], neighbor))

        print("\nShortest distances from source:", start)
        for i in range(self.V):
            print(f"Vertex {i}: {dist[i]}")


# Example usage
n = int(input("Enter number of vertices: "))
g = DijkstraGraph(n)
e = int(input("Enter number of edges: "))

print("Enter edges in format (u v w):")
for _ in range(e):
    u, v, w = map(int, input().split())
    g.add_edge(u, v, w)

source = int(input("Enter source vertex: "))
g.dijkstra(source)