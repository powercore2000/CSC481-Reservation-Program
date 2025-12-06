package org.example.resturant;

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
    private static String selectedRestaurantName = "";

    public static void setSelectedRestaurantName(String name)
    {
        selectedRestaurantName = name;
    }

    public static String getSelectedRestaurantName()
    {
        return selectedRestaurantName;
    }
    private static int selectedRestaurant = 0;

    public static int getSelectedRestaurant()
    {
        return selectedRestaurant;
    }

    public static void setSelectedRestaurant(int id)
    {
        selectedRestaurant = id;
    }

}
