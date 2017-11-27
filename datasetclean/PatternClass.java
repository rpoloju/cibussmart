package datasetclean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PatternClass {

	public static void main(String[] args) {
		List<Integer> a = new ArrayList<Integer>();
        int n;
        System.out.println("Enter n");
        Scanner s = new Scanner(System.in);
        n = s.nextInt();
        for (int i=0;i<n;i++) {
            if (i%2 == 0) 
                a.add(1);
            else
                a.add(0);
            Collections.reverse(a);
            System.out.println(a);
            Collections.reverse(a);
        }
	}
}
