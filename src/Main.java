import java.util.Arrays;

public class Main {
    static int[][] possibleNeighborhoods = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}, {1, 0, 0}, {0, 1, 1}, {0, 1, 0}, {0, 0, 1}, {0, 0, 0}};
    static int[] ruleset;
    static int screenWidth;

    public static int[] getNeighborhood(int[] gen, int index) {
        int[] neighborhood = new int[3];
        for (int i = 0; i < neighborhood.length; i++) {
            try {
                neighborhood[i] = gen[index - 2 + i];
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return neighborhood;
    }

    public static int[] nextGen(int[] prevGen) {
        int[] nextGen = new int[prevGen.length + 2];
        for (int i = 0; i < nextGen.length; i++) {
            for (int j = 0; j < possibleNeighborhoods.length; j++) {
                if (Arrays.equals(getNeighborhood(prevGen, i), possibleNeighborhoods[j])) {
                    nextGen[i] = ruleset[j];
                }
            }
        }
        return nextGen;
    }

    public static void printArr(int[] gen) {
        String toPrint = "";
        if (gen.length < screenWidth) {
            for (int i = 0; i < (screenWidth - gen.length) / 2; i++) {
                toPrint = toPrint.concat(" ");
            }
        }
        for (int i : gen) {
            if (i == 1)
                toPrint = toPrint.concat("@");
            else toPrint = toPrint.concat(" ");
        }
        if (gen.length > screenWidth) {
            toPrint = toPrint.substring((toPrint.length() / 2 - screenWidth / 2) + 1, toPrint.length() / 2 + screenWidth / 2);
        }
        System.out.println(toPrint);
    }

    public static void toBinaryArr(String binaryString) {
        int missingDigits = 8 - binaryString.length();
        ruleset = new int[binaryString.length() + missingDigits];
        for (int i = 0; i < ruleset.length; i++) {
            if (i < missingDigits) {
                ruleset[i] = 0;
            } else {
                ruleset[i] = Integer.parseInt(String.valueOf(binaryString.charAt(i - missingDigits)));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // get to the end of the terminal for "smooth scrolling" (windows cmd thing)
        String newLines = "\n";
        for (int i = 0; i < 10000; i++) {
            newLines = newLines.concat("\n");
        }
        System.out.println(newLines);

        int[] gen = {1};
        toBinaryArr(Integer.toBinaryString(Integer.parseInt(args[2])));
        screenWidth = Integer.parseInt(args[1]);
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            printArr(gen);
            gen = nextGen(gen);
            Thread.sleep(1000 / 30);
        }
    }
}
