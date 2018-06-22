import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class RedBlackTreeMap_Tester {
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		String choice;
		boolean check = true;
		String filename = "players_homeruns.csv";
		File file = new File(filename);
		String k;
		int v;
		RedBlackTreeMap<String, Integer> RBTM = new RedBlackTreeMap<String, Integer>();
		try{
		Scanner in = new Scanner(file);
		BufferedReader bfRdr = new BufferedReader(new FileReader(new File("players_homeruns.csv")));
		String line = null;
		int test = 1;
		while((line = bfRdr.readLine()) != null){
			String[] splitrow = line.split(",");
			k = splitrow[0];
			v = Integer.parseInt(splitrow[1]);
//			System.out.println("\n\nInsert #" + test + ")");
//			System.out.println("--------------------------");
			RBTM.insert(k,v);
			test++;
		}
		bfRdr.close();
		in.close();
		}catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } 
		RBTM.printStructure();
		System.out.println();		
		System.out.println("1) Find one key that is a leaf (Stan Musial): " + RBTM.find("Stan Musial"));
		System.out.println("2) Find one key that is a root(Honus Wagner): " + RBTM.find("Honus Wagner"));
		System.out.println("3) Find one key that has one Nil child and one non-NIL child (Rogers Hornsby): " + RBTM.find("Rogers Hornsby"));
		System.out.println("4) Find one key that is a red node(Ted Williams): " + RBTM.find("Ted Williams"));

	}

}
