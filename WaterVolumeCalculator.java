import java.util.Scanner;

public class WaterVolumeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Unesite broj kolona: ");
        int cols = scanner.nextInt();
        System.out.print("Unesite broj redova: ");
        int rows = scanner.nextInt();

        int[] heights = new int[cols];

        System.out.println("Unesite broj kockica sa vodom u svakoj koloni:");
        for (int i = 0; i < cols; i++) {
            System.out.print("Kolona " + (i + 1) + ": ");
            heights[i] = scanner.nextInt();
        }

        int[][] structure = buildStructure(rows, cols, heights);

        System.out.println("Početna struktura:");
        printStructure(structure, rows, cols);

        int totalWater = calculateWaterVolume(structure, rows, cols);

        System.out.println("Ukupno vode koja može stati: " + totalWater);
        System.out.println("Struktura sa vodom:");
        printStructure(structure, rows, cols);

        scanner.close();
    }

    public static int[][] buildStructure(int rows, int cols, int[] heights) {
        int[][] structure = new int[rows][cols];
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < heights[col]; row++) {
                structure[rows - 1 - row][col] = 1;
            }
        }
        return structure;
    }

    public static void printStructure(int[][] structure, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (structure[i][j] == 1) {
                    System.out.print("#");
                } else if (structure[i][j] == 2) {
                    System.out.print("~");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static int calculateWaterVolume(int[][] structure, int rows, int cols) {
        int totalWater = 0;

        for (int row = 0; row < rows; row++) {
            int[] leftMax = new int[cols];
            int[] rightMax = new int[cols];

            leftMax[0] = structure[row][0];
            for (int col = 1; col < cols; col++) {
                leftMax[col] = Math.max(leftMax[col - 1], structure[row][col]);
            }

            rightMax[cols - 1] = structure[row][cols - 1];
            for (int col = cols - 2; col >= 0; col--) {
                rightMax[col] = Math.max(rightMax[col + 1], structure[row][col]);
            }

            for (int col = 0; col < cols; col++) {
                int waterLevel = Math.min(leftMax[col], rightMax[col]);
                if (waterLevel > structure[row][col]) {
                    totalWater += waterLevel - structure[row][col];
                    structure[row][col] = 2; 
                }
            }
        }

        return totalWater;
    }
}
