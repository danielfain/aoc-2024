import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Day3 {

    public static void main(String[] args) throws IOException {
        String corruptedMemory = Files.readString(Path.of("src/main/resources/day3/input.txt"));
        part1(corruptedMemory);
        part2(corruptedMemory);
    }

    public static void part1(String memory) {
        Map<Integer, String> instructions = parseMultiplyInstructions(memory);
        int total = instructions.values().stream().mapToInt(Day3::multiply).sum();
        System.out.println("Part 1: " + total);
    }

    public static void part2(String memory) {
        List<Range> enabledRanges = findEnabledRanges(memory);
        Map<Integer, String> instructions = parseMultiplyInstructions(memory);

        // Can improve by sorting instruction indices and only looking through ranges once
        int total = instructions.keySet().stream().mapToInt(index -> {
            for (Range range : enabledRanges) {
                if (range.contains(index)) {
                    return multiply(instructions.get(index));
                }
            }
            return 0;
        }).sum();

        System.out.println("Part 2: " + total);
    }

    record Range(int start, int end) {
        public boolean contains(int index) {
            return index >= start && index <= end;
        }
    }

    private static List<Range> findEnabledRanges(String memory) {
        List<Range> ranges = new ArrayList<>();
        int startIdx = 0;

        while (startIdx != -1) {
            int endIdx = memory.indexOf("don't()", startIdx);
            if (endIdx == -1) {
                endIdx = memory.length() - 1;
            }
            ranges.add(new Range(startIdx, endIdx));
            startIdx = memory.indexOf("do()", endIdx);
        }

        return ranges;
    }

    private static Map<Integer, String> parseMultiplyInstructions(String memory) {
        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(memory);
        return matcher.results().collect(Collectors.toMap(MatchResult::start, MatchResult::group));
    }

    private static int multiply(String instruction) {
        String[] numbers = instruction.substring(4, instruction.length() - 1).split(",");
        return Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
    }

}