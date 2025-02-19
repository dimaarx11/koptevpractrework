import java.util.Scanner;

public class Main {
    private static char[][] board;
    private static int boardSize = 3;
    private static char currentPlayer = 'X';
    private static boolean gameOver = false;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            int choice = showMenu();
            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    changeSettings();
                    break;
                case 0:
                    System.out.println("Вихід з гри");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static int showMenu() {
        System.out.println("=== Головне меню ===");
        System.out.println("1. Почати гру");
        System.out.println("2. Налаштування");
        System.out.println("0. Вихід");
        System.out.print("Ваш вибір: ");
        return scanner.nextInt();
    }

    private static void startGame() {
        board = new char[boardSize][boardSize];
        initializeBoard();
        gameOver = false;
        currentPlayer = 'X';

        while (!gameOver) {
            displayBoard();
            playerMove();
            checkGameState();
            switchPlayer();
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void displayBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j]);
                if (j < boardSize - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i < boardSize - 1) {
                System.out.println("-".repeat(boardSize * 4 - 1));
            }
        }
    }

    private static void playerMove() {
        int row, col;
        while (true) {
            System.out.println("Гравець " + currentPlayer + ", введіть рядок і стовпець (1-" + boardSize + "): ");
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;
            if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                break;
            } else {
                System.out.println("Невірний хід, спробуйте ще раз.");
            }
        }
    }

    private static void checkGameState() {
        if (checkWin()) {
            displayBoard();
            System.out.println("Гравець " + currentPlayer + " переміг!");
            gameOver = true;
        } else if (isDraw()) {
            displayBoard();
            System.out.println("Нічия!");
            gameOver = true;
        }
    }

    private static boolean checkWin() {
        for (int i = 0; i < boardSize; i++) {
            if (checkRow(i) || checkColumn(i)) return true;
        }
        return checkDiagonals();
    }

    private static boolean checkRow(int row) {
        for (int j = 1; j < boardSize; j++) {
            if (board[row][j] != board[row][0] || board[row][j] == ' ') return false;
        }
        return true;
    }

    private static boolean checkColumn(int col) {
        for (int i = 1; i < boardSize; i++) {
            if (board[i][col] != board[0][col] || board[i][col] == ' ') return false;
        }
        return true;
    }

    private static boolean checkDiagonals() {
        boolean mainDiagonal = true, antiDiagonal = true;
        for (int i = 1; i < boardSize; i++) {
            if (board[i][i] != board[0][0] || board[i][i] == ' ') mainDiagonal = false;
            if (board[i][boardSize - i - 1] != board[0][boardSize - 1] || board[i][boardSize - i - 1] == ' ') antiDiagonal = false;
        }
        return mainDiagonal || antiDiagonal;
    }

    private static boolean isDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') return false;
            }
        }
        return true;
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private static void changeSettings() {
        System.out.println("Оберіть розмір поля: 3, 5, 7, 9");
        int newSize = scanner.nextInt();
        if (newSize == 3 || newSize == 5 || newSize == 7 || newSize == 9) {
            boardSize = newSize;
        } else {
            System.out.println("Неправильний розмір, встановлено 3x3 за замовчуванням.");
            boardSize = 3;
        }
    }
}
