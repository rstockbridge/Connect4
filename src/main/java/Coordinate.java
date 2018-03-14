final class Coordinate {
    private final int x;
    private final int y;

    Coordinate(final int inputX, final int inputY) {
        x = inputX;
        y = inputY;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return (x * 31) ^ y;
    }

    @Override
    public boolean equals(Object o) {
        Coordinate other = (Coordinate) o;
        return (x == other.x && y == other.y);
    }
}