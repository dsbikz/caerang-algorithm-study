import java.io.*;
public class Main{
	public static void main(String[]args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer std = new StringTokenizer(br.readLine());
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder str1 = new StringBuilder();
		int a = Integer.parseInt(std.nextToken());
		int[] ch = new int[a+1];
		int[] check = new int[a+1];
		std = new StringTokenizer(br.readLine());
		int max =-1;
		for(int i=1;i<=a;i++) ch[i] = Integer.parseInt(std.nextToken());
		for(int i=1;i<=a;i++) {
			if(check[ch[i]-1]==0) check[ch[i]] = 1;
			else check[ch[i]] = check[ch[i]-1]+1;
			max = Math.max(max, check[ch[i]]);
		}
		System.out.println(a-max);
	}
}
