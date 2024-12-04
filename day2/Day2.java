import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class Day2 {

    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./day2/input.txt"))) {
            var reports = lines.map(line -> {
                List<Integer> levels = new ArrayList<>();
                for (String level : line.split(" ")) {
                    levels.add(Integer.parseInt(level));
                }
                return levels;
            }).toList();

            part1(reports);
            part2(reports);
        }
    }

    public static void part1(List<List<Integer>> reports) {
        long safeReports = reports.stream().filter(Day2::isReportSafe).count();
        System.out.println("Part 1: " + safeReports);
    }

    public static void part2(List<List<Integer>> reports) {
        long safeReports = reports.stream().filter(report -> {
            // Checks all possible reports with 1 level missing, should figure out better way
            for (int i = 0; i < report.size(); i++) {
                List<Integer> modifiedReport = new ArrayList<>(report);
                modifiedReport.remove(i);
                if (isReportSafe(modifiedReport)) {
                    return true;
                }
            }
            return false;
        }).count();
        System.out.println("Part 2: " + safeReports);
    }

    private static boolean isReportSafe(List<Integer> report) {
        boolean isAscending = true;
        boolean isDescending = true;

        for (int i = 1; i < report.size(); i++) {
            int prevLevel = report.get(i - 1);
            int level = report.get(i);

            if (prevLevel < level) {
                isDescending = false;
            } else if (level < prevLevel) {
                isAscending = false;
            }

            int diff = Math.abs(level - prevLevel);
            if (diff < 1 || diff > 3) {
                return false;
            }
        }

        return isAscending ^ isDescending;
    }

}