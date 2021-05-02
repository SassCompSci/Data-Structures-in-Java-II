package algs42;

import algs42.Digraph;

public class MyDegrees {
	private int[] id;			// array containing vertex indegree
	private int[] od;			// array containing vertex outdegree
	private Bag<Integer> so;	// bag containing sources
	private Bag<Integer> si;	// bag containing sinks
	
	private int numV;	// G.V()
	
	public MyDegrees(Digraph G)
	{
		numV = G.V();
		id = new int[numV];
		od = new int[numV];
		so = new Bag<Integer>();
		si = new Bag<Integer>();
		int count;
		
		// calculate indegrees
		for (int i = 0; i < numV; i++)
		{
			count = 0;
			for (int cV : G.adj(i))
				count++;
			id[i] = count;
			
			// determine sources
			if (id[i] == 0)
				so.add(i);
		}

		// calculate outdegrees
		Digraph R = G.reverse();
		for (int i = 0; i < numV; i++)
		{
			count = 0;			
			for (int cV : R.adj(i))
				count++;
			od[i] = count;
			
			// determine sinks
			if (od[i] == 0)
				si.add(i);
		}
	}
	
	// indegree of v
	public int indegree(int v)
	{ return id[v]; }
	
	// outdegree of v
	public int outdegree(int v)
	{ return od[v]; }
	
	// sources
	Iterable<Integer> sources()
	{
        if (!so.iterator().hasNext()) return null;
		return so;
	}
	
	// sinks
	Iterable<Integer> sinks()
	{
        if (!si.iterator().hasNext()) return null;
		return si;
	}
	
	// is G a map?
	boolean isMap()
	{
		for (int i = 0; i < od.length; i++)
			if (od[i] != 1) return false;
		return true;
	}
}