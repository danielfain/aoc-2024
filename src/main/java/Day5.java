import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day5 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/day5/input.txt"));

        int sectionDivideIndex = lines.indexOf(lines.stream().filter(String::isBlank).findFirst().orElseThrow());
        List<String> rules = lines.subList(0, sectionDivideIndex);
        List<String> updates = lines.subList(sectionDivideIndex + 1, lines.size());

        part1(rules, updates);
        part2(rules, updates);
    }

    public static void part1(List<String> rules, List<String> updates) {
        Map<String, Set<String>> pageSuccessors = new HashMap<>();
        Map<String, Set<String>> pagePredecessors = new HashMap<>();

        for (String rule : rules) {
            String[] pages = rule.split("\\|");
            pageSuccessors.computeIfAbsent(pages[0], page -> new HashSet<>()).add(pages[1]);
            pagePredecessors.computeIfAbsent(pages[1], page -> new HashSet<>()).add(pages[0]);
        }

        Comparator<String> pageComparator = (p1, p2) -> {
            if (pageSuccessors.containsKey(p1) && pageSuccessors.get(p1).contains(p2)) {
                return -1;
            } else if (pagePredecessors.containsKey(p1) && pagePredecessors.get(p1).contains(p2)) {
                return 1;
            } else {
                return 0;
            }
        };

        int total = 0;

        for (String update : updates) {
            String[] updatePages = update.split(",");
            Arrays.sort(updatePages, pageComparator);
            if (update.equals(String.join(",", updatePages))) {
                int mid = updatePages.length / 2;
                total += Integer.parseInt(updatePages[mid]);
            }
        }

        System.out.println("Part 1: " + total);
    }

    public static void part2(List<String> rules, List<String> updates) {
        Map<String, Set<String>> pageSuccessors = new HashMap<>();
        Map<String, Set<String>> pagePredecessors = new HashMap<>();

        for (String rule : rules) {
            String[] pages = rule.split("\\|");
            pageSuccessors.computeIfAbsent(pages[0], page -> new HashSet<>()).add(pages[1]);
            pagePredecessors.computeIfAbsent(pages[1], page -> new HashSet<>()).add(pages[0]);
        }

        Comparator<String> pageComparator = (p1, p2) -> {
            if (pageSuccessors.containsKey(p1) && pageSuccessors.get(p1).contains(p2)) {
                return -1;
            } else if (pagePredecessors.containsKey(p1) && pagePredecessors.get(p1).contains(p2)) {
                return 1;
            } else {
                return 0;
            }
        };

        int total = 0;

        for (String update : updates) {
            String[] updatePages = update.split(",");
            Arrays.sort(updatePages, pageComparator);
            if (!update.equals(String.join(",", updatePages))) {
                int mid = updatePages.length / 2;
                total += Integer.parseInt(updatePages[mid]);
            }
        }

        System.out.println("Part 2: " + total);
    }

}
