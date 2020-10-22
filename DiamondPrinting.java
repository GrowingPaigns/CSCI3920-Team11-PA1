public class DiamondPrinting {
    public String getCharSequence(char c, int n) {
        String str = "";
        for (int i = 0; i < n; i++) {
            str += c;
        }
        return str;
    }

    public String getDiamond(int n) {
        String str = "";
        int x = 1;

        // if number is not odd, return error
        if (n % 2 == 0)
            return "\nNumber of lines should be Odd\n";

        // if number is not in range return error
        if (n < 1 || n > 99) {
            return "\nNumber should be between 1 - 99\n";
        }

        /* for upper half of the triangle */
        for (int i = 1; i <= n / 2; i++) {
            for (int j = 0; j <= n / 2 - i; j++) {
                str += " ";
            }
            str += getCharSequence('*', x);
            x += 2;
            str += "\n";
        }

        /* middle line */
        str += getCharSequence('*', x);
        x -= 2;
        str += "\n";

        /* for lower half of the triangle */
        for (int i = n / 2; i >= 1; i--) {
            for (int j = 0; j <= n / 2 - i; j++) {
                str += " ";
            }
            str += getCharSequence('*', x);
            x -= 2;
            str += "\n";
        }

        return str;
    }
}
