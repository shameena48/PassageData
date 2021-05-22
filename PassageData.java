/***
   Author: Shameena
***/
import java.io.*;
import java.util.*;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class PassageData{

    static int totalWordCount = 0;
    static List<List<String>> wordsArray = new ArrayList<>();
    static Map<String, Integer> map = new HashMap<>();
    static Map<String, Integer> treemap = new TreeMap<>();
    static List<List<String>> sentenceList = new ArrayList<>();
    
   
   // Method calculates and returns the total wordcount in file
	public static long processWordsAndSentences(Path filepath) throws Exception{
      
      try{
   		FileReader file = new FileReader(filepath.toString());
   	   BufferedReader br = new BufferedReader(file);
         String str;
   		while((str = br.readLine()) != null){
         
            // Get words from each line of file
            String[] strArray = str.split(" ");
            wordsArray.add(Arrays.asList(strArray));
            
            // Extract sentences from the file 
            String[] sentence = str.split("[!?.]+");
            sentenceList.add(Arrays.asList(sentence));
            
            totalWordCount = totalWordCount + strArray.length;
   		}
        br.close();
     }catch(FileNotFoundException ex ){
         System.out.println("An error occurred.");
         ex.printStackTrace();
     }
     
     return totalWordCount;
	}
   
   //Method identifies top10 words & displays them in sorted order
   public static List<Map.Entry<String, Integer>> displayTop10Words(int top){
 
      for(List<String> wordsArr: wordsArray){
      
         for(String word : wordsArr){
            word = word.replaceAll("[^a-zA-Z0-9]","");
            word = word.toLowerCase();
            if(map.containsKey(word)){
                map.put(word, map.get(word)+1);
            }else{
                map.put(word, 1);
            }
         }
      }
      
      // Return only Top 10 used words
      List<Map.Entry<String, Integer>> resultList = new ArrayList<>(map.entrySet());
      // used Anonymous class
      Collections.sort(resultList, new Comparator<Map.Entry<String, Integer>>(){
       @Override
       public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
           return obj1.getValue().compareTo(obj2.getValue());        
       }
      });
      Collections.reverse(resultList);
     return resultList.subList(0, top+1);
      
   }
  
   /** Returns the last sentence - contains most used word **/
   public static String displayLastSentence(String mostUsedWord){
      if(mostUsedWord == null){
         return null;
      }
      List<String> sentences = new ArrayList<>(); 
      for(List<String> sents : sentenceList){
      
         for(String sentence : sents){
               sentences.add(sentence.toLowerCase());
         }
      }
      // Iterate sentences from end to find the last sentence using mostused word
      for(int i = sentences.size()-1; i >= 0; i--){
         String s1 = sentences.get(i);
         if(s1.contains(mostUsedWord.toLowerCase())){
            return sentences.get(i);
         }
      }
      
     return null;
   }
   
   
	public static void main(String[] args) throws Exception{
   
         String mostUsedWord = null;
         // Get file from currentWorkingDirectory
         Path filepath = Paths.get("passage.txt");
         
         long totalWordCount =  processWordsAndSentences(filepath);
         System.out.println("Total Word Count in given file "+ totalWordCount+"\n");

         List<Map.Entry<String, Integer>> res = displayTop10Words(9);
         System.out.println("Top 10 words used & sorted on frequency of words are displayed below");
         for(Map.Entry<String, Integer> mapElems : res){
            treemap.put(mapElems.getKey(),mapElems.getValue());
            System.out.println(mapElems.getKey() +" : " +mapElems.getValue());
            if(mostUsedWord == null){
               mostUsedWord = mapElems.getKey();
            }
         }
        
         System.out.println("\n Top 10 words used & sorted on words: \n");
         for(Map.Entry<String, Integer> mapElems : treemap.entrySet()){
            System.out.println(mapElems.getKey() +" : " +mapElems.getValue());
          }
         System.out.println("\n");
         String lastsentence = displayLastSentence(mostUsedWord);
         System.out.println("Lastsentence Containing MostUsed Word "+'"'+ mostUsedWord +'"'+" is: \n"+ lastsentence);
    }
}


