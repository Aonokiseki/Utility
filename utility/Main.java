
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utility.FileOperator;

public class Main {
	public static void main(String[] args){
		String inputFilePath = "C:/work/JAVA_WORKSPACE/Utility";
	    String suffix = ".java";
	    Map<String,String> options = new HashMap<String,String>();
	    List<String> list;
		try {
			list = FileOperator.traversal(inputFilePath, suffix, options);
			for(int i=0;i<list.size(); i++){
				System.out.println(list.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}