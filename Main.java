package Project;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("  ___________  ");
        System.out.println(" |    |      | ");
        System.out.println(" |____|______| ");
        System.out.println(" |    |      | ");
        System.out.println(" |____|______| ");
        System.out.println(" |    |      | ");
        System.out.println(" |____|______| ");
        System.out.println(" |    |      | ");
        System.out.println(" |____|______| \n");
		System.out.print("Enter Number Of Destination Wanted: ");
		int numOfDes = sc.nextInt();
		ArrayList<Integer> riders = new ArrayList<>();

		for (int i = 0; i < numOfDes; i++) {
			int num = i + 1;
			System.out.print("Enter Destination " + num + ": ");
			int x = sc.nextInt();
			riders.add(x);

		}
		System.out.print("Destination Are: [ ");
		for (int i = 0; i < numOfDes; i++) {

			System.out.print(riders.get(i));
			if (i > numOfDes-2) {
				break;
			} else {
				System.out.print(",");
			}

		}
		System.out.println(" ]\n");
		System.out.println(String.format("Minimum Steps= %d", minSteps(riders, 3)));

	}

	public static int minSteps(ArrayList<Integer> riders, int maxStops) {

		int n = riders.size();

		// initialize Array M to save the costs and S for the chosen floors
		int[][] M = new int[n][n];
		int[][] S = new int[n][n];

		for (int i = 0; i < n; i++) {
			M[i][0] = 0;
		}

		for (int diagonal = 2; diagonal <= n; diagonal++) {

			for (int i = 0; i < n - diagonal + 1; i++) {
				int j = i + diagonal - 1;
				M[i][j] = Integer.MAX_VALUE;

				// if the difference between the two floors is one the cost will be 1 and the
				// chosen floor will be the lowest one
				if (riders.get(j) - riders.get(i) == 1) {
					M[i][j] = 1;
					S[i][j] = i;
				}

				else {
					// calculate the cost between the two floors
					int cost = 0;
					// calculate the mid floor between both floors
					int s = riders.get(i) + ((riders.get(j) - riders.get(i)) / 2);
					S[i][j] = s;
					// if the floor is in the riders chosen floors
					if (riders.indexOf(s) != -1) {

						for (int k = i; k <= j; k++) {
							// calculate the cost based on the previous results
							if (k == 0 && riders.indexOf(s) == 1)
								cost += M[0][riders.indexOf(s)];

							else
								cost += Math.min(M[Math.min(k, riders.indexOf(s))][Math.max(k, riders.indexOf(s))],
										M[Math.max(k, riders.indexOf(s))][Math.min(k, riders.indexOf(s))]);

						}
						M[i][j] = cost;

					} else {
						// if we don't know the cost to the floor we will calculate it
						int k = j;
						while (riders.get(k) > s) {
							cost += (riders.get(k) - s);
							k--;
						}
						k = i;
						while (riders.get(k) < s) {
							cost += (s - riders.get(k));
							k++;
						}
						M[i][j] = cost;
					}
				}
				M[j][i] = riders.get(j) - riders.get(i);

			}
		}
		System.out.println("the stops matrix");
		for (int i = 0; i < S.length; i++) {
			System.out.println();
			for (int j = 0; j < S.length; j++) {
				System.out.print(S[i][j]);
				System.out.print(" ");
			}
		}
		System.out.println();
		System.out.println("The costs matrix");
		for (int i = 0; i < M.length; i++) {
			System.out.println();
			for (int j = 0; j < M.length; j++) {
				System.out.print(M[i][j]);
				System.out.print(" ");
			}
		}
		System.out.println();
		System.out.println(String.format("The stop floor= %d", S[0][n - 1]));
		return M[0][n - 1];
	}

}