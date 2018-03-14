enum Player {
    PLAYER_X {
        @Override
        public String toString() {
            return "X";
        }
    }, 
    PLAYER_O {
        @Override
        public String toString() {
            return "O";
        }
    }
}