import java.io.*;
import java.util.*;
public class Main{ 
	public static void main(String[]args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer std = new StringTokenizer(br.readLine());
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder str1 = new StringBuilder();
		int N = Integer.parseInt(std.nextToken());
		std = new StringTokenizer(br.readLine());
		String answer = "";
		String[] str = new String[N];
		for(int i=0;i<N;i++) {
			str[i] = std.nextToken();
		}
		int[] check = new int[N];
		for(int i=0;i<N;i++) {
			int cnt = 0;
			while(check[cnt]!=0) cnt++;
			String tmp = str[cnt];
			int idx = cnt;
			for(int j=0;j<N;j++) {
				if(check[j]==0&&j!=cnt) {
					int count = 0;
					int c = 0;
					for(int k=0;k<str[j].length();k++) {
						int a = 0;
						if(count<tmp.length()) {
							a = tmp.charAt(count)-'0';
							count++;
						}
						else {
							a = tmp.charAt(0)-'0';
							count=1;
						}
						int b = str[j].charAt(k)-'0';
						if(a>b) break;
						else if(a<b) {
							tmp = str[j];
							idx = j;
							break;
						}
						else c++;
					}
					if(c==str[j].length()) {
						String tmp1 = tmp+str[j];
						long tmp12 = Long.parseLong(tmp1);
						String tmp2 = str[j]+tmp;
						long tmp21 = Long.parseLong(tmp2);
						if(tmp12<tmp21) {
							tmp = str[j];
							idx = j;
						}
					}	
				}
			}
			answer += tmp;
			check[idx] = 1;
		}
		if(answer.charAt(0)=='0') System.out.println(0);
		else System.out.println(answer);
	}	
}
