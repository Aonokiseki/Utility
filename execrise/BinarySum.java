package execrise;

public class BinarySum {
	public static void main(String[] args){
		String a = "111100";
		String b = "110101";
		System.out.println(addBinary1(a, b));
	}
	
	public static String addBinary(String a, String b){
		 StringBuffer sb = new StringBuffer();
	        int carry = 0, i = a.length()-1, j = b.length()-1;
	        while(i >= 0 || j >= 0 || carry != 0){
	            if(i >= 0) carry += a.charAt(i--)-'0';
	            if(j >= 0) carry += b.charAt(j--)-'0';
	            sb.append(carry%2);
	            carry /= 2;
	        }
	        return sb.reverse().toString();
	}
	
	public static String addBinary1(String a, String b){
		StringBuilder sb = new StringBuilder();
		int carry = 0;
		int i = a.length() - 1;
		int j = b.length() - 1;
		while(i >= 0 || j >= 0 || carry != 0){
			if(i >= 0){
				int carryAdd = a.charAt(i) - '0';
				carry += carryAdd;
				i--;
			}
			if(j >= 0){
				int carryAdd = b.charAt(j) - '0';
				carry += carryAdd;
				j--;
			}
			sb.append(carry%2);
			carry /= 2;
		}
		return sb.reverse().toString();
	}
}
