import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;


    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        ArrayList<String> cast = new ArrayList<String>();
        ArrayList<Movie> moviesWithCast = new ArrayList<Movie>();
        System.out.print("Enter a CAST MEMBER: ");
        String searchTerm = scanner.nextLine();

        int listing =1;

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        for (int i =0; i< movies.size(); i++) //sets up cast arraylist
        {
            String splitString[] = movies.get(i).getCast().split("\\|");


            for (int j = 0; j < splitString.length; j++)
            {
                if (splitString[j].toLowerCase().indexOf(searchTerm) !=-1)
                {
                        if (!cast.contains(splitString[j]))
                        {
                            cast.add(splitString[j]);
                        }
                    }


            }
        }

        for (int i = 1; i < cast.size(); i++) //sorts cast list
        {
            String temp = cast.get(i);
            int j = i-1;

            while (j>= 0 && cast.get(j).compareTo(temp) > 0 )
            {
                cast.set(j+1, cast.get(j));
                j--;
            }
            cast.set(j+1, temp);
        }



        for (int i = 0; i< cast.size(); i++)
        {
            System.out.println(""+listing + "."+ cast.get(i));
            listing++;
        }

        System.out.println("Which CAST MEMBER would you like to see more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCast = cast.get(choice - 1);

        for (int i = 0; i< movies.size(); i++) //searches for movies with the cast
        {
            String splitString[] = movies.get(i).getCast().split("\\|");
            ArrayList<String> splitStringA = new ArrayList<String>(Arrays.asList(splitString));

            if (splitStringA.contains(selectedCast))
            {
                moviesWithCast.add(movies.get(i));
            }
        }

        sortResults(moviesWithCast);

        listing = 1;

        for (int i = 0; i < moviesWithCast.size(); i++)
        {
            System.out.println("" + listing + ". " + moviesWithCast.get(i).getTitle());
            listing++;
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = moviesWithCast.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String keywords = movies.get(i).getKeywords();
            keywords = keywords.toLowerCase();

            if (keywords.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listGenres()
    {
        ArrayList<String> genres = new ArrayList<String>();
        ArrayList<Movie> moviesWithGenre = new ArrayList<Movie>();

        int listing =1;


        for (int i =0; i< movies.size(); i++) //sets genres arraylist
        {
            String splitString[] = movies.get(i).getGenres().split("\\|");



            for (int j = 0; j < splitString.length; j++)
            {
                if (!genres.contains(splitString[j]))
                {
                    genres.add(splitString[j]);
                }
            }
        }

        for (int i = 1; i < genres.size(); i++) //sorts genre list
        {
            String temp = genres.get(i);
            int j = i-1;

            while (j>= 0 && genres.get(j).compareTo(temp) > 0 )
            {
                genres.set(j+1, genres.get(j));
                j--;
            }
            genres.set(j+1, temp);
        }


        for (int i = 0; i< genres.size(); i++)
        {
            System.out.println(""+listing + "."+ genres.get(i));
            listing++;
        }

        System.out.println("Which genre movies would you like to see?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = genres.get(choice - 1);

        for (int i = 0; i< movies.size(); i++) //searches for movies with the genre
        {
            String splitString[] = movies.get(i).getGenres().split("\\|");
            ArrayList<String> splitStringA = new ArrayList<String>(Arrays.asList(splitString));

            if (splitStringA.contains(selectedGenre))
            {
                moviesWithGenre.add(movies.get(i));
            }
        }

        sortResults(moviesWithGenre);

        listing = 1;

        for (int i = 0; i < moviesWithGenre.size(); i++)
        {
            System.out.println("" + listing + ". " + moviesWithGenre.get(i).getTitle());
            listing++;
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = moviesWithGenre.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void listHighestRated()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 1; i < movies.size(); i++)
        {
            Movie temp = movies.get(i);
            int j = i-1;

            while (j>= 0 && movies.get(j).getUserRating() < temp.getUserRating())
            {
                movies.set(j+1, movies.get(j));
                j--;
            }
            movies.set(j+1, temp);
        }

        int listing = 1;

        for (int i = 0; i< 50; i++)
        {
            results.add(movies.get(i));
            System.out.println(listing + "."+ movies.get(i).getTitle() + ", Rating: "+ movies.get(i).getUserRating());
            listing++;
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 1; i < movies.size(); i++)
        {
            Movie temp = movies.get(i);
            int j = i-1;

            while (j>= 0 && movies.get(j).getRevenue() < temp.getRevenue())
            {
                movies.set(j+1, movies.get(j));
                j--;
            }
            movies.set(j+1, temp);
        }

        int listing = 1;

        for (int i = 0; i< 50; i++)
        {
            results.add(movies.get(i));
            System.out.println(listing + "."+ movies.get(i).getTitle() + ", Revenue: "+ movies.get(i).getRevenue());
            listing++;
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
