import java.util.Scanner;

final class PlayConnect4 {

    public static void main(String args[]) {

        final Board board = new Board();
        Player currentPlayer = Player.PLAYER_O; // start of first move will switch to Player_X

        while (board.getGameStatus() == GameStatus.KEEP_GOING) {
            currentPlayer = getOtherPlayer(currentPlayer);
            InputStatus inputStatus;

            do {
                final String input = getInput(currentPlayer);

                if (input.matches("[1-7]")) {
                    // subtract one from input so column indices starts at 0
                    inputStatus = board.addDisc(new Disc(currentPlayer), Integer.parseInt(input) - 1);
                } else {
                    inputStatus = InputStatus.INVALID_TEXT;
                }

                if (!(inputStatus == InputStatus.VALID)) {
                    printMoveError(inputStatus);
                }
            }
            while (!(inputStatus == InputStatus.VALID));

            board.print();
        }

        printResult(board.getGameStatus(), currentPlayer);
    }

    private static Player getOtherPlayer(final Player player) {
        if (player == Player.PLAYER_X) {
            return Player.PLAYER_O;
        } else {
            return Player.PLAYER_X;
        }
    }

    private static String getInput(final Player player) {
        System.out.format("\nPlayer %s, enter in a column number: ", player.toString());

        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void printMoveError(final InputStatus inputStatus) {
        if (inputStatus == InputStatus.COLUMN_FULL) {
            System.out.println("This column is full. Try again.\n");
        } else {
            System.out.println("\nYou must enter a column number between 1 and 7. Try again.\n");
        }
    }

    private static void printResult(final GameStatus gameStatus, final Player lastPlayer) {
        switch (gameStatus) {
            case WINNER:
                System.out.format("Player [%s] won!\n", lastPlayer.toString());
                break;
            case NO_WINNER:
                System.out.println("No winner.\n");
        }
    }
}
