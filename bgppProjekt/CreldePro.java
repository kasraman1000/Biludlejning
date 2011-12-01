package bgppProjekt;

public class CreldePro {
	boolean creldeIsAwesome = true;	
	int awesomePoints = 0;
	public void awesomeCheck()
	{
		if (awesomePoints==0)
		{
			creldeIsAwesome=false;
		}
		
	}
	public void doIgiveCreldeFreeRedbull() {
		if (creldeIsAwesome == true)
		{ 
			System.out.println("Give the Man a Redbull!");
		
		}
		else
		{
			System.out.println("Give the Man a pity Redbull!");
		}
	}

}
