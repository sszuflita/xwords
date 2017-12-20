public enum Tile {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, BLACK, EMPTY;

    @Override
    public String toString() {
        if (this.equals(EMPTY)) {
            return " ";
        }
        if (this.equals(BLACK)) {
            return "*";
        }
        return name();
    }
}
