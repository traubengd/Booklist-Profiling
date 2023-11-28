import java.io.*;
import java.util.*;

class Profile {
	String name;
	String[] bookTitles;
	
	public Profile(String nameInput, String[] bookTitlesInput){
		name = nameInput;
		bookTitles = bookTitlesInput;
	}
	
	public String getName(){
		return name;
	}
	
	public String[] getBookTitles(){
		return bookTitles;
	}
}

public class Profiling {

   /* This is a program that reads in a file of profile data to create profiles of persons and the books they have read, 
    * printing their booklist as well as offering recommendations for books to read next
    */
	
	static ArrayList<Profile> profiles = new ArrayList<Profile>();
	static String userQuery;
	static Profile queriedProfile;

   public static void main(String []args) {
	   try {
		   readFile();
	   }
	   catch (IOException e) {
		   e.printStackTrace();
		   System.out.println("Unable to read the specified file");
	   }
	   System.out.println("Please enter (part of) a user's name to search database");
	   userQuery = getUserQuery();
	   queriedProfile = findProfile(userQuery);
	   try {
		   System.out.println("Profile name: " + queriedProfile.getName());
		   System.out.println("List of books: " + Arrays.toString(queriedProfile.getBookTitles()));
	   }
	   catch (NullPointerException e){
		   e.printStackTrace();
		   System.out.println("Could not find queried profile in database");
	   }
	   System.out.println("Please enter (part of) a book title to search database for readers");
	   userQuery = getUserQuery();
	   System.out.println("Readers that have read books containing your query:");
	   printReaders(userQuery);
	   System.out.println("Please enter (part of) a user's name to search database and get book recommendations");
	   userQuery = getUserQuery();
	   queriedProfile = findProfile(userQuery);
	   try {
		   System.out.println("Profile name: " + queriedProfile.getName());
		   System.out.println(getRecommendation(queriedProfile));
	   }
	   catch (NullPointerException e){
		   e.printStackTrace();
		   System.out.println("Could not find queried profile in database");
	   }
   }
   
   public static void readFile() throws IOException {
	   String fileName = "profiling-data.txt";
	   BufferedReader br = new BufferedReader(new FileReader(fileName));
	   
	   String line;
	   
	   while ((line = br.readLine()) != null) {
		   String[] parsedProfile = line.split(", "); //Note: the provided dataset has some corruption, since a few booktitles contain commas, the current method for parsing cannot handle these exceptions. Resolving this would probably require changing the dataset to enable escape or change the separations.
		   String[] bookArray = new String [parsedProfile.length-1];
		   System.arraycopy(parsedProfile, 1, bookArray, 0, parsedProfile.length-1);
		   Profile newProfile = new Profile(parsedProfile[0], bookArray);
		   profiles.add(newProfile);
	   }
   }
   
   public static String getUserQuery(){
	   Scanner input = new Scanner(System.in);
	   return input.nextLine();
   }
   
   public static Profile findProfile(String query){
	   for(Profile pr : profiles){
		   if (pr.name.toLowerCase().contains(query.toLowerCase())){
			   return pr;
		   }
	   }
	   return null;
   }
   
   public static void printReaders(String query){
	   for(Profile pr : profiles){
		   if (Arrays.toString(pr.getBookTitles()).toLowerCase().contains(query.toLowerCase())){
			   System.out.println(pr.getName());
		   }
	   }
   }
   
   public static String getRecommendation(Profile queryProfile){
	   Set<String> querySet = new HashSet<>(Arrays.asList(queryProfile.bookTitles));
	   for (Profile pr : profiles){
		   Set<String> compareSet = new HashSet<>(Arrays.asList(pr.bookTitles));
		   Set<String> intersection = new HashSet<String>(querySet);
		   intersection.retainAll(compareSet);
		   if (intersection.size()>=3){
			   Set<String> difference = new HashSet<String>(compareSet);
			   difference.removeAll(querySet);
			   if(difference.size()!=0){
				   String bookRecommendation = difference.iterator().next();
				   return queryProfile.getName() + ", we recommend " + bookRecommendation + " to you!";
			   }
		   }
	   }
	   return "There are no recommendations to be given at this time, try again later!";
   }
}