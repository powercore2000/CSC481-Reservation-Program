package frontend.java;

public class AppState
{
    private static boolean signedIn = false;

    public static boolean isSignedIn() {

        return signedIn;
    }

    public static void setSignedIn(boolean value)
    {
        signedIn = value;
    }
}
