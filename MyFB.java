package algs34;

import java.util.*;
import stdlib.*;

/*
 * Complete the methods of MyFB so that it works.
 * Each method must work in the worst case time given below (assuming uniform hashing and ignoring string lengths):
 *
 *   addPerson must take constant time
 *
 *   addFriendship must take constant time
 *
 *   queryFriendship must take constant time
 *
 *   removeFriendship should take constant time
 *
 *   lookupFriends should take time proportional to the number of
 *   friends that the named person has (or less)
 *
 * Your solution should use a single field of type
 *
 *   HashMap<Person,HashSet<Person>>
 *
 * You will need to add hashCode and equals methods to the Person class.
 * Follow the suggestions from Joshua Bloch, given in items 7 and 8 here:
 *
 *   http://fpl.cs.depaul.edu/jriely/ds2/extras/Chapter3.pdf
 *
 * Don't change the first line of any method given below.
 * The names and types of the methods should not change.
 * I will run you MyFB class using my own main method.
 *
 * You can add more classes too.  Just put them all in this file, inside MyFB.
 * Any classes you add should be "static" and not "public".
 * You can import/use any file in the java APIs.
 * You can import/use any file in the algs packages.
 *
 * You do not need to implement a hash table for this assignment.
 * You can use java.util.HashMap, java.util.HashSet, or the implementations in algs34.
 *
 * You do not need to implement a iterable object.
 * You can use algs13.Queue.
 * Also note that all of the Map implementations we have looked at create an iterable
 *   object via the keys() method.
 *
 *
 * Here is an example session:
 *
 *   INPUT           OUTPUT
 *
 *   p sa 8
 *   p li 7
 *   p he 9
 *   p tu 5
 *   f li 7 tu 5
 *   f li 7 he 9
 *   f tu 5 sa 8
 *   l tu 5          li 7 sa 8
 *   l sa 8          tu 5
 *   u li 7 tu 5
 *   l tu 5          sa 8
 *   q li 7 he 9     yes
 *   q he 9 li 7     yes
 *   q he 9 li 23    no
 *   q he 9 he 9     no
 *
 * Note that friendship is symmetric and irreflexive.
 * So if a/b are friends, then so are b/a.
 * And no one is their own friend.
 *
 * Put the following in a file "input.txt" to run this test:
p sa 8
p li 7
p he 9
p tu 5
f li 7 tu 5
f li 7 he 9
f tu 5 sa 8
l tu 5
l sa 8
u li 7 tu 5
l tu 5
q li 7 he 9
q he 9 li 7
q he 9 li 23
q he 9 he 9
 */
public class MyFB {	
	Person k;
	HashMap<Person,HashSet<Person>> map;
	
	public MyFB()
	{
		map=new HashMap<Person,HashSet<Person>>();
	}
	public MyFB(HashMap<Person,HashSet<Person>> map)
	{
		this.map = map;
	}
	public HashMap<Person,HashSet<Person>> getMap ()
	{
		return map;
	}
	public static boolean DEBUG = true;
	public static Person makePerson (In in) {
		try {
			String name = in.readString ();
			int age = in.readInt ();
			return makePerson (name, age);
		} catch (java.util.InputMismatchException e) {
			StdOut.println ("Input format error");
			return null;
		}
	}
	public static Person makePerson (String name, int age) {
		return new Person (name, age);
	}
	static class Person {
		String name;
		int age;
		public Person (String name, int age) {
			this.name = name;
			this.age = age;
		}
		public String toString () {
			return name + " " + age;
		}
		public int hashCode ()
		{
			int hash = 31;
			hash = 31 * hash + ((Integer) age).hashCode();
			hash = 31 * hash + (null == name ? 0 : name.hashCode());
			return hash;
		}
		public boolean equals (Object o)
		{
			if (this == o)
				return true;
			if((o == null) || (o.getClass() != this.getClass()))
				return false;
		Person test = (Person)o;
		return age == test.age && (name == test.name || (name != null && name.equals(test.name)));
		}
	}

