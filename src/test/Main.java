package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
정수 4를 1, 2, 3의 합으로 나타내는 방법은 총 7가지가 있다. 합을 나타낼 때는 수를 1개 이상 사용해야 한다.

1+1+1+1
1+1+2
1+2+1
2+1+1
2+2
1+3
3+1
정수 n이 주어졌을 때, n을 1, 2, 3의 합으로 나타내는 방법의 수를 구하는 프로그램을 작성하시오.

 */
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int x = Integer.parseInt(br.readLine());
		int val = 1;
		int count = 0; 
//		while(x != 1) {
//			if(x % 3 == 0 && x / 3 != 1)  
//				x = x / 3;
//			else if(x % 2 == 0 && x / 2 != 1)
//				x = x / 2;
//			else 
//				x = x - 1;
//			count++;
//		}
		
		while(val < x) {
			if(val * 3 <= x)
				val = val * 3;
			else if(val * 2 <= x)
				val = val * 2;
			else 
				val = val + 1;
			count++;
		}
		
		System.out.println(count);
	}
}


