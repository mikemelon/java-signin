package cn.lynu.lyq.signin.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

public class WordDocumentCutToParts {

	public static void main(String[] args) throws Exception {
		HWPFDocument doc = new HWPFDocument(new FileInputStream("d:/Java新问题1.doc"));
		Range range = doc.getRange();
		int nParagraph = range.numParagraphs();
		System.out.println("一共有" + nParagraph + "个段落");

		PicturesTable picTable = doc.getPicturesTable();
		int nPic = picTable.getAllPictures().size();
		System.out.println("一共有" + nPic + "张图片");

		// 先扫描一遍全文档，找到每个问题分割位置（空行段落），note:每个问题空一行结束
		ArrayList<Integer> blankParagraphIndex = new ArrayList<Integer>();
		ArrayList<Integer> partWithPictureIndex = new ArrayList<Integer>();//带有图片的文档的编号，从1开始
		for (int i = 0; i < nParagraph; i++) {
			Paragraph p = range.getParagraph(i);
			String text = p.text();

			// 判断该行是否含有图片
			boolean isPicture = false;
			for (int j = 0; j < p.numCharacterRuns(); j++) {
				CharacterRun cr = p.getCharacterRun(j);
				if (picTable.hasPicture(cr)) {
					isPicture = true;
					partWithPictureIndex.add(blankParagraphIndex.size()+1);
				}
			}

			if (text.trim().equals("") && false == isPicture){
				blankParagraphIndex.add(i);
			}
		}
		System.out.println("一共有" + blankParagraphIndex.size() + "个问题（即空行段落数量）");
		// System.out.println(blankParagraphIndex);
		 System.out.println("如下文档含有图片："+partWithPictureIndex);
		

		// 对每个“空行段落”分割的部分，读出标题作为word文件名，读出所有文本字符串写入该文件
		for (int i = 0; i < blankParagraphIndex.size(); i++) {
			int startParaIndex = 0;
			if (i > 0) {
				startParaIndex = blankParagraphIndex.get(i - 1) + 1;
			}
			//读出第一行中的标题    note:标题就是该部分第一行里面第一个句号前面的部分 
			Paragraph p = range.getParagraph(startParaIndex);
			String text = p.text();
			String title = text.substring(0, text.indexOf("。"));
//			System.out.println(title);

			File myFile = new File("d:/JavaProblems/" + title + ".doc");
			if (false == myFile.exists()) {
				myFile.createNewFile();
			}
			
			//读出“空行段落”分割的部分 ，存入一个StringBuilder
			StringBuilder sb = new StringBuilder();
			for(int j=startParaIndex; j<=blankParagraphIndex.get(i); j++){
				Paragraph p1 = range.getParagraph(j);
				String text1 = p1.text();
				sb.append(text1);
			}
			
			//将字符串写入该文档    problem:不能一行一行写入（前面缺字？）
//			FileInputStream fis = new FileInputStream(myFile);
			FileInputStream fis = new FileInputStream("empty.doc"); //貌似得有一个空的word模板文档，然后用替换方法
			
			HWPFDocument  docForWrite = new HWPFDocument(fis);
			Range rangeForWrite = docForWrite.getRange();
			rangeForWrite.replaceText("${Content}", sb.toString());
			FileOutputStream fos = new FileOutputStream(myFile);
			docForWrite.write(fos);
			docForWrite.close();
			fos.close();
			fis.close();
		}
		doc.close();
	}

}
