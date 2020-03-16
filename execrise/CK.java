package execrise;

import java.io.File;
import java.io.IOException;
import java.util.List;

import utility.FileOperator;

public class CK {
	public static void main(String[] args){
		String entrance = "C:/Users/trs/Downloads/CK2script";
		try {
			List<File> files = FileOperator.traversal(entrance, ".txt", false);
			String content = null;
			for(int i=0, size=files.size(); i<size; i++){
				content = FileOperator.read(files.get(i).getAbsolutePath());
				if(content.contains("combat_rating"))
					System.out.println(files.get(i).getAbsolutePath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
