package execrise.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import utility.FileOperator;

public class BigDataSort {
	private final static int FILE_COUNT = 11;
	private final static String FILES_AFTER_PARTITION_DIRECTORY = "temp";
	private final static String ENCODING = "UTF-8";
	private final static long TIME_SLEEP = 1000;
	private final static String LS = System.lineSeparator();
	private final static String FS = System.getProperty("file.separator");
	
	private List<File> srcFiles;
	private List<File> filesAfterPartition;
	
	public BigDataSort(){
		File fPointer = new File(FILES_AFTER_PARTITION_DIRECTORY);
		if(!fPointer.exists())
			fPointer.mkdirs();
	}
	/**
	 * 执行排序
	 * @param inputFilePath 输入文件(目录)的路径,是目录则会遍历所有txt文件. 要求每行一个数字,必须是数字。
	 * @param outputFilePath 排序后输出文件, 只有一个。每行一个数字。
	 * @throws IOException 两个路径存在错误时
	 */
	public void execute(String inputFilePath, String outputFilePath) throws IOException{
		srcFiles = FileOperator.traversal(inputFilePath, ".txt", false);
		executePartition();
		filesAfterPartition = FileOperator.traversal(FILES_AFTER_PARTITION_DIRECTORY, ".txt", false);
		localSort();
		merge(outputFilePath);
		clear(filesAfterPartition);
	}
	/**
	 * 对每个输入文件做划分
	 * @throws IOException
	 */
	private void executePartition() throws IOException{
		for(int i=0,size=srcFiles.size(); i<size; i++){
			partitionOnEachFile(srcFiles.get(i));
		}
	}
	/**
	 * 单个文件的划分<br>
	 * 对文件中的每行(数字), 用这个值对FILE_COUNT求模, 用来散落到不同文件中。
	 * @param filePath
	 * @throws IOException
	 */
	private void partitionOnEachFile(File file) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), ENCODING));
		List<BufferedWriter> bufferedWriters = new ArrayList<BufferedWriter>(FILE_COUNT);
		for(int i=0; i<FILE_COUNT; i++){
			bufferedWriters.add(i, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILES_AFTER_PARTITION_DIRECTORY +FS+ "filesAfterPartition_"+i+".txt", true), ENCODING)));
		}
		double currentLineNumber = 0.0;
		int fileId = -1;
		String currentLine;
		while((currentLine = bufferedReader.readLine()) != null){
			if(currentLine.trim().isEmpty())
				continue;
			currentLineNumber = Double.valueOf(currentLine);
			fileId = (int)(currentLineNumber) % FILE_COUNT;
			bufferedWriters.get(fileId).write(currentLine+LS);
		}
		bufferedReader.close();
		for(int i=0; i<FILE_COUNT; i++){
			bufferedWriters.get(i).close();
		}
	}
	/**
	 * 对每个文件做内排序
	 * @throws IOException
	 */
	private void localSort() throws IOException{
		for(int i=0,size=filesAfterPartition.size(); i<size; i++){
			localSortOnEachFile(filesAfterPartition.get(i));
		}
	}
	/**
	 * 单个文件的内排序, 排序后重新写入到源文件
	 * @param filePath
	 * @throws IOException
	 */
	private void localSortOnEachFile(File file) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), ENCODING));
		List<Double> numbers = new ArrayList<Double>();
		double currentLineNumber = 0.0;
		String currentLine;
		while((currentLine = bufferedReader.readLine()) != null){
			currentLineNumber = Double.valueOf(currentLine);
			numbers.add(currentLineNumber);
		}
		bufferedReader.close();
		Collections.sort(numbers);
		StringBuilder sb = new StringBuilder();
		for(int i=0,size=numbers.size(); i<size; i++){
			sb.append(numbers.get(i)+LS);
		}
		FileOperator.write(file.getAbsolutePath(), sb.toString());
	}
	/**
	 * 归并<br>
	 * 开启一个最小值队列, 队列的每个元素都是每个文件中最前边的行(最小值)<br>
	 * 每弹出一个最小值, 找到这个最小值的来源文件, 然后让这个来源文件往下读一行<br>
	 * 当某个文件读完的时候, 最小值队列的size会减一, 同时将读这个文件的BufferedReader移出<br>
	 * 保证bufferedReaders和最小值队列元素的下标是同步的
	 * @param outputFilePath
	 * @throws IOException
	 */
	private void merge(String outputFilePath)throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath), ENCODING));
		List<BufferedReader> bufferedReaders = new LinkedList<BufferedReader>();
		List<Double> minNumbers = new LinkedList<Double>();
		String currentLine;
		for(int i=0,size=filesAfterPartition.size(); i<size; i++){
			bufferedReaders.add(i, new BufferedReader(new InputStreamReader(new FileInputStream(filesAfterPartition.get(i)), ENCODING)));
			currentLine = bufferedReaders.get(i).readLine();
			minNumbers.add(i, Double.valueOf(currentLine));
		}
		int indexOfMin = -1;
		while(!minNumbers.isEmpty()){
			indexOfMin = findMinIndex(minNumbers);
			bufferedWriter.append(minNumbers.remove(indexOfMin)+LS);
			if((currentLine = bufferedReaders.get(indexOfMin).readLine()) == null){
				bufferedReaders.remove(indexOfMin).close();
				continue;
			}
			minNumbers.add(indexOfMin, Double.valueOf(currentLine));
		}
		bufferedWriter.close();
	}
	
	private int findMinIndex(List<Double> numbers){
		double currentMinValue = Double.MAX_VALUE;
		int indexOfMin = -1;
		for(int i=0,size=numbers.size(); i<size; i++){
			if(currentMinValue < numbers.get(i))
				continue;
			currentMinValue = numbers.get(i);
			indexOfMin = i;
		}
		return indexOfMin;
	}
	
	private void clear(List<File> filesAfterPartition){
		File fPointer = null;
		System.gc();
		try {
			Thread.sleep(TIME_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0, size=filesAfterPartition.size(); i<size; i++){
			fPointer = filesAfterPartition.get(i);
			System.out.println(fPointer.delete());
		}
	}
}
