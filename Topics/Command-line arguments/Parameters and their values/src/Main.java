class Problem {
    public static void main(String[] args) {

        for (int i = 0; i < args.length; i = i+2) {
            System.out.println(String.format("%s=%s", args[i], args[i + 1]));
        }
        //System.out.println(String.format("%s=%s", args[2], args[3]));

    }
}