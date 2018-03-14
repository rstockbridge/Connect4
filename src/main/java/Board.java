import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class Board {

    private final Map<Coordinate, Disc> board = new HashMap<>();
    private static final int NUMBER_OF_ROWS = 6;
    private static final int NUMBER_OF_COLUMNS = 7;
    private GameStatus gameStatus = GameStatus.KEEP_GOING;

    InputStatus addDisc(final Disc disc, int column) {
        int row = 0;
        while (board.containsKey(new Coordinate(row, column)) && row < NUMBER_OF_ROWS) {
            row++;
        }

        if (row == NUMBER_OF_ROWS) {
            return InputStatus.COLUMN_FULL;
        } else {
            board.put(new Coordinate(row, column), disc);
            updateGameStatus();
            return InputStatus.VALID;
        }
    }

    private void updateGameStatus() {
        final List<Coordinate> locationsWithDiscs = new ArrayList<>(board.keySet());
        int discIndex = 0;

        while (discIndex < locationsWithDiscs.size() && !(gameStatus == GameStatus.WINNER)) {
            final Coordinate location = locationsWithDiscs.get(discIndex);
            final Player player = board.get(location).getPlayer();
            int row = location.getX();
            int column = location.getY();

            if (northWins(row, column, player) || southWins(row, column, player) ||
                    eastWins(row, column, player) || westWins(row, column, player) ||
                    northeastWins(row, column, player) || northwestWins(row, column, player) ||
                    southeastWins(row, column, player) || southwestWins(row, column, player)) {
                gameStatus = GameStatus.WINNER;
            }

            discIndex++;
        }

        // check if all locations on the board are filled
        if (!(gameStatus == GameStatus.WINNER) && board.size() == NUMBER_OF_ROWS * NUMBER_OF_COLUMNS) {
            gameStatus = GameStatus.NO_WINNER;
        }
    }

    private boolean northWins(final int startingRow, final int startingColumn, final Player player) {
        for (int testRow = startingRow + 1; testRow < startingRow + 4; testRow++) {
            if (playerDiscNotAtLocation(new Coordinate(testRow, startingColumn), player)) {
                return false;
            }
        }

        return true;
    }

    private boolean southWins(final int startingRow, final int startingColumn, final Player player) {
        for (int testRow = startingRow - 1; testRow > startingRow - 4; testRow++) {
            if (playerDiscNotAtLocation(new Coordinate(testRow, startingColumn), player)) {
                return false;
            }
        }

        return true;
    }

    private boolean eastWins(final int startingRow, final int startingColumn, final Player player) {
        for (int testColumn = startingColumn + 1; testColumn < startingColumn + 4; testColumn++) {
            if (playerDiscNotAtLocation(new Coordinate(startingRow, testColumn), player)) {
                return false;
            }
        }

        return true;
    }

    private boolean westWins(final int startingRow, final int startingColumn, final Player player) {
        for (int testColumn = startingColumn - 1; testColumn > startingColumn - 4; testColumn++) {
            if (playerDiscNotAtLocation(new Coordinate(startingRow, testColumn), player)) {
                return false;
            }
        }

        return true;
    }

    private boolean northeastWins(final int startingRow, final int startingColumn, final Player player) {
        for (int i = 1; i < 4; i++) {
            if (playerDiscNotAtLocation(new Coordinate(startingRow + i, startingColumn + i), player)) {
                return false;
            }
        }

        return true;
    }

    private boolean northwestWins(final int startingRow, final int startingColumn, final Player player) {
        for (int i = 1; i < 4; i++) {
            if (playerDiscNotAtLocation(new Coordinate(startingRow + i, startingColumn - i), player)) {
                return false;
            }
        }

        return true;
    }

    private boolean southeastWins(final int startingRow, final int startingColumn, final Player player) {
        for (int i = 1; i < 4; i++) {
            if (playerDiscNotAtLocation(new Coordinate(startingRow - i, startingColumn + i), player)) {
                return false;
            }
        }

        return true;
    }

    private boolean southwestWins(final int startingRow, final int startingColumn, final Player player) {
        for (int i = 1; i < 4; i++) {
            if (playerDiscNotAtLocation(new Coordinate(startingRow - i, startingColumn - i), player)) {
                return false;
            }
        }

        return true;
    }

    private boolean playerDiscNotAtLocation(final Coordinate location, final Player player) {
        // returns false if location is off board or doesn't contain correct disc
        return !board.containsKey(location) || !(board.get(location).getPlayer() == player);
    }

    GameStatus getGameStatus() {
        return gameStatus;
    }

    void print() {
        System.out.println();

        for (int row = NUMBER_OF_ROWS - 1; row >= 0; row--) {
            System.out.print("|");

            for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
                final Coordinate location = new Coordinate(row, column);

                if (board.containsKey(location)) {
                    printDisc(board.get(location));
                } else {
                    System.out.print(".");
                }
            }

            System.out.print("|\n");
        }

        StringBuilder bottomOfBoard = new StringBuilder(" ");
        StringBuilder columnNumber = new StringBuilder(" ");
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            {
                bottomOfBoard.append("-");
                columnNumber.append(Integer.toString(i + 1));
            }
        }
        System.out.println(bottomOfBoard);
        System.out.println(columnNumber);
        System.out.println();
    }

    private void printDisc(final Disc disc) {
        System.out.print(disc.getPlayer().toString());
    }
}
