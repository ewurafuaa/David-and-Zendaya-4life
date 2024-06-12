import java.util.*;

class Edge {
    int target;
    int cost;

    public Edge(int target, int cost) {
        this.target = target;
        this.cost = cost;
    }
}

class Node implements Comparable<Node> {
    int vertex;
    int cost;

    public Node(int vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }
}

public class UniformCostSearch {
    private Map<Integer, List<Edge>> graph;

    public UniformCostSearch() {
        this.graph = new HashMap<>();
    }

    public void addEdge(int source, int target, int cost) {
        this.graph.putIfAbsent(source, new ArrayList<>());
        this.graph.get(source).add(new Edge(target, cost));
    }

    public int ucs(int start, int goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));
        Set<Integer> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.vertex == goal) {
                return current.cost;  // Goal reached
            }

            if (visited.contains(current.vertex)) {
                continue;
            }

            visited.add(current.vertex);

            for (Edge edge : graph.getOrDefault(current.vertex, new ArrayList<>())) {
                if (!visited.contains(edge.target)) {
                    pq.add(new Node(edge.target, current.cost + edge.cost));
                }
            }
        }

        return -1;  // Path not found
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UniformCostSearch ucs = new UniformCostSearch();

        System.out.println("Enter number of edges:");
        int numberOfEdges = scanner.nextInt();

        System.out.println("Enter edges (source target cost):");
        for (int i = 0; i < numberOfEdges; i++) {
            int source = scanner.nextInt();
            int target = scanner.nextInt();
            int cost = scanner.nextInt();
            ucs.addEdge(source, target, cost);
        }

        System.out.println("Enter start node and goal node:");
        int start = scanner.nextInt();
        int goal = scanner.nextInt();

        int result = ucs.ucs(start, goal);
        if (result != -1) {
            System.out.println("Minimum cost from " + start + " to " + goal + " is " + result);
        } else {
            System.out.println("Path not found");
        }

        scanner.close();
    }
}

