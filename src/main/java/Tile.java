public enum Tile {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, BLACK, EMPTY;

    public static Tile fromString(String s) {
        if (s.equals("*")) {
            return BLACK;
        }
        if (s.equals("_")) {
            return EMPTY;
        }
        return valueOf(s.toUpperCase());
    }

    @Override
    public String toString() {
        if (this.equals(EMPTY)) {
            return "_";
        }
        if (this.equals(BLACK)) {
            return "*";
        }
        return name();
    }
}
