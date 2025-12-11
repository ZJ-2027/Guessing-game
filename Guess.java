import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Guess
{
  public static Scanner s = new Scanner(System.in);
  
  public static String filename = "game.csv";
  
  public static void main(String[] args)
  {
	  boolean playing = true;
	  boolean win = false;
	  Tree t = new Tree();
      importFile(filename, t);
	  
	  Node a = t.root;
	  
	  System.out.print("Welcome to the AI Animal Guessing Game\n\nFirst... ");
	  
	  while(playing)
	  {
		  if(a.isLeaf())
		  {
			  System.out.print("Are you a/an " + a.question + "? ");
		  } else {
			  System.out.print(a.question + " ");
		  }
		  String response = s.nextLine();
		  
		  if(a.isLeaf()) {
			  if((response.charAt(0) == 'y') || (response.charAt(0) == 'Y'))
			  {
				  win = true;
				  System.out.println("YEA! I Win...  Play Again?");
				  response = s.nextLine();
				  if((response.charAt(0) == 'y') || (response.charAt(0) == 'Y'))
				  {
                      a = t.root;
				  }
				  else
				  {
					  playing = false;
				  }
			  }
			  else
			  {
				  //add new animal
				  System.out.println("Dang, Thought I had it.  What was your animal?");
				  String newAnimalName = s.nextLine();  // stores new animal
                  Node newAnimal = new Node(newAnimalName);  //makes node for it
				  //ALL THE REAL WORK				  
				  System.out.println("What is a QUESTION that seperates a " + response + " from a " + a.question + "? ");
				  response = s.nextLine();
				 
				  
				  Node q = new Node(a.question);
				  a.question = response;
				  
				  System.out.println("For a " + newAnimal.question + ", is the answer Yes or No? ");
				  response = s.nextLine();
				  if((response.charAt(0) == 'y') || (response.charAt(0) == 'Y'))
				  {
					  a.yes = newAnimal;
					  a.no = q;
				  }
				  else
				  {
					  a.no = newAnimal;
					  a.yes = q;
				  }
				  System.out.println("Play Again?");
				  response = s.nextLine();
				  if((response.charAt(0) == 'y') || (response.charAt(0) == 'Y'))
				  {
					  //reset and run again
					  a = t.root;
				  }
				  else
				  {
					  playing = false;
				  }
			  }
		  }
		  else
		  {
			  if((response.charAt(0) == 'y') || (response.charAt(0) == 'Y'))
			  {
				//go down yes branch
				  a = a.yes;
			  }
			  else
			  {
				  //go down no branch
				a=a.no;
			  }			  
		  }
	  }
	  System.out.println("OK - Be that way.  Bye.");
	  deleteFile();   //make this one yourself
	  export(t.root,1);
	  s.close();
  }
  
  	public static void importFile(String filename, Tree t)
  	{
  		String line;
        String[] data = new String[10000];  // make an array that can hold up to 10000 values

        try {   // uses old code as base
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            
            while ((line = br.readLine()) != null) 
            {
            	String[] parts = line.split(",");
                int index = Integer.parseInt(parts[0]);   // index for data
                data[index] = parts[1];  // gives data a value
            }
            br.close();
        } catch (IOException e) {
            System.out.println("File Error: " + filename);
        }
        
        Node[] nodes = new Node[10000];   // stores the data in a node
        for (int i = 1; i < data.length; i++) 
        {
            if (data[i] != null) 
            {
                nodes[i] = new Node(data[i]);   // makes a new node with value of data
            }
        }

        for (int i = 1; i < nodes.length; i++)  // for max length of node do a loop
        {
            if (nodes[i] != null) 
            {
                if (2 * i < nodes.length) 
                {
                    nodes[i].yes = nodes[2 * i];  // puts in values for if yes side
                }
                if (2 * i + 1 < nodes.length) 
                {
                    nodes[i].no = nodes[2 * i + 1];  // puts in values for if no side
                }
            }
        }

        if (nodes[1] != null) 
        {
            t.root = nodes[1]; // set the root
        } 
        else 
        {
            t.root = new Node("Elephant");    // mkaes a new root if not exist
        }
            
  	}
  
	public static void export(Node r, int index)
	{
        if (r.yes != null) 
        {
            export(r.yes, index * 2);  // recusion for yes and go forward in index
        }
        
        System.out.println(r.question);
        writeToFile(index, r.question);  // writes to file
        
        if (r.no != null) 
        {
            export(r.no, index * 2 + 1);   // uses recursion
        }
	}
	
	public static void writeToFile(int index, String question)
	{
		try   // old code modified for this assignment
		{
			FileWriter fw = new FileWriter(filename,true);
	        String outString = String.format("%d,%s\n", index, question);
	        fw.write(outString);
			fw.close();
		}
		catch (IOException e)
		{
			
		}
	}
	
	public static void deleteFile() 
	{
        try 
        {
            new FileWriter(filename, false).close();   // creates a new file and closes it
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
