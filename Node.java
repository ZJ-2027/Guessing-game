
public class Node
{
	String question = "";
	Node yes = null;
	Node no = null;
	
	public Node(String q)
	{
		question = q;
	}
	
	public boolean isLeaf()
	{
		if((yes == null) && (no == null))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
