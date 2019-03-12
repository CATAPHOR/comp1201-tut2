import java.util.*;

public class CircularArrayQueue implements MyQueue
{
	//private ints to hold size, head and tail pointers
	private int head, tail;
	//private array holds elements of queue
	private int[] array;
	
	//constructor initialises all class vars to 0 (empty) and creates empty array
	public CircularArrayQueue()
	{
		this.head = this.tail = 0;
		this.array = new int[11];
	}
	
	//enqueues int in array
	public void enqueue(int i)
	{
		//if no capacity left, double array size
		if (this.getCapacityLeft() == 0)
		{
			this.array = doubleArraySize(this.array);
		}
		//place element at tail position and increment tail
		this.array[this.tail++] = i;
		//return tail to beginning of array if it exceeds the index range
		this.tail %= this.array.length;
	}

	//takes input array, returns array with same contents but double size
	public int[] doubleArraySize(int[] input)
	{
		//create output array of twice the length of the input array
		int[] output = new int[2 * input.length];
		//populate output array with head to end of input array
		for (int i = this.head; i < input.length; i++)
		{
			output[i] = input[i];
		}
		//if tail is before head then start of input array to tail is appended after the end of input array
		if (this.tail < this.head)
		{
			for (int i = 0; i < this.tail; i++)
			{
				output[input.length + i] = input[i];
			}
			//give tail new position after end of output's elements
			this.tail = this.head + input.length - 1;
		}
		//return output array
		return output;
	}
	
	//dequeues from array (throw exception if isEmpty())
	public int dequeue() throws NoSuchElementException 
	{
		//if empty array, no dequeue possible
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		else
		{
			//set output element to element at head position and increment head
			int output = this.array[this.head++];
			//return head to beginning of array if it exceeds the index range
			this.head %= this.array.length;
			//return output
			return output;
		}
	}

	//return number of elements in queue
	public int noItems() 
	{
		//if tail ahead of head
		if (this.tail >= this.head)
		{
			return this.tail - this.head;
		}
		//if head ahead of tail
		else
		{
			return this.array.length - (this.head - this.tail);
		}
	}

	//return if empty
	public boolean isEmpty() 
	{
		return (this.tail == this.head);
	}
	
	//return number of free elements left in queue
	public int getCapacityLeft()
	{
		//subtract 1 as the tail cannot hold an element
		return this.array.length - (this.noItems()) - 1;
	}
}
