import java.util.*;
interface WaterConservationSystem{
	void calculateTrappedWater(int[] blockHeights);
}
abstract class RainySeasonConservation implements WaterConservationSystem{
	public abstract void calculateTrappedWater(int[] blockHeights);
}
class CityBlockConservation extends RainySeasonConservation{
	public void calculateTrappedWater(int[] blockHeights){
		int total=0;
		int n=blockHeights.length;
		int flag[]=new int[n];
		for(int i=0;i<n;i++){
			flag[i]=1;
		}
		//Firstly we will create an array and assign 0 if a house can collect water
		for(int i=1;i<n-1;i++){
			int flag1=0;
			int flag2=0;
			for(int j=i-1;j>=0;j--){
				if(blockHeights[i]<blockHeights[j]){
					flag1=1;
					break;
				}
			}
			for (int j=i+1;j<n;j++){
				if(blockHeights[i]<blockHeights[j]){
					flag2=1;
					break;
				}
			}
			if(flag1==1 && flag2==1){
				flag[i]=0;
			}
		}
		//calculate water capacity for house with value assignes 0 from previous step
		for(int i=1;i<n-1;i++){
			int value1=0;
			int value2=0;
			if(flag[i]==0){
				for(int j=i-1;j>=0;j--){
					if(flag[j]==1){
						value1=blockHeights[j];
						break;
					}
				}
				for(int j=i+1;j<n;j++){
					if(flag[j]==1){
						value2=blockHeights[j];
						break;
					}
				}
				if(value1>value2){
					total+=value2-blockHeights[i];
				}
				else{
					total+=value1-blockHeights[i];
				}
			}
		}
		System.out.println("Total collection="+total);
	}
}
class Conservation{
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		CityBlockConservation obj=new CityBlockConservation();
		int a=0;
		do
		{
			System.out.println("Enter the number of houses ");
			int b=sc.nextInt();
			int array[]=new int[b];
			for(int i=0;i<b;i++){
				System.out.println("Enter height of house "+(i+1));
				array[i]=sc.nextInt();
			}
			obj.calculateTrappedWater(array);
			System.out.println("Do you want to:1.Try Again  2.Exit");
			a=sc.nextInt();
		}while(a!=2);
		System.out.println("Thank you");
	}
}
