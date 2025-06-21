public class Show {
    public int id;
    public String name;
    public String language;
    public String[] genres;
    public Rating rating;
    public String status;
    public String premiered;
    public String ended;
    public Network network;
    public String summary;

    public static class Rating {
        public Double average;
    }

    public static class Network {
        public String name;
    }
}