	// a person "exists" after they are added using addPerson
	// addPerson does nothing if p already exists
	public void addPerson (Person p) {
		if (DEBUG) { StdOut.format ("P %s\n", p); }
		if (map.containsValue(p))
		{
			StdOut.println("This person is "+ "already exists.");
		return;
		}
		if (! map.containsValue(p))
		{
			HashSet<Person>set1 = new HashSet<Person>();
			set1.add(p);
			map.put(p, set1);
		}
	}
	// addFriendship does nothing if p1 and p2 are already friends or if one does not exist
	public void addFriendship (Person p1, Person p2) {
		if (DEBUG) { StdOut.format ("F %s %s\n", p1, p2); }
		if (p1.equals(p2))
		{
			return;
		}
		if (!map.containsKey(p1) || !map.containsKey(p2))
		{
			return;
		}
		if (map.containsKey(p1) && map.containsKey(p2))
		{
			HashSet<Person> set1 = map.get(p1);
			HashSet<Person> set2 = map.get(p2);
			set1.add(p2);
			set2.add(p1);
			this.map.put(p1, set1);
			this.map.put(p2, set2);
		}
	}
	// removeFriendship does nothing if p1 and p2 are not friends or if one does not exist
	public void removeFriendship (Person p1, Person p2) {
		if (DEBUG) { StdOut.format ("U %s %s\n", p1, p2); }
		if (p1.equals(p2))
		{
			return;
		}
		if (!map.containsKey(p1) || !map.containsKey(p2))
		{
			return;
		}
		if (map.containsKey(p1) && map.containsKey(p2))
		{
			HashSet<Person> set1 = map.get(p1);
			HashSet<Person> set2 = map.get(p2);
			set1.remove(p2);
			set2.remove(p1);
			map.remove(p2, set2);
			map.remove(p1, set1);
			map.remove(p1);
		}
	}
	// queryFriendship returns false if p1 and p2 are not friends or if one does not exist
	public boolean queryFriendship (Person p1, Person p2) {
		if (DEBUG) { StdOut.format ("Q %s %s\n", p1, p2); }
		if (!map.containsKey(p1) || !map.containsKey(p2))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	// lookupFriends returns null or empty iterable if p does not exists
	public Iterable<Person> lookupFriends (Person p) {
		if (DEBUG) { StdOut.format ("L %s\n", p); }
		return null;
	}

	public static void main (String[] args) {
		Trace.showBuiltInObjects (true);
		//Trace.run ();
		/*
		 * The first line below causes "in" to read from the console. You can
		 * change this to read from a file, by using something like the line
		 * below, where "input.txt" is a file you create in your eclipse
		 * workspace. The file should be in the same folder that contains "src"
		 * "bin" and "lib":
		 */

		//In[] inputFiles = new In[] { new In () }; // from console
		//In[] inputFiles = new In[] { new In ("input.txt") }; // from file
		In[] inputFiles = new In[] { new In ("input.txt"), new In () }; // from file, then console
		MyFB fb = new MyFB ();
		StdOut.println ("Enter one of the following:");
		StdOut.println ("  P name age -- add person");
		StdOut.println ("  F name1 age1 name2 age2 -- add friendship");
		StdOut.println ("  U name1 age1 name2 age2 -- remove friendship");
		StdOut.println ("  Q name1 age1 name2 age2 -- query friendship");
		StdOut.println ("  L name age -- lookup friends");
		StdOut.println ("  R -- restart");
		StdOut.println ("  X -- exit");
		for (In in : inputFiles) {
			while (in.hasNextLine ()) {
				//StdOut.println (fb. ...); // while debugging, print debugging info here!  You can print maps, sets, lists.
				String action;
				try {
					action = in.readString ();
				} catch (NoSuchElementException e) { continue; }
				switch (action) {
				case "P":
				case "p": {
					Person p = makePerson (in);
					fb.addPerson (p);
					break;
				}
				case "F":
				case "f": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					fb.addFriendship (p1, p2);
					break;
				}
				case "U":
				case "u": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					fb.removeFriendship (p1, p2);
					//Trace.draw ();
					break;
				}
				case "Q":
				case "q": {
					Person p1 = makePerson (in);
					Person p2 = makePerson (in);
					boolean result = fb.queryFriendship (p1, p2);
					StdOut.println (result ? "Yes" : "No");
					break;
				}
				case "L":
				case "l": {
					Person p = makePerson (in);
					Iterable<Person> result = fb.lookupFriends (p);
					if (result != null) {
						for (Person friend : result) {
							StdOut.print (friend);
							StdOut.print (" ");
						}
					}
					StdOut.println ();
					break;
				}
				case "R":
				case "r":
					fb = new MyFB ();
					break;
				case "X":
				case "x":
					System.exit (0);
				}
			}
		}
	}
}
