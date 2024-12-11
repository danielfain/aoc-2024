import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day4 {
    private static final List<Direction> dirs = List.of(new Direction(0, -1), new Direction(1, -1), new Direction(1, 0), new Direction(1, 1), new Direction(0, 1), new Direction(-1, 1), new Direction(-1, 0), new Direction(-1, -1));

    record Direction(int dx, int dy) {
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/day4/input.txt"));
        part1(lines);
        part2(lines);
    }

    public static void part1(List<String> lines) {
        int total = 0;

        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.getFirst().length(); col++) {
                for (var dir : dirs) {
                    if (findXmas(lines, row, col, dir)) {
                        total++;
                    }
                }
            }
        }

        System.out.println("Part 1 total: " + total);
    }

    public static void part2(List<String> lines) {
        int total = 0;

        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.getFirst().length(); col++) {
                if (lines.get(row).charAt(col) == 'A') {
                    if (findXmas(lines, row, col)) {
                        total++;
                    }
                }
            }
        }

        System.out.println("Part 2 total: " + total);
    }

    private static boolean findXmas(List<String> lines, int row, int col, Direction dir) {
        for (int i = 0; i < "XMAS".length(); i++) {
            int dx = row + i * dir.dx();
            int dy = col + i * dir.dy();

            if (dx < 0 || dx >= lines.size() || dy < 0 || dy >= lines.getFirst().length()) {
                return false;
            }

            if (lines.get(dx).charAt(dy) != "XMAS".charAt(i)) {
                return false;
            }
        }

        return true;
    }

    private static boolean findXmas(List<String> lines, int row, int col) {
        if (row <= 0 || row >= lines.size() - 1 || col <= 0 || col >= lines.getFirst().length() - 1) {
            return false;
        }

        List<String> validDiagonals = List.of("MAS", "SAM");

        String firstDiagonal = lines.get(row - 1).charAt(col - 1) + "A" + lines.get(row + 1).charAt(col + 1);
        String secondDiagonal = lines.get(row - 1).charAt(col + 1) + "A" + lines.get(row + 1).charAt(col - 1);

        return validDiagonals.contains(firstDiagonal) && validDiagonals.contains(secondDiagonal);
    }

}
