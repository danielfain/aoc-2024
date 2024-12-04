import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class Day1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/main/resources/day1/input.txt")).toList();
        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        List<Integer> leftLocationIds = new ArrayList<>();
        List<Integer> rightLocationIds = new ArrayList<>();

        for (String line : lines) {
            String[] locationIds = line.split(" +");
            leftLocationIds.add(Integer.parseInt(locationIds[0]));
            rightLocationIds.add(Integer.parseInt(locationIds[1]));
        }

        Collections.sort(leftLocationIds);
        Collections.sort(rightLocationIds);

        int totalDistance = 0;

        for (int i = 0; i < leftLocationIds.size(); i++) {
            int leftLocationId = leftLocationIds.get(i);
            int rightLocationId = rightLocationIds.get(i);
            totalDistance += Math.abs(leftLocationId - rightLocationId);
        }

        System.out.println("Part 1 answer: " + totalDistance);
    }

    public static void part2(List<String> lines) {
        List<Integer> leftLocationIds = new ArrayList<>();
        Map<Integer, Integer> rightLocationIdCounts = new HashMap<>();

        for (String line : lines) {
            String[] locationIds = line.split(" +");

            int leftLocationId = Integer.parseInt(locationIds[0]);
            leftLocationIds.add(leftLocationId);

            int rightLocationId = Integer.parseInt(locationIds[1]);
            int rightLocationIdCount = rightLocationIdCounts.getOrDefault(rightLocationId, 0);
            rightLocationIdCounts.put(rightLocationId, rightLocationIdCount + 1);
        }

        int similarityScore = 0;

        for (int leftLocationId : leftLocationIds) {
            similarityScore += leftLocationId * rightLocationIdCounts.getOrDefault(leftLocationId, 0);
        }

        System.out.println("Part 2 answer: " + similarityScore);
    }

}
